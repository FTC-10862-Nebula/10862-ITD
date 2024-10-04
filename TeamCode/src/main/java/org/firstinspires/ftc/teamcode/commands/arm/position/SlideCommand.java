package org.firstinspires.ftc.teamcode.commands.arm.position;

import com.arcrobotics.ftclib.command.SequentialCommandGroup;

import org.firstinspires.ftc.teamcode.subsystems.Claw;
import org.firstinspires.ftc.teamcode.subsystems.Arm;
import org.firstinspires.ftc.teamcode.subsystems.intake.VerticalSlide;

public class SlideCommand extends SequentialCommandGroup {
    public SlideCommand(VerticalSlide verticalSlide, Arm arm, Claw claw, VerticalSlide.SlideEnum pos) {
        addCommands(
            claw.setBothClaw(Claw.ClawPos.CLOSE_POS),
//            new ParallelCommandGroup(
            verticalSlide.setSetPoint(pos),
            arm.armSetPositionCommand(Arm.ArmPos.OUTTAKE)
//            )
        );
    }
}
