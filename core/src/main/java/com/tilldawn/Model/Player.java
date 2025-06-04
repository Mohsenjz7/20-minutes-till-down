package com.tilldawn.Model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.tilldawn.Model.Enums.Ability;
import com.tilldawn.Model.Enums.HeroType;

public class Player {
    private HeroType hero;
    private Texture playerTexture;
    private Sprite playerSprite;
    private float playerHealth;
    private CollisionRect rect ;
    private Ability ability;
    private int maxHealth ;
    private float time = 0;
    private float speed;
    private int xp;
    private int level;
    private boolean facingLeft;
    private int zarib = 0;
    private boolean isPlayerIdle = true;
    private boolean isPlayerRunning = false;

    public Player(HeroType hero){
        this.hero = hero;playerTexture = new Texture(Gdx.files.internal(hero.getTEXTURE()));
        playerSprite = new Sprite(playerTexture);
        maxHealth = hero.getHP();

        playerHealth = hero.getHP();
        speed = hero.getSPEED();
        playerSprite.setPosition((float) Gdx.graphics.getWidth() / 2, (float) Gdx.graphics.getHeight() / 2);
        playerSprite.setSize(playerTexture.getWidth() * 3, playerTexture.getHeight() * 3);
        rect = new CollisionRect((float) Gdx.graphics.getWidth() / 2, (float) Gdx.graphics.getHeight()/2, playerTexture.getWidth() * 3, playerTexture.getHeight() * 3);
        level = 1;
        xp = 0;
    }


    public void setFacingLeft(boolean facingLeft) {
        this.facingLeft = facingLeft;
    }

    public boolean isFacingLeft() {
        return facingLeft;
    }
    public float getSpeed() {
        return speed;
    }
    public Texture getPlayerTexture() {
        return playerTexture;
    }

    public void setPlayerTexture(Texture playerTexture) {
        this.playerTexture = playerTexture;
    }

    public Sprite getPlayerSprite() {
        return playerSprite;
    }

    public void setPlayerSprite(Sprite playerSprite) {
        this.playerSprite = playerSprite;
    }

    public float getPlayerHealth() {
        return playerHealth;
    }

    public void setPlayerHealth(float playerHealth) {
        this.playerHealth = playerHealth;
    }

    public CollisionRect getRect() {
        return rect;
    }

    public void setRect(CollisionRect rect) {
        this.rect = rect;
    }

    public boolean isPlayerIdle() {
        return isPlayerIdle;
    }

    public void setPlayerIdle(boolean playerIdle) {
        isPlayerIdle = playerIdle;
    }

    public boolean isPlayerRunning() {
        return isPlayerRunning;
    }

    public void setPlayerRunning(boolean playerRunning) {
        isPlayerRunning = playerRunning;
    }

    public float getTime() {
        return time;
    }

    public void setTime(float time) {
        this.time = time;
    }

    public HeroType getHero() {
        return hero;
    }

    public void setHero(HeroType hero) {
        this.hero = hero;
    }


    public int getXp() {
        return xp;
    }

    public void setXp(int xp) {
        this.xp = xp;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public Ability getAbility() {
        return ability;
    }

    public void setAbility(Ability ability) {
        this.ability = ability;
    }

    public int getZarib() {
        return zarib;
    }

    public void setZarib(int zarib) {
        this.zarib = zarib;
    }

    public enum PlayerState {
        IDLE, RUNNING
    }

    private PlayerState state = PlayerState.IDLE;

    public PlayerState getState() {
        return state;
    }

    public void setState(PlayerState state) {
        this.state = state;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }


    public void addHealth(float health) {
        if (health+playerHealth > maxHealth) {
            playerHealth = maxHealth;
        }else{
            playerHealth += health;
        }
    }
    public void update() {
        rect.move(playerSprite.getX(), playerSprite.getY());
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public void setMaxHealth(int maxHealth) {
        this.maxHealth = maxHealth;
    }

}
