package org.firstinspires.ftc.teamcode.opmode.teleop.misc;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.button.Button;
import com.arcrobotics.ftclib.command.button.GamepadButton;
import com.arcrobotics.ftclib.command.button.Trigger;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.commands.ClimberMoveManual;
import org.firstinspires.ftc.teamcode.commands.ColorSensorCommand;
import org.firstinspires.ftc.teamcode.commands.arm.position.ResetCommand;
import org.firstinspires.ftc.teamcode.commands.arm.position.SlideCommand;
import org.firstinspires.ftc.teamcode.commands.arm.slide.SlideMoveManual;
import org.firstinspires.ftc.teamcode.commands.drive.teleop.DefaultDriveCommand;
import org.firstinspires.ftc.teamcode.subsystems.AutoDropper;
import org.firstinspires.ftc.teamcode.subsystems.Claw;
import org.firstinspires.ftc.teamcode.subsystems.arm.Arm;
import org.firstinspires.ftc.teamcode.subsystems.climber.Climber;
import org.firstinspires.ftc.teamcode.subsystems.drive.mec.Drivetrain;
import org.firstinspires.ftc.teamcode.subsystems.drive.mec.MecanumDrive;
import org.firstinspires.ftc.teamcode.subsystems.intake.PowerIntake;
import org.firstinspires.ftc.teamcode.subsystems.sensor.SensorColor;
import org.firstinspires.ftc.teamcode.subsystems.slide.Slide;
import org.firstinspires.ftc.teamcode.util.teleop.GamepadTrigger;
import org.firstinspires.ftc.teamcode.util.teleop.MatchOpMode;
@Disabled
@Config
@TeleOp
public class IntakeOnColorSensor extends MatchOpMode {
    //TODO: Add a on/off switch for drivetrain
    private GamepadEx driverGamepad, operatorGamepad;
    private PowerIntake intake;
    private Claw claw;
    private SensorColor sensorColor;
    public IntakeOnColorSensor() {}

    @Override
    public void robotInit() {
        driverGamepad = new GamepadEx(gamepad1);
        operatorGamepad = new GamepadEx(gamepad2);

        claw = new Claw(telemetry, hardwareMap, true);
        intake = new PowerIntake(telemetry, hardwareMap, true);
        sensorColor = new SensorColor(telemetry, hardwareMap);
    }


    @Override
    public void configureButtons() {
        //Claw
        Button closeF = (new GamepadTrigger(operatorGamepad, GamepadKeys.Trigger.LEFT_TRIGGER)
                .whenPressed(claw.setBothClaw(Claw.ClawPos.CLOSE_POS)));

        Button closeB= (new GamepadTrigger(operatorGamepad,  GamepadKeys.Trigger.RIGHT_TRIGGER)
                .whenPressed(claw.setBothClaw(Claw.ClawPos.OPEN_POS)));

//      Button openF = (new GamepadButton(operatorGamepad, GamepadKeys.Button.LEFT_BUMPER)
//               .whenPressed(claw.setFClaw(Claw.ClawPos.OPEN_POS)));
        Button openB= (new GamepadButton(operatorGamepad,  GamepadKeys.Button.RIGHT_BUMPER)
                .whenPressed(claw.setFClaw(Claw.ClawPos.OPEN_POS)));

        Trigger INTAKE = (new GamepadTrigger(driverGamepad, GamepadKeys.Trigger.RIGHT_TRIGGER)
                .whenPressed(new InstantCommand(intake::setDown))
                .whileHeld(intake.setSetPointCommand(PowerIntake.IntakePower.INTAKE)))
                .whenReleased(intake.setSetPointCommand(PowerIntake.IntakePower.STOP))
                .whenReleased(new InstantCommand(intake::setUp));
        Trigger OUTTAKE = (new GamepadTrigger(driverGamepad, GamepadKeys.Trigger.LEFT_TRIGGER)
                .whenPressed(new InstantCommand(intake::setDown))
                .whileHeld(intake.setSetPointCommand(PowerIntake.IntakePower.OUTTAKE)))
                .whenReleased(intake.setSetPointCommand(PowerIntake.IntakePower.STOP))
                .whenReleased(new InstantCommand(intake::setUp));

        claw.setDefaultCommand(new ColorSensorCommand(claw, sensorColor));


        //y - up/dowm
        //x- right left
    }

    @Override
    public void matchStart() {

    }

    /*@Override
    public void matchLoop() {}
    @Override
    public void disabledPeriodic() { }
    @Override
    public void robotPeriodic(){
    }*/
}
