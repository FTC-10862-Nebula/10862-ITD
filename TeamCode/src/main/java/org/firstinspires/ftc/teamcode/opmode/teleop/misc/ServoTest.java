package org.firstinspires.ftc.teamcode.opmode.teleop.misc;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;
//@Disabled
@TeleOp
public class ServoTest extends OpMode {
    Servo servo1;
    /**
     * User defined init method
     * <p>
     * This method will be called once when the INIT button is pressed.
     */
    @Override
    public void init() {
        servo1 = hardwareMap.get(Servo.class, "intakeL");
 //         servo1.setDirection(Servo.Direction.REVERSE);
      servo1.setDirection(Servo.Direction.FORWARD);

        servo1.setPosition(0);
    }

    /**
     * User defined loop method
     * <p>
     * This method will be called repeatedly in a loop while this op mode is running
     */
    @Override
    public void loop() {
        if(gamepad1.dpad_down){
            servo1.setPosition(servo1.getPosition()+ 0.001);
        }
        else if(gamepad1.dpad_up){
            servo1.setPosition(servo1.getPosition()- 0.001);
        }

        //DO NOT CHANGE THIS PLEASE
        telemetry.addData("Servo",servo1.getPosition());
        telemetry.update();
    }
}