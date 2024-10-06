package org.firstinspires.ftc.teamcode.opmode.auto;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.ParallelAction;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.subsystems.drive.MecDrive;

@Config
@Autonomous(name = "Template Autoop", group = "16481-Example")
public class Test extends LinearOpMode {

    @Override
    public void runOpMode() {

        MecDrive drive = new MecDrive();

        DcMotor motor1 = hardwareMap.get(DcMotor.class,  "motor");

        // Delcare Trajectory as such
        Action pathOne = drive.drivetrain.actionBuilder(drive.getPose())
                .lineToX(10)
                .splineTo(new Vector2d(0,0), 4)
                .turnTo(3)
                .build();

        Action pathTwo = drive.drivetrain.actionBuilder(new Pose2d(15,20,0))
                .splineTo(new Vector2d(5,5), Math.toRadians(90))
                .build();


        while(!isStopRequested() && !opModeIsActive()) {

        }

        waitForStart();

        if (isStopRequested()) return;

        Actions.runBlocking(
                new SequentialAction(
                        pathOne, // Example of a drive action
                        new ParallelAction( // several actions being run in parallel
                                pathTwo, // Run second trajectory
                                (telemetryPacket) -> { // Run some action
                                    motor1.setPower(1);
                                    return false;
                                }
                        )
                )
        );


    }

}