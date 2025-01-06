package org.firstinspires.ftc.teamcode.subsystems.intake;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.util.nebulaHardware.NebulaMotor;

@Config
public class PowerIntake extends SubsystemBase {
    private Telemetry telemetry;
    public NebulaMotor motor;
    
    public PowerIntake(Telemetry tl, HardwareMap hw, boolean isEnabled) {
        motor = new NebulaMotor(hw, "intakeM",
            NebulaMotor.MotorType.RPM_435, NebulaMotor.Direction.Reverse,
            NebulaMotor.IdleMode.Coast, isEnabled);
        this.telemetry = tl;
    }

    @Override
    public void periodic() {}
    public void setSetPoint(double power){
        motor.setPower(power);
    }
}
