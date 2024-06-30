/*
 * Copyright (c) 2021-2024 - The MegaMek Team. All Rights Reserved.
 *
 * This file is part of MegaMek.
 *
 * MegaMek is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * MegaMek is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with MegaMek. If not, see <http://www.gnu.org/licenses/>.
 */
package megamek;

/**
 * These are constants that hold across the entire MegaMek Suite of MegaMek,
 * MegaMekLab, and MekHQ.
 */
public class ErrorConstants {
  private ErrorConstants() {
    throw new IllegalStateException("Error Constants - Utility Class");
  }

  // region General Errors
  public static final String INCORRECT_ARGUMENTS = "Incorrect arguments: %s%n%s";
  public static final String MEGAMEK_UNCAUGHT_EXCEPTION = "Uncaught %s detected. Please open up an issue containing all logs, the game save file, and customs at https://github.com/MegaMek/megamek/issues";
  // endregion General Errors

  // region Suite Preferences
  public static final String SUITE_PREFERENCES_COULD_NOT_SAVE = "Could not save nameToPreferencesMap to: %s";
  public static final String SUITE_PREFERENCES_ERROR_WRITING = "Error writing to the nameToPreferencesMap file: %s";
  public static final String SUITE_PREFERENCES_ERROR_READING_NODE = "Error reading node. %s";
  public static final String SUITE_PREFERENCES_ERROR_READING_FILE = "Error reading from the user preferences file: %s";
  public static final String SUITE_PREFERENCES_ERROR_NOT_FOUND = "Preferences File Not found. Ignoring";
  public static final String SUITE_PREFERENCES_ERROR_READING_ELEMENT = "Error reading elements for node: %s.";
  public static final String SUITE_PREFERENCES_ERROR_CLASS_NOT_FOUND = "No class with name %s found.";
  // endregion Suite Preferences
}
