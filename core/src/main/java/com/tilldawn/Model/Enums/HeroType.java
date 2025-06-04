package com.tilldawn.Model.Enums;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.tilldawn.Model.GameAssetManager;

public enum HeroType {
    Shana(4, 4, GameAssetManager.getGameAssetManager().getShana_idle0(), GameAssetManager.getGameAssetManager().getShana_idle_anim(),GameAssetManager.getGameAssetManager().getShana_Run_anim()),
    Diamond(7, 1, GameAssetManager.getGameAssetManager().getDiamond_idle0(), GameAssetManager.getGameAssetManager().getDiamond_idle_anim(),GameAssetManager.getGameAssetManager().getDiamond_Run_anim()),
    Scarlet(3, 5, GameAssetManager.getGameAssetManager().getScarlet_idle0(), GameAssetManager.getGameAssetManager().getScarlet_idle_anim(),GameAssetManager.getGameAssetManager().getScarlet_Run_anim()),
    Lilith(5, 3, GameAssetManager.getGameAssetManager().getLilith_idle0(), GameAssetManager.getGameAssetManager().getLilith_idle_anim(),GameAssetManager.getGameAssetManager().getLilith_Run_anim()),
    Dasher(2, 10, GameAssetManager.getGameAssetManager().getDasher_idle0(), GameAssetManager.getGameAssetManager().getDasher_idle_anim(),GameAssetManager.getGameAssetManager().getDasher_Run_anim()),;
    private final int HP;
    private final int SPEED;
    private final String TEXTURE;
    private final Animation<Texture> ANIMATION;
    private final Animation<Texture> ANIMATIONRUN;


    HeroType(int hp, int speed, String texture , Animation<Texture> animation,  Animation<Texture> animationrun) {
        HP = hp;
        SPEED = speed;
        TEXTURE = texture;
        ANIMATION =  animation;
        ANIMATIONRUN =  animationrun;
    }

    public int getHP() {
        return HP;
    }

    public int getSPEED() {
        return SPEED;
    }

    public String getTEXTURE() {
        return TEXTURE;
    }

    public Animation<Texture> getANIMATION() {
        return ANIMATION;
    }

    public Animation<Texture> getANIMATIONRUN() {
        return ANIMATIONRUN;
    }
}
