package ua.lviv.tariffs.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ua.lviv.tariffs.model.NetworkType;
import ua.lviv.tariffs.model.PrepaidTariff;
import ua.lviv.tariffs.model.Tariff;

import java.util.Collection;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class TariffRepositoryTest {

    private TariffRepository repository;
    private Tariff tariff;

    @BeforeEach
    void setUp() {
        repository = new TariffRepository();
        tariff = new PrepaidTariff("Starter", 100.0, 10, 100, 5.0, 10, NetworkType._4G, 20);
    }

    @Test
    void add_shouldStoreTariff() {
        repository.add(tariff);

        Collection<Tariff> all = repository.all();
        assertEquals(1, all.size());
        assertTrue(all.contains(tariff));
    }

    @Test
    void remove_shouldDeleteTariffById() {
        repository.add(tariff);
        UUID id = tariff.getId();

        repository.remove(id);

        assertNull(repository.findById(id));
        assertTrue(repository.all().isEmpty());
    }

    @Test
    void findById_shouldReturnCorrectTariff() {
        repository.add(tariff);
        UUID id = tariff.getId();

        Tariff found = repository.findById(id);

        assertNotNull(found);
        assertEquals(tariff, found);
    }

    @Test
    void clear_shouldRemoveAllTariffs() {
        repository.add(tariff);
        repository.add(new PrepaidTariff("Two", 150.0, 20, 200, 10.0, 20, NetworkType._5G, 15));

        repository.clear();

        assertTrue(repository.all().isEmpty());
        assertTrue(repository.isEmpty());
    }

    @Test
    void isEmpty_shouldReturnTrueWhenNoTariffs() {
        assertTrue(repository.isEmpty());
    }

    @Test
    void isEmpty_shouldReturnFalseWhenTariffsExist() {
        repository.add(tariff);
        assertFalse(repository.isEmpty());
    }
}
