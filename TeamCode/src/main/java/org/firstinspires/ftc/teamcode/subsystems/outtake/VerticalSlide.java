package org.firstinspires.ftc.teamcode.subsystems.outtake;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.controller.PIDFController;
import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.util.NebulaConstants;
import org.firstinspires.ftc.teamcode.util.nebulaHardware.NebulaMotor;

@Config
public class VerticalSlide extends SubsystemBase {
    protected Telemetry telemetry;
    protected NebulaMotor vRSlide,vLSlide;

    protected PIDFController slideController;
    protected double output = 0, multiplier =1.5;

    public VerticalSlide(Telemetry tl, HardwareMap hw, boolean isEnabled) {
        vRSlide = new NebulaMotor(hw,
            "vRSlide",
            NebulaMotor.MotorType.RPM_435, NebulaMotor.Direction.Reverse,
            NebulaMotor.IdleMode.Coast, isEnabled);
        vLSlide = new NebulaMotor(hw,
                "vLSlide",
                NebulaMotor.MotorType.RPM_435, NebulaMotor.Direction.Forward,
                NebulaMotor.IdleMode.Coast, isEnabled);


        slideController = new PIDFController(
            0.005,0,0,0,
            getEncoderDistance(),
            getEncoderDistance());
        slideController.setTolerance(10);
        resetEncoder();
        setSetPoint(0);

        this.telemetry = tl;
    }

    @Override
    public void periodic() {
        telemetry.addData("Slide SetPoint:", getSetPoint());
        telemetry.addData("Encoder: ", getEncoderDistance());
        telemetry.addData("Slide Motor Output:", output* multiplier);
        output = slideController.calculate(getEncoderDistance());
        setPower(output* multiplier);
    }

    public double getEncoderDistance() {
        return vRSlide.getPosition();
//        return vLSlide.getPosition();
    }
    public void setPower(double power) {
        vRSlide.setPower(power);
        vLSlide.resetEncoder();
    }
    public void resetEncoder() {
        vRSlide.resetEncoder();
        vLSlide.resetEncoder();
    }
    public void setSetPoint(double setPoint) {
//        if (getEncoderDistance()>setPoint){
//            multiplier =0.8;
//            slideController.setP(slideController.getP()*0.6);
//        } else {
//            multiplier = 1;
//            slideController.setP(slideController.getP()*1);
//        }
        slideController.setSetPoint(setPoint);
    }
    public double getSetPoint() {
        return slideController.getSetPoint();

    }

}