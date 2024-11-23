package org.firstinspires.ftc.teamcode.commands.manual;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.subsystems.climber.Climber;

import java.util.function.Supplier;

@Deprecated
public class ClimberManual extends CommandBase {
        private final Climber climber;
    private final Supplier<Double> doubleSupplier;
    public ClimberManual(Climber climber, Supplier<Double> doubleSupplier) {
        this.climber = climber;
        this.doubleSupplier = doubleSupplier;
        addRequirements(climber);
    }
    @Override
    public void execute() {
        double position = doubleSupplier.get();
        if (Math.abs(position) > 0.05) {
            climber.setSetPoint(climber.getSetPoint() + position * -80);
        }
    }
}
