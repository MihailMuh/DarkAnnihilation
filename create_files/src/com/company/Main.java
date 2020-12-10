package com.company;
import java.io.*;
import java.util.Calendar;
import java.util.Date;

public class Main {

    public static void main(String[] args) throws IOException {
        String path = "C:/Users/mobile/Desktop/My_projects/JAVA/Maraphone/src/com/company/note.txt";
        File file = new File(path);
        if (file.createNewFile()) {
            System.out.println(path + " Файл создан");
        } else {
            System.out.println("Файл " + path + " уже существует");
        }
//        try (FileWriter writer = new FileWriter(path, false)) {
//            String text = "HELLO WORLD!";
//            writer.write(text);
//            writer.append('\n');
//            writer.append('E');
//
//            writer.flush();
//        } catch (IOException ex) {
//
//            System.out.println(ex.getMessage());
//        }
        long start = getCurrentTimeInMillisecs();
        try (FileReader reader = new FileReader(path)) {
            // читаем посимвольно
            int c;
            while ((c = reader.read()) != -1) {

                System.out.print((char) c);
            }
        } catch (IOException ex) {

            System.out.println(ex.getMessage());
        }
        System.out.println();
        long stop = getCurrentTimeInMillisecs();
        System.out.println(stop - start);
    }

    public static long getCurrentTimeInMillisecs() {
        Calendar cal = Calendar.getInstance();
        return cal.getTimeInMillis();
    }
}

