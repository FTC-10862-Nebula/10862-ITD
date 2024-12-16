package org.firstinspires.ftc.teamcode.opmode.teleop;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.subsystems.intake.HorizontalSlide;
import org.firstinspires.ftc.teamcode.subsystems.intake.Intake;
import org.firstinspires.ftc.teamcode.subsystems.intake.IntakeServo;
import org.firstinspires.ftc.teamcode.subsystems.intake.PowerIntake;
import org.firstinspires.ftc.teamcode.util.teleop.MatchOpMode;
@Disabled
@Config
@TeleOp
public class TestColorSensor extends MatchOpMode {
    //TODO: Add a on/off switch for drivetrain
    private GamepadEx driverGamepad, operatorGamepad;
    private Intake intake;
    public TestColorSensor() {}

    @Override
    public void robotInit() {
        driverGamepad = new GamepadEx(gamepad1);
        operatorGamepad = new GamepadEx(gamepad2);
        intake = new Intake(
                new HorizontalSlide(telemetry, hardwareMap,false),
                new IntakeServo(telemetry, hardwareMap,false),
                new PowerIntake(telemetry, hardwareMap,false),
                telemetry
        );

    }

    @Override
    public void robotPeriodic(){
        intake.periodic();
    }


    @Override
    public void configureButtons() {
    }

    @Override
    public void matchStart() {}
}
