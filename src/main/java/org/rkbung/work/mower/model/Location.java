package org.rkbung.work.mower.model;

public class Location extends BaseObject {
    private Position position;

    private Orientation orientation;

    public Location(int x, int y, Orientation orientation) {
        this.position = new Position(x, y);
        this.orientation = orientation;
    }
}
