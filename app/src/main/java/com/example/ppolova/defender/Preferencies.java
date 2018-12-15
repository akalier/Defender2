package com.example.ppolova.defender;

import android.content.Context;
import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;

public class Preferencies {

    private int difficulty;
    private String playerName;

    private SharedPreferences mySharedPref;

    Context context;

    public Preferencies(Context context) {

        this.context = context;

        mySharedPref = context.getSharedPreferences("myPref", MODE_PRIVATE);

        playerName = mySharedPref.getString("name", "Player");
        difficulty = mySharedPref.getInt("difficulty", 1);
    }

    public int getDifficulty() {
        return difficulty;
    }

    public String getPlayerName() {
        return playerName;
    }
}
