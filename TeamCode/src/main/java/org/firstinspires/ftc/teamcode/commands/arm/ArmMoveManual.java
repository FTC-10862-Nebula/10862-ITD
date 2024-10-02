package org.firstinspires.ftc.teamcode.commands.arm;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.subsystems.arm.Arm;
import org.firstinspires.ftc.teamcode.subsystems.slide.Slide;

import java.util.function.Supplier;

public class ArmMoveManual extends CommandBase {
    private final Arm arm;
    private final Supplier<Double> doubleSupplier;
    public ArmMoveManual(Arm arm, Supplier<Double> doubleSupplier) {
        this.arm = arm;
        this.doubleSupplier = doubleSupplier;
        addRequirements(arm);
    }
    @Override
    public void execute() {
        double position = doubleSupplier.get();
        if (Math.abs(position) > 0.05) {
            arm.armSetPositionCommand(arm.getRPosition()+0.01, arm.getLPosition()+0.01);
        }
    }
}
