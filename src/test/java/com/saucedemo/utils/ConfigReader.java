package com.saucedemo.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
/**
 * Loads and exposes configuration values defined in
 * src/test/resources/config.properties, so values like the base URL
 * or the explicit wait timeout are not hardcoded inside the page
 * objects or the tests.
 *
 * Implemented as a lazily-initialized utility class: the properties
 * file is read from disk only once, the first time any value is asked
 * for, and cached afterwards.
 */

public class ConfigReader {

    private static final String CONFIG_FILE_PATH = "config.properties";
    private static Properties properties;

    private ConfigReader() {
    }

    /**
     * Loads config.properties from the classpath the first time it's
     * called, and reuses the same Properties object afterwards.
     *
     * @return the loaded configuration properties
     */
    private static Properties getProperties() {
        if (properties == null) {
            properties = new Properties();
            try (InputStream inputStream =
                         ConfigReader.class.getClassLoader().getResourceAsStream(CONFIG_FILE_PATH)) {

                if (inputStream == null) {
                    throw new RuntimeException(
                            "Unable to find " + CONFIG_FILE_PATH + " in the classpath (src/test/resources).");
                }
                properties.load(inputStream);

            } catch (IOException e) {
                throw new RuntimeException("Failed to load " + CONFIG_FILE_PATH, e);
            }
        }
        return properties;
    }

    /**
     * @return the base URL of the application under test, as defined
     *         by the "base.url" property.
     */
    public static String getBaseUrl() {
        return getProperties().getProperty("base.url");
    }

    /**
     * @return how many seconds explicit waits (WebDriverWait) should
     *         wait before timing out, as defined by the
     *         "explicit.wait.seconds" property. Defaults to 10 if the
     *         property is missing.
     */
    public static int getExplicitWaitSeconds() {
        return Integer.parseInt(getProperties().getProperty("explicit.wait.seconds", "10"));
    }
}