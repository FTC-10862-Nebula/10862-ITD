package org.firstinspires.ftc.teamcode.subsystems.intake;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;


    public class SensorColor extends SubsystemBase {

        private final ColorSensor colorSensor;
        private final Telemetry telemetry;

        public SensorColor(Telemetry tl, HardwareMap hardwareMap) {
            this.colorSensor = hardwareMap.get(ColorSensor.class, "colorS");
            this.telemetry = tl;
            this.colorSensor.enableLed(true);
        }

        public void periodic() {
           telemetry.addData("grabSample:", grabbedYellowSample());

            telemetry.update();
        }

        public boolean grabbedYellowSample() {
            telemetry.addLine("Got yellow Sample");
            return (colorSensor.blue() > 250 && colorSensor.red() > 1000 && colorSensor.green() > 1000);
        }

        public boolean grabbedSample(ColorSensor colorSensor) {
            return grabbedSample(this.colorSensor);
        }
    }
