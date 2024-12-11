package org.firstinspires.ftc.teamcode.subsystems.intake;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.controller.PIDFController;
import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Func;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.subsystems.outtake.Claw;
import org.firstinspires.ftc.teamcode.util.NebulaConstants;
import org.firstinspires.ftc.teamcode.util.nebulaHardware.NebulaMotor;

@Config
public class HorizontalSlide extends SubsystemBase {
    protected Telemetry telemetry;
    protected NebulaMotor hSlide;
    public enum Value{
        IN(0),
        OUT(200);
        public final double pos;
        Value(double pos) {
            this.pos = pos;
        }
        Value(HorizontalSlide.Value value) {
            this.pos = value.pos;
        }

    }

    protected PIDFController slideController;
    protected double output = 0, multiplier =1;

    public HorizontalSlide(Telemetry tl, HardwareMap hw, boolean isEnabled) {
        hSlide = new NebulaMotor(hw,
            "hSlide",
            NebulaMotor.MotorType.RPM_435,
            NebulaMotor.Direction.Forward,
            NebulaMotor.IdleMode.Coast,
            isEnabled);
        hSlide.getEncoder().setDirection(Motor.Direction.FORWARD);
      //  hSlide.setDistancePerPulse(1);

        slideController = new PIDFController(0.5,0,0,0, getEncoderDistance(), getEncoderDistance());
        slideController.setTolerance(1);
        resetEncoder();
        setSetPoint(0);
        this.telemetry = tl;
    }

    @Override
    public void periodic() {
        slideController.setF(slideController.getF() *
                Math.cos(Math.toRadians(slideController.getSetPoint())));
        output = slideController.calculate(getEncoderDistance());
        hSlide.setPower(output* multiplier);

        telemetry.addData("HorSlide SetPoint:" ,getSetPoint());
        telemetry.addData("; HorEncoder: ", hSlide.getPosition());
        telemetry.addData("HorSlide Motor Output:", output* multiplier);
        telemetry.addData("HorSlide Motor Velocity:", hSlide.getVelocity());

    }

    public double getEncoderDistance() {
        return hSlide.getPosition();//
    }
    public void setPower(double power) {
        hSlide.setPower(power);
    }
    public void resetEncoder() {
        hSlide.resetEncoder();
    }
    public void setSetPoint(double setPoint) {
//        if (getEncoderDistance()>setPoint){
//            multiplier =0.8;
//            slideController.setP(slideController.getP()*0.6);
//        } else {
//            multiplier = 1;
//            slideController.setP(slideController.getP() * 1);
//        }
        slideController.setSetPoint(setPoint);
    }
    public double getSetPoint() {
        return slideController.getSetPoint();
    }

 }