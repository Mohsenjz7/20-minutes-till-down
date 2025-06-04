package com.tilldawn.View;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Cursor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.tilldawn.Control.GameController.GameController;
import com.tilldawn.Control.GameController.PlayerController;
import com.tilldawn.Control.GameController.WeaponController;
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




public class GameView implements Screen, InputProcessor {
    private Stage stage;
    private GameController controller;
    private final OrthographicCamera camera = new OrthographicCamera();
    private Label timeLabel;
    private Label label;
    private Skin skin;

    private World world;
    private RayHandler rayHandler;
    private PointLight playerLight;





    public GameView(GameController controller, Skin skin) {
        this.controller = controller;
        timeLabel = new Label("00:00", skin);
        this.skin = skin;
        label = new Label("HP :"+4, GameAssetManager.getGameAssetManager().getSkin());
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        controller.setView(this);
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(this);

        Pixmap pixmap = new Pixmap(Gdx.files.internal(GameAssetManager.getGameAssetManager().getCursorGame()));
        Cursor customCursor = Gdx.graphics.newCursor(pixmap, 0, 0);
        Gdx.graphics.setCursor(customCursor);


        timeLabel = new Label("00:00", skin);
        timeLabel.setFontScale(2);
        timeLabel.setPosition(20, Gdx.graphics.getHeight() - 60);
        stage.addActor(timeLabel);

        label.setFontScale(2);
        label.setPosition(200, Gdx.graphics.getHeight() - 60);
        stage.addActor(label);
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
        GameTimer.update(delta);
        timeLabel.setText(GameTimer.getFormattedTime());


        Main.getBatch().setProjectionMatrix(camera.combined);
        Main.getBatch().begin();
        controller.getWorldController().update();
        controller.updateGame();
        label.setText("HP :"+String.format("%.1f",PlayerController.getPlayer().getPlayerHealth()));
        controller.getWeaponController().updateBullets();

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
    public boolean keyDown(int keycode) {
        return false;
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
}
