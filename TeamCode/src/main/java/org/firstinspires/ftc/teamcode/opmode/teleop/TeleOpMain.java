package org.firstinspires.ftc.teamcode.opmode.teleop;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.commands.manual.DefaultDriveCommand;
import org.firstinspires.ftc.teamcode.subsystems.drive.MecDrive;
import org.firstinspires.ftc.teamcode.subsystems.intake.Intake;
import org.firstinspires.ftc.teamcode.subsystems.outtake.Outtake;
import org.firstinspires.ftc.teamcode.util.teleop.MatchOpMode;

import java.util.function.DoubleSupplier;
import java.util.function.Supplier;

@Config
@TeleOp
public class TeleOpMain extends MatchOpMode {
    //TODO: Add a on/off switch for drivetrain
    private GamepadEx driverGamepad, operatorGamepad;




//    private Outtake outtake;
    private MecDrive drive;
    private HorizontalSlide horizontalSlide;
//    private VerticalSlide verticalSlide;
//    private IntakeServo intakeServo;
//    private PowerIntake powerIntake;
//    private Arm arm;
//    private Pivot pivot;
//    private Claw claw;
//    private Climber climb;
    private Intake intake;
    private Outtake outtake;
    private Supplier<Double> DoubleSupplier;

    public TeleOpMain() {}

    @Override
    public void robotInit() {
        drive = new MecDrive(hardwareMap);
        driverGamepad = new GamepadEx(gamepad1);
        operatorGamepad = new GamepadEx(gamepad2);
        drive = new MecDrive(hardwareMap);
        intake = new Intake(
                new HorizontalSlide(telemetry, hardwareMap, true),
                new IntakeServo(telemetry, hardwareMap, true),
                new PowerIntake(telemetry, hardwareMap, true)
        );
        outtake = new Outtake(
                new VerticalSlide(telemetry, hardwareMap, true),
                new Arm(telemetry, hardwareMap, true),
                new Claw(telemetry, hardwareMap, true),
                new Pivot(telemetry, hardwareMap, true)
        );
//        intake = new Intake(
//                new HorizontalSlide(telemetry, hardwareMap, true),
//                new IntakeServo(telemetry, hardwareMap, true),
//                new PowerIntake(telemetry, hardwareMap, true)
//        );
//        outtake = new Outtake(
//                new VerticalSlide(telemetry, hardwareMap, true),
//                new Arm(telemetry, hardwareMap, true),
//                new Claw(telemetry, hardwareMap, true),
//                new Pivot(telemetry, hardwareMap, true)
//        );


//        intakeServo = new IntakeServo(telemetry, hardwareMap, true);
//        verticalSlide = new VerticalSlide(telemetry, hardwareMap, true);
        horizontalSlide = new HorizontalSlide(telemetry, hardwareMap, true);
//        claw = new Claw(telemetry, hardwareMap, true);
//        powerIntake = new PowerIntake(telemetry, hardwareMap, true);
//        outtake = new Outtake (verticalSlide, arm, claw, pivot);
//        pivot = new Pivot(telemetry, hardwareMap, true);
//        climb = new Climber(telemetry,hardwareMap, true);
//        arm = new Arm(telemetry, hardwareMap, true);
//        intake = new Intake()

    }


    @Override
    public void configureButtons() {
        drive.setDefaultCommand(new DefaultDriveCommand(drive,driverGamepad,true));
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

        Trigger OuttakeBucket = (new GamepadTrigger(operatorGamepad,GamepadKeys.Trigger.LEFT_TRIGGER)
                .whenPressed(outtake.setPosition(Outtake.Value.OUTTAKE_BUCKET)));

        Trigger OuttakeSpecimenBar = (new GamepadTrigger(operatorGamepad,GamepadKeys.Trigger.RIGHT_TRIGGER)
                .whenPressed(outtake.setPosition(Outtake.Value.OUTTAKE_SPECIMEN_BAR)));

        Button OuttakeLowBucket = (new GamepadButton(operatorGamepad, GamepadKeys.Button.A)
                .whenPressed(outtake.setPosition(Outtake.Value.LOW_BUCKET)));

        Button OuttakeHighBucket = (new GamepadButton(operatorGamepad, GamepadKeys.Button.B)
                .whenPressed(outtake.setPosition(Outtake.Value.HIGH_BUCKET)));

        Button OuttakeFirstBar = (new GamepadButton(operatorGamepad, GamepadKeys.Button.X)
                .whenPressed(outtake.setPosition(Outtake.Value.LOW_RUNG)));

        Button OuttakeSecondBar = (new GamepadButton(operatorGamepad, GamepadKeys.Button.Y)
                .whenPressed(outtake.setPosition(Outtake.Value.HIGH_RUNG)));

        drive.setDefaultCommand(new DefaultDriveCommand(drive,driverGamepad));


        Trigger IntakeIntake = (new GamepadTrigger(driverGamepad, GamepadKeys.Trigger.RIGHT_TRIGGER))
                .whenPressed(intake.setPosition(Intake.Value.START));
//ALEX BUTTONS NEW
//        Trigger OuttakeBucket = (new GamepadTrigger(operatorGamepad,GamepadKeys.Trigger.LEFT_TRIGGER)
//                .whenPressed(outtake.setPosition(Outtake.Value.OUTTAKE_BUCKET)));
//
//
//
//        Button OuttakeLowBucket = (new GamepadButton(operatorGamepad, GamepadKeys.Button.A)
//                .whenPressed(outtake.setPosition(Outtake.Value.LOW_BUCKET)));
//
//        Button OuttakeHighBucket = (new GamepadButton(operatorGamepad, GamepadKeys.Button.B)
//                .whenPressed(outtake.setPosition(Outtake.Value.HIGH_BUCKET)));
//
//        Button OuttakeFirstRung = (new GamepadButton(operatorGamepad, GamepadKeys.Button.X)
//                .whenPressed(outtake.setPosition(Outtake.Value.LOW_RUNG)));
//
//        Button OuttakeSecondRung = (new GamepadButton(operatorGamepad, GamepadKeys.Button.Y)
//                .whenPressed(outtake.setPosition(Outtake.Value.HIGH_RUNG)));
//
//
//
//        Trigger IntakeIntake = (new GamepadTrigger(operatorGamepad, GamepadKeys.Trigger.RIGHT_TRIGGER))
//                .whenPressed(intake.setPosition(Intake.Value.START));

        Trigger IntakePoop = (new GamepadTrigger(driverGamepad, GamepadKeys.Trigger.LEFT_TRIGGER))
                .whenPressed(intake.setPosition(Intake.Value.START));;

    intake.horizontalSlide.setDefaultCommand(new SlideHorizontalManual(horizontalSlide,
            ()->operatorGamepad.getRightX()));



//        Button slideLow  = (new GamepadButton(operatorGamepad, GamepadKeys.Button.X))
//                .whenPressed(new HorizontalSlide (horizontalSlide,arm,claw, horizontalSlide.);

//        Button slideMid  = (new GamepadButton(operatorGamepad, GamepadKeys.Button.B))
//
//        Button CLAW;
//        CLAW = (new Button(operatorGamepad, GamepadKeys.Button.B);
//
//        Trigger INTAKE = (new GamepadTrigger(operatorGamepad, GamepadKeys.Trigger.RIGHT_TRIGGER)
//                .whenPressed(intake.getCurrentCommand())
//        );



    }



    @Override
    public void matchStart() {}

}
