package ua.lviv.tariffs.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PrepaidTariffTest {

    @Test
    void gettersAndSetters_shouldWorkCorrectly() {
        PrepaidTariff tariff = new PrepaidTariff(
                "Prepaid", 100.0, 50, 200, 5.0, 30, NetworkType._4G, 10
        );

        assertEquals("Prepaid", tariff.getName());
        assertEquals(100.0, tariff.getMonthlyFee());
        assertEquals(50, tariff.getClients());
        assertEquals(200, tariff.getMinutes());
        assertEquals(5.0, tariff.getDataGb());
        assertEquals(30, tariff.getSms());
        assertEquals(NetworkType._4G, tariff.getNetwork());
        assertEquals(10, tariff.getTopUpBonusPercent());

        tariff.setTopUpBonusPercent(25);
        assertEquals(25, tariff.getTopUpBonusPercent());
    }

    @Test
    void effectiveFee_shouldDecreaseWhenBonusIncreases() {
        PrepaidTariff tariff = new PrepaidTariff(
                "Bonus", 100.0, 10, 100, 5.0, 10, NetworkType.GSM, 20
        );

        double effective = tariff.effectiveFee();

        assertEquals(80.0, effective);
    }

    @Test
    void toString_shouldContainClassNameAndBaseFields() {
        PrepaidTariff tariff = new PrepaidTariff(
                "Test", 100.0, 10, 100, 5.0, 10, NetworkType.GSM, 15
        );

        String str = tariff.toString();

        assertTrue(str.contains("PrepaidTariff"));
        assertTrue(str.contains("Test"));
    }
}
