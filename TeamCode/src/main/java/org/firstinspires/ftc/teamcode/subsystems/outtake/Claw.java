package org.firstinspires.ftc.teamcode.subsystems.outtake;


import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.util.nebulaHardware.NebulaServo;

@Config
public class Claw extends SubsystemBase {

    public enum Value{
        OPEN_SAMPLE(.538),
        CLOSE_SAMPLE(.72),
        OPEN_SPECIMEN(CLOSE_SAMPLE),
        CLOSE_SPECIMEN(OPEN_SAMPLE);
        public final double pos;
        Value(double pos) {
            this.pos = pos;
        }
        Value(Value value) {
            this.pos = value.pos;
        }
    }

    Telemetry telemetry;
    private final NebulaServo turnServo, clawServo;     //Claw

    public Claw(Telemetry tl, HardwareMap hw, boolean isEnabled) {
        turnServo = new NebulaServo(hw,
            "turnS",
            NebulaServo.Direction.Forward,
            0,
            360,
            isEnabled);
        clawServo = new NebulaServo(hw,
            "clawS",
            NebulaServo.Direction.Forward,
            0,
            360,
            isEnabled);
        this.telemetry = tl;
    }

    @Override
    public void periodic() {
        telemetry.addData("Turn Servo: " + getTurnPos() + "; Claw: ", getClawPos());

    }
    public void setTurnSetPoint(double turnPos){
        turnServo.setPosition(turnPos);//.162
//        clawServo.setPosition(clawPos);//.356
        //Claw close: 0.72   Claw open:0.20
    }

    public void setClawSetPoint(Claw.Value value){
        clawServo.setPosition(value.pos);
    }

    public double getTurnPos(){
        return turnServo.getPosition();
    }
    public double getClawPos(){
        return  clawServo.getPosition();
    }
}