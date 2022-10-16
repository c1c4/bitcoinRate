package unit.app.shells;

import app.dtos.BitcoinRatesDTO;
import app.dtos.CurrencyCountryDTO;
import app.services.BitcoinInfoService;
import app.shells.ConvertCurrencyToBitcoinShellComponent;
import nl.altindag.log.LogCaptor;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(PowerMockRunner.class)
@PrepareForTest(ConvertCurrencyToBitcoinShellComponent.class)
public class ConvertCurrencyToBitcoinShellComponentTest {

    ConvertCurrencyToBitcoinShellComponent shell;
    BitcoinInfoService service;
    LogCaptor logCaptor;

    @Before
    public void setup() {
        logCaptor = LogCaptor.forClass(ConvertCurrencyToBitcoinShellComponent.class);
        service = mock(BitcoinInfoService.class);
        shell = new ConvertCurrencyToBitcoinShellComponent(service);
    }

    @After
    public void tearDown() {
        logCaptor.close();
    }

    @Test
    public void shouldPrintOnConsoleBitcoinInfo() {
        CurrencyCountryDTO currencyCountryDTO = new CurrencyCountryDTO();
        currencyCountryDTO.setCurrency("USD");
        currencyCountryDTO.setCountry("United States Dollar");
        doReturn(List.of(currencyCountryDTO)).when(service).getSupportedCurrencies();
        doReturn("19,137.5771").when(service).getCurrentRate("USD");
        doReturn(new BitcoinRatesDTO("10260.033", "11545.1583"))
                .when(service).getBitcoinInfo("USD");

        shell.bitcoinInfo("USD");
        assertEquals(3, logCaptor.getInfoLogs().size());
        assertEquals(1, logCaptor.getInfoLogs()
                .stream().filter(info -> info.contains("19,137.5771")).count());
        assertEquals(1, logCaptor.getInfoLogs()
                .stream().filter(info -> info.contains("10260.033")).count());
        assertEquals(1, logCaptor.getInfoLogs()
                .stream().filter(info -> info.contains("11545.1583")).count());
    }

    @Test
    public void shouldPrintNotValidCurrencyBitcoinInfo() {
        doReturn(new ArrayList<>()).when(service).getSupportedCurrencies();
        shell.bitcoinInfo("USD");
        assertEquals(1, logCaptor.getInfoLogs().size());
        assertEquals(1, logCaptor.getInfoLogs()
                .stream().filter(info -> info.contains("currency is not valid")).count());
    }
}
