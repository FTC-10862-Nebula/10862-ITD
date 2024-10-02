package org.firstinspires.ftc.teamcode.opmode.teleop.misc;

import com.arcrobotics.ftclib.hardware.motors.MotorEx;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@TeleOp
public class MotorTest extends OpMode {
    private DcMotorEx motor;
    /**
     * User defined init method
     * <p>
     * This method will be called once when the INIT button is pressed.
     */
    @Override
    public void init() {
        motor = hardwareMap.get(DcMotorEx.class, "intake");
        motor.setDirection(DcMotorSimple.Direction.FORWARD);

    }

    /**
     * User defined loop method
     * <p>
     * This method will be called repeatedly in a loop while this op mode is running
     */
    @Override
    public void loop() {
        if(gamepad1.dpad_down){
            motor.setPower(-0.4);
        }
        else if(gamepad1.dpad_up){
            motor.setPower(0.4);
        }
        else{
            motor.setPower(0);
        }
        telemetry.addData("Motor: ", motor.getCurrentPosition());
        telemetry.update();

    }
}