package ua.lviv.tariffs.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NetworkTypeTest {

    @Test
    void values_shouldContainAllNetworkTypes() {
        NetworkType[] values = NetworkType.values();
        assertEquals(4, values.length);
    }

    @Test
    void valueOf_shouldReturnCorrectEnum() {
        assertEquals(NetworkType._4G, NetworkType.valueOf("_4G"));
        assertEquals(NetworkType.GSM, NetworkType.valueOf("GSM"));
    }
}
