package org.firstinspires.ftc.teamcode.opmode.teleop.test;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;
//@Disabled
@TeleOp
public class ServoTest extends OpMode {
    Servo servo1;
 Servo servo2;

     double servoPos=0;
    @Override
    public void init() {
        servo1 =hardwareMap.get(Servo.class, "armL");
        servo1.setDirection(Servo.Direction.FORWARD);
        servo2 =hardwareMap.get(Servo.class, "armR");
        servo2.setDirection(Servo.Direction.REVERSE);


        servo1.setPosition(0);
        servo2.setPosition(0);
    }

    @Override
    public void loop() {
        if (gamepad1.b) {
            servo1.setPosition(servo1.getPosition() + 0.001);
            servo2.setPosition(servo2.getPosition() + 0.001);
        }
       else if (gamepad1.a) {
           servo1.setPosition(servo1.getPosition() - 0.001);
          servo2.setPosition(servo2.getPosition() - 0.001);
        }

        if (gamepad1.x) {
            servo1.setPosition(1);
           servo2.setPosition(1);
        }
        else if (gamepad1.y) {
            servo1.setPosition(0);
            servo2.setPosition(0);
        }

        telemetry.addData("Servo1 Pos", servo1.getPosition());
        telemetry.addData("servo2 pos", servo2.getPosition());
        telemetry.update();
    }
}