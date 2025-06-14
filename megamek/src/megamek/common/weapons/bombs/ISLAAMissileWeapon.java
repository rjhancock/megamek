/**
 * MegaMek - Copyright (C) 2005 Ben Mazur (bmazur@sev.org)
 *
 *  This program is free software; you can redistribute it and/or modify it
 *  under the terms of the GNU General Public License as published by the Free
 *  Software Foundation; either version 2 of the License, or (at your option)
 *  any later version.
 *
 *  This program is distributed in the hope that it will be useful, but
 *  WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 *  or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License
 *  for more details.
 */
package megamek.common.weapons.bombs;

import megamek.common.AmmoType;
import megamek.common.BombType;
import megamek.common.BombType.BombTypeEnum;
import megamek.common.weapons.missiles.ThunderBoltWeapon;

/**
 * @author Jay Lawson
 */
public class ISLAAMissileWeapon extends ThunderBoltWeapon {

    /**
     *
     */
    private static final long serialVersionUID = 6262048986109960442L;

    public ISLAAMissileWeapon() {
        super();

        this.name = "Light Air-to-Air (LAA) Missiles";
        this.setInternalName(BombTypeEnum.LAA.getWeaponName());
        this.heat = 0;
        this.damage = 6;
        this.rackSize = 1;
        this.minimumRange = 7;
        this.shortRange = 14;
        this.mediumRange = 21;
        this.longRange = 28;
        this.extremeRange = 42;
        this.tonnage = 0.5;
        this.criticals = 0;
        this.hittable = false;
        this.bv = 0;
        this.cost = 6000;
        this.flags = flags.or(F_MISSILE).or(F_LARGEMISSILE).or(F_BOMB_WEAPON).andNot(F_MEK_WEAPON).andNot(F_TANK_WEAPON);
        this.shortAV = 6;
        this.medAV = 6;
        this.maxRange = RANGE_MED;
        this.ammoType = AmmoType.AmmoTypeEnum.LAA_MISSILE;
        this.capital = false;
        this.missileArmor = 6;
        rulesRefs = "359, TO";
        techAdvancement.setTechBase(TechBase.IS)
    	.setIntroLevel(false)
    	.setUnofficial(false)
        .setTechRating(TechRating.E)
        .setAvailability(AvailabilityValue.X, AvailabilityValue.X, AvailabilityValue.F, AvailabilityValue.D)
        .setISAdvancement(3069, 3072, DATE_NONE, DATE_NONE, DATE_NONE)
        .setISApproximate(true, false, false, false, false)
        .setPrototypeFactions(Faction.FW)
        .setProductionFactions(Faction.FW);
    }
}
