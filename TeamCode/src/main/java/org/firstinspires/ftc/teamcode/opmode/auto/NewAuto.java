package org.firstinspires.ftc.teamcode.opmode.auto;


import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.ParallelAction;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.ParallelCommandGroup;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.subsystems.drive.MecDrive;
import org.firstinspires.ftc.teamcode.subsystems.intake.HorizontalSlide;
import org.firstinspires.ftc.teamcode.subsystems.intake.Intake;
import org.firstinspires.ftc.teamcode.subsystems.intake.IntakeServo;
import org.firstinspires.ftc.teamcode.subsystems.intake.PowerIntake;
import org.firstinspires.ftc.teamcode.util.teleop.MatchOpMode;

//@Disabled
@Autonomous(preselectTeleOp = "TeleOpMain")
public class NewAuto extends MatchOpMode {
    // Subsystems
    private final MecDrive drive = new MecDrive();
//    private final Intake intake = new Intake(
//            new HorizontalSlide(telemetry, hardwareMap,true),
//            new IntakeServo(telemetry, hardwareMap,true),
//            new PowerIntake(telemetry, hardwareMap,true)
//    );
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
            new ParallelCommandGroup(
                new InstantCommand(
                ()-> Actions.runBlocking(
                    new SequentialAction(
                        pathOne, // Example of a drive action
                        new ParallelAction( // several actions being run in parallel
                            pathTwo
//                            intake.setPositionAction(Intake.Value.INTAKE)//TODO: Does this work
                        )
                    )
                ))
//                intake.setPosition(Intake.Value.INTAKE)//TODO: Or This
            ),
            run(() -> MecDrive.currentPose = drive.getPose()),
            run(this::stop)
        );
    }
}