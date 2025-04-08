package com.mygdx.roguelikeproject;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.mygdx.roguelikeproject.screens.MenuScreen;
import com.mygdx.roguelikeproject.screens.GameScreen;

public class RoguelikeProject extends Game {

    private MenuScreen menuScreen;

    @Override
    public void create() {
        // Initialiser et afficher le menu au démarrage
        menuScreen = new MenuScreen(this);
        setScreen(menuScreen);
    }

    public void startGame() {
        // Lancer la partie (nouvelle instance à chaque fois)
        setScreen(new GameScreen(this));
    }

    public void returnToMenu() {
        // Revenir au menu depuis un autre écran
        setScreen(menuScreen);
    }

    public static void main(String[] args) {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        config.setTitle("Roguelike Project");
        config.setWindowedMode(800, 600);
        config.setForegroundFPS(60);

        new Lwjgl3Application(new RoguelikeProject(), config);
    }
}
