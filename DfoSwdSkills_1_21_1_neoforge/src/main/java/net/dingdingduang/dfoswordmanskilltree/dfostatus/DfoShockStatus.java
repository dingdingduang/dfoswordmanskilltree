package net.dingdingduang.dfoswordmanskilltree.dfostatus;

public class DfoShockStatus extends DfoStatusGeneralType{
    //    private int RemainingTicks;
    private double Amount;
    private int InflictedTimes;
    private int MaxInflictedTimes;

    public DfoShockStatus(int ele_type, double amount, int maxInflictedTimes) {
        super(ele_type);
        this.Amount = amount;
        this.InflictedTimes = 0;
        this.MaxInflictedTimes = maxInflictedTimes;
//        this.RemainingTicks = remainingTicks;
    }

    //    public int getRemainingTicks() { return this.RemainingTicks; }
//    public void setRemainingTicks(int remainingTicks) { this.RemainingTicks = remainingTicks; }
    public double getAmount() { return this.Amount; }
    public void setAmount(double amount) { this.Amount = amount; }

    public int getInflictedTimes() { return this.InflictedTimes; }
    public void setInflictedTimes(int inflictedTimes) { this.InflictedTimes = inflictedTimes; }
    public int getMaxInflictedTimes() { return this.MaxInflictedTimes; }
    public void setMaxInflictedTimes(int maxInflictedTimes) { this.MaxInflictedTimes = maxInflictedTimes; }
}
