/*
 * Copyright (C) 2021-2025 The MegaMek Team. All Rights Reserved.
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
package megamek;

/**
 * These are constants that hold across the entire MegaMek Suite of MegaMek, MegaMekLab, and MekHQ.
 */
public abstract class SuiteConstants {
    protected SuiteConstants() {
        throw new IllegalStateException("SuiteConstant Utility CLass");
    }

    // region General Constants
    public static final String PROJECT_NAME = "MegaMek Suite";
    public static final Version VERSION = new Version();
    public static final Version LAST_MILESTONE = new Version("0.50.06");
    public static final int MAXIMUM_D6_VALUE = 6;

    // This is used in creating the name of save files, e.g. the MekHQ campaign file
    public static final String FILENAME_DATE_FORMAT = "yyyyMMdd";
    // endregion General Constants

    // region Font Constants
    // FIXME : These uses all need to be converted into SuiteOptions, and this step was done to simplify and isolate
    //  places that will be required This is an accessibility issue
    public static final String FONT_ARIAL = "Arial";
    public static final String FONT_COURIER_NEW = "Courier New";
    public static final String FONT_DIALOG = "Dialog";
    public static final String FONT_HELVETICA = "Helvetica";
    public static final String FONT_MONOSPACED = "Monospaced";
    public static final String FONT_SANS_SERIF = "Sans Serif";
    // endregion Font Constants

    // region GUI Constants
    // endregion GUI Constants

    // region SuiteOptions
    // endregion SuiteOptions

    // region File Formats
    public static final String TRUETYPE_FONT = ".ttf";
    public static final String SCENARIO_EXT = ".mms";
    // endregion File Formats

    // region File Paths
    public static final String FONT_DIRECTORY = "data/fonts/";
    public static final String MHQ_PREFERENCES_FILE = "mmconf/mhq.preferences";
    public static final String MM_PREFERENCES_FILE = "mmconf/mm.preferences";
    public static final String MML_PREFERENCES_FILE = "mmconf/mml.preferences";
    // endregion File Paths
}
