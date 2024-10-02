package org.firstinspires.ftc.teamcode.opmode.auto.league;


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
    private MecDrive drivetrain;

    public static class DropSpikeMark {
        public static Pose2dContainer startPose = new Pose2dContainer(10, 65, (270));

        static TrajectorySequenceContainer preload() {
            return new TrajectorySequenceContainer(
                Speed::getBaseConstraints,
                new Back(4.4),
                new Turn(-90),
                new Back(4)
            );
        }
        static TrajectorySequenceContainer getOne() {
            return new TrajectorySequenceContainer(
                    Speed::getBaseConstraints,
                    new Back(4.4),
                    new Turn(-90),
                    new Back(4)
            );
        }
        static TrajectorySequenceContainer dropOne() {
            return new TrajectorySequenceContainer(
                    Speed::getBaseConstraints,
                    new Back(4.4),
                    new Turn(-90),
                    new Back(4)
            );
        }

    }


//     public static Back b = new Back(9);



    @Override
    public void robotInit() {
        drivetrain = new MecDrive();
    }

    @Override
    public void disabledPeriodic() {

    }

    public void matchStart() {
        schedule(

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