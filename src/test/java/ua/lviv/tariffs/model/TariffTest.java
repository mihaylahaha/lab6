package ua.lviv.tariffs.model;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class TariffTest {

    private static class TestTariff extends Tariff {
        protected TestTariff(String name, double monthlyFee, int clients,
                             int minutes, double dataGb, int sms, NetworkType network) {
            super(name, monthlyFee, clients, minutes, dataGb, sms, network);
        }

        @Override
        public double effectiveFee() {
            return getMonthlyFee();
        }
    }

    @Test
    void constructorAndGetters_shouldInitializeAllFields() {
        Tariff tariff = new TestTariff(
                "Base", 100.0, 50, 200, 5.0, 30, NetworkType._4G
        );

        UUID id = tariff.getId();
        assertNotNull(id);
        assertEquals("Base", tariff.getName());
        assertEquals(100.0, tariff.getMonthlyFee());
        assertEquals(50, tariff.getClients());
        assertEquals(200, tariff.getMinutes());
        assertEquals(5.0, tariff.getDataGb());
        assertEquals(30, tariff.getSms());
        assertEquals(NetworkType._4G, tariff.getNetwork());
    }

    @Test
    void setters_shouldUpdateFields() {
        Tariff tariff = new TestTariff(
                "Base", 100.0, 50, 200, 5.0, 30, NetworkType._4G
        );

        tariff.setName("NewName");
        tariff.setMonthlyFee(150.0);
        tariff.setClients(70);
        tariff.setMinutes(300);
        tariff.setDataGb(7.5);
        tariff.setSms(40);
        tariff.setNetwork(NetworkType.GSM);

        assertEquals("NewName", tariff.getName());
        assertEquals(150.0, tariff.getMonthlyFee());
        assertEquals(70, tariff.getClients());
        assertEquals(300, tariff.getMinutes());
        assertEquals(7.5, tariff.getDataGb());
        assertEquals(40, tariff.getSms());
        assertEquals(NetworkType.GSM, tariff.getNetwork());
    }

    @Test
    void effectiveFee_shouldBeOverriddenInSubclass() {
        Tariff tariff = new TestTariff(
                "Base", 100.0, 10, 100, 5.0, 10, NetworkType.GSM
        );

        assertEquals(100.0, tariff.effectiveFee());
    }

    @Test
    void toString_shouldContainClassNameAndKeyFields() {
        Tariff tariff = new TestTariff(
                "Base", 100.0, 10, 100, 5.0, 10, NetworkType.GSM
        );

        String s = tariff.toString();

        assertNotNull(s);
        assertTrue(s.contains("Tariff"));
        assertTrue(s.contains("Base"));
        assertTrue(s.contains("monthlyFee=100.0"));
    }
}
