package com.tilldawn.Model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.tilldawn.Model.Enums.WeaponType;

public class Weapon {
    private final WeaponType weaponType;
    private final Texture smgTexture;
    private int projectile;
    private Sprite smgSprite;
    private int ammo;
    private int zarib2 = 0;
    private float zarib = 1;


    public Weapon(Player owner, WeaponType weaponType){
        this.weaponType = weaponType;
        smgTexture = new Texture(weaponType.getGunStill());
        this.projectile = weaponType.getProjectilesPerShot();
        smgSprite = new Sprite(smgTexture);
        ammo = weaponType.getAmmoMax() + zarib2;
        smgSprite.setX(owner.getPlayerSprite().getX());
        smgSprite.setY(owner.getPlayerSprite().getY());
        smgSprite.setSize(50,50);
    }

    public Sprite getSmgSprite() {
        return smgSprite;
    }

    public int getAmmo() {
        return ammo+zarib2;
    }

    public void setAmmo(int ammo){
        this.ammo = ammo;
    }


    public WeaponType getWeaponType() {
        return weaponType;
    }

    public float getZarib() {
        return zarib;
    }

    public void setZarib(float zarib) {
        this.zarib = zarib;
    }

    public int getZarib2() {
        return zarib2;
    }

    public void setZarib2(int zarib2) {
        this.zarib2 = zarib2;
    }

    public int getProjectile() {
        return projectile;
    }

    public void setProjectile(int projectile) {
        this.projectile = projectile;
    }
}
