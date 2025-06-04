package com.tilldawn.Control;

import com.badlogic.gdx.Input;
import com.tilldawn.Main;
import com.tilldawn.Model.GameAssetManager;
import com.tilldawn.Model.MusicManager;
import com.tilldawn.View.SettingMenuView;

import static com.badlogic.gdx.Gdx.input;

public class SettingMenuController
{
    private SettingMenuView view;

    public void setView(SettingMenuView view) {
        this.view = view;
    }


    public void volumeSetting(Main main) {
        float[] volumeLevels = {1.0f, 0.5f, 0.2f, 0f};
        float currentVolume = MusicManager.getInstance().getVolume();

        int closestIndex = 0;
        float minDiff = Float.MAX_VALUE;

        for (int i = 0; i < volumeLevels.length; i++) {
            float diff = Math.abs(currentVolume - volumeLevels[i]);
            if (diff < minDiff) {
                minDiff = diff;
                closestIndex = i;
            }
        }
        int nextIndex = (closestIndex + 1) % volumeLevels.length;
        float newVolume = volumeLevels[nextIndex];
        if (Math.abs(newVolume - currentVolume) >= 0.01f) {
            MusicManager.getInstance().setVolume(newVolume);
        }
    }



    public String sfxSetting(Main main){
        float currentVolume = Main.SFX_VOLUME;
        String nextLabel;

        if (!Main.SFX_ENABLED || currentVolume <= 0f) {
            Main.SFX_VOLUME = 1.0f;
            Main.SFX_ENABLED = true;
            nextLabel = "High";
        } else if (currentVolume > 0.9f) {
            Main.SFX_VOLUME = 0.6f;
            nextLabel = "Medium";
        } else if (currentVolume > 0.5f) {
            Main.SFX_VOLUME = 0.3f;
            nextLabel = "Low";
        } else {
            Main.SFX_VOLUME = 0f;
            Main.SFX_ENABLED = false;
            nextLabel = "Off";
        }


        return nextLabel;
    }

    public String changeMove(Main main) {
        if(main.getCurrentUser() == null){
            view.getError().setText("You need to login first!");
            return "Change Move To UP,DOWN,RIGHT,LEFT";
        }
        if(main.getCurrentUser().getKeyMoveUp() == Input.Keys.W){
            main.getCurrentUser().setKeyMoveRight(Input.Keys.RIGHT);
            main.getCurrentUser().setKeyMoveLeft(Input.Keys.LEFT);
            main.getCurrentUser().setKeyMoveDown(Input.Keys.DOWN);
            main.getCurrentUser().setKeyMoveUp(Input.Keys.UP);
            return "Change Move To W,S,D,A";
        }else if(main.getCurrentUser().getKeyMoveUp() == Input.Keys.UP){
            main.getCurrentUser().setKeyMoveRight(Input.Keys.D);
            main.getCurrentUser().setKeyMoveLeft(Input.Keys.A);
            main.getCurrentUser().setKeyMoveDown(Input.Keys.S);
            main.getCurrentUser().setKeyMoveUp(Input.Keys.W);
            return "Change Move To UP,DOWN,RIGHT,LEFT";
        }

        return "Change Move To UP,DOWN,RIGHT,LEFT";
    }
    public String changeReload(Main main) {
        if(main.getCurrentUser() == null){
            view.getError().setText("You need to login first!");
            return "Change ReloadKey to H";
        }
        if(main.getCurrentUser().KEY_RELOAD == Input.Keys.R){
            main.getCurrentUser().setKeyReload(Input.Keys.H);
            return "Change ReloadKey to R";
        } else if(main.getCurrentUser().KEY_RELOAD == Input.Keys.H){
            main.getCurrentUser().setKeyReload(Input.Keys.R);
            return "Change ReloadKey to H";
        }
        return "Change ReloadKey to H";

    }

    public String changeAutoAim(Main main) {
        if(main.getCurrentUser() == null){
            view.getError().setText("You need to login first!");
            return "Change AutoAim to Shift Right";
        }
        if(main.getCurrentUser().KEY_AUTOAIM == Input.Keys.SPACE){
            main.getCurrentUser().setKeyReload(Input.Keys.SHIFT_RIGHT);
            return "Change AutoAim to Shift Right";
        } else if(main.getCurrentUser().KEY_AUTOAIM== Input.Keys.SHIFT_RIGHT){
            main.getCurrentUser().setKeyReload(Input.Keys.SPACE);
            return "Change AutoAim to Space";
        }
        return "Change AutoAim to Shift Right";

    }

    public String changeShooting(Main main) {
        if(main.getCurrentUser() == null){
            view.getError().setText("You need to login first!");
            return "Change Shooter to RIGHT";
        }
        if(main.getCurrentUser().KEY_SHOOT == Input.Buttons.LEFT){
            main.getCurrentUser().setKeyReload(Input.Buttons.RIGHT);
            return "Change Shooter to LEFT";
        } else if(main.getCurrentUser().KEY_RELOAD == Input.Buttons.RIGHT){
            main.getCurrentUser().setKeyReload(Input.Buttons.LEFT);
            return "Change Shooter to RIGHT";
        }
        return "Change Shooter to RIGHT";

    }

}
