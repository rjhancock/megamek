/*
 * 2025 The MegaMek Team. All Rights Reserved.
 *
 * This file is part of MegaMek.
 *
 * MegaMek is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License (GPL),
 * version 3 or (at your option) any later version,
 * as published by the Free Software Foundation.
 *
 * MegaMek is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty
 * of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU General Public License for more details.
 *
 * A copy of the GPL should have been included with this project;
 * if not, see <https://www.gnu.org/licenses/>.
 *
 * NOTICE: The MegaMek organization is a non-profit group of volunteers
 * creating free software for the BattleTech community.
 *
 * MechWarrior, BattleMech, `Mech and AeroTech are registered trademarks
 * of The Topps Company, Inc. All Rights Reserved.
 *
 * Catalyst Game Labs and the Catalyst Game Labs logo are trademarks of
 * InMediaRes Productions, LLC.
 */
package megamek.common.internationalization;

import java.text.ChoiceFormat;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.ResourceBundle;

import megamek.common.preference.PreferenceManager;

public class I18NMessages {
    private final HashMap<String, String> messages = new HashMap<>();
    private final MessageFormat messageFormat = new MessageFormat("");

    /**
     * Initialize an instance with the calling class to get the resource bundle based upon default methodology of Java
     * (going from country_language -> country -> system_language -> system -> default_language -> default) as the base
     * Messages for this class.
     *
     * @param resourceBaseName Class that is creating this instance.
     */
    public I18NMessages(final Class<?> resourceBaseName) {
        Locale locale = PreferenceManager.getClientPreferences().getLocale();
        messageFormat.setLocale(locale);
        loadResourceBundle(resourceBaseName.getTypeName());
    }

    /**
     * Add a resource bundle to the collection. Any content loaded here will NOT override existing keys. This will allow
     * a class level override of common messages as needed.
     *
     * @param resourceBundleName String for the resource to be loaded.
     */
    public void loadResourceBundle(final String resourceBundleName) {
        ResourceBundle resourceBundle = ResourceBundle.getBundle(resourceBundleName);

        for (String key : resourceBundle.keySet()) {
            if (!messages.containsKey(key)) {
                messages.put(key, resourceBundle.getString(key));
            }
        }
    }

    /**
     * Takes the key, checks to see if it is available, then uses the found value to create a formatted string using
     * the
     * Locale set by the user.
     * <p>
     * The format for the messages for use with this is customizable and will allow localization of numbers, dates, and
     * currency to local formats.
     * <p>
     * The first example uses the static method {@link MessageFormat#format}, which internally creates a MessageFormat
     * for one-time use:
     * <p>
     * <code>
     * <pre>
     * int planet = 7;
     * String event = "a disturbance in the Force";
     * String result = MessageFormat.format("At {1,time} on {1,date}, there was {2} on planet {0,number,integer}.", planet, new Date(), event);
     * </pre>
     * </code>
     * <p>
     * The output is: At 12:30 PM on Jul 3, 2053, there was a disturbance in the Force on planet 7.
     * <p>
     * The following example creates a MessageFormat instance that can be used repeatedly:
     * <p>
     * <code>
     * <pre>
     * int fileCount = 1273;
     * String diskName = "MyDisk";
     * Object[] testArgs = {new Long(fileCount), diskName};
     * MessageFormat form = new MessageFormat("The disk \"{1}\" contains {0} file(s).");
     * System.out.println(form.format(testArgs));
     * </pre>
     * </code>
     * <p>
     * The output with different values for fileCount:
     * <ul>
     *  <li>The disk "MyDisk" contains 0 file(s).</li>
     *  <li>The disk "MyDisk" contains 1 file(s).</li>
     *  <li>The disk "MyDisk" contains 1,273 file(s).</li>
     * </ul>
     * <p>
     * For more sophisticated patterns, you can use a {@link ChoiceFormat} to produce correct forms for singular and
     * plural:
     * <code>
     * <pre>
     * MessageFormat form = new MessageFormat("The disk \"{1}\" contains {0}.");
     *
     * double[] fileLimits = {0,1,2};
     * String[] filePart = { "no files", "one file", "{0,number} files" };
     *
     * ChoiceFormat fileForm = new ChoiceFormat(fileLimits, filePart);
     *
     * form.setFormatByArgumentIndex(0, fileForm);
     *
     * int fileCount = 1273;
     * String diskName = "MyDisk";
     * Object[] testArgs = {new Long(fileCount), diskName};
     *
     * System.out.println(form.format(testArgs));
     * </pre>
     * </code>
     * <p>
     * The output with different values for fileCount:
     * <ul>
     *     <li>The disk "MyDisk" contains no files.</li>
     *     <li>The disk "MyDisk" contains one file.</li>
     *     <li>The disk "MyDisk" contains 1,273 files.</li>
     * </ul>
     * <p>
     * You can create the {@link ChoiceFormat} programmatically, as in the above example, or by using a pattern. See
     * {@link ChoiceFormat} for more information.
     * <code>
     *      <pre>
     * form.applyPattern("There {0,choice,0#are no files|1#is one file|1&lt;are {0,number,integer} files}.");
     *      </pre>
     * </code>
     * <p>
     * Note: As we see above, the string produced by a {@link ChoiceFormat} in {@link MessageFormat} is treated as
     * special occurrences of '{' are used to indicate subformats, and cause recursion. If you create both a
     * {@link MessageFormat} and {@link ChoiceFormat} programmatically (instead of using the string patterns), then
     * be careful not to produce a format that recurses on itself, which will cause an infinite loop.
     * <p>
     * When a single argument is parsed more than once in the string, the last match will be the final result of the
     * parsing. For example,
     * <p>
     * <code><pre>
     * MessageFormat mf = new MessageFormat("{0,number,#.##}, {0,number,#.#}");
     * Object[] objs = {new Double(3.1415)};
     * String result = mf.format( objs ); // result now equals "3.14, 3.1"
     * objs = null;
     * objs = mf.parse(result, new ParsePosition(0)); // objs now equals {new Double(3.1)}
     * </pre></code>
     * <p>
     * Likewise, parsing with a MessageFormat object using patterns containing multiple occurrences of the same argument
     * would return the last match. For example,
     * <p>
     * <code><pre>
     * MessageFormat mf = new MessageFormat("{0}, {0}, {0}");
     * String forParsing = "x, y, z";
     * Object[] objs = mf.parse(forParsing, new ParsePosition(0)); // result now equals {new String("z")}
     * </pre></code>
     *
     * @param key  Key for the message within the bundles.
     * @param args Variable list of arguments to be passed to the formatters
     *
     * @return The formatted locale-based string.
     */
    public String getString(final String key, Object... args) {
        if (messages.containsKey(key)) {
            String message = messages.get(key);
            messageFormat.applyPattern(message);
            return messageFormat.format(args);
        }

        return "!!!" + key + "!!!";
    }
}
