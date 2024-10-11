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
    public final NebulaMotor motor;
    private final ColorSensor colorSensor;
    

    public PowerIntake(Telemetry tl, HardwareMap hw, boolean isEnabled) {
        motor = new NebulaMotor(hw, "intakeM",
            NebulaMotor.MotorType.RPM_435, NebulaMotor.Direction.Forward,
            NebulaMotor.IdleMode.Coast, isEnabled);
        this.colorSensor = hw.get(ColorSensor.class, "colorS");
        this.telemetry = tl;
    }

    @Override
    public void periodic() {
        //Add data for ColorSensor
    }

    public void setSetPoint(double power){
        motor.setPower(power);
    }

    public int red(){
        return colorSensor.red();
    }
    public int blue(){
        return colorSensor.blue();
    }
    public int green(){
        return colorSensor.green();
    }

}
