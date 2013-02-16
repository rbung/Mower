package org.rkbung.work.mower.service.impl;

import org.apache.commons.collections.CollectionUtils;
import org.rkbung.work.mower.exception.CollisionException;
import org.rkbung.work.mower.exception.OutOfFieldException;
import org.rkbung.work.mower.model.Direction;
import org.rkbung.work.mower.model.Location;
import org.rkbung.work.mower.model.Orientation;
import org.rkbung.work.mower.model.Position;
import org.rkbung.work.mower.model.Sequence;
import org.rkbung.work.mower.service.IMowerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MowerService implements IMowerService {
    private static final Logger LOGGER = LoggerFactory.getLogger(MowerService.class);

    public static final List<Orientation> CARDINALITIES = Arrays.asList(Orientation.NORTH, Orientation.EAST, Orientation.SOUTH, Orientation.WEST);

    @Override
    public List<Location> runMowers(Position upperRightFieldPosition, List<Sequence> sequences) {
        validEntries(upperRightFieldPosition, sequences);
        if (upperRightFieldPosition == null) {
            throw new IllegalArgumentException("upperRightFieldPosition must not be null");
        }
        List<Location> result = new ArrayList<Location>();
        if (CollectionUtils.isNotEmpty(sequences)) {
            for (Sequence sequence : sequences) {
                List<Position> otherMowersPositions = getOtherMowersPositions(sequence, sequences);
                result.add(playSequence(sequence, upperRightFieldPosition, otherMowersPositions));
            }
        }
        return result;
    }

    private void validEntries(Position upperRightFieldPosition, List<Sequence> sequences) {
        //TODO (valider si le champs est correcte, si des tondeuses ne sont pas superposées, etc.
    }

    private Location playSequence(Sequence sequence, Position upperRightFieldPosition, List<Position> otherMowersPositions) {
        if (CollectionUtils.isNotEmpty(sequence.getDirections())) {
            for (Direction direction : sequence.getDirections()) {
                updateLocation(sequence.getInitialLocation(), direction, upperRightFieldPosition, otherMowersPositions);
            }
        }
        return sequence.getInitialLocation();
    }

    private void updateLocation(Location location, Direction direction, Position upperRightFieldPosition, List<Position> otherMowersPositions) {
        if (Direction.G == direction) {
            location.setOrientation(turnLeft(location.getOrientation()));
            LOGGER.info("Turn Left : orientation updated {}", location);
        } else if (Direction.D == direction) {
            location.setOrientation(turnRight(location.getOrientation()));
            LOGGER.info("Turn Right : orientation updated {}", location);
        } else {
            try {
                location.setPosition(moveOn(location, upperRightFieldPosition, otherMowersPositions));
                LOGGER.info("Move on : position updated {}", location);
            } catch (CollisionException e) {
                LOGGER.info("Position not updated : collision", e);
            } catch (OutOfFieldException e) {
                LOGGER.info("New position : out of field", e);
            }
        }
    }

    protected Position moveOn(Location location, Position upperRightFieldPosition, List<Position> otherMowersPositions) {
        Position nextPosition = getNextPosition(location);
        validIsInField(nextPosition, upperRightFieldPosition);
        validNotCollision(nextPosition, otherMowersPositions);
        return nextPosition;
    }

    private void validNotCollision(Position nextPosition, List<Position> otherMowersPositions) {
        if (otherMowersPositions.contains(nextPosition)) {
            throw new CollisionException("cause nextPosition is " + nextPosition);
        }
    }

    protected void validIsInField(Position nextPosition, Position upperRightFieldPosition) {
        if (nextPosition.getX() < 0
                || nextPosition.getY() < 0
                || nextPosition.getX() > upperRightFieldPosition.getX()
                || nextPosition.getX() > upperRightFieldPosition.getX()) {
            throw new OutOfFieldException("cause nextPosition is " + nextPosition);
        }
    }

    private Position getNextPosition(Location location) {
        Position nextPosition = new Position(location.getPosition().getX(), location.getPosition().getY());
        if (Orientation.NORTH == location.getOrientation()) {
            nextPosition.setY(nextPosition.getY() + 1);
        }
        if (Orientation.SOUTH == location.getOrientation()) {
            nextPosition.setY(nextPosition.getY() - 1);
        }
        if (Orientation.WEST == location.getOrientation()) {
            nextPosition.setX(nextPosition.getX() - 1);
        }
        if (Orientation.EAST == location.getOrientation()) {
            nextPosition.setX(nextPosition.getX() + 1);
        }
        return nextPosition;
    }

    protected Orientation turnRight(Orientation orientation) {
        return CARDINALITIES.get((CARDINALITIES.indexOf(orientation) + 1) % CARDINALITIES.size());
    }

    protected Orientation turnLeft(Orientation orientation) {
        if ((CARDINALITIES.indexOf(orientation) == 0)) {
            return Orientation.WEST;
        } else {
            return CARDINALITIES.get((CARDINALITIES.indexOf(orientation) + -1) % CARDINALITIES.size());
        }
    }

    protected List<Position> getOtherMowersPositions(Sequence currentSequence, List<Sequence> sequences) {
        List<Position> result = new ArrayList<Position>();
        for (Sequence sequence : sequences) {
            if (!currentSequence.equals(sequence)) {
                result.add(sequence.getInitialLocation().getPosition());
            }
        }
        LOGGER.info("getOtherMowersPositions : {}", result);
        return result;
    }
}
