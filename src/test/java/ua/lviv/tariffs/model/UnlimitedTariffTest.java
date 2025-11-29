package ua.lviv.tariffs.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UnlimitedTariffTest {

    @Test
    void gettersAndSetters_shouldWorkCorrectly() {
        UnlimitedTariff tariff = new UnlimitedTariff(
                "Unlim", 200.0, 100, 500, 50.0, 100,
                NetworkType._5G, 100.0, 10
        );

        assertEquals("Unlim", tariff.getName());
        assertEquals(200.0, tariff.getMonthlyFee());
        assertEquals(100, tariff.getClients());
        assertEquals(500, tariff.getMinutes());
        assertEquals(50.0, tariff.getDataGb());
        assertEquals(100, tariff.getSms());
        assertEquals(NetworkType._5G, tariff.getNetwork());
        assertEquals(100.0, tariff.getFairUseGb());
        assertEquals(10, tariff.getThrottleMbps());

        tariff.setFairUseGb(150.0);
        tariff.setThrottleMbps(5);

        assertEquals(150.0, tariff.getFairUseGb());
        assertEquals(5, tariff.getThrottleMbps());
    }

    @Test
    void effectiveFee_shouldReturnMonthlyFeeWhenFairUseZeroOrNegative() {
        UnlimitedTariff tariff = new UnlimitedTariff(
                "Unlim", 200.0, 10, 100, 5.0, 10,
                NetworkType.GSM, 0.0, 10
        );

        assertEquals(200.0, tariff.effectiveFee());
    }

    @Test
    void effectiveFee_shouldReturnPricePerFairUseGb() {
        UnlimitedTariff tariff = new UnlimitedTariff(
                "Unlim", 200.0, 10, 100, 5.0, 10,
                NetworkType.GSM, 100.0, 10
        );

        double effective = tariff.effectiveFee();

        assertEquals(2.0, effective); // 200 / 100
    }

    @Test
    void toString_shouldContainClassNameAndBaseFields() {
        UnlimitedTariff tariff = new UnlimitedTariff(
                "Test", 200.0, 10, 100, 5.0, 10,
                NetworkType.GSM, 100.0, 10
        );

        String str = tariff.toString();
        assertTrue(str.contains("UnlimitedTariff"));
        assertTrue(str.contains("Test"));
    }
}
