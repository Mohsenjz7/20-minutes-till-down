package com.tilldawn.Control;

import com.tilldawn.Main;
import com.tilldawn.Model.GameAssetManager;
import com.tilldawn.Model.Enums.Regex;
import com.tilldawn.Model.Save.User;
import com.tilldawn.View.LoginMenuView;
import com.tilldawn.View.ProfileMenuView;

import java.util.Random;
import java.util.regex.Matcher;

public class ProfileMenuController {
    private ProfileMenuView view;


    public ProfileMenuView getView() {
        return view;
    }

    public void setView(ProfileMenuView view) {
        this.view = view;
    }
    public String randomAvatarHandle() {
        int rand = new Random().nextInt(3);
        if (rand == 0) {
            return GameAssetManager.getGameAssetManager().getAvatar1();
        } else if (rand == 1) {
            return GameAssetManager.getGameAssetManager().getAvatar2();
        } else if (rand == 2) {
            return GameAssetManager.getGameAssetManager().getAvatar3();
        }  else {
            return GameAssetManager.getGameAssetManager().getAvatar4();
        }
    }

    public void changeUsername(String username, Main main) {
        if(main.getCurrentUser() == null){
            view.getError().setText("you are guest!");
            return;
        }
        StringBuilder err = new StringBuilder();
        if(username.equals("")){
            err.append("Please enter your username!");
        }
        else if(username.equals(main.getCurrentUser().getUserName())){
            err.append("new username equals to older!");
        } else if(GameAssetManager.getUserByUsername(username) != null){
            err.append("this username is already in use!");
        }

        view.getError().setText(err);
        User user = main.getCurrentUser();
        GameAssetManager.removeUser(GameAssetManager.getUserByUsername(user.getUserName()));
        user.setUserName(username);
        GameAssetManager.addUser(user);

    }

    public void changePassword(String password, Main main) {
        if(main.getCurrentUser() == null){
            view.getError().setText("you are guest!");
            return;
        }
        StringBuilder err = new StringBuilder();
        Matcher matcher;
        if(password.equals("")){
            err.append("Please enter your password!");
        }
        else if ((matcher = Regex.passWord.GetMatcher(password)) == null) {
            err.append("password is weak!");
        }

        view.getError().setText(err);
        User user = main.getCurrentUser();
        GameAssetManager.removeUser(GameAssetManager.getUserByUsername(user.getUserName()));
        user.setPassWord(password);
        GameAssetManager.addUser(user);
    }

    public void deleteUser(Main main) {
        if(main.getCurrentUser() == null){
            view.getError().setText("you are guest!");
            return;
        }
        view.getError().setText("your account have been deleted!");
        User user = main.getCurrentUser();
        GameAssetManager.removeUser(GameAssetManager.getUserByUsername(user.getUserName()));
        main.getScreen().dispose();
        main.setScreen(new LoginMenuView(new LoginMenuController(),GameAssetManager.getGameAssetManager().getSkin(), main));
    }

    public void changeAvatarHandle(Main main) {
        if (main.getCurrentUser() == null){
            view.getError().setText("you are guest!");
            return;
        }
        System.out.println(main.getCurrentUser().getImage());
        String newAvatar = randomAvatarHandle();
        while(true){
            if(!newAvatar.equals(main.getCurrentUser().getImage())){
                newAvatar = randomAvatarHandle();
                break;
            }
        }
        view.getError().setText("your avatar have been changed!");
        main.getCurrentUser().setImage(newAvatar);
        System.out.println(main.getCurrentUser().getImage());
    }

}
