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
public class LoggingConstants {
  private LoggingConstants() {
    throw new IllegalStateException("Logging Constants - Utility Class");
  }

  // region Server Logging
  public static final String STARTING_DEDICATED_SERVER = "Starting Dedicated Server. %s";
  public static final String STARTING_HOST_SERVER = "Starting Host Server. {0}";
  public static final String STARTING_CLIENT_SERVER = "Starting Client Server. %s";
  // endregion Server Logging

  // region Suite Preferences
  public static final String SUITE_PREFERENCES_SAVING = "Saving nameToPreferencesMap to: %s";
  public static final String SUITE_PREFERENCES_LOADING = "Loading user preferences from: %s";
  // endregion Suite Preferences
}
