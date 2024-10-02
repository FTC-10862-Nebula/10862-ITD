package org.firstinspires.ftc.teamcode.opmode.teleop.misc;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

//@Disabled
@TeleOp
public class ArmTest extends OpMode {
    Servo servo2, servo1;
    /**
     * User defined init method
     * <p>
     * This method will be called once when the INIT button is pressed.
     */
    @Override
    public void init() {
        servo2 = hardwareMap.get(Servo.class, "armR");
        servo2.setDirection(Servo.Direction.REVERSE);
        servo1 = hardwareMap.get(Servo.class, "armL");
        servo1.setDirection(Servo.Direction.FORWARD);
//        servo1.setPosition(0);
    }

    /**
     * User defined loop method
     * <p>
     * This method will be called repeatedly in a loop while this op mode is running
     */
    @Override
    public void loop() {
        if(gamepad1.dpad_down){
            servo2.setPosition(servo2.getPosition()+ 0.001);
            servo1.setPosition(servo1.getPosition()+ 0.001);
        }
        else if(gamepad1.dpad_up){
            servo2.setPosition(servo2.getPosition()- 0.001);
            servo1.setPosition(servo1.getPosition()- 0.001);
        }
//        servo1.setPosition(0);
        telemetry.addData("Servo pos2: ",servo2.getPosition());
        telemetry.addData("Servo pos1: ",servo1.getPosition());
        telemetry.update();
    }
}