package com.tilldawn.Control;

import com.badlogic.gdx.Gdx;
import com.tilldawn.Main;
import com.tilldawn.Model.GameAssetManager;
import com.tilldawn.Model.Pregame;
import com.tilldawn.View.MainMenuView;
import com.tilldawn.View.PreGameMenuView;

import java.util.Random;

public class MainMenuController {
    private MainMenuView view;

    public void setView(MainMenuView view) {
        this.view = view;
    }


    public String randomAvatarHandle() {
        int rand = new Random().nextInt(3);
        if (rand == 0) {
            return GameAssetManager.getGameAssetManager().getAvatar1();
        } else if (rand == 1) {
            return GameAssetManager.getGameAssetManager().getAvatar2();
        } else if (rand == 2) {
            return GameAssetManager.getGameAssetManager().getAvatar3();
        }  else {
            return GameAssetManager.getGameAssetManager().getAvatar4();
        }
    }
}
