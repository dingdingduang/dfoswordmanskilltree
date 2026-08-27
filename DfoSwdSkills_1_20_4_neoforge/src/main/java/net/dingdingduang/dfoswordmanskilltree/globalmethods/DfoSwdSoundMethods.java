package net.dingdingduang.dfoswordmanskilltree.globalmethods;

import net.dingdingduang.dfoswordmanskilltree.dfoswdregistry.SoundRegistry;
import net.dingdingduang.dfoswordmanskilltree.util.ExtraMathMethods;

import net.dingdingduang.dfoswordmanskilltree.util.MethodSoundAction;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.level.Level;

import org.joml.Vector3f;

import static net.dingdingduang.somebasicskills.globalmethods.SoundMethods.PlayClientSound;
import static net.dingdingduang.somebasicskills.globalmethods.SoundMethods.PlaySoundAtLocation;

public class DfoSwdSoundMethods {
    //skill sounds
    public static final SoundEvent[] BLADEMASTER_0_05_TRIPLE_SLASH = {SoundRegistry.TSLASH_01.value(), SoundRegistry.TSLASH_02.value(), SoundRegistry.TSLASH_03.value()};



    //=====================
    //hit sound
    public static final SoundEvent[] BOODONG_HIT_SOUND = {SoundRegistry.BOODONG_HIT_01.value(), SoundRegistry.BOODONG_HIT_02.value()};
    //weapon sounds
    public static final SoundEvent[] WEAPON_TOUCH_SOUND = {SoundRegistry.BEAMSWD_TOUCH.value(), SoundRegistry.KAT_TOUCH.value(), SoundRegistry.MINSWD_TOUCH.value(), SoundRegistry.SLESSSWD_TOUCH.value(), SoundRegistry.STICK_TOUCH.value(), SoundRegistry.SQRSWD_TOUCH.value()};
    //lightsabre
    public static final SoundEvent[] WEAPON_LIGHTSABRE_SHOCK_EFF_SOUNDS = {SoundRegistry.BEAMSWDB.value(), SoundRegistry.BEAMSWDC_01.value()};
    public static final SoundEvent[] WEAPON_LIGHTSABRE_WIELDING_SOUNDS = {SoundRegistry.BEAMSWDA_01.value(), SoundRegistry.BEAMSWDA_02.value(), SoundRegistry.BEAMSWDA_03.value()};
    public static final SoundEvent[] WEAPON_LIGHTSABRE_HIT_SOUNDS = {SoundRegistry.BEAMSWDA_HIT_01.value(), SoundRegistry.BEAMSWDA_HIT_02.value()};
    public static final SoundEvent WEAPON_LIGHTSABRE_STAB_HIT_SOUND = SoundRegistry.BEAMSWDB_HIT.value();
    public static final SoundEvent[] WEAPON_LIGHTSABRE_LIGHTNING_SOUNDS = {SoundRegistry.BEAMSWDC_HIT_01.value(), SoundRegistry.BEAMSWDC_HIT_02.value()};

    //katana
    public static final SoundEvent[] WEAPON_KATANA_WIELDING_SOUNDS = {SoundRegistry.KATA_01.value(), SoundRegistry.KATA_02.value(), SoundRegistry.KATA_03.value(), SoundRegistry.KATC_01.value(), SoundRegistry.KATC_02.value()};
    public static final SoundEvent[] WEAPON_KATANA_HIT_SOUNDS = {SoundRegistry.KATA_HIT_01.value(), SoundRegistry.KATA_HIT_02.value(), SoundRegistry.KATC_01.value(), SoundRegistry.KATB_HIT.value()};
    public static final SoundEvent WEAPON_KATANA_STAB_HIT_SOUND = SoundRegistry.KATB_HIT.value();

    //unknown sword
    public static final SoundEvent[] WEAPON_EFF_SWORD_WIELDING_SOUNDS = {SoundRegistry.MINSWDA_01.value(), SoundRegistry.MINSWDA_02.value(), SoundRegistry.MINSWDA_03.value(), SoundRegistry.MINSWDB.value(), SoundRegistry.MINSWDC_01.value(), SoundRegistry.MINSWDC_02.value(), SoundRegistry.MINSWDC_03.value()};
    public static final SoundEvent[] WEAPON_EFF_SWORD_HIT_SOUNDS = {SoundRegistry.MINSWDA_HIT_01.value(), SoundRegistry.MINSWDA_HIT_02.value(), SoundRegistry.MINSWDA_HIT_03.value()};
    public static final SoundEvent WEAPON_EFF_SWORD_STAB_HIT_SOUND = SoundRegistry.MINSWDB_HIT.value();

