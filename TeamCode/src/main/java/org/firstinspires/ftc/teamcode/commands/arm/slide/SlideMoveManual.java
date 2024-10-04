package org.firstinspires.ftc.teamcode.commands.arm.slide;

import com.arcrobotics.ftclib.command.CommandBase;
import org.firstinspires.ftc.teamcode.subsystems.intake.VerticalSlide;
import java.util.function.Supplier;

public class SlideMoveManual extends CommandBase {
    private final VerticalSlide verticalSlide;
    private final Supplier<Double> doubleSupplier;
    public SlideMoveManual(VerticalSlide verticalSlide, Supplier<Double> doubleSupplier) {
        this.verticalSlide = verticalSlide;
        this.doubleSupplier = doubleSupplier;
        addRequirements(verticalSlide);
    }
    @Override
    public void execute() {
        double position = doubleSupplier.get();
        if (Math.abs(position) > 0.05) {
            verticalSlide.setSetPoint(verticalSlide.getSetPoint() + position * -20);
        }
    }
}
