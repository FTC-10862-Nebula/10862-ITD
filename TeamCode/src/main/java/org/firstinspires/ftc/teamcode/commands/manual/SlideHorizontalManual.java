//package org.firstinspires.ftc.teamcode.commands.manual;
//
//import com.arcrobotics.ftclib.command.CommandBase;
//import org.firstinspires.ftc.teamcode.subsystems.intake.HorizontalSlide;
//import java.util.function.Supplier;
//
//public class SlideHorizontalManual extends CommandBase {
//
//    private final HorizontalSlide horizontalSlide;
//    private final Supplier<Double> doubleSupplier;
//    public SlideHorizontalManual(HorizontalSlide horizontalSlide, Supplier<Double> doubleSupplier) {
//        this.horizontalSlide = horizontalSlide;
//        this.doubleSupplier = doubleSupplier;
//        addRequirements(horizontalSlide);
//    }
//    @Override
//    public void execute() {
//        double position = doubleSupplier.get();
//        if (Math.abs(position) > 0.2) {
//            horizontalSlide.setPower();horizontalSlide.() + position * -120);
//        }
//    }
//}
