package org.firstinspires.ftc.teamcode.subsystems.Outtake;


import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.command.Command;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.util.NebulaConstants;
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
        telemetry.addData("Turn Servo: " + turnServo.getPosition() + "; Claw: ", clawServo.getPosition());
    }
    public void setSetPoint(double turnPos, double clawPos){
        turnServo.setPosition(turnPos);
        clawServo.setPosition(clawPos);
    }
}