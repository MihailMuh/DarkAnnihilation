package ru.warfare.darkannihilation;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.net.ConnectivityManager;
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

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
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
    private final Context context = this;
    public static Request request;
    public static OkHttpClient client = new OkHttpClient();
    public static JSONObject json = new JSONObject();
    public static JSONArray names;
    public static SharedPreferences preferences = null;
    public static String nickname = "";
    public static String TAG = "myLog";
    public static String IP = "http://78.29.33.173:49150/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

        WindowManager windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        Point size = new Point();
        display.getRealSize(size);

        try {
            getTop();
        } catch (Exception e) {
            Log.e(TAG, "" + e);
        }

        game = findViewById(R.id.gameView);
        game.initGame(size.x, size.y);

        checkOnFirstRun();
        Log.e(TAG, "" + Runtime.getRuntime().maxMemory() / 1024);
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
        game.onResume();
    }

    public static void getTop() {
        request = new Request.Builder()
                .url(IP + "get")
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e(TAG, "" + e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    json = new JSONObject(response.body().string());
                    names = json.names();
                } catch (JSONException e) {
                    Log.e(TAG, "" + e);
                }
            }
        });
    }

    public static void postScore(String string) {
        request = new Request.Builder()
                .url(IP + "write?data=" + string)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e(TAG, "" + e);
            }

            @Override
            public void onResponse(Call call, Response response) {}
        });
    }

    public void saveNickname() {
        try {
            FileOutputStream writer = this.openFileOutput("NICKNAME.txt", Context.MODE_PRIVATE);
            OutputStreamWriter writer_str = new OutputStreamWriter(writer);

            writer_str.write(MainActivity.nickname);
            writer_str.close();
        } catch (Exception e) {
            Log.e(MainActivity.TAG, "Can't save NICKNAME " + e);
        }
    }

    public void loadNickname() {
        try {
            InputStreamReader reader_cooler = new InputStreamReader(this.openFileInput("NICKNAME.txt"));
            BufferedReader reader_buffer = new BufferedReader(reader_cooler);
            String line;
            StringBuilder builder = new StringBuilder();

            while ((line = reader_buffer.readLine()) != null) {
                builder.append(line);
            }

            reader_cooler.close();

            nickname = builder.toString();

        } catch (IOException e) {
            Log.e(MainActivity.TAG, "Can't recovery NICKNAME: " + e);
        }
    }

    private boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }

    private void checkOnFirstRun() {
        preferences = getSharedPreferences("ru.warfare.darkannihilation", MODE_PRIVATE);

        if (preferences.getBoolean("firstrun", true)) {
            if (isOnline()) {
                LayoutInflater li = LayoutInflater.from(context);
                View promptsView = li.inflate(R.layout.dialog, null);

                AlertDialog.Builder mDialogBuilder = new AlertDialog.Builder(context);

                mDialogBuilder.setView(promptsView);

                final EditText userInput = (EditText) promptsView.findViewById(R.id.input_text);

                mDialogBuilder
                        .setCancelable(false)
                        .setPositiveButton("Apply", null);

                AlertDialog alertDialog = mDialogBuilder.create();
                alertDialog.setOnShowListener(dialog -> {
                    Button button = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
                    button.setOnClickListener(view -> {
                        nickname = userInput.getText().toString();
                        if (nickname.length() == 0) {
                            Toast toast = Toast.makeText(context, "Nickname must be notnull!", Toast.LENGTH_LONG);
                            toast.show();
                        } else {
                            if (json.has(nickname)) {
                                Toast toast = Toast.makeText(context, "This nickname already exists", Toast.LENGTH_LONG);
                                toast.show();
                            } else {
                                try {
                                    preferences.edit().putBoolean("firstrun", false).apply();
                                    saveNickname();
                                    JSONObject jsonScore = new JSONObject();
                                    jsonScore.put(nickname, 0);
                                    postScore(jsonScore.toString());
                                    Toast toast = Toast.makeText(context, "Congratulations! You have registered!", Toast.LENGTH_LONG);
                                    toast.show();
                                    dialog.dismiss();
                                } catch (Exception e) {
                                    Log.e(TAG, "" + e);
                                }
                            }
                        }
                    });
                });
                alertDialog.show();
            } else {
                LayoutInflater li = LayoutInflater.from(context);
                View promptsView = li.inflate(R.layout.warning, null);

                AlertDialog.Builder mDialogBuilder = new AlertDialog.Builder(context);

                mDialogBuilder.setView(promptsView);

                mDialogBuilder
                        .setCancelable(false)
                        .setPositiveButton("Exit", null)
                        .setNegativeButton("Later", null)
                        .setNeutralButton("I enabled internet and want to register", null);

                AlertDialog alertDialog = mDialogBuilder.create();
                alertDialog.setOnShowListener(dialog -> {
                    Button button = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
                    button.setOnClickListener(view -> System.exit(0));

                    Button buttonCancel = alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE);
                    buttonCancel.setOnClickListener(view -> dialog.dismiss());

                    Button buttonRegister = alertDialog.getButton(AlertDialog.BUTTON_NEUTRAL);
                    buttonRegister.setOnClickListener(view -> {
                        checkOnFirstRun();
                        dialog.dismiss();
                    });
                });
                alertDialog.show();
            }
        } else {
            loadNickname();
        }
    }
}