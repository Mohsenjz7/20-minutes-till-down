package com.tilldawn.View;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.Cursor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.tilldawn.Control.GameController.GameController;
import com.tilldawn.Control.GameController.PlayerController;
import com.tilldawn.Control.GameController.WeaponController;
import com.tilldawn.Control.MainMenuController;
import com.tilldawn.Control.SettingMenuController;
import com.tilldawn.Main;
import com.tilldawn.Model.Bullet;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.Color;
import com.tilldawn.Model.GameAssetManager;
import com.tilldawn.Model.GameTimer;
import box2dLight.RayHandler;
import box2dLight.PointLight;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Body;

import javax.imageio.stream.ImageInputStream;


public class GameView implements Screen, InputProcessor {
    private Stage stage;
    private GameController controller;
    private final OrthographicCamera camera = new OrthographicCamera();
    private Label timeLabel;
    private Label label;
    private Label xpLabel;
    private Skin skin;
    private Table deathTable;
    private boolean isPaused = false;

    private Label deathMessageLabel;
    private World world;
    private RayHandler rayHandler;
    private PointLight playerLight;





    public GameView(GameController controller, Skin skin) {
        this.controller = controller;
        timeLabel = new Label("00:00", skin);
        deathTable = new Table();
        this.skin = skin;
        label = new Label("HP :"+4, GameAssetManager.getGameAssetManager().getSkin());
        xpLabel = new Label("XP :"+0, GameAssetManager.getGameAssetManager().getSkin());
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        controller.setView(this);
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        InputMultiplexer multiplexer = new InputMultiplexer();
        multiplexer.addProcessor(stage);
        multiplexer.addProcessor(this);
        Gdx.input.setInputProcessor(multiplexer);


        Pixmap pixmap = new Pixmap(Gdx.files.internal(GameAssetManager.getGameAssetManager().getCursorGame()));
        Cursor customCursor = Gdx.graphics.newCursor(pixmap, 0, 0);
        Gdx.graphics.setCursor(customCursor);

        deathTable = new Table();
        deathTable.setFillParent(true);
        deathTable.center();
        deathTable.setVisible(false);

        deathMessageLabel = new Label("", skin);
        deathMessageLabel.setFontScale(3);
        deathMessageLabel.setColor(Color.RED);

        deathTable.add(deathMessageLabel).pad(20).row();

        TextButton backButton = new TextButton("Back", skin);
        backButton.getLabel().setFontScale(1.5f);
        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.postRunnable(() -> {
                    Main.getMain().getScreen().dispose();
                    Main.getMain().setScreen(new MainMenuView(new MainMenuController(), skin, Main.getMain()));
                });
            }
        });


        deathTable.add(backButton).pad(10);

        stage.addActor(deathTable);


        timeLabel = new Label("00:00", skin);
        timeLabel.setFontScale(2);
        timeLabel.setPosition(20, Gdx.graphics.getHeight() - 60);
        stage.addActor(timeLabel);

        label.setFontScale(2);
        label.setPosition(200, Gdx.graphics.getHeight() - 60);
        stage.addActor(label);

        xpLabel.setFontScale(2);
        xpLabel.setPosition(400, Gdx.graphics.getHeight() - 60);
        stage.addActor(xpLabel);
        //shining
        Box2D.init();
        world = new World(new Vector2(0, 0), true);
        rayHandler = new RayHandler(world);
        rayHandler.setBlur(true);
        rayHandler.setAmbientLight(0.4f);
        rayHandler.setCulling(true);
        rayHandler.setShadows(true);

        playerLight = new PointLight(rayHandler, 200);
        playerLight.setDistance(500f);
        playerLight.setSoftnessLength(100f);

        playerLight.setSoft(true);
        playerLight.setColor(new Color(1f, 1f, 1f, 0.5f));
        playerLight.setActive(true);

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);
        timeLabel.setText(GameTimer.getFormattedTime());

        Main.getBatch().setProjectionMatrix(camera.combined);
        Main.getBatch().begin();
        if (!isPaused) {
            GameTimer.update(delta);
            controller.getWorldController().update();
            controller.updateGame();
            controller.getWeaponController().updateBullets();
        }

        if (PlayerController.getPlayer().getPlayerHealth() <= 0 && !deathTable.isVisible()) {
            isPaused = true;

            String playerName = "guest";
            if(Main.getMain().getCurrentUser() != null){
                playerName = Main.getMain().getCurrentUser().getUserName();
            }

            int kills = PlayerController.getPlayer().getKill();
            int xp = PlayerController.getPlayer().getXp();

            String message = String.format("💀 YOU DIED 💀\n\nPlayer: %s\nKills: %d\nXP: %d\n", playerName, kills, xp);

            deathMessageLabel.setText(message);
            deathMessageLabel.setFontScale(3f);

            deathTable.setVisible(true);
        }


        label.setText("HP :"+String.format("%.1f",PlayerController.getPlayer().getPlayerHealth()));
        xpLabel.setText("XP :"+PlayerController.getPlayer().getXp());

        Main.getBatch().end();
        camera.position.set(PlayerController.getPlayer().getPlayerSprite().getX(),
            PlayerController.getPlayer().getPlayerSprite().getY(), 0);
        camera.update();
        rayHandler.setCombinedMatrix(camera);

        float playerX = PlayerController.getPlayer().getPlayerSprite().getX();
        float playerY = PlayerController.getPlayer().getPlayerSprite().getY();
        playerLight.setPosition(playerX + PlayerController.getPlayer().getPlayerSprite().getWidth() / 2f,
            playerY + PlayerController.getPlayer().getPlayerSprite().getHeight() / 2f);

        rayHandler.updateAndRender();
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
        rayHandler.dispose();
        world.dispose();
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        Vector3 worldCoordinates = camera.unproject(new Vector3(screenX, screenY, 0));
        controller.getWeaponController().handleWeaponShoot((int) worldCoordinates.x, (int) worldCoordinates.y);
        return true;
    }



    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchCancelled(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        Vector3 worldCoordinates = camera.unproject(new Vector3(screenX, screenY, 0));
        controller.getWeaponController().handleWeaponRotation(screenX, screenY);
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }
    @Override
    public boolean keyDown(int keycode) {
//        Main.getMain().getScreen().dispose();
//        Main.getMain().setScreen(new MainMenuView(new MainMenuController(), GameAssetManager.getGameAssetManager().getSkin(), Main.getMain()));
        return false;
    }


}
