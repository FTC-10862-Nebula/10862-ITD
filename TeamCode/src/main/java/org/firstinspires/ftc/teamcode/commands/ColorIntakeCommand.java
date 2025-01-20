package org.firstinspires.ftc.teamcode.commands;
import org.firstinspires.ftc.teamcode.subsystems.intake.Intake;
import org.firstinspires.ftc.teamcode.subsystems.outtake.Claw;
import org.firstinspires.ftc.teamcode.subsystems.intake.SensorColor;
import org.firstinspires.ftc.teamcode.subsystems.outtake.Outtake;
import com.arcrobotics.ftclib.command.*;

//
//public class ColorIntakeCommand extends SequentialCommandGroup {
//    SensorColor sensorColor;
//    Intake.Sample sample;
//
//    public ColorIntakeCommand(Intake intake, Outtake outtake,
//                              SensorColor sensorColor, Intake.Sample sample) {
//        this.sensorColor = sensorColor;
//        this.sample = sample;
//        addRequirements(outtake.claw, sensorColor);
//
//        addCommands(
//            new ConditionalCommand(
//                new SequentialCommandGroup(
//                    outtake.setClawSetPoint(Claw.Value.OPEN),
//                    intake.setPosition(Intake.Value.START),
//                    new WaitCommand(500),
//                    outtake.setPosition(Outtake.Value.START),
//                    new WaitCommand(500),
//                    outtake.setClawSetPoint(Claw.Value.CLOSE),
//                    new WaitCommand(300),
//                    intake.setPosition(Intake.Value.SAMPLE),
//                    createRepeatCommand(intake, outtake) // Replaced RepeatCommand
//                ),
//                new InstantCommand(),
//                () -> getColors(sample)
//            )
//        );
//    }
//
//    private Command createRepeatCommand(Intake intake, Outtake outtake) {
//        return new SequentialCommandGroup(
//            new ConditionalCommand(
//                new SequentialCommandGroup(
//                    outtake.setPosition(Outtake.Value.OUTTAKE_HOLD),
//                    new WaitCommand(1000),
//                    new ConditionalCommand(
//                        new InstantCommand(() -> outtake.setPosition(Outtake.Value.OUTTAKE_SAMPLE)),
//                        new SequentialCommandGroup(
//                            new InstantCommand(() -> outtake.setClawSetPoint(Claw.Value.OPEN)),
//                            new InstantCommand(() -> outtake.setPosition(Outtake.Value.SAMPLE_WAIT)),
//                            new ParallelCommandGroup(
//                                new InstantCommand(() -> intake.setPosition(Intake.Value.START)),
//                                new InstantCommand(() -> outtake.setPosition(Outtake.Value.START))
//                            ),
//                            new WaitCommand(300),
//                            new InstantCommand(() -> outtake.setClawSetPoint(Claw.Value.CLOSE)),
//                            new WaitCommand(300),
//                            new InstantCommand(() -> intake.setPosition(Intake.Value.SAMPLE))
//                        ),
//                        this::checkColorAgain
//                    )
//                ),
//                createRepeatCommand(intake, outtake), // Recursively add itself
//                this::checkColorAgain
//            )
//        );
//    }
//
//    public boolean getColors(Intake.Sample sample) {
//        if (sensorColor.grabbedYellowSample()) {
//            return true;
//        } else return sensorColor.grabbedSample(sample);
//    }
//
//    public boolean checkColorAgain() {
//        return sensorColor.grabbedYellowSample() || sensorColor.grabbedSample(sample);
//    }
//}

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