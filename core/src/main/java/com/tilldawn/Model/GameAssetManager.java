package com.tilldawn.Model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.tilldawn.Model.Save.User;

import java.io.*;
import java.util.ArrayList;

public class GameAssetManager {
    private static GameAssetManager gameAssetManager;
    private static final String FILE_PATH = "users.json";
    private final Skin skin = new Skin(Gdx.files.internal("skin/pixthulhu-ui.json"));
    private final String Avatar1 = "Avatar/Avatar1.jpg";
    private final String Avatar2= "Avatar/Avatar2.jpg";
    private final String Avatar3 = "Avatar/Avatar3.jpg";
    private final String Avatar4 = "Avatar/Avatar4.jpg";


    private final String cursorGame = "Icon/cursorGame.png";
    private final String cursorMenu = "Icon/cursorMenu.png";



    //ENEMY
    //TREE
    private final String Tree_0 = "Enemy/Tree/T_TreeMonster_0.png";
    private final String Tree_1 = "Enemy/Tree/T_TreeMonster_1.png";
    private final String Tree_2 = "Enemy/Tree/T_TreeMonster_2.png";
    private final Animation<Texture> TreeAnimation = new Animation<>(0.3f, new Texture(Tree_0),new Texture(Tree_1), new Texture(Tree_2));

    //TENTACLE
    //Spawn

    private final String Tentacle_0 = "Enemy/Tantacle/T_TentacleSpawn_0.png";
    private final String Tentacle_1 = "Enemy/Tantacle/T_TentacleSpawn_1.png";
    private final String Tentacle_2 = "Enemy/Tantacle/T_TentacleSpawn_2.png";
    private final String Tentacle_3 = "Enemy/Tantacle/TentacleSummonAttackIndicator.png";
    private final Animation<Texture> spawnAnimation = new Animation<>(0.2f,new Texture(Tentacle_3), new Texture(Tentacle_0), new Texture(Tentacle_1), new Texture(Tentacle_2));//


    //walk
    private final String Tentacle_0Attack = "Enemy/Tantacle/T_TentacleEnemy_0.png";
    private final String Tentacle_1Attack = "Enemy/Tantacle/T_TentacleEnemy_1.png";
    private final String Tentacle_2Attack = "Enemy/Tantacle/T_TentacleEnemy_2.png";
    private final String Tentacle_3Attack = "Enemy/Tantacle/T_TentacleEnemy_3.png";
    private final Animation<Texture> attack = new Animation<>(0.2f,new Texture(Tentacle_0Attack), new Texture(Tentacle_1Attack), new Texture(Tentacle_2Attack), new Texture(Tentacle_3Attack));



    // Revolver
    private final String RevolverStill = "Weapons/RevolverStill/RevolverStill.png";
    private final String RevolverReload_0 = "Weapons/RevolverReload/RevolverReload_0.png";
    private final String RevolverReload_1 = "Weapons/RevolverReload/RevolverReload_1.png";
    private final String RevolverReload_2 = "Weapons/RevolverReload/RevolverReload_2.png";
    private final String RevolverReload_3 = "Weapons/RevolverReload/RevolverReload_3.png";
    private final Animation<Texture> RevolverReloadAnimation = new Animation<>(0.1f, new Texture(RevolverReload_0), new Texture(RevolverReload_1), new Texture(RevolverReload_2), new Texture(RevolverReload_3) );

    // SMG
    private final String SMGStill = "Weapons/SMGStill/SMGStill.png";
    private final String SMGReload_0 = "Weapons/SMGReload/SMGReload_0.png";
    private final String SMGReload_1 = "Weapons/SMGReload/SMGReload_1.png";
    private final String SMGReload_2 = "Weapons/SMGReload/SMGReload_2.png";
    private final String SMGReload_3 = "Weapons/SMGReload/SMGReload_3.png";
    private final Animation<Texture> SMGReloadAnimation = new Animation<>(0.1f, new Texture(SMGReload_0), new Texture(SMGReload_1), new Texture(SMGReload_2), new Texture(SMGReload_3) );

    // Grenade Launcher
    private final String GrenadeLauncherStill = "Weapons/GrenadeLauncherStill/GrenadeLauncherStill.png";
    private final String GrenadeLauncherReload_0 = "Weapons/GrenadeLauncherReload/GrenadeLauncherReload_0.png";
    private final String GrenadeLauncherReload_1 = "Weapons/GrenadeLauncherReload/GrenadeLauncherReload_1.png";
    private final String GrenadeLauncherReload_2 = "Weapons/GrenadeLauncherReload/GrenadeLauncherReload_2.png";
    private final Animation<Texture> GrenadeLauncherReloadAnimation = new Animation<>(0.1f,new Texture(GrenadeLauncherReload_0),new Texture(GrenadeLauncherReload_1), new Texture(GrenadeLauncherReload_2));


