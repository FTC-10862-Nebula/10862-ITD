package org.firstinspires.ftc.teamcode.subsystems.outtake;


import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.util.nebulaHardware.NebulaServo;

@Config
public class Claw extends SubsystemBase
{
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
    }
    public void setSetPoint(double turnPos, double clawPos){
        turnServo.setPosition(0);
        clawServo.setPosition(0);
        //Claw close: 0.72   Claw open:0.20
    }

    public double getTurnPos(){
        return turnServo.getPosition();
    }
    public double getClawPos(){
        return  clawServo.getPosition();
    }
}