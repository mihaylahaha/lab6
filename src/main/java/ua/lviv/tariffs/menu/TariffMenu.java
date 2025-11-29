package ua.lviv.tariffs.menu;

import ua.lviv.tariffs.analytics.TariffAnalytics;
import ua.lviv.tariffs.io.CsvFileManager;
import ua.lviv.tariffs.model.*;
import ua.lviv.tariffs.repository.TariffRepository;
import ua.lviv.tariffs.util.FeeComparator;

import java.io.IOException;
import java.util.*;

public class TariffMenu {

    private final TariffRepository repository;
    private final java.util.Scanner in;
    private final TariffAnalytics analytics = new TariffAnalytics();
    private final CsvFileManager fileManager = new CsvFileManager();

    public TariffMenu(TariffRepository repository, java.util.Scanner in) {
        this.repository = repository;
        this.in = in;
    }

    public void run() {
        while (true) {
            showMenu();
            System.out.print("Ваш вибір: ");
            String line = in.nextLine();

            int choice;
            try {
                choice = Integer.parseInt(line);
            } catch (NumberFormatException ex) {
                System.out.println("Введіть номер пункту меню.");
                continue;
            }

            if (choice == 0) {
                System.out.println("Програма завершена.");
                break;
            }

            if (choice == 1) {
                createTariff();
            } else if (choice == 2) {
                deleteTariff();
            } else if (choice == 3) {
                listTariffs();
            } else if (choice == 4) {
                sortByMonthlyFee();
            } else if (choice == 5) {
                searchByMinutesRange();
            } else if (choice == 6) {
                computeTotals();
            } else if (choice == 7) {
                loadFromCsv();
            } else if (choice == 8) {
                saveToCsv();
            } else if (choice == 9) {
                help();
            } else {
                System.out.println("Невірний пункт меню.");
            }
        }
    }

    private void showMenu() {
        System.out.println("\n==== МЕНЮ ТАРИФІВ ====");
        System.out.println("1. Створити тариф");
        System.out.println("2. Видалити тариф за ID");
        System.out.println("3. Список усіх тарифів");
        System.out.println("4. Сортувати за місячною платою");
        System.out.println("5. Пошук за діапазоном хвилин");
        System.out.println("6. Підрахувати кількість клієнтів і середню абонплату");
        System.out.println("7. Завантажити з CSV");
        System.out.println("8. Зберегти в CSV");
        System.out.println("9. Довідка");
        System.out.println("0. Вихід");
    }

    private void createTariff() {
        System.out.println("\nВиберіть тип тарифу:");
        System.out.println("1. Prepaid");
        System.out.println("2. Postpaid");
        System.out.println("3. Unlimited");
        int type = readInt("Тип: ");

        String name = readString("Назва: ");
        double monthlyFee = readDouble("Місячна плата: ");
        int clients = readInt("Кількість клієнтів: ");
        int minutes = readInt("Хвилин: ");
        double dataGb = readDouble("Інтернет, ГБ: ");
        int sms = readInt("SMS: ");

        NetworkType network = chooseNetwork();

        Tariff tariff;
        switch (type) {
            case 1 -> {
                int bonus = readInt("Бонус при поповненні (%): ");
                tariff = new PrepaidTariff(name, monthlyFee, clients, minutes, dataGb, sms,
                        network, bonus);
            }
            case 2 -> {
                int months = readInt("Тривалість контракту (місяці): ");
                tariff = new PostpaidTariff(name, monthlyFee, clients, minutes, dataGb, sms,
                        network, months);
            }
            case 3 -> {
                double fairUse = readDouble("Fair use, ГБ: ");
                int throttle = readInt("Швидкість після обмеження, Мбіт/с: ");
                tariff = new UnlimitedTariff(name, monthlyFee, clients, minutes, dataGb, sms,
                        network, fairUse, throttle);
            }
            default -> {
                System.out.println("Невірний тип.");
                return;
            }
        }

        repository.add(tariff);
        System.out.println("Тариф створено, ID: " + tariff.getId());
    }

