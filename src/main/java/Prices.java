import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Prices {

    @JsonProperty("ONUSUSDT")
    private Price ONUSUSDT;

    @JsonProperty("ONUSVNDC")
    private Price ONUSVNDC;

    @JsonProperty("USDTVNDC")
    private Price USDTVNDC;

    @JsonProperty("HTDUSDT")
    private Price HTDUSDT;
}
