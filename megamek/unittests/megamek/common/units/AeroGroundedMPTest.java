/*
 * Copyright (C) 2026 The MegaMek Team. All Rights Reserved.
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
 *
 * MechWarrior Copyright Microsoft Corporation. MegaMek was created under
 * Microsoft's "Game Content Usage Rules"
 * <https://www.xbox.com/en-US/developers/rules> and it is not endorsed by or
 * affiliated with Microsoft.
 */
package megamek.common.units;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.spy;

import megamek.common.MPCalculationSetting;
import megamek.common.equipment.EquipmentType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Regression tests for issue #8187: grounded Spheroid DropShips had non-zero run MP because {@link Aero#getRunMP} on
 * the grounded path was bypassing {@link Aero#getWalkMP}, which is where the "spheroid-grounded -> 0, aerodyne-grounded
 * -> thrust/2" rule (Total Warfare p.86) is applied.
 *
 * <p>The fix restores the call chain so {@code getRunMP} on a grounded aero defers to the grounded
 * {@code getWalkMP} (no run multiplier on the ground per rules).</p>
 */
class AeroGroundedMPTest {

    private static final int THRUST = 6;

    @BeforeAll
    static void setUp() {
        EquipmentType.initializeTypes();
    }

    private Dropship makeDropship(boolean spheroid, boolean airborne) {
        Dropship dropship = new Dropship();
        dropship.setSpheroid(spheroid);
        dropship.setOriginalWalkMP(THRUST);
        Dropship spy = spy(dropship);
        doReturn(airborne).when(spy).isAirborne();
        return spy;
    }

    @Test
    @DisplayName("Grounded Spheroid DropShip has 0 walk MP (TW p.86)")
    void groundedSpheroidWalkMpIsZero() {
        Dropship dropship = makeDropship(true, false);
        assertEquals(0, dropship.getWalkMP(MPCalculationSetting.STANDARD));
    }

    @Test
    @DisplayName("Grounded Spheroid DropShip has 0 run MP (regression for issue #8187)")
    void groundedSpheroidRunMpIsZero() {
        Dropship dropship = makeDropship(true, false);
        assertEquals(0, dropship.getRunMP(MPCalculationSetting.STANDARD));
    }

    @Test
    @DisplayName("Grounded Aerodyne DropShip walks at thrust/2 (taxi MP, TW p.86)")
    void groundedAerodyneWalkMpIsHalfThrust() {
        Dropship dropship = makeDropship(false, false);
        assertEquals(THRUST / 2, dropship.getWalkMP(MPCalculationSetting.STANDARD));
    }

    @Test
    @DisplayName("Grounded Aerodyne DropShip run MP equals walk MP (no run multiplier on the ground)")
    void groundedAerodyneRunMpEqualsWalk() {
        Dropship dropship = makeDropship(false, false);
        assertEquals(THRUST / 2, dropship.getRunMP(MPCalculationSetting.STANDARD));
    }

    @Test
    @DisplayName("Airborne Spheroid DropShip uses full thrust for walk and 1.5x for run")
    void airborneSpheroidStandardThrust() {
        Dropship dropship = makeDropship(true, true);
        assertEquals(THRUST, dropship.getWalkMP(MPCalculationSetting.STANDARD));
        assertEquals((int) Math.ceil(THRUST * 1.5), dropship.getRunMP(MPCalculationSetting.STANDARD));
    }

    @Test
    @DisplayName("Airborne Aerodyne DropShip uses full thrust for walk and 1.5x for run")
    void airborneAerodyneStandardThrust() {
        Dropship dropship = makeDropship(false, true);
        assertEquals(THRUST, dropship.getWalkMP(MPCalculationSetting.STANDARD));
        assertEquals((int) Math.ceil(THRUST * 1.5), dropship.getRunMP(MPCalculationSetting.STANDARD));
    }
}