    private final String dasher_Run0 = "Hero/Dasher/run/Run_0.png";
    private final String dasher_Run1 = "Hero/Dasher/run/Run_1.png";
    private final String dasher_Run2 = "Hero/Dasher/run/Run_2.png";
    private final String dasher_Run3 = "Hero/Dasher/run/Run_3.png";
    private final Animation<Texture> dasher_Run_anim = new Animation<>(0.1f,
        new Texture(dasher_Run0),
        new Texture(dasher_Run1),
        new Texture(dasher_Run2),
        new Texture(dasher_Run3)
    );

    // DIAMOND
    private final String diamond_Run0 = "Hero/Diamond/run/Run_0.png";
    private final String diamond_Run1 = "Hero/Diamond/run/Run_1.png";
    private final String diamond_Run2 = "Hero/Diamond/run/Run_2.png";
    private final String diamond_Run3 = "Hero/Diamond/run/Run_3.png";
    private final Animation<Texture> diamond_Run_anim = new Animation<>(0.1f,
        new Texture(diamond_Run0),
        new Texture(diamond_Run1),
        new Texture(diamond_Run2),
        new Texture(diamond_Run3)
    );

    // LILITH
    private final String lilith_Run0 = "Hero/Lilith/run/Run_0.png";
    private final String lilith_Run1 = "Hero/Lilith/run/Run_1.png";
    private final String lilith_Run2 = "Hero/Lilith/run/Run_2.png";
    private final String lilith_Run3 = "Hero/Lilith/run/Run_3.png";
    private final Animation<Texture> lilith_Run_anim = new Animation<>(0.1f,
        new Texture(lilith_Run0),
        new Texture(lilith_Run1),
        new Texture(lilith_Run2),
        new Texture(lilith_Run3)
    );

    // SCARLET
    private final String scarlet_Run0 = "Hero/Scarlet/run/Run_0.png";
    private final String scarlet_Run1 = "Hero/Scarlet/run/Run_1.png";
    private final String scarlet_Run2 = "Hero/Scarlet/run/Run_2.png";
    private final String scarlet_Run3 = "Hero/Scarlet/run/Run_3.png";
    private final Animation<Texture> scarlet_Run_anim = new Animation<>(0.1f,
        new Texture(scarlet_Run0),
        new Texture(scarlet_Run1),
        new Texture(scarlet_Run2),
        new Texture(scarlet_Run3)
    );

    // SHANA
    private final String shana_Run0 = "Hero/Shana/run/Run_0.png";
    private final String shana_Run1 = "Hero/Shana/run/Run_1.png";
    private final String shana_Run2 = "Hero/Shana/run/Run_2.png";
    private final String shana_Run3 = "Hero/Shana/run/Run_3.png";
    private final Animation<Texture> shana_Run_anim = new Animation<>(0.1f,
        new Texture(shana_Run0),
        new Texture(shana_Run1),
        new Texture(shana_Run2),
        new Texture(shana_Run3)
    );


    // DASher
    private final String dasher_idle0 = "Hero/Dasher/idle/Idle_0.png";
    private final String dasher_idle1 = "Hero/Dasher/idle/Idle_1.png";
    private final String dasher_idle2 = "Hero/Dasher/idle/Idle_2.png";
    private final String dasher_idle3 = "Hero/Dasher/idle/Idle_3.png";
    private final String dasher_idle4 = "Hero/Dasher/idle/Idle_4.png";
    private final String dasher_idle5 = "Hero/Dasher/idle/Idle_5.png";
    private final Animation<Texture> dasher_idle_anim = new Animation<>(0.1f,
        new Texture(dasher_idle0),
        new Texture(dasher_idle1),
        new Texture(dasher_idle2),
        new Texture(dasher_idle3),
        new Texture(dasher_idle4),
        new Texture(dasher_idle5)
    );



    // DIAMOND
    private final String diamond_idle0 = "Hero/Diamond/idle/Idle_0.png";
    private final String diamond_idle1 = "Hero/Diamond/idle/Idle_1.png";
    private final String diamond_idle2 = "Hero/Diamond/idle/Idle_2.png";
    private final String diamond_idle3 = "Hero/Diamond/idle/Idle_3.png";
    private final String diamond_idle4 = "Hero/Diamond/idle/Idle_4.png";
    private final String diamond_idle5 = "Hero/Diamond/idle/Idle_5.png";
    private final Animation<Texture> diamond_idle_anim = new Animation<>(0.1f,
        new Texture(diamond_idle0),
        new Texture(diamond_idle1),
        new Texture(diamond_idle2),
        new Texture(diamond_idle3),
        new Texture(diamond_idle4),
        new Texture(diamond_idle5)
    );

