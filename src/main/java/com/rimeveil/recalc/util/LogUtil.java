
package com.rimeveil.recalc.util;

import com.rimeveil.recalc.Recalc;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(Recalc.MOD_ID);
    
    private static final boolean DEBUG = Boolean.getBoolean("recalc.debug");
    
    public static void info(String message) {
        LOGGER.info(message);
    }
    
    public static void info(String format, Object... args) {
        LOGGER.info(format, args);
    }
    
    public static void warn(String message) {
        LOGGER.warn(message);
    }
    
    public static void error(String message) {
        LOGGER.error(message);
    }
    
    public static void error(String message, Throwable throwable) {
        LOGGER.error(message, throwable);
    }
    
    public static void debug(String message) {
        if (DEBUG) {
            LOGGER.debug(message);
        }
    }
    
    public static void debug(String format, Object... args) {
        if (DEBUG) {
            LOGGER.debug(format, args);
        }
    }
}
