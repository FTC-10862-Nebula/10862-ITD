package org.firstinspires.ftc.teamcode.util.misc;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.util.teleop.MatchOpMode;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Util {

    public static void logger(Object currentClass, Telemetry tl, Level level, String caption, Object data) {
        Logger.getLogger(currentClass.getClass().getName()).log(level, caption + " " + data);
        tl.addData(caption, data);
        MatchOpMode.telemetryList.put(caption, data);
    }
    public static void logger(Object currentClass, Level level, String caption, Object data) {
        Logger.getLogger(currentClass.getClass().getName()).log(level, caption + " " + data);
        MatchOpMode.telemetryList.put(caption, data);
    }


    /**
     * Returns modulus of error where error is the difference between the reference
     * and a measurement.
     *
     * @param reference Reference input of a controller.
     * @param measurement The current measurement.
     * @param minimumInput The minimum value expected from the input.
     * @param maximumInput The maximum value expected from the input.
     */
    public static double getModulusError(double reference, double measurement, double minimumInput,
                                         double maximumInput) {
        double error = reference - measurement;
        double modulus = maximumInput - minimumInput;

        // Wrap error above maximum input
        int numMax = (int) ((error + maximumInput) / modulus);
        error -= numMax * modulus;

        // Wrap error below minimum input
        int numMin = (int) ((error + minimumInput) / modulus);
        error -= numMin * modulus;

        return error;
    }
}