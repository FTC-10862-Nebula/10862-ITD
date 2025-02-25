package org.firstinspires.ftc.teamcode.subsystems.intake;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
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
        return (colorSensor.blue() > 130 &&
            colorSensor.red() > 470 &&
            colorSensor.green() > 555);
    }
    
    public boolean grabbedSample(Intake.Sample sample) {
        if(Intake.Sample.BLUE==sample){
            return (colorSensor.blue() > 250 &&
                colorSensor.green() > 120)&&
                colorSensor.red()>65;
            
        } else if(Intake.Sample.RED==sample){
            return (colorSensor.red() > 315 &&
                colorSensor.green() > 154 &&
                colorSensor.blue() > 74);
        } else{
            return false;
        }
    }
    public int red(){
        return colorSensor.red();
    }
    public int blue(){
        return colorSensor.blue();
    }
    public int green(){
        return colorSensor.green();
    }
    
}
