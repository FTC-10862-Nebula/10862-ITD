package org.firstinspires.ftc.teamcode.opmode.auto.league.old;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.ParallelCommandGroup;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

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
import org.firstinspires.ftc.teamcode.subsystems.vision.ff.TeamMarkerPipeline;
import org.firstinspires.ftc.teamcode.subsystems.vision.ff.FFVision;
import org.firstinspires.ftc.teamcode.util.PoseStorage;
import org.firstinspires.ftc.teamcode.util.misc.Util;
import org.firstinspires.ftc.teamcode.util.teleop.MatchOpMode;
import org.firstinspires.ftc.teamcode.util.trajectorysequence.container.Back;
import org.firstinspires.ftc.teamcode.util.trajectorysequence.container.Forward;
import org.firstinspires.ftc.teamcode.util.trajectorysequence.container.LineToConstantHeading;
import org.firstinspires.ftc.teamcode.util.trajectorysequence.container.LineToLinearHeading;
import org.firstinspires.ftc.teamcode.util.trajectorysequence.container.Pose2dContainer;
import org.firstinspires.ftc.teamcode.util.trajectorysequence.container.StrafeLeft;
import org.firstinspires.ftc.teamcode.util.trajectorysequence.container.StrafeRight;
import org.firstinspires.ftc.teamcode.util.trajectorysequence.container.TrajectorySequenceContainer;
import org.firstinspires.ftc.teamcode.util.trajectorysequence.container.Turn;

import java.util.logging.Level;

@Disabled
@Autonomous
@Config
public class BlueWingOtherWay extends MatchOpMode {
    // Subsystems
    private Drivetrain drivetrain;
    //    private AprilTagVision aprilTagVision;
    private FFVision vision;
    private PowerIntake intake;
    //    private Climber climber;
    private Arm arm;
    //    private Shooter shooter;
    private Slide slide;
    private Claw claw;
    private AutoDropper dropper;
    private SensorColor sensorColor;


    public static DropSpikeMark aDropSpikeMark;

    public static class DropSpikeMark {
        public static Pose2dContainer startPose = new Pose2dContainer(10, 65, 270);

        static TrajectorySequenceContainer getMarker(TeamMarkerPipeline.FFPosition position) {
            switch (position) {
                default:
                case RIGHT:
                    return new TrajectorySequenceContainer(
                            Speed::getFastConstraints,
                            new Back(23)
                    );
                case MIDDLE:
                    return new TrajectorySequenceContainer(
                            Speed::getFastConstraints,
                            new Back(30)
                    );
                case LEFT:
                    return new TrajectorySequenceContainer(
                            Speed::getFastConstraints,
                            //new Back(35)
                new LineToConstantHeading(23,24)

                    );
            }
        }
        static TrajectorySequenceContainer getTurnDrop(TeamMarkerPipeline.FFPosition position) {
            switch (position) {
                default:
                case LEFT:
                    return new TrajectorySequenceContainer(
                            Speed::getFastConstraints,
                            new Turn(90),
                            new Back(2)
                    );
                case MIDDLE:
                    return new TrajectorySequenceContainer(
                            Speed::getFastConstraints,
                            new Forward(0.1)

                    );
                case RIGHT:
                    return new TrajectorySequenceContainer(
                            Speed::getFastConstraints,
                            new StrafeLeft(15)
                    );


            }
        }

        static TrajectorySequenceContainer getTurn(TeamMarkerPipeline.FFPosition position) {
            switch (position) {
                default:
                case LEFT:
                    return new TrajectorySequenceContainer(
                            Speed::getFastConstraints,
                            new Forward(24),
                            new StrafeRight(9)


                            );
                case MIDDLE:
                    return new TrajectorySequenceContainer(
                            Speed::getFastConstraints,
                            new Forward(4),
                            new Turn(90),
                            new StrafeLeft(4.5),
                            new Forward(23.5)
                    );
                case RIGHT:
                    return new TrajectorySequenceContainer(
                            Speed::getFastConstraints,
                            new Forward(6),
                            new Turn(90),
                            new Forward(12.4)
                     //       new StrafeLeft(15)

                    );
            }
        }
    }
        static TrajectorySequenceContainer getStrafes(TeamMarkerPipeline.FFPosition position) {
           switch (position) {
                default:
                    //ADD LINETO
                case LEFT:
                    return new TrajectorySequenceContainer(
                            Speed::getFastConstraints,
                            new LineToConstantHeading(34,50),
                        //    ***TUNE
                         //   new StrafeRight(34.5),
                            new Back(100)
                    );
                case MIDDLE:
                    return new TrajectorySequenceContainer(
                            Speed::getFastConstraints,
                         new LineToConstantHeading(36,70),
                         //   new StrafeRight(34),
                            new Back(90)
                    );
                case RIGHT:
                    return new TrajectorySequenceContainer(
                            Speed::getFastConstraints,
                            new LineToConstantHeading(38,90),
                            new StrafeRight(34),
                            new Back(90)
                    );
            }
        }

