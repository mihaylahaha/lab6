package ua.lviv.tariffs.util;

import org.junit.jupiter.api.Test;
import ua.lviv.tariffs.model.*;

import static org.junit.jupiter.api.Assertions.assertTrue;

class FeeComparatorTest {

    @Test
    void compare_shouldReturnNegative_whenFirstCheaper() {
        FeeComparator comparator = new FeeComparator();
        Tariff t1 = new PrepaidTariff("Cheap", 50.0, 10, 100, 5.0, 10, NetworkType.GSM, 10);
        Tariff t2 = new PrepaidTariff("Expensive", 100.0, 10, 100, 5.0, 10, NetworkType.GSM, 10);

        int result = comparator.compare(t1, t2);

        assertTrue(result < 0);
    }

    @Test
    void compare_shouldReturnPositive_whenFirstMoreExpensive() {
        FeeComparator comparator = new FeeComparator();
        Tariff t1 = new PrepaidTariff("Expensive", 100.0, 10, 100, 5.0, 10, NetworkType.GSM, 10);
        Tariff t2 = new PrepaidTariff("Cheap", 50.0, 10, 100, 5.0, 10, NetworkType.GSM, 10);

        int result = comparator.compare(t1, t2);

        assertTrue(result > 0);
    }

    @Test
    void compare_shouldReturnZero_whenFeesEqual() {
        FeeComparator comparator = new FeeComparator();
        Tariff t1 = new PrepaidTariff("A", 70.0, 10, 100, 5.0, 10, NetworkType.GSM, 10);
        Tariff t2 = new PrepaidTariff("B", 70.0, 10, 100, 5.0, 10, NetworkType.GSM, 20);

        int result = comparator.compare(t1, t2);

        assertTrue(result == 0);
    }
}
