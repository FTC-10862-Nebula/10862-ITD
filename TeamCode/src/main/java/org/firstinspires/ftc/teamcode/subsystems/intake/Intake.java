package org.firstinspires.ftc.teamcode.subsystems.intake;


import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.command.Command;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.controller.PIDFController;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.util.NebulaConstants;
import org.firstinspires.ftc.teamcode.util.nebulaHardware.NebulaMotor;
import org.firstinspires.ftc.teamcode.util.nebulaHardware.NebulaServo;
@Deprecated
@Config
public class Intake extends SubsystemBase {
    public final PIDFController controller;

    public enum IntakeRPM {
        OUTTAKE(100),
        INTAKE(-100,true),
        STOP(0);

        public final double speed;
        public final boolean down;
        IntakeRPM(double speed) {
            this.speed = speed;
            this.down = false;
        }
        IntakeRPM(double speed, boolean down) {
            this.speed = speed;
            this.down = down;
        }
    }

    public enum IntakePos {
        UP(0,0),
        DOWN(0,0);

        public final double rPos, lPos;
        IntakePos(double rPos, double lPos) {
            this.rPos = rPos;
            this.lPos = lPos;
        }
    }

    IntakeRPM shooterRPM = IntakeRPM.STOP;
    Telemetry telemetry;
    public final NebulaMotor motor;
    private final NebulaServo intakeServoR,intakeServoL;


    public Intake(Telemetry tl, HardwareMap hw, Boolean isEnabled) {
        motor = new NebulaMotor(hw, NebulaConstants.Intake.intakeMName,
            NebulaConstants.Intake.intakeType, NebulaConstants.Intake.intakeDirection,
            NebulaMotor.IdleMode.Coast, isEnabled);
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
        controller = new PIDFController(
            NebulaConstants.Intake.intakePID.p,
            NebulaConstants.Intake.intakePID.i,
            NebulaConstants.Intake.intakePID.d,
            NebulaConstants.Intake.intakePID.f,
            getShooterRPM(),
            getShooterRPM());
        controller.setTolerance(NebulaConstants.Intake.intakeTolerance);
        this.telemetry = tl;
    }

    @Override
    public void periodic() {
        double output = (controller.calculate(getShooterRPM()));
        telemetry.addData("Intake RPM:", getShooterRPM());
        telemetry.addData("Intake Required RPM:", controller.getSetPoint());

        motor.setPower(output);
    }


    public double getShooterRPM() {
        return motor.getCorrectedVelocity();//TODO:Fix RPM Math
//        return 60 * ((double) motorGroup.getCorrectedVelocity() /
//            (double) Constants.SHOOTER_TPR);
    }
    // motor.getRPM() *60/motor.getCPR()
    // rpm_right = (float)(right_wheel_pulse_count * 60 / ENC_COUNT_REV);
    //    ang_velocity_right = rpm_right * rpm_to_radians;
    //    ang_velocity_right_deg = ang_velocity_right * rad_to_deg;


    public void setSetPoint(double setPoint, boolean down) {
//        if(setPoint>NebulaConstants.Intake.MAX_POSITION ||
//            setPoint<NebulaConstants.Intake.MIN_POSITION){
//            motor.stop();
//            return;
//        }
        controller.setSetPoint(setPoint);

        if(down){
            setDown();
        } else {
            setUp();
        }
//        if(reset){NebulaConstants.Intake.intakeTime.reset();} //Just Add if using this feature
    }

    //TODO: Test!
    public Command setSetPointCommand(double setPoint, boolean down) {
        return new InstantCommand(()->{setSetPoint(setPoint, down);});
    }
    public Command setSetPointCommand(IntakeRPM pos) {
        return setSetPointCommand(pos.speed, pos.down);
//        return new InstantCommand(()->{setSetPoint(pos);});
    }

    public void encoderReset() {//Motors wouldn't need reset; Would It
        motor.resetEncoder();
    }
    public double getSetPoint(){
        return controller.getSetPoint();
    }

//    public boolean isIntaked(){
//        //TODO:Needs to have something where it times
//        // Would be for coontroller shaking (FRC 2023)
//        if(NebulaConstants.Intake.intakeTime.seconds()>2){
//            return controller.getVelocityError()>100;//Whatever the Number is
//        }
//        return false;
//    }

    private void setDown(){
        intakeServoR.setPosition(IntakePos.DOWN.rPos);
        intakeServoL.setPosition(IntakePos.DOWN.lPos);
    }
    private void setUp(){
        intakeServoR.setPosition(IntakePos.UP.rPos);
        intakeServoL.setPosition(IntakePos.UP.lPos);
    }

}
