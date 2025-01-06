//package org.firstinspires.ftc.teamcode.opmode.auto;
//import com.acmerobotics.roadrunner.Pose2d;
//import com.acmerobotics.roadrunner.Trajectory;
//import com.acmerobotics.roadrunner.TrajectoryBuilder;
//import com.arcrobotics.ftclib.trajectory.TrajectoryConfig;
//import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
//import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
//
//import org.firstinspires.ftc.teamcode.subsystems.drive.MecDrive;
//import org.firstinspires.ftc.teamcode.subsystems.drive.RoadrunnerMecanumDrive;
//import org.firstinspires.ftc.teamcode.subsystems.intake.HorizontalSlide;
//import org.firstinspires.ftc.teamcode.subsystems.intake.Intake;
//import org.firstinspires.ftc.teamcode.subsystems.intake.IntakeServo;
//import org.firstinspires.ftc.teamcode.subsystems.intake.PowerIntake;
//import org.firstinspires.ftc.teamcode.subsystems.intake.SensorColor;
//import org.firstinspires.ftc.teamcode.subsystems.outtake.Arm;
//import org.firstinspires.ftc.teamcode.subsystems.outtake.Claw;
//import org.firstinspires.ftc.teamcode.subsystems.outtake.Outtake;
//import org.firstinspires.ftc.teamcode.subsystems.outtake.Pivot;
//import org.firstinspires.ftc.teamcode.subsystems.outtake.Slide;
//
//import java.util.List;
//
//
//@Autonomous(preselectTeleOp = "TeleOpMain")
//    public class NewAutoRed extends LinearOpMode {
//
//    private Outtake outtake;
//    private MecDrive drive;
//    private Intake intake;
//    private SensorColor sensorColor;
//
//    public void runOpMode() throws InterruptedException {
//
//    Pose2d startPose = new Pose2d(-16, 60, 300);
//    Pose2d specimen = new Pose2d(-12,-34,300);
//    Pose2d intakeS1 = new Pose2d(-34,-32,185.35);
//    Pose2d intakeS2 = new Pose2d(-39,-26,185.35);
//    Pose2d sample1 = new Pose2d(-54,-56,0.8);
//    Pose2d sample2 = new Pose2d(-54,-56,0.8);
//    Pose2d Park = new Pose2d(-26,-10,185.35);
//
//
//        class DropSpecimen {
//
//            private final RoadrunnerMecanumDrive drive;  // Assuming you're using a Mecanum drive system
//
//            public Pose2d startPose = new Pose2d(-16, 60, Math.toRadians(300)); // Convert degrees to radians
//
//            public DropSpecimen(RoadrunnerMecanumDrive drive) {
//                this.drive = drive;
//            }
//
//            // Method to build the trajectory
//            public List<Trajectory> goBar() {
//                // Create a TrajectoryConfig with max velocity and max acceleration
//                TrajectoryConfig config = new TrajectoryConfig(10, 10); // Adjust max velocity and acceleration as needed
//
//                // Initialize TrajectoryBuilder with the MecanumDrive
//                TrajectoryBuilder builder = new TrajectoryBuilder(startPose, config);
//                // Create and return the trajectory using the builder
//                return builder.splineToLinearHeading(new Pose2d(-12, -34, Math.toRadians(300)), 0)
//                    .build(); // Return the built trajectory
//            }
//        }
//
//
////    @Override
////    public void roboInit(){
////    drive = new MecDrive(hardwareMap);
////    sensorColor = new SensorColor(telemetry, hardwareMap);
////    intake = new Intake(
////            new HorizontalSlide(telemetry, hardwareMap, true),
////            new IntakeServo(telemetry, hardwareMap, true),
////            new PowerIntake(telemetry, hardwareMap, true),
////    telemetry
////        );
////
////    outtake = new Outtake(
////            new Slide(telemetry, hardwareMap, true),
////            new Arm(telemetry, hardwareMap, true),
////            new Claw(telemetry, hardwareMap, true),
////            new Pivot(telemetry, hardwareMap, true),
////    telemetry
////        );
////        intake.init();
////        outtake.init();
////        }
//    }
//}
//
