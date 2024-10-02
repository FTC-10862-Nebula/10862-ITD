package org.firstinspires.ftc.teamcode.opmode.auto.league;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.command.ParallelCommandGroup;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

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
public class RedWingRoboLobo extends MatchOpMode {
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


//    public static DropSpikeMark aDropSpikeMark;
//
//    public static class DropSpikeMark {
        public static Pose2dContainer startPose = new Pose2dContainer(10, 65, 270);
        //^^ Has Wrong Coordinates
        static TrajectorySequenceContainer getTurnDrop(TeamMarkerPipeline.FFPosition position) {
            switch (position) {
                default:
                case LEFT:
                    return new TrajectorySequenceContainer(
                            Speed::getBaseConstraints,
                            new Back(35.5),
                            new Turn(90),
                            new Back(2)
                    );
                case MIDDLE:
                    return new TrajectorySequenceContainer(
                            Speed::getBaseConstraints,
                            new Back(30.5)
                    );

                case RIGHT:
                    return new TrajectorySequenceContainer(
                            Speed::getBaseConstraints,
                            new Back(33.5),
                            new Turn(-90),
                            new Back(2)
                            );
            }
        }

        static TrajectorySequenceContainer getTurn(TeamMarkerPipeline.FFPosition position) {
            switch (position) {
                default:
                case LEFT:
                    return new TrajectorySequenceContainer(
                            Speed::getBaseConstraints,
                            new Forward(1.3),
                            new StrafeRight(38)
                    );
                case MIDDLE:
                    return new TrajectorySequenceContainer(
                            Speed::getBaseConstraints,
                                new Forward(27.2),
                                new Turn (-89)
//                            new StrafeRight(26),
//                            new Turn(-90),
//                            new StrafeRight(30.8)

                    );
                case RIGHT:
                    return new TrajectorySequenceContainer(
                            Speed::getBaseConstraints,
                            new Forward(2.2),
                            new StrafeLeft(39)
                    );
            }
        }
//    }

        static TrajectorySequenceContainer getBack(TeamMarkerPipeline.FFPosition position) {
            switch (position) {
                default:
                    case LEFT:
                        return new TrajectorySequenceContainer(
                                Speed::getFastConstraints,
                                //97
                                new Turn(176.6),
                                new Back(92)
                        );
                case MIDDLE:
                    return new TrajectorySequenceContainer(
                            Speed::getFastConstraints,
                            //120
//                            new Turn(-90),
                            new Back(91)
                    );
                case RIGHT:
                    return new TrajectorySequenceContainer(
                            Speed::getFastConstraints,
                            //93
                            new Back(92.5)
                    );
            }
        }

        static TrajectorySequenceContainer getDrop(TeamMarkerPipeline.FFPosition position) {
            switch (position) {
                case LEFT:
                default:
                    return new TrajectorySequenceContainer(
                            Speed::getSlowConstraints,
                            new StrafeRight(38.5),
                            new Back(6)
                    );
                case MIDDLE:
                    return new TrajectorySequenceContainer(
                            Speed::getSlowConstraints,
                            new StrafeRight(34),
                            new Back(6.3)

                    );
                case RIGHT:
                    return new TrajectorySequenceContainer(
                            Speed::getSlowConstraints,
                            new StrafeRight(30),
                            new Back(5)
                    );
            }
        }

        static TrajectorySequenceContainer getPark(TeamMarkerPipeline.FFPosition position) {
            switch (position) {
                case LEFT:
                    return new TrajectorySequenceContainer(
                            Speed::getFastConstraints,
                            new StrafeLeft(41)
//                            new Back(12)
                    );
                case MIDDLE:
                    return new TrajectorySequenceContainer(
                            Speed::getFastConstraints,
                            new StrafeLeft(33)
//                            new Back(10)
                    );
                default:
                case RIGHT:
                    return new TrajectorySequenceContainer(
                            Speed::getFastConstraints,
                            new StrafeLeft(33.5)
 //                          new Back(12.5)
                    );
            }
        }



    @Override
    public void robotInit() {
        drivetrain = new Drivetrain(new MecanumDrive(hardwareMap, telemetry), telemetry);
        vision = new FFVision(hardwareMap, telemetry);
        intake = new PowerIntake(telemetry, hardwareMap, true);
        arm = new Arm(telemetry, hardwareMap,true);
//        climber = new Climber(telemetry, hardwareMap, true);
//        climber.setSetPointCommand(Climber.ClimbEnum.REST);
        claw = new Claw(telemetry, hardwareMap, true);
        slide = new Slide(telemetry, hardwareMap, true);

        dropper = new AutoDropper(telemetry, hardwareMap, true);
        sensorColor = new SensorColor(telemetry,hardwareMap );


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
        claw.setBothClaw(Claw.ClawPos.CLOSE_POS);

        schedule(
                claw.setBothClaw(Claw.ClawPos.CLOSE_POS),

            new SequentialCommandGroup(
                /*** YellowPixel ***/
                new TrajectorySequenceContainerFollowCommand(drivetrain,
                       getTurnDrop(position)),
                    dropper.dropperSetPositionCommand(AutoDropper.DropPos.DROP),

                /*** get pixel ***/
                new ParallelCommandGroup(
                    new TrajectorySequenceContainerFollowCommand(drivetrain,
                        getTurn(position))
                ),
          //      new AutoIntakeCommand(claw, intake, sensorColor, drivetrain),

                /**drop pixel**/
                new SequentialCommandGroup(
                    new TrajectorySequenceContainerFollowCommand(drivetrain,
                            getBack(position)),
                    new ParallelCommandGroup(
                        new TrajectorySequenceContainerFollowCommand(drivetrain,
                            getDrop(position)),
                        new DisplacementCommand(3.5,
                        new SlideCommand(slide, arm, claw, Slide.SlideEnum.AUTO_LOW))
                    )
                ),

//                new WaitCommand(300),
                claw.setBothClaw(Claw.ClawPos.OPEN_POS),
                new WaitCommand(800),
                new ParallelCommandGroup(
                new TrajectorySequenceContainerFollowCommand(drivetrain,
                    new TrajectorySequenceContainer(Speed::getBaseConstraints,
                        new Forward(7))),
                new ResetCommand(slide,arm, claw)
                        ),

                new TrajectorySequenceContainerFollowCommand(drivetrain,
                    getPark(position)),

                /* Save Pose and end opmode*/
                run(() -> PoseStorage.currentPose = drivetrain.getPoseEstimate()),
                run(this::stop)
            )
        );
    }
}