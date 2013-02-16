package org.rkbung.work.mower.model;

public class Location extends BaseObject {
    private Position position;

    private Orientation orientation;

    public Location(Position position, Orientation orientation) {
        this.position = position;
        this.orientation = orientation;
    }
}
