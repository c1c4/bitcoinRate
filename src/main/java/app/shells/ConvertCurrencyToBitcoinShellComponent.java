package app.shells;

import app.dtos.CurrencyCountryDTO;
import app.services.BitcoinInfoService;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@ShellComponent
public class ConvertCurrencyToBitcoinShellComponent {
    private BitcoinInfoService bitcoinInfoService;


    Logger log = Logger.getLogger(ConvertCurrencyToBitcoinShellComponent.class.getName());

    public ConvertCurrencyToBitcoinShellComponent(BitcoinInfoService bitcoinInfoService) {
        this.bitcoinInfoService = bitcoinInfoService;
    }


    @ShellMethod(value = "bitcoin info")
    public void bitcoinInfo(@ShellOption(value = "--cc", defaultValue = "EUR", help = "Provide a currency code (USD, EUR, GBP, etc.)") String currency) {
        String upperCaseCurrency = currency.toUpperCase();
        Boolean validCurrendy = checkCurrencyIsValid(upperCaseCurrency);

        if (validCurrendy) {
            var currentRate = bitcoinInfoService.getCurrentRate(upperCaseCurrency);
            var info = bitcoinInfoService.getBitcoinInfo(upperCaseCurrency);

            log.info(String.format("The current Bitcoin rate is: %s", currentRate));
            log.info(String.format("The lowest Bitcoin rate  is: %s", info.getLowest()));
            log.info(String.format("The highest Bitcoin  rate is: %s", info.getHighest()));
        } else {
            log.info("The current currency is not valid");
        }
    }

    private Boolean checkCurrencyIsValid(String currency) {
        List<CurrencyCountryDTO> currencyCountryDTOS = bitcoinInfoService.getSupportedCurrencies();

        List<CurrencyCountryDTO> filteredResult = currencyCountryDTOS.stream()
                .filter(ccDTO -> ccDTO.getCurrency().equals(currency))
                .collect(Collectors.toList());

        return filteredResult.size() > 0;
    }

}
