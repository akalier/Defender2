package com.example.ppolova.defender;

import android.app.ListActivity;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
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

    RecyclerView scoreLW;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        scoreManager = new ScoreManager(this);
        ArrayList<String> listData = populateLW();

        setListAdapter(new ArrayAdapter<String>(this, R.layout.activity_score, listData));
        ListView listView = getListView();
        listView.setTextFilterEnabled(true);
        listView.setEnabled(false);

    }

    private ArrayList<String> populateLW() {
        Cursor data = scoreManager.getData();
        ArrayList<String> listData = new ArrayList<String>();
        int i = 0;
        while (data.moveToNext()) {
            i++;
            Score score = new Score(i, data.getString(1), data.getInt(2));
            listData.add(score.toString());
        }

        return listData;
    }
}
