package org.firstinspires.ftc.teamcode.subsystems.intake;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.controller.PIDFController;
import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Func;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.util.NebulaConstants;
import org.firstinspires.ftc.teamcode.util.nebulaHardware.NebulaMotor;

@Config
public class HorizontalSlide extends SubsystemBase implements Telemetry {
    protected Telemetry telemetry;
    protected NebulaMotor hSlide;
    
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
        hSlide.setDistancePerPulse(1);

        slideController = new PIDFController(0,0,0,0, getEncoderDistance(), getEncoderDistance());
        slideController.setTolerance(1);
        resetEncoder();
        setSetPoint(0);

        this.telemetry = tl;
    }

    @Override
    public void periodic() {
        output = slideController.calculate(getEncoderDistance());
        hSlide.setPower(output* multiplier);
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
        if (getEncoderDistance()>setPoint){
            multiplier =0.8;
            slideController.setP(slideController.getP()*0.6);
        } else {
            multiplier = 1;
            slideController.setP(slideController.getP() * 1);
        }
        slideController.setSetPoint(setPoint);
    }
    public double getSetPoint() {
        return slideController.getSetPoint();
    }

    @Override
    public Item addData(String s, String s1, Object... objects) {
        return null;
    }

    @Override
    public Item addData(String s, Object o) {
        return null;
    }

    @Override
    public <T> Item addData(String s, Func<T> func) {
        return null;
    }

    @Override
    public <T> Item addData(String s, String s1, Func<T> func) {
        return null;
    }

    @Override
    public boolean removeItem(Item item) {
        return false;
    }

    @Override
    public void clear() {

    }

    @Override
    public void clearAll() {

    }

    @Override
    public Object addAction(Runnable runnable) {
        return null;
    }

    @Override
    public boolean removeAction(Object o) {
        return false;
    }

    @Override
    public void speak(String s) {

    }

    @Override
    public void speak(String s, String s1, String s2) {

    }

    @Override
    public boolean update() {
        return false;
    }

    @Override
    public Line addLine() {
        return null;
    }

    @Override
    public Line addLine(String s) {
        return null;
    }

    @Override
    public boolean removeLine(Line line) {
        return false;
    }

    @Override
    public boolean isAutoClear() {
        return false;
    }

    @Override
    public void setAutoClear(boolean b) {

    }

    @Override
    public int getMsTransmissionInterval() {
        return 0;
    }

    @Override
    public void setMsTransmissionInterval(int i) {

    }

    @Override
    public String getItemSeparator() {
        return null;
    }

    @Override
    public void setItemSeparator(String s) {

    }

    @Override
    public String getCaptionValueSeparator() {
        return null;
    }

    @Override
    public void setCaptionValueSeparator(String s) {

    }

    @Override
    public void setDisplayFormat(DisplayFormat displayFormat) {

    }

    @Override
    public Log log() {
        return null;
    }
}