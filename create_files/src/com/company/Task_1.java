package com.company;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Calendar;

public class Task_1 {

    public static void main(String[] args) {
        String path = "C:/Users/mobile/Desktop/My_projects/JAVA/Maraphone/src/com/company/note.txt";

        File inputFile = new File(path);

        if (!inputFile.exists()) {
            System.out.println("The input file doesn't exit.");
            return;
        }

        try {
            FileInputStream fis = new FileInputStream(inputFile);
            FileChannel fileChannel = fis.getChannel();
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            BufferedReader br = new BufferedReader(fis);

            while (fileChannel.read(buffer) > 0) {
                buffer.flip();
                while (buffer.hasRemaining()) {
                    byte b = buffer.get();
                    System.out.print((char) b);
                }
                buffer.clear();
            }

            fileChannel.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static long getCurrentTimeInMillisecs() {
        Calendar cal = Calendar.getInstance();
        return cal.getTimeInMillis();
    }
}