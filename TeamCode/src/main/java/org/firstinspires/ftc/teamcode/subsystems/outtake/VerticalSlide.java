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
    protected double output = 0;//, multiplier =1;

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
            0.001,0,0,0);
        slideController.setTolerance(10);
        resetEncoder();
        setSetPoint(0);

        this.telemetry = tl;
    }

    @Override
    public void periodic() {
        output = slideController.calculate(getEncoderDistance());
        telemetry.addData("Slide SetPoint:", getSetPoint());
        telemetry.addData("Encoder: ", getEncoderDistance());
        telemetry.addData("slide power: ", vRSlide.getCorrectedVelocity());
        telemetry.addData("Slide Motor OutputNorm:", vRSlide.getCorrectedVelocity());
        telemetry.addData("Slide Motor Output:", output);
        setPower(output);
    }

    public double getEncoderDistance() {
        return vRSlide.getEncoder().getDistance();
//        return vLSlide.getPosition();
    }
    public void setPower(double power) {
        vRSlide.setPower(power);
        vLSlide.setPower(power);
    }
    public void resetEncoder() {
        vRSlide.resetEncoder();
        vLSlide.resetEncoder();
    }
    public void setSetPoint(double setPoint) {
        slideController.setSetPoint(setPoint);
    }
    public double getSetPoint() {
        return slideController.getSetPoint();

    }

}