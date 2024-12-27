package org.firstinspires.ftc.teamcode.opmode.teleop;

import static org.firstinspires.ftc.teamcode.subsystems.intake.Intake.Value.*;
import static org.firstinspires.ftc.teamcode.subsystems.outtake.Claw.Value.*;
import static org.firstinspires.ftc.teamcode.subsystems.outtake.Outtake.Value.*;
import static org.firstinspires.ftc.teamcode.subsystems.outtake.Outtake.Value.START;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.ParallelCommandGroup;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;
import com.arcrobotics.ftclib.command.WaitUntilCommand;
import com.arcrobotics.ftclib.command.button.Button;
import com.arcrobotics.ftclib.command.button.GamepadButton;
import com.arcrobotics.ftclib.command.button.Trigger;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.commands.ColorIntakeCommand;
import org.firstinspires.ftc.teamcode.commands.ScoreCommand;
import org.firstinspires.ftc.teamcode.commands.manual.DefaultDriveCommand;
import org.firstinspires.ftc.teamcode.commands.manual.SlidePowerHorizontalManual;
import org.firstinspires.ftc.teamcode.commands.manual.SlideVerticalManual;
import org.firstinspires.ftc.teamcode.subsystems.drive.MecDrive;
import org.firstinspires.ftc.teamcode.subsystems.intake.HorizontalSlide;
import org.firstinspires.ftc.teamcode.subsystems.intake.Intake;
import org.firstinspires.ftc.teamcode.subsystems.intake.IntakeServo;
import org.firstinspires.ftc.teamcode.subsystems.intake.PowerIntake;
import org.firstinspires.ftc.teamcode.subsystems.intake.SensorColor;
import org.firstinspires.ftc.teamcode.subsystems.outtake.Arm;
import org.firstinspires.ftc.teamcode.subsystems.outtake.Claw;
import org.firstinspires.ftc.teamcode.subsystems.outtake.Outtake;
import org.firstinspires.ftc.teamcode.subsystems.outtake.Pivot;
import org.firstinspires.ftc.teamcode.subsystems.outtake.Slide;
import org.firstinspires.ftc.teamcode.util.teleop.GamepadTrigger;
import org.firstinspires.ftc.teamcode.util.teleop.MatchOpMode;


@Config
@TeleOp
public class TeleOpMain extends MatchOpMode {
    //TODO: Add a on/off switch for drivetrain
    private GamepadEx driverGamepad, operatorGamepad;

    private Outtake outtake;
    private MecDrive drive;
//    private Climber climb;
    private Intake intake;
    private SensorColor sensorColor;

    public TeleOpMain() {}

    @Override
    public void robotInit() {
        drive = new MecDrive(hardwareMap);
        driverGamepad = new GamepadEx(gamepad1);
        operatorGamepad = new GamepadEx(gamepad2);
        sensorColor = new SensorColor(telemetry, hardwareMap);


        intake = new Intake(
                new HorizontalSlide(telemetry, hardwareMap, true),
                new IntakeServo(telemetry, hardwareMap, true),
                new PowerIntake(telemetry, hardwareMap, true),
                telemetry
        );

        outtake = new Outtake(
                new Slide(telemetry, hardwareMap, true),
                new Arm(telemetry, hardwareMap, true),
                new Claw(telemetry, hardwareMap, true),
                new Pivot(telemetry, hardwareMap, true),
                telemetry
        );

        intake.init();
        outtake.init();
    }

    @Override
    public void robotPeriodic(){
        outtake.periodic();
        intake.periodic();
        
//        if(intake.value == DOWNI){
//            new SequentialCommandGroup(
//                new WaitUntilCommand(()->intake.horizontalSlide.getEncoderDistance()<5),
//                new ParallelCommandGroup(
//                    outtake.setPosition(TRANSFER),
//                    outtake.setClawSetPoint(OPEN)
//                ),
//                new ParallelCommandGroup(
//                    outtake.setPosition(Outtake.Value.TRANSFER),
//                    outtake.setClawSetPoint(CLOSE)
//                )
//            );
//        }
    }

