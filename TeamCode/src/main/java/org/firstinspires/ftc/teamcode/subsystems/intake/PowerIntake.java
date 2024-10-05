package org.firstinspires.ftc.teamcode.subsystems.intake;


import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.command.Command;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.util.NebulaConstants;
import org.firstinspires.ftc.teamcode.util.nebulaHardware.NebulaCRServo;
import org.firstinspires.ftc.teamcode.util.nebulaHardware.NebulaMotor;
import org.firstinspires.ftc.teamcode.util.nebulaHardware.NebulaServo;

import java.sql.DriverManager;

@Config
public class PowerIntake extends SubsystemBase {
    private Telemetry telemetry;
    public final NebulaMotor motor;
    private final ColorSensor colorSensor;
    

    public PowerIntake(Telemetry tl, HardwareMap hw, Boolean isEnabled) {
        motor = new NebulaMotor(hw, NebulaConstants.Intake.intakeMName,
            NebulaConstants.Intake.intakeType, NebulaConstants.Intake.intakeDirection,
            NebulaConstants.Intake.intakeIdleMode, isEnabled);
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
