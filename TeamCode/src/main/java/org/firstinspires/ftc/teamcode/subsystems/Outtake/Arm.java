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
public class Arm extends SubsystemBase
{
    Telemetry telemetry;
    private final NebulaServo armR, armL;

    public Arm(Telemetry tl, HardwareMap hw, boolean isEnabled) {
        armR = new NebulaServo(hw,
                NebulaConstants.Arm.armRName,
                NebulaConstants.Arm.armRDirection,
                NebulaConstants.Arm.minAngle,
                NebulaConstants.Arm.maxAngle,
                isEnabled);
        armL = new NebulaServo(hw,
                NebulaConstants.Arm.armLName,
                NebulaConstants.Arm.armLDirection,
                NebulaConstants.Arm.minAngle,
                NebulaConstants.Arm.maxAngle,
                isEnabled);
//        setSetPoint(Outtake.Value.);
        this.telemetry = tl;
    }

    @Override
    public void periodic() {
        telemetry.addData("ArmR Pos: " + armR.getPosition() +"; ArmLPos: ", armL.getPosition());
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