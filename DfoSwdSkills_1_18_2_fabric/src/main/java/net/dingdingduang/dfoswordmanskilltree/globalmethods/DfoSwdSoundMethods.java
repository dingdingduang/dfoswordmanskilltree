package net.dingdingduang.dfoswordmanskilltree.globalmethods;

import net.dingdingduang.dfoswordmanskilltree.dfoswdregistry.SoundRegistry;
import net.dingdingduang.dfoswordmanskilltree.util.ExtraMathMethods;

import net.dingdingduang.dfoswordmanskilltree.util.MethodSoundAction;

import net.minecraft.entity.LivingEntity;
import net.minecraft.sound.SoundEvent;
import net.minecraft.world.World;

import modified.org.joml.Vector3f;

import static net.dingdingduang.somebasicskills.globalmethods.SoundMethods.*;

public class DfoSwdSoundMethods {
    //skill sounds
    public static final SoundEvent[] BLADEMASTER_0_05_TRIPLE_SLASH = {SoundRegistry.TSLASH_01, SoundRegistry.TSLASH_02, SoundRegistry.TSLASH_03};



    //=====================
    //hit sound
    public static final SoundEvent[] BOODONG_HIT_SOUND = {SoundRegistry.BOODONG_HIT_01, SoundRegistry.BOODONG_HIT_02};
    //weapon sounds
    public static final SoundEvent[] WEAPON_TOUCH_SOUND = {SoundRegistry.BEAMSWD_TOUCH, SoundRegistry.KAT_TOUCH, SoundRegistry.MINSWD_TOUCH, SoundRegistry.SLESSSWD_TOUCH, SoundRegistry.STICK_TOUCH, SoundRegistry.SQRSWD_TOUCH};
    //lightsabre
    public static final SoundEvent[] WEAPON_LIGHTSABRE_SHOCK_EFF_SOUNDS = {SoundRegistry.BEAMSWDB, SoundRegistry.BEAMSWDC_01};
    public static final SoundEvent[] WEAPON_LIGHTSABRE_WIELDING_SOUNDS = {SoundRegistry.BEAMSWDA_01, SoundRegistry.BEAMSWDA_02, SoundRegistry.BEAMSWDA_03};
    public static final SoundEvent[] WEAPON_LIGHTSABRE_HIT_SOUNDS = {SoundRegistry.BEAMSWDA_HIT_01, SoundRegistry.BEAMSWDA_HIT_02};
    public static final SoundEvent WEAPON_LIGHTSABRE_STAB_HIT_SOUND = SoundRegistry.BEAMSWDB_HIT;
    public static final SoundEvent[] WEAPON_LIGHTSABRE_LIGHTNING_SOUNDS = {SoundRegistry.BEAMSWDC_HIT_01, SoundRegistry.BEAMSWDC_HIT_02};

    //katana
    public static final SoundEvent[] WEAPON_KATANA_WIELDING_SOUNDS = {SoundRegistry.KATA_01, SoundRegistry.KATA_02, SoundRegistry.KATA_03, SoundRegistry.KATC_01, SoundRegistry.KATC_02};
    public static final SoundEvent[] WEAPON_KATANA_HIT_SOUNDS = {SoundRegistry.KATA_HIT_01, SoundRegistry.KATA_HIT_02, SoundRegistry.KATC_01, SoundRegistry.KATB_HIT};
    public static final SoundEvent WEAPON_KATANA_STAB_HIT_SOUND = SoundRegistry.KATB_HIT;

    //unknown sword
    public static final SoundEvent[] WEAPON_EFF_SWORD_WIELDING_SOUNDS = {SoundRegistry.MINSWDA_01, SoundRegistry.MINSWDA_02, SoundRegistry.MINSWDA_03, SoundRegistry.MINSWDB, SoundRegistry.MINSWDC_01, SoundRegistry.MINSWDC_02, SoundRegistry.MINSWDC_03};
    public static final SoundEvent[] WEAPON_EFF_SWORD_HIT_SOUNDS = {SoundRegistry.MINSWDA_HIT_01, SoundRegistry.MINSWDA_HIT_02, SoundRegistry.MINSWDA_HIT_03};
    public static final SoundEvent WEAPON_EFF_SWORD_STAB_HIT_SOUND = SoundRegistry.MINSWDB_HIT;

