package com.tilldawn.Control.GameController;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.tilldawn.Main;
import com.tilldawn.Model.*;
import com.tilldawn.Model.Enums.Ability;

import java.util.ArrayList;

public class PlayerController {
    private static Player player;
    private float abilityTimer;
    private boolean abilityTime = false;
    private static ArrayList<XP> xpList = new ArrayList();
    private static ArrayList<Bullet> bulletList = new ArrayList();

    public PlayerController(Player player) {
        PlayerController.player = player;
    }

    public static ArrayList<Bullet> getBulletList() {
        return bulletList;
    }

    public static void setBulletList(ArrayList<Bullet> bulletList) {
        PlayerController.bulletList = bulletList;
    }

    public void update() {
        if(player.getPlayerHealth() <= 0){

        }
        handlePlayerInput();
        if (abilityTime) {
            abilityTimer += Gdx.graphics.getDeltaTime();
        }

        if (abilityTimer == 10) {
            if (player.getAbility().equals(Ability.Damage)) {
                abilityTimer = 0;
                abilityTime = false;
                WeaponController.getWeapon().setZarib(1);
            } else if (player.getAbility().equals(Ability.Speedy)) {
                abilityTimer = 0;
                abilityTime = true;
                player.setSpeed(player.getSpeed() / 2);
            }
        }
        player.update();
        if (player.getState() == Player.PlayerState.RUNNING) {
            runAnimation();
        } else {
            idleAnimation();
        }

        for (XP xp : xpList) {
            xp.getSprite().draw(Main.getBatch());
        }
        updateXP();
        checkBulletCollisions();
        player.getPlayerSprite().draw(Main.getBatch());
        calculateLevel();
    }

    public void handlePlayerInput() {
        boolean isMoving = false;
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {

            if (player.getPlayerSprite().getY() <= 2650) {
                player.getPlayerSprite().setY(player.getPlayerSprite().getY() + player.getSpeed());
            }
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            if (player.getPlayerSprite().getY() >= 30) {
                player.getPlayerSprite().setY(player.getPlayerSprite().getY() - player.getSpeed());
            }
        }

        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            if (player.getPlayerSprite().getX() <= 3710) {
                player.getPlayerSprite().setX(player.getPlayerSprite().getX() + player.getSpeed());
                if (player.isFacingLeft()) {
                    player.getPlayerSprite().flip(true, false);
                    player.setFacingLeft(false);
                }
                isMoving = true;
            }


        }

        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            if (player.getPlayerSprite().getX() >= 24) {
                player.getPlayerSprite().setX(player.getPlayerSprite().getX() - player.getSpeed());
                if (!player.isFacingLeft()) {
                    player.getPlayerSprite().flip(true, false);
                    player.setFacingLeft(true);
                }

                isMoving = true;
            }

        }

        if (isMoving) {
            if (player.getState() != Player.PlayerState.RUNNING) {
                player.setTime(0);
            }
            player.setState(Player.PlayerState.RUNNING);
        } else {
            if (player.getState() != Player.PlayerState.IDLE) {
                player.setTime(0);
            }
            player.setState(Player.PlayerState.IDLE);
        }
    }

    public void idleAnimation() {
        Animation<Texture> animation = player.getHero().getANIMATION();
        animation.setPlayMode(Animation.PlayMode.LOOP);

        TextureRegion frame = new TextureRegion(animation.getKeyFrame(player.getTime(), true));
        if (frame.isFlipX() != player.isFacingLeft()) {
            frame.flip(true, false);
        }

        player.getPlayerSprite().setRegion(frame);
        player.setTime(player.getTime() + Gdx.graphics.getDeltaTime());
    }

    public void runAnimation() {
        Animation<Texture> animation = player.getHero().getANIMATIONRUN();
        animation.setPlayMode(Animation.PlayMode.LOOP);

        TextureRegion frame = new TextureRegion(animation.getKeyFrame(player.getTime(), true));
        if (frame.isFlipX() != player.isFacingLeft()) {
            frame.flip(true, false);
        }

        player.getPlayerSprite().setRegion(frame);
        player.setTime(player.getTime() + Gdx.graphics.getDeltaTime());
    }

    public void calculateLevel() {
        if (player.getXp() > player.getLevel() * 20) {
            player.setLevel(player.getLevel() + 1);
            player.setXp(0);
        }
    }

    public static void updateXP() {
        ArrayList<XP> toRemove = new ArrayList<>();
        for (XP xp : xpList) {
            if (xp.getRect().collidesWith(player.getRect())) {
                player.setXp(player.getXp() + 1);
                toRemove.add(xp);
            }
        }
        xpList.removeAll(toRemove);
    }


    public static Player getPlayer() {
        return player;
    }

    public static void setPlayer(Player player) {
        PlayerController.player = player;
    }

    public void ability() {
        if (player.getAbility() != null) {
            if (player.getAbility().equals(Ability.Damage)) {
                abilityTime = true;
                WeaponController.getWeapon().setZarib(1.25f);
            } else if (player.getAbility().equals(Ability.Amogrease)) {
                WeaponController.getWeapon().setZarib2(5);
            } else if (player.getAbility().equals(Ability.Speedy)) {
                abilityTime = true;
                player.setSpeed(PlayerController.player.getSpeed() * 2);
            } else if (player.getAbility().equals(Ability.Progrease)) {
                WeaponController.getWeapon().setProjectile(WeaponController.getWeapon().getProjectile() + 1);
            } else if (player.getAbility().equals(Ability.Vitality)) {
                player.setMaxHealth(PlayerController.player.getMaxHealth() + 1);
            }
        }
    }

    public boolean isAbilityTime() {
        return abilityTime;
    }

    public void setAbilityTime(boolean abilityTime) {
        this.abilityTime = abilityTime;
    }

    public static ArrayList<XP> getXpList() {
        return xpList;
    }

    public static void setXpList(ArrayList<XP> xpList) {
        PlayerController.xpList = xpList;
    }

    private void checkBulletCollisions() {
        ArrayList<Bullet> bullets = bulletList;
        ArrayList<Bullet> bulletsToRemove = new ArrayList<>();

        for (Bullet bullet : bullets) {
            if(bullet.getRect().collidesWith(player.getRect())) {
                bulletsToRemove.add(bullet);
                player.setPlayerHealth(player.getPlayerHealth() - bullet.getDamage());
            }
        }

        bullets.removeAll(bulletsToRemove);

    }
}
