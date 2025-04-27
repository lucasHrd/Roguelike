package com.mygdx.roguelikeproject.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

public class BestScoreManager {

    private static final String FILE_PATH = "best_score.txt";

    public static void saveBestScore(float timeSurvived) {
        float currentBest = loadBestScore();
        if (timeSurvived > currentBest) {
            FileHandle file = Gdx.files.local(FILE_PATH);
            file.writeString(String.valueOf(timeSurvived), false); // Juste écrire le float en texte
        }
    }

    public static float loadBestScore() {
        FileHandle file = Gdx.files.local(FILE_PATH);
        if (file.exists()) {
            try {
                return Float.parseFloat(file.readString());
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        return 0f;
    }
}
