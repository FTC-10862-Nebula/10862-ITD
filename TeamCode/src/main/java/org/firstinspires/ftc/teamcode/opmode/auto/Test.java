//package org.firstinspires.ftc.teamcode.opmode.auto;
//
//import com.acmerobotics.dashboard.config.Config;
//import com.acmerobotics.roadrunner.Action;
//import com.acmerobotics.roadrunner.ParallelAction;
//import com.acmerobotics.roadrunner.Pose2d;
//import com.acmerobotics.roadrunner.SequentialAction;
//import com.acmerobotics.roadrunner.Vector2d;
//import com.acmerobotics.roadrunner.ftc.Actions;
//import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
//import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
//
//import org.firstinspires.ftc.teamcode.subsystems.drive.MecDrive;
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
//@Config
//@Autonomous(name = "Template Autoop", group = "16481-Example")
//public class Test extends LinearOpMode {
//
//    @Override
//    public void runOpMode() {
//        // Initialize subsystems
//        MecDrive drive = new MecDrive(hardwareMap);
//        Outtake outtake;
//        Intake intake;
//        SensorColor sensorColor;
//
//        // Initialize subsystems
//        sensorColor = new SensorColor(telemetry, hardwareMap);
//        intake = new Intake(
//            new HorizontalSlide(telemetry, hardwareMap, true),
//            new IntakeServo(telemetry, hardwareMap, true),
//            new PowerIntake(telemetry, hardwareMap, true),
//            telemetry);
//        outtake = new Outtake(
//            new Slide(telemetry, hardwareMap, true),
//            new Arm(telemetry, hardwareMap, true),
//            new Claw(telemetry, hardwareMap, true),
//            new Pivot(telemetry, hardwareMap, true),
//            telemetry);
//
//        // Define Positions for Trajectories
//        Pose2d startPose = new Pose2d(-16, -60, Math.toRadians(300));
//        Pose2d specimenDrop = new Pose2d(-12, -34, Math.toRadians(300));
//        Pose2d intakeS1 = new Pose2d(-34, -32, Math.toRadians(185.35));
//        Pose2d intakeS2 = new Pose2d(-39, -26, Math.toRadians(185.35));
//        Pose2d sample1 = new Pose2d(-54, -56, Math.toRadians(0.8));
//        Pose2d sample2 = new Pose2d(-54, -56, Math.toRadians(0.8));
//        Pose2d park = new Pose2d(-26, -10, Math.toRadians(185.35));
//
//        // Define Trajectories using the action builder
//        Action pathOne = drive.drivetrain.actionBuilder(startPose)
//            .splineToLinearHeading(specimenDrop, Math.toRadians(300))
//            .waitSeconds(3)
//            .splineToLinearHeading(intakeS1, Math.toRadians(0))
//            .strafeTo(new Vector2d(-34, -26))
//            .waitSeconds(4)
//            .build();
//
//        Action pathTwo = drive.drivetrain.actionBuilder(intakeS1)
//            .splineToSplineHeading(sample1, Math.toRadians(15))
//            .waitSeconds(2)
//            .splineToLinearHeading(intakeS2, Math.toRadians(0))
//            .waitSeconds(4)
//            .build();
//
//        Action pathThree = drive.drivetrain.actionBuilder(intakeS2)
//            .splineToSplineHeading(sample2, Math.toRadians(15))
//            .waitSeconds(2)
//            .splineToLinearHeading(park, Math.toRadians(0))
//            .build();
//
//        // Wait for the start command
//        waitForStart();
//
//        // Execute actions if the op mode is active
//        if (opModeIsActive()) {
//            Actions.runBlocking(
//                new SequentialAction(
//                    pathOne,
//                    outtake.value.HIGH_RUNG,
//                    wait(500),
//                    outtake.value.SPECIMEN_HIGH_BAR);// Path for specimen drop and first intake
//                    new ParallelAction(
//                        pathTwo,  // Path for sample 1 and second intake
//                        (telemetryPacket) -> { // Additional parallel action can be added here
//                            return false;
//                        }
//                    ),
//                    pathThree  // Path for sample 2 and park
//                );
//        }
//    }
//}
