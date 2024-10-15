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
    private ColorSensor colorSensor;
    

    public PowerIntake(Telemetry tl, HardwareMap hw, boolean isEnabled) {
        motor = new NebulaMotor(hw, "intakeM",
            NebulaMotor.MotorType.RPM_435, NebulaMotor.Direction.Forward,
            NebulaMotor.IdleMode.Coast, isEnabled);
        this.colorSensor = hw.get(ColorSensor.class, "colorS");
        this.telemetry = tl;
    }

    public PowerIntake(HorizontalSlide tl, IntakeServo intakeServo, PowerIntake powerIntake) {
    }

    @Override
    public void periodic() {
        //Add data for ColorSensor
    }

    public void setSetPoint(double power){
        motor.setPower(power);
    }

    //red (255,0,0)
    //blue (0,0,255)
    //yellow (0,255, 255)
    public int red(){
        return colorSensor.red();
    }
    public int blue(){
        return colorSensor.blue();
    }
    public int green(){
        return colorSensor.green();
    }

    public Intake.Sample getSampleColors(){
        if(colorSensor.red()>255){
            return Intake.Sample.RED; //TODO: Works?
        } else if(colorSensor.green()>255&&colorSensor.blue()>255){
            return Intake.Sample.YELLOW; //TODO: Works?
        } else if(colorSensor.blue()>255){
            return Intake.Sample.BLUE; //TODO: Works?
        } else return  Intake.Sample.NONE;
    }

}
