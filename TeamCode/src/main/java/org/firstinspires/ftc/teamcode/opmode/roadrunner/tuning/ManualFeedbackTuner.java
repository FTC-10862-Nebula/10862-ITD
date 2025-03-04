package org.firstinspires.ftc.teamcode.opmode.roadrunner.tuning;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.subsystems.drive.RoadrunnerMecanumDrive;


public final class ManualFeedbackTuner extends LinearOpMode {
    public static double DISTANCE = 100;

    @Override
    public void runOpMode() throws InterruptedException {
        if (TuningOpModes.DRIVE_CLASS.equals(RoadrunnerMecanumDrive.class)) {
            RoadrunnerMecanumDrive drive = new RoadrunnerMecanumDrive(hardwareMap, new Pose2d(0, 0, 0));

            waitForStart();

            while (opModeIsActive()) {
                Actions.runBlocking(
                    drive.actionBuilder(new Pose2d(0, 0, 0))
                            .lineToX(DISTANCE)
                            .lineToX(0)
                            .build());
            }
            telemetry.addData("Pose", drive.updatePoseEstimate());
            telemetry.update();
        } else {
            throw new RuntimeException();
        }
    }
}
