package net.dingdingduang.dfoswordmanskilltree.util.keybindingctrl;

import com.mojang.blaze3d.platform.InputConstants;

public class KeyHelper {
    private String KeyName;
    private InputConstants.Type KeyType;
    private int KeyVal;

    public KeyHelper(String keyName, InputConstants.Type keyType, int keyVal) {
        this.KeyName = keyName;
        this.KeyType = keyType;
        this.KeyVal = keyVal;
    }

    public String getKeyName() { return this.KeyName; }
    public void setKeyName(String keyName) { this.KeyName = keyName; }
    public InputConstants.Type getKeyType() { return this.KeyType; }
    public void setKeyType(InputConstants.Type keyType) { this.KeyType = keyType; }
    public int getKeyVal() { return this.KeyVal; }
    public void setKeyVal(int keyVal) { this.KeyVal = keyVal; }
}
