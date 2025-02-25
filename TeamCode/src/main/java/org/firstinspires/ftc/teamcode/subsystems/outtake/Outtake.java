package org.firstinspires.ftc.teamcode.subsystems.outtake;
import com.arcrobotics.ftclib.command.Command;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.ParallelCommandGroup;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class Outtake {
    private Telemetry telemetry;
    
    //Arm
    private static final double
        ARM_SAMPLE = 0.7,
        ARM_INTAKE =0.16,
        ARM_HOLD = 0.2,
        ARM_SPECIMEN = 0;
    
    
    private static final double
        PIVOT_SAMPLE = .5,
        PIVOT_INTAKE =0.25,
        PIVOT_SPECIMENWALL = 0,
        PIVOT_SPECIMEN= 0.7;
    
    
    
    public enum Value{
        START(0,ARM_INTAKE,PIVOT_INTAKE),
        SAMPLE_WAIT(0,ARM_HOLD,PIVOT_INTAKE),
        OUTTAKE_SAMPLE(0,ARM_SAMPLE,PIVOT_INTAKE),
        LOW_BUCKET(1000,ARM_SAMPLE,PIVOT_SAMPLE),
        HIGH_BUCKET(3620,ARM_SAMPLE,PIVOT_SAMPLE),
        SPECIMEN_WALL(400,ARM_SPECIMEN, PIVOT_SPECIMENWALL),
        LOW_RUNG(220,ARM_SAMPLE,PIVOT_SPECIMEN),
        HIGH_RUNG(1600,ARM_SAMPLE,PIVOT_SPECIMEN),
        SPECIMEN_LOW_BAR(LOW_RUNG.slidePos-200,
            ARM_SAMPLE,PIVOT_SPECIMEN),
        SPECIMEN_HIGH_BAR(HIGH_RUNG.slidePos-200,
            ARM_SAMPLE,PIVOT_SPECIMEN),
        CLIMB(3600,ARM_INTAKE,PIVOT_INTAKE);
        
        
        
        public final double slidePos, armPos, pivotPos;
        Value(double slidePos, double armPos, double pivotPos) {
            this.slidePos = slidePos;
            this.pivotPos = pivotPos;
            this.armPos = armPos;
        }
//        Value(double slidePos, Value value){
//            this.slidePos = slidePos;
//            this.armPos = value.armPos;
//            this.pivotPos = value.pivotPos;
//        }
    }
    
    public Value value;
    public Slide verticalSlide;
    public Arm arm;
    public Claw claw;
    public Pivot pivot;
    
    public Outtake(Slide verticalSlide, Arm arm, Claw claw, Pivot pivot, Telemetry telemetry){
        this.verticalSlide = verticalSlide;
        this.arm = arm;
        this.claw = claw;
        this.pivot = pivot;
        this.telemetry = telemetry;
    }
    
    public Command setPosition(Value value){
        switch (value){
            case SPECIMEN_LOW_BAR:
            case SPECIMEN_HIGH_BAR:
                return new SequentialCommandGroup(
                    new ParallelCommandGroup(
                        new InstantCommand(()-> arm.setSetPoint(value.armPos,value.armPos)),
                        new InstantCommand(()-> pivot.setSetPoint(value.pivotPos)),
                        new InstantCommand(()-> verticalSlide.setSetPoint(value.slidePos)),
                        setClawSetPoint(Claw.Value.OPEN)),
                    new InstantCommand(()->setValue(value))
                );
            case SPECIMEN_WALL:
                return  new SequentialCommandGroup(
                    new InstantCommand(()-> verticalSlide.setSetPoint(600)),
                    new WaitCommand(1000),
                    new InstantCommand(()-> arm.setSetPoint(value.armPos,value.armPos)),
                    new InstantCommand(()-> pivot.setSetPoint(value.pivotPos)),
                    new InstantCommand(()-> verticalSlide.setSetPoint(value.slidePos)),
                    setClawSetPoint(Claw.Value.OPEN)
                );
            case START:
            case SAMPLE_WAIT:
                return new SequentialCommandGroup(
                    new ParallelCommandGroup(
                        new InstantCommand(()-> arm.setSetPoint(value.armPos,value.armPos)),
                        new InstantCommand(()-> pivot.setSetPoint(value.pivotPos))
                    ),
                    new WaitCommand(500),
                    new InstantCommand(()-> verticalSlide.setSetPoint(value.slidePos)),
                    new InstantCommand(()->setValue(value))
                );
            default:
                return new SequentialCommandGroup(
                    new ParallelCommandGroup(
                        new InstantCommand(()-> verticalSlide.setSetPoint(value.slidePos)),
                        new InstantCommand(()-> arm.setSetPoint(value.armPos,value.armPos)),
                        new InstantCommand(()-> pivot.setSetPoint(value.pivotPos))),
                    new InstantCommand(()->setValue(value))
                );
        }
    }
    
    public void setValue(Value value){
        this.value = value;
    }
    public void periodic(){
        telemetry.addData("Slide Value:", value);
    }
    
    public void init(){
        claw.setClawSetPoint(Claw.Value.OPEN);
        pivot.setSetPoint(Value.START.pivotPos);
        arm.setSetPoint(Value.SAMPLE_WAIT.armPos,Value.SAMPLE_WAIT.armPos);
        verticalSlide.resetEncoder();
        value = Value.START;
    }
    
    public Command setClawSetPoint(Claw.Value value) {
        return new InstantCommand(()->claw.setClawSetPoint(value));
    }
}