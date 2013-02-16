package org.rkbung.work.mower.service.impl;

import org.junit.Test;
import org.rkbung.work.mower.model.Direction;
import org.rkbung.work.mower.model.Location;
import org.rkbung.work.mower.model.Orientation;
import org.rkbung.work.mower.model.Position;
import org.rkbung.work.mower.model.Sequence;

import java.util.ArrayList;
import java.util.List;

/**
 * User: rkbung
 * Date: 16/02/13
 * Time: 20:37
 */
public class MowerServiceTest {
    private MowerService mowerService = new MowerService();

    @Test
    public void testRequiredMower() throws Exception {
        Position upperRightFieldPosition = new Position(5, 5);
        List<Sequence> sequences = new ArrayList<Sequence>();
        mowerService.doRun(upperRightFieldPosition, sequences);
    }
}
