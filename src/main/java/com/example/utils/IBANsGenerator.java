package com.example.utils;

import java.util.Random;

public class IBANsGenerator {
    public static String generateIBAN() {
        Random random = new Random();
        StringBuilder stringBuilder = new StringBuilder("BGN");
        for (int i = 0; i < 10; i++) {
            int randomNumber = random.nextInt(10); // Generate a random number between 0 and 9
            stringBuilder.append(randomNumber);
        }

        return stringBuilder.toString();
    }
}