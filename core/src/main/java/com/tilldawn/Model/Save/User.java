package com.tilldawn.Model.Save;

import com.badlogic.gdx.Input;

public class User {
    private String userName;
    private String passWord;
    private String Image;
    private int rate;
    private int kill;
    private int duration;
    private String question;
    private String answer;
    public int KEY_MOVE_UP = Input.Keys.W;
    public int KEY_MOVE_DOWN = Input.Keys.S;
    public int KEY_MOVE_LEFT = Input.Keys.A;
    public int KEY_MOVE_RIGHT = Input.Keys.D;
    public int KEY_SHOOT = Input.Buttons.LEFT;
    public int KEY_RELOAD = Input.Keys.R;
    public int KEY_AUTOAIM = Input.Keys.SPACE;

    public User(String userName, String passWord, String Image, int rate, int kill, int duration) {
        this.userName = userName;
        this.passWord = passWord;
        this.Image = Image;
        this.rate = rate;
        this.kill = kill;
        this.duration = duration;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public int getKill() {
        return kill;
    }

    public void setKill(int kill) {
        this.kill = kill;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public int getKeyAutoaim() {
        return KEY_AUTOAIM;
    }

    public void setKeyAutoaim(int keyAutoaim) {
        KEY_AUTOAIM = keyAutoaim;
    }

    public int getKeyReload() {
        return KEY_RELOAD;
    }

    public void setKeyReload(int keyReload) {
        KEY_RELOAD = keyReload;
    }

    public int getKeyShoot() {
        return KEY_SHOOT;
    }

    public void setKeyShoot(int keyShoot) {
        KEY_SHOOT = keyShoot;
    }

    public int getKeyMoveLeft() {
        return KEY_MOVE_LEFT;
    }

    public void setKeyMoveLeft(int keyMoveLeft) {
        KEY_MOVE_LEFT = keyMoveLeft;
    }

    public int getKeyMoveRight() {
        return KEY_MOVE_RIGHT;
    }

    public void setKeyMoveRight(int keyMoveRight) {
        KEY_MOVE_RIGHT = keyMoveRight;
    }

    public int getKeyMoveDown() {
        return KEY_MOVE_DOWN;
    }

    public void setKeyMoveDown(int keyMoveDown) {
        KEY_MOVE_DOWN = keyMoveDown;
    }

    public int getKeyMoveUp() {
        return KEY_MOVE_UP;
    }

    public void setKeyMoveUp(int keyMoveUp) {
        KEY_MOVE_UP = keyMoveUp;
    }
}
