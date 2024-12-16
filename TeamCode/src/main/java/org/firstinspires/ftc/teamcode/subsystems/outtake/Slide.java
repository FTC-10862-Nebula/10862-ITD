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

//    private final MotorEx right, left;
    protected PIDFController slideController = new PIDFController(0,0,0,0);
//    private final HardwareMap hardwareMap;
    private final Telemetry telemetry;

    public Slide (HardwareMap hw, Telemetry telemetry, boolean isEnabled){
        this.telemetry = telemetry;
        
        right = new NebulaMotor(hw,
            "vRSlide",
            NebulaMotor.MotorType.RPM_435, NebulaMotor.Direction.Reverse,
            NebulaMotor.IdleMode.Coast, isEnabled);
        left = new NebulaMotor(hw,
            "vLSlide",
            NebulaMotor.MotorType.RPM_435, NebulaMotor.Direction.Forward,
            NebulaMotor.IdleMode.Coast, isEnabled);
        
////        this.hardwareMap = hardwareMap;
//        left = new MotorEx(hardwareMap, "vLSlide");
//        right = new MotorEx(hardwareMap, "vRSlide");
//        left.setInverted(true);
//        right.setInverted(false);
    }

    @Override
    public void periodic(){
        double output = slideController.calculate(right.getPosition());
        telemetry.addData("setPoint", slideController.getSetPoint());
        telemetry.addData("encoder", right.getPosition());
        telemetry.addData("output", output);
        telemetry.addData("slide power: ", right.getCorrectedVelocity());

        right.setPower(output);
        left.setPower(output);
    }

    public Command setSetPoint(double num){
        return new InstantCommand(()-> slideController.setSetPoint(num));
    }
}
