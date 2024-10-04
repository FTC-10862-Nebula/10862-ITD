package org.firstinspires.ftc.teamcode.commands.arm.position;

import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;

import org.firstinspires.ftc.teamcode.subsystems.Claw;
import org.firstinspires.ftc.teamcode.subsystems.Arm;
import org.firstinspires.ftc.teamcode.subsystems.intake.HorizontalSlide;

public class ResetCommand extends SequentialCommandGroup {
    public ResetCommand(HorizontalSlide horizontalSlide, Arm arm, Claw claw) {
        addCommands(
            claw.setBothClaw(Claw.ClawPos.CLOSE_POS),
//            new WaitCommand(200),
            arm.armSetPositionCommand(Arm.ArmPos.TRANSFER),
//            new ParallelCommandGroup(
            new WaitCommand(600),
            horizontalSlide.setSetPoint(HorizontalSlide.SlideEnum.TRANSFER),
            new WaitCommand(800),
            claw.setBothClaw(Claw.ClawPos.OPEN_POS)

//            )
//            new WaitCommand(2500),
//            claw.setClawPos(Claw.ClawPos.OPEN_POS)
        );
    }
}
