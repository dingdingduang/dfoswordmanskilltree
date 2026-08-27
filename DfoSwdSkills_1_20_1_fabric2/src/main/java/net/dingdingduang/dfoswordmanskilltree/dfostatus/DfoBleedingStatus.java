package net.dingdingduang.dfoswordmanskilltree.dfostatus;

public class DfoBleedingStatus extends DfoStatusGeneralType{
    private float TotalBleedingDamageAmount;
//    private int RemainingTotalTimeTicks;
    private float ConsumedBleedingDamageAmount;
    private int TotalStacks;

//    public DfoBleedingStatus(int type, float totalDmgAmount, int remainingTotalTimeTicks, int totalStacks) {
public DfoBleedingStatus(int type, float totalDmgAmount, int totalStacks) {
        super(type);
        this.TotalBleedingDamageAmount = totalDmgAmount;
//        this.RemainingTotalTimeTicks = remainingTotalTimeTicks;
        this.ConsumedBleedingDamageAmount = 0f;
        this.TotalStacks = totalStacks;
    }

    public void resetWhenExploded() {
        this.TotalBleedingDamageAmount = 0f;
        this.ConsumedBleedingDamageAmount = 0f;
        this.TotalStacks = 0;
    }

    public float getTotalBleedingDamageAmount() { return this.TotalBleedingDamageAmount; }
    public void setTotalBleedingDamageAmount(float totalBleedingDamageAmount) { this.TotalBleedingDamageAmount = totalBleedingDamageAmount; }

//    public int getRemainingTotalTimeTicks() { return this.RemainingTotalTimeTicks; }
//    public void setRemainingTotalTimeTicks(int remainingTotalTimeTicks) { this.RemainingTotalTimeTicks = remainingTotalTimeTicks; }

    public float getConsumedBleedingDamageAmount() { return this.ConsumedBleedingDamageAmount; }
    public void setConsumedBleedingDamageAmount(float consumedBleedingDamageAmount) { this.ConsumedBleedingDamageAmount = consumedBleedingDamageAmount; }

    public int getTotalStacks() { return this.TotalStacks; }
    public void setTotalStacks(int totalStacks) { this.TotalStacks = totalStacks; }
}
