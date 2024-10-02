package org.firstinspires.ftc.teamcode.opmode.auto.league;

import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.commands.drive.trajectory.sequence.TrajectorySequenceContainerFollowCommand;
import org.firstinspires.ftc.teamcode.opmode.auto.Speed;
import org.firstinspires.ftc.teamcode.subsystems.Claw;
import org.firstinspires.ftc.teamcode.subsystems.Arm;
import org.firstinspires.ftc.teamcode.subsystems.climber.Climber;
import org.firstinspires.ftc.teamcode.subsystems.drive.mec.Drivetrain;
import org.firstinspires.ftc.teamcode.subsystems.drive.mec.ProjectDrive;
import org.firstinspires.ftc.teamcode.subsystems.PowerIntake;
import org.firstinspires.ftc.teamcode.subsystems.shooter.Shooter;
import org.firstinspires.ftc.teamcode.subsystems.Slide;
import org.firstinspires.ftc.teamcode.subsystems.vision.ff.TeamMarkerPipeline;
import org.firstinspires.ftc.teamcode.subsystems.vision.ff.FFVision;
import org.firstinspires.ftc.teamcode.util.PoseStorage;
import org.firstinspires.ftc.teamcode.util.misc.Util;
import org.firstinspires.ftc.teamcode.util.teleop.MatchOpMode;
import org.firstinspires.ftc.teamcode.util.trajectorysequence.container.Back;
import org.firstinspires.ftc.teamcode.util.trajectorysequence.container.Pose2dContainer;
import org.firstinspires.ftc.teamcode.util.trajectorysequence.container.TrajectorySequenceContainer;
import org.firstinspires.ftc.teamcode.util.trajectorysequence.container.Turn;

import java.util.logging.Level;

@Autonomous
public class DropSpikeMarkAuto extends MatchOpMode {
    // Subsystems
    private Drivetrain drivetrain;
    private PowerIntake intake;
    private Climber climber;
    private Arm arm;
    private Shooter shooter;
    private Slide slide;
    private Claw claw;

    public static Pose2dContainer startPose = new Pose2dContainer(10, 65, 270);
    //^^ Has Wrong Coordinates
    static TrajectorySequenceContainer getTurnDrop(TeamMarkerPipeline.FFPosition position) {
        switch (position) {
            default:
            case LEFT:
                return new TrajectorySequenceContainer(
                        Speed::getBaseConstraints,
                        new Back(37.5),
                        new Turn(90),
                        new Back(2.7)
                );
            case MIDDLE:
                return new TrajectorySequenceContainer(
                        Speed::getBaseConstraints,
                        new Back(31.)
                );

            case RIGHT:
                return new TrajectorySequenceContainer(
                        Speed::getBaseConstraints,
                        new Back(37.5),
                        new Turn(-90),
                        new Back(3)
                );
        }
    }

    @Override
    public void robotInit() {
        drivetrain = new Drivetrain(new ProjectDrive(hardwareMap, telemetry), telemetry);
//        drivetrain.init();
        vision = new FFVision(hardwareMap, telemetry);
        intake = new PowerIntake(telemetry, hardwareMap, true);
        arm = new Arm(telemetry, hardwareMap, true);
        climber = new Climber(telemetry, hardwareMap, true);
        claw = new Claw(telemetry, hardwareMap, true);
        slide = new Slide(telemetry, hardwareMap, true);
        climber.setSetPointCommand(Climber.ClimbEnum.REST);
        dropper = new AutoDropper(telemetry, hardwareMap, true);
    }

    @Override
    public void disabledPeriodic() {
        vision.setPosition(vision.getPosition());
        Util.logger(this, telemetry, Level.INFO, "Current Position", vision.getFinalPosition());
    }

    public void matchStart() {
        TeamMarkerPipeline.FFPosition position = vision.getPosition();
        //  TeamMarkerPipeline.FFPosition position = TeamMarkerPipeline.FFPosition.RIGHT;

        drivetrain.setPoseEstimate(startPose.getPose());
        PoseStorage.trajectoryPose = startPose.getPose();

        schedule(
                new SequentialCommandGroup(
                        /*** YellowPixel ***/
                        new TrajectorySequenceContainerFollowCommand(drivetrain,
                                getTurnDrop(position)),
                        dropper.dropperSetPositionCommand(AutoDropper.DropPos.DROP),


                        /* Save Pose and end opmode*/
                        run(() -> PoseStorage.currentPose = drivetrain.getPoseEstimate()),
                        run(this::stop)
                )
        );
    }
}