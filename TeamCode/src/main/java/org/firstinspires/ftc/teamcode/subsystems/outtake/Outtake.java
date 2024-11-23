package org.firstinspires.ftc.teamcode.subsystems.outtake;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;

import com.arcrobotics.ftclib.command.Command;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.ParallelCommandGroup;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;

public class Outtake {
    //Claw
    private static final double OPEN_SAMPLE =0.162 ,
            CLOSE_SAMPLE =0.356,
            OPEN_SPECIMEN =CLOSE_SAMPLE,
            CLOSE_SPECIMEN =OPEN_SAMPLE;

    //Turn
    private static final double INTAKE_SPECIMEN_POS =0.9,
        OUTTAKE_POS = 0.44,
        FLIP_POS = 0,
        INTAKE_SAMPLE_POS = 0;//Similar to FLIP


    public enum Value{
        START   (0,0,0,INTAKE_SPECIMEN_POS, CLOSE_SAMPLE,0.9),
        INTAKE_SAMPLE(0,0,0,1, CLOSE_SAMPLE,1),
//        STOP    (0,0,0,0,CLOSE),
//        HOLD    (0,0,0,0,CLOSE),
        READY_TO_INTAKE_SAMPLE(0,0,0,1, OPEN_SAMPLE,0.9),
        LOW_BUCKET(1500,1,1,0.44, CLOSE_SAMPLE,0.1),
        HIGH_BUCKET(3600,1,1,0.44, CLOSE_SAMPLE,0.1),
        OUTTAKE_BUCKET(0,1,1,0.44, OPEN_SAMPLE,0.1),



        READY_TO_INTAKE_SPECIMEN(0,0,0,INTAKE_SPECIMEN_POS, OPEN_SPECIMEN,0.26),
        INTAKE_SPECIMEN(0,0,0,INTAKE_SPECIMEN_POS, CLOSE_SPECIMEN,0.26),
        LOW_RUNG(0,0,0,INTAKE_SPECIMEN_POS, CLOSE_SPECIMEN,0),
        HIGH_RUNG(0,0,0,INTAKE_SPECIMEN_POS, CLOSE_SPECIMEN,0),
        SPECIMEN_LOW_BAR(LOW_RUNG.slidePos-10, LOW_RUNG, OPEN_SAMPLE),
        SPECIMEN_HIGH_BAR(HIGH_RUNG.slidePos-10, HIGH_RUNG, OPEN_SAMPLE),

        OUTTAKE_SPECIMEN_BAR(0,0,0,0.9, OPEN_SAMPLE,0.26),



        CLIMB_LOW_RUNG(0,0,0,1, CLOSE_SAMPLE,0),
        CLIMB_UP(0,0,0,1, CLOSE_SAMPLE,0.9);




        public final double slidePos, armLPos, armRPos, turnPos, clawPos, pivotPos;
        Value(double slidePos, double armLPos, double armRPos, double turnPos, double clawPos, double pivotPos) {
            this.slidePos = slidePos;
            this.pivotPos = pivotPos;
            this.armLPos = armLPos;
            this.armRPos = armRPos;
            this.turnPos = turnPos;
            this.clawPos = clawPos;
        }
        Value(double slidePos, Value value, double clawPos){
            this.slidePos = slidePos;
            this.armLPos = value.armLPos;
            this.armRPos = value.armRPos;
            this.turnPos = value.turnPos;
            this.clawPos = clawPos;
            this.pivotPos = value.pivotPos;

        }
    }

    public Value value = Value.START;
    public VerticalSlide verticalSlide;
    public Arm arm;
    public Claw claw;
    public Pivot pivot;

    public Outtake(VerticalSlide verticalSlide, Arm arm, Claw claw, Pivot pivot){
        this.verticalSlide = verticalSlide;
        this.arm = arm;
        this.claw = claw;
        this.pivot=pivot;
    }

    public Command setPosition(Value value){
        switch(value) {
            case SPECIMEN_LOW_BAR:
            case SPECIMEN_HIGH_BAR:
                return new SequentialCommandGroup(
                    new InstantCommand(()-> verticalSlide.setSetPoint(value.slidePos)),
                    new WaitCommand(100),
                    new InstantCommand(()-> arm.setSetPoint(value.armRPos,value.armLPos)),
                        new InstantCommand(()-> pivot.setSetPoint(value.pivotPos)),
                        new InstantCommand(()-> claw.setSetPoint(value.turnPos, value.clawPos))
                );
            case OUTTAKE_BUCKET:
                return new SequentialCommandGroup(
                        new InstantCommand(()-> claw.setSetPoint(value.turnPos, value.clawPos)));
            default:
                return new ParallelCommandGroup(
                    new InstantCommand(()-> verticalSlide.setSetPoint(value.slidePos)),
                    new InstantCommand(()-> arm.setSetPoint(value.armRPos,value.armLPos)),
                    new InstantCommand(()-> pivot.setSetPoint(value.pivotPos)),
                    new InstantCommand(()-> claw.setSetPoint(value.turnPos, value.clawPos))
                );
        }
    }

    public void periodic(){
        telemetry.addData("Outtake Position:", value);
        telemetry.addData("SlideSetPoint:", verticalSlide.getSetPoint());
        telemetry.addData("SlideR Encoder: ", verticalSlide.getEncoderDistance());
//        telemetry.addData("Slide Motor Output:", verticalSlide.output* verticalSlide.multiplier);
        telemetry.addData("ArmR Pos: " + arm.getRPosition() +"; ArmLPos: "+ arm.getLPosition()+"; PivotPos:",pivot.getPosition());
        telemetry.addData("Turn Servo: " + claw.getTurnPos() + "; Claw: ", claw.getClawPos());
    }

    public void init(){
        verticalSlide.setSetPoint(0);
        claw.setSetPoint(1,0.162);
        arm.setSetPoint(0,0);
        pivot.setSetPoint(0.9);

    }
}
