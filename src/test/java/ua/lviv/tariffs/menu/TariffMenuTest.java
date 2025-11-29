package ua.lviv.tariffs.menu;

import org.junit.jupiter.api.Test;
import ua.lviv.tariffs.model.NetworkType;
import ua.lviv.tariffs.model.PrepaidTariff;
import ua.lviv.tariffs.model.Tariff;
import ua.lviv.tariffs.repository.TariffRepository;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class TariffMenuTest {

    @Test
    void run_shouldCreatePrepaidTariffAndExit() {
        String input =
                "1\n" +   // створити тариф
                "1\n" +   // тип Prepaid
                "Test\n" +   // назва
                "100\n" +    // місячна плата
                "10\n" +     // клієнти
                "200\n" +    // хвилини
                "5\n" +      // ГБ
                "10\n" +     // SMS
                "1\n" +      // GSM
                "20\n" +     // бонус
                "0\n";       // вихід

        TariffRepository repository = new TariffRepository();
        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8)));

        TariffMenu menu = new TariffMenu(repository, scanner);
        menu.run();

        Collection<Tariff> tariffs = repository.all();
        assertEquals(1, tariffs.size());

        Tariff tariff = tariffs.iterator().next();
        assertTrue(tariff instanceof PrepaidTariff);
        assertEquals("Test", tariff.getName());
        assertEquals(100.0, tariff.getMonthlyFee());
        assertEquals(10, tariff.getClients());
        assertEquals(200, tariff.getMinutes());
        assertEquals(NetworkType.GSM, tariff.getNetwork());
    }
}