    //short sword
    public static final SoundEvent[] WEAPON_SHORT_SWORD_WIELDING_SOUNDS = {SoundRegistry.SLESSSWD_01, SoundRegistry.SLESSSWD_02};
    public static final SoundEvent[] WEAPON_SHORT_SWORD_HIT_SOUNDS = {SoundRegistry.SLESSSWD_HIT_01, SoundRegistry.SLESSSWD_HIT_02, SoundRegistry.MINSWDA_HIT_03};
    public static final SoundEvent WEAPON_SHORT_SWORD_STAB_HIT_SOUND = SoundRegistry.MINSWDB_HIT;

    //BLUDGEON
    public static final SoundEvent[] WEAPON_BLUDGEON_WIELDING_SOUNDS = {SoundRegistry.STICKA_01, SoundRegistry.STICKA_02, SoundRegistry.STICKB_01, SoundRegistry.STICKC_01};
    public static final SoundEvent[] WEAPON_BLUDGEON_HIT_SOUNDS = {SoundRegistry.STICKA_HIT_01, SoundRegistry.STICKA_HIT_02, SoundRegistry.STICKC_HIT_01, SoundRegistry.STICKC_HIT_02};
    public static final SoundEvent WEAPON_BLUDGEON_STAB_HIT_SOUND = SoundRegistry.STICKB_HIT_01;

    //zanbato
    public static final SoundEvent[] WEAPON_ZANBATO_WIELDING_SOUNDS = {SoundRegistry.SQRSWDA_01, SoundRegistry.SQRSWDA_02, SoundRegistry.SQRSWDB};
    public static final SoundEvent[] WEAPON_ZANBATO_HIT_SOUNDS = {SoundRegistry.SQRSWDA_HIT_01, SoundRegistry.SQRSWDA_HIT_02, SoundRegistry.SQRSWDC_HIT_01, SoundRegistry.SQRSWDC_HIT_02};
    public static final SoundEvent WEAPON_ZANBATO_STAB_HIT_SOUND = SoundRegistry.SQRSWDB_HIT;

    //skill
    public static final SoundEvent[] BLADEMASTER_DRAGON_SLASH = {SoundRegistry.SM_MENGRYONG_01, SoundRegistry.SM_MENGRYONG_02, SoundRegistry.SM_MENGRYONG_03, SoundRegistry.SM_MENGRYONG_START, SoundRegistry.SM_MENGRYONG_FIN};
    public static final SoundEvent[] BLADEMASTER_TEMPEST_SLASH = {SoundRegistry.W_ATK_01, SoundRegistry.W_ATK_02, SoundRegistry.W_SPIRITS_FLASH, SoundRegistry.W_WEAPON_MOVING, SoundRegistry.W_WEAPON_DRIVE, SoundRegistry.W_ATK_FIN};

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
    public static void PlayBlademaster005TripleSlashSound(World worldLevel, double x1, double y1, double z1, float volume, float pitch) {
        PlaySoundAtLocation(BLADEMASTER_0_05_TRIPLE_SLASH[ExtraMathMethods.randomInt(3)], worldLevel, x1, y1, z1, volume, pitch);
    }



    //===============================================
    //weapon sounds
    //===============================================
    public static void PlayRandomSwordHitSound(World worldLevel, double x1, double y1, double z1, float volume, float pitch) {
        SoundEvent[] tempArr = {SoundRegistry.SQRSWDA_HIT_01, SoundRegistry.SQRSWDC_HIT_01, SoundRegistry.SQRSWDC_HIT_02};
        PlaySoundAtLocation(tempArr[ExtraMathMethods.randomInt(tempArr.length)], worldLevel, x1, y1, z1, volume, pitch);
    }

    public static void PlayRandomSwordWieldingSound(World worldLevel, double x1, double y1, double z1, float volume, float pitch) {
        SoundEvent[] tempArr = {SoundRegistry.SLESSSWD_01, SoundRegistry.SLESSSWD_02, SoundRegistry.SQRSWDB,
        SoundRegistry.SQRSWDC_01, SoundRegistry.STICKA_01, SoundRegistry.STICKA_02, SoundRegistry.STICKB_01, SoundRegistry.STICKC_01};
        PlaySoundAtLocation(tempArr[ExtraMathMethods.randomInt(tempArr.length)], worldLevel, x1, y1, z1, volume, pitch);
    }

    public static void PlayRandomSwordUpperSlashSound(World worldLevel, double x1, double y1, double z1, float volume, float pitch) {
        SoundEvent[] tempArr = {SoundRegistry.UPPER_SLASH_01, SoundRegistry.UPPER_SLASH_02};
        PlaySoundAtLocation(tempArr[ExtraMathMethods.randomInt(tempArr.length)], worldLevel, x1, y1, z1, volume, pitch);
    }

    public static void PlayGroundQuakeSound(Vector3f targetLocBlockCenter, float volume, float pitch) {
        PlayClientSound(targetLocBlockCenter, SoundRegistry.GROUND_STOMP, volume, pitch);
    }

