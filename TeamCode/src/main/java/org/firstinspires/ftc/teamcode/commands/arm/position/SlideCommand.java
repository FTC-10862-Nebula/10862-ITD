package org.firstinspires.ftc.teamcode.commands.arm.position;

import com.arcrobotics.ftclib.command.SequentialCommandGroup;

import org.firstinspires.ftc.teamcode.subsystems.Claw;
import org.firstinspires.ftc.teamcode.subsystems.arm.Arm;
import org.firstinspires.ftc.teamcode.subsystems.slide.Slide;

public class SlideCommand extends SequentialCommandGroup {
    public SlideCommand(Slide slide, Arm arm, Claw claw, Slide.SlideEnum pos) {
        addCommands(
            claw.setBothClaw(Claw.ClawPos.CLOSE_POS),
//            new ParallelCommandGroup(
            slide.setSetPointCommand(pos),
            arm.armSetPositionCommand(Arm.ArmPos.OUTTAKE)
//            )
        );
    }
}
