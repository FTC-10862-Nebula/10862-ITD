package org.firstinspires.ftc.teamcode.subsystems;


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
    public enum ClawPos {
        CLOSE_POS(0.175,0.715),
//        AUTO_CLOSE (0.5),
//        INTAKE_OPEN(0.13,0),
        OPEN_POS(0.30,0.61);

        public final double clawPosF, clawPosB;
        ClawPos(double clawPosF,double clawPosB) {
            this.clawPosF = clawPosF;
            this.clawPosB = clawPosB;
        }
    }

    Telemetry telemetry;
    private final NebulaServo clawF, clawB;     //Claw

    public Claw(Telemetry tl, HardwareMap hw, boolean isEnabled) {
        clawF = new NebulaServo(hw,
            NebulaConstants.Claw.clawFName,
            NebulaConstants.Claw.clawFDirection,
            NebulaConstants.Claw.minAngle,
            NebulaConstants.Claw.maxAngle,
            isEnabled);
        clawB = new NebulaServo(hw,
            NebulaConstants.Claw.clawBName,
            NebulaConstants.Claw.clawBDirection,
            NebulaConstants.Claw.minAngle,
            NebulaConstants.Claw.maxAngle,
            isEnabled);
//        setBothClaw(ClawPos.CLOSE_POS);
        clawB.setPosition(ClawPos.CLOSE_POS.clawPosB);
        clawF.setPosition(ClawPos.CLOSE_POS.clawPosF);
        this.telemetry = tl;
    }

    @Override
    public void periodic() {
    //    telemetry.addData("Claw Servo 1 Pos: ", clawF.getPosition());
    }

//    public Command setClawPos(ClawPos pos){
//        return new InstantCommand(()->{
//            clawF.setPosition(pos.clawPos);});
//    }
    public Command setFClaw(ClawPos pos){
        return new InstantCommand(()->{
            clawF.setPosition(pos.clawPosF);
        });
    }
    public Command setBClaw(ClawPos pos){
        return new InstantCommand(()->{
            clawB.setPosition(pos.clawPosB);
        });
    }
    public Command setBothClaw(ClawPos pos){
        return new InstantCommand(()->{
            clawB.setPosition(pos.clawPosB);
            clawF.setPosition(pos.clawPosF);
        });
    }
}