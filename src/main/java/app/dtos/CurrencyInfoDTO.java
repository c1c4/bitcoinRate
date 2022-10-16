package app.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public class CurrencyInfoDTO {

    private String code;

    private String rate;

    private String description;

    @JsonProperty("rate_float")
    private BigDecimal rateFloat;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getRateFloat() {
        return rateFloat;
    }

    public void setRateFloat(BigDecimal rateFloat) {
        this.rateFloat = rateFloat;
    }
}
