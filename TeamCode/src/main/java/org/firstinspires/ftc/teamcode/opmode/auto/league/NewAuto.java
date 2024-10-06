package org.firstinspires.ftc.teamcode.opmode.auto.league;


import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.ParallelAction;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.subsystems.drive.MecDrive;
import org.firstinspires.ftc.teamcode.util.teleop.MatchOpMode;

//@Disabled
@Autonomous(preselectTeleOp = "TeleOpMain")
public class NewAuto extends MatchOpMode {
    // Subsystems
    private MecDrive drive = new MecDrive();
//    private TrajectoryActionBuilder builder = new TrajectoryActionBuilder();

    Action pathOne = drive.drivetrain.actionBuilder(drive.getPose())
            .lineToX(10)
            .splineTo(new Vector2d(0,0), 4)
            .turnTo(3)
            .build();

    Action pathTwo = drive.drivetrain.actionBuilder(new Pose2d(15,20,0))
            .splineTo(new Vector2d(5,5), Math.toRadians(90))
            .build();
    @Override
    public void robotInit() {

    }

    @Override
    public void disabledPeriodic() {

    }

    public void matchStart() {
        schedule(
            new InstantCommand(
                ()-> Actions.runBlocking(
                    new SequentialAction(
                        pathOne, // Example of a drive action
                        new ParallelAction( // several actions being run in parallel
                            pathTwo
                        )
                    )
                )
            )
        );
    }
}