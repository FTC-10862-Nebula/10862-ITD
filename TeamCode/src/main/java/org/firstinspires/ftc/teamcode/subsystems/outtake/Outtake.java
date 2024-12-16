package org.firstinspires.ftc.teamcode.subsystems.outtake;
import com.arcrobotics.ftclib.command.Command;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.ParallelCommandGroup;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;


public class Outtake {
    private Telemetry telemetry;

    //Arm
//    private static final double INTAKE =0.05,
//        SCORE_SAMPLE = 1,
//        SPECIMEN = 1;

    public enum Value{
        START   (0,0,0),
        LOW_BUCKET(1500,0.7,0.5),
        HIGH_BUCKET(3000,0.7,0.8),//3550
        SPECIMEN_WALL(150,1,0.2),
        LOW_RUNG(600,0.6,0.48),
        HIGH_RUNG(1700,0.6,0.5),
        SPECIMEN_LOW_BAR(LOW_RUNG.slidePos-600, LOW_RUNG),
        SPECIMEN_HIGH_BAR(HIGH_RUNG.slidePos-800, HIGH_RUNG),
        CLIMB(1500,0,1),
        TRANSFER(0,0,0);



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
        this.value=value;
        return new SequentialCommandGroup( //ParallelCommandGroup
                new InstantCommand(()-> verticalSlide.setSetPoint(value.slidePos)),
                new WaitCommand(500),
                new ParallelCommandGroup(
                    new InstantCommand(()-> arm.setSetPoint(value.armPos,value.armPos)),
                    new InstantCommand(()-> pivot.setSetPoint(value.pivotPos))
                )

        );
//        switch(value) {
//            default:
//                return new ParallelCommandGroup(
//                    new InstantCommand(()-> verticalSlide.setSetPoint(value.slidePos)),
//                    new InstantCommand(()-> arm.setSetPoint(value.armPos,value.armPos)),
//                    new InstantCommand(()-> pivot.setSetPoint(value.pivotPos))
//                );
//        }
    }

    public void periodic(){
        telemetry.addData("Slide Value:", value);
//        telemetry.addData("Outtake Position:", value);
//        telemetry.addData("SlideSetPoint:", verticalSlide.getSetPoint());
//        telemetry.addData("SlideR Encoder: ", verticalSlide.getEncoderDistance());
//        telemetry.addData("Slide Motor Output:", verticalSlide.output);
//        telemetry.addData("ArmR Pos: " + arm.getRPosition() +"; ArmLPos: "+ arm.getLPosition()+"; PivotPos:",pivot.getPosition());
//        telemetry.addData("Turn Servo: " + claw.getTurnPos() + "; Claw: ", claw.getClawPos());
    }

    public void init(){
        claw.setClawSetPoint(Claw.Value.CLOSE);
        pivot.setSetPoint(0);
        arm.setSetPoint(0,0);
        verticalSlide.resetEncoder();
        value= Value.START;
    }

    public Command setClawSetPoint(Claw.Value value) {
        return new InstantCommand(()->claw.setClawSetPoint(value));
    }
}


