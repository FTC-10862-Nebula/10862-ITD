package org.firstinspires.ftc.teamcode.subsystems.outtake;
import com.arcrobotics.ftclib.command.Command;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.ParallelCommandGroup;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;

import org.firstinspires.ftc.robotcore.external.Telemetry;


public class Outtake {
    private final Telemetry telemetry;
    //Arm
//    private static final double INTAKE =0.05,
//        SCORE_SAMPLE = 1,
//        SPECIMEN = 1;

    public enum Value{
        START   (0,1,0.1),
        LOW_BUCKET(1500,0,0.17),
        HIGH_BUCKET(3550,0,0),
        SPECIMEN_WALL(120,0,0.2),
        LOW_RUNG(1000,0,0.2),
        HIGH_RUNG(1900,0,0.2),
        SPECIMEN_LOW_BAR(LOW_RUNG.slidePos-400, LOW_RUNG),
        SPECIMEN_HIGH_BAR(HIGH_RUNG.slidePos-400, HIGH_RUNG),
        CLIMB(1500,0,0);



        public final double slidePos, armPos, pivotPos;
        Value(double slidePos, double armPos, double pivotPos) {
            this.slidePos = slidePos;
            this.pivotPos = pivotPos;
            this.armPos = armPos;
        }
        Value(double slidePos, Value value){
            this.slidePos = slidePos;
            this.armPos = value.armPos;
            this.pivotPos = value.pivotPos;

        }
    }

    public Value value = Value.START;
    public VerticalSlide verticalSlide;
    public Arm arm;
    public Claw claw;
    public Pivot pivot;

    public Outtake(VerticalSlide verticalSlide, Arm arm, Claw claw, Pivot pivot, Telemetry telemetry){
        this.verticalSlide = verticalSlide;
        this.arm = arm;
        this.claw = claw;
        this.pivot = pivot;
        this.telemetry = telemetry;
    }

    public Command setPosition(Value value){
        this.value=value;
        switch(value) {
            case LOW_RUNG:
            case HIGH_RUNG:
            case HIGH_BUCKET:
            case LOW_BUCKET:
                return new SequentialCommandGroup(
                    new InstantCommand(()-> verticalSlide.setSetPoint(value.slidePos)),
                    new WaitCommand(300),
                    new InstantCommand(()-> arm.setSetPoint(value.armPos,value.armPos)),
                    new InstantCommand(()-> pivot.setSetPoint(value.pivotPos))
                );
            case SPECIMEN_WALL:
                return new SequentialCommandGroup(
                        new InstantCommand(()->verticalSlide.setSetPoint(500)),
                        new WaitCommand(700),
                        new InstantCommand(()-> arm.setSetPoint(value.armPos,value.armPos)),
                        new InstantCommand(()-> pivot.setSetPoint(value.pivotPos)),
                        new WaitCommand(500),
                        new InstantCommand(()->verticalSlide.setSetPoint(value.slidePos))
                );
            case SPECIMEN_LOW_BAR:
            case SPECIMEN_HIGH_BAR:
            default:
                return new ParallelCommandGroup(
                    new InstantCommand(()-> verticalSlide.setSetPoint(value.slidePos)),
                    new InstantCommand(()-> arm.setSetPoint(value.armPos,value.armPos)),
                    new InstantCommand(()-> pivot.setSetPoint(value.pivotPos))
                );
        }
    }

    public void periodic(){
        telemetry.addData("Outtake Position:", value);
        telemetry.addData("SlideSetPoint:", verticalSlide.getSetPoint());
        telemetry.addData("SlideR Encoder: ", verticalSlide.getEncoderDistance());
        telemetry.addData("Slide Motor Output:", verticalSlide.output* verticalSlide.multiplier);
//        telemetry.addData("ArmR Pos: " + arm.getRPosition() +"; ArmLPos: "+ arm.getLPosition()+"; PivotPos:",pivot.getPosition());
//        telemetry.addData("Turn Servo: " + claw.getTurnPos() + "; Claw: ", claw.getClawPos());
    }

    public void init(){
        verticalSlide.setSetPoint(0);
        claw.setClawSetPoint(Claw.Value.CLOSE);
        arm.setSetPoint(0.05,0.05);
        pivot.setSetPoint(0);
        value= Value.START;
    }

//    public boolean getIfHigh(){return (value==Outtake.Value.HIGH_RUNG);
//    }

    public Command setClawSetPoint(Claw.Value value) {
        return new InstantCommand(()->claw.setClawSetPoint(value));
    }
}


