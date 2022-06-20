import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;
import ru.netology.entity.Country;
import ru.netology.entity.Location;
import ru.netology.geo.GeoService;
import ru.netology.geo.GeoServiceImpl;
import ru.netology.i18n.LocalizationService;
import ru.netology.i18n.LocalizationServiceImpl;
import ru.netology.sender.MessageSenderImpl;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class MessageSenderTest {

    public static Map<String, String> header;

    @BeforeEach
    public void setHeader() {
        header = new HashMap<>();
    }

    @ParameterizedTest
    @MethodSource("source")
    void shouldReturnStringWithCorrectLanguage(String Ip, Country country, String msg) {
        GeoService geoService = Mockito.mock(GeoServiceImpl.class);
        Mockito.when(geoService.byIp(Ip))
                .thenReturn(new Location(null, country, null, 0));

        LocalizationService localizationService = Mockito.mock(LocalizationServiceImpl.class);
        Mockito.when(localizationService.locale(country))
                .thenReturn(msg);

        MessageSenderImpl sender = new MessageSenderImpl(geoService, localizationService);
        header.put(MessageSenderImpl.IP_ADDRESS_HEADER, Ip);
        String expected = msg;
        Assertions.assertEquals(expected, sender.send(header));
    }

    public static Stream source() {
        return Stream.of(Arguments.of("172.123.12.19", Country.RUSSIA, "Добро пожаловать"),
                Arguments.of("96.44.183.149", Country.USA, "Welcome"));
    }

}