    public static void PlaySwordGuardStartSound(World worldlevel, float x1, float y1, float z1, float volume, float pitch) {
        PlaySoundAtLocation(SoundRegistry.SWD_EFF_05, worldlevel, x1, y1, z1, volume, pitch);
    }

    public static void PlaySwordGuardingSound(World worldlevel, float x1, float y1, float z1, float volume, float pitch) {
        PlaySoundAtLocation(SoundRegistry.SWD_EFF_01, worldlevel, x1, y1, z1, volume, pitch);
    }

    public static void PlaySwordReflectionSound(World worldlevel, float x1, float y1, float z1, float volume, float pitch) {
        PlaySoundAtLocation(SoundRegistry.GUARD_ATK, worldlevel, x1, y1, z1, volume, pitch);
    }

    public static void PlaySwordGuardEndSound(World worldlevel, float x1, float y1, float z1, float volume, float pitch) {
        PlaySoundAtLocation(SoundRegistry.SWD_EFF_06, worldlevel, x1, y1, z1, volume, pitch);
    }



    //-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-
    //lightsabre
    public static void PlayLightSabreWieldingSound(World worldLevel, double x1, double y1, double z1, float volume, float pitch) {
        PlaySoundAtLocation(WEAPON_LIGHTSABRE_WIELDING_SOUNDS[ExtraMathMethods.randomInt(WEAPON_LIGHTSABRE_WIELDING_SOUNDS.length)], worldLevel, x1, y1, z1, volume, pitch);
    }
    public static void PlayLightSabreStabHitSound(World worldLevel, double x1, double y1, double z1, float volume, float pitch) {
        PlaySoundAtLocation(WEAPON_LIGHTSABRE_STAB_HIT_SOUND, worldLevel, x1, y1, z1, volume, pitch);
    }
    public static void PlayLightSabreHitSound(World worldLevel, double x1, double y1, double z1, float volume, float pitch) {
        PlaySoundAtLocation(WEAPON_LIGHTSABRE_HIT_SOUNDS[ExtraMathMethods.randomInt(WEAPON_LIGHTSABRE_HIT_SOUNDS.length)], worldLevel, x1, y1, z1, volume, pitch);
    }

    //katana
    public static void PlayKatanaWieldingSound(World worldLevel, double x1, double y1, double z1, float volume, float pitch) {
        PlaySoundAtLocation(WEAPON_KATANA_WIELDING_SOUNDS[ExtraMathMethods.randomInt(WEAPON_KATANA_WIELDING_SOUNDS.length)], worldLevel, x1, y1, z1, volume, pitch);
    }
    public static void PlayKatanaHitSound(World worldLevel, double x1, double y1, double z1, float volume, float pitch) {
        PlaySoundAtLocation(WEAPON_KATANA_HIT_SOUNDS[ExtraMathMethods.randomInt(WEAPON_KATANA_HIT_SOUNDS.length)], worldLevel, x1, y1, z1, volume, pitch);
    }
    public static void PlayKatanaStabHitSound(World worldLevel, double x1, double y1, double z1, float volume, float pitch) {
        PlaySoundAtLocation(WEAPON_KATANA_STAB_HIT_SOUND, worldLevel, x1, y1, z1, volume, pitch);
    }

    //unknown wave sword
    public static void PlayWaveSwordWieldingSound(World worldLevel, double x1, double y1, double z1, float volume, float pitch) {
        PlaySoundAtLocation(WEAPON_EFF_SWORD_WIELDING_SOUNDS[ExtraMathMethods.randomInt(WEAPON_EFF_SWORD_WIELDING_SOUNDS.length)], worldLevel, x1, y1, z1, volume, pitch);
    }
    public static void PlayWaveSwordHitSound(World worldLevel, double x1, double y1, double z1, float volume, float pitch) {
        PlaySoundAtLocation(WEAPON_EFF_SWORD_HIT_SOUNDS[ExtraMathMethods.randomInt(WEAPON_EFF_SWORD_HIT_SOUNDS.length)], worldLevel, x1, y1, z1, volume, pitch);
    }
    public static void PlayWaveSwordStabHitSound(World worldLevel, double x1, double y1, double z1, float volume, float pitch) {
        PlaySoundAtLocation(WEAPON_EFF_SWORD_STAB_HIT_SOUND, worldLevel, x1, y1, z1, volume, pitch);
    }

