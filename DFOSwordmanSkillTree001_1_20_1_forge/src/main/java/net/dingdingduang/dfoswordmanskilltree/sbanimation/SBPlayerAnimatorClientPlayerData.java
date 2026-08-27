package net.dingdingduang.dfoswordmanskilltree.sbanimation;

import dev.kosmx.playerAnim.api.layered.KeyframeAnimationPlayer;
import dev.kosmx.playerAnim.api.layered.modifier.MirrorModifier;
import dev.kosmx.playerAnim.api.layered.modifier.SpeedModifier;

import net.dingdingduang.dfoswordmanskilltree.DfoSwordmanSkillTreeConstants;

public class SBPlayerAnimatorClientPlayerData {
    private MirrorModifier PlayerMirrorModifier;
    private SpeedModifier PlayerAnimationSpeedModifier;
    private int PlayerAnimationModifierHashCode = -1;
    private String PlayerLastAnimationName = DfoSwordmanSkillTreeConstants.NO_ANIMATION;
    private KeyframeAnimationPlayer PlayerLastAnimPlayer = null;

    public SBPlayerAnimatorClientPlayerData() {
        this.PlayerMirrorModifier = new MirrorModifier(false);
        this.PlayerAnimationSpeedModifier = new SpeedModifier(1.0f);
    }

    public SBPlayerAnimatorClientPlayerData(MirrorModifier mirrorModifier, SpeedModifier speedModifier, int playerAnimationModifierHashCode, String playerLastAnimationName, KeyframeAnimationPlayer keyframeAnimationPlayer) {
        this.PlayerMirrorModifier = mirrorModifier;
        this.PlayerAnimationSpeedModifier = speedModifier;
        this.PlayerAnimationModifierHashCode = playerAnimationModifierHashCode;
        this.PlayerLastAnimationName = playerLastAnimationName;
        this.PlayerLastAnimPlayer = keyframeAnimationPlayer;
    }

    public MirrorModifier getPlayerMirrorModifier() { return this.PlayerMirrorModifier; }
    public void setPlayerMirrorModifier(MirrorModifier playerMirrorModifier) { this.PlayerMirrorModifier = playerMirrorModifier; }
    public SpeedModifier getPlayerAnimationSpeedModifier() { return this.PlayerAnimationSpeedModifier; }
    public void setPlayerAnimationSpeedModifier(SpeedModifier playerAnimationSpeedModifier) { this.PlayerAnimationSpeedModifier = playerAnimationSpeedModifier; }
    public int getPlayerAnimationModifierHashCode() { return this.PlayerAnimationModifierHashCode; }
    public void setPlayerAnimationModifierHashCode(int playerAnimationModifierHashCode) { this.PlayerAnimationModifierHashCode = playerAnimationModifierHashCode; }
    public String getPlayerLastAnimationName() { return this.PlayerLastAnimationName; }
    public void setPlayerLastAnimationName(String playerLastAnimationName) { this.PlayerLastAnimationName = playerLastAnimationName; }
    public KeyframeAnimationPlayer getPlayerLastAnimPlayer() { return this.PlayerLastAnimPlayer; }
    public void setPlayerLastAnimPlayer(KeyframeAnimationPlayer playerLastAnimPlayer) { this.PlayerLastAnimPlayer = playerLastAnimPlayer; }
}
