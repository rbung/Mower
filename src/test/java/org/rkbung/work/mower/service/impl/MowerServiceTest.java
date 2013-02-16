package org.rkbung.work.mower.service.impl;

import org.junit.Test;
import org.rkbung.work.mower.model.Direction;
import org.rkbung.work.mower.model.Location;
import org.rkbung.work.mower.model.Orientation;
import org.rkbung.work.mower.model.Position;
import org.rkbung.work.mower.model.Sequence;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.util.Collections.EMPTY_LIST;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;

public class MowerServiceTest {
    private MowerService mowerService = new MowerService();

    @Test
    public void testRequiredMower() throws Exception {
        Position upperRightFieldPosition = new Position(5, 5);
        List<Sequence> sequences = new ArrayList<Sequence>();
        Location location1 = new Location(new Position(1, 2), Orientation.NORTH);
        List<Direction> directions1 = Arrays.asList(Direction.G, Direction.A, Direction.G, Direction.A, Direction.G,
                Direction.A, Direction.G, Direction.A, Direction.A);
        Sequence sequence1 = new Sequence(location1, directions1);
        sequences.add(sequence1);
        Location location2 = new Location(new Position(3, 3), Orientation.EAST);
        List<Direction> directions2 = Arrays.asList(Direction.A, Direction.A, Direction.D, Direction.A, Direction.A,
                Direction.D, Direction.A, Direction.D, Direction.D, Direction.A);
        Sequence sequence2 = new Sequence(location2, directions2);
        sequences.add(sequence2);
        final List<Location> locations = mowerService.runMowers(upperRightFieldPosition, sequences);
        Location expectedLocation1 = new Location(new Position(1, 3), Orientation.NORTH);
        Location expectedLocation2 = new Location(new Position(5, 1), Orientation.EAST);
        assertThat(locations, is(notNullValue()));
        assertThat(locations.size(), is(2));
        assertThat(locations.get(0), is(expectedLocation1));
        assertThat(locations.get(1), is(expectedLocation2));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testUpperRightFieldPositionNull() throws Exception {
        List<Sequence> sequences = new ArrayList<Sequence>();
        mowerService.runMowers(null, sequences);
    }

    @Test
    public void testGetOtherMowersPositionOneSequence() throws Exception {
        Sequence sequence = new Sequence(new Location(new Position(1, 1), Orientation.NORTH), null);
        List<Sequence> sequences = Arrays.asList(sequence);
        final List<Position> otherMowersPositions = mowerService.getOtherMowersPositions(sequence, sequences);
        assertThat(otherMowersPositions, is(EMPTY_LIST));
    }

    @Test
    public void testGetOtherMowersPositionTwoSequence() throws Exception {
        final Position position1 = new Position(1, 1);
        Sequence sequence1 = new Sequence(new Location(position1, Orientation.NORTH), null);
        final Position position2 = new Position(3, 3);
        Sequence sequence2 = new Sequence(new Location(position2, Orientation.NORTH), null);
        List<Sequence> sequences = Arrays.asList(sequence1, sequence2);
        final List<Position> otherMowersPositions = mowerService.getOtherMowersPositions(sequence1, sequences);
        assertThat(otherMowersPositions, not(EMPTY_LIST));
        assertThat(otherMowersPositions.get(0), is(position2));
    }

    @Test
    public void testTurnRight() throws Exception {
        assertThat(mowerService.turnRight(Orientation.NORTH), is(Orientation.EAST));
        assertThat(mowerService.turnRight(Orientation.EAST), is(Orientation.SOUTH));
        assertThat(mowerService.turnRight(Orientation.SOUTH), is(Orientation.WEST));
        assertThat(mowerService.turnRight(Orientation.WEST), is(Orientation.NORTH));
    }

    @Test
    public void testTurnLeft() throws Exception {
        assertThat(mowerService.turnLeft(Orientation.NORTH), is(Orientation.WEST));
        assertThat(mowerService.turnLeft(Orientation.WEST), is(Orientation.SOUTH));
        assertThat(mowerService.turnLeft(Orientation.SOUTH), is(Orientation.EAST));
        assertThat(mowerService.turnLeft(Orientation.EAST), is(Orientation.NORTH));
    }
}