package com.tilldawn.Control.GameController;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.tilldawn.Main;
import com.tilldawn.Model.Bullet;
import com.tilldawn.Model.Weapon;

import java.util.ArrayList;

public class WeaponController {
    private static Weapon weapon;
    private static ArrayList<Bullet> bullets = new ArrayList<>();
    private float animationTime = 0f;
    private boolean isReloading = false;
    private float reloadTimer = 0f;
    private final float RELOAD_DURATION ;
    private boolean weaponFacingRight = true;


    public WeaponController(Weapon weapon) {
        WeaponController.weapon = weapon;
        RELOAD_DURATION = weapon.getWeaponType().getReloadTimeSeconds();
    }

    public void update() {
        if(PlayerController.getPlayer().isFacingLeft()){
            weapon.getSmgSprite().setX(PlayerController.getPlayer().getPlayerSprite().getX());
            weapon.getSmgSprite().setY(PlayerController.getPlayer().getPlayerSprite().getY()+15);
        }else{
            weapon.getSmgSprite().setX(PlayerController.getPlayer().getPlayerSprite().getX()+20);
            weapon.getSmgSprite().setY(PlayerController.getPlayer().getPlayerSprite().getY()+15);
        }



        if (isReloading) {
            animationTime += Gdx.graphics.getDeltaTime();
            reloadAnimation();

            if (animationTime >= RELOAD_DURATION) {
                weapon.setAmmo(weapon.getWeaponType().getAmmoMax());
                isReloading = false;
                reloadTimer = 0;
                animationTime = 0;

                weapon.getSmgSprite().setRegion(new Texture(weapon.getWeaponType().getGunStill()));
            }
        } else {

            animationTime = 0;
            weapon.getSmgSprite().draw(Main.getBatch());
            handleWeaponInput();
        }

        updateBullets();
    }


    public void handleWeaponInput() {
        boolean reloadKeyPressed = false;

        if (Main.getMain().getCurrentUser() == null) {
            reloadKeyPressed = Gdx.input.isKeyJustPressed(Input.Keys.R);
        } else {
            int reloadKey = Main.getMain().getCurrentUser().KEY_RELOAD;
            reloadKeyPressed = Gdx.input.isKeyJustPressed(reloadKey);
        }

        if (reloadKeyPressed && !isReloading) {
            isReloading = true;
            reloadTimer = 0;
        }
    }

    public void handleWeaponRotation(int x, int y) {
        Sprite weaponSprite = weapon.getSmgSprite();

        float centerX = (float) Gdx.graphics.getWidth() / 2;
        float centerY = (float) Gdx.graphics.getHeight() / 2;
        float angle = (float) Math.atan2(y - centerY-10, x - centerX-10);
        weaponSprite.setRotation((float) (3.14 - angle * MathUtils.radiansToDegrees));

    }

    public void handleWeaponShoot(int x, int y) {
        if (isReloading) return;
        float playerX = PlayerController.getPlayer().getPlayerSprite().getX() +
            PlayerController.getPlayer().getPlayerSprite().getWidth() / 2;
        float playerY = PlayerController.getPlayer().getPlayerSprite().getY() +
            PlayerController.getPlayer().getPlayerSprite().getHeight() / 2;

        if (weapon.getAmmo() > 0) {
            int i = 0;
            for(int j = 0 ; j <weapon.getProjectile(); j++){
                Bullet bullet = new Bullet(playerX, playerY, x+i*20, y+i*20,weapon.getWeaponType().getDamagePerProjectile()*weapon.getZarib());
                bullets.add(bullet);
                i++;
            }
            weapon.setAmmo(weapon.getAmmo() - 1);
        }
    }

    public void updateBullets() {
        for (Bullet bullet : bullets) {
            bullet.update();
            bullet.getSprite().draw(Main.getBatch());
        }
    }

    public void reloadAnimation(){
        Animation<Texture> animation = weapon.getWeaponType().getAnimation();
        Texture currentFrame = animation.getKeyFrame(animationTime, false);
        weapon.getSmgSprite().setRegion(currentFrame);
        weapon.getSmgSprite().draw(Main.getBatch());
    }

    public static Weapon getWeapon() {
        return weapon;
    }

    public static void setWeapon(Weapon weapon) {
        WeaponController.weapon = weapon;
    }

    public static ArrayList<Bullet> getBullets() {
        return bullets;
    }

    public static  void setBullets(ArrayList<Bullet> bullets) {
         WeaponController.bullets = bullets;
    }
}
