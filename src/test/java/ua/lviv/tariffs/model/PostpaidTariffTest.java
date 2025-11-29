package ua.lviv.tariffs.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PostpaidTariffTest {

    @Test
    void gettersAndSetters_shouldWorkCorrectly() {
        PostpaidTariff tariff = new PostpaidTariff(
                "Post", 120.0, 40, 300, 10.0, 40, NetworkType._5G, 12
        );

        assertEquals("Post", tariff.getName());
        assertEquals(120.0, tariff.getMonthlyFee());
        assertEquals(40, tariff.getClients());
        assertEquals(300, tariff.getMinutes());
        assertEquals(10.0, tariff.getDataGb());
        assertEquals(40, tariff.getSms());
        assertEquals(NetworkType._5G, tariff.getNetwork());
        assertEquals(12, tariff.getContractMonths());

        tariff.setContractMonths(24);
        assertEquals(24, tariff.getContractMonths());
    }

    @Test
    void effectiveFee_shouldReturnMonthlyFeeWhenContractMonthsNonPositive() {
        PostpaidTariff tariff = new PostpaidTariff(
                "Bad", 100.0, 10, 100, 5.0, 10, NetworkType.GSM, 0
        );

        assertEquals(100.0, tariff.effectiveFee());
    }

    @Test
    void effectiveFee_shouldDistributeFeeAcrossContractPeriod() {
        PostpaidTariff tariff = new PostpaidTariff(
                "Contract", 100.0, 10, 100, 5.0, 10, NetworkType.GSM, 10
        );

        double effective = tariff.effectiveFee();

        assertEquals(120.0, effective); // 100 * 12 / 10
    }

    @Test
    void toString_shouldContainClassNameAndBaseFields() {
        PostpaidTariff tariff = new PostpaidTariff(
                "Test", 100.0, 10, 100, 5.0, 10, NetworkType.GSM, 12
        );

        String str = tariff.toString();

        assertTrue(str.contains("PostpaidTariff"));
        assertTrue(str.contains("Test"));
    }
}
