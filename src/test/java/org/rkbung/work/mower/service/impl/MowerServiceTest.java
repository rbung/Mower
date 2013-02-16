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

import static org.hamcrest.core.Is.is;
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
}