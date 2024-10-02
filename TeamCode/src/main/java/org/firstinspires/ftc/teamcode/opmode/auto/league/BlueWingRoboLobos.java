package org.firstinspires.ftc.teamcode.opmode.auto.league;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.command.ParallelCommandGroup;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

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
import org.firstinspires.ftc.teamcode.util.trajectorysequence.container.Pose2dContainer;
import org.firstinspires.ftc.teamcode.util.trajectorysequence.container.StrafeLeft;
import org.firstinspires.ftc.teamcode.util.trajectorysequence.container.StrafeRight;
import org.firstinspires.ftc.teamcode.util.trajectorysequence.container.TrajectorySequenceContainer;
import org.firstinspires.ftc.teamcode.util.trajectorysequence.container.Turn;

import java.util.logging.Level;

//@Disabled
@Autonomous
@Config
public class BlueWingRoboLobos extends MatchOpMode {
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
                static TrajectorySequenceContainer getTurnDrop(TeamMarkerPipeline.FFPosition position) {
                    switch (position) {
                        default:
                        case LEFT:
                            return new TrajectorySequenceContainer(
                                    Speed::getBaseConstraints,
                                    new Back(31.8),
                                    new Turn(90),
                                    new Back(2)
                                    );
                        case MIDDLE:
                            return new TrajectorySequenceContainer(
                                    Speed::getBaseConstraints,
                                    new Back(30)

                            );
                       case RIGHT:
                           return new TrajectorySequenceContainer(
                                   Speed::getBaseConstraints,
                                   new Back(36),
                                   new Turn(-90),
                                    new Back(.5)
//                                   new Back(3)
                            );


                    }
                }
                static TrajectorySequenceContainer getTurn(TeamMarkerPipeline.FFPosition position) {
                    switch (position) {
                        default:
                        case LEFT:
                            return new TrajectorySequenceContainer(
                                    Speed::getBaseConstraints,
                                    new Forward(1.2),
                                    new StrafeRight(40)
                            );
                        case MIDDLE:
                            return new TrajectorySequenceContainer(
                                    Speed::getBaseConstraints,
                                    new Forward(26.8),
                                    new Turn(92)

                            );
                        case RIGHT:
                            return new TrajectorySequenceContainer(
                                    Speed::getBaseConstraints,
                                    new StrafeLeft(39)
                            );
                    }
                }
            }
        static TrajectorySequenceContainer getFactor(TeamMarkerPipeline.FFPosition position) {
            switch (position) {
                default:
                case LEFT:
                    return new TrajectorySequenceContainer(
                            Speed::getFastConstraints,
                            //change to 93 today
                            new Back(92)
                    );
                case MIDDLE:
                    return new TrajectorySequenceContainer(
                            Speed::getFastConstraints,
                            //change to 120 today
                            new Back(92.5)
                    );
                case RIGHT:
                    return new TrajectorySequenceContainer(
                            Speed::getFastConstraints,
                            new Turn(182.4),
                            //change to 97 today
                            new Back(92)
                    );
            }
        }

            static TrajectorySequenceContainer getDropped(TeamMarkerPipeline.FFPosition position) {
                switch (position) {
                    default:
                    case LEFT:
                        return new TrajectorySequenceContainer(
                                Speed::getSlowConstraints,
                                new StrafeLeft(29.5),
                                new Back(7)
                        );
                    case MIDDLE:
                        return new TrajectorySequenceContainer(
                                Speed::getSlowConstraints,
                                new StrafeLeft(38.5),
                                new Back(6)
                        );
                    case RIGHT:
                        return new TrajectorySequenceContainer(
                            Speed::getSlowConstraints,
                            new StrafeLeft(46),
                            new Back(6.5)
                        );
                }
            }
        static TrajectorySequenceContainer getParked(TeamMarkerPipeline.FFPosition position) {
            switch (position) {
                default:
                case LEFT:
                    return new TrajectorySequenceContainer(
                            Speed::getFastConstraints,
                            new StrafeRight(32.3)
//                            new Back(10)
                    );
                case MIDDLE:
                    return new TrajectorySequenceContainer(
                            Speed::getFastConstraints,
                            new StrafeRight(35)
//                            new Back(10)
                    );
                case RIGHT:
                    return new TrajectorySequenceContainer(
                            Speed::getFastConstraints,
                            new StrafeRight(44.5),
                           new Back(8)
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
        //TeamMarkerPipeline.FFPosition position = TeamMarkerPipeline.FFPosition.RIGHT;

        drivetrain.setPoseEstimate(DropSpikeMark.startPose.getPose());
        PoseStorage.trajectoryPose = DropSpikeMark.startPose.getPose();
        claw.setBothClaw(Claw.ClawPos.CLOSE_POS);

        schedule(
                claw.setBothClaw(Claw.ClawPos.CLOSE_POS),
                new SequentialCommandGroup(
                        /*** YellowPixel ***/
                        new SequentialCommandGroup(
                        new TrajectorySequenceContainerFollowCommand(drivetrain,
                                DropSpikeMark.getTurnDrop(position)),
                        dropper.dropperSetPositionCommand(AutoDropper.DropPos.DROP),

                        /*** get pixel ***/
                        new TrajectorySequenceContainerFollowCommand(drivetrain,
                               DropSpikeMark.getTurn(position)),
                        claw.setFClaw(Claw.ClawPos.OPEN_POS),
                        /**drop pixel**/
                      //  new TrajectorySequenceContainerFollowCommand(drivetrain, getStrafe(position)),
                        new TrajectorySequenceContainerFollowCommand(drivetrain, getFactor(position)),
                        new ParallelCommandGroup(
                            new TrajectorySequenceContainerFollowCommand(drivetrain,
                                getDropped(position)) ,
                            new DisplacementCommand(3.5,
                                new SlideCommand(slide, arm, claw, Slide.SlideEnum.AUTO_LOW))
                        )
                        ),
                        claw.setBothClaw(Claw.ClawPos.OPEN_POS),
                        new WaitCommand(800),
                        new ParallelCommandGroup(
                        new TrajectorySequenceContainerFollowCommand(drivetrain,
                                new TrajectorySequenceContainer(Speed::getBaseConstraints,
                                        new Forward(7))),
                        new ResetCommand(slide,arm, claw)
                                ),
                        new TrajectorySequenceContainerFollowCommand(drivetrain,
                                getParked(position)),




                        /* Save Pose and end opmode*/
                        run(() -> PoseStorage.currentPose = drivetrain.getPoseEstimate()),

                        run(this::stop)
                )
        );
    }
}