package com.tilldawn.View;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Cursor;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.tilldawn.Control.LoginMenuController;
import com.tilldawn.Control.SignUpMenuController;
import com.tilldawn.Main;
import com.tilldawn.Model.GameAssetManager;
import com.tilldawn.Model.Enums.SecurityQuestion;

public class SignUpMenuView implements Screen {
    private Stage stage;
    private final TextButton enter;
    private final Label gameTitle;
    private final Label error;
    private final TextField usernameField;
    private final TextField passwordField;
    private final SelectBox<SecurityQuestion> securityQuestionSelectBox;
    private final TextField SecurityAnswer;
    private final Main main;
    public Table table;
    public Image background = new Image(new Texture(Gdx.files.internal(GameAssetManager.getGameAssetManager().getBackGroundImage())));
    private final SignUpMenuController controller;

    public SignUpMenuView(SignUpMenuController controller, Skin skin, Main main) {
        this.main = main;
        this.controller = controller;
        this.securityQuestionSelectBox = new SelectBox<>(skin);
        this.securityQuestionSelectBox.setItems(SecurityQuestion.values());
        this.SecurityAnswer = new TextField("", skin);
        this.enter = new TextButton("Login", skin);
        this.gameTitle = new Label("20 Minutes UntilDawn", skin);
        this.usernameField = new TextField("", skin);
        this.usernameField.setMessageText("Username");

        this.passwordField = new TextField("", skin);
        this.passwordField.setMessageText("Password");
        this.passwordField.setPasswordMode(true);
        this.passwordField.setPasswordCharacter('*');

        this.error = new Label("", skin);
        this.table = new Table();
        controller.setView(this);
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        Pixmap pixmap = new Pixmap(Gdx.files.internal(GameAssetManager.getGameAssetManager().getCursorMenu()));
        Cursor customCursor = Gdx.graphics.newCursor(pixmap, 0, 0);
        Gdx.graphics.setCursor(customCursor);

        background.setFillParent(true);
        stage.addActor(background);

        table.setFillParent(true);
        table.center();
        stage.addActor(table);

        usernameField.setText("");
        passwordField.setText("");
        error.setText("");

        float paddingHorizontal = 700;
        table.clear();

        TextButton continueButton = new TextButton("Continue", GameAssetManager.getGameAssetManager().getSkin());
        TextButton backButton = new TextButton("Back", GameAssetManager.getGameAssetManager().getSkin());

        securityQuestionSelectBox.setVisible(false);
        SecurityAnswer.setVisible(false);
        enter.setVisible(false);
        backButton.setVisible(false);
        Label label1 = new Label("Select a security question:", GameAssetManager.getGameAssetManager().getSkin());
        label1.setVisible(false);

        table.row().pad(10, 70, 0, 0);
        table.add(error).fillX().expandX().pad(0, 200, 0, paddingHorizontal);

        table.row().pad(10, 0, 10, 0);
        table.add(gameTitle).padBottom(100);

        table.row().pad(10, 0, 10, 0);
        table.add(usernameField).fillX().expandX().pad(0, paddingHorizontal, 20, paddingHorizontal);

        table.row().pad(10, 0, 20, 0);
        table.add(passwordField).fillX().expandX().pad(0, paddingHorizontal, 20, paddingHorizontal);

        table.row().pad(10, 0, 20, 0);
        table.add(continueButton).fillX().expandX().pad(0, paddingHorizontal, 20, paddingHorizontal);

        table.row().pad(10, 0, 20, 0);


        table.add(label1);

        table.row().pad(10, 0, 20, 0);
        table.add(securityQuestionSelectBox).fillX().expandX().pad(0, paddingHorizontal, 20, paddingHorizontal);

        table.row().pad(10, 0, 20, 0);
        table.add(SecurityAnswer).fillX().expandX().pad(0, paddingHorizontal, 20, paddingHorizontal);

        table.row().pad(20, 0, 20, 0);
        table.add(enter).fillX().expandX().pad(0, paddingHorizontal, 20, paddingHorizontal);

        table.row().pad(10, 0, 10, 0);
        table.add(backButton).fillX().expandX().pad(0, paddingHorizontal, 20, paddingHorizontal);



        continueButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                securityQuestionSelectBox.setVisible(true);
                SecurityAnswer.setVisible(true);
                enter.setVisible(true);
                backButton.setVisible(true);
                label1.setVisible(true);
            }
        });

        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                securityQuestionSelectBox.setVisible(false);
                label1.setVisible(false);
                SecurityAnswer.setVisible(false);
                enter.setVisible(false);
                backButton.setVisible(false);
                main.getScreen().dispose();
                main.setScreen(new LoginMenuView(new LoginMenuController(),GameAssetManager.getGameAssetManager().getSkin(), main));
            }
        });

        enter.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.handleSignUp(main);
            }
        });
    }


    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);
        Main.getBatch().begin();
        Main.getBatch().end();
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {

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


    public TextField getUsernameField() {
        return usernameField;
    }

    public TextField getPasswordField() {
        return passwordField;
    }

    public SignUpMenuController getController() {
        return controller;
    }

    public Label getError() {
        return error;
    }

    public TextButton getEnter() {
        return enter;
    }

    public SelectBox<SecurityQuestion> getSecurityQuestionSelectBox() {
        return securityQuestionSelectBox;
    }


    public TextField getSecurityAnswer() {
        return SecurityAnswer;
    }
}
