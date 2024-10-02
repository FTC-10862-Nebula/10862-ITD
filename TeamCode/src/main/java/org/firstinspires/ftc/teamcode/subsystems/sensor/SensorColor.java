package org.firstinspires.ftc.teamcode.subsystems.sensor;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class SensorColor extends SubsystemBase {

    private final ColorSensor colorSensorF, colorSensorB;
    private final Telemetry telemetry;

    public SensorColor(Telemetry tl, HardwareMap hardwareMap) {
        this.colorSensorF = hardwareMap.get(ColorSensor.class, "colorSF");
        this.colorSensorB = hardwareMap.get(ColorSensor.class, "colorSB");
        this.telemetry = tl;
    }

    public void periodic() {
        telemetry.addData("grabBackPixel: ", grabbedBackPixel());
        telemetry.addData("grabFrontPixel: ", grabbedFrontPixel());
    }

    public boolean grabbedWhitePixel(ColorSensor sensor) {
        return (sensor.blue() > 2000 &&sensor.red() > 2000 &&sensor.green() > 2000);
    }
    public boolean grabbedPurpleCone(ColorSensor sensor) {
        return (sensor.red() > 1000 &&sensor.blue() > 1500 &&sensor.green() > 1000);
    }
    public boolean grabbedGreenPixel(ColorSensor sensor) {
        return (sensor.green() > 1000);
    }
    public boolean grabbedYellowPixel(ColorSensor sensor) {
        return (sensor.blue() > 250 &&sensor.red() > 1000 &&sensor.green()> 1000);
    }
    public boolean grabbedBackPixel() {
        return grabbedYellowPixel(colorSensorB)||grabbedGreenPixel(colorSensorB)||
            grabbedPurpleCone(colorSensorB)||grabbedWhitePixel(colorSensorB);
    }
    public boolean grabbedFrontPixel() {
        return grabbedYellowPixel(colorSensorF)||grabbedGreenPixel(colorSensorF)||
            grabbedPurpleCone(colorSensorF)||grabbedWhitePixel(colorSensorF);
    }

}