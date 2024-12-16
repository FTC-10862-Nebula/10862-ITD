package org.firstinspires.ftc.teamcode.opmode.auto;


import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.ParallelAction;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.ParallelCommandGroup;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.subsystems.drive.MecDrive;
import org.firstinspires.ftc.teamcode.subsystems.intake.HorizontalSlide;
import org.firstinspires.ftc.teamcode.subsystems.intake.Intake;
import org.firstinspires.ftc.teamcode.subsystems.intake.IntakeServo;
import org.firstinspires.ftc.teamcode.subsystems.intake.PowerIntake;
import org.firstinspires.ftc.teamcode.subsystems.outtake.Arm;
import org.firstinspires.ftc.teamcode.subsystems.outtake.Claw;
import org.firstinspires.ftc.teamcode.subsystems.outtake.Outtake;
import org.firstinspires.ftc.teamcode.subsystems.outtake.Pivot;
import org.firstinspires.ftc.teamcode.subsystems.outtake.VerticalSlide;
import org.firstinspires.ftc.teamcode.util.teleop.MatchOpMode;

//@Disabled
@Autonomous(preselectTeleOp = "TeleOpMain")
public class NewAuto extends MatchOpMode {
    // Subsystems
    private  MecDrive drive;
    private  Intake intake;
    private  Outtake outtake;

    Action pathOne;

    @Override
    public void robotInit()
    {
        drive = new MecDrive(hardwareMap);


        intake = new Intake(
                new HorizontalSlide(telemetry, hardwareMap, true),
                new IntakeServo(telemetry, hardwareMap, true),
                new PowerIntake(telemetry, hardwareMap, true),
                telemetry
        );

         outtake = new Outtake(
                new VerticalSlide(telemetry, hardwareMap, true),
                new Arm(telemetry, hardwareMap, true),
                new Claw(telemetry, hardwareMap, true),
                new Pivot(telemetry, hardwareMap, true),
                telemetry
        );

        intake.init();
        outtake.init();
        pathOne = drive.drivetrain.actionBuilder(drive.getPose())
            .lineToXConstantHeading(-50)
            .build();
    }

    public void matchStart() {
        schedule(
            new ParallelCommandGroup(
                new InstantCommand(
                ()-> Actions.runBlocking(
                    new SequentialAction(
                        pathOne
                        )
                    )
                )),

//                intake.setPosition(Intake.Value.INTAKE)//TODO: Or This
            run(() -> MecDrive.currentPose = drive.getPose()),
           run(this::stop)
        );
    }
}