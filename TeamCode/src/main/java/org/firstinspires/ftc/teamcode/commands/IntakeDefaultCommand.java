package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.ConditionalCommand;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.ParallelCommandGroup;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;

import org.firstinspires.ftc.teamcode.subsystems.intake.Intake;
import org.firstinspires.ftc.teamcode.subsystems.outtake.Outtake;

public class IntakeDefaultCommand extends SequentialCommandGroup {
    Intake.Sample noLookFor;
    public IntakeDefaultCommand(Intake intake, Outtake outtake, Intake.Sample lookFor) {
        if(lookFor== Intake.Sample.BLUE){
            noLookFor = Intake.Sample.RED;
        } else {
            noLookFor = Intake.Sample.BLUE;
        }
        addRequirements(intake.powerIntake);
        addCommands(
            new ConditionalCommand(
                new SequentialCommandGroup(
                    new ParallelCommandGroup(
                        intake.setPosition(Intake.Value.HOLD),
                        outtake.setPosition(Outtake.Value.READY_TO_INTAKE_SAMPLE)
                    ),
                    new WaitCommand(500),
                    outtake.setPosition(Outtake.Value.INTAKE_SAMPLE)
                ),
                new ConditionalCommand(
                    intake.setPosition(Intake.Value.POOP), //Bot Pooper
                    new InstantCommand(), //Nothing
                    ()-> (noLookFor == intake.powerIntake.getSampleColors())
                ),
                ()-> (lookFor == intake.powerIntake.getSampleColors()) ||
                    (lookFor == Intake.Sample.YELLOW)
            )
        );
    }}
