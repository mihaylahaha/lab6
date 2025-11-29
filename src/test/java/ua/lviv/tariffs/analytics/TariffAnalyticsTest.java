package ua.lviv.tariffs.analytics;

import org.junit.jupiter.api.Test;
import ua.lviv.tariffs.model.NetworkType;
import ua.lviv.tariffs.model.PrepaidTariff;
import ua.lviv.tariffs.model.Tariff;
import ua.lviv.tariffs.repository.TariffRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TariffAnalyticsTest {

    private final TariffAnalytics analytics = new TariffAnalytics();

    @Test
    void totalClients_shouldSumClientsFromAllTariffs() {
        TariffRepository repo = new TariffRepository();

        Tariff t1 = new PrepaidTariff("T1", 100.0, 100,
                200, 5.0, 10, NetworkType.GSM, 10);
        Tariff t2 = new PrepaidTariff("T2", 150.0, 50,
                300, 10.0, 20, NetworkType._4G, 15);

        repo.add(t1);
        repo.add(t2);

        int total = analytics.totalClients(repo);

        assertEquals(150, total);
    }

    @Test
    void averageMonthlyFee_shouldReturnAverageOfAllTariffs() {
        TariffRepository repo = new TariffRepository();

        Tariff t1 = new PrepaidTariff("T1", 100.0, 10,
                200, 5.0, 10, NetworkType.GSM, 10);
        Tariff t2 = new PrepaidTariff("T2", 200.0, 20,
                300, 10.0, 20, NetworkType._4G, 15);

        repo.add(t1);
        repo.add(t2);

        double avg = analytics.averageMonthlyFee(repo);

        assertEquals(150.0, avg);
    }

    @Test
    void averageMonthlyFee_shouldReturnZeroWhenNoTariffs() {
        TariffRepository repo = new TariffRepository();

        double avg = analytics.averageMonthlyFee(repo);

        assertEquals(0.0, avg);
    }
}
