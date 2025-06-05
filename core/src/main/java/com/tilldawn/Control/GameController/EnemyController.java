package com.tilldawn.Control.GameController;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.tilldawn.Main;
import com.tilldawn.Model.Bullet;
import com.tilldawn.Model.Enemy;
import com.tilldawn.Model.Enums.EnemyType;
import com.tilldawn.Model.GameAssetManager;
import com.tilldawn.Model.XP;

import java.util.ArrayList;
import java.util.Random;


public class EnemyController {
    private ArrayList<Enemy> enemies = new ArrayList<>();
    private float spawnRate = 0;
    private float lastTentacleSpawnTime = 0;
    private float lastShooterSpawnTime = 0f;
    private float shooterBulletTimer = 0f;




    public void update() {
        if (spawnRate == 0) {
            spawnTree();
            spawnTentacleMonster(5);
        }
        if (spawnRate - lastTentacleSpawnTime >= 10f) {
            int count = (int) (spawnRate / 10);
            spawnTentacleMonster(count);
            lastTentacleSpawnTime = spawnRate;
        }
        if (spawnRate >= 4f && spawnRate - lastShooterSpawnTime >= 10f) {
            int i = (int) spawnRate;
            int shooterCount = Math.min((int) (spawnRate / 15), 2);
            spawnShooterEnemies(shooterCount);
            lastShooterSpawnTime = spawnRate;
        }
        shooterBulletTimer += Gdx.graphics.getDeltaTime();

        if (shooterBulletTimer >= 3f) {
            shootBulletsFromShooters();
            shooterBulletTimer = 0f;
        }

        updateBullets();
        treeAnimation();
        treeDamage();
        tentacleDamage();
        spawnRate += Gdx.graphics.getDeltaTime();
        checkBulletCollisions();
        updateEnemies();
    }


    public void updateBullets() {
        for (Bullet bullet : PlayerController.getBulletList()) {
            bullet.update();
            bullet.getSprite().draw(Main.getBatch());
        }
    }


    private void spawnTentacleMonster(int count) {
        Random rand = new Random();
        for (int i = 0; i < count; i++) {
            int x = rand.nextInt(0, 3600);
            int y = rand.nextInt(0, 2500);
            while ((Math.abs(PlayerController.getPlayer().getPlayerSprite().getY() - y) < 250 &&
                Math.abs(PlayerController.getPlayer().getPlayerSprite().getX() - x) < 200)) {
                x = rand.nextInt(0, 3600);
                y = rand.nextInt(0, 2500);
            }
            Enemy e = new Enemy(EnemyType.Tentacle, x, y);
            e.setState(Enemy.EnemyState.SPAWNING);
            enemies.add(e);
        }
    }

    public void treeAnimation() {
        for (Enemy e : enemies) {
            if (e.getEnemy().equals(EnemyType.Tree)) {
                if (Math.abs(e.getEnemySprite().getX() - PlayerController.getPlayer().getPlayerSprite().getX()) < 200 && Math.abs(e.getEnemySprite().getY() - PlayerController.getPlayer().getPlayerSprite().getY()) < 200) {
                    Animation<Texture> animation = e.getEnemy().getWalkAnimation();

                    TextureRegion frame = new TextureRegion(animation.getKeyFrame(e.getTime(), false));

                    e.getEnemySprite().setRegion(frame);
                    e.setTime(e.getTime() + Gdx.graphics.getDeltaTime());
                } else {
                    TextureRegion frame = new TextureRegion(e.getEnemyTexture());
                    e.getEnemySprite().setRegion(frame);
                }
            }
        }
    }

    public void treeDamage() {
        for (Enemy e : enemies) {
            if (e.getEnemy().equals(EnemyType.Tree)) {
                float dx = Math.abs(e.getEnemySprite().getX() - PlayerController.getPlayer().getPlayerSprite().getX());
                float dy = Math.abs(e.getEnemySprite().getY() - PlayerController.getPlayer().getPlayerSprite().getY());

                boolean isNear = dx < 200 && dy < 200;
                if (isNear && !e.hasDealtDamage()) {
                    PlayerController.getPlayer().setPlayerHealth(PlayerController.getPlayer().getPlayerHealth() - 0.1f);
                    e.setHasDealtDamage(true);
                } else if (!isNear) {
                    e.setHasDealtDamage(false);
                }
            }
        }
    }

    public void tentacleDamage() {
        for (Enemy e : enemies) {
            if (e.getEnemy().equals(EnemyType.Tentacle)) {
                float dx = Math.abs(e.getEnemySprite().getX() - PlayerController.getPlayer().getPlayerSprite().getX());
                float dy = Math.abs(e.getEnemySprite().getY() - PlayerController.getPlayer().getPlayerSprite().getY());

                boolean isNear = dx < 50 && dy < 50;
                if (isNear && !e.hasDealtDamage()) {
                    PlayerController.getPlayer().setPlayerHealth(PlayerController.getPlayer().getPlayerHealth() - 0.2f);
                    e.setHasDealtDamage(true);
                } else if (!isNear) {
                    e.setHasDealtDamage(false);
                }
            }
        }
    }


