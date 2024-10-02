package org.firstinspires.ftc.teamcode.subsystems.sensor;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.AnalogInput;
import com.qualcomm.robotcore.hardware.AnalogInputController;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class Potentiometer extends SubsystemBase {
    //FRC: return (m_analogInput.getAverageVoltage() / RobotController.getVoltage5V()) * m_fullRange
    //        + m_offset;
    private final Telemetry telemetry;
    private final AnalogInput potentiometer;
    private AnalogInputController controller;
    double theta1,theta2;
    public Potentiometer(HardwareMap hw, Telemetry tl, int channel) {
        this.telemetry = tl;
//        potentiometer = hw.get(AnalogInput.class, "potentiometer");
        potentiometer = new AnalogInput(controller, channel);
    }

    public void periodic() {
        telemetry.addData("Current Voltage", potentiometer.getVoltage());
        telemetry.addData("Theta 1", theta1);
        telemetry.addData("Theta 2", theta2);
        telemetry.update();
    }


    //Check Documentation if confused
    public double getPotentiometerAngle(){
        double angle = potentiometer.getVoltage()*81.8;
        return Range.scale(potentiometer.getVoltage(), 0, potentiometer.getMaxVoltage(), 0, 270);
    }
    public double getPotentiometerAngle2(){
        return solveForTheta(potentiometer.getVoltage());
//        double angle = potentiometer.getVoltage()*81.8;
//        return Range.scale(potentiometer.getVoltage(), 0, potentiometer.getMaxVoltage(), 0, 270);

        //GetAverageVoltage() * potentiometerRange / (upperVoltageLimit - lowerVoltageLimit)) - voltageOffset;//From Botbusters 4635

    }

    public String getDeviceType() {return "Color Sensor";}

    public double solveForTheta(double voltage) {//Created by ChatGPT TODO: Test the formula
        //Formula from REV: (445.5((theta)-270))/((theta)^2-270(theta)-36450) = voltage;
        double a = voltage;
        double b = -445.5;
        double c = 120285 - 36450*voltage;
        double discriminant = b*b - 4*a*c;
        theta1 = (445.5 + Math.sqrt(discriminant)) / (2*a);
        theta2 = (445.5 - Math.sqrt(discriminant)) / (2*a);
        // Choose the appropriate solution based on the context of the problem
        if(isPositive(theta1)){return theta1;}
        else if (isPositive(theta2)){return theta2;}
        else return 0;
    }

    public boolean isPositive(double num) {
        return num >= 0;
    }

}