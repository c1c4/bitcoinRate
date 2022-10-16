package unit.app.services;

import app.dtos.BitcoinRatesDTO;
import app.dtos.CurrencyCountryDTO;
import app.services.BitcoinInfoService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import utils.Utils;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.whenNew;

@RunWith(PowerMockRunner.class)
@PrepareForTest(BitcoinInfoService.class)
public class BitcoinInfoServiceTest {
    private BitcoinInfoService service;
    private HttpURLConnection conn;
    private final Utils utils = new Utils();

    @Before
    public void setup() throws Exception {
        service = new BitcoinInfoService();

        URL url = mock(URL.class);
        conn = mock(HttpURLConnection.class);

        whenNew(URL.class).withArguments(ArgumentMatchers.anyString()).thenReturn(url);
        doReturn(conn).when(url).openConnection();
    }

    @Test
    public void shouldReturnCurrentRate() throws IOException {
        String expectedRate = "19,137.5771";
        InputStream responseInputStream = new ByteArrayInputStream(utils.readFile("currentRate.json").getBytes());

        doReturn(responseInputStream).when(conn).getInputStream();

        assertEquals(expectedRate, service.getCurrentRate("USD"));

    }

    @Test
    public void shouldThrowCurrentRateException() throws IOException {
        String expectedThrowMessage = "java.io.FileNotFoundException: https://api.coindesk.com/v1/bpi/currentprice/YXZ.json";
        doThrow(new FileNotFoundException("https://api.coindesk.com/v1/bpi/currentprice/YXZ.json"))
                .when(conn).getInputStream();
        Throwable throwable = assertThrows(RuntimeException.class, () -> service.getCurrentRate("YXZ"));
        assertEquals(RuntimeException.class, throwable.getClass());
        assertEquals(expectedThrowMessage, throwable.getMessage());
    }

    @Test
    public void shouldReturnLowestAndHighestValue() throws IOException {
        BitcoinRatesDTO expectedMinMax = new BitcoinRatesDTO("10260.033", "11545.1583");
        InputStream responseInputStream = new ByteArrayInputStream(utils.readFile("historicalCurrency.json").getBytes());

        doReturn(responseInputStream).when(conn).getInputStream();

        BitcoinRatesDTO actualLowestHighest = service.getBitcoinInfo("USD");

        assertEquals(expectedMinMax.getLowest(), actualLowestHighest.getLowest());
        assertEquals(expectedMinMax.getHighest(), actualLowestHighest.getHighest());
    }

    @Test
    public void shouldThrowMinAndMaxValueException() throws IOException {
        String expectedThrowMessage = "java.io.FileNotFoundException: https://api.coindesk.com/v1/bpi/historical/close.json?start=2020-09-16&end=2020-10-15&currency=XYZ";
        doThrow(new FileNotFoundException("https://api.coindesk.com/v1/bpi/historical/close.json?start=2020-09-16&end=2020-10-15&currency=XYZ"))
                .when(conn).getInputStream();
        Throwable throwable = assertThrows(RuntimeException.class, () -> service.getBitcoinInfo("YXZ"));
        assertEquals(RuntimeException.class, throwable.getClass());
        assertEquals(expectedThrowMessage, throwable.getMessage());
    }

    @Test
    public void shouldReturnListOfSupportedCurrencies() throws IOException {
        int expectedSize = 167;
        InputStream responseInputStream = new ByteArrayInputStream(utils.readFile("supportedCurrencies.json").getBytes());
        doReturn(responseInputStream).when(conn).getInputStream();

        List<CurrencyCountryDTO> listOfSupportedCurrencies = service.getSupportedCurrencies();

        assertEquals(expectedSize, listOfSupportedCurrencies.size());
    }

    @Test
    public void shouldReturnFalseForValidCurrency() throws IOException {
        String expectedThrowMessage = "java.io.FileNotFoundException: https://api.coindesk.com/v1/bpi/supported-currencies.json";
        doThrow(new FileNotFoundException("https://api.coindesk.com/v1/bpi/supported-currencies.json"))
                .when(conn).getInputStream();
        Throwable throwable = assertThrows(RuntimeException.class, () -> service.getBitcoinInfo("YXZ"));
        assertEquals(RuntimeException.class, throwable.getClass());
        assertEquals(expectedThrowMessage, throwable.getMessage());
    }
}