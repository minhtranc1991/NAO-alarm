import java.awt.*;
import java.time.LocalDateTime;

public class Alarm {

    public static void main(String[] args) {

        float lastBuyONUS = 0;
        float lastBuyUSDT = 0;

        while (true) {
            float buyONUS;
            float buyUSDT;
            int base = 1;
            float lastHTDSalePrice = 47967;
            PriceList priceList = new PriceList();
            try {
                Prices prices = priceList.connection();
                float askHTDPrice = prices.getHTDUSDT().getAsk() * prices.getONUSUSDT().getAsk();
                LocalDateTime dateTime = LocalDateTime.now();

                buyONUS = base / prices.getONUSVNDC().getBid() * prices.getONUSUSDT().getAsk() * prices.getUSDTVNDC().getAsk();
                buyUSDT = base / prices.getUSDTVNDC().getBid() / prices.getONUSUSDT().getBid() * prices.getONUSVNDC().getAsk();

                if (buyONUS != lastBuyONUS || buyUSDT != lastBuyUSDT) {
                    System.out.println("---------------------------------------------------------------------------------------------------------------");
                    System.out.println(dateTime + ": ");
                    System.out.println("HTD/USDT: " + prices.getHTDUSDT() + " HTD/VNDC: Price(bid=" + format(prices.getHTDUSDT().getBid() * prices.getUSDTVNDC().getBid()) +
                            ", ask=" + format(prices.getHTDUSDT().getAsk() * prices.getUSDTVNDC().getAsk()) + ") Sale price: " + format(lastHTDSalePrice));
                    System.out.println("ONUS/USDT: Price(bid=" + format(prices.getONUSUSDT().getBid() * prices.getUSDTVNDC().getBid()) +
                            ", ask=" + format(prices.getONUSUSDT().getAsk() * prices.getUSDTVNDC().getAsk()) + ") - " + prices.getONUSUSDT());
                    System.out.println("ONUS/VNDC: " + prices.getONUSVNDC());
                    System.out.println("USDT/VNDC: " + prices.getUSDTVNDC());
                    System.out.println("Buy ONUS: " + format(buyONUS * 100) + " %");
                    System.out.println("Buy USDT: " + format(buyUSDT * 100) + " %");
                    lastBuyONUS = buyONUS;
                    lastBuyUSDT = buyUSDT;
                    System.out.flush();
                }

                if (buyONUS >= base * 1.001) {
                    Toolkit.getDefaultToolkit().beep();
                    System.out.println("Buy ONUS: " + format((buyONUS - 1) * 0.065 * prices.getONUSVNDC().getBid()) + "M !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
                } else if (buyUSDT >= base * 1) {
                    Toolkit.getDefaultToolkit().beep();
                    System.out.println("Buy USDT: " + format((buyUSDT - 1) * 50000) + " !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
                }
                if (askHTDPrice >= lastHTDSalePrice) {
                    Toolkit.getDefaultToolkit().beep();
                    System.out.println("Sell HTD ask price: " + format(askHTDPrice) + " !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
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

    private static String format(double value) {
        return String.format("%.2f", value);
    }
}
