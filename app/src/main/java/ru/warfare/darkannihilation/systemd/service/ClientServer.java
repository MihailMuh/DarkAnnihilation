package ru.warfare.darkannihilation.systemd.service;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static ru.warfare.darkannihilation.systemd.service.Py.print;
import static ru.warfare.darkannihilation.constant.Constants.SERVER_IP;

public final class ClientServer {
    private static final OkHttpClient client = new OkHttpClient();
    private static final Request getRequest = new Request.Builder().url(SERVER_IP + "get").build();

    public static JSONObject info_from_server = new JSONObject();
    public static JSONArray nicksPlayers = new JSONArray();

    private static void postToServer(String message) throws IOException {
        client.newCall(new Request.Builder().url(SERVER_IP + "write?data=" + message).build()).execute().close();
    }

    private static Response getFromServer() throws IOException {
        return client.newCall(getRequest).execute();
    }

    public static void getStatistics() {
        try {
            Response response = getFromServer();

            info_from_server = new JSONObject(response.body().string());
            nicksPlayers = info_from_server.names();

            response.close();
        } catch (Exception e) {
            print(e);
        }
    }

    public static void postBestScore(String nickname, int bestScore) {
        try {
            JSONObject jsonScore = new JSONObject();
            jsonScore.put(nickname, bestScore);

            postToServer(jsonScore.toString());
        } catch (Exception e) {
            print(e);
        }
    }
}
