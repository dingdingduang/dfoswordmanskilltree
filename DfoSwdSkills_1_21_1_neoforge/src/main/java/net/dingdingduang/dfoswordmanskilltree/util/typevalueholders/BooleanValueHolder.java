package net.dingdingduang.dfoswordmanskilltree.util.typevalueholders;

public class BooleanValueHolder {
    private boolean BooleanVal;

    public BooleanValueHolder(boolean booleanVal) {
        this.BooleanVal = booleanVal;
    }

    public boolean getBooleanVal() { return this.BooleanVal; }
    public void setBooleanVal(boolean booleanVal) { this.BooleanVal = booleanVal; }

//    @Override
//    public int hashCode() {
//        return this.BooleanVal;
//    }

    @Override
    public int hashCode() {
//        return Objects.hashCode(this.BooleanVal);
        return Boolean.hashCode(this.BooleanVal);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || this.getClass() != o.getClass()) return false;
        BooleanValueHolder that = (BooleanValueHolder) o;
        return this.BooleanVal == that.BooleanVal;
    }
}
