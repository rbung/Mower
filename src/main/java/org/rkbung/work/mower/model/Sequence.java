package org.rkbung.work.mower.model;

import java.util.List;

public class Sequence extends BaseObject {
    private Location initialLocation;

    private List<Direction> directions;

    public Sequence(Location initialLocation, List<Direction> directions) {
        this.initialLocation = initialLocation;
        this.directions = directions;
    }

    public Location getInitialLocation() {
        return initialLocation;
    }

    public void setInitialLocation(Location initialLocation) {
        this.initialLocation = initialLocation;
    }

    public List<Direction> getDirections() {
        return directions;
    }

    public void setDirections(List<Direction> directions) {
        this.directions = directions;
    }
}
