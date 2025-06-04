package com.tilldawn.View;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Cursor;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.tilldawn.Control.MainMenuController;
import com.tilldawn.Control.PreGameMenuController;
import com.tilldawn.Control.SettingMenuController;
import com.tilldawn.Main;
import com.tilldawn.Model.GameAssetManager;
import com.tilldawn.Model.MusicManager;

public class SettingMenuView implements Screen {

    public Table table;
    private SettingMenuController controller;
    private final Label error;
    private Stage stage;
    private final Main main;

    SettingMenuView(SettingMenuController controller, Skin skin, Main main) {
        this.controller = controller;
        this.main = main;
        this.table = new Table(skin);
        this.error = new Label("", skin);
        controller.setView(this);

    }
    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        Pixmap pixmap = new Pixmap(Gdx.files.internal(GameAssetManager.getGameAssetManager().getCursorMenu()));
        Cursor customCursor = Gdx.graphics.newCursor(pixmap, 0, 0);
        Gdx.graphics.setCursor(customCursor);

        table.setFillParent(true);
        stage.addActor(table);

        ImageButton volumeDownButton = new ImageButton(new TextureRegionDrawable(GameAssetManager.getGameAssetManager().getAudioOn()));
        volumeDownButton.addListener(event -> {
            if (volumeDownButton.isPressed()) {
                controller.volumeSetting(main);
            }
            return true;
        });

        TextButton sfxButton = new TextButton("SFX Volume: Medium", GameAssetManager.getGameAssetManager().getSkin());
        sfxButton.addListener(event -> {
            if (sfxButton.isPressed()) {
                sfxButton.setText("SFX Volume: " + controller.sfxSetting(main));
            }
            return true;
        });

        TextButton changeMove =  new TextButton("Change Move To UP,DOWN,RIGHT,LEFT", GameAssetManager.getGameAssetManager().getSkin());
        changeMove.addListener(event -> {
            if (changeMove.isPressed()) {
                changeMove.setText(controller.changeMove(main));
            }
            return true;
        });
        TextButton changeReload =  new TextButton("Change ReloadKey to H", GameAssetManager.getGameAssetManager().getSkin());
        changeReload.addListener(event -> {
            if (changeReload.isPressed()) {
                changeReload.setText(controller.changeReload(main));
            }
            return true;
        });
        TextButton changeAutoAim =  new TextButton("Change AutoAim to Shift Right", GameAssetManager.getGameAssetManager().getSkin());
        changeAutoAim.addListener(event -> {
            if (changeAutoAim.isPressed()) {
                changeAutoAim.setText(controller.changeAutoAim(main));
            }
            return true;
        });
        TextButton changeShooting =  new TextButton("Change Shooter to RIGHT", GameAssetManager.getGameAssetManager().getSkin());
        changeShooting.addListener(event -> {
            if (changeShooting.isPressed()) {
                changeShooting.setText(controller.changeShooting(main));
            }
            return true;
        });
        TextButton backButton = new TextButton("Back", GameAssetManager.getGameAssetManager().getSkin());
        backButton.addListener(event -> {
            if (backButton.isPressed()) {
                main.getScreen().dispose();
                main.setScreen(new MainMenuView(new MainMenuController(),GameAssetManager.getGameAssetManager().getSkin(), main));
            }
            return true;
        });
        table.center();
        table.add(changeMove).padTop(10);
        table.row();
        table.add(changeAutoAim).padTop(10);
        table.row();
        table.add(changeShooting).padTop(10);
        table.row();
        table.add(changeReload).padTop(10);
        table.row();
        table.add(sfxButton).padBottom(10);
        table.row();
        table.add(backButton).padBottom(10);
        error.setPosition(3,1000);
        stage.addActor(error);



        Table table1 = new Table();
        table1.right().top();
        table1.add(volumeDownButton).size(64).padBottom(10);
        table1.setFillParent(true);
        stage.addActor(table1);


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
