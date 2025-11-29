package ua.lviv.tariffs.util;

import ua.lviv.tariffs.model.Tariff;

import java.util.Comparator;

public class FeeComparator implements Comparator<Tariff> {

    @Override
    public int compare(Tariff t1, Tariff t2) {
        return Double.compare(t1.getMonthlyFee(), t2.getMonthlyFee());
    }
}
