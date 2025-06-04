package com.tilldawn.Model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.tilldawn.Model.Enums.EnemyType;
import com.tilldawn.Model.Enums.HeroType;

public class Enemy {
    private EnemyType enemy;
    private Texture enemyTexture;
    private Sprite enemySprite;
    private float enemyHealth;
    private CollisionRect rect ;
    private float time = 0;
    int x;
    int y;
    private boolean hasDealtDamage = false;
    public enum EnemyState {
        SPAWNING,
        ALIVE,
        DYING
    }

    private EnemyState state = EnemyState.SPAWNING;
    private float deathAnimTime = 0;





    public Enemy(EnemyType enemyType, int x, int y) {
        this.enemy = enemyType;
        float scale = (enemyType == EnemyType.Tree) ? 2f : 2.5f;
        if(enemyType == EnemyType.EyeBat){
            scale = 1.5f;
        }

        if (enemyType.getIdle() != null) {
            enemyTexture = new Texture(Gdx.files.internal(enemyType.getIdle()));
            enemySprite = new Sprite(enemyTexture);
            enemySprite.setSize(enemyTexture.getWidth() * scale, enemyTexture.getHeight() * scale);
        } else {
            TextureRegion firstFrame = new TextureRegion(
                enemyType.getSpawnAnimation() != null
                    ? enemyType.getSpawnAnimation().getKeyFrame(0)
                    : enemyType.getWalkAnimation().getKeyFrame(0)
            );
            enemySprite = new Sprite(firstFrame);
            enemySprite.setSize(firstFrame.getRegionWidth() * 2, firstFrame.getRegionHeight() * scale);
        }

        enemySprite.setPosition(x, y);
        rect = new CollisionRect(x, y, enemySprite.getWidth()*2, enemySprite.getHeight()*2
        );
        if (enemyType.getSpawnAnimation() != null) {
            this.state = EnemyState.SPAWNING;
        } else {
            this.state = EnemyState.ALIVE;
        }

        this.enemyHealth = enemyType.getHP();
        this.x = x;
        this.y = y;
    }


    public EnemyType getEnemy() {
        return enemy;
    }

    public void setEnemy(EnemyType enemy) {
        this.enemy = enemy;
    }

    public Texture getEnemyTexture() {
        return enemyTexture;
    }

    public void setEnemyTexture(Texture enemyTexture) {
        this.enemyTexture = enemyTexture;
    }

    public Sprite getEnemySprite() {
        return enemySprite;
    }

    public void setEnemySprite(Sprite enemySprite) {
        this.enemySprite = enemySprite;
    }

    public float getEnemyHealth() {
        return enemyHealth;
    }

    public void setEnemyHealth(float enemyHealth) {
        this.enemyHealth = enemyHealth;
    }

    public CollisionRect getRect() {
        return rect;
    }

    public void setRect(CollisionRect rect) {
        this.rect = rect;
    }

    public float getTime() {
        return time;
    }

    public void setTime(float time) {
        this.time = time;
    }

    public boolean hasDealtDamage() {
        return hasDealtDamage;
    }

    public void setHasDealtDamage(boolean hasDealtDamage) {
        this.hasDealtDamage = hasDealtDamage;
    }

    public EnemyState getState() { return state; }
    public void setState(EnemyState state) { this.state = state; }

    public float getDeathAnimTime() { return deathAnimTime; }
    public void setDeathAnimTime(float t) { this.deathAnimTime = t; }

}
