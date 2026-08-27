package net.dingdingduang.dfoswordmanskilltree.util;

public class BlockVec3 {
    public int x;
    public int y;
    public int z;

    public BlockVec3(int X, int Y, int Z) {
        this.x = X;
        this.y = Y;
        this.z = Z;
    }

    public BlockVec3 add(int X, int Y, int Z) {
        return new BlockVec3(this.x + X, this.y + Y, this.z + Z);
    }

    public int getX() {
        return this.x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return this.y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getZ() {
        return this.z;
    }

    public void setZ(int z) {
        this.z = z;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        BlockVec3 blockVec3 = (BlockVec3) o;
        return x == blockVec3.x && y == blockVec3.y && z == blockVec3.z;
    }

    @Override
    public int hashCode() {
//        return Objects.hash(x, y, z);
        return (this.getY() + this.getZ() * 31) * 31 + this.getX();
    }

    @Override
    public String toString() {
        return "BlockVec3{" +
                "x=" + x +
                ", y=" + y +
                ", z=" + z +
                '}';
    }
}
