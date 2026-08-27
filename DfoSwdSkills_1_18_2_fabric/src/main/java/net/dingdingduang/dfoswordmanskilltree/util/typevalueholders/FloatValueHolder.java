package net.dingdingduang.dfoswordmanskilltree.util.typevalueholders;

public class FloatValueHolder {
    private float FloatVal;

    public FloatValueHolder(float floatVal) {
        this.FloatVal = floatVal;
    }

    public float getFloatVal() { return this.FloatVal; }
    public void setFloatVal(float floatVal) { this.FloatVal = floatVal; }

//    @Override
//    public int hashCode() {
//        return this.floatVal;
//    }

    @Override
    public int hashCode() {
//        return Objects.hashCode(FloatVal);
        return Float.hashCode(this.FloatVal);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || this.getClass() != o.getClass()) return false;
        FloatValueHolder that = (FloatValueHolder) o;
        return this.FloatVal == that.FloatVal;
    }
}
