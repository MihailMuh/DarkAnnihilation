package com.example.shapes;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Spinner;

public class MainActivity extends AppCompatActivity {
    RadioGroup shapes;
    Spinner colors;
    Scene scene;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        scene = findViewById(R.id.scene);
        shapes = findViewById(R.id.shapeGroup);
        colors = findViewById(R.id.colors);

        shapes.setOnCheckedChangeListener(new OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rect: scene.setTypeShape("rect"); break;
                    case R.id.circle: scene.setTypeShape("circle"); break;
                    case R.id.triangle: scene.setTypeShape("triangle"); break;
                }
            }
        });

        colors.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String[] colorArr = getResources().getStringArray(R.array.colors);
                scene.setColor("#" + colorArr[position]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}