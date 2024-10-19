package org.firstinspires.ftc.teamcode.util.teleop;

import com.arcrobotics.ftclib.command.button.Button;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;

import org.firstinspires.ftc.teamcode.subsystems.Outtake.Claw;

public class GamepadTrigger extends Button {
    private final GamepadEx gamepad;
    private GamepadKeys.Trigger trigger;

    public GamepadTrigger(GamepadEx gamepad, GamepadKeys.Trigger trigger) {
        this.gamepad = gamepad;
        this.trigger = trigger;
    }

    @Override
    public boolean get() {
        return gamepad.getTrigger(trigger) > 0.5;
    }

    public Button whenPressed(Claw Claw) {
        return null;
    }
}