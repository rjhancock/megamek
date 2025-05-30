/*
 * MegaMek - Copyright (C) 2004 Ben Mazur (bmazur@sev.org)
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
package megamek.common.actions;

import megamek.common.*;
import megamek.common.equipment.WeaponMounted;
import megamek.common.options.OptionsConstants;
import megamek.common.weapons.bayweapons.CapitalMissileBayWeapon;
import megamek.common.weapons.capitalweapons.CapitalMissileWeapon;

import java.io.Serializable;
import java.util.Vector;

/**
 * ArtilleryAttackAction Holds the data needed for an artillery attack in
 * flight.
 */
public class ArtilleryAttackAction extends WeaponAttackAction implements Serializable {
    private static final long serialVersionUID = -3893844894076028005L;
    protected int turnsTilHit;
    private Vector<Integer> spotterIds = new Vector<>(); // IDs of possible spotters, won't know
    // until it lands.
    protected int playerId;
    private Coords firingCoords;
    private Coords oldTargetCoords;

    public ArtilleryAttackAction(int entityId, int targetType, int targetId,
                                 int weaponId, Game game) {
        super(entityId, targetType, targetId, weaponId);
        playerId = game.getEntity(entityId).getOwnerId();
        firingCoords = game.getEntity(entityId).getPosition();
        Targetable target = getTarget(game);
        EquipmentType eType = getEntity(game).getEquipment(weaponId).getType();
        WeaponType wType = (WeaponType) eType;
        WeaponMounted mounted = (WeaponMounted) getEntity(game).getEquipment(weaponId);
        // Remove altitude from Artillery Flak and ADA distance calcs
        if ((target != null) && target.isAirborne() && (mounted.getLinkedAmmo() != null)
                  && mounted.getLinkedAmmo().getType().countsAsFlak()) {
            turnsTilHit = 0;
            return;
        }

        int distance = Compute.effectiveDistance(game, getEntity(game), target);

        if (getEntity(game).usesWeaponBays() && wType.getAtClass() == WeaponType.CLASS_ARTILLERY) {
            for (WeaponMounted bayW : mounted.getBayWeapons()) {
                // TO:AR p.149
                WeaponType bayWType = bayW.getType();
                if (bayWType.hasFlag(WeaponType.F_CRUISE_MISSILE)) {
                    turnsTilHit = 1 + (distance / Board.DEFAULT_BOARD_HEIGHT / 5);
                    break;
                } else if (getEntity(game).isAirborne() && !getEntity(game).isSpaceborne()) {
                    if (getEntity(game).getAltitude() < 9) {
                        turnsTilHit = 1;
                    } else {
                        turnsTilHit = 2;
                    }
                } else {
                    turnsTilHit = Compute.turnsTilHit(distance);
                }
            }
            return;
        }
        // Capital missiles fired at bearings-only ranges will act like artillery and use this aaa.
        // An aaa will only be returned if the weapon is set to the correct mode
        if (mounted.isInBearingsOnlyMode()
                && distance >= RangeType.RANGE_BEARINGS_ONLY_MINIMUM) {
            this.launchVelocity = game.getOptions().intOption(OptionsConstants.ADVAERORULES_STRATOPS_BEARINGS_ONLY_VELOCITY);
            turnsTilHit = distance / launchVelocity;
            return;
        }
        // Capital missiles fired surface to surface as artillery have a flight time of their capital hex range / 6
        if (wType instanceof CapitalMissileWeapon || wType instanceof CapitalMissileBayWeapon) {
            turnsTilHit = (distance / Board.DEFAULT_BOARD_HEIGHT);
            return;
        }
        // Currently, spaceborne entities also count as airborne, though the reverse is not true.
        // See TO p181. Flight time is 1 turn at altitude 1-8,  2 turns at alt 9.
        if (getEntity(game).isAirborne() && !getEntity(game).isSpaceborne()) {
            if (getEntity(game).getAltitude() < 9) {
                turnsTilHit = 1;
            } else {
                turnsTilHit = 2;
            }
        } else if (eType.hasFlag(WeaponType.F_CRUISE_MISSILE)) {
            // See TO p181. Cruise missile flight time is (1 + (Mapsheets / 5, round down)
            turnsTilHit = 1 + (distance / Board.DEFAULT_BOARD_HEIGHT / 5);
        } else {
            turnsTilHit = Compute.turnsTilHit(distance);
        }
    }

    public Vector<Integer> getSpotterIds() {
        return spotterIds;
    }

    public int getPlayerId() {
        return playerId;
    }

    public void setSpotterIds(Vector<Integer> spotterIds) {
        this.spotterIds = spotterIds;
    }

    public void setCoords(Coords coords) {
        this.firingCoords = coords;
    }

    public Coords getCoords() {
        return this.firingCoords;
    }

    // For use with AMS and artillery to-hit tables
    public void setOldTargetCoords(Coords coords) {
        this.oldTargetCoords = coords;
    }

    public Coords getOldTargetCoords() {
        return this.oldTargetCoords;
    }

    /*
     * Updates the turnsTilHit value of this aaa
     * Needed after aaa setup by bearings-only missiles, which have a variable velocity
     */
    @Override
    public void updateTurnsTilHit(Game game) {
        // adjust distance for gravity
        this.turnsTilHit = Compute.turnsTilBOMHit(game, getEntity(game), getTarget(game), launchVelocity);
    }

    public int getTurnsTilHit() {
        return this.turnsTilHit;
    }

    public void setTurnsTilHit(int turnsTilHit) {
        this.turnsTilHit = turnsTilHit;
    }

    public void decrementTurnsTilHit() {
        decrementTurnsTilHit(1);
    }

    public void decrementTurnsTilHit(int numTurns) {
        this.turnsTilHit-=numTurns;
    }
}
