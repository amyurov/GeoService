import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import ru.netology.entity.Country;
import ru.netology.entity.Location;
import ru.netology.geo.GeoService;
import ru.netology.geo.GeoServiceImpl;

import java.util.stream.Stream;

public class GeoServiceTest {

    private static GeoService geo;

    @BeforeAll
    public static void create() {
        geo = new GeoServiceImpl();
    }

    @ParameterizedTest
    @MethodSource("source")
    @DisplayName("Inbound IP matching with country")
    void shouldReturnCurrentLocationByIP(String ip, Country expected) {
        //act
        Location actual = geo.byIp(ip);
        //assert
        Assertions.assertEquals(expected, actual.getCountry());
    }

    public static Stream source() {
        return Stream.of(Arguments.of("172.0.32.11", Country.RUSSIA), Arguments.of("96.44.183.149", Country.USA));
    }
}