    //short sword
    public static final SoundEvent[] WEAPON_SHORT_SWORD_WIELDING_SOUNDS = {SoundRegistry.SLESSSWD_01.value(), SoundRegistry.SLESSSWD_02.value()};
    public static final SoundEvent[] WEAPON_SHORT_SWORD_HIT_SOUNDS = {SoundRegistry.SLESSSWD_HIT_01.value(), SoundRegistry.SLESSSWD_HIT_02.value(), SoundRegistry.MINSWDA_HIT_03.value()};
    public static final SoundEvent WEAPON_SHORT_SWORD_STAB_HIT_SOUND = SoundRegistry.MINSWDB_HIT.value();

    //BLUDGEON
    public static final SoundEvent[] WEAPON_BLUDGEON_WIELDING_SOUNDS = {SoundRegistry.STICKA_01.value(), SoundRegistry.STICKA_02.value(), SoundRegistry.STICKB_01.value(), SoundRegistry.STICKC_01.value()};
    public static final SoundEvent[] WEAPON_BLUDGEON_HIT_SOUNDS = {SoundRegistry.STICKA_HIT_01.value(), SoundRegistry.STICKA_HIT_02.value(), SoundRegistry.STICKC_HIT_01.value(), SoundRegistry.STICKC_HIT_02.value()};
    public static final SoundEvent WEAPON_BLUDGEON_STAB_HIT_SOUND = SoundRegistry.STICKB_HIT_01.value();

    //zanbato
    public static final SoundEvent[] WEAPON_ZANBATO_WIELDING_SOUNDS = {SoundRegistry.SQRSWDA_01.value(), SoundRegistry.SQRSWDA_02.value(), SoundRegistry.SQRSWDB.value()};
    public static final SoundEvent[] WEAPON_ZANBATO_HIT_SOUNDS = {SoundRegistry.SQRSWDA_HIT_01.value(), SoundRegistry.SQRSWDA_HIT_02.value(), SoundRegistry.SQRSWDC_HIT_01.value(), SoundRegistry.SQRSWDC_HIT_02.value()};
    public static final SoundEvent WEAPON_ZANBATO_STAB_HIT_SOUND = SoundRegistry.SQRSWDB_HIT.value();

    //skill
    public static final SoundEvent[] BLADEMASTER_DRAGON_SLASH = {SoundRegistry.SM_MENGRYONG_01.value(), SoundRegistry.SM_MENGRYONG_02.value(), SoundRegistry.SM_MENGRYONG_03.value(), SoundRegistry.SM_MENGRYONG_START.value(), SoundRegistry.SM_MENGRYONG_FIN.value()};
    public static final SoundEvent[] BLADEMASTER_TEMPEST_SLASH = {SoundRegistry.W_ATK_01.value(), SoundRegistry.W_ATK_02.value(), SoundRegistry.W_SPIRITS_FLASH.value(), SoundRegistry.W_WEAPON_MOVING.value(), SoundRegistry.W_WEAPON_DRIVE.value(), SoundRegistry.W_ATK_FIN.value()};

    //==========================
    private static final MethodSoundAction[] WeaponTypeWieldingSoundArr = {DfoSwdSoundMethods::PlayZanbatoWieldingSound,
            DfoSwdSoundMethods::PlayBludgeonWieldingSound, DfoSwdSoundMethods::PlayShortSwordWieldingSound,
            DfoSwdSoundMethods::PlayKatanaWieldingSound, DfoSwdSoundMethods::PlayLightSabreWieldingSound};
    private static final MethodSoundAction[] WeaponTypeStabHitSoundArr = {DfoSwdSoundMethods::PlayZanbatoStabHitSound,
            DfoSwdSoundMethods::PlayBludgeonStabHitSound, DfoSwdSoundMethods::PlayShortSwordStabHitSound,
            DfoSwdSoundMethods::PlayKatanaStabHitSound, DfoSwdSoundMethods::PlayLightSabreStabHitSound};
    private static final MethodSoundAction[] WeaponTypeHitEntitySoundArr = {DfoSwdSoundMethods::PlayZanbatoHitSound,
            DfoSwdSoundMethods::PlayBludgeonHitSound, DfoSwdSoundMethods::PlayShortSwordHitSound,
            DfoSwdSoundMethods::PlayKatanaHitSound, DfoSwdSoundMethods::PlayLightSabreHitSound};



