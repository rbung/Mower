package org.rkbung.work.mower.service.impl;

import org.rkbung.work.mower.model.Location;
import org.rkbung.work.mower.model.Position;
import org.rkbung.work.mower.model.Sequence;
import org.rkbung.work.mower.service.IMowerService;

import java.util.List;

public class MowerService implements IMowerService {
    @Override
    public List<Location> doRun(Position upperRightFieldPosition, List<Sequence> sequences) {
        return null;
    }
}
