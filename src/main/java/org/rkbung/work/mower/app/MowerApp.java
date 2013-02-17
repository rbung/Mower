package org.rkbung.work.mower.app;

import org.apache.commons.lang.ArrayUtils;
import org.rkbung.work.mower.service.IMowerService;
import org.rkbung.work.mower.service.impl.MowerService;

import java.io.File;

public class MowerApp {
    IMowerService mowerService;

    public MowerApp() {
        mowerService = new MowerService();
    }

    public static void main(String[] args) {
        if(ArrayUtils.getLength(args) != 1) {
            System.out.println("Usage :  java MowerApp PATH_TO_MOWERS_FILE");
            System.exit(-1);
        }
        File mowersFile = new File(args[0]);
        if(!mowersFile.exists()){
            System.out.println("File does not exist !");
            System.exit(-1);
        }
    }
}
