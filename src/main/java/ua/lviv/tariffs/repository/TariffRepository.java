package ua.lviv.tariffs.repository;

import ua.lviv.tariffs.model.Tariff;

import java.util.*;

public class TariffRepository {

    private final Map<java.util.UUID, Tariff> store = new LinkedHashMap<>();

    public void add(Tariff tariff) {
        store.put(tariff.getId(), tariff);
    }

    public void remove(java.util.UUID id) {
        store.remove(id);
    }

    public java.util.Collection<Tariff> all() {
        return store.values();
    }

    public Tariff findById(java.util.UUID id) {
        return store.get(id);
    }

    public void clear() {
        store.clear();
    }

    public boolean isEmpty() {
        return store.isEmpty();
    }
}
