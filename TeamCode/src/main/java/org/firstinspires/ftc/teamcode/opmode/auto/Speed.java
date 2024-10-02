package org.firstinspires.ftc.teamcode.opmode.auto;

import org.firstinspires.ftc.teamcode.subsystems.drive.mec.DriveConstants;
import org.firstinspires.ftc.teamcode.util.trajectorysequence.container.TrajectorySequenceConstraints;

public class Speed {
    //TODO: Fix the Speeds
    public static double baseVel = DriveConstants.MAX_VEL; // value
    public static double baseAccel = DriveConstants.MAX_ACCEL; // value
    public static double turnVel = DriveConstants.MAX_VEL; // value
    public static double turnAccel = DriveConstants.MAX_ANG_ACCEL; // value
    
    public static double slowVel = baseVel * 0.8; // value
    public static double slowAccel = baseAccel * 0.8; // value
    public static double slowTurnVel = turnVel * 0.8; // value
    public static double slowTurnAccel = turnAccel * 0.8; // value

    public static double fastVel = baseVel * 1.1; // value
    public static double fastAccel = baseAccel * 1.1; // value
    public static double fastTurnVel = turnVel * 1.1; // value
    public static double fastTurnAccel = turnAccel * 1.1; // value

    public static TrajectorySequenceConstraints getPickupConstraints() {
        return new TrajectorySequenceConstraints(
            (s, a, b, c) -> {
                if (s > 18) {
                    return baseVel * 0.4;
                } else {
                    return baseVel;
                }
                
            },
            (s, a, b, c) -> baseAccel,
            turnVel,
            turnAccel
        );
    }
    public static TrajectorySequenceConstraints getDropConstraints() {
        return new TrajectorySequenceConstraints(
            (s, a, b, c) -> {
                if (s > 20) {
                    return baseVel * 0.65;
                } else {
                    return baseVel;
                }
                
            },
            (s, a, b, c) -> baseAccel,
            turnVel,
            turnAccel
        );
    }
//    public static TrajectorySequenceConstraints getPreLoadDropConstraints() {
//        return new TrajectorySequenceConstraints(
//            (s, a, b, c) -> {
//                if (s > 48) {
//                    return baseVel * 0.6;
//                } else {
//                    return baseVel;
//                }
//            },
//            (s, a, b, c) -> baseAccel,
//            turnVel,
//            turnAccel
//        );
//    }
//    public static TrajectorySequenceConstraints getParkConstraint() {
//        return new TrajectorySequenceConstraints(
//            (s, a, b, c) -> baseVel * 0.6,
//            (s, a, b, c) -> baseAccel,
//            turnVel,
//            turnAccel
//        );
//    }
    public static TrajectorySequenceConstraints getBaseConstraints() {
        return new TrajectorySequenceConstraints(baseVel, baseAccel, turnVel, turnAccel);
    }
    public static TrajectorySequenceConstraints getSlowConstraints() {
        return new TrajectorySequenceConstraints(slowVel, slowAccel, slowTurnVel, slowTurnAccel);
    }
    public static TrajectorySequenceConstraints getFastConstraints() {
        return new TrajectorySequenceConstraints(fastVel, fastAccel, fastTurnVel, fastTurnAccel);
    }
}
