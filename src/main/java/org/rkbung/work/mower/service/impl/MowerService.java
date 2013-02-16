package org.rkbung.work.mower.service.impl;

import org.apache.commons.collections.CollectionUtils;
import org.rkbung.work.mower.model.Location;
import org.rkbung.work.mower.model.Position;
import org.rkbung.work.mower.model.Sequence;
import org.rkbung.work.mower.service.IMowerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class MowerService implements IMowerService {
    private static final Logger LOGGER = LoggerFactory.getLogger(MowerService.class);

    @Override
    public List<Location> runMowers(Position upperRightFieldPosition, List<Sequence> sequences) {
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

    private Location playSequence(Sequence sequence, Position upperRightFieldPosition, List<Position> otherMowersPositions) {
        return null;
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
