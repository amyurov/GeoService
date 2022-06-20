import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import ru.netology.entity.Country;
import ru.netology.i18n.LocalizationService;
import ru.netology.i18n.LocalizationServiceImpl;

import java.util.stream.Stream;

public class LocalizationServiceTest {

    private static LocalizationService localizationService;

    @BeforeAll
    public static void setLocalizationService() {
        localizationService = new LocalizationServiceImpl();
    }

    @ParameterizedTest
    @MethodSource("source")
    @DisplayName("Greeting consistent with country language")
    void shouldReturnCurrentGreeting(String expected, Country country) {
        //act
        String actual = localizationService.locale(country);
        //assert
        Assertions.assertEquals(expected, actual);
    }

    public static Stream source() {
        return Stream.of(Arguments.of("Добро пожаловать", Country.RUSSIA),
                Arguments.of("Welcome", Country.USA));
    }
}
