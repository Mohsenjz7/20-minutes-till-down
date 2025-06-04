package com.tilldawn.Model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class XP {
    private final Texture texture = new Texture(GameAssetManager.getGameAssetManager().getXp());
    private Sprite sprite = new Sprite(texture);
    private CollisionRect rect;

    public XP(float x, float y) {
        sprite.setSize(30, 30);
        sprite.setPosition(x, y);
        rect = new CollisionRect(x, y, sprite.getWidth(), sprite.getHeight());
    }

    public void updateRect() {
        rect.move(sprite.getX(), sprite.getY());
    }

    public Sprite getSprite() {
        return sprite;
    }

    public CollisionRect getRect() {
        return rect;
    }
}
