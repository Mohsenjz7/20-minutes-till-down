package com.tilldawn.Control.GameController;

import com.tilldawn.Model.Enums.HeroType;
import com.tilldawn.Model.Enums.WeaponType;
import com.tilldawn.Model.Player;
import com.tilldawn.Model.Weapon;
import com.tilldawn.View.GameView;

public class GameController {
    private PlayerController playerController;
    private WorldController worldController;
    private WeaponController weaponController;
    private EnemyController enemyController;

    public void setView(GameView view) {
        playerController = new PlayerController(new Player(HeroType.Shana));
        worldController = new WorldController(playerController);
        weaponController = new WeaponController(new Weapon(playerController.getPlayer(), WeaponType.SHOTGUN));
        enemyController = new EnemyController();
    }

    public void updateGame() {
        worldController.update();
        playerController.update();
        weaponController.update();
        enemyController.update();
    }

    public PlayerController getPlayerController() {
        return playerController;
    }

    public WeaponController getWeaponController() {
        return weaponController;
    }

    public WorldController getWorldController() {
        return worldController;
    }
}
