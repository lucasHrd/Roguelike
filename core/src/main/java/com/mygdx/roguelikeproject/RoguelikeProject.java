package com.mygdx.roguelikeproject;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.mygdx.roguelikeproject.screens.MenuScreen;
import com.mygdx.roguelikeproject.screens.GameScreen;
import com.mygdx.roguelikeproject.screens.DeathScreen;


public class RoguelikeProject extends Game {

    @Override
    public void create() {
        // Au démarrage, on affiche le menu
        setScreen(new MenuScreen(this));
    }

    public void startGame() {
        // Méthode appelée depuis le MenuScreen pour lancer la partie
        setScreen(new GameScreen(this));
    }

    public static void main(String[] args) {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        config.setTitle("Roguelike Project");
        config.setWindowedMode(800, 600);
        config.setForegroundFPS(60);

        new Lwjgl3Application(new RoguelikeProject(), config);
    }

    public void endGame() {
        setScreen(new DeathScreen(this));
    }
}
