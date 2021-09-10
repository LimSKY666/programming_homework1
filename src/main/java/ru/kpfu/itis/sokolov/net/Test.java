package ru.kpfu.itis.sokolov.net;

import java.util.HashMap;
import java.util.Map;

public class Test {
    public static void main(String[] args) {
        Map<String, String> hashMap1 = new HashMap<>();
        Map<String, String> hashMap2 = new HashMap<>();
        hashMap1.put("Content-Type","application/json");
        hashMap1.put("Accept","application/json");
        hashMap2.put("x-forwarded-proto","https");
        hashMap2.put("x-forwarded","https");

        System.out.println(new Homework().post("https://postman-echo.com/post", hashMap1, hashMap2));
    }
}
