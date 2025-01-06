package org.firstinspires.ftc.teamcode.commands;
import org.firstinspires.ftc.teamcode.subsystems.intake.Intake;
import org.firstinspires.ftc.teamcode.subsystems.outtake.Claw;
import org.firstinspires.ftc.teamcode.subsystems.intake.SensorColor;
import org.firstinspires.ftc.teamcode.subsystems.outtake.Outtake;
import com.arcrobotics.ftclib.command.*;


public class ColorIntakeCommand extends SequentialCommandGroup {
    SensorColor sensorColor;
    Intake.Sample sample;
    public ColorIntakeCommand(Intake intake, Outtake outtake,
                              SensorColor sensorColor, Intake.Sample sample) {
        this.sensorColor = sensorColor;
        this.sample = sample;
        addRequirements(outtake.claw, sensorColor);
        addCommands(
            new ConditionalCommand(
                new SequentialCommandGroup(
                    new WaitCommand(1000),
                    outtake.setClawSetPoint(Claw.Value.OPEN),
                    intake.setPosition(Intake.Value.START),
                    new WaitCommand(500),
                    outtake.setPosition(Outtake.Value.START),
                    new WaitCommand(1000),
                    outtake.setClawSetPoint(Claw.Value.CLOSE),
                    new WaitCommand(500),
                    intake.setPosition(Intake.Value.SAMPLE),
                    outtake.setPosition(Outtake.Value.OUTTAKE_SAMPLE),
                    new WaitCommand(2000)
                ),
                new InstantCommand(),
                ()->getColors(sample)
            )
        );
    }

    public boolean getColors(Intake.Sample sample){
        if(sensorColor.grabbedYellowSample()){
            return true;
        } else return sensorColor.grabbedSample(sample);
    }
}
