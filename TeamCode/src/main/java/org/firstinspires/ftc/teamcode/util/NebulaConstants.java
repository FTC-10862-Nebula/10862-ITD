package org.firstinspires.ftc.teamcode.util;

import com.acmerobotics.dashboard.config.Config;

@Config
@Deprecated
public class NebulaConstants {
    //Arm Distance Per Pulse
    //Math.PI/2
    //(Counts per revolution*Gear ratio(5:1=5 / 1:5))/(Turns per revolution * 2π)

    //Slide Distance Per Pulse
    //(COUNTS_PER_PULSE * GEAR_RATIO) / (GEAR_DIAMETER_INCHES * Math.PI);

    public static double squareInput(double value) {
        return value * Math.abs(value);
    }
    public static double cubeInput(double value) {
        return value * Math.abs(value) * Math.abs(value);
    }
}
