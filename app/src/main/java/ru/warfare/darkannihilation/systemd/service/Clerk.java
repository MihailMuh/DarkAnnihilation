package ru.warfare.darkannihilation.systemd.service;

public final class Clerk {
    public static String nickname = null;

    public static void saveNickname() {
        Service.writeToFile("NICKNAME", nickname);
    }

    public static void getNickname() {
        nickname = Service.readFromFile("NICKNAME");
    }

    public static void saveBestScore(int bestScore) {
        Service.writeToFile("SCORE", bestScore + "");
    }

    public static int getMaxScore() {
        try {
            return Integer.parseInt(Service.readFromFile("SCORE"));
        } catch (Exception e) {
            return 0;
        }
    }

    public static void saveSettings(String string) {
        Service.writeToFile("SETTINGS", string);
    }

    public static String getSettings() {
        String string = Service.readFromFile("SETTINGS");
        if (string == null) {
            saveSettings("1 1 1 en");
            return "1 1 1 en";
        }
        return string;
    }
}
