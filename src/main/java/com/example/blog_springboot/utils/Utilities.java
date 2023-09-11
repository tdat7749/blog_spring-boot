package com.example.blog_springboot.utils;

import java.util.Random;

public class Utilities {
    public static String generateCode() {
        Random rnd = new Random();
        int number = rnd.nextInt(999999);

        return String.format("%06d", number);
    }
}
