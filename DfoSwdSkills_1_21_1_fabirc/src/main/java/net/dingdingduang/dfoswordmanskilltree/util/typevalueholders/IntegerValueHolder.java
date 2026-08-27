package net.dingdingduang.dfoswordmanskilltree.util.typevalueholders;

public class IntegerValueHolder {
    private int IntCounter;

    public IntegerValueHolder(int counter) {
        this.IntCounter = counter;
    }

    public int getIntCounter() { return this.IntCounter; }
    public void setIntCounter(int intCounter) { this.IntCounter = intCounter; }

//    @Override
//    public int hashCode() {
//        return this.IntCounter;
//    }

    @Override
    public int hashCode() {
//        return Objects.hashCode(IntCounter);
        return this.IntCounter;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || this.getClass() != o.getClass()) return false;
        IntegerValueHolder that = (IntegerValueHolder) o;
        return this.IntCounter == that.IntCounter;
    }
}
