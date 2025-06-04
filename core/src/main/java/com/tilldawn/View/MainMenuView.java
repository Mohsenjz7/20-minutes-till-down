package com.tilldawn.View;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Cursor;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.tilldawn.Control.*;
import com.tilldawn.Control.GameController.GameController;
import com.tilldawn.Main;
import com.tilldawn.Model.GameAssetManager;
import com.tilldawn.Model.MusicManager;

public class MainMenuView implements Screen {
    private Stage stage;
    private Main main;
    private final TextButton playButton;
    private final TextButton loadGameButton;
    private Music mainMenuMusic;
    private final TextButton exitButton;
    private final TextButton setting;
    private final TextButton profile;
    private final TextButton preGameButton;
    private final TextButton scoreBoardButton;
    private final TextButton hint;
    public Table table;
    private final MainMenuController controller;
    private Animation<TextureRegion> backgroundAnimation;
    private float stateTime;
    private Array<Texture> backgroundTextures;


    public MainMenuView(MainMenuController controller, Skin skin,Main main) {
        this.main = main;
        this.controller = controller;
        this.playButton = new TextButton("new Game", skin);
        this.loadGameButton = new TextButton("Load Game", skin);
        this.exitButton = new TextButton("Exit", skin);
        this.setting = new TextButton("Settings", skin);
        this.profile = new TextButton("Profile", skin);
        this.preGameButton = new TextButton("Pre Game", skin);
        this.scoreBoardButton = new TextButton("Score Board", skin);
        this.hint = new TextButton("Hint", skin);
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

        table.setFillParent(true);
        stage.addActor(table);

        float buttonWidth = 600f;
        float buttonHeight = 100f;
        float buttonSpacing = 30f;

        playButton.setSize(buttonWidth, buttonHeight);
        loadGameButton.setSize(buttonWidth, buttonHeight);
        exitButton.setSize(buttonWidth, buttonHeight);
        profile.setSize(buttonWidth, buttonHeight);
        preGameButton.setSize(buttonWidth, buttonHeight);
        scoreBoardButton.setSize(buttonWidth, buttonHeight);
        hint.setSize(buttonWidth, buttonHeight);

        table.top().left().padTop(150).padLeft(50);
        table.add(playButton).width(buttonWidth).height(buttonHeight).pad(10).left().row();
        playButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                main.getScreen().dispose();
                main.setScreen(new GameView(new GameController(),GameAssetManager.getGameAssetManager().getSkin()));
            }
        });
        table.add(loadGameButton).width(buttonWidth).height(buttonHeight).pad(10).left().row();
        table.add(preGameButton).width(buttonWidth).height(buttonHeight).pad(10).left().row();
        table.add(scoreBoardButton).width(buttonWidth).height(buttonHeight).pad(10).left().row();

        scoreBoardButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                main.getScreen().dispose();
                main.setScreen(new ScoreBoardView(new ScoreBoardController(),GameAssetManager.getGameAssetManager().getSkin(), main));
            }
        });
        table.add(hint).width(buttonWidth).height(buttonHeight).pad(10).left().row();
        table.add(exitButton).width(buttonWidth).height(buttonHeight).pad(10).left().row();

        exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });
        Texture avatarTexture;
        int score = 0;
        StringBuilder username = new StringBuilder();
        if(main.getCurrentUser() == null){
            avatarTexture = new Texture(Gdx.files.internal(controller.randomAvatarHandle()));
            username.append("Guest");
        }else{
            avatarTexture = new Texture(Gdx.files.internal(main.getCurrentUser().getImage()));
            score = main.getCurrentUser().getRate();
            username.append(main.getCurrentUser().getUserName());
        }



        ImageButton.ImageButtonStyle gearStyle = new ImageButton.ImageButtonStyle();
        gearStyle.imageUp = new TextureRegionDrawable(new TextureRegion(GameAssetManager.getGameAssetManager().getGearTexture()));

        ImageButton.ImageButtonStyle avatarStyle = new ImageButton.ImageButtonStyle();
        avatarStyle.imageUp = new TextureRegionDrawable(new TextureRegion(avatarTexture));

        ImageButton.ImageButtonStyle exitStyle = new ImageButton.ImageButtonStyle();
        exitStyle.imageUp = new TextureRegionDrawable(new TextureRegion(GameAssetManager.getGameAssetManager().getExitToDesktop()));

        ImageButton gearButton = new ImageButton(gearStyle);
        ImageButton avatarButton = new ImageButton(avatarStyle);
        ImageButton exitButton = new ImageButton(exitStyle);

        Table topRightTable = new Table();
        topRightTable.top().right();
        topRightTable.setFillParent(true);
        Table avatarColumnTable = new Table();
        topRightTable.add(gearButton).size(120, 120).pad(0);
        gearButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                main.getScreen().dispose();
                main.setScreen(new SettingMenuView(new SettingMenuController(),GameAssetManager.getGameAssetManager().getSkin(), main));
            }
        });
        topRightTable.add(exitButton).size(120, 120).pad(0);
        exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(main.getCurrentUser() != null) {
                    main.setCurrentUser(null);
                }
                main.getScreen().dispose();
                main.setScreen(new LoginMenuView(new LoginMenuController(),GameAssetManager.getGameAssetManager().getSkin(),main));
            }
        });

        avatarColumnTable.add(avatarButton).size(120, 120).padBottom(0).row();
        avatarButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                main.getScreen().dispose();
                main.setScreen(new ProfileMenuView(new ProfileMenuController(),GameAssetManager.getGameAssetManager().getSkin(),main));
            }
        });
        avatarColumnTable.add(new Label(username.toString(), GameAssetManager.getGameAssetManager().getSkin())).padBottom(2).row();
        avatarColumnTable.add(new Label("Score: " + score, GameAssetManager.getGameAssetManager().getSkin())).row();

        topRightTable.add(avatarColumnTable).pad(0);

        stage.addActor(topRightTable);
        int totalFrames = 580;
        backgroundTextures = new Array<>();
        Array<TextureRegion> regions = new Array<>();

        for (int i = 1; i <= 70 ; i++) {
            String fileName = String.format("backgoundVideo/frame_%04d.png", i);
            Texture tex = new Texture(Gdx.files.internal(fileName));
            tex.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
            backgroundTextures.add(tex);
            regions.add(new TextureRegion(tex));
        }
        backgroundAnimation = new Animation<>(1f / 30f, regions, Animation.PlayMode.LOOP);
        stateTime = 0f;


