package ua.lviv.tariffs.io;

import org.junit.jupiter.api.Test;
import ua.lviv.tariffs.model.*;
import ua.lviv.tariffs.repository.TariffRepository;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import static org.junit.jupiter.api.Assertions.*;

class CsvFileManagerTest {

    @Test
    void saveAndLoadTariffs_shouldPersistAndRestoreData() throws IOException {
        TariffRepository repo = new TariffRepository();
        repo.add(new PrepaidTariff("Pre", 100.0, 10, 100, 5.0, 10, NetworkType.GSM, 10));
        repo.add(new PostpaidTariff("Post", 150.0, 20, 200, 10.0, 20, NetworkType._4G, 24));
        repo.add(new UnlimitedTariff("Unlim", 200.0, 30, 300, 20.0, 30, NetworkType._5G, 100.0, 5));

        CsvFileManager manager = new CsvFileManager();

        File tempFile = Files.createTempFile("tariffs", ".csv").toFile();
        tempFile.deleteOnExit();

        manager.saveTariffs(repo, tempFile.getAbsolutePath());

        TariffRepository loaded = new TariffRepository();
        manager.loadTariffs(loaded, tempFile.getAbsolutePath());

        assertEquals(3, loaded.all().size());
    }

    @Test
    void exportToText_shouldCreateReadableFile() throws IOException {
        TariffRepository repo = new TariffRepository();
        repo.add(new PrepaidTariff("Pre", 100.0, 10, 100, 5.0, 10, NetworkType.GSM, 10));

        CsvFileManager manager = new CsvFileManager();
        File tempFile = Files.createTempFile("tariffs_txt", ".txt").toFile();
        tempFile.deleteOnExit();

        manager.exportToText(repo, tempFile.getAbsolutePath());

        String content = Files.readString(tempFile.toPath());
        assertTrue(content.contains("Pre"));
    }
}
