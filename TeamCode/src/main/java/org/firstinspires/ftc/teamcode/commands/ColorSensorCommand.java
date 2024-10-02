package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.Command;
import com.arcrobotics.ftclib.command.ConditionalCommand;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;

import org.firstinspires.ftc.teamcode.subsystems.Claw;
import org.firstinspires.ftc.teamcode.subsystems.sensor.SensorColor;

public class ColorSensorCommand extends SequentialCommandGroup {
    public ColorSensorCommand(Claw claw, SensorColor sensorColor) {
        addRequirements(claw, sensorColor);
        addCommands(
            new ConditionalCommand(
                new SequentialCommandGroup(
                    claw.setBClaw(Claw.ClawPos.CLOSE_POS),
                    new ConditionalCommand(
                        new SequentialCommandGroup(
                            claw.setFClaw(Claw.ClawPos.CLOSE_POS)
                    ),
                        new InstantCommand(),
                        sensorColor::grabbedFrontPixel
                    )
                ),
                new SequentialCommandGroup(),
                sensorColor::grabbedBackPixel)//.withTimeout(1000)
    );
    }
}