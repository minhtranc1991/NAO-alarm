import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;

public class PriceList {
    private static HttpURLConnection connection;

    Prices connection() {
        StringBuffer responseContent = new StringBuffer();
        BufferedReader reader;
        String line;
        Prices prices = new Prices();
        int status = 0;
        try {
            URL url = new URL("https://exchange.vndc.io/exchange/api/v1/showup-prices");
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);
            try {
                status = connection.getResponseCode();
            } catch (SocketTimeoutException e) {
                System.out.println("Disconnected !");
            }

            if (status > 299) {
                reader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
            } else {
                reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            }
            while ((line = reader.readLine()) != null) {
                responseContent.append(line);
            }
            reader.close();

            ObjectMapper mapper = new ObjectMapper();
            prices = mapper.readValue(String.valueOf(responseContent), Prices.class);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            connection.disconnect();
        }
        return prices;
    }
}