    //short sword
    public static void PlayShortSwordWieldingSound(World worldLevel, double x1, double y1, double z1, float volume, float pitch) {
        PlaySoundAtLocation(WEAPON_SHORT_SWORD_WIELDING_SOUNDS[ExtraMathMethods.randomInt(WEAPON_SHORT_SWORD_WIELDING_SOUNDS.length)], worldLevel, x1, y1, z1, volume, pitch);
    }
    public static void PlayShortSwordHitSound(World worldLevel, double x1, double y1, double z1, float volume, float pitch) {
        PlaySoundAtLocation(WEAPON_SHORT_SWORD_HIT_SOUNDS[ExtraMathMethods.randomInt(WEAPON_SHORT_SWORD_HIT_SOUNDS.length)], worldLevel, x1, y1, z1, volume, pitch);
    }
    public static void PlayShortSwordStabHitSound(World worldLevel, double x1, double y1, double z1, float volume, float pitch) {
        PlaySoundAtLocation(WEAPON_SHORT_SWORD_STAB_HIT_SOUND, worldLevel, x1, y1, z1, volume, pitch);
    }

    //Bludgeon
    public static void PlayBludgeonWieldingSound(World worldLevel, double x1, double y1, double z1, float volume, float pitch) {
        PlaySoundAtLocation(WEAPON_BLUDGEON_WIELDING_SOUNDS[ExtraMathMethods.randomInt(WEAPON_BLUDGEON_WIELDING_SOUNDS.length)], worldLevel, x1, y1, z1, volume, pitch);
    }
    public static void PlayBludgeonHitSound(World worldLevel, double x1, double y1, double z1, float volume, float pitch) {
        PlaySoundAtLocation(WEAPON_BLUDGEON_HIT_SOUNDS[ExtraMathMethods.randomInt(WEAPON_BLUDGEON_HIT_SOUNDS.length)], worldLevel, x1, y1, z1, volume, pitch);
    }
    public static void PlayBludgeonStabHitSound(World worldLevel, double x1, double y1, double z1, float volume, float pitch) {
        PlaySoundAtLocation(WEAPON_BLUDGEON_STAB_HIT_SOUND, worldLevel, x1, y1, z1, volume, pitch);
    }

    //Zanbato
    public static void PlayZanbatoWieldingSound(World worldLevel, double x1, double y1, double z1, float volume, float pitch) {
        PlaySoundAtLocation(WEAPON_ZANBATO_WIELDING_SOUNDS[ExtraMathMethods.randomInt(WEAPON_ZANBATO_WIELDING_SOUNDS.length)], worldLevel, x1, y1, z1, volume, pitch);
    }
    public static void PlayZanbatoHitSound(World worldLevel, double x1, double y1, double z1, float volume, float pitch) {
        PlaySoundAtLocation(WEAPON_ZANBATO_HIT_SOUNDS[ExtraMathMethods.randomInt(WEAPON_ZANBATO_HIT_SOUNDS.length)], worldLevel, x1, y1, z1, volume, pitch);
    }
    public static void PlayZanbatoStabHitSound(World worldLevel, double x1, double y1, double z1, float volume, float pitch) {
        PlaySoundAtLocation(WEAPON_ZANBATO_STAB_HIT_SOUND, worldLevel, x1, y1, z1, volume, pitch);
    }

