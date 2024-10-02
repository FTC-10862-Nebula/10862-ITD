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
public class AutoDropper extends SubsystemBase
{
    public enum DropPos {
        HOLD(0.55),
        TELEOP_HOLD(.62),
        DROP(0.64);

        public final double dropperPos;
        DropPos(double dropperPos) {
            this.dropperPos = dropperPos;
        }
    }
    Telemetry telemetry;
    private final NebulaServo dropper;

    public AutoDropper(Telemetry tl, HardwareMap hw, boolean isEnabled) {
        dropper = new NebulaServo(hw,
                NebulaConstants.AutoDropper.dropperName,
                NebulaConstants.AutoDropper.dropperDirection,
                NebulaConstants.AutoDropper.minAngle,
                NebulaConstants.AutoDropper.maxAngle,
                isEnabled);
        dropperSetPosition(DropPos.HOLD.dropperPos);
        this.telemetry = tl;
    }

    @Override
    public void periodic() {
        telemetry.addData("Dropper Pos: ", dropper.getPosition());
    }
    private void dropperSetPosition(double pos) {
        dropper.setPosition(pos);
    }
    public Command dropperSetPositionCommand(DropPos dropPos) {
        return new InstantCommand(()->dropperSetPosition(dropPos.dropperPos));
    }
//    public Command dropperSetPositionCommand(double pos) {
//        return new InstantCommand(()->{dropperSetPosition(pos);});
//    }
    public double getDropperPosition(){
        return dropper.getPosition();
    }

}