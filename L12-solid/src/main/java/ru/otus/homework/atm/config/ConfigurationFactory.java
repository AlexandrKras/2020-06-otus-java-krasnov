package ru.otus.homework.atm.config;

import java.util.Map;

public class ConfigurationFactory {
    public static String RUR01011999 = "RUR01011999";
    public static String RUR12102017 = "RUR12102017";

    public static Map<Integer, Integer> getConfiguration(String confiName) {
        if (RUR01011999.equals(confiName)) {
            return new СonfigRUR01011999().createСonfig(10);
        }
        if (RUR12102017.equals(confiName)) {
            return new СonfigRUR12102017().createСonfig(10);
        }
        throw new IllegalArgumentException("unknown param:" + confiName);
    }
}