    //====================================
    public static void PlayWeaponTypeWieldingSound(int weaponType, World worldLevel, double x1, double y1, double z1, float volume, float pitch) {
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

    public static void PlayWeaponTypeStabHitSound(int weaponType, World worldLevel, double x1, double y1, double z1, float volume, float pitch) {
        if (weaponType != DfoSkillTreeItemMethods.IS_HOLDING_NOTHING) {
            WeaponTypeStabHitSoundArr[weaponType].executeSoundAction(worldLevel, x1, y1, z1, volume, pitch);
        }
        else {
            PlayRandomSwordHitSound(worldLevel, x1, y1, z1, volume, pitch);
        }
    }

    public static void PlayWeaponTypeHitEntitySound(int weaponType, World worldLevel, double x1, double y1, double z1, float volume, float pitch) {
        if (weaponType != DfoSkillTreeItemMethods.IS_HOLDING_NOTHING) {
            WeaponTypeHitEntitySoundArr[weaponType].executeSoundAction(worldLevel, x1, y1, z1, volume, pitch);
        }
        else {
            PlayRandomSwordHitSound(worldLevel, x1, y1, z1, volume, pitch);
        }
    }

    public static void PlayChannelingStartSound(World worldLevel, double x1, double y1, double z1, float volume, float pitch) {
        PlaySoundAtLocation(SoundRegistry.CHANNELING_SOUND, worldLevel, x1, y1, z1, volume, pitch);
    }

    public static void PlayChannelingEndSound(World worldLevel, double x1, double y1, double z1, float volume, float pitch) {
        PlaySoundAtLocation(SoundRegistry.CHANNELING_FINISHED, worldLevel, x1, y1, z1, volume, pitch);
    }

    public static void PlayMaxChannelingReadySound(World worldLevel, double x1, double y1, double z1, float volume, float pitch) {
        PlaySoundAtLocation(SoundRegistry.ENERGY_CHARGE_END, worldLevel, x1, y1, z1, volume, pitch);
    }

    public static void PlayDragonNormalSlashSound(World worldLevel, double x1, double y1, double z1, float volume, float pitch) {
        PlaySoundAtLocation(BLADEMASTER_DRAGON_SLASH[ExtraMathMethods.randomInt(3)], worldLevel, x1, y1, z1, volume, pitch);
    }

    public static void PlayDragonStartSlashSound(World worldLevel, double x1, double y1, double z1, float volume, float pitch) {
        PlaySoundAtLocation(BLADEMASTER_DRAGON_SLASH[BLADEMASTER_DRAGON_SLASH.length - 2], worldLevel, x1, y1, z1, volume, pitch);
    }

    public static void PlayDragonEndSlashSound(World worldLevel, double x1, double y1, double z1, float volume, float pitch) {
        PlaySoundAtLocation(BLADEMASTER_DRAGON_SLASH[BLADEMASTER_DRAGON_SLASH.length - 1], worldLevel, x1, y1, z1, volume, pitch);
    }

    public static void PlayBooDongHitSound(World worldLevel, double x1, double y1, double z1, float volume, float pitch) {
        PlaySoundAtLocation(BOODONG_HIT_SOUND[ExtraMathMethods.randomInt(2)], worldLevel, x1, y1, z1, volume, pitch);
    }

    //Tempest
    public static void PlayBlademaster025TempestSoundWithIndex(World worldLevel, int index, double x1, double y1, double z1, float volume, float pitch) {
        PlaySoundAtLocation(BLADEMASTER_TEMPEST_SLASH[index], worldLevel, x1, y1, z1, volume, pitch);
    }

//    public static void PlayBlademaster025TempestSlashSound(World worldLevel, double x1, double y1, double z1, float volume, float pitch) {
//        PlaySoundAtLocation(BLADEMASTER_TEMPEST_SLASH[ExtraMathMethods.randomInt(2)], worldLevel, x1, y1, z1, volume, pitch);
//    }
//
//    public static void PlayBlademaster025TempestSwordConvergedSound(World worldLevel, double x1, double y1, double z1, float volume, float pitch) {
//        PlaySoundAtLocation(BLADEMASTER_TEMPEST_SLASH[2], worldLevel, x1, y1, z1, volume, pitch);
//    }
//
//    public static void PlayBlademaster025TempestSwordMovingSound(World worldLevel, double x1, double y1, double z1, float volume, float pitch) {
//        PlaySoundAtLocation(BLADEMASTER_TEMPEST_SLASH[3], worldLevel, x1, y1, z1, volume, pitch);
//    }
//
//    public static void PlayBlademaster025TempestGroundSwordThrustGroundSound(World worldLevel, double x1, double y1, double z1, float volume, float pitch) {
//        PlaySoundAtLocation(BLADEMASTER_TEMPEST_SLASH[4], worldLevel, x1, y1, z1, volume, pitch);
//    }

    public static void PlayBlademaster025TempestUpwardSlashSound(World worldLevel, double x1, double y1, double z1, float volume, float pitch) {
        PlaySoundAtLocation(BLADEMASTER_TEMPEST_SLASH[5], worldLevel, x1, y1, z1, volume, pitch);
    }

    //draw sword
    public static void PlayBlademaster027DrawSwordStartSound(World worldLevel, double x1, double y1, double z1, float volume, float pitch) {
        PlaySoundAtLocation(SoundRegistry.DRAWING_SWORD_CAST, worldLevel, x1, y1, z1, volume, pitch);
    }

    public static void PlayBlademaster027DrawSwordSlashSound(World worldLevel, double x1, double y1, double z1, float volume, float pitch) {
        PlaySoundAtLocation(SoundRegistry.DRAWING_SWORD, worldLevel, x1, y1, z1, volume, pitch);
    }

    public static void PlayerSBSCastingFailedSound(LivingEntity entity1) {
        PlaySoundAtLivingEntityLocationEX(entity1, net.dingdingduang.somebasicskills.registries.SoundRegistry.CHANNELING_FAILED_SOUND, 0.4f, 1.0f);
    }
}
