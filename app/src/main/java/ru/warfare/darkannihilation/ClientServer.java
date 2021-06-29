package ru.warfare.darkannihilation;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static ru.warfare.darkannihilation.Constants.SERVER_IP;

public final class ClientServer {
    private static final OkHttpClient client = new OkHttpClient();

    public static JSONObject info_from_server = new JSONObject();
    public static JSONArray namesPlayers = new JSONArray();

    static {
        getStatistics();
    }

    public static void getStatistics() {
        client.newCall(
                new Request.Builder()
                        .url(SERVER_IP + "get")
                        .build())
                .enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Service.print(e.toString());
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) {
                try {
                    if (response.body() != null) {
                        info_from_server = new JSONObject(response.body().string());
                        namesPlayers = info_from_server.names();
                    }
                } catch (Exception e) {
                    Service.print(e.toString());
                }
            }
        });
    }

    public static void postBestScore(String nickname, int bestScore) {
        JSONObject jsonScore = new JSONObject();
        try {
            jsonScore.put(nickname, bestScore);
        } catch (JSONException e) {
            Service.print(e.toString());
        }
        client.newCall(
                new Request.Builder()
                        .url(SERVER_IP + "write?data=" + jsonScore.toString())
                        .build())
                .enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Service.print(e.toString());
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) {}
        });
    }

    public static void postAndGetBestScore(String nickname, int bestScore) {
        JSONObject jsonScore = new JSONObject();
        try {
            jsonScore.put(nickname, bestScore);
        } catch (JSONException e) {
            Service.print(e.toString());
        }
        client.newCall(
                new Request.Builder()
                        .url(SERVER_IP + "write?data=" + jsonScore.toString())
                        .build())
                .enqueue(new Callback() {
                    @Override
                    public void onFailure(@NotNull Call call, @NotNull IOException e) {
                        Service.print(e.toString());
                    }

                    @Override
                    public void onResponse(@NotNull Call call, @NotNull Response response) {
                        getStatistics();
                    }
                });
    }
}
