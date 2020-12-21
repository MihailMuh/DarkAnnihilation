package com.example.shapes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Spinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    RadioGroup shapes;
    Spinner colors;
    Scene scene;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        scene = findViewById(R.id.scene);
        shapes = findViewById(R.id.shapeGroup);
        colors = findViewById(R.id.colors);
        button = findViewById(R.id.button);

        RecoveryData();

        button.setOnClickListener(this);

        shapes.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rect:
                        scene.setTypeShape("rect"); break;
                    case R.id.triangle:
                        scene.setTypeShape("triangle"); break;
                    case R.id.circle:
                        scene.setTypeShape("circle"); break;
                }
            }
        });
        colors.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String[] colors = getResources().getStringArray(R.array.colors);
                scene.setColor("#"+colors[position]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        scene.undo();
    }

    public void RecoveryData(){
        try {
            InputStreamReader reader_cooler = new InputStreamReader(getApplicationContext().openFileInput("DATA.txt"));
            BufferedReader reader_buffer = new BufferedReader(reader_cooler);
            String line;
            StringBuilder builder = new StringBuilder();

            while ((line = reader_buffer.readLine()) != null) {
                builder.append(line);
            }
            JSONArray jsonArray = new JSONArray(builder.toString());
            JSONArray jsonArray_end = new JSONArray();
            for (int i = 0; i < jsonArray.length(); i++)
            {
                JSONObject jsonObj = jsonArray.getJSONObject(i);
                jsonArray_end.put(jsonObj);
                Log.e("Инфо", jsonObj.toString());
            }

            reader_cooler.close();
            Log.e("Инфо", jsonArray.toString());
            scene.recovery_info(jsonArray_end);
        } catch (IOException | JSONException e) {}
    }

    public void SaveData() {
        try {
            FileOutputStream writer = getApplicationContext().openFileOutput("DATA.txt", Context.MODE_PRIVATE);
            OutputStreamWriter writer_str = new OutputStreamWriter(writer);
            writer_str.write(scene.GetData().toString());
            writer_str.close();
        } catch (IOException e) {}
    }

    @Override
    public void onStop() {
        SaveData();
        super.onStop();
    }
}