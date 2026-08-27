package net.dingdingduang.dfoswordmanskilltree.util.keybindingctrl;

import net.minecraft.client.util.InputUtil;

public class KeyHelper {
    private String KeyName;
    private InputUtil.Type KeyType;
    private int KeyVal;

    public KeyHelper(String keyName, InputUtil.Type keyType, int keyVal) {
        this.KeyName = keyName;
        this.KeyType = keyType;
        this.KeyVal = keyVal;
    }

    public String getKeyName() { return this.KeyName; }
    public void setKeyName(String keyName) { this.KeyName = keyName; }
    public InputUtil.Type getKeyType() { return this.KeyType; }
    public void setKeyType(InputUtil.Type keyType) { this.KeyType = keyType; }
    public int getKeyVal() { return this.KeyVal; }
    public void setKeyVal(int keyVal) { this.KeyVal = keyVal; }
}
