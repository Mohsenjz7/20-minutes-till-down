package com.tilldawn.Control;


import com.tilldawn.Model.GameAssetManager;
import com.tilldawn.Model.Save.User;
import com.tilldawn.View.ScoreBoardView;

import java.util.*;
import java.util.stream.Collectors;

public class ScoreBoardController {

    private ScoreBoardView view;

    public void setView(ScoreBoardView view) {
        this.view = view;
        sortByScore();
    }

    private List<User> getAllUsers() {
        return GameAssetManager.getUsers();
    }

    public void sortByScore() {
        List<User> sorted = getAllUsers().stream()
            .sorted(Comparator.comparingInt(User::getRate).reversed())
            .limit(10)
            .collect(Collectors.toList());
        view.updateScoreBoard(sorted);
    }

    public void sortByUsername() {
        List<User> sorted = getAllUsers().stream()
            .sorted(Comparator.comparing(User::getUserName))
            .limit(10)
            .collect(Collectors.toList());

        for(int i = 0 ; i < sorted.size() ; i++) {
            System.out.println(sorted.get(i).getUserName());
        }
        view.updateScoreBoard(sorted);
    }

    public void sortByKill() {
        List<User> sorted = getAllUsers().stream()
            .sorted(Comparator.comparingInt(User::getKill).reversed())
            .limit(10)
            .collect(Collectors.toList());
        view.updateScoreBoard(sorted);
    }

    public void sortByDuration() {
        List<User> sorted = getAllUsers().stream()
            .sorted(Comparator.comparingInt(User::getDuration).reversed())
            .limit(10)
            .collect(Collectors.toList());
        view.updateScoreBoard(sorted);
    }

}
