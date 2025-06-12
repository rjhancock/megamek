package megamek.common.internationalization;

import megamek.client.ui.clientGUI.GUIPreferences;
import megamek.common.preference.PreferenceManager;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.ResourceBundle;

public class I18NMessages {
    private final HashMap<String, String> messages = new HashMap<>();
    private final MessageFormat messageFormat =  new MessageFormat("");

    /**
     * Initialize an instance with the calling class to get the resource bundle based upon default methodology of
     * Java (going from country_language -> country -> system_language -> system -> default_language -> default) as
     * the base Messages for this class.
     *
     * @param resourceBaseName Class that is creating this instance.
     */
    public I18NMessages(final Class<?> resourceBaseName) {
        Locale locale = PreferenceManager.getClientPreferences().getLocale();
        messageFormat.setLocale(locale);
        loadResourceBundle(resourceBaseName.getTypeName());
    }

    /**
     * Add a resource bundle to the collection. Any content loaded here will NOT override existing keys. This will
     * allow a class level override of common messages as needed.
     *
     * @param resourceBundleName String for the resource to be loaded.
     */
    public void loadResourceBundle(final String resourceBundleName) {
        ResourceBundle resourceBundle = ResourceBundle.getBundle(resourceBundleName);

        for(String key : resourceBundle.keySet()) {
            if (!messages.containsKey(key)) {
                messages.put(key, resourceBundle.getString(key));
            }
        }
    }

    /**
     * Takes the key, checks to see if it is available, then uses the found value to create a formatted string using
     * the Locale set by the user.
     *
     * @param key Key for the message within the bundles.
     * @param args Variable list of arguments to be passed to the formatters
     *
     * @return The formatted locale based string.
     */
    public String getString(final String key, Object... args) {
        if (messages.containsKey(key)) {
            String message =  messages.get(key);
            messageFormat.applyPattern(message);
            return messageFormat.format(args);
        }

        return "!!!" + key + "!!!";
    }
}
