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
    protected NebulaMotor vertSlide;

    protected PIDFController slideController;
    protected double output = 0, multiplier =1;

    public VerticalSlide(Telemetry tl, HardwareMap hw, boolean isEnabled) {
        vertSlide = new NebulaMotor(hw,
            "vSlide",
            NebulaMotor.MotorType.RPM_435, NebulaMotor.Direction.Forward,
            NebulaMotor.IdleMode.Coast, isEnabled);
        vertSlide.getEncoder().setDirection(Motor.Direction.FORWARD);
        vertSlide.setDistancePerPulse(1);

        slideController = new PIDFController(
            0.005,0,0,0,
            getEncoderDistance(),
            getEncoderDistance());
        slideController.setTolerance(NebulaConstants.Slide.slideTolerance);
        resetEncoder();
        setSetPoint(0);

        this.telemetry = tl;
    }

    @Override
    public void periodic() {
        slideController.setF(NebulaConstants.Slide.slidePID.f *
            Math.cos(Math.toRadians(slideController.getSetPoint())));
        output = slideController.calculate(getEncoderDistance());
        setPower(output* multiplier);
    
        telemetry.addData("Slide SetPoint:", getSetPoint());
//        telemetry.addData("Slide Position Word:", slidePos.name());
        telemetry.addData("Slide Motor Output:", output* multiplier);
        telemetry.addData("SlideR Encoder: ", vertSlide.getPosition());
        
    }

    public double getEncoderDistance() {
        return vertSlide.getPosition();
    }
    public void setPower(double power) {
        vertSlide.setPower(power);
    }
    public void resetEncoder() {
        vertSlide.resetEncoder();
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
//    public Command setSetPointCommand(SlideEnum pos) {
//        slidePos = pos;
//        return  new InstantCommand(()->setSetPoint(pos.slidePos));
//    }
    public double getSetPoint() {
        return slideController.getSetPoint();
    }
}