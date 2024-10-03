package org.firstinspires.ftc.teamcode.opmode.auto.league;


import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.acmerobotics.roadrunner.Vector2d;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.commands.drive.trajectory.sequence.TrajectorySequenceContainerFollowCommand;
import org.firstinspires.ftc.teamcode.opmode.auto.Speed;
import org.firstinspires.ftc.teamcode.subsystems.Arm;
import org.firstinspires.ftc.teamcode.subsystems.Claw;
import org.firstinspires.ftc.teamcode.subsystems.PowerIntake;
import org.firstinspires.ftc.teamcode.subsystems.Slide;
import org.firstinspires.ftc.teamcode.subsystems.drive.mec.MecDrive;
import org.firstinspires.ftc.teamcode.util.PoseStorage;
import org.firstinspires.ftc.teamcode.util.teleop.MatchOpMode;
import org.firstinspires.ftc.teamcode.util.trajectorysequence.container.Back;
import org.firstinspires.ftc.teamcode.util.trajectorysequence.container.Pose2dContainer;
import org.firstinspires.ftc.teamcode.util.trajectorysequence.container.TrajectorySequenceContainer;
import org.firstinspires.ftc.teamcode.util.trajectorysequence.container.Turn;

//@Disabled
@Autonomous(preselectTeleOp = "TeleOpMain")
public class NewAuto extends MatchOpMode {
    // Subsystems
    private MecDrive drive;
    private TrajectoryActionBuilder builder = new TrajectoryActionBuilder();

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
        drive = new MecDrive();
    }

    @Override
    public void disabledPeriodic() {

    }

    public void matchStart() {
        schedule(

//                builder
//                        .lineToX(-12)
//                        .afterDisp(5, dispMarker)
//                        .splineTo(Vector2d(-24, -12), -Math.PI / 2)
//                        .turn(Math.PI / 2)
//                        .afterTime(0.5, tempMarker)
//                        .turn(Math.PI / 2)
//                        .setReversed(true)
//                        .splineTo(Vector2d(-36, 0), Math.PI)

//            new SequentialCommandGroup(
//                new TrajectorySequenceContainerFollowCommand(drivetrain,
//                        new TrajectorySequenceContainer(Speed::getBaseConstraints,
//                                new Back(27))),
//                new SequentialCommandGroup(
//                ),
//                /* Save Pose and end opmode*/
//                run(() -> PoseStorage.currentPose = drivetrain.getPose()),
//                run(this::stop)
//            )
        );

    }
}