package org.firstinspires.ftc.teamcode.opmode.teleop;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.command.button.Button;
import com.arcrobotics.ftclib.command.button.GamepadButton;
import com.arcrobotics.ftclib.command.button.Trigger;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.commands.manual.DefaultDriveCommand;
import org.firstinspires.ftc.teamcode.commands.manual.SlideHorizontalManual;
import org.firstinspires.ftc.teamcode.commands.manual.SlideVerticalManual;
import org.firstinspires.ftc.teamcode.subsystems.drive.MecDrive;
import org.firstinspires.ftc.teamcode.subsystems.intake.HorizontalSlide;
import org.firstinspires.ftc.teamcode.subsystems.intake.Intake;
import org.firstinspires.ftc.teamcode.subsystems.intake.IntakeServo;
import org.firstinspires.ftc.teamcode.subsystems.intake.IntakeWOSlides;
import org.firstinspires.ftc.teamcode.subsystems.intake.PowerIntake;
import org.firstinspires.ftc.teamcode.subsystems.outtake.Outtake;
import org.firstinspires.ftc.teamcode.util.teleop.GamepadTrigger;
import org.firstinspires.ftc.teamcode.util.teleop.MatchOpMode;

import java.util.function.Supplier;


    @Config
    @TeleOp
    public class TOMwoSlides extends MatchOpMode {
        //TODO: Add a on/off switch for drivetrain
        private GamepadEx driverGamepad, operatorGamepad;




        //    private Outtake outtake;
        private MecDrive drive;
        //   private HorizontalSlide horizontalSlide;
//    private VerticalSlide verticalSlide;
//    private IntakeServo intakeServo;
        private PowerIntake powerIntake;
        //    private Arm arm;
//    private Pivot pivot;
//    private Claw claw;
//    private Climber climb;
        private IntakeWOSlides intaker;
        private Intake intake;
        private Outtake outtake;
        private Supplier<Double> DoubleSupplier;

        public TOMwoSlides() {}

        @Override
        public void robotInit() {
      //      drive = new MecDrive(hardwareMap);
            driverGamepad = new GamepadEx(gamepad1);
            operatorGamepad = new GamepadEx(gamepad2);
      //      drive = new MecDrive(hardwareMap);
            intaker = new IntakeWOSlides(
                    new IntakeServo(telemetry, hardwareMap, true),
                    new PowerIntake(telemetry, hardwareMap, true)
            );
//            intake = new Intake(
//                    new HorizontalSlide(telemetry, hardwareMap, true),
//                    new IntakeServo(telemetry, hardwareMap, true),
//                    new PowerIntake(telemetry, hardwareMap, true)
//            );

//        outtake = new Outtake(
//                new VerticalSlide(telemetry, hardwareMap, true),
//                new Arm(telemetry, hardwareMap, true),
//                new Claw(telemetry, hardwareMap, true),
//                new Pivot(telemetry, hardwareMap, true)
//        );


//        intakeServo = new IntakeServo(telemetry, hardwareMap, true);
////        verticalSlide = new VerticalSlide(telemetry, hardwareMap, true);
//       horizontalSlide = new HorizontalSlide(telemetry, hardwareMap, true);
////        claw = new Claw(telemetry, hardwareMap, true);
//        powerIntake = new PowerIntake(telemetry, hardwareMap, true);
////        outtake = new Outtake (verticalSlide, arm, claw, pivot);
//        pivot = new Pivot(telemetry, hardwareMap, true);
//        climb = new Climber(telemetry,hardwareMap, true);
//        arm = new Arm(telemetry, hardwareMap, true);

        }


        @Override
        public void configureButtons() {
 //           drive.setDefaultCommand(new DefaultDriveCommand(drive, driverGamepad));
            //y - up/down
            //x- right left
//OUTTAKE
//        Trigger OuttakeBucket = (new GamepadTrigger(operatorGamepad,GamepadKeys.Trigger.LEFT_TRIGGER)
//                .whenPressed(outtake.setPosition(Outtake.Value.OUTTAKE_BUCKET)));
//
//        Trigger OuttakeSpecimenBar = (new GamepadTrigger(operatorGamepad,GamepadKeys.Trigger.RIGHT_TRIGGER)
//                .whenPressed(outtake.setPosition(Outtake.Value.OUTTAKE_SPECIMEN_BAR)));
//
//        Button OuttakeLowBucket = (new GamepadButton(operatorGamepad, GamepadKeys.Button.A)
//                .whenPressed(outtake.setPosition(Outtake.Value.LOW_BUCKET)));
//
//        Button OuttakeHighBucket = (new GamepadButton(operatorGamepad, GamepadKeys.Button.B)
//                .whenPressed(outtake.setPosition(Outtake.Value.HIGH_BUCKET)));
//
//        Button OuttakeFirstBar = (new GamepadButton(operatorGamepad, GamepadKeys.Button.X)
//                .whenPressed(outtake.setPosition(Outtake.Value.LOW_RUNG)));

//            Button OuttakeSecondBar = (new GamepadButton(operatorGamepad, GamepadKeys.Button.Y)
//                    .whenPressed(outtake.setPosition(Outtake.Value.HIGH_RUNG)));





//INTAKE
            Trigger IntakeIntake = (new GamepadTrigger(operatorGamepad, GamepadKeys.Trigger.RIGHT_TRIGGER))
                    .whenPressed(intaker.setPosition(IntakeWOSlides.Value.INTAKE))
                    .whenHeld(intaker.setPosition(IntakeWOSlides.Value.INTAKE))
                    .whenReleased(intaker.setPosition(IntakeWOSlides.Value.HOLD));
            Trigger IntakeOuttake = (new GamepadTrigger(operatorGamepad, GamepadKeys.Trigger.LEFT_TRIGGER))
                    .whenPressed(intaker.setPosition(IntakeWOSlides.Value.OUTTAKE))
                    .whenReleased(intaker.setPosition(IntakeWOSlides.Value.HOLD));
//            outtake.verticalSlide.setDefaultCommand(new SlideVerticalManual(
//                    outtake.verticalSlide, operatorGamepad::getRightY));
//            intake.horizontalSlide.setDefaultCommand(new SlideHorizontalManual(intake.horizontalSlide,
//                    ()->operatorGamepad.getRightX()));
        }

        @Override
        public void matchStart() {}

    }
