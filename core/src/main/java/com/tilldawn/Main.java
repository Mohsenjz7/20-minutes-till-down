package com.tilldawn;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.tilldawn.Control.LoginMenuController;
import com.tilldawn.Model.GameAssetManager;
import com.tilldawn.Model.Save.User;
import com.tilldawn.View.LoginMenuView;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends Game {
    private User currentUser;
    private static Main main;
    private static SpriteBatch batch;

    public static boolean SFX_ENABLED = true;
    public static float SFX_VOLUME = 1.0f;

    @Override
    public void create() {
        main = this;
        batch = new SpriteBatch();
//        getMain().setScreen(new MainMenuView(new MainMenuController(), GameAssetManager.getGameAssetManager().getSkin()));
        getMain().setScreen(new LoginMenuView(new LoginMenuController(),GameAssetManager.getGameAssetManager().getSkin(),this));
    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void dispose() {
        batch.dispose();
    }

    public static Main getMain() {
        return main;
    }

    public static void setMain(Main main) {
        Main.main = main;
    }

    public static SpriteBatch getBatch() {
        return batch;
    }

    public static void setBatch(SpriteBatch batch) {
        Main.batch = batch;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

}
