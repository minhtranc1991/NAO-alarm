import java.awt.*;
import java.time.LocalDateTime;

public class Alarm {

    public static void main(String[] args) {

        double lastBuyONUS = 0;
        double lastBuyUSDT = 0;

        while (true) {
            float buyONUS;
            float buyUSDT;
            int base = 1;
            float lastHTDSalePrice = 47967;
            PriceList priceList = new PriceList();
            Prices prices = priceList.connection();
            float askHTDPrice = prices.getHTDUSDT().getAsk() * prices.getONUSUSDT().getAsk();
            LocalDateTime dateTime = LocalDateTime.now();

            buyONUS = base / prices.getONUSVNDC().getBid() * prices.getONUSUSDT().getAsk() * prices.getUSDTVNDC().getAsk();
            buyUSDT = base / prices.getUSDTVNDC().getBid() / prices.getONUSUSDT().getBid() * prices.getONUSVNDC().getAsk();

            if (buyONUS != lastBuyONUS || buyUSDT != lastBuyUSDT) {
                System.out.println("-----------------------------------------------------------------");
                System.out.println(dateTime + ": ");
                System.out.println("HTD/USDT: " + prices.getHTDUSDT() + " HTD/VNDC: Price(bid=" + prices.getHTDUSDT().getBid() * prices.getUSDTVNDC().getBid() +
                        ", ask=" + prices.getHTDUSDT().getAsk() * prices.getUSDTVNDC().getAsk() + ") Sale price: " + lastHTDSalePrice);
                System.out.println("ONUS/USDT: Price(bid=" + prices.getONUSUSDT().getBid() * prices.getUSDTVNDC().getBid() +
                        ", ask=" + prices.getONUSUSDT().getAsk() * prices.getUSDTVNDC().getAsk() + ") - " + prices.getONUSUSDT());
                System.out.println("ONUS/VNDC: " + prices.getONUSVNDC());
                System.out.println("USDT/VNDC: " + prices.getUSDTVNDC());
                System.out.println("Buy ONUS: " + buyONUS * 100 + " %");
                System.out.println("Buy USDT: " + buyUSDT * 100 + " %");
                lastBuyONUS = buyONUS;
                lastBuyUSDT = buyUSDT;
                System.out.flush();
            }

            if (buyONUS >= base * 1.001) {
                Toolkit.getDefaultToolkit().beep();
                System.out.println("Buy ONUS: " + (buyONUS - 1) * 0.098 * prices.getONUSVNDC().getBid() + "M !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
            } else if (buyUSDT >= base * 1) {
                Toolkit.getDefaultToolkit().beep();
                System.out.println("Buy USDT: " + (buyUSDT - 0.998) * 51000 + " !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
            }
            if (askHTDPrice >= lastHTDSalePrice) {
                Toolkit.getDefaultToolkit().beep();
                System.out.println("Sell HTD ask price: " + askHTDPrice + " !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
            }

            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}