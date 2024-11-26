package org.firstinspires.ftc.teamcode.subsystems.outtake;


import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.util.nebulaHardware.NebulaServo;

@Config
public class Arm extends SubsystemBase
{
    Telemetry telemetry;
    private final NebulaServo armR, armL;

    public Arm(Telemetry tl, HardwareMap hw, boolean isEnabled) {
        armR = new NebulaServo(hw,
            "armR",
            NebulaServo.Direction.Forward,
            0,
            360,
            isEnabled);
        armL = new NebulaServo(hw,
            "armL",
            NebulaServo.Direction.Forward,
            0,
            360,
            isEnabled);
        this.telemetry = tl;
    }

    @Override
    public void periodic() {
        telemetry.addData("ArmR Pos: " , getRPosition());
        telemetry.addData("ArmLPos: ", getLPosition());

    }
    public void setSetPoint(double rNum, double lNum) {
        armR.setPosition(rNum);
        armL.setPosition(lNum);
    }
    public double getRPosition(){
        return armR.getPosition();
    }
    public double getLPosition(){
        return armL.getPosition();
    }

}