package org.firstinspires.ftc.teamcode.commands;
import org.firstinspires.ftc.teamcode.subsystems.intake.Intake;
import org.firstinspires.ftc.teamcode.subsystems.outtake.Claw;
import org.firstinspires.ftc.teamcode.subsystems.intake.SensorColor;
import org.firstinspires.ftc.teamcode.subsystems.outtake.Outtake;
import com.arcrobotics.ftclib.command.*;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

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
                    new ParallelCommandGroup(
                        new InstantCommand(()-> outtake.verticalSlide.setSetPoint(0)),
                        outtake.setClawSetPoint(Claw.Value.OPEN),
                        intake.setPosition(Intake.Value.START)),
                    new WaitCommand(500),
                    outtake.setPosition(Outtake.Value.START),
                    new WaitCommand(250),
                    outtake.setClawSetPoint(Claw.Value.CLOSE),
                    new WaitCommand(250),
                    outtake.setPosition(Outtake.Value.OUTTAKE_SAMPLE),
                    intake.setPosition(Intake.Value.SAMPLE),
                    new WaitCommand(1000)
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