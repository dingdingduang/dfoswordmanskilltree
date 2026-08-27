package net.dingdingduang.dfoswordmanskilltree.util.typevalueholders;

public class TypeValueHolder<AnyType> {
    private AnyType AnyTypeValue;
//    private float FloatCounter;
//    private boolean IsDone;

    public TypeValueHolder(AnyType anyTypeValue) {
        this.AnyTypeValue = anyTypeValue;
    }

    public AnyType getAnyTypeValue() { return this.AnyTypeValue; }
    public void setAnyTypeValue(AnyType anyTypeValue) { this.AnyTypeValue = anyTypeValue; }

    @Override
    public int hashCode() {
        return this.AnyTypeValue.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || this.getClass() != obj.getClass()) return false;
        return this.AnyTypeValue.equals(obj);
    }
}
