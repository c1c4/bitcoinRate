package app.dtos;

import java.math.BigDecimal;
import java.util.Map;

public class BitcoinHistoricalInfoDTO {

    private Map<String, BigDecimal> bpi;

    private String disclaimer;

    private Map<String, String> time;

    public Map<String, BigDecimal> getBpi() {
        return bpi;
    }

    public void setBpi(Map<String, BigDecimal> bpi) {
        this.bpi = bpi;
    }

    public String getDisclaimer() {
        return disclaimer;
    }

    public void setDisclaimer(String disclaimer) {
        this.disclaimer = disclaimer;
    }

    public Map<String, String> getTime() {
        return time;
    }

    public void setTime(Map<String, String> time) {
        this.time = time;
    }
}
