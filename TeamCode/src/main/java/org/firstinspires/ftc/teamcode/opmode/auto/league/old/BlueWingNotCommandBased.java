//package org.firstinspires.ftc.teamcode.opmode.auto.league;
//import org.firstinspires.ftc.teamcode.util.trajectorysequence.container.Back;
//import org.firstinspires.ftc.teamcode.util.trajectorysequence.container.Forward;
//import org.firstinspires.ftc.teamcode.util.trajectorysequence.container.LineToLinearHeading;
//import org.firstinspires.ftc.teamcode.util.trajectorysequence.container.Pose2dContainer;
//import org.firstinspires.ftc.teamcode.util.trajectorysequence.container.SetReversed;
//import org.firstinspires.ftc.teamcode.util.trajectorysequence.container.SplineTo;
//import org.firstinspires.ftc.teamcode.util.trajectorysequence.container.StrafeLeft;
//import org.firstinspires.ftc.teamcode.util.trajectorysequence.container.StrafeRight;
//import org.firstinspires.ftc.teamcode.util.trajectorysequence.container.TrajectorySequenceContainer;
//
//public static NebulaConstants constants;
//public class constants {
//    public static Speed speed;
//
//    public static class Speed {
//        public static double maxVel = 45; // value
//        public static double maxAccel = 45; // value
//        public static double turnVel = Math.toRadians(180); // value
//        public static double turnAccel = Math.toRadians(180); // value
//    }
//
//    public static PreLoadMid preLoadMid;
//
//    public static class PreLoadMid {
//        public static Pose2dContainer startPose = new Pose2dContainer(-35, 60, 270);
//
//        public static SetReversed a = new SetReversed(true);
//        public static LineToLinearHeading b = new LineToLinearHeading(-44, 24, 0);
//        public static StrafeRight c = new StrafeRight(13);
//        public static Back d = new Back(17);
//
//        public static final TrajectorySequenceContainer traj = new TrajectorySequenceContainer(a, b, c, d);
//    }
//    public static class YellowPixel yellowPixel;
//    public static class YellowPixel {
//        public static Forward a = new Forward(110);
//        public static StrafeLeft b = new StrafeLeft(50);
//        public static Forward c = new Forward(10);
//    }
//}
//
//
//public class BlueWingNotCommandBased {
//
//
//    public static void main(String[] args) {
//        // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
//            .setConstraints(45, 45, Math.toRadians(180), Math.toRadians(180), 15)
//                .followTrajectorySequence(drive ->
//                        drive.trajectorySequenceBuilder(new Pose2d(-35, 60, Math.toRadians(270)))
//                                .splineTo(new Vector2d(-34, 30), Math.toRadians(45))
//                                .setReversed(true)
//                                .lineToLinearHeading(new Pose2d(-50, 35))
//                                .strafeRight(23)
//                                .forward(100)
//                                .strafeLeft(50)
//                                .forward(10)
//
//                                .build()
//                );
//
//    }
//}
//
//public class BlueWingNotCommandBase {
//    public static void main(String[] args) {
//
//        // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
//                .setConstraints(45, 45, Math.toRadians(180), Math.toRadians(180), 15)
//                .followTrajectorySequence(drive ->
//                        drive.trajectorySequenceBuilder(new Pose2d(-35, 60, Math.toRadians(270)))
//                                //  .forward(8)
//                                .setReversed(true)
//                                .lineToLinearHeading(new Pose2d(-54, 46))
//                                .strafeRight(34)
//                                .back(7)
//                                .forward(112)
//                                .strafeLeft(50)
//                                .forward(10)
//
//                                .build()
//                );
//    }
//}
