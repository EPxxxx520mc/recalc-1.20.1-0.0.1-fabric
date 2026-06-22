package com.rimeveil.recalc.util;

import com.rimeveil.recalc.Recalc;

public class LogUtil {
    private static final boolean DEBUG = Boolean.getBoolean("recalc.debug");

    public static void info(String message) {
        Recalc.LOGGER.info(message);
    }

    public static void info(String format, Object... args) {
        Recalc.LOGGER.info(format, args);
    }

    public static void warn(String message) {
        Recalc.LOGGER.warn(message);
    }

    public static void warn(String format, Object... args) {
        Recalc.LOGGER.warn(format, args);
    }

    public static void error(String message) {
        Recalc.LOGGER.error(message);
    }

    public static void error(String format, Object... args) {
        Recalc.LOGGER.error(format, args);
    }

    public static void error(String message, Throwable throwable) {
        Recalc.LOGGER.error(message, throwable);
    }

    public static void debug(String message) {
        if (DEBUG) {
            Recalc.LOGGER.debug(message);
        }
    }

    public static void debug(String format, Object... args) {
        if (DEBUG) {
            Recalc.LOGGER.debug(format, args);
        }
    }
}