    public void updateEnemies() {
        ArrayList<Enemy> enemiesToRemove = new ArrayList<>();

        for (Enemy e : enemies) {
            switch (e.getState()) {
                case SPAWNING:
                    Animation<Texture> spawnAnim = e.getEnemy().getSpawnAnimation();
                    TextureRegion spawnFrame = new TextureRegion(spawnAnim.getKeyFrame(e.getTime(), false));
                    e.getEnemySprite().setRegion(spawnFrame);
                    e.setTime(e.getTime() + Gdx.graphics.getDeltaTime());

                    if (spawnAnim.isAnimationFinished(e.getTime())) {
                        e.setTime(0);
                        e.setState(Enemy.EnemyState.ALIVE);
                    }
                    break;

                case ALIVE:
                    if (e.getEnemy().equals(EnemyType.Tentacle)||e.getEnemy().equals(EnemyType.EyeBat)) {
                        float px = PlayerController.getPlayer().getPlayerSprite().getX();
                        float py = PlayerController.getPlayer().getPlayerSprite().getY();
                        float ex = e.getEnemySprite().getX();
                        float ey = e.getEnemySprite().getY();

                        float dx = px - ex;
                        float dy = py - ey;

                        float dist = (float) Math.sqrt(dx * dx + dy * dy);
                        if (dist > 0.1f) {
                            float speed = 100 * Gdx.graphics.getDeltaTime();
                            e.getEnemySprite().setX(ex + speed * dx / dist);
                            e.getEnemySprite().setY(ey + speed * dy / dist);
                        }

                        Animation<Texture> walkAnim = e.getEnemy().getWalkAnimation();
                        TextureRegion walkFrame = new TextureRegion(walkAnim.getKeyFrame(e.getTime(), true));
                        e.getEnemySprite().setRegion(walkFrame);
                        e.setTime(e.getTime() + Gdx.graphics.getDeltaTime());
                    }
                    break;

                case DYING:
                    Animation<Texture> deathAnim = GameAssetManager.getGameAssetManager().getDeathAnimation();
                    TextureRegion deathFrame = new TextureRegion(deathAnim.getKeyFrame(e.getDeathAnimTime(), false));
                    e.getEnemySprite().setRegion(deathFrame);
                    e.setDeathAnimTime(e.getDeathAnimTime() + Gdx.graphics.getDeltaTime());

                    if (deathAnim.isAnimationFinished(e.getDeathAnimTime())) {
                        if (e.getEnemy().equals(EnemyType.Tree)) {
                            PlayerController.getXpList().add(new XP(e.getEnemySprite().getX(), e.getEnemySprite().getY()));

                        }
                        if (e.getEnemy().equals(EnemyType.Tentacle)) {
                            PlayerController.getXpList().add(new XP(e.getEnemySprite().getX(), e.getEnemySprite().getY()));
                            PlayerController.getXpList().add(new XP(e.getEnemySprite().getX() + 15, e.getEnemySprite().getY()));
                        }

                        enemiesToRemove.add(e);
                    }
                    break;
            }

            e.getEnemySprite().draw(Main.getBatch());
            e.getRect().move(e.getEnemySprite().getX(), e.getEnemySprite().getY());
        }

        enemies.removeAll(enemiesToRemove);
    }


    private void spawnTree() {
        spawnRate = 0;
        Random rand = new Random();
        int randomNum = rand.nextInt(15, 30);
        for (int i = 0; i < randomNum; i++) {
            int x = rand.nextInt(0, 3600);
            int y = rand.nextInt(0, 2500);
            while ((Math.abs(PlayerController.getPlayer().getPlayerSprite().getY() - y) < 250 && Math.abs(PlayerController.getPlayer().getPlayerSprite().getX() - x) < 200)) {
                x = rand.nextInt(0, 3600);
                y = rand.nextInt(0, 2500);
            }
            enemies.add(new Enemy(EnemyType.Tree, x, y));

        }
    }

    public ArrayList<Enemy> getEnemies() {
        return enemies;
    }

    private void checkBulletCollisions() {
        ArrayList<Bullet> bullets = WeaponController.getBullets();
        ArrayList<Bullet> bulletsToRemove = new ArrayList<>();
        ArrayList<Enemy> enemiesToRemove = new ArrayList<>();

        for (Bullet bullet : bullets) {
            for (Enemy enemy : enemies) {
                if (enemy.getRect().collidesWith(bullet.getRect())) {
                    Texture hit = new Texture(GameAssetManager.getGameAssetManager().getDeath4());
                    enemy.getEnemySprite().setRegion(hit);
                    enemy.setEnemyHealth(enemy.getEnemyHealth() - bullet.getDamage());
                    bulletsToRemove.add(bullet);
                    if (enemy.getEnemyHealth() <= 0) {
                        PlayerController.getPlayer().setKill(PlayerController.getPlayer().getKill() + 1);
                        enemy.setState(Enemy.EnemyState.DYING);
                        enemy.setDeathAnimTime(0);
                    }

                    break;
                }
            }
        }

        bullets.removeAll(bulletsToRemove);
        enemies.removeAll(enemiesToRemove);
    }
    private void spawnShooterEnemies(int count) {
        Random rand = new Random();
        for (int i = 0; i < count; i++) {
            int x = rand.nextInt(0, 3600);
            int y = rand.nextInt(0, 2500);
            while ((Math.abs(PlayerController.getPlayer().getPlayerSprite().getY() - y) < 250 &&
                Math.abs(PlayerController.getPlayer().getPlayerSprite().getX() - x) < 200)) {
                x = rand.nextInt(0, 3600);
                y = rand.nextInt(0, 2500);
            }
            Enemy e = new Enemy(EnemyType.EyeBat, x, y);
            e.setState(Enemy.EnemyState.ALIVE);
            enemies.add(e);
        }
    }
    private void shootBulletsFromShooters() {
        for (Enemy e : enemies) {
            if (e.getEnemy().equals(EnemyType.EyeBat) && e.getState() == Enemy.EnemyState.ALIVE) {
                float sx = e.getEnemySprite().getX();
                float sy = e.getEnemySprite().getY();
                float px = PlayerController.getPlayer().getPlayerSprite().getX();
                float py = PlayerController.getPlayer().getPlayerSprite().getY();

                Bullet b = new Bullet(sx, sy, px, py, 5);
                PlayerController.getBulletList().add(b);
            }
        }
    }

}
