package dev.korgi.utils;

public class Utils {
    public static void print(Object printOb){
        System.out.println(printOb);
    }

    public static void printList(Object[] printlsit){
        for (Object object : printlsit) {
            System.out.println(object);
        }
    }

    public static int getIndex(Object[] list, Object item){
        for (int i = 0; i < list.length; i++){
            if (list[i].equals(item)){
                return i;
            }
        }
        return 0;
    }
}
