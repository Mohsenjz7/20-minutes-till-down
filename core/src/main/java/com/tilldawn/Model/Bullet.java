package com.tilldawn.Model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.tilldawn.Model.Enums.WeaponType;

public class Bullet {
    private Texture texture = new Texture(GameAssetManager.getGameAssetManager().getBullet());
    private Sprite sprite = new Sprite(texture);
    private float damage ;
    private float velocityX;
    private float velocityY;
    private float speed =7;
    private CollisionRect rect ;

    public Bullet(float startX, float startY, float targetX, float targetY, float damage){
        sprite.setSize(20, 20);
        sprite.setPosition(startX-10, startY-10);
        this.damage = damage;
        float dx = targetX - startX;
        float dy = targetY - startY;
        float length = (float) Math.sqrt(dx * dx + dy * dy);
        rect = new CollisionRect(sprite.getX(), sprite.getY(), sprite.getWidth(), sprite.getHeight());
        velocityX = speed * (dx / length);
        velocityY = speed * (dy / length);
    }

    public void update() {
        sprite.translate(velocityX, velocityY);
        rect.move(sprite.getX(), sprite.getY());
    }

    public Sprite getSprite() {
        return sprite;
    }

    public float getDamage() {
        return damage;
    }

    public CollisionRect getRect() {
        return rect;
    }
}
