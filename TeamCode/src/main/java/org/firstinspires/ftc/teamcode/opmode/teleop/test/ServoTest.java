package org.firstinspires.ftc.teamcode.opmode.teleop.test;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;
//@Disabled
@TeleOp
public class    ServoTest extends OpMode {
    Servo servo1;
  //  Servo servo2;
     double servoPos=0;
    /**
     * User defined init method
     * <p>
     * This method will be called once when the INIT button is pressed.
     */
    @Override
    public void init() {
      //  servo1 = hardwareMap.get(Servo.class, "intakeL");
        //  servo1.setDirection(Servo.Direction.REVERSE);
        servo1 =hardwareMap.get(Servo.class, "pivot");
        servo1.setPosition(0);
        servo1.setDirection(Servo.Direction.FORWARD);
//        servo2 =hardwareMap.get(Servo.class, "armR");
//        servo2.setPosition(0);
//        servo2.setDirection(Servo.Direction.REVERSE);


        //  servo2.setDirection(Servo.Direction.REVERSE);

    }

    /**
     * User defined loop method
     * <p>
     * This method will be called repeatedly in a loop while this op mode is running
     */
    @Override
    public void loop() {
        if (gamepad1.b) {
//            servoPos = (servoPos+ 0.001);
            servo1.setPosition(servo1.getPosition() + 0.001);
           // servo2.setPosition(servo2.getPosition() + 0.001);
        }
           else if (gamepad1.a) {
//            servoPos = (servoPos- 0.001);
                servo1.setPosition(servo1.getPosition() - 0.001);
          //  servo2.setPosition(servo2.getPosition() - 0.001);
        }
//                if (gamepad1.x) {
//                    servo1.setPosition(0.45);
//                    //   servoPos=((0.45));
//                }
//                if (gamepad1.y) {
//                    //  servoPos=((0.46));
//                    servo1.setPosition(0.46);
//                }


                //  servo1.setPosition(servoPos);
                //DO NOT CHANGE THIS PLEASE
                //  telemetry.addData("Servo1 Require",servoPos);

        telemetry.addData("Servo1 Pos", servo1.getPosition());
         //  telemetry.addData("servo2 pos", servo2.getPosition());
            telemetry.update();
            }
        }