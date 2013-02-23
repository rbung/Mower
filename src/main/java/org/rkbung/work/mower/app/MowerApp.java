package org.rkbung.work.mower.app;

import com.google.common.io.Files;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.rkbung.work.mower.model.Direction;
import org.rkbung.work.mower.model.Location;
import org.rkbung.work.mower.model.Orientation;
import org.rkbung.work.mower.model.Position;
import org.rkbung.work.mower.model.Sequence;
import org.rkbung.work.mower.service.IMowerService;
import org.rkbung.work.mower.service.impl.MowerService;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MowerApp {
    private static final String BLANK = " ";

    private Map<String, Orientation> ORIENTATIONS_MAPPING = new HashMap<String, Orientation>();

    private Map<String, Direction> DIRECTIONS_MAPPING = new HashMap<String, Direction>();

    private IMowerService mowerService;

    public MowerApp() {
        mowerService = new MowerService();
        ORIENTATIONS_MAPPING.put("N", Orientation.NORTH);
        ORIENTATIONS_MAPPING.put("S", Orientation.SOUTH);
        ORIENTATIONS_MAPPING.put("E", Orientation.EAST);
        ORIENTATIONS_MAPPING.put("W", Orientation.WEST);
        DIRECTIONS_MAPPING.put("A", Direction.A);
        DIRECTIONS_MAPPING.put("D", Direction.D);
        DIRECTIONS_MAPPING.put("G", Direction.G);
    }

    public static void main(String[] args) {
        if (ArrayUtils.getLength(args) != 1) {
            usage();
        }
        File mowersFile = new File(args[0]);
        if (!mowersFile.exists()) {
            System.out.println("File does not exist !");
            usage();
        } else {
            MowerApp mowerApp = new MowerApp();
            mowerApp.runMowers(mowersFile);
        }
    }

    private void runMowers(File mowersFile) {
        List<String> lines = new ArrayList<String>();
        try {
            lines = Files.readLines(mowersFile, Charset.defaultCharset());
        } catch (IOException e) {
            invalidFile();
        }
        if (lines.size() < 3) {
            invalidFile();
        }
        Position upperRightFieldPosition = getUpperRightPosition(lines.get(0));
        List<Sequence> sequences = readSequences(lines);
        final List<Location> locations = mowerService.runMowers(upperRightFieldPosition, sequences);
        for (Location location : locations) {
            System.out.println(MessageFormat.format("{0} {1} {2}", location.getPosition().getX(), location.getPosition().getY(), location.getOrientation()));
        }
        System.exit(0);
    }

    private List<Sequence> readSequences(List<String> lines) {
        List<Sequence> sequences = new ArrayList<Sequence>();
        List<Location> locations = new ArrayList<Location>();
        List<List<Direction>> directions = new ArrayList<List<Direction>>();
        for (int i = 1; i < lines.size(); i++) {
            if (i % 2 != 0) {
                locations.add(getLocation(lines.get(i)));
            } else {
                directions.add(getDirections(lines.get(i)));
            }
        }
        if (locations.size() != directions.size()) {
            invalidFile();
        }
        for (int i = 0; i < locations.size(); i++) {
            Sequence sequence = new Sequence(locations.get(i), directions.get(i));
            sequences.add(sequence);
        }
        return sequences;
    }

    private List<Direction> getDirections(String line) {
        List<Direction> result = new ArrayList<Direction>();
        if (StringUtils.containsOnly(line, "ADG")) {
            final char[] chars = line.toCharArray();
            for (char aChar : chars) {
                result.add(DIRECTIONS_MAPPING.get(String.valueOf(aChar)));
            }
        } else {
            invalidFile();
        }
        return result;
    }

    private Location getLocation(String line) {
        final String[] split = StringUtils.split(line, BLANK);
        if (split.length != 3 || !StringUtils.isNumeric(split[0]) || !StringUtils.isNumeric(split[1]) || ORIENTATIONS_MAPPING.get(split[2]) == null) {
            invalidFile();
        }
        Position position = new Position(Integer.valueOf(split[0]), Integer.valueOf(split[1]));
        return new Location(position, ORIENTATIONS_MAPPING.get(split[2]));
    }


    private Position getUpperRightPosition(String line) {
        final String[] split = StringUtils.split(line, BLANK);
        if (split.length != 2 || !StringUtils.isNumeric(split[0]) || !StringUtils.isNumeric(split[1])) {
            invalidFile();
        }
        return new Position(Integer.valueOf(split[0]), Integer.valueOf(split[1]));
    }

    private static void usage() {
        System.out.println("Usage :  java MowerApp PATH_TO_MOWERS_FILE");
        System.exit(-1);
    }

    private static void invalidFile() {
        System.out.println("Invalid file !");
        System.exit(-1);
    }
}