    // LILITH
    private final String lilith_idle0 = "Hero/Lilith/idle/Idle_0.png";
    private final String lilith_idle1 = "Hero/Lilith/idle/Idle_1.png";
    private final String lilith_idle2 = "Hero/Lilith/idle/Idle_2.png";
    private final String lilith_idle3 = "Hero/Lilith/idle/Idle_3.png";
    private final String lilith_idle4 = "Hero/Lilith/idle/Idle_4.png";
    private final String lilith_idle5 = "Hero/Lilith/idle/Idle_5.png";
    private final Animation<Texture> lilith_idle_anim = new Animation<>(0.1f,
        new Texture(lilith_idle0),
        new Texture(lilith_idle1),
        new Texture(lilith_idle2),
        new Texture(lilith_idle3),
        new Texture(lilith_idle4),
        new Texture(lilith_idle5)
    );

    // SCARLET
    private final String scarlet_idle0 = "Hero/Scarlet/idle/Idle_0.png";
    private final String scarlet_idle1 = "Hero/Scarlet/idle/Idle_1.png";
    private final String scarlet_idle2 = "Hero/Scarlet/idle/Idle_2.png";
    private final String scarlet_idle3 = "Hero/Scarlet/idle/Idle_3.png";
    private final String scarlet_idle4 = "Hero/Scarlet/idle/Idle_4.png";
    private final String scarlet_idle5 = "Hero/Scarlet/idle/Idle_5.png";
    private final Animation<Texture> scarlet_idle_anim = new Animation<>(0.1f,
        new Texture(scarlet_idle0),
        new Texture(scarlet_idle1),
        new Texture(scarlet_idle2),
        new Texture(scarlet_idle3),
        new Texture(scarlet_idle4),
        new Texture(scarlet_idle5)
    );

    // SHANA
    private final String shana_idle0 = "Hero/Shana/idle/Idle_0.png";
    private final String shana_idle1 = "Hero/Shana/idle/Idle_1.png";
    private final String shana_idle2 = "Hero/Shana/idle/Idle_2.png";
    private final String shana_idle3 = "Hero/Shana/idle/Idle_3.png";
    private final String shana_idle4 = "Hero/Shana/idle/Idle_4.png";
    private final String shana_idle5 = "Hero/Shana/idle/Idle_5.png";
    private final Animation<Texture> shana_idle_anim = new Animation<>(0.1f,
        new Texture(shana_idle0),
        new Texture(shana_idle1),
        new Texture(shana_idle2),
        new Texture(shana_idle3),
        new Texture(shana_idle4),
        new Texture(shana_idle5)
    );


    private final String backGroundImage = "BackGroundMenu.jpg";
    private final String backgroundImage1 = "back11.PNG";

    private final Texture gearTexture = new Texture(Gdx.files.internal("Icon/White/2x/gear.png"));
    private final Texture exitToDesktop = new Texture(Gdx.files.internal("Icon/White/2x/exitRight.png"));
    private final Texture audioOn = new Texture(Gdx.files.internal("Icon/White/2x/audioOn.png"));

    private final String smg = "smg/SMGStill.png";
    private final Texture smgTexture = new Texture(smg);

    private final String bullet = "bullet.png";

    private final String haloMusic = "music/Halo.mp3";
    private final String mainMusic = "music/Low Life.mp3";
    private final String MetroMusic = "music/Metro.mp3";
    private final String SpiderMusic = "music/Spider.mp3";



    private GameAssetManager(){

    }

    public static GameAssetManager getGameAssetManager(){
        if (gameAssetManager == null){
            gameAssetManager = new GameAssetManager();
        }
        return gameAssetManager;
    }

    public Skin getSkin() {
        return skin;
    }


    public Texture getSmgTexture(){
        return smgTexture;
    }

    public String getSmg(){
        return smg;
    }

    public String getBullet(){
        return bullet;
    }

    public String getBackGroundImage() {
        return backGroundImage;
    }

