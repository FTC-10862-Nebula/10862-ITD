package org.firstinspires.ftc.teamcode.commands.arm.slide;

import com.arcrobotics.ftclib.command.CommandBase;
import org.firstinspires.ftc.teamcode.subsystems.slide.Slide;
import java.util.function.Supplier;

public class SlideMoveManual extends CommandBase {
    private final Slide slide;
    private final Supplier<Double> doubleSupplier;
    public SlideMoveManual(Slide slide, Supplier<Double> doubleSupplier) {
        this.slide = slide;
        this.doubleSupplier = doubleSupplier;
        addRequirements(slide);
    }
    @Override
    public void execute() {
        double position = doubleSupplier.get();
        if (Math.abs(position) > 0.05) {
            slide.setSetPoint(slide.getSetPoint() + position * -20);
        }
    }
}
