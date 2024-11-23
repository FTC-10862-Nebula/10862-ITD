package org.firstinspires.ftc.teamcode.commands.manual;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.subsystems.outtake.VerticalSlide;

import java.util.function.Supplier;

public class SlideVerticalManual extends CommandBase {
    private final VerticalSlide verticalSlide;
    private final Supplier<Double> doubleSupplier;
    public SlideVerticalManual(VerticalSlide verticalSlide, Supplier<Double> doubleSupplier) {
        this.verticalSlide = verticalSlide;
        this.doubleSupplier = doubleSupplier;
        addRequirements(verticalSlide);
    }
    @Override
    public void execute() {
        double position = doubleSupplier.get();
        if (Math.abs(position) >= 1) {
            verticalSlide.setSetPoint(verticalSlide.getSetPoint() + (position * -20));
        }
    }
}
