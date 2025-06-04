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

    private Player player = new Player(HeroType.Diamond);
    private WeaponType weaponType = WeaponType.REVOLVER;

    public void setView(GameView view) {
        playerController = new PlayerController(player);
        worldController = new WorldController(playerController);
        weaponController = new WeaponController(new Weapon(playerController.getPlayer(),weaponType));
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

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public WeaponType getWeaponType() {
        return weaponType;
    }

    public void setWeaponType(WeaponType weaponType) {
        this.weaponType = weaponType;
    }
}
