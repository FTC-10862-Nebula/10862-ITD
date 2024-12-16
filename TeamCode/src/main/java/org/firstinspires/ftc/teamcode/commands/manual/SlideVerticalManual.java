package org.firstinspires.ftc.teamcode.commands.manual;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.subsystems.outtake.Slide;

import java.util.function.Supplier;

public class SlideVerticalManual extends CommandBase {
    private final Slide verticalSlide;
    private final Supplier<Double> doubleSupplier;
    public SlideVerticalManual(Slide verticalSlide, Supplier<Double> doubleSupplier) {
        this.verticalSlide = verticalSlide;
        this.doubleSupplier = doubleSupplier;
        addRequirements(verticalSlide);
    }
    @Override
    public void execute() {
        double position = doubleSupplier.get();
        if (Math.abs(position) >= .4) {
            verticalSlide.setSetPoint(verticalSlide.getSetPoint() + (position * -20));
        }
    }
}
