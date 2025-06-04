package com.tilldawn.View;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Cursor;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.tilldawn.Control.LoginMenuController;
import com.tilldawn.Control.MainMenuController;
import com.tilldawn.Control.ScoreBoardController;
import com.tilldawn.Main;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.tilldawn.Model.GameAssetManager;
import com.tilldawn.Model.Save.User;

import java.util.List;


public class ScoreBoardView implements Screen {

    private Stage stage;
    private final Main main;
    private final Skin skin;
    private final ScoreBoardController controller;
    private Table table;

    public ScoreBoardView(ScoreBoardController controller, Skin skin, Main main) {
        this.main = main;
        this.skin = skin;
        this.controller = controller;
        table = new Table(skin);
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
        table.top();
        stage.addActor(table);

        TextButton sortByScore = new TextButton("Score", skin);
        TextButton sortByUsername = new TextButton("Username", skin);
        TextButton sortByKill = new TextButton("Kill", skin);
        TextButton sortByDuration = new TextButton("Duration", skin);
        TextButton back = new TextButton("Back", skin);

        float buttonWidth = 220f;
        float buttonHeight = 100f;
        sortByScore.setSize(buttonWidth, buttonHeight);
        sortByUsername.setSize(buttonWidth, buttonHeight);
        sortByKill.setSize(buttonWidth, buttonHeight);
        sortByDuration.setSize(buttonWidth, buttonHeight);
        back.setSize(buttonWidth-70, buttonHeight-20);

        Table buttonTable = new Table();
        buttonTable.bottom().padBottom(30);
        buttonTable.setFillParent(true);

        buttonTable.add(sortByScore).pad(5).padRight(10).size(buttonWidth, buttonHeight);
        buttonTable.add(sortByUsername).pad(5).padRight(10).size(buttonWidth, buttonHeight);
        buttonTable.add(sortByKill).pad(5).padRight(10).size(buttonWidth, buttonHeight);
        buttonTable.add(sortByDuration).pad(5).padRight(10).size(buttonWidth-50, buttonHeight-10);

        stage.addActor(buttonTable);


        sortByScore.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.sortByScore();
            }
        });
        sortByUsername.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.sortByUsername();
            }
        });
        sortByKill.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.sortByKill();
            }
        });
        sortByDuration.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.sortByDuration();
            }
        });
        back.setPosition(Gdx.graphics.getWidth()-buttonWidth,Gdx.graphics.getHeight()-buttonHeight);
        back.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                main.getScreen().dispose();
                main.setScreen(new MainMenuView(new MainMenuController(), skin, main));
            }
        });
        stage.addActor(back);
        controller.sortByScore();
    }



    public void updateScoreBoard(List<User> users) {
        table.clear();
        table.add(new Label("Rank", skin)).pad(10);
        table.add(new Label("Username", skin)).pad(10);
        table.add(new Label("Score", skin)).pad(10);
        table.add(new Label("Kill", skin)).pad(10);
        table.add(new Label("Duration", skin)).pad(10);

        table.row();

        String loggedInUsername = main.getCurrentUser() == null ? "Guest" : main.getCurrentUser().getUserName();
        for (int i = 0; i < users.size(); i++) {
            User user = users.get(i);
            String medal = "";
            switch (i) {
                case 0:
                    medal = "🥇";
                    break;
                case 1:
                    medal = "🥈";
                    break;
                case 2:
                    medal = "🥉";
                    break;
            }

            Label.LabelStyle baseStyle = skin.get("default", Label.LabelStyle.class);
            Label.LabelStyle style = new Label.LabelStyle(baseStyle.font, Color.WHITE);

            if (user.getUserName().equals(loggedInUsername)) {
                style.fontColor = Color.GREEN;
            } else if (i == 0) {
                style.fontColor = Color.GOLD;
            } else if (i == 1) {
                style.fontColor = Color.BLUE;
            } else if (i == 2) {
                style.fontColor = Color.BROWN;
            }

            table.add(new Label(String.valueOf(i + 1), style)).pad(10);
            table.add(new Label( user.getUserName(), style)).pad(10);
            table.add(new Label(String.valueOf(user.getRate()), style)).pad(10);
            table.add(new Label(String.valueOf(user.getKill()), style)).pad(10);
            table.add(new Label(String.valueOf(user.getDuration()), style)).pad(10);
            table.row();
        }
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

    }
}
