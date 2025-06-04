package com.tilldawn.View;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Cursor;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.tilldawn.Control.LoginMenuController;
import com.tilldawn.Control.MainMenuController;
import com.tilldawn.Control.ProfileMenuController;
import com.tilldawn.Control.SettingMenuController;
import com.tilldawn.Main;
import com.tilldawn.Model.GameAssetManager;
import com.tilldawn.Model.Save.User;


public class ProfileMenuView implements Screen {
    public Table table;
    private ProfileMenuController controller;
    private final Label error;
    private Stage stage;
    private final Main main;
    private final Skin skin;
    private TextField newUserName = new TextField("" , GameAssetManager.getGameAssetManager().getSkin());
    private TextField newPassWord = new TextField("", GameAssetManager.getGameAssetManager().getSkin());
    private TextButton changeButtonUsername = new TextButton("Change Username", GameAssetManager.getGameAssetManager().getSkin() , "default");
    private TextButton changeButtonPassword = new TextButton("Change Password", GameAssetManager.getGameAssetManager().getSkin() , "default");
    private TextButton logoutButton = new TextButton("Logout", GameAssetManager.getGameAssetManager().getSkin() , "default");
    private TextButton deleteAccount = new TextButton("Delete Account",GameAssetManager.getGameAssetManager().getSkin() , "default");
    private TextButton avatarButton = new TextButton("Change Avatar",GameAssetManager.getGameAssetManager().getSkin() , "default");
    private TextButton backButton = new TextButton("Back", GameAssetManager.getGameAssetManager().getSkin(), "default");

    ProfileMenuView(ProfileMenuController profileMenuController, Skin skin, Main main) {
        this.controller = profileMenuController;
        this.skin = skin;
        this.main = main;
        error = new Label("", skin);
        controller.setView(this);
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        Pixmap pixmap = new Pixmap(Gdx.files.internal(GameAssetManager.getGameAssetManager().getCursorMenu()));
        Cursor customCursor = Gdx.graphics.newCursor(pixmap, 0, 0);
        Gdx.graphics.setCursor(customCursor);

        Texture avatarTexture = new Texture(Gdx.files.internal(main.getCurrentUser() == null ? controller.randomAvatarHandle() : main.getCurrentUser().getImage()));
        Image avatarImage = new Image(avatarTexture);
        avatarImage.setSize(100, 100);
        avatarImage.setPosition(20, Gdx.graphics.getHeight() - 120);
        stage.addActor(avatarImage);

        Label usernameLabel = new Label("Username: " + (main.getCurrentUser() == null ? "Guest" : main.getCurrentUser().getUserName()), skin);
        usernameLabel.setPosition(130, Gdx.graphics.getHeight() - 60);
        stage.addActor(usernameLabel);

        Label scoreLabel = new Label("Score: " + (main.getCurrentUser() == null ? 0 : main.getCurrentUser().getRate()), skin);
        scoreLabel.setPosition(130, Gdx.graphics.getHeight() - 90);
        stage.addActor(scoreLabel);

        float centerX = Gdx.graphics.getWidth() / 2f;
        float centerY = Gdx.graphics.getHeight() / 2f;
        float spacing = 120;

        float btnWidth = 600, btnHeight = 100;

        newUserName.setSize(btnWidth, btnHeight);
        changeButtonUsername.setSize(btnWidth, btnHeight);
        changeButtonUsername.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {


            }
        });

        newPassWord.setSize(btnWidth, btnHeight);
        changeButtonPassword.setSize(btnWidth, btnHeight);
        avatarButton.setSize(btnWidth, btnHeight);
        deleteAccount.setSize(btnWidth, btnHeight);
        logoutButton.setSize(btnWidth, btnHeight);
        backButton.setSize(btnWidth, btnHeight);
        error.setPosition(10,60);


        newUserName.setPosition(centerX - btnWidth / 2, centerY + spacing * 3);
        changeButtonUsername.setPosition(centerX - btnWidth / 2, centerY + spacing * 2);
        changeButtonUsername.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.changeUsername(newUserName.getText(),main);
            }
        });
        newPassWord.setPosition(centerX - btnWidth / 2, centerY + spacing);
        changeButtonPassword.setPosition(centerX - btnWidth / 2, centerY);
        changeButtonPassword.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.changePassword(newPassWord.getText(),main);
            }
        });
        avatarButton.setPosition(centerX - btnWidth / 2, centerY - spacing);
        avatarButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.changeAvatarHandle(main);
            }
        });
        deleteAccount.setPosition(centerX - btnWidth / 2, centerY - spacing * 2);
        deleteAccount.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.deleteUser(main);
            }
        });
        logoutButton.setPosition(centerX - btnWidth / 2, centerY - spacing * 3);
        logoutButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                main.getScreen().dispose();
                main.setScreen(new LoginMenuView(new LoginMenuController(), skin, main));
            }
        });
        backButton.setPosition(centerX - btnWidth / 2, centerY - spacing * 4);
        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                main.getScreen().dispose();
                main.setScreen(new MainMenuView(new MainMenuController(), skin, main));
            }
        });

        stage.addActor(newUserName);
        stage.addActor(changeButtonUsername);
        stage.addActor(newPassWord);
        stage.addActor(changeButtonPassword);
        stage.addActor(avatarButton);
        stage.addActor(deleteAccount);
        stage.addActor(logoutButton);
        stage.addActor(backButton);
        stage.addActor(error);

    }






    @Override
    public void render(float v) {
        ScreenUtils.clear(0, 0, 0, 1);
        Main.getBatch().begin();
        Main.getBatch().end();
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
    }

    @Override
    public void resize(int i, int i1) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        stage.dispose();
    }

    public Label getError() {
        return error;
    }

}
