package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.ConditionalCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import org.firstinspires.ftc.teamcode.subsystems.outtake.Claw;
import org.firstinspires.ftc.teamcode.subsystems.outtake.Outtake;

public class ScoreCommand extends SequentialCommandGroup {
    private final Outtake outtake;
    public ScoreCommand(Outtake outtake) {
        this.outtake = outtake;
        addRequirements();
        addCommands(
                new ConditionalCommand(
                        outtake.setClawSetPoint(Claw.Value.OPEN),
                        new ConditionalCommand(
                                outtake.setPosition(Outtake.Value.SPECIMEN_HIGH_BAR),
                                outtake.setPosition(Outtake.Value.SPECIMEN_LOW_BAR),
                                this::isHighSpecimen
                        ),
                        this::isBucket
                )
        );
    }

    public boolean isBucket(){
        return (outtake.value== Outtake.Value.HIGH_BUCKET)
                ||(outtake.value== Outtake.Value.LOW_BUCKET);
    }
    public boolean isHighSpecimen(){
        return (outtake.value== Outtake.Value.HIGH_RUNG);
    }
}