//        MusicManager.getInstance().playMusic(GameAssetManager.getGameAssetManager().getMainMusic(),true);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);
        stateTime += delta;
        TextureRegion currentFrame = backgroundAnimation.getKeyFrame(stateTime, true);

        Main.getBatch().begin();
        Main.getBatch().draw(currentFrame, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
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
        if (backgroundTextures != null) {
            for (Texture tex : backgroundTextures) {
                tex.dispose();
            }
        }
        stage.dispose();

    }

    public TextButton getPlayButton() {
        return playButton;
    }

    public Main getMain() {
        return main;
    }

    public void setMain(Main main) {
        this.main = main;
    }

    public TextButton getLoadGameButton() {
        return loadGameButton;
    }

    public TextButton getExitButton() {
        return exitButton;
    }

    public TextButton getSetting() {
        return setting;
    }

    public TextButton getProfile() {
        return profile;
    }

    public TextButton getPreGameButton() {
        return preGameButton;
    }

    public TextButton getScoreBoardButton() {
        return scoreBoardButton;
    }

    public TextButton getHint() {
        return hint;
    }

    public Music getMainMenuMusic() {
        return mainMenuMusic;
    }

    public void setMainMenuMusic(Music mainMenuMusic) {
        this.mainMenuMusic = mainMenuMusic;
    }
}
