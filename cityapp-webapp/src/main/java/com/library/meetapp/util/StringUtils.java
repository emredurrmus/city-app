package com.library.meetapp.util;

import java.util.Arrays;
import java.util.stream.Collectors;

public class StringUtils {


    public static String initials(String... strings) {
        return Arrays.asList(strings).stream().filter(str -> str != null && !str.isEmpty()).map(str -> str.substring(0, 1).toUpperCase()).collect(Collectors.joining());
    }



    public static String concat(String... strings) {
        return Arrays.asList(strings).stream().filter(str -> str != null).collect(Collectors.joining(" "));
    }


    public static String replaceTrChars(String filename) {
        String[] trChars = {"ç", "ğ", "ı", "ö", "ş", "ü", "Ç", "Ğ", "İ", "Ö", "Ş", "Ü"};
        String[] replacementChars = {"c", "g", "i", "o", "s", "u", "C", "G", "I", "O", "S", "U"};
        for (int i = 0; i < trChars.length; i++) {
            filename = filename.replaceAll(trChars[i], replacementChars[i]);
        }
        return filename;
    }




}
