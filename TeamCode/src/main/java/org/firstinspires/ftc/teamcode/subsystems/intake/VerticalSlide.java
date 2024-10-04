package org.firstinspires.ftc.teamcode.subsystems.intake;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.command.Command;
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
    protected NebulaMotor slideR, slideL;
    
    protected PIDFController slideController;
    protected double output = 0, multiplier =1;

    public enum SlideEnum {
        TRANSFER(-5),

        AUTO_LOW(800),
        AUTO_CLOSE(850),
        LOW(1200),
        MID(1900),
        HIGH(2300),

        MANUAL(0.5);
        public final double slidePos;
        SlideEnum(double slidePos) {
            this.slidePos = slidePos;
        }
    }


    protected static SlideEnum slidePos;

    public VerticalSlide(Telemetry tl, HardwareMap hw, boolean isEnabled) {
        slideR = new NebulaMotor(hw,
            NebulaConstants.Slide.slideRName,
            NebulaConstants.Slide.slideType,
            NebulaConstants.Slide.slideRDirection,
            NebulaConstants.Slide.slideIdleMode,
            isEnabled);
        slideL = new NebulaMotor(hw,
            NebulaConstants.Slide.slideLName,
            NebulaConstants.Slide.slideType,
            NebulaConstants.Slide.slideLDirection,
            NebulaConstants.Slide.slideIdleMode,
            isEnabled);
        slideL.getEncoder().setDirection(Motor.Direction.FORWARD);
        slideR.getEncoder().setDirection(Motor.Direction.FORWARD);
//        motorGroup = new NebulaMotorGroup(slideR, slideL);
        slideR.setDistancePerPulse(NebulaConstants.Slide.slideDistancePerPulse);

        slideController = new PIDFController(
                NebulaConstants.Slide.slidePID.p,
            NebulaConstants.Slide.slidePID.i,
            NebulaConstants.Slide.slidePID.d,
            NebulaConstants.Slide.slidePID.f,
            getEncoderDistance(),
            getEncoderDistance());
        slideController.setTolerance(NebulaConstants.Slide.slideTolerance);
        resetEncoder();
        setSetPoint(0);

        this.telemetry = tl;
        slidePos = SlideEnum.TRANSFER;
        
    }

    @Override
    public void periodic() {
        slideController.setF(NebulaConstants.Slide.slidePID.f *
            Math.cos(Math.toRadians(slideController.getSetPoint())));
        output = slideController.calculate(getEncoderDistance());
        setPower(output* multiplier);
    
        telemetry.addData("Slide SetPoint:" + getSetPoint() + "; ", slidePos.name());
        telemetry.addData("Slide Motor Output:", output* multiplier);
        telemetry.addData("EncoderR: " + slideR.getPosition() + "EncoderL:", slideL.getPosition());
    }

    public double getEncoderDistance() {
        return slideL.getPosition();
//        return slideR.getPosition();
    }
    public void setPower(double power) {
        slideR.setPower(power);
        slideL.setPower(power);
    }
    public void resetEncoder() {
        slideR.resetEncoder();
        slideL.resetEncoder();
    }
    public void setSetPoint(double setPoint) {
        if (getEncoderDistance()>setPoint){
            multiplier =0.8;
            slideController.setP(NebulaConstants.Slide.slidePID.p*0.6);
        } else {
            multiplier = 1;
            slideController.setP(NebulaConstants.Slide.slidePID.p * 1);
        }
        slideController.setSetPoint(setPoint);
    }
    public double getSetPoint() {
        return slideController.getSetPoint();
    }
}