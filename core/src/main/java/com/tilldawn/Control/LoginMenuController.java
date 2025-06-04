package com.tilldawn.Control;

import com.tilldawn.Main;
import com.tilldawn.Model.GameAssetManager;
import com.tilldawn.Model.Save.User;
import com.tilldawn.View.LoginMenuView;
import com.tilldawn.View.MainMenuView;
import com.tilldawn.View.SignUpMenuView;

public class LoginMenuController {
    private LoginMenuView view;

    public void setView(LoginMenuView view) {
        this.view = view;
    }

    public void handleLogin(Main main) {
        String userName = view.getUsernameField().getText();
        String passWord = view.getPasswordField().getText();

        if (userName.isEmpty() || passWord.isEmpty()) {
            view.getError().setText("Please fill all the fields");
            return;
        }

        User user = GameAssetManager.getUserByUsername(userName);
        if (user == null || !user.getPassWord().equals(passWord)) {
            view.getError().setText("Invalid username or password");
            return;
        }

        view.getError().setText("Login successful!");
        main.setCurrentUser(user);
        main.setScreen(new MainMenuView(new MainMenuController(),GameAssetManager.getGameAssetManager().getSkin(), main));
    }


    public void guestHandle(Main main) {
        main.getScreen().dispose();
        main.setScreen(new MainMenuView(new MainMenuController(), GameAssetManager.getGameAssetManager().getSkin(), main));
    }


    public void forgotPassWord(Main main, String answer, String username) {
        if (answer.isEmpty() || username.isEmpty()) {
            view.getError().setText("Please fill all the fields");
            view.getError().setVisible(true);
            return;
        }

        User user = GameAssetManager.getUserByUsername(username);
        if (user == null) {
            view.getError().setText("User not found.");
            view.getError().setVisible(true);
            return;
        }

        if (!user.getAnswer().equals(answer)) {
            view.getError().setText("Wrong answer!");
            view.getError().setVisible(true);
            return;
        }

        view.getError().setVisible(false);
        view.showResetPasswordForm(main, user);
    }

    public void checkResetPassword(Main main, User user, String newPassword,String confirmPassword) {
        if (newPassword.isEmpty() || confirmPassword.isEmpty()) {
            view.getError().setText("Please fill all the fields");
            view.getError().setVisible(true);
            return;
        }

        if (!newPassword.equals(confirmPassword)) {
            view.getError().setText("Passwords do not match!");
            view.getError().setVisible(true);
            return;
        }

        if (newPassword.equals(user.getPassWord())) {
            view.getError().setText("Passwords is same with last one!");
            view.getError().setVisible(true);
            return;
        }
        GameAssetManager.removeUser(user);
        user.setPassWord(newPassword);
        GameAssetManager.addUser(user);

        view.getError().setColor(0, 0.6f, 0, 1);
        view.getError().setText("Password reset successfully!");
        view.getError().setVisible(true);
    }



    public void signUpHandle(Main main){
        main.getScreen().dispose();
        main.setScreen(new SignUpMenuView(new SignUpMenuController(), GameAssetManager.getGameAssetManager().getSkin(), main));
    }




}
