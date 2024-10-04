package org.firstinspires.ftc.teamcode.subsystems.intake;


import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.util.NebulaConstants;
import org.firstinspires.ftc.teamcode.util.nebulaHardware.NebulaServo;

@Config
public class IntakeServo extends SubsystemBase {
    private final NebulaServo intakeServo;
    private final Telemetry telemetry;

    public IntakeServo(Telemetry tl, HardwareMap hw, Boolean isEnabled) {
        intakeServo = new NebulaServo(hw,
            NebulaConstants.Intake.intakeRName,
            NebulaConstants.Intake.intakeRDirection,
            NebulaConstants.Intake.minAngle,
            NebulaConstants.Intake.maxAngle,
            isEnabled);
        this.telemetry = tl;
    }

    @Override
    public void periodic() {
        telemetry.addData("IntakeServoPos: ", intakeServo.getPosition());
    }

    public void setSetPoint(double pos){
        intakeServo.setPosition(pos);
    }
}
