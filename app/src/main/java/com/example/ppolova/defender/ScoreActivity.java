package com.example.ppolova.defender;

import android.app.ListActivity;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ScoreActivity extends ListActivity {

    ScoreManager scoreManager;

    private ListView scoreLW;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_score);

        scoreManager = new ScoreManager(this);
        ArrayList<String> listData = populateLW();

        setListAdapter(new ArrayAdapter<String>(this, R.layout.activity_score, listData));
        ListView listView = getListView();
        listView.setTextFilterEnabled(true);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // When clicked, show a toast with the TextView text
                /*Toast.makeText(getApplicationContext(),
                        ((TextView) view).getText(), Toast.LENGTH_SHORT).show();*/
            }
        });

    }

    private ArrayList<String> populateLW() {
        Cursor data = scoreManager.getData();
        ArrayList<String> listData = new ArrayList<String>();
        while (data.moveToNext()) {
            Log.d("DATOUU", "pridany data");
            Score score = new Score(data.getString(1), data.getInt(2));
            listData.add(score.toString());
            System.out.println("_______________ " + score.toString());
        }

        return listData;
    }
}
