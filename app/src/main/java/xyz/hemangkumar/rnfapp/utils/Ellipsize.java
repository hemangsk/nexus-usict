package xyz.hemangkumar.rnfapp.utils;

/**
 * Created by Hemang on 19/09/16.
 */
public class Ellipsize {
    public static String ellipsize(String input, int maxLength) {
        if (input == null || input.length() < maxLength) {
            return input;
        }
        return input.substring(0, maxLength) + "...";
    }
}