    public static ArrayList<User> getUsers() {
        Gson gson = new Gson();
        ArrayList<User> tmpUsers = new ArrayList<>();
        try (Reader reader = new FileReader(FILE_PATH)) {
            tmpUsers = gson.fromJson(reader, new TypeToken<ArrayList<User>>() {
            }.getType());
        } catch (FileNotFoundException e) {
            tmpUsers = new ArrayList<>();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return tmpUsers;
    }

    public static void addUser(User user) {
        Gson gson = new Gson();
        ArrayList<User> tmpUsers = new ArrayList<>();
        try (Reader reader = new FileReader(FILE_PATH)) {
            tmpUsers = gson.fromJson(reader, new TypeToken<ArrayList<User>>() {}.getType());
            if (tmpUsers == null) {
                tmpUsers = new ArrayList<>();
            }
        } catch (FileNotFoundException e) {
            tmpUsers = new ArrayList<>();
        } catch (IOException e) {
            e.printStackTrace();
        }

        tmpUsers.add(user);

        try (FileWriter writer = new FileWriter(FILE_PATH)) {
            gson.toJson(tmpUsers, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void removeUser(User user) {
        Gson gson = new Gson();
        ArrayList<User> tmpUsers = new ArrayList<>();
        try (Reader reader = new FileReader(FILE_PATH)) {
            tmpUsers = gson.fromJson(reader, new TypeToken<ArrayList<User>>() {
            }.getType());
        } catch (FileNotFoundException e) {
            tmpUsers = new ArrayList<>();
        } catch (IOException e) {
            e.printStackTrace();
        }
        tmpUsers.remove(user);
        try (FileWriter writer = new FileWriter(FILE_PATH)) {
            gson.toJson(tmpUsers, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static User getUserByUsername(String username) {
        if(getUsers()==null){
            return null;
        }
        for(User user : getUsers()){
            if(user.getUserName().equals(username)){
                return user;
            }
        }
        return null;
    }

    public String getAvatar1() {
        return Avatar1;
    }

    public String getAvatar2() {
        return Avatar2;
    }

    public String getAvatar3() {
        return Avatar3;
    }

    public String getAvatar4() {
        return Avatar4;
    }

    public Texture getGearTexture() {
        return gearTexture;
    }

    public Texture getExitToDesktop() {
        return exitToDesktop;
    }

    public Texture getAudioOn() {
        return audioOn;
    }

    public String getHaloMusic() {
        return haloMusic;
    }

    public String getMainMusic() {
        return mainMusic;
    }

    public String getSpiderMusic() {
        return SpiderMusic;
    }

    public String getBackgroundImage1() {
        return backgroundImage1;
    }

    public Animation<Texture> getDasher_idle_anim() {
        return dasher_idle_anim;
    }

    public Animation<Texture> getDiamond_idle_anim() {
        return diamond_idle_anim;
    }

    public Animation<Texture> getLilith_idle_anim() {
        return lilith_idle_anim;
    }

    public Animation<Texture> getScarlet_idle_anim() {
        return scarlet_idle_anim;
    }

    public Animation<Texture> getShana_idle_anim() {
        return shana_idle_anim;
    }

    public String getDasher_idle0() {
        return dasher_idle0;
    }

    public String getDiamond_idle0() {
        return diamond_idle0;
    }

    public String getLilith_idle0() {
        return lilith_idle0;
    }

    public String getScarlet_idle0() {
        return scarlet_idle0;
    }

    public String getShana_idle0() {
        return shana_idle0;
    }

    public String getRevolverStill() {
        return RevolverStill;
    }

    public Animation<Texture> getRevolverReloadAnimation() {
        return RevolverReloadAnimation;
    }

    public String getRevolverReload_0() {
        return RevolverReload_0;
    }

    public String getSMGStill() {
        return SMGStill;
    }

    public Animation<Texture> getSMGReloadAnimation() {
        return SMGReloadAnimation;
    }

    public Animation<Texture> getGrenadeLauncherReloadAnimation() {
        return GrenadeLauncherReloadAnimation;
    }

    public String getGrenadeLauncherStill() {
        return GrenadeLauncherStill;
    }

    public String getCursorGame() {
        return cursorGame;
    }

    public String getCursorMenu() {
        return cursorMenu;
    }

    public Animation<Texture> getDasher_Run_anim() {
        return dasher_Run_anim;
    }

    public Animation<Texture> getDiamond_Run_anim() {
        return diamond_Run_anim;
    }

    public Animation<Texture> getLilith_Run_anim() {
        return lilith_Run_anim;
    }

    public Animation<Texture> getScarlet_Run_anim() {
        return scarlet_Run_anim;
    }

    public Animation<Texture> getShana_Run_anim() {
        return shana_Run_anim;
    }

    public Animation<Texture> getTreeAnimation() {
        return TreeAnimation;
    }

    public String getTree_0() {
        return Tree_0;
    }

    public Animation<Texture> getSpawnAnimation() {
        return spawnAnimation;
    }

    public Animation<Texture> getAttack() {
        return attack;
    }
}
