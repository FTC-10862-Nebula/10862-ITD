package org.firstinspires.ftc.teamcode.subsystems.outtake;


import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.util.NebulaConstants;
import org.firstinspires.ftc.teamcode.util.nebulaHardware.NebulaServo;

@Config
public class Pivot extends SubsystemBase
{
    Telemetry telemetry;
    private final NebulaServo pivot;

    public Pivot(Telemetry tl, HardwareMap hw, boolean isEnabled) {
        pivot = new NebulaServo(hw,
            "pivot",
            NebulaServo.Direction.Forward,
            0,
            360,
            isEnabled);
        this.telemetry = tl;
    }

    @Override
    public void periodic() {}
    public void setSetPoint(double num) {
        pivot.setPosition(num);
    }
    public double getPosition(){
        return pivot.getPosition();
    }

}