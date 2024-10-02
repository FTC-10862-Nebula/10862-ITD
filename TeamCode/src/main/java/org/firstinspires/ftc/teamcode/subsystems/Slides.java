package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.robotcore.external.Telemetry;

public class Slides {
    private final DcMotor RightSlide;
    private final DcMotor LeftSlide;
    private final Gamepad driver1;
    private final Gamepad driver2;
    private final Telemetry telemetry;
    private final HardwareMap hardwareMap;

    public static double POWER = 1;
    public static int High = 1850;
    public static int Mid = 1450;
    public static int Low = 1100;
    public static int RESET = 0;
    public static int MANUAL_MOVE_SPEED = 10;
    private int position = 0;

        public Slides (OpMode opMode) {
            driver1 = opMode.gamepad1;
            driver2 = opMode.gamepad2;
            hardwareMap = opMode.hardwareMap;
            telemetry = opMode.telemetry;

            LeftSlide = hardwareMap.get(DcMotor.class,"LeftSlide");
            LeftSlide.setDirection(DcMotorSimple.Direction.REVERSE);
            LeftSlide.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

            RightSlide = hardwareMap.get(DcMotor.class,"RightSlide");
            RightSlide.setDirection(DcMotorSimple.Direction.FORWARD);
            RightSlide.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

            LeftSlide.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            RightSlide.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

            LeftSlide.setTargetPosition(0);
            RightSlide.setTargetPosition(0);

            RightSlide.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            LeftSlide.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        }

        public void teleOp(){
            if(driver1.y) moveHigh();
            else if (driver1.x) moveMid();
            else if (driver1.b) moveLow();
            else if(driver1.a) Reset();
            else if (driver1.dpad_up) moveMotors(position + MANUAL_MOVE_SPEED);
            else if (driver1.dpad_down) moveMotors(position - MANUAL_MOVE_SPEED);
            telemetry.addData("SlidePos",LeftSlide.getCurrentPosition());
            telemetry.addData("SlidePos",RightSlide.getCurrentPosition());

        }
    public void moveHigh(){
        LeftSlide.setPower(1);
        LeftSlide.setTargetPosition(High);

        RightSlide.setPower(1);
        RightSlide.setTargetPosition(High);
    }
    public void moveMid() {
        LeftSlide.setPower(1);
        LeftSlide.setTargetPosition(Mid);

        RightSlide.setPower(1);
        RightSlide.setTargetPosition(Mid);

    }
    public void moveLow() {
        LeftSlide.setPower(1);
        LeftSlide.setTargetPosition(Low);

        RightSlide.setPower(1);
        RightSlide.setTargetPosition(Low);

    }
    public void Reset(){
        LeftSlide.setPower(1);
        LeftSlide.setTargetPosition(RESET);

        RightSlide.setPower(1);
        RightSlide.setTargetPosition(RESET);
    }
    public void moveMotors(int position){
        this.position = position;
        LeftSlide.setTargetPosition(position);
        RightSlide.setTargetPosition(position);

        LeftSlide.setPower(POWER);
        RightSlide.setPower(POWER);

    }
}
