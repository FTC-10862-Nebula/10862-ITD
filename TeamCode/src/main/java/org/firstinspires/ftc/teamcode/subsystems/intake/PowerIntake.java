package org.firstinspires.ftc.teamcode.subsystems.intake;


import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.command.Command;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.util.NebulaConstants;
import org.firstinspires.ftc.teamcode.util.nebulaHardware.NebulaCRServo;
import org.firstinspires.ftc.teamcode.util.nebulaHardware.NebulaMotor;
import org.firstinspires.ftc.teamcode.util.nebulaHardware.NebulaServo;
//@Deprecated
@Config
public class PowerIntake extends SubsystemBase {
    public enum IntakePower {
        OUTTAKE(-0.7, -0.3),
        INTAKE(.5, 0.3,true),//0.7 --.75
        STOP(0,0),
        OUTTAKE_PURPLE(0, -0.5),//-0.4
        AUTO_INTAKE(0.40, 0.67,true),
        AUTO_OUTTAKE(-0.2, 0);


        public final double power, rollerPower;
        public final boolean reset;
        IntakePower(double power, double rollerPower) {
            this.power = power;
            this.rollerPower = rollerPower;
            this.reset = false;
        }
        IntakePower(double power, double rollerPower, boolean reset) {
            this.power = power;
            this.rollerPower = rollerPower;
            this.reset = reset;
        }
    }
    
    public enum IntakePos {
        UP(0.76,0.76),
        FIVE(.9, .9),
        DOWN(0.925,0.925);

        
        public final double rPos, lPos;
        IntakePos(double rPos, double lPos) {
            this.rPos = rPos;
            this.lPos = lPos;
        }
    }
//    IntakePower shooterRPM = IntakePower.STOP;
    Telemetry telemetry;
    public final NebulaMotor motor;
    private final NebulaServo intakeServoR,intakeServoL;
    private final NebulaCRServo rollerServo;
    

    public PowerIntake(Telemetry tl, HardwareMap hw, Boolean isEnabled) {
        motor = new NebulaMotor(hw, NebulaConstants.Intake.intakeMName,
            NebulaConstants.Intake.intakeType, NebulaConstants.Intake.intakeDirection,
            NebulaConstants.Intake.intakeIdleMode, isEnabled);
        intakeServoR = new NebulaServo(hw,
            NebulaConstants.Intake.intakeRName,
            NebulaConstants.Intake.intakeRDirection,
            NebulaConstants.Intake.minAngle,
            NebulaConstants.Intake.maxAngle,
            isEnabled);
        intakeServoL = new NebulaServo(hw,
            NebulaConstants.Intake.intakeLName,
            NebulaConstants.Intake.intakeLDirection,
            NebulaConstants.Intake.minAngle,
            NebulaConstants.Intake.maxAngle,
            isEnabled);
        rollerServo = new NebulaCRServo(hw,
            NebulaConstants.Intake.rollerName,
            NebulaConstants.Intake.rollerDirection,
            NebulaConstants.Intake.rollerIdleMode,
            isEnabled);
        setUp();
        this.telemetry = tl;
    }

    @Override
    public void periodic() {
//        telemetry.addData("Intake Speed:", motor.getVelocity());
    }

    public void setPower(double power, double rollerPower, boolean reset) {
        motor.setPower(power);
        rollerServo.setPower(rollerPower);
        if(reset){
            NebulaConstants.Intake.intakeTime.reset();
        }
    }

    //TODO: Test!
    public Command setSetPointCommand(double power, double rollerPower, boolean reset) {
        return new InstantCommand(()->{
            setPower(power, rollerPower, reset);});
    }
    public Command setSetPointCommand(IntakePower pos) {
        return setSetPointCommand(pos.power, pos.rollerPower,pos.reset);
//        return new InstantCommand(()->{setSetPoint(pos);});
    }

    public void encoderReset() {//Motors wouldn't need reset
        motor.resetEncoder();
    }

//    @Deprecated //To FInd Alternative or Not Use
    public boolean isIntaked(){//TODO:Needs to have something where it times
        if(NebulaConstants.Intake.intakeTime.seconds()>2){
//            return controller.getVelocityError()>100;//Whatever the Number is
        }
        return false;
    }
    
    public void setUp(){
        intakeServoR.setPosition(IntakePos.UP.rPos);
        intakeServoL.setPosition(IntakePos.UP.lPos);
    }
    public void setDown(){
        intakeServoR.setPosition(IntakePos.DOWN.rPos);
        intakeServoL.setPosition(IntakePos.DOWN.lPos);
    }
    public void setFive(){
        intakeServoR.setPosition(IntakePos.FIVE.rPos);
        intakeServoL.setPosition(IntakePos.FIVE.lPos);
    }
}
