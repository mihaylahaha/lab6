package ua.lviv.tariffs.analytics;

import ua.lviv.tariffs.model.Tariff;
import ua.lviv.tariffs.repository.TariffRepository;

public class TariffAnalytics {

    public int totalClients(TariffRepository repo) {
        return repo.all()
                .stream()
                .mapToInt(Tariff::getClients)
                .sum();
    }

    public double averageMonthlyFee(TariffRepository repo) {
        return repo.all()
                .stream()
                .mapToDouble(Tariff::getMonthlyFee)
                .average()
                .orElse(0.0);
    }
}
