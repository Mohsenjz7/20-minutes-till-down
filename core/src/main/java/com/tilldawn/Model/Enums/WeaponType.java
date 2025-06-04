package com.tilldawn.Model.Enums;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.tilldawn.Model.GameAssetManager;

public enum WeaponType {
    REVOLVER(6, 1f, 1, 20, "Revolver", GameAssetManager.getGameAssetManager().getRevolverStill(), GameAssetManager.getGameAssetManager().getRevolverReloadAnimation()),
    SHOTGUN(2, 1f, 4, 10, "Shotgun", GameAssetManager.getGameAssetManager().getGrenadeLauncherStill(),  GameAssetManager.getGameAssetManager().getGrenadeLauncherReloadAnimation()),
    SMGS_DUAL(24, 2f, 1, 8, "SMGs Dual",GameAssetManager.getGameAssetManager().getSMGStill(), GameAssetManager.getGameAssetManager().getSMGReloadAnimation());

    private final int ammoMax;
    private final float reloadTimeSeconds;
    private final int projectilesPerShot;
    private final int damagePerProjectile;
    private final String displayName;
    private final String GunStill;
    private final Animation<Texture> reloadAnimation;

    WeaponType(int ammoMax, float reloadTimeSeconds, int projectilesPerShot, int damagePerProjectile, String displayName, String gunStill, Animation<Texture> animation) {
        this.ammoMax = ammoMax;
        this.reloadTimeSeconds = reloadTimeSeconds;
        this.projectilesPerShot = projectilesPerShot;
        this.damagePerProjectile = damagePerProjectile;
        this.displayName = displayName;
        GunStill = gunStill;
        this.reloadAnimation = animation;
    }


    public int getAmmoMax() {
        return ammoMax;
    }

    public float getReloadTimeSeconds() {
        return reloadTimeSeconds;
    }

    public int getProjectilesPerShot() {
        return projectilesPerShot;
    }

    public int getDamagePerProjectile() {
        return damagePerProjectile;
    }

    public String getDisplayName() {
        return displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }

    public Animation<Texture> getAnimation() {
        return reloadAnimation;
    }

    public String getGunStill() {
        return GunStill;
    }
}
