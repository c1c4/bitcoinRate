package app.dtos;

public class BitcoinRatesDTO {

    private String lowest;

    private String highest;

    public BitcoinRatesDTO(String lowest, String highest) {
        this.lowest = lowest;
        this.highest = highest;
    }

    public String getLowest() {
        return lowest;
    }

    public String getHighest() {
        return highest;
    }
}
