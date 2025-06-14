/*
 * MegaMek - Copyright (C) 2004, 2005 Ben Mazur (bmazur@sev.org)
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by the Free
 * Software Foundation; either version 2 of the License, or (at your option)
 * any later version.
 *
 * This program is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 * or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License
 * for more details.
 */
package megamek.common.weapons.infantry;

import megamek.common.AmmoType;

/**
 * @author Ben Grills
 * @since Sep 7, 2005
 */
public class InfantrySupportVintageGatlingGunWeapon extends InfantryWeapon {
	private static final long serialVersionUID = -3164871600230559641L;

	public InfantrySupportVintageGatlingGunWeapon() {
		super();

		name = "Vintage Gatling Gun";
		setInternalName(name);
		addLookupName("InfantryVintageGatlingGun");
		addLookupName("Vintage Gatling Gun");
		ammoType = AmmoType.AmmoTypeEnum.INFANTRY;
		cost = 450000;
		tonnage = 0.296;
		bv = 0.0;
		flags = flags.or(F_NO_FIRES).or(F_DIRECT_FIRE).or(F_INF_SUPPORT).or(F_BALLISTIC);
		infantryDamage = 0.17;
		infantryRange = 2;
		crew = 4;
		ammoWeight = 0.015;
		ammoCost = 650;
		shots = 480;
		bursts = 40;
		rulesRefs = "195, AToW-C";
		techAdvancement.setTechBase(TechBase.ALL).setISAdvancement(1950, 1950, 1950, DATE_NONE, DATE_NONE)
		        .setISApproximate(false, false, false, false, false)
		        .setClanAdvancement(1950, 1950, 1950, DATE_NONE, DATE_NONE)
		        .setClanApproximate(false, false, false, false, false).setTechRating(TechRating.B)
		        .setAvailability(AvailabilityValue.E, AvailabilityValue.E, AvailabilityValue.F, AvailabilityValue.F);
	}
}
