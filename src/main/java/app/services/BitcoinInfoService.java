package app.services;

import app.dtos.BitcoinCurrentPriceInfoDTO;
import app.dtos.BitcoinHistoricalInfoDTO;
import app.dtos.BitcoinRatesDTO;
import app.dtos.CurrencyCountryDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
public class BitcoinInfoService {
    @Cacheable("supportedCurrencies")
    public List<CurrencyCountryDTO> getSupportedCurrencies() {
        try {
            var urlString = "https://api.coindesk.com/v1/bpi/supported-currencies.json";
            URL url = new URL(urlString);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");

            InputStream responseStream = con.getInputStream();

            ObjectMapper mapper = new ObjectMapper();

            return Arrays.asList(mapper.readValue(responseStream, CurrencyCountryDTO[].class));
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public String getCurrentRate(String currency) {
        try {
            var urlString = String.format("https://api.coindesk.com/v1/bpi/currentprice/%s.json", currency);


            URL url = new URL(urlString);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");

            InputStream responseStream = con.getInputStream();

            ObjectMapper mapper = new ObjectMapper();
            BitcoinCurrentPriceInfoDTO dto = mapper.readValue(responseStream, BitcoinCurrentPriceInfoDTO.class);
            return dto.getBpi().get(currency).getRate();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

    }

    public BitcoinRatesDTO getBitcoinInfo(String currency) {
        try {


            var formatPattern = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            var date = LocalDate.now().minusYears(2);
            var todayString = date.format(formatPattern);
            var todayMinus30String = date.minusDays(29).format(formatPattern);

            var urlString = String.format(
                    "https://api.coindesk.com/v1/bpi/historical/close.json?start=%s&end=%s&currency=%s",
                    todayMinus30String,
                    todayString,
                    currency
            );

            URL url = new URL(urlString);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");

            InputStream responseStream = con.getInputStream();

            ObjectMapper mapper = new ObjectMapper();
            BitcoinHistoricalInfoDTO dto = mapper.readValue(responseStream, BitcoinHistoricalInfoDTO.class);


            var highest = dto.getBpi().values().stream().max(BigDecimal::compareTo).orElseThrow();
            var lowest = dto.getBpi().values().stream().min(BigDecimal::compareTo).orElseThrow();

            return new BitcoinRatesDTO(String.valueOf(lowest), String.valueOf(highest));
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

}