    //===============================================
    //skill sounds
    //===============================================
    public static void PlayBlademaster005TripleSlashSound(Level worldLevel, double x1, double y1, double z1, float volume, float pitch) {
        PlaySoundAtLocation(BLADEMASTER_0_05_TRIPLE_SLASH[ExtraMathMethods.randomInt(3)], worldLevel, x1, y1, z1, volume, pitch);
    }



    //===============================================
    //weapon sounds
    //===============================================
    public static void PlayRandomSwordHitSound(Level worldLevel, double x1, double y1, double z1, float volume, float pitch) {
        SoundEvent[] tempArr = {SoundRegistry.SQRSWDA_HIT_01.value(), SoundRegistry.SQRSWDC_HIT_01.value(), SoundRegistry.SQRSWDC_HIT_02.value()};
        PlaySoundAtLocation(tempArr[ExtraMathMethods.randomInt(tempArr.length)], worldLevel, x1, y1, z1, volume, pitch);
    }

    public static void PlayRandomSwordWieldingSound(Level worldLevel, double x1, double y1, double z1, float volume, float pitch) {
        SoundEvent[] tempArr = {SoundRegistry.SLESSSWD_01.value(), SoundRegistry.SLESSSWD_02.value(), SoundRegistry.SQRSWDB.value(),
        SoundRegistry.SQRSWDC_01.value(), SoundRegistry.STICKA_01.value(), SoundRegistry.STICKA_02.value(), SoundRegistry.STICKB_01.value(), SoundRegistry.STICKC_01.value()};
        PlaySoundAtLocation(tempArr[ExtraMathMethods.randomInt(tempArr.length)], worldLevel, x1, y1, z1, volume, pitch);
    }

    public static void PlayRandomSwordUpperSlashSound(Level worldLevel, double x1, double y1, double z1, float volume, float pitch) {
        SoundEvent[] tempArr = {SoundRegistry.UPPER_SLASH_01.value(), SoundRegistry.UPPER_SLASH_02.value()};
        PlaySoundAtLocation(tempArr[ExtraMathMethods.randomInt(tempArr.length)], worldLevel, x1, y1, z1, volume, pitch);
    }

    public static void PlayGroundQuakeSound(Vector3f targetLocBlockCenter, float volume, float pitch) {
        PlayClientSound(targetLocBlockCenter, SoundRegistry.GROUND_STOMP.value(), volume, pitch);
    }

    public static void PlaySwordGuardStartSound(Level worldlevel, float x1, float y1, float z1, float volume, float pitch) {
        PlaySoundAtLocation(SoundRegistry.SWD_EFF_05.value(), worldlevel, x1, y1, z1, volume, pitch);
    }

    public static void PlaySwordGuardingSound(Level worldlevel, float x1, float y1, float z1, float volume, float pitch) {
        PlaySoundAtLocation(SoundRegistry.SWD_EFF_01.value(), worldlevel, x1, y1, z1, volume, pitch);
    }

    public static void PlaySwordReflectionSound(Level worldlevel, float x1, float y1, float z1, float volume, float pitch) {
        PlaySoundAtLocation(SoundRegistry.GUARD_ATK.value(), worldlevel, x1, y1, z1, volume, pitch);
    }

    public static void PlaySwordGuardEndSound(Level worldlevel, float x1, float y1, float z1, float volume, float pitch) {
        PlaySoundAtLocation(SoundRegistry.SWD_EFF_06.value(), worldlevel, x1, y1, z1, volume, pitch);
    }



