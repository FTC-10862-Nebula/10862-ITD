package org.firstinspires.ftc.teamcode.subsystems.outtake;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;
import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;

import com.arcrobotics.ftclib.command.Command;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.controller.PIDFController;
import com.arcrobotics.ftclib.hardware.motors.MotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.util.nebulaHardware.NebulaMotor;

public class Slide extends SubsystemBase {
    protected NebulaMotor left, right;
    protected PIDFController slideController = new PIDFController(0.004,0,0,0);
    private final Telemetry telemetry;

    public Slide (Telemetry telemetry,HardwareMap hw, boolean isEnabled){
        this.telemetry = telemetry;
        
        right = new NebulaMotor(hw,
            "vRSlide",
            NebulaMotor.MotorType.RPM_312, NebulaMotor.Direction.Forward,
            NebulaMotor.IdleMode.Coast, isEnabled);
        left = new NebulaMotor(hw,
            "vLSlide",
            NebulaMotor.MotorType.RPM_312, NebulaMotor.Direction.Reverse,
            NebulaMotor.IdleMode.Coast, isEnabled);
        
////        this.hardwareMap = hardwareMap;
//        left = new MotorEx(hardwareMap, "vLSlide");
//        right = new MotorEx(hardwareMap, "vRSlide");
//        left.setInverted(true);
//        right.setInverted(false);
    }

    @Override
    public void periodic(){
        double output = slideController.calculate(getPosition());
        telemetry.addData("Slide SetPoint", slideController.getSetPoint());
        telemetry.addData("Slide Encoder", getPosition());
        telemetry.addData("Slide output", output);
        telemetry.addData("Slide Power: ", right.getCorrectedVelocity());

        right.setPower(output);
        left.setPower(output);
    }

    public double getPosition(){
        return right.getPosition();
//        return left.getPosition();
    }

    public void setSetPoint(double num){
        slideController.setSetPoint(num);
    }
    public void resetEncoder() {
        right.resetEncoder();
        left.resetEncoder();
    }

    public double getSetPoint(){
        return slideController.getSetPoint();
    }
}
