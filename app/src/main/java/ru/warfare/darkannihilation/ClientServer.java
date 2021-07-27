package ru.warfare.darkannihilation;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import ru.warfare.darkannihilation.systemd.Game;

import static ru.warfare.darkannihilation.constant.Constants.SERVER_IP;
import static ru.warfare.darkannihilation.Py.print;

public final class ClientServer {
    private static final OkHttpClient client = new OkHttpClient();

    public static JSONObject info_from_server = new JSONObject();
    public static JSONArray namesPlayers = new JSONArray();

    static {
        getStatistics(false);
    }

    public static void getStatistics(boolean needATable) {
        client.newCall(
                new Request.Builder()
                        .url(SERVER_IP + "get")
                        .build())
                .enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                print(e);
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) {
                try {
                    if (response.body() != null) {
                        info_from_server = new JSONObject(response.body().string());
                        namesPlayers = info_from_server.names();
                        if (needATable) {
                            Game.makeTopPlayersTable();
                        }
                    }
                } catch (Exception e) {
                    print(e);
                }
            }
        });
    }

    public static void postBestScore(String nickname, int bestScore) {
        JSONObject jsonScore = new JSONObject();
        try {
            jsonScore.put(nickname, bestScore);
        } catch (Exception e) {
            print(e);
        }
        client.newCall(
                new Request.Builder()
                        .url(SERVER_IP + "write?data=" + jsonScore.toString())
                        .build())
                .enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                print(e);
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) {}
        });
    }

    public static void postAndGetBestScore(String nickname, int bestScore) {
        JSONObject jsonScore = new JSONObject();
        try {
            jsonScore.put(nickname, bestScore);
        } catch (Exception e) {
            print(e);
        }
        client.newCall(
                new Request.Builder()
                        .url(SERVER_IP + "write?data=" + jsonScore.toString())
                        .build())
                .enqueue(new Callback() {
                    @Override
                    public void onFailure(@NotNull Call call, @NotNull IOException e) {
                        print(e);
                    }

                    @Override
                    public void onResponse(@NotNull Call call, @NotNull Response response) {
                        getStatistics(true);
                    }
                });
    }
}
