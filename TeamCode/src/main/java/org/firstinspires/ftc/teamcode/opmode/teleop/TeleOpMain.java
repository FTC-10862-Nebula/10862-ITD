package org.firstinspires.ftc.teamcode.opmode.teleop;

import static org.firstinspires.ftc.teamcode.subsystems.intake.Intake.Value.OUTTAKE;
import static org.firstinspires.ftc.teamcode.subsystems.outtake.Outtake.Value.INTAKE_SPECIMEN;
import static org.firstinspires.ftc.teamcode.subsystems.outtake.Outtake.Value.READY_TO_INTAKE_SPECIMEN;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.command.Command;
import com.arcrobotics.ftclib.command.ConditionalCommand;
import com.arcrobotics.ftclib.command.SelectCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;
import com.arcrobotics.ftclib.command.button.Button;
import com.arcrobotics.ftclib.command.button.GamepadButton;
import com.arcrobotics.ftclib.command.button.Trigger;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.commands.manual.DefaultDriveCommand;
import org.firstinspires.ftc.teamcode.commands.manual.SlideHorizontalManual;
import org.firstinspires.ftc.teamcode.commands.manual.SlideVerticalManual;
import org.firstinspires.ftc.teamcode.subsystems.climber.Climber;
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
import org.firstinspires.ftc.teamcode.util.teleop.GamepadTrigger;
import org.firstinspires.ftc.teamcode.util.teleop.MatchOpMode;

import java.util.function.Supplier;

@Config
@TeleOp
public class TeleOpMain extends MatchOpMode {
    //TODO: Add a on/off switch for drivetrain
    private GamepadEx driverGamepad, operatorGamepad;




    private Outtake outtake;
    private MecDrive drive;
//    private Climber climb;
    private Intake intake;

    public TeleOpMain() {}

    @Override
    public void robotInit() {
        drive = new MecDrive(hardwareMap);
        driverGamepad = new GamepadEx(gamepad1);
        operatorGamepad = new GamepadEx(gamepad2);

        intake = new Intake(
                new HorizontalSlide(telemetry, hardwareMap, false),
                new IntakeServo(telemetry, hardwareMap, false),
                new PowerIntake(telemetry, hardwareMap, false),
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
    }


    @Override
    public void robotPeriodic(){
        outtake.periodic();
        intake.periodic();
    }
    @Override
    public void configureButtons() {
        drive.setDefaultCommand(new DefaultDriveCommand(drive, driverGamepad));
//INTAKE
//        Trigger IntakeIntake = (new GamepadTrigger(driverGamepad, GamepadKeys.Trigger.RIGHT_TRIGGER))
//                .whenPressed(intake.setPosition(Intake.Value.INTAKE))
//                .whenHeld(intake.setPosition(Intake.Value.INTAKE))
//                .whenReleased(intake.setPosition(Intake.Value.HOLD));
//        Trigger IntakeOuttake = (new GamepadTrigger(driverGamepad, GamepadKeys.Trigger.LEFT_TRIGGER))
//                .whenPressed(intake.setPosition(OUTTAKE))
//                .whenReleased(intake.setPosition(Intake.Value.STOP));
        //y - up/down
        //x- right left

        //OUTTAKE
        outtake.verticalSlide.setDefaultCommand(new SlideVerticalManual(
                outtake.verticalSlide, operatorGamepad::getRightY));
//        Trigger OuttakeBucket = (new GamepadTrigger(operatorGamepad,GamepadKeys.Trigger.LEFT_TRIGGER)
//                .whenPressed(outtake.setPosition(Outtake.Value.OUTTAKE_BUCKET)));
//        Button lowBucket = (new GamepadButton(operatorGamepad, GamepadKeys.Button.A)
//                .whenPressed(outtake.setPosition(Outtake.Value.LOW_BUCKET)));
//        Button highBucket = (new GamepadButton(operatorGamepad, GamepadKeys.Button.B)
//                .whenPressed(outtake.setPosition(Outtake.Value.HIGH_BUCKET)));
//        Button transfer = (new GamepadButton(operatorGamepad, GamepadKeys.Button.X)
//                .whenPressed(new SequentialCommandGroup(
//                        intake.setPosition(Intake.Value.TRANSFER),
//                        outtake.setPosition(Outtake.Value.READY_TO_INTAKE_SAMPLE),
//                        new WaitCommand(300),
//                        outtake.setPosition(Outtake.Value.INTAKE_SAMPLE))));
        Button readySpecimen = (new GamepadButton(operatorGamepad,GamepadKeys.Button.DPAD_UP)
                        .whenPressed(outtake.setPosition(READY_TO_INTAKE_SPECIMEN)));

        Button intakeSpecimen = (new GamepadButton(operatorGamepad,GamepadKeys.Button.DPAD_DOWN))
                .whenPressed(outtake.setPosition(INTAKE_SPECIMEN));
    //    Button outtakeSpecimen

        Button climb = (new GamepadButton(driverGamepad, GamepadKeys.Button.X)
                .whileHeld(outtake.setPosition(Outtake.Value.CLIMB_LOW_RUNG))
                .whenReleased(outtake.setPosition(Outtake.Value.CLIMB_UP)));

        Button OuttakeFirstBar = (new GamepadButton(operatorGamepad, GamepadKeys.Button.X)
                .whenPressed(outtake.setPosition(Outtake.Value.LOW_RUNG)));
        Button OuttakeSecondBar = (new GamepadButton(operatorGamepad, GamepadKeys.Button.Y)
                .whenPressed(outtake.setPosition(Outtake.Value.HIGH_RUNG)));
//        Trigger OuttakeSpecimenBar = (new GamepadTrigger(operatorGamepad,GamepadKeys.Trigger.RIGHT_TRIGGER)
//                .whenPressed(outtake.setPosition(Outtake.Value.OUTTAKE_SPECIMEN_BAR)));
        Trigger OuttakeSpecimenBasr = (new GamepadTrigger(operatorGamepad,GamepadKeys.Trigger.RIGHT_TRIGGER)
                .whenPressed(new ConditionalCommand(
                        outtake.setPosition(Outtake.Value.SPECIMEN_HIGH_BAR),
                        outtake.setPosition(Outtake.Value.SPECIMEN_LOW_BAR),
                        ()->outtake.getIfHigh())));
        //()->(outtake.value==Outtake.Value.HIGH_RUNG)

//        intake.horizontalSlide.setDefaultCommand(new SlideHorizontalManual(intake.horizontalSlide,
//            operatorGamepad::getLeftX));
    }


    @Override
    public void matchStart() {}

}
