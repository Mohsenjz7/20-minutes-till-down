package com.tilldawn.View;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Cursor;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.tilldawn.Control.LoginMenuController;
import com.tilldawn.Main;
import com.tilldawn.Model.GameAssetManager;
import com.tilldawn.Model.Save.User;

public class LoginMenuView implements Screen {
    private Stage stage;
    private final TextButton userName;
    private final TextButton passWord;
    private final TextButton backButton;
    private final TextButton fotGotPassword;
    private final TextButton enter;
    private final Label gameTitle;
    private final Label error;
    private final TextField usernameField;
    private final TextField passwordField;
    private final Main main;
    public Table table;
    public Image background = new Image(new Texture(Gdx.files.internal(GameAssetManager.getGameAssetManager().getBackgroundImage1())));
    private final LoginMenuController controller;

    public LoginMenuView(LoginMenuController controller, Skin skin, Main main) {
        this.main = main;
        this.controller = controller;
        this.userName = new TextButton("Your Username ", skin);
        this.passWord = new TextButton("Your Password ", skin);
        this.backButton = new TextButton("Guest", skin);
        this.fotGotPassword = new TextButton("forgot your password?", skin);
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

        usernameField.setMessageText("Username");
        usernameField.setText("");
        passwordField.setMessageText("Password");
        passwordField.setText("");
        passwordField.setPasswordCharacter('*');
        passwordField.setPasswordMode(true);

        Label forgotPasswordLabel1 = new Label("Don't have Account?", GameAssetManager.getGameAssetManager().getSkin());
        forgotPasswordLabel1.setColor(20f, 20f, 20f, 20);
        forgotPasswordLabel1.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.signUpHandle(main);
            }

            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                forgotPasswordLabel1.setColor(1f, 0.4f, 0.4f, 1);
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                forgotPasswordLabel1.setColor(20f, 20f, 20f, 20);
            }
        });

        Label forgotPasswordLabel = new Label("Forgot your password?", GameAssetManager.getGameAssetManager().getSkin());
        forgotPasswordLabel.setColor(0f, 0f, 0f, 1f);

        forgotPasswordLabel.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                table.clear();
                float paddingHorizontal = 500;

                TextField name = new TextField("", GameAssetManager.getGameAssetManager().getSkin());
                name.setMessageText("Username");

                TextButton fetchQuestion = new TextButton("Get Security Question", GameAssetManager.getGameAssetManager().getSkin());

                Label questionLabel = new Label("", GameAssetManager.getGameAssetManager().getSkin());

                TextField answer = new TextField("", GameAssetManager.getGameAssetManager().getSkin());
                answer.setMessageText("Answer");

                TextButton continueButton = new TextButton("Continue", GameAssetManager.getGameAssetManager().getSkin());
                TextButton backButton = new TextButton("Back", GameAssetManager.getGameAssetManager().getSkin());


                error.setText("");
                error.setVisible(false);


                table.row().pad(10);
                table.add(gameTitle).padBottom(50).colspan(2);

                table.row().pad(10);
                table.add(name).width(400).height(60).padRight(20);
                table.add(fetchQuestion).width(600).height(60);

                table.row().pad(10);
                table.add(questionLabel).colspan(2).width(800).height(60);

                table.row().pad(10);
                table.add(answer).colspan(2).width(600).height(60);

                table.row().pad(10);
                table.add(continueButton).colspan(2).width(600).height(60);

                table.row().pad(10);
                table.add(backButton).colspan(2).width(600).height(60);

                table.row().pad(10);
                table.add(error).colspan(2).width(600).height(60);

                fetchQuestion.addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        String username = name.getText();
                        User user = GameAssetManager.getUserByUsername(username);
                        if (user == null) {
                            error.setText("User not found.");
                            error.setVisible(true);
                            questionLabel.setText("");
                            return;
                        }

                        questionLabel.setText(user.getQuestion());
                        error.setVisible(false);
                    }
                });

                continueButton.addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        controller.forgotPassWord(main, answer.getText(), name.getText());
                    }
                });

                backButton.addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        main.getScreen().dispose();
                        main.setScreen(new LoginMenuView(new LoginMenuController(), GameAssetManager.getGameAssetManager().getSkin(), main));
                    }
                });
            }

            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                forgotPasswordLabel.setColor(1f, 0.4f, 0.4f, 1);
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                forgotPasswordLabel.setColor(0f, 0f, 0f, 1);
            }
        });


        float paddingHorizontal = 750;

        table.row().pad(1000, 0,50, 0);
        table.add(usernameField).fillX().expandX().pad(450 , paddingHorizontal, 20, paddingHorizontal);

        table.row().pad(10, 0, 20, 0);
        table.add(passwordField).fillX().expandX().pad(0, paddingHorizontal, 20, paddingHorizontal);

        table.row().pad(20, 0, 2, 0);
        table.add(enter).fillX().expandX().pad(0, paddingHorizontal, 10, paddingHorizontal);
        enter.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.handleLogin(main);
            }
        });
        table.row().pad(2, 0, 0, 0);
        table.add(error).fillX().expandX().pad(0, paddingHorizontal, 0, paddingHorizontal);

        table.row().pad(2, 0, 5, 0);
        table.add(backButton).fillX().expandX().pad(0, paddingHorizontal, 20, paddingHorizontal);
        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.guestHandle(main);
            }
        });

        table.row().pad(5, 0, 5, 0);
        table.add(forgotPasswordLabel1).right();

        forgotPasswordLabel.setPosition(10,50);
        stage.addActor(forgotPasswordLabel);
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

    public TextButton getPassWord() {
        return passWord;
    }

    public TextButton getUserName() {
        return userName;
    }

    public TextButton getBackButton() {
        return backButton;
    }

    public TextField getUsernameField() {
        return usernameField;
    }

    public TextField getPasswordField() {
        return passwordField;
    }

    public TextButton getFotGotPassword() {
        return fotGotPassword;
    }

    public LoginMenuController getController() {
        return controller;
    }

    public Label getError() {
        return error;
    }

    public TextButton getEnter() {
        return enter;
    }

    public void showResetPasswordForm(Main main, User user) {
        table.clear();
        float paddingHorizontal = 500;

        Label resetLabel = new Label("Reset Your Password", GameAssetManager.getGameAssetManager().getSkin());

        TextField newPassword = new TextField("", GameAssetManager.getGameAssetManager().getSkin());
        newPassword.setMessageText("New Password");

        TextField confirmPassword = new TextField("", GameAssetManager.getGameAssetManager().getSkin());
        confirmPassword.setMessageText("Confirm Password");

        TextButton resetButton = new TextButton("Reset Password", GameAssetManager.getGameAssetManager().getSkin());

        TextButton backToLogin = new TextButton("Back to Login", GameAssetManager.getGameAssetManager().getSkin());

        error.setText("");
        error.setVisible(false);

        table.row().pad(10);
        table.add(resetLabel).colspan(2).padBottom(50);

        table.row().pad(10);
        table.add(newPassword).colspan(2).width(600).height(60);

        table.row().pad(10);
        table.add(confirmPassword).colspan(2).width(600).height(60);

        table.row().pad(10);
        table.add(resetButton).colspan(2).width(600).height(60);

        table.row().pad(10);
        table.add(backToLogin).colspan(2).width(600).height(60);

        table.row().pad(10);
        table.add(error).colspan(2).width(600).height(60);

        resetButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.checkResetPassword(main, user, newPassword.getText(), confirmPassword.getText());
            }
        });

        backToLogin.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                main.getScreen().dispose();
                main.setScreen(new LoginMenuView(new LoginMenuController(), GameAssetManager.getGameAssetManager().getSkin(), main));
            }
        });
    }

}
