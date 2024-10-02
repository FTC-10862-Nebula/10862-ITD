package org.firstinspires.ftc.teamcode.subsystems;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.Servo;

@Config
public class Claw {
    private static Servo Claw;
    private final double Open = 0;
    private final double Close = .25;
    private static Gamepad Driver2;

    public Claw(OpMode opMode) {
        Driver2 = opMode.gamepad2;
        Claw = (Servo) opMode.hardwareMap.get("claw");
        Claw.setDirection(Servo.Direction.REVERSE);
        Claw(Close, Close);
    }
    public void teleOp(){
        if (Driver2.left_bumper){
            Claw.setPosition(Close);}
        if (Driver2.right_bumper){
            Claw.setPosition(Open);}
    }

    public void Claw(double setPositionRight, double setPositionLeft) {
        Claw.setPosition(setPositionLeft);
    }
}
