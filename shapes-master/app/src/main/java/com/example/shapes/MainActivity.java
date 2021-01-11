package com.example.shapes;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    RadioGroup shapes;
    Spinner colors;
    Scene scene;
    Button button;
    NiceAdapter adapter;
    String[] color;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        color = getResources().getStringArray(R.array.colors);
        adapter = new NiceAdapter(MainActivity.this, R.layout.spinner_item, color);

        scene = findViewById(R.id.scene);
        shapes = findViewById(R.id.shapeGroup);

        colors = findViewById(R.id.colors);
        colors.setAdapter(adapter);
        colors.setSelection(0, true);

        button = findViewById(R.id.button);
        button.setOnClickListener(this);

        RecoveryData();

        shapes.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @SuppressLint("NonConstantResourceId")
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
                scene.setColor("#"+color[position]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public class NiceAdapter extends ArrayAdapter<String> {
        public NiceAdapter(Context context, int body, String[] objects) {
            super(context, body, objects);
        }
        @Override
        public View getDropDownView(int pos, View newView, ViewGroup parent) {
            return GetNiceView(pos, newView, parent);
        }
        @Override
        public View getView(int pos, View newView, ViewGroup parent) {
            return GetNiceView(pos, newView, parent);
        }
        @SuppressLint("SetTextI18n")
        public View GetNiceView(int pos, View newView, ViewGroup parent) {
            LayoutInflater inflater = getLayoutInflater();
            View nice_spinner = inflater.inflate(R.layout.spinner_item, parent, false);

            TextView label = (TextView) nice_spinner.findViewById(R.id.textView);
            label.setText("#"+color[pos]);
            label.setTextColor(Color.MAGENTA);
            label.setTextSize(12);

            ImageView image = (ImageView) nice_spinner.findViewById(R.id.imageView);
            image.setImageResource(R.drawable.ic_launcher_foreground);

            return nice_spinner;
        }
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