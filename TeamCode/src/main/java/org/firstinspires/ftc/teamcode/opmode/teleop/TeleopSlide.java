package org.firstinspires.ftc.teamcode.opmode.teleop;


import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.command.button.Button;
import com.arcrobotics.ftclib.command.button.GamepadButton;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

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
        slide = new Slide(hardwareMap,telemetry, true);
        
    }


    @Override
    public void configureButtons() {
//        Button HoutPower = (new GamepadButton(operatorGamepad,GamepadKeys.Button.A)
//                .whenPressed(slide.setPower(-1)))
//                .whenReleased(slide.setPower(0));
//        Button HinPower = (new GamepadButton(operatorGamepad,GamepadKeys.Button.B)
//                .whenPressed(slide.setPower(1))
//                .whenReleased(slide.setPower(0)));
        
        
        Button HoutPower = (new GamepadButton(operatorGamepad,GamepadKeys.Button.A)
            .whenPressed(slide.setSetPoint(1000)));
        Button HinPower = (new GamepadButton(operatorGamepad,GamepadKeys.Button.B)
            .whenPressed(slide.setSetPoint(300)));
    }


    @Override
    public void matchStart() {}

}
