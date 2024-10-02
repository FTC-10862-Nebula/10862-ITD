package org.firstinspires.ftc.teamcode.opmode.auto.league;

import static org.firstinspires.ftc.teamcode.subsystems.Claw.ClawPos.OPEN_POS;

import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.ParallelCommandGroup;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;

import org.firstinspires.ftc.teamcode.commands.AutoIntakeCommand;
import org.firstinspires.ftc.teamcode.commands.arm.position.ResetCommand;
import org.firstinspires.ftc.teamcode.commands.arm.position.SlideCommand;
import org.firstinspires.ftc.teamcode.commands.drive.trajectory.sequence.DisplacementCommand;
import org.firstinspires.ftc.teamcode.commands.drive.trajectory.sequence.TrajectorySequenceContainerFollowCommand;
import org.firstinspires.ftc.teamcode.opmode.auto.Speed;
import org.firstinspires.ftc.teamcode.subsystems.AutoDropper;
import org.firstinspires.ftc.teamcode.subsystems.Claw;
import org.firstinspires.ftc.teamcode.subsystems.arm.Arm;
import org.firstinspires.ftc.teamcode.subsystems.drive.mec.Drivetrain;
import org.firstinspires.ftc.teamcode.subsystems.drive.mec.MecanumDrive;
import org.firstinspires.ftc.teamcode.subsystems.intake.PowerIntake;
import org.firstinspires.ftc.teamcode.subsystems.sensor.SensorColor;
import org.firstinspires.ftc.teamcode.subsystems.slide.Slide;
import org.firstinspires.ftc.teamcode.subsystems.vision.ff.FFVision;
import org.firstinspires.ftc.teamcode.subsystems.vision.ff.TeamMarkerPipeline;
import org.firstinspires.ftc.teamcode.util.PoseStorage;
import org.firstinspires.ftc.teamcode.util.misc.Util;
import org.firstinspires.ftc.teamcode.util.teleop.MatchOpMode;
import org.firstinspires.ftc.teamcode.util.trajectorysequence.container.Back;
import org.firstinspires.ftc.teamcode.util.trajectorysequence.container.Forward;
import org.firstinspires.ftc.teamcode.util.trajectorysequence.container.LineToConstantHeading;
import org.firstinspires.ftc.teamcode.util.trajectorysequence.container.Pose2dContainer;
import org.firstinspires.ftc.teamcode.util.trajectorysequence.container.SplineTo;
import org.firstinspires.ftc.teamcode.util.trajectorysequence.container.SplineToSplineHeading;
import org.firstinspires.ftc.teamcode.util.trajectorysequence.container.StrafeLeft;
import org.firstinspires.ftc.teamcode.util.trajectorysequence.container.StrafeRight;
import org.firstinspires.ftc.teamcode.util.trajectorysequence.container.TrajectorySequenceContainer;

import java.util.logging.Level;

public class RedWingSplinesNew extends MatchOpMode {
    private Drivetrain drivetrain;
    private FFVision vision;
    private PowerIntake intake;
    private Arm arm;
    private Slide slide;
    private Claw claw;
    private AutoDropper dropper;
    private SensorColor sensorColor;

    public static Pose2dContainer startPose = new Pose2dContainer(10, 65, 270);

