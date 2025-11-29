package ua.lviv.tariffs.model;

public class PostpaidTariff extends Tariff {

    private int contractMonths;

    public PostpaidTariff(String name, double monthlyFee, int clients,
                          int minutes, double dataGb, int sms,
                          NetworkType network, int contractMonths) {
        super(name, monthlyFee, clients, minutes, dataGb, sms, network);
        this.contractMonths = contractMonths;
    }

    public int getContractMonths() {
        return contractMonths;
    }

    public void setContractMonths(int contractMonths) {
        this.contractMonths = contractMonths;
    }

    @Override
    public double effectiveFee() {
        // умовно розподіляємо абонплату на строк контракту
        if (contractMonths <= 0) {
            return getMonthlyFee();
        }
        return getMonthlyFee() * 12 / contractMonths;
    }

    @Override
    public String toString() {
        return "PostpaidTariff{" +
                "contractMonths=" + contractMonths +
                ", base=" + super.toString() +
                '}';
    }
}
