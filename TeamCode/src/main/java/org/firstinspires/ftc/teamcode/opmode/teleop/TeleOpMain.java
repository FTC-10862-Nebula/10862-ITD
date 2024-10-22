package org.firstinspires.ftc.teamcode.opmode.teleop;

import android.widget.Button;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.button.GamepadButton;
import com.arcrobotics.ftclib.command.button.Trigger;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion;
import org.firstinspires.ftc.teamcode.commands.manual.DefaultDriveCommand;
import org.firstinspires.ftc.teamcode.subsystems.climber.Climber;
import org.firstinspires.ftc.teamcode.subsystems.drive.MecDrive;
import org.firstinspires.ftc.teamcode.subsystems.intake.HorizontalSlide;
import org.firstinspires.ftc.teamcode.subsystems.intake.Intake;
import org.firstinspires.ftc.teamcode.subsystems.intake.IntakeServo;
import org.firstinspires.ftc.teamcode.subsystems.intake.PowerIntake;
import org.firstinspires.ftc.teamcode.subsystems.outtake.Arm;
import org.firstinspires.ftc.teamcode.subsystems.outtake.Claw;
import org.firstinspires.ftc.teamcode.subsystems.outtake.Outtake;
import org.firstinspires.ftc.teamcode.subsystems.outtake.VerticalSlide;
import org.firstinspires.ftc.teamcode.util.teleop.GamepadTrigger;
import org.firstinspires.ftc.teamcode.util.teleop.MatchOpMode;

@Config
@TeleOp
public class TeleOpMain extends MatchOpMode {
    //TODO: Add a on/off switch for drivetrain
    private GamepadEx driverGamepad, operatorGamepad;
//    private Outtake outtake;
    private MecDrive drive;
//    private HardwareMap hardwareMap;
//    private HorizontalSlide horizontalSlide;
//    private VerticalSlide verticalSlide;
//    private IntakeServo intakeServo;
//    private PowerIntake intake;
//    private Arm arm;
//    private Claw claw;
//    private Climber climb;

    public TeleOpMain() {
    }

    @Override
    public void robotInit() {
        driverGamepad = new GamepadEx(gamepad1);
        operatorGamepad = new GamepadEx(gamepad2);
        drive = new MecDrive(hardwareMap);

//        claw = new Claw(telemetry, hardwareMap, true);
//        intake = new PowerIntake(telemetry, hardwareMap, true);
//        climb = new Climber(telemetry, hardwareMap, true);
//        arm = new Arm(telemetry, hardwareMap, true);
//        horizontalSlide = new HorizontalSlide(telemetry, hardwareMap, true);
    }


    @Override
    public void configureButtons() {
      drive.setDefaultCommand(new DefaultDriveCommand(drive, driverGamepad, true));
     //   drivetrain.setDefaultCommand(new DefaultDriveCommand(drivetrain, driverGamepad, true));
//        Trigger OUTTAKE = (new GamepadTrigger(driverGamepad, GamepadKeys.Trigger.LEFT_TRIGGER)
//            .whenPressed(new InstantCommand(intake::setDown))
//            .whileHeld(intake.setSetPointCommand(PowerIntake.IntakePower.OUTTAKE)))
////                .whenPressed(cycleTracker.trackCycle())
//            .whenReleased(intake.setSetPointCommand(PowerIntake.IntakePower.STOP))
//            .whenReleased(new InstantCommand(intake::setUp));
//
////        Button climbDown  = (new GamepadButton(operatorGamepad, GamepadKeys.Button.DPAD_DOWN))
////                .whileHeld(climb.setSetPointCommand(Climber.ClimbEnum.REST));
//        climb.setDefaultCommand(new ClimberMoveManual(climb, operatorGamepad::getLeftY));

        //y - up/down
        //x- right left
        //claw


//        intake = new PowerIntake(
//                new HorizontalSlide(telemetry, hardwareMap, false),
//                new IntakeServo(telemetry, hardwareMap, false),
//                new PowerIntake(telemetry, hardwareMap, false)
//                );

        //      Button = (new GamepadButton(operatorGamepad, GamepadKeys.Button.DPAD_LEFT))

//                .whenPressed(outtake.setPosition(Outtake.Value.READY_TO_INTAKE_SAMPLE))
//                .whileHeld(outtake.setPosition(Outtake.Value.INTAKE_SAMPLE))
//                .whenReleased(outtake.setPosition(Outtake.Value.READY_TO_INTAKE_SAMPLE))
//
//        );

//        outtake = new Outtake(
//                new VerticalSlide(telemetry, hardwareMap, false),
//                new Arm(telemetry, hardwareMap, false),
//                new Claw(telemetry, hardwareMap, false)
//        );
//       Button outtake = new GamepadButton(operatorGamepad,GamepadKeys.Button.DPAD_UP)
//               .whenPressed()
//
//    }

    }
        @Override
        public void matchStart() {
        }
    }
