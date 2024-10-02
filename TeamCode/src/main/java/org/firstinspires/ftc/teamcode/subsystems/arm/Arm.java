package org.firstinspires.ftc.teamcode.subsystems.arm;


import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.command.Command;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.util.NebulaConstants;
import org.firstinspires.ftc.teamcode.util.nebulaHardware.NebulaServo;

import java.util.function.Supplier;

@Config
public class Arm extends SubsystemBase
{
    public enum ArmPos {
        TRANSFER(0.03,0.03),
        OUTTAKE(0.37,0.37);

        public final double armRPos, armLPos;
        ArmPos(double armRPos, double armLPos) {
            this.armRPos = armRPos;
            this.armLPos = armLPos;
        }
    }
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
//        armSetPositionCommand(ArmPos.TRANSFER);
        armSetPosition(ArmPos.TRANSFER.armRPos,ArmPos.TRANSFER.armLPos );
        this.telemetry = tl;
    }

    @Override
    public void periodic() {
        telemetry.addData("ArmR Pos: ", armR.getPosition());
        telemetry.addData("ArmL Pos: ", armL.getPosition());
    }
    private void armSetPosition(double rNum, double lNum) {
        armR.setPosition(rNum);
        armL.setPosition(lNum);
    }
    public Command armSetPositionCommand(ArmPos armPos) {
        return new InstantCommand(()->
            {armSetPosition(armPos.armRPos,
                    armPos.armLPos);});
    }
    public Command armSetPositionCommand(double rNum, double lNum) {
        return new InstantCommand(()->{armSetPosition(rNum, lNum);});
    }
    public double getRPosition(){
        return armR.getPosition();
    }
    public double getLPosition(){
        return armL.getPosition();
    }

}