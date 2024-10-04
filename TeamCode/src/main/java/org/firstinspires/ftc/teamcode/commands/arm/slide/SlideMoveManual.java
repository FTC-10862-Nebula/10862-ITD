package org.firstinspires.ftc.teamcode.commands.arm.slide;

import com.arcrobotics.ftclib.command.CommandBase;
import org.firstinspires.ftc.teamcode.subsystems.intake.HorizontalSlide;
import java.util.function.Supplier;

public class SlideMoveManual extends CommandBase {
    private final HorizontalSlide horizontalSlide;
    private final Supplier<Double> doubleSupplier;
    public SlideMoveManual(HorizontalSlide horizontalSlide, Supplier<Double> doubleSupplier) {
        this.horizontalSlide = horizontalSlide;
        this.doubleSupplier = doubleSupplier;
        addRequirements(horizontalSlide);
    }
    @Override
    public void execute() {
        double position = doubleSupplier.get();
        if (Math.abs(position) > 0.05) {
            horizontalSlide.setSetPoint(horizontalSlide.getSetPoint() + position * -20);
        }
    }
}
