package ru.warfare.darkannihilation;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Point;
import android.media.AudioManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Objects;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }
    Game game;
    final Context context = this;
    public static Request request;
    public static OkHttpClient client = new OkHttpClient();;
    public static JSONObject json = new JSONObject();
    public static JSONArray names;
    public static SharedPreferences preferences = null;
    public static String nickname = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        Objects.requireNonNull(getSupportActionBar()).hide();
        this.getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_LOW_PROFILE
        );
        setContentView(R.layout.activity_main);

        setVolumeControlStream(AudioManager.STREAM_MUSIC);

        preferences = getSharedPreferences("ru.warfare.darkannihilation", MODE_PRIVATE);

        WindowManager windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        Point size = new Point();
        display.getRealSize(size);

        try {
            getTop();
        } catch (Exception e) {
            Log.e("Error", "" + e);
        }


        game = findViewById(R.id.gameView);
        game.initGame(size.x, size.y);
    }

    @Override
    protected void onPause() {
        super.onPause();
        game.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        game.onPause();
        game.saveScore();
        AudioPlayer.releaseAP();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Objects.requireNonNull(getSupportActionBar()).hide();
        this.getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_LOW_PROFILE
        );
        if (preferences.getBoolean("firstrun", true)) {
            LayoutInflater li = LayoutInflater.from(context);
            View promptsView = li.inflate(R.layout.dialog, null);

            AlertDialog.Builder mDialogBuilder = new AlertDialog.Builder(context);

            mDialogBuilder.setView(promptsView);

            final EditText userInput = (EditText) promptsView.findViewById(R.id.input_text);

            mDialogBuilder
                    .setCancelable(false)
                    .setPositiveButton("Apply", null);

            AlertDialog alertDialog = mDialogBuilder.create();
            alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
                @Override
                public void onShow(DialogInterface dialog) {
                    Button button = ((AlertDialog) alertDialog).getButton(AlertDialog.BUTTON_POSITIVE);
                    button.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View view) {
                            nickname = userInput.getText().toString();
                            if (nickname.length() == 0) {
                                Toast toast = Toast.makeText(context, "Nickname must be notnull!", Toast.LENGTH_LONG);
                                toast.show();
                            } else {
                                if (json.has(nickname)) {
                                    Toast toast = Toast.makeText(context, "This nickname already exists", Toast.LENGTH_LONG);
                                    toast.show();
                                } else {
                                    Toast toast = Toast.makeText(context, "Congratulations! You have registered!", Toast.LENGTH_LONG);
                                    toast.show();
                                    dialog.dismiss();
                                }
                            }
                        }
                    });
                }
            });
            alertDialog.show();

            preferences.edit().putBoolean("firstrun", false).apply();
        }
        game.onResume();
    }

    public static void getTop() {
        request = new Request.Builder()
                .url("http://192.168.1.86:8000/get")
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("err", "" + e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    json = new JSONObject(response.body().string());
                    names = json.names();
                } catch (JSONException e) {
                    Log.e("Error", "" + e);
                }
            }
        });
    }

    public static void postScore(String string) {
        request = new Request.Builder()
                .url("http://192.168.1.86:8000/write?data=" + string)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("Error", "" + e);
            }

            @Override
            public void onResponse(Call call, Response response) {}
        });
    }
}