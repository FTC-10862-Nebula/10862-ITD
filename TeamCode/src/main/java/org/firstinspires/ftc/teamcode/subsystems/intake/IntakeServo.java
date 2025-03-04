package org.firstinspires.ftc.teamcode.subsystems.intake;


import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.util.NebulaConstants;
import org.firstinspires.ftc.teamcode.util.nebulaHardware.NebulaMotor;
import org.firstinspires.ftc.teamcode.util.nebulaHardware.NebulaServo;
@Disabled
@Config
public class IntakeServo extends SubsystemBase {
    private final NebulaServo intakeR;
    private final Telemetry telemetry;

    public IntakeServo(Telemetry tl, HardwareMap hw, Boolean isEnabled) {
        Telemetry telemetry1;
        intakeR = new NebulaServo(hw,
            "intakeR",
            NebulaServo.Direction.Forward,
            0,
            360,
            isEnabled);
        this.telemetry =tl;
    }

    @Override
    public void periodic() {
        telemetry.addData("IntakeServoPos: ", getPos());

    }

    public void setSetPoint(double posR){
        intakeR.setPosition(posR);
    }
    public double getPos(){
        return intakeR.getPosition();
    }
}
