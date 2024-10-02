package org.firstinspires.ftc.teamcode.opmode.teleop;


import com.arcrobotics.ftclib.command.button.Button;
import com.arcrobotics.ftclib.command.button.GamepadButton;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.commands.ClimberMoveManual;
import org.firstinspires.ftc.teamcode.subsystems.climber.Climber;
import org.firstinspires.ftc.teamcode.util.teleop.MatchOpMode;
@Disabled
@TeleOp
public class ClimbOnly extends MatchOpMode {
    private GamepadEx operatorGamepad;
    private Climber climb;

    @Override
    public void robotInit() {
        operatorGamepad = new GamepadEx(gamepad2);
        climb = new Climber(telemetry, hardwareMap, true);
    }


    @Override
    public void configureButtons() {
        Button moveUp  = (new GamepadButton(operatorGamepad, GamepadKeys.Button.A))
            .whenPressed(climb.setSetPointCommand(Climber.ClimbEnum.CLIMB));
        Button moveDown  = (new GamepadButton(operatorGamepad, GamepadKeys.Button.B))
            .whenPressed(climb.setSetPointCommand(Climber.ClimbEnum.REST));
        climb.setDefaultCommand(new ClimberMoveManual(climb, operatorGamepad::getRightX));
    }

    @Override
    public void matchLoop() {
        telemetry.addData("asking", climb.getSetPoint());
    }
    @Override
    public void disabledPeriodic() { }
    @Override
    public void matchStart() { }
    @Override
    public void robotPeriodic(){
    }
}
