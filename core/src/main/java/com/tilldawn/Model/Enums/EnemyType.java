package com.tilldawn.Model.Enums;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.tilldawn.Model.GameAssetManager;

public enum EnemyType {
    Tree(30, GameAssetManager.getGameAssetManager().getTree_0(),
        GameAssetManager.getGameAssetManager().getTreeAnimation(), null),

    Tentacle(25, null, GameAssetManager.getGameAssetManager().getAttack(),
        GameAssetManager.getGameAssetManager().getSpawnAnimation()),

    EyeBat(50,null,GameAssetManager.getGameAssetManager().getEyeBat(),null);

    private final int HP;
    private final String idle;
    private final Animation<Texture> walkAnimation;
    private final Animation<Texture> spawnAnimation;

    EnemyType(int HP, String idle, Animation<Texture> walkAnimation, Animation<Texture> spawnAnimation) {
        this.HP = HP;
        this.idle = idle;
        this.walkAnimation = walkAnimation;
        this.spawnAnimation = spawnAnimation;
    }

    public String getIdle() {
        return idle;
    }

    public Animation<Texture> getWalkAnimation() {
        return walkAnimation;
    }

    public Animation<Texture> getSpawnAnimation() {
        return spawnAnimation;
    }

    public int getHP() {
        return HP;
    }
}
