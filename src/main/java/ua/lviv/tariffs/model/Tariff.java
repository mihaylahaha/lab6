package ua.lviv.tariffs.model;

import java.util.UUID;

public abstract class Tariff {

    private final UUID id;
    private String name;
    private double monthlyFee;
    private int clients;
    private int minutes;
    private double dataGb;
    private int sms;
    private NetworkType network;

    protected Tariff(String name,
                     double monthlyFee,
                     int clients,
                     int minutes,
                     double dataGb,
                     int sms,
                     NetworkType network) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.monthlyFee = monthlyFee;
        this.clients = clients;
        this.minutes = minutes;
        this.dataGb = dataGb;
        this.sms = sms;
        this.network = network;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getMonthlyFee() {
        return monthlyFee;
    }

    public int getClients() {
        return clients;
    }

    public int getMinutes() {
        return minutes;
    }

    public double getDataGb() {
        return dataGb;
    }

    public int getSms() {
        return sms;
    }

    public NetworkType getNetwork() {
        return network;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMonthlyFee(double monthlyFee) {
        this.monthlyFee = monthlyFee;
    }

    public void setClients(int clients) {
        this.clients = clients;
    }

    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }

    public void setDataGb(double dataGb) {
        this.dataGb = dataGb;
    }

    public void setSms(int sms) {
        this.sms = sms;
    }

    public void setNetwork(NetworkType network) {
        this.network = network;
    }

    /** Розрахунок «ефективної» вартості (кожен тариф може рахувати по-своєму). */
    public abstract double effectiveFee();

    @Override
    public String toString() {
        return "Tariff{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", monthlyFee=" + monthlyFee +
                ", clients=" + clients +
                ", minutes=" + minutes +
                ", dataGb=" + dataGb +
                ", sms=" + sms +
                ", network=" + network +
                '}';
    }
}
