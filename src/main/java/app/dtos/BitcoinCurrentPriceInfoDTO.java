package app.dtos;

import java.util.Map;

public class BitcoinCurrentPriceInfoDTO {

    private Map<String, CurrencyInfoDTO> bpi;

    private String disclaimer;

    private Map<String, String> time;

    public Map<String, CurrencyInfoDTO> getBpi() {
        return bpi;
    }

    public void setBpi(Map<String, CurrencyInfoDTO> bpi) {
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
