package ua.lviv.tariffs.model;

public class PrepaidTariff extends Tariff {

    private int topUpBonusPercent;

    public PrepaidTariff(String name, double monthlyFee, int clients,
                         int minutes, double dataGb, int sms,
                         NetworkType network, int topUpBonusPercent) {
        super(name, monthlyFee, clients, minutes, dataGb, sms, network);
        this.topUpBonusPercent = topUpBonusPercent;
    }

    public int getTopUpBonusPercent() {
        return topUpBonusPercent;
    }

    public void setTopUpBonusPercent(int topUpBonusPercent) {
        this.topUpBonusPercent = topUpBonusPercent;
    }

    @Override
    public double effectiveFee() {
        // умовно: чим більший бонус – тим менша ефективна плата
        return getMonthlyFee() * (100.0 - topUpBonusPercent) / 100.0;
    }

    @Override
    public String toString() {
        return "PrepaidTariff{" +
                "bonus=" + topUpBonusPercent +
                "%, base=" + super.toString() +
                '}';
    }
}
