package ua.lviv.tariffs.io;

import ua.lviv.tariffs.model.*;
import ua.lviv.tariffs.repository.TariffRepository;

import java.io.*;
import java.util.Locale;

public class CsvFileManager {

    // Формат: type;name;monthlyFee;clients;minutes;dataGb;sms;network;extra1;extra2
    public void saveTariffs(TariffRepository repo, String filename) throws IOException {
        try (PrintWriter out = new PrintWriter(new FileWriter(filename))) {
            out.println("type;name;monthlyFee;clients;minutes;dataGb;sms;network;extra1;extra2");
            for (Tariff t : repo.all()) {
                out.println(toCsvLine(t));
            }
        }
    }

    private String toCsvLine(Tariff t) {
        String type;
        String extra1 = "";
        String extra2 = "";
        if (t instanceof PrepaidTariff p) {
            type = "PREPAID";
            extra1 = String.valueOf(p.getTopUpBonusPercent());
        } else if (t instanceof PostpaidTariff p) {
            type = "POSTPAID";
            extra1 = String.valueOf(p.getContractMonths());
        } else if (t instanceof UnlimitedTariff u) {
            type = "UNLIMITED";
            extra1 = String.valueOf(u.getFairUseGb());
            extra2 = String.valueOf(u.getThrottleMbps());
        } else {
            type = "UNKNOWN";
        }

        return String.join(";",
                type,
                t.getName(),
                String.format(Locale.US, "%.2f", t.getMonthlyFee()),
                String.valueOf(t.getClients()),
                String.valueOf(t.getMinutes()),
                String.format(Locale.US, "%.2f", t.getDataGb()),
                String.valueOf(t.getSms()),
                t.getNetwork().name(),
                extra1,
                extra2
        );
    }

    public void loadTariffs(TariffRepository repo, String filename) throws IOException {
        repo.clear();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line = br.readLine(); // header
            while ((line = br.readLine()) != null) {
                if (line.isBlank()) continue;
                Tariff t = parseLine(line);
                if (t != null) {
                    repo.add(t);
                }
            }
        }
    }

    private Tariff parseLine(String line) {
        try {
            String[] parts = line.split(";");
            String type = parts[0];
            String name = parts[1];
            double monthlyFee = Double.parseDouble(parts[2]);
            int clients = Integer.parseInt(parts[3]);
            int minutes = Integer.parseInt(parts[4]);
            double dataGb = Double.parseDouble(parts[5]);
            int sms = Integer.parseInt(parts[6]);
            NetworkType network = NetworkType.valueOf(parts[7]);
            String extra1 = parts.length > 8 ? parts[8] : "";
            String extra2 = parts.length > 9 ? parts[9] : "";

            return switch (type) {
                case "PREPAID" -> new PrepaidTariff(
                        name, monthlyFee, clients, minutes, dataGb, sms,
                        network, Integer.parseInt(extra1));
                case "POSTPAID" -> new PostpaidTariff(
                        name, monthlyFee, clients, minutes, dataGb, sms,
                        network, Integer.parseInt(extra1));
                case "UNLIMITED" -> new UnlimitedTariff(
                        name, monthlyFee, clients, minutes, dataGb, sms,
                        network, Double.parseDouble(extra1), Integer.parseInt(extra2));
                default -> null;
            };
        } catch (Exception e) {
            System.err.println("Cannot parse line: " + line);
            return null;
        }
    }

    public void exportToText(TariffRepository repo, String filename) throws IOException {
        try (PrintWriter out = new PrintWriter(new FileWriter(filename))) {
            for (Tariff t : repo.all()) {
                out.println(t);
            }
        }
    }
}