    //-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-
    //lightsabre
    public static void PlayLightSabreWieldingSound(Level worldLevel, double x1, double y1, double z1, float volume, float pitch) {
        PlaySoundAtLocation(WEAPON_LIGHTSABRE_WIELDING_SOUNDS[ExtraMathMethods.randomInt(WEAPON_LIGHTSABRE_WIELDING_SOUNDS.length)], worldLevel, x1, y1, z1, volume, pitch);
    }
    public static void PlayLightSabreStabHitSound(Level worldLevel, double x1, double y1, double z1, float volume, float pitch) {
        PlaySoundAtLocation(WEAPON_LIGHTSABRE_STAB_HIT_SOUND, worldLevel, x1, y1, z1, volume, pitch);
    }
    public static void PlayLightSabreHitSound(Level worldLevel, double x1, double y1, double z1, float volume, float pitch) {
        PlaySoundAtLocation(WEAPON_LIGHTSABRE_HIT_SOUNDS[ExtraMathMethods.randomInt(WEAPON_LIGHTSABRE_HIT_SOUNDS.length)], worldLevel, x1, y1, z1, volume, pitch);
    }

    //katana
    public static void PlayKatanaWieldingSound(Level worldLevel, double x1, double y1, double z1, float volume, float pitch) {
        PlaySoundAtLocation(WEAPON_KATANA_WIELDING_SOUNDS[ExtraMathMethods.randomInt(WEAPON_KATANA_WIELDING_SOUNDS.length)], worldLevel, x1, y1, z1, volume, pitch);
    }
    public static void PlayKatanaHitSound(Level worldLevel, double x1, double y1, double z1, float volume, float pitch) {
        PlaySoundAtLocation(WEAPON_KATANA_HIT_SOUNDS[ExtraMathMethods.randomInt(WEAPON_KATANA_HIT_SOUNDS.length)], worldLevel, x1, y1, z1, volume, pitch);
    }
    public static void PlayKatanaStabHitSound(Level worldLevel, double x1, double y1, double z1, float volume, float pitch) {
        PlaySoundAtLocation(WEAPON_KATANA_STAB_HIT_SOUND, worldLevel, x1, y1, z1, volume, pitch);
    }

    //unknown wave sword
    public static void PlayWaveSwordWieldingSound(Level worldLevel, double x1, double y1, double z1, float volume, float pitch) {
        PlaySoundAtLocation(WEAPON_EFF_SWORD_WIELDING_SOUNDS[ExtraMathMethods.randomInt(WEAPON_EFF_SWORD_WIELDING_SOUNDS.length)], worldLevel, x1, y1, z1, volume, pitch);
    }
    public static void PlayWaveSwordHitSound(Level worldLevel, double x1, double y1, double z1, float volume, float pitch) {
        PlaySoundAtLocation(WEAPON_EFF_SWORD_HIT_SOUNDS[ExtraMathMethods.randomInt(WEAPON_EFF_SWORD_HIT_SOUNDS.length)], worldLevel, x1, y1, z1, volume, pitch);
    }
    public static void PlayWaveSwordStabHitSound(Level worldLevel, double x1, double y1, double z1, float volume, float pitch) {
        PlaySoundAtLocation(WEAPON_EFF_SWORD_STAB_HIT_SOUND, worldLevel, x1, y1, z1, volume, pitch);
    }

    //short sword
    public static void PlayShortSwordWieldingSound(Level worldLevel, double x1, double y1, double z1, float volume, float pitch) {
        PlaySoundAtLocation(WEAPON_SHORT_SWORD_WIELDING_SOUNDS[ExtraMathMethods.randomInt(WEAPON_SHORT_SWORD_WIELDING_SOUNDS.length)], worldLevel, x1, y1, z1, volume, pitch);
    }
    public static void PlayShortSwordHitSound(Level worldLevel, double x1, double y1, double z1, float volume, float pitch) {
        PlaySoundAtLocation(WEAPON_SHORT_SWORD_HIT_SOUNDS[ExtraMathMethods.randomInt(WEAPON_SHORT_SWORD_HIT_SOUNDS.length)], worldLevel, x1, y1, z1, volume, pitch);
    }
    public static void PlayShortSwordStabHitSound(Level worldLevel, double x1, double y1, double z1, float volume, float pitch) {
        PlaySoundAtLocation(WEAPON_SHORT_SWORD_STAB_HIT_SOUND, worldLevel, x1, y1, z1, volume, pitch);
    }

