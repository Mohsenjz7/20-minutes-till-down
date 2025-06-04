package com.tilldawn.Control;

import com.tilldawn.Main;
import com.tilldawn.Model.GameAssetManager;
import com.tilldawn.Model.Enums.Regex;
import com.tilldawn.Model.Save.User;
import com.tilldawn.Model.Enums.SecurityQuestion;
import com.tilldawn.View.LoginMenuView;
import com.tilldawn.View.SignUpMenuView;

import java.util.Random;
import java.util.regex.Matcher;

public class SignUpMenuController {
    private SignUpMenuView view;

    public void setView(SignUpMenuView view) {
        this.view = view;
    }


    public void handleSignUp(Main main) {
        String userName = view.getUsernameField().getText();
        String passWord = view.getPasswordField().getText();
        SecurityQuestion selectedQuestion = view.getSecurityQuestionSelectBox().getSelected();
        String answer = view.getSecurityAnswer().getText();


        if (userName.isEmpty() || passWord.isEmpty()) {
            view.getError().setText("Please fill all the fields");
            return;
        }

        if (GameAssetManager.getUserByUsername(userName) != null) {
            view.getError().setText("This username already exists!");
            return;
        }

        Matcher matcher = Regex.passWord.GetMatcher(passWord);
        if (matcher == null || !matcher.matches()) {
            view.getError().setText("Choose a stronger password!");
            return;
        }

        User newUser = new User(userName, passWord, randomAvatarHandle(), 0, 0, 0);
        newUser.setQuestion(selectedQuestion.getQuestion());
        newUser.setAnswer(answer);
        GameAssetManager.addUser(newUser);
        main.setCurrentUser(newUser);
        view.getError().setText("Account created successfully!");
        main.setScreen(new LoginMenuView(new LoginMenuController(), GameAssetManager.getGameAssetManager().getSkin(), main));
    }

    private String randomAvatarHandle() {
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

}