    @Override
    public void configureButtons() {

  //      drive.setDefaultCommand(new DefaultDriveCommand(drive, driverGamepad));
////INTAKE
        Trigger IntakeOuttake = (new GamepadTrigger(driverGamepad, GamepadKeys.Trigger.LEFT_TRIGGER))
                .whenPressed(intake.setPosition(INTAKE))
                .whenReleased(intake.setPosition(STOP));
        Trigger IntakeIntake = (new GamepadTrigger(driverGamepad, GamepadKeys.Trigger.RIGHT_TRIGGER))
                .whenPressed(intake.setPosition(OUTTAKE))
                .whenHeld(intake.setPosition(OUTTAKE))
                .whenReleased(intake.setPosition(STOP));
//        Button IntakeDown =(new GamepadTrigger(driverGamepad, GamepadKeys.Trigger.LEFT_TRIGGER))
//                .whenPressed(intake.setPosition(DOWNI));
//
//
//        //OUTTAKE
//
//        //SLIDE MANUALS
//        outtake.verticalSlide.setDefaultCommand(new SlideVerticalManual(
//                outtake.verticalSlide, operatorGamepad::getRightY));
////        intake.horizontalSlide.setDefaultCommand(new SlideHorizontalManual(intake.horizontalSlide,
////                operatorGamepad::getLeftX));
//
        //BUCKET
        Button LowBucket = (new GamepadButton(operatorGamepad, GamepadKeys.Button.X)
                .whenPressed(outtake.setPosition(LOW_BUCKET))
                .whenPressed(outtake.setClawSetPoint(CLOSE)));
        Button HighBucket = (new GamepadButton(operatorGamepad, GamepadKeys.Button.Y)
                .whenPressed(outtake.setPosition(HIGH_BUCKET))
                .whenPressed(outtake.setClawSetPoint(CLOSE)));
//
//        //SPECIMEN
        Button IntakeSpecimen = (new GamepadButton(operatorGamepad,GamepadKeys.Button.LEFT_BUMPER)
                .whenPressed(outtake.setPosition(SPECIMEN_WALL))
                .whenPressed(outtake.setClawSetPoint(OPEN)));
        Button HighRung = (new GamepadButton(operatorGamepad, GamepadKeys.Button.DPAD_UP)
                .whenPressed(outtake.setPosition(HIGH_RUNG))
                .whenPressed(outtake.setClawSetPoint(CLOSE)));
        Button lowRung = (new GamepadButton(operatorGamepad,GamepadKeys.Button.DPAD_LEFT)
                .whenPressed(outtake.setPosition(LOW_RUNG))
                .whenPressed(outtake.setClawSetPoint(CLOSE)));

        Button reset =(new GamepadButton(operatorGamepad,GamepadKeys.Button.DPAD_DOWN)
                .whenPressed(outtake.setPosition(START))
                .whenPressed(outtake.setClawSetPoint(OPEN)));


        Button score = (new GamepadButton(operatorGamepad,GamepadKeys.Button.RIGHT_BUMPER)
                .whenPressed(new ScoreCommand(outtake)));

//        Button SpecimenHigh = (new GamepadButton(operatorGamepad,GamepadKeys.Button.DPAD_DOWN)
//                .whenPressed(outtake.setPosition(SPECIMEN_HIGH_BAR)));
//        Button SpecimenLow = (new GamepadButton(operatorGamepad,GamepadKeys.Button.DPAD_RIGHT)
//                .whenPressed(outtake.setPosition(SPECIMEN_LOW_BAR)));


//        Trigger Climb =(new GamepadButton(operatorGamepad,GamepadKeys.Button.DPAD_DOWN)
//                .whenPressed(outtake.setPosition(START)));

        //CLAW
//        Trigger ClawOpen = (new GamepadTrigger(operatorGamepad,GamepadKeys.Trigger.LEFT_TRIGGER)
//                .whenPressed(outtake.setClawSetPoint(OPEN)));
//        Trigger ClawClose = (new GamepadTrigger(operatorGamepad,GamepadKeys.Trigger.RIGHT_TRIGGER)
//                .whenPressed(outtake.setClawSetPoint(CLOSE)));
        outtake.claw.setDefaultCommand(new ColorIntakeCommand(outtake.claw, sensorColor));

        //HORIZONTAL POWER
        Button HoutPower = (new GamepadButton(operatorGamepad,GamepadKeys.Button.A)
                .whenPressed(intake.hslidePower(HorizontalSlide.Value.OUT)))
                .whenReleased(intake.hslidePower(HorizontalSlide.Value.STOP));
        Button HinPower = (new GamepadButton(operatorGamepad,GamepadKeys.Button.B)
                .whenPressed(intake.hslidePower(HorizontalSlide.Value.IN))
                .whenReleased(intake.hslidePower(HorizontalSlide.Value.STOP)));
    }


    @Override
    public void matchStart() {}

 }
