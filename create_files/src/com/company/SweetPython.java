package com.company;

public class SweetPython {
    public String Str(int a){
        String b = String.valueOf(a);
        return b;
    }
    public int Int(String a){
        int b = Integer.valueOf(a);
        return b;
    }
    public float Str_To_Float(String a){
        float b = Float.valueOf(a);
        return b;
    }
    public float Int_To_Float(int a){
        float b = Float.valueOf(a);
        return b;
    }
    public int Max(int[] a) {
        int max = -999999999;
        for (int i = 0; i < a.length; i++) {
            if (a[i] > max) {
                max = a[i];
            }
        }
        return max;
    }
    public int Min(int[] a) {
        int min = 999999999;
        for (int i = 0; i < a.length; i++) {
            if (a[i] < min) {
                min = a[i];
            }
        }
        return min;
    }
    public void PrintArray(int[] a){
        for (int i = 0; i < a.length; i++) {
            if (i != a.length - 1) {
                System.out.print(a[i] + " ");
            } else {
                System.out.print(a[i]);
            }
        }
    }
    public void PrintArray(String[] a){
        for (int i = 0; i < a.length; i++) {
            if (i != a.length - 1) {
                System.out.print(a[i] + " ");
            } else {
                System.out.print(a[i]);
            }
        }
    }
}
