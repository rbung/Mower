package org.rkbung.work.mower.service;

import org.rkbung.work.mower.model.Location;
import org.rkbung.work.mower.model.Position;
import org.rkbung.work.mower.model.Sequence;

import java.util.List;

public interface IMowerService {
    List<Location> doRun(Position upperRightFieldPosition, List<Sequence> sequences);
}
