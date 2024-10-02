package org.firstinspires.ftc.teamcode.util.nebulaHardware;

import com.arcrobotics.ftclib.hardware.SimpleServo;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

public class NebulaServo {// Normal Servo
    public enum Direction {
        Forward, //False to Inverted
        Reverse //True to Inverted
    }
//    private final SimpleServo servo;
    private final SimpleServo servo; //TODO: CAN Just make this be a simple servo
    private final Boolean isEnabled;

    /**
     * @param hM HardwareMap
     * @param deviceId The Name in the Config File
     * @param direction Direction of Servo Turning
     * @param minAngle Minimal Angle
     * @param maxAngle Maximum Angle
     * @param isEnabled On or Off
     */
    public NebulaServo(HardwareMap hM, String deviceId,
                       Direction direction, double minAngle,
                       double maxAngle, Boolean isEnabled){
        servo = new SimpleServo(hM, deviceId, minAngle, maxAngle, AngleUnit.DEGREES);
        this.isEnabled = isEnabled;
        switch (direction){ //Initialization of Motor Direction
            case Forward:
                setInverted(false);
                break;
            case Reverse:
                setInverted(true);
                break;
        }
    }

    public void setPosition(double position) {
        if (!isEnabled) servo.setPosition(servo.getPosition());//Instead of doing this, maybe close the servo
            else servo.setPosition(position);
    }

    private void setInverted(boolean isInverted) {
        servo.setInverted(isInverted);
    }
    public double getPosition() {
        return servo.getPosition();
    }
    public double getAngle() {
        return servo.getAngle();
    }

    public void disable() {//How does it disable, Restart Teleop or robot
        servo.disable();
    }

    @Deprecated
    public void rotateByAngle(double angle) {
        servo.rotateByAngle(angle, AngleUnit.DEGREES);
    }
    @Deprecated
    public void turnToAngle(double angle) {
        servo.turnToAngle(angle, AngleUnit.DEGREES);
//        servo.turnToAngle(angle);
    }

    public static NebulaServo create(HardwareMap hM, String deviceId,
                                     Direction direction, double minAngle,
                                     double maxAngle, Boolean isEnabled){
        return new NebulaServo(hM, deviceId, direction, minAngle, maxAngle, isEnabled);
    }
}
