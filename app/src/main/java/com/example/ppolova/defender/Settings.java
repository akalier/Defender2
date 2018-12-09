package com.example.ppolova.defender;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class Settings extends AppCompatActivity {

    TextView nameText;
    Button saveButton;

    String playerName = "Player";

    RadioGroup radioDifficultyGroup;
    RadioButton radioDifficultyButton;

    SharedPreferences mySharedPref;
    SharedPreferences.Editor mySharedEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        nameText = findViewById(R.id.nameText);
        saveButton = findViewById(R.id.saveButton);

        radioDifficultyGroup = findViewById(R.id.radioGroup);

        addListenerOnButton();

        mySharedPref = getSharedPreferences("myPref", MODE_PRIVATE);

        int difficulty = mySharedPref.getInt("difficulty", 1);
        String playerName = mySharedPref.getString("name", "Player");

        switch (difficulty) {
            case 0:
                radioDifficultyGroup.check(R.id.radioEasy);
                break;
            case 1:
                radioDifficultyGroup.check(R.id.radioMedium);
                break;
            case 2:
                radioDifficultyGroup.check(R.id.radioHard);
                break;
            case 3:
                radioDifficultyGroup.check(R.id.radioDeadly);
                break;
            default:
                radioDifficultyGroup.check(R.id.radioEasy);
                break;
        }

        nameText.setText(playerName);

        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
    }

    public void addListenerOnButton() {

        saveButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                // get selected radio button from radioGroup
                int selected;
                switch (radioDifficultyGroup.getCheckedRadioButtonId()) {
                    case R.id.radioEasy:
                        selected = 0;
                        break;
                    case R.id.radioMedium:
                        selected = 1;
                        break;
                    case R.id.radioHard:
                        selected = 2;
                        break;
                    case R.id.radioDeadly:
                        selected = 3;
                        break;
                    default:
                        selected = 0;
                        break;
                }

                if (!nameText.getText().toString().equals("")) {
                    playerName = nameText.getText().toString();
                }

                mySharedPref = getSharedPreferences("myPref", MODE_PRIVATE);
                mySharedEditor = mySharedPref.edit();

                mySharedEditor.putString("name", playerName);
                mySharedEditor.putInt("difficulty", selected);

                mySharedEditor.apply();

                finish();
                startActivity(new Intent(Settings.this, MenuActivity.class));

            }

        });

    }
}
