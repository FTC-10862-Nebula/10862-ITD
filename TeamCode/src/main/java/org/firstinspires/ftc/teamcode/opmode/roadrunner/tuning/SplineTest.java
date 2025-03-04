package org.firstinspires.ftc.teamcode.opmode.roadrunner.tuning;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.subsystems.drive.RoadrunnerMecanumDrive;

@Autonomous
public final class SplineTest extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        Pose2d beginPose = new Pose2d(0, 0, 0);
        if (TuningOpModes.DRIVE_CLASS.equals(RoadrunnerMecanumDrive.class)) {
            RoadrunnerMecanumDrive drive = new RoadrunnerMecanumDrive(hardwareMap, beginPose);
            
            waitForStart();
            
            Actions.runBlocking(
                drive.actionBuilder(beginPose)
                    .splineTo(new Vector2d(30, 30), Math.PI / 2)
                    .splineTo(new Vector2d(0, 0), Math.PI)
                    .build());
        } else {
            throw new RuntimeException();
        }
    }
}
