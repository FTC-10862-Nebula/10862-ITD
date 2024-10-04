package org.firstinspires.ftc.teamcode.commands.arm.position;

import com.arcrobotics.ftclib.command.SequentialCommandGroup;

import org.firstinspires.ftc.teamcode.subsystems.Claw;
import org.firstinspires.ftc.teamcode.subsystems.Arm;
import org.firstinspires.ftc.teamcode.subsystems.intake.HorizontalSlide;

public class SlideCommand extends SequentialCommandGroup {
    public SlideCommand(HorizontalSlide horizontalSlide, Arm arm, Claw claw, HorizontalSlide.SlideEnum pos) {
        addCommands(
            claw.setBothClaw(Claw.ClawPos.CLOSE_POS),
//            new ParallelCommandGroup(
            horizontalSlide.setSetPoint(pos),
            arm.armSetPositionCommand(Arm.ArmPos.OUTTAKE)
//            )
        );
    }
}