    static TrajectorySequenceContainer getDrop(TeamMarkerPipeline.FFPosition position) {
        switch (position) {
            default:
            case LEFT:
                return new TrajectorySequenceContainer(
                        Speed::getFastConstraints,
                        new StrafeLeft(27.8),
                        new Back(19)
                );
            case MIDDLE:
                return new TrajectorySequenceContainer(
                        Speed::getFastConstraints,
                        new StrafeLeft(36),
                        new Back(25)
                );
            case RIGHT:
                return new TrajectorySequenceContainer(
                        Speed::getFastConstraints,
                        new StrafeLeft(46.5),
                        new Back(26)
                );
        }
    }

    static TrajectorySequenceContainer getPark(TeamMarkerPipeline.FFPosition position) {
        switch (position) {
            default:
            case LEFT:
                return new TrajectorySequenceContainer(
                        Speed::getFastConstraints,
                        new StrafeLeft(10)
                );
            case MIDDLE:
                return new TrajectorySequenceContainer(
                        Speed::getFastConstraints,
                        new StrafeLeft(24.3)
                );
            case RIGHT:
                return new TrajectorySequenceContainer(
                        Speed::getFastConstraints,
                        new StrafeLeft(26.5)
                );
        }
    }


    @Override
    public void robotInit() {
        drivetrain = new Drivetrain(new MecanumDrive(hardwareMap, telemetry), telemetry);
//        drivetrain.init();
        vision = new FFVision(hardwareMap, telemetry);
        intake = new PowerIntake(telemetry, hardwareMap, true);
        arm = new Arm(telemetry, hardwareMap, true);
//        climber = new Climber(telemetry, hardwareMap, true);
        claw = new Claw(telemetry, hardwareMap, true);
        slide = new Slide(telemetry, hardwareMap, true);
//        climber.setSetPointCommand(Climber.ClimbEnum.REST);
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
        //      TeamMarkerPipeline.FFPosition position = TeamMarkerPipeline.FFPosition.MIDDLE;

        drivetrain.setPoseEstimate(DropSpikeMark.startPose.getPose());
        PoseStorage.trajectoryPose = DropSpikeMark.startPose.getPose();

        schedule(
                new SequentialCommandGroup(
                        /*** YellowPixel ***/
                        new TrajectorySequenceContainerFollowCommand(drivetrain,
                                DropSpikeMark.getMarker(position)),
                        new TrajectorySequenceContainerFollowCommand(drivetrain,
                                DropSpikeMark.getTurnDrop(position)),
                        dropper.dropperSetPositionCommand(AutoDropper.DropPos.DROP),

                        /*** get pixel ***/
                        new ParallelCommandGroup(
                                new TrajectorySequenceContainerFollowCommand(drivetrain,
                                        DropSpikeMark.getTurn(position)),
                                claw.setFClaw(Claw.ClawPos.OPEN_POS)
                        ),
                        new InstantCommand(intake::setFive),
                        new AutoIntakeCommand(claw, intake, sensorColor, drivetrain),

                        /**drop pixel**/
                                new TrajectorySequenceContainerFollowCommand(drivetrain,
                                        getStrafes(position)),
                                new ParallelCommandGroup(
                                new TrajectorySequenceContainerFollowCommand(drivetrain,
                                        getDrop(position)),
                                new DisplacementCommand(3.5,
                                        new SlideCommand(slide, arm, claw, Slide.SlideEnum.AUTO_LOW))
                        )
                ),
                        new TrajectorySequenceContainerFollowCommand(drivetrain,
                                new TrajectorySequenceContainer(Speed::getBaseConstraints,
                                        new Back(11.5))),
//                new WaitCommand(300),
                        claw.setBothClaw(Claw.ClawPos.OPEN_POS),
                        new WaitCommand(800),
                        new TrajectorySequenceContainerFollowCommand(drivetrain,
                                new TrajectorySequenceContainer(Speed::getFastConstraints,
                                        new Forward(6))),
                        new ResetCommand(slide, arm, claw),
                        new TrajectorySequenceContainerFollowCommand(drivetrain,
                                getPark(position)),


                        /* Save Pose and end opmode*/
                        run(() -> PoseStorage.currentPose = drivetrain.getPoseEstimate()),
                        run(this::stop)
        );
    }
}