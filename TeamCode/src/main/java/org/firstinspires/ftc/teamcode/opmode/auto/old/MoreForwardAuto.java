package org.firstinspires.ftc.teamcode.opmode.auto.old;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import org.firstinspires.ftc.teamcode.commands.drive.trajectory.sequence.TrajectorySequenceContainerFollowCommand;
import org.firstinspires.ftc.teamcode.opmode.auto.Speed;
import org.firstinspires.ftc.teamcode.subsystems.climber.PowerClimber;
import org.firstinspires.ftc.teamcode.subsystems.drive.mec.Drivetrain;
import org.firstinspires.ftc.teamcode.subsystems.drive.mec.MecanumDrive;
import org.firstinspires.ftc.teamcode.subsystems.intake.PowerIntake;
import org.firstinspires.ftc.teamcode.util.PoseStorage;
import org.firstinspires.ftc.teamcode.util.teleop.MatchOpMode;
import org.firstinspires.ftc.teamcode.util.trajectorysequence.container.Forward;
import org.firstinspires.ftc.teamcode.util.trajectorysequence.container.Pose2dContainer;
import org.firstinspires.ftc.teamcode.util.trajectorysequence.container.TrajectorySequenceContainer;
@Disabled
@Autonomous
public class MoreForwardAuto extends MatchOpMode {
    // Subsystems
    private Drivetrain drivetrain;
    private PowerIntake intake;
    private PowerClimber climb;
    @Config
    private static class RedBackstageConstants {
        public static Path path;
        public static class Path {
            public static ForwardPath aForwardPath;
            public static class ForwardPath {
                public static Pose2dContainer startPose = new Pose2dContainer(10, -65, (90));
                public static Forward a =
                    new Forward(-60);
                static TrajectorySequenceContainer preload =
                    new TrajectorySequenceContainer(Speed::getBaseConstraints, a);
            }
        }
    }

    @Override
    public void robotInit() {
        drivetrain = new Drivetrain(new MecanumDrive(hardwareMap, telemetry), telemetry);
//        drivetrain.init();
        intake = new PowerIntake(telemetry, hardwareMap, true);
    }

    public void matchStart() {
        drivetrain.setPoseEstimate(RedBackstageConstants.Path.ForwardPath.startPose.getPose());
        PoseStorage.trajectoryPose = RedBackstageConstants.Path.ForwardPath.startPose.getPose();

        schedule(
            new SequentialCommandGroup( //TODO:TEST!
                new InstantCommand(intake::setUp),
                new TrajectorySequenceContainerFollowCommand(drivetrain,
                        RedBackstageConstants.Path.ForwardPath.preload),
                /* Save Pose and end opmode*/
                run(() -> PoseStorage.currentPose = drivetrain.getPoseEstimate()),
                run(this::stop)
            )
        );
    }
}