package org.firstinspires.ftc.teamcode.util.nebulaHardware;

import com.arcrobotics.ftclib.hardware.motors.CRServo;
import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class NebulaCRServo {// Continuous Rotation Servo
    private final CRServo servo;
    private final Boolean isEnabled;

    /**
     * @param hM HardwareMap
     * @param deviceId The Name in the Config File
     * @param direction Direction of Servo Turning
     * @param behavior Coast or Brake Mode
     * @param isEnabled On or Off
     */
    public NebulaCRServo(HardwareMap hM, String deviceId,
                         NebulaMotor.Direction direction,
                         NebulaMotor.IdleMode behavior,
                         Boolean isEnabled){
        servo = new CRServo(hM, deviceId);
        this.isEnabled = isEnabled;
        switch (direction){ //Initialization of Motor Direction
            case Forward:
                setInverted(false);
                break;
            case Reverse:
                setInverted(true);
                break;
        }
//        setIdleMode(behavior);
    }

    public void setPower(double power) {
        if (!isEnabled) servo.stopMotor();
        else servo.set(power);
    }
    public void stop() {
        servo.stopMotor();
    }

    public void resetEncoder() {
        servo.resetEncoder();
    }
    public void setInverted(boolean isInverted) {
        servo.setInverted(isInverted);
    }
    public double getPosition() {
        return servo.getCurrentPosition();
    }

    public void close() {//How does it disable, Restart Teleop or robot
        servo.disable();
    }
//    public void setIdleMode(NebulaMotor.IdleMode behavior) {
//        switch(behavior){
//            case Coast: servo.setZeroPowerBehavior(Motor.ZeroPowerBehavior.FLOAT);
//                break;
//            case Brake: servo.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE);
//                break;
//        }
//    }

    public static NebulaCRServo create(HardwareMap hM, String deviceId, NebulaMotor.Direction direction,
                                       NebulaMotor.IdleMode behavior, Boolean isEnabled){
        return new NebulaCRServo(hM, deviceId, direction, behavior, isEnabled);
    }
}