    //Bludgeon
    public static void PlayBludgeonWieldingSound(Level worldLevel, double x1, double y1, double z1, float volume, float pitch) {
        PlaySoundAtLocation(WEAPON_BLUDGEON_WIELDING_SOUNDS[ExtraMathMethods.randomInt(WEAPON_BLUDGEON_WIELDING_SOUNDS.length)], worldLevel, x1, y1, z1, volume, pitch);
    }
    public static void PlayBludgeonHitSound(Level worldLevel, double x1, double y1, double z1, float volume, float pitch) {
        PlaySoundAtLocation(WEAPON_BLUDGEON_HIT_SOUNDS[ExtraMathMethods.randomInt(WEAPON_BLUDGEON_HIT_SOUNDS.length)], worldLevel, x1, y1, z1, volume, pitch);
    }
    public static void PlayBludgeonStabHitSound(Level worldLevel, double x1, double y1, double z1, float volume, float pitch) {
        PlaySoundAtLocation(WEAPON_BLUDGEON_STAB_HIT_SOUND, worldLevel, x1, y1, z1, volume, pitch);
    }

    //Zanbato
    public static void PlayZanbatoWieldingSound(Level worldLevel, double x1, double y1, double z1, float volume, float pitch) {
        PlaySoundAtLocation(WEAPON_ZANBATO_WIELDING_SOUNDS[ExtraMathMethods.randomInt(WEAPON_ZANBATO_WIELDING_SOUNDS.length)], worldLevel, x1, y1, z1, volume, pitch);
    }
    public static void PlayZanbatoHitSound(Level worldLevel, double x1, double y1, double z1, float volume, float pitch) {
        PlaySoundAtLocation(WEAPON_ZANBATO_HIT_SOUNDS[ExtraMathMethods.randomInt(WEAPON_ZANBATO_HIT_SOUNDS.length)], worldLevel, x1, y1, z1, volume, pitch);
    }
    public static void PlayZanbatoStabHitSound(Level worldLevel, double x1, double y1, double z1, float volume, float pitch) {
        PlaySoundAtLocation(WEAPON_ZANBATO_STAB_HIT_SOUND, worldLevel, x1, y1, z1, volume, pitch);
    }

    //====================================
    public static void PlayWeaponTypeWieldingSound(int weaponType, Level worldLevel, double x1, double y1, double z1, float volume, float pitch) {
        if (weaponType != DfoSkillTreeItemMethods.IS_HOLDING_NOTHING) {
            WeaponTypeWieldingSoundArr[weaponType].executeSoundAction(worldLevel, x1, y1, z1, volume, pitch);
        }
        else {
            PlayRandomSwordWieldingSound(worldLevel, x1, y1, z1, volume, pitch);
        }
    }

    public static MethodSoundAction getPlayWeaponTypeWieldingSoundAction(final int weaponType) {
        return (worldLevel2, x2, y2, z2, vol2, pitch2) -> {
            PlayWeaponTypeWieldingSound(weaponType, worldLevel2, x2, y2, z2, vol2, pitch2);
        };
    }

    public static void PlayWeaponTypeStabHitSound(int weaponType, Level worldLevel, double x1, double y1, double z1, float volume, float pitch) {
        if (weaponType != DfoSkillTreeItemMethods.IS_HOLDING_NOTHING) {
            WeaponTypeStabHitSoundArr[weaponType].executeSoundAction(worldLevel, x1, y1, z1, volume, pitch);
        }
        else {
            PlayRandomSwordHitSound(worldLevel, x1, y1, z1, volume, pitch);
        }
    }

    public static void PlayWeaponTypeHitEntitySound(int weaponType, Level worldLevel, double x1, double y1, double z1, float volume, float pitch) {
        if (weaponType != DfoSkillTreeItemMethods.IS_HOLDING_NOTHING) {
            WeaponTypeHitEntitySoundArr[weaponType].executeSoundAction(worldLevel, x1, y1, z1, volume, pitch);
        }
        else {
            PlayRandomSwordHitSound(worldLevel, x1, y1, z1, volume, pitch);
        }
    }

    public static void PlayChannelingStartSound(Level worldLevel, double x1, double y1, double z1, float volume, float pitch) {
        PlaySoundAtLocation(SoundRegistry.CHANNELING_SOUND.value(), worldLevel, x1, y1, z1, volume, pitch);
    }

