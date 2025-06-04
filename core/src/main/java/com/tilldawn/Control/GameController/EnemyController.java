package com.tilldawn.Control.GameController;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.tilldawn.Main;
import com.tilldawn.Model.Bullet;
import com.tilldawn.Model.Enemy;
import com.tilldawn.Model.Enums.EnemyType;

import java.util.ArrayList;
import java.util.Random;

public class EnemyController {
    private ArrayList<Enemy> enemies = new ArrayList<>();
    private float spawnRate = 0;
    private boolean nearEnemies = false;

    public void update(){
        if(spawnRate == 0){
            spawnTree();
        }
        int i = (int)spawnRate;
        treeAnimation();
        treeDamage();
        spawnRate += Gdx.graphics.getDeltaTime();
        checkBulletCollisions();
        updateEnemies();

    }



    public void treeAnimation(){
        for(Enemy e: enemies){
            if(e.getEnemy().equals(EnemyType.Tree)){
                if(Math.abs(e.getEnemySprite().getX() - PlayerController.getPlayer().getPlayerSprite().getX()) < 200&& Math.abs(e.getEnemySprite().getY() - PlayerController.getPlayer().getPlayerSprite().getY()) < 200 ){
                    Animation<Texture> animation = e.getEnemy().getWalkAnimation();

                    TextureRegion frame = new TextureRegion(animation.getKeyFrame(e.getTime(),false));

                    e.getEnemySprite().setRegion(frame);
                    e.setTime(e.getTime() + Gdx.graphics.getDeltaTime());
                }else {
                    TextureRegion frame = new TextureRegion(e.getEnemyTexture());
                    e.getEnemySprite().setRegion(frame);
                }
            }
        }
    }
    public void treeDamage(){
        for(Enemy e: enemies){
            if(e.getEnemy().equals(EnemyType.Tree)){
                float dx = Math.abs(e.getEnemySprite().getX() - PlayerController.getPlayer().getPlayerSprite().getX());
                float dy = Math.abs(e.getEnemySprite().getY() - PlayerController.getPlayer().getPlayerSprite().getY());

                boolean isNear = dx < 200 && dy < 200;
                if(isNear && !e.hasDealtDamage()) {
                    PlayerController.getPlayer().setPlayerHealth(PlayerController.getPlayer().getPlayerHealth() - 0.1f);
                    e.setHasDealtDamage(true);
                } else if (!isNear) {
                    e.setHasDealtDamage(false);
                }
            }
        }
    }


    public void updateEnemies() {
        for (Enemy enemy : enemies) {
            enemy.getEnemySprite().draw(Main.getBatch());
        }
    }

    private void spawnTentacleMonster(int i){

    }

    private void spawnTree(){
        spawnRate = 0;
        Random rand = new Random();
        int randomNum = rand.nextInt(15,30);
        for(int i = 0; i < randomNum; i++){
            int x = rand.nextInt(0,3600);
            int y  = rand.nextInt(0,2500);
            while((Math.abs(PlayerController.getPlayer().getPlayerSprite().getY()-y) < 250 && Math.abs(PlayerController.getPlayer().getPlayerSprite().getX()-x) <200)){
                x = rand.nextInt(0,3600);
                y = rand.nextInt(0,2500);
            }
            enemies.add(new Enemy(EnemyType.Tree,x,y));
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
                    enemy.setEnemyHealth(enemy.getEnemyHealth() - bullet.getDamage());
                    bulletsToRemove.add(bullet);
                    if (enemy.getEnemyHealth() <= 0) {
                        enemiesToRemove.add(enemy);
                    }
                    break;
                }
            }
        }

        bullets.removeAll(bulletsToRemove);
        enemies.removeAll(enemiesToRemove);
    }

}
