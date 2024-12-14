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

public class Slide extends SubsystemBase {

    private final MotorEx right =  new MotorEx(hardwareMap, "vRSlide")
            , left=  new MotorEx(hardwareMap, "vLSlide");
    protected PIDFController slideController = new PIDFController(0,0,0,0);
//    private final HardwareMap hardwareMap;
    private final Telemetry telemetry;

    public Slide (HardwareMap hardwareMap, Telemetry telemetry){
        this.telemetry = telemetry;
////        this.hardwareMap = hardwareMap;
//        left = new MotorEx(hardwareMap, "vLSlide");
//        right = new MotorEx(hardwareMap, "vRSlide");
//        left.setInverted(true);
//        right.setInverted(false);
    }

    @Override
    public void periodic(){
//        double output = slideController.calculate(right.getCurrentPosition());
//        telemetry.addData("setPoint", slideController.getSetPoint());
//        telemetry.addData("encoder", right.getCurrentPosition());
//        telemetry.addData("output", output);
//        telemetry.addData("slide power: ", right.getCorrectedVelocity());

//        right.set(output);
//        left.set(output);
    }

//    public Command setSetPoint(double num){
//        return new InstantCommand(()-> slideController.setSetPoint(num));
//    }
}