    public static void PlayChannelingEndSound(Level worldLevel, double x1, double y1, double z1, float volume, float pitch) {
        PlaySoundAtLocation(SoundRegistry.CHANNELING_FINISHED.value(), worldLevel, x1, y1, z1, volume, pitch);
    }

    public static void PlayMaxChannelingReadySound(Level worldLevel, double x1, double y1, double z1, float volume, float pitch) {
        PlaySoundAtLocation(SoundRegistry.ENERGY_CHARGE_END.value(), worldLevel, x1, y1, z1, volume, pitch);
    }

    public static void PlayDragonNormalSlashSound(Level worldLevel, double x1, double y1, double z1, float volume, float pitch) {
        PlaySoundAtLocation(BLADEMASTER_DRAGON_SLASH[ExtraMathMethods.randomInt(3)], worldLevel, x1, y1, z1, volume, pitch);
    }

    public static void PlayDragonStartSlashSound(Level worldLevel, double x1, double y1, double z1, float volume, float pitch) {
        PlaySoundAtLocation(BLADEMASTER_DRAGON_SLASH[BLADEMASTER_DRAGON_SLASH.length - 2], worldLevel, x1, y1, z1, volume, pitch);
    }

    public static void PlayDragonEndSlashSound(Level worldLevel, double x1, double y1, double z1, float volume, float pitch) {
        PlaySoundAtLocation(BLADEMASTER_DRAGON_SLASH[BLADEMASTER_DRAGON_SLASH.length - 1], worldLevel, x1, y1, z1, volume, pitch);
    }

    public static void PlayBooDongHitSound(Level worldLevel, double x1, double y1, double z1, float volume, float pitch) {
        PlaySoundAtLocation(BOODONG_HIT_SOUND[ExtraMathMethods.randomInt(2)], worldLevel, x1, y1, z1, volume, pitch);
    }

    //Tempest
    public static void PlayBlademaster025TempestSoundWithIndex(Level worldLevel, int index, double x1, double y1, double z1, float volume, float pitch) {
        PlaySoundAtLocation(BLADEMASTER_TEMPEST_SLASH[index], worldLevel, x1, y1, z1, volume, pitch);
    }

//    public static void PlayBlademaster025TempestSlashSound(Level worldLevel, double x1, double y1, double z1, float volume, float pitch) {
//        PlaySoundAtLocation(BLADEMASTER_TEMPEST_SLASH[ExtraMathMethods.randomInt(2)], worldLevel, x1, y1, z1, volume, pitch);
//    }
//
//    public static void PlayBlademaster025TempestSwordConvergedSound(Level worldLevel, double x1, double y1, double z1, float volume, float pitch) {
//        PlaySoundAtLocation(BLADEMASTER_TEMPEST_SLASH[2], worldLevel, x1, y1, z1, volume, pitch);
//    }
//
//    public static void PlayBlademaster025TempestSwordMovingSound(Level worldLevel, double x1, double y1, double z1, float volume, float pitch) {
//        PlaySoundAtLocation(BLADEMASTER_TEMPEST_SLASH[3], worldLevel, x1, y1, z1, volume, pitch);
//    }
//
//    public static void PlayBlademaster025TempestGroundSwordThrustGroundSound(Level worldLevel, double x1, double y1, double z1, float volume, float pitch) {
//        PlaySoundAtLocation(BLADEMASTER_TEMPEST_SLASH[4], worldLevel, x1, y1, z1, volume, pitch);
//    }

    public static void PlayBlademaster025TempestUpwardSlashSound(Level worldLevel, double x1, double y1, double z1, float volume, float pitch) {
        PlaySoundAtLocation(BLADEMASTER_TEMPEST_SLASH[5], worldLevel, x1, y1, z1, volume, pitch);
    }

    //draw sword
    public static void PlayBlademaster027DrawSwordStartSound(Level worldLevel, double x1, double y1, double z1, float volume, float pitch) {
        PlaySoundAtLocation(SoundRegistry.DRAWING_SWORD_CAST.value(), worldLevel, x1, y1, z1, volume, pitch);
    }

    public static void PlayBlademaster027DrawSwordSlashSound(Level worldLevel, double x1, double y1, double z1, float volume, float pitch) {
        PlaySoundAtLocation(SoundRegistry.DRAWING_SWORD.value(), worldLevel, x1, y1, z1, volume, pitch);
    }
}
