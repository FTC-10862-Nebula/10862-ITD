package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Arm {
    private final DcMotor Arm;
    private HardwareMap hardwareMap;
    private Gamepad Driver1;
    private Gamepad Driver2;

    public static int Intake = (int) 712.55;
    public static int Outtake = 0;

    public Arm(OpMode opMode){
        Driver1 = opMode.gamepad1;
        Driver2 = opMode.gamepad2;
        hardwareMap = opMode.hardwareMap;

         Arm = hardwareMap.get(DcMotor.class,"Arm");
         Arm.setDirection(DcMotorSimple.Direction.FORWARD);
         Arm.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
         Arm.setTargetPosition(Intake);
    }

    public void teleOp(){
        if (Driver2.dpad_up) intakePos();
        else if (Driver2.dpad_down) outtakePos();
    }
    public void intakePos(){
        Arm.setPower(1);
        Arm.setTargetPosition(Intake);
    }
    public void outtakePos(){
        Arm.setPower(1);
        Arm.setTargetPosition(Outtake);
    }

}