    private NetworkType chooseNetwork() {
        System.out.println("Тип мережі:");
        NetworkType[] values = NetworkType.values();
        for (int i = 0; i < values.length; i++) {
            System.out.printf("%d. %s%n", i + 1, values[i]);
        }
        int idx = readInt("Вибір: ") - 1;
        if (idx < 0 || idx >= values.length) {
            System.out.println("Невірний вибір, використано GSM.");
            return NetworkType.GSM;
        }
        return values[idx];
    }

    private void deleteTariff() {
        if (repository.isEmpty()) {
            System.out.println("Список тарифів порожній.");
            return;
        }
        String idStr = readString("Введіть ID тарифу для видалення: ");
        try {
            java.util.UUID id = java.util.UUID.fromString(idStr);
            if (repository.findById(id) == null) {
                System.out.println("Тариф з таким ID не знайдено.");
            } else {
                repository.remove(id);
                System.out.println("Тариф видалено.");
            }
        } catch (IllegalArgumentException ex) {
            System.out.println("Невірний формат UUID.");
        }
    }

    private void listTariffs() {
        if (repository.isEmpty()) {
            System.out.println("Список порожній.");
            return;
        }
        System.out.println("\n=== Усі тарифи ===");
        repository.all().forEach(System.out::println);
    }

    private void sortByMonthlyFee() {
        java.util.List<Tariff> sorted = new java.util.ArrayList<>(repository.all());
        sorted.sort(new FeeComparator());
        System.out.println("\n=== Тарифи, відсортовані за абонплатою ===");
        sorted.forEach(System.out::println);
    }

    private void searchByMinutesRange() {
        if (repository.isEmpty()) {
            System.out.println("Список порожній.");
            return;
        }
        int min = readInt("Мінімум хвилин: ");
        int max = readInt("Максимум хвилин: ");

        System.out.printf("Тарифи з хвилинами [%d; %d]:%n", min, max);
        repository.all().stream()
                .filter(t -> t.getMinutes() >= min && t.getMinutes() <= max)
                .forEach(System.out::println);
    }

    private void computeTotals() {
        int totalClients = analytics.totalClients(repository);
        double avgFee = analytics.averageMonthlyFee(repository);
        System.out.println("Загальна кількість клієнтів: " + totalClients);
        System.out.printf(java.util.Locale.US, "Середня абонплата: %.2f%n", avgFee);
    }

    private void loadFromCsv() {
        String file = readString("Ім'я CSV файлу для завантаження: ");
        try {
            fileManager.loadTariffs(repository, file);
            System.out.println("Тарифи завантажено з файлу.");
        } catch (IOException e) {
            System.out.println("Помилка читання файлу: " + e.getMessage());
        }
    }

    private void saveToCsv() {
        String file = readString("Ім'я CSV файлу для збереження: ");
        try {
            fileManager.saveTariffs(repository, file);
            System.out.println("Тарифи збережено у файл.");
        } catch (IOException e) {
            System.out.println("Помилка запису у файл: " + e.getMessage());
        }
    }

    private void help() {
        System.out.println("\nДовідка:");
        System.out.println("Програма моделює тарифні плани мобільного оператора.");
        System.out.println("Використовується ООП: наслідування, поліморфізм, інкапсуляція.");
        System.out.println("Дані можна зберігати/завантажувати у форматі CSV.");
    }

    private int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            String s = in.nextLine();
            try {
                return Integer.parseInt(s);
            } catch (NumberFormatException ex) {
                System.out.println("Введіть ціле число.");
            }
        }
    }

    private double readDouble(String prompt) {
        while (true) {
            System.out.print(prompt);
            String s = in.nextLine();
            try {
                return Double.parseDouble(s.replace(',', '.'));
            } catch (NumberFormatException ex) {
                System.out.println("Введіть число.");
            }
        }
    }

    private String readString(String prompt) {
        System.out.print(prompt);
        return in.nextLine();
    }
}
