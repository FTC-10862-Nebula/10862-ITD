package org.firstinspires.ftc.teamcode.opmode.teleop;


import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.command.button.Button;
import com.arcrobotics.ftclib.command.button.GamepadButton;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.commands.manual.SlideVerticalManual;
import org.firstinspires.ftc.teamcode.subsystems.outtake.Slide;
import org.firstinspires.ftc.teamcode.subsystems.outtake.VerticalSlidePower;
import org.firstinspires.ftc.teamcode.util.teleop.MatchOpMode;

//@Disabled
@Config
@TeleOp
public class TeleopSlide extends MatchOpMode {
    //TODO: Add a on/off switch for drivetrain
    private GamepadEx driverGamepad, operatorGamepad;

//    private VerticalSlidePower slide;
    private Slide slide;
    public TeleopSlide() {}

    @Override
    public void robotInit() {
        driverGamepad = new GamepadEx(gamepad1);
        operatorGamepad = new GamepadEx(gamepad2);

//        slide = new VerticalSlidePower(telemetry, hardwareMap, true);
        slide = new Slide(telemetry,hardwareMap, true);
        
    }


    @Override
    public void configureButtons() {
        slide.setDefaultCommand(new SlideVerticalManual(slide,
                ()-> driverGamepad.getRightX()));
        
//        Button HoutPower = (new GamepadButton(operatorGamepad,GamepadKeys.Button.A)
//            .whenPressed(Slide.setSetPoint(2000)));
//        Button HinPower = (new GamepadButton(operatorGamepad,GamepadKeys.Button.B)
//            .whenPressed(slide.setSetPoint(1500)));
    }


    @Override
    public void matchStart() {}

}
