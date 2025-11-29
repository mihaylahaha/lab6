package ua.lviv.tariffs.model;

public class UnlimitedTariff extends Tariff {

    private double fairUseGb;
    private int throttleMbps;

    public UnlimitedTariff(String name, double monthlyFee, int clients,
                           int minutes, double dataGb, int sms,
                           NetworkType network, double fairUseGb, int throttleMbps) {
        super(name, monthlyFee, clients, minutes, dataGb, sms, network);
        this.fairUseGb = fairUseGb;
        this.throttleMbps = throttleMbps;
    }

    public double getFairUseGb() {
        return fairUseGb;
    }

    public void setFairUseGb(double fairUseGb) {
        this.fairUseGb = fairUseGb;
    }

    public int getThrottleMbps() {
        return throttleMbps;
    }

    public void setThrottleMbps(int throttleMbps) {
        this.throttleMbps = throttleMbps;
    }

    @Override
    public double effectiveFee() {
        // умовно: вартість за 1 GB у зоні "fair use"
        if (fairUseGb <= 0) {
            return getMonthlyFee();
        }
        return getMonthlyFee() / fairUseGb;
    }

    @Override
    public String toString() {
        return "UnlimitedTariff{" +
                "fairUseGb=" + fairUseGb +
                ", throttleMbps=" + throttleMbps +
                ", base=" + super.toString() +
                '}';
    }
}
