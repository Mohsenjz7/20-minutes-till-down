package com.tilldawn.Model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;

public class MusicManager {
    private static MusicManager instance;
    private Music music;
    private float volume = 1.0f;

    private MusicManager() {}

    public static MusicManager getInstance() {
        if (instance == null) {
            instance = new MusicManager();
        }
        return instance;
    }

    public void playMusic(String filePath, boolean looping) {
        if (music != null) music.stop();
        music = Gdx.audio.newMusic(Gdx.files.internal(filePath));
        music.setLooping(looping);
        music.setVolume(volume);
        music.play();
    }

    public void setVolume(float newVolume) {
        volume = newVolume;
        if (music != null) {
            music.setVolume(volume);
        }
    }

    public float getVolume() {
        return volume;
    }

    public void stop() {
        if (music != null) music.stop();
    }

    public void pause() {
        if (music != null) music.pause();
    }

    public void resume() {
        if (music != null) music.play();
    }

    public void dispose() {
        if (music != null) {
            music.dispose();
        }
    }
}