    static TrajectorySequenceContainer getDropSpike(TeamMarkerPipeline.FFPosition position) {
        switch (position) {
            case LEFT:
                return new TrajectorySequenceContainer(
                        Speed::getFastConstraints,
                        new SplineToSplineHeading(-56,-30,90,180)
                );
            case MIDDLE:
                return new TrajectorySequenceContainer(
                        Speed::getFastConstraints,
                        new SplineTo(-32,-32,90)
                );
            default:
            case RIGHT:
                return new TrajectorySequenceContainer(
                        Speed::getFastConstraints,
                        new SplineTo(-32,-34,45)
                );
        }
    }
    static TrajectorySequenceContainer getPlus(TeamMarkerPipeline.FFPosition position) {
        switch (position) {
            case LEFT:
                return new TrajectorySequenceContainer(
                        Speed::getFastConstraints,
                        new LineToConstantHeading(-60,-30)
                );
            case MIDDLE:
                return new TrajectorySequenceContainer(
                    Speed::getFastConstraints,
                    new SplineToSplineHeading(-60,-35,90,180)
            );
            default:
            case RIGHT:
                return new TrajectorySequenceContainer(
                        Speed::getFastConstraints,
                        new SplineToSplineHeading(-60,-35,45,180)
                );
        }
    }
    static TrajectorySequenceContainer getBackYellow(TeamMarkerPipeline.FFPosition position) {
        switch (position) {
            case LEFT:
                return new TrajectorySequenceContainer(
                        Speed::getFastConstraints,
                        new LineToConstantHeading(-40,-58),
                        new Back(90)
                );
            default:
            case RIGHT:
            case MIDDLE:
                return new TrajectorySequenceContainer(
                        Speed::getFastConstraints,
                        new LineToConstantHeading(-45,-58),
                        new Back(95)
                        );
        }
    }
    static TrajectorySequenceContainer getStrafe(TeamMarkerPipeline.FFPosition position) {
        switch (position) {
            default:
            case RIGHT:
            case LEFT:
            case MIDDLE:
                return new TrajectorySequenceContainer(
                        Speed::getFastConstraints,
                        new StrafeLeft(25));
        }
    }
    static TrajectorySequenceContainer getForwardPlus(TeamMarkerPipeline.FFPosition position) {
        switch (position) {
            default:
            case LEFT:
                return new TrajectorySequenceContainer(
                        Speed::getFastConstraints,
                        new StrafeRight(25),
                        new Forward(90),
                        new LineToConstantHeading(-60,-35)
                );
            case MIDDLE:
            case RIGHT:
                return new TrajectorySequenceContainer(
                        Speed::getFastConstraints,
                        new StrafeRight(25),
                        new Forward(95),
                        new LineToConstantHeading(-60,-35)
                );
        }
    }
    static TrajectorySequenceContainer getPlusTwo(TeamMarkerPipeline.FFPosition position) {
        switch (position) {
            case LEFT:
                return new TrajectorySequenceContainer(
                        Speed::getFastConstraints,
                        new LineToConstantHeading(-40,-58),
                        new Back(90)
                );
            case MIDDLE:
            case RIGHT:
            default:
               return new TrajectorySequenceContainer(
                Speed::getFastConstraints,
                        new LineToConstantHeading(-45,-58),
                        new Back(90)
                        );
        }
    }
    static TrajectorySequenceContainer getStrafePlus(TeamMarkerPipeline.FFPosition position) {
        switch (position) {
            default:
            case LEFT:
            case MIDDLE:
            case RIGHT:
                return new TrajectorySequenceContainer(
                        Speed::getFastConstraints,
                        new StrafeLeft(30));
        }
    }
    static TrajectorySequenceContainer getParking(TeamMarkerPipeline.FFPosition position) {
        switch (position) {
            default:
            case LEFT:
            case MIDDLE:
            case RIGHT:
             return new TrajectorySequenceContainer(
                    Speed::getFastConstraints,
                    new StrafeRight(30)
            );
        }
    }
    @Override
    public void robotInit() {
        drivetrain = new Drivetrain(new MecanumDrive(hardwareMap, telemetry), telemetry);
        vision = new FFVision(hardwareMap, telemetry);
        intake = new PowerIntake(telemetry, hardwareMap, true);
        arm = new Arm(telemetry, hardwareMap, true);
        claw = new Claw(telemetry, hardwareMap, true);
        slide = new Slide(telemetry, hardwareMap, true);
        dropper = new AutoDropper(telemetry, hardwareMap, true);
        sensorColor = new SensorColor(telemetry, hardwareMap);
    }
    @Override
    public void disabledPeriodic() {
        vision.setPosition(vision.getPosition());
        Util.logger(this, telemetry, Level.INFO, "Current Position", vision.getFinalPosition());
    }

    public void matchStart() {
        TeamMarkerPipeline.FFPosition position = vision.getPosition();
        drivetrain.setPoseEstimate(startPose.getPose());
        PoseStorage.trajectoryPose = startPose.getPose();

        schedule(
                new SequentialCommandGroup(
                new SequentialCommandGroup(
                       new  TrajectorySequenceContainerFollowCommand
                                (drivetrain, getDropSpike(position)),
                        dropper.dropperSetPositionCommand(AutoDropper.DropPos.DROP)),
                       new TrajectorySequenceContainerFollowCommand(drivetrain,
                               getPlus(position)),
                       new ParallelCommandGroup(
                       claw.setBothClaw(OPEN_POS),
                       new InstantCommand(intake::setFive)
                               ),
                       new AutoIntakeCommand(claw, intake, sensorColor, drivetrain),
                       new TrajectorySequenceContainerFollowCommand(drivetrain,
                               getBackYellow(position)),
                       new ParallelCommandGroup(
                             new  TrajectorySequenceContainerFollowCommand(drivetrain,
                                       getStrafe(position)),
                        new DisplacementCommand(3.5,
                        new SlideCommand(slide, arm, claw, Slide.SlideEnum.AUTO_LOW))
                       ),
                               new WaitCommand(800),
                        new ParallelCommandGroup( new ResetCommand(slide,arm, claw),
                       new TrajectorySequenceContainerFollowCommand(drivetrain,
                               getForwardPlus(position))),

                       new TrajectorySequenceContainerFollowCommand(drivetrain,
                                       getForwardPlus(position)),
                       new TrajectorySequenceContainerFollowCommand(drivetrain,
                               getPlusTwo(position)),

                       new ParallelCommandGroup( new TrajectorySequenceContainerFollowCommand(
                               drivetrain, getStrafePlus(position)),
                       new DisplacementCommand(3.5,
                         new SlideCommand(slide, arm, claw, Slide.SlideEnum.AUTO_LOW))
                               ),
                       new ParallelCommandGroup(
                       new TrajectorySequenceContainerFollowCommand(drivetrain,
                               getParking(position)),
                        new ResetCommand(slide, arm, claw)
                       )
           )
        );
    }
}
