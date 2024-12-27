package org.firstinspires.ftc.teamcode.commands;

import org.firstinspires.ftc.teamcode.subsystems.outtake.Claw;
import org.firstinspires.ftc.teamcode.subsystems.intake.SensorColor;
import com.arcrobotics.ftclib.command.*;

public class ColorIntakeCommand extends SequentialCommandGroup {
    public ColorIntakeCommand(Claw claw, SensorColor sensorColor) {
        addRequirements(claw, sensorColor);

        addCommands(
                new ConditionalCommand(
                        new SequentialCommandGroup(
                                new InstantCommand(() -> claw.setClawSetPoint(Claw.Value.CLOSE)),
                                new InstantCommand(sensorColor::grabbedYellowSample)
                        ),
                        new InstantCommand(),
                        sensorColor::grabbedYellowSample
                )
        );
    }
}
