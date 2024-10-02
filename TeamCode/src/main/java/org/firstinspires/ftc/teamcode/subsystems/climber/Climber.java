package org.firstinspires.ftc.teamcode.subsystems.climber;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.command.Command;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.controller.PIDFController;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.util.NebulaConstants;
import org.firstinspires.ftc.teamcode.util.nebulaHardware.NebulaMotor;

@Config
public class Climber extends SubsystemBase {
    protected Telemetry telemetry;
    protected NebulaMotor climber;
    protected PIDFController climberController;
    protected double output = 0, multiplier = 2;

    public enum ClimbEnum {
        CLIMB(1850),
        SHOOTER(780),
        REST(0),
        MANUAL(10.0);
        public final double climbPos;
        ClimbEnum(double climbPos) {
            this.climbPos = climbPos;
        }
    }


    protected static ClimbEnum climbPos;

    public Climber(Telemetry tl, HardwareMap hw, boolean isEnabled) {
        climber = new NebulaMotor(hw,
            NebulaConstants.Climber.climberName,
            NebulaConstants.Climber.climberType,
            NebulaConstants.Climber.climberDirection,
            NebulaConstants.Climber.climberIdleMode,
            isEnabled);

        climber.setDistancePerPulse(NebulaConstants.Climber.climberDistancePerPulse);

        climberController = new PIDFController(NebulaConstants.Climber.climberPID.p,
            NebulaConstants.Climber.climberPID.i,
            NebulaConstants.Climber.climberPID.d,
            NebulaConstants.Climber.climberPID.f,
            getEncoderDistance(),
            getEncoderDistance());
        climberController.setTolerance(NebulaConstants.Climber.climberTolerance);
    
        setSetPointCommand(Climber.ClimbEnum.REST);
        this.telemetry = tl;
        climbPos = ClimbEnum.REST;
    }

    @Override
    public void periodic() {
//        slideController.setF(NebulaConstants.Slide.slidePID.f * Math.cos(Math.toRadians(slideController.getSetPoint())));
        output = climberController.calculate(getEncoderDistance());
        setPower(output*multiplier);

        telemetry.addData("Climb Motor Output:", output);
        telemetry.addData("Climber Encoder: ", climber.getPosition());
        telemetry.addData("Climb SetPoint:", getSetPoint());
    }

    public double getEncoderDistance() {
//        return climber.getDistance();
        return climber.getPosition();
        //TODO:Does this work?
    }


    public void setPower(double power) {
        climber.setPower(power);
    }

    public void stopSlide() {
        climber.stop();
        climberController.setSetPoint(getEncoderDistance());
    }
    /****************************************************************************************/


    public void resetEncoder() {
        climber.resetEncoder();
    }


    public void setSetPoint(double setPoint) {
        //TODO: Maybe should remove all Safety Stuff
//        if(NebulaConstants.GamePad.overrideSafety){
//            if(setPoint>NebulaConstants.Climber.MAX_POSITION ||
//                setPoint<NebulaConstants.Climber.MIN_POSITION){
////                slideM1.stop();
//                return;
//            }
//        }

        climberController.setSetPoint(setPoint);
    }

    //TODO: Test!
    public Command setSetPointCommand(double setPoint) {
//        climbPos = ClimbEnum.MANUAL;    //WTH is this; The booleans don't match
        return new InstantCommand(()->{this.setSetPoint(setPoint);});
    }
    public Command setSetPointCommand(ClimbEnum pos) {
        if(pos==ClimbEnum.CLIMB){
            multiplier=2;
        } else if(pos==ClimbEnum.REST){
            multiplier=1;
        }
        return new InstantCommand(()->{setSetPoint(pos.climbPos);});
    }

    public double getSetPoint() {
        return climberController.getSetPoint();
    }
}