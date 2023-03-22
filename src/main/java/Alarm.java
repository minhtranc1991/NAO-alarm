import java.awt.*;
import java.time.LocalDateTime;

public class Alarm {

    public static void main(String[] args) {

        float naoMA = 1839;

        while (true) {
            float buyNAO;
            float sellNAO;
            float slippage = 1.05f;
            PriceList priceList = new PriceList();
            try {
                Prices prices = priceList.connection();
                float askNAOPrice = prices.getNAOVNDC().getAsk();
                float bidNAOPrice = prices.getNAOVNDC().getBid();
                LocalDateTime dateTime = LocalDateTime.now();

                buyNAO = naoMA / slippage;
                sellNAO = naoMA * slippage;
//                naoMA = (naoMA * 233 + askNAOPrice + bidNAOPrice) / 235;
                if (buyNAO < askNAOPrice || sellNAO > bidNAOPrice) {
                    System.out.println("---------------------------------------------------------------------------------------------------------------");
                    System.out.println(dateTime + ": ");
                    System.out.println("NAO/VNDC: Price(bid= " + bidNAOPrice + ", ask= " + askNAOPrice + "), Buy: " + format(buyNAO) + " VNDC, Sell: " + format(sellNAO) + " VNDC");
                    System.out.flush();
                }

                if (buyNAO >= bidNAOPrice) {
                    Toolkit.getDefaultToolkit().beep();
                    System.out.println("Buy NAO: " + format(buyNAO) + " VNDC !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
                } else if (sellNAO <= askNAOPrice) {
                    Toolkit.getDefaultToolkit().beep();
                    System.out.println("Sell NAO: " + format(sellNAO) + " VNDC !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
                }

                Thread.sleep(3000);

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static String format(float value) {
        return String.format("%.2f", value);
    }
}
