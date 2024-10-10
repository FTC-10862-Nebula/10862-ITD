package org.firstinspires.ftc.teamcode.subsystems.outtake;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;

import com.arcrobotics.ftclib.command.Command;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;


public class Outtake {
    private static final double OPEN =0.1, CLOSE =0;
    public enum Value{
        START   (0,0,0,0,CLOSE),
        OUTTAKE (0,0,0,0,OPEN),
        INTAKE  (0,0,0,0,CLOSE),
        STOP    (0,0,0,0,CLOSE),
        HOLD    (0,0,0,0,CLOSE);


        public final double slidePos, armLPos, armRPos, turnPos, clawPos;
        Value(double slidePos, double armLPos, double armRPos, double turnPos, double clawPos) {
            this.slidePos = slidePos;
            this.armLPos = armLPos;
            this.armRPos = armRPos;
            this.turnPos = turnPos;
            this.clawPos = clawPos;
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
            case OUTTAKE:
                return new InstantCommand();
            case START:
                return new SequentialCommandGroup();
            default:
                return new SequentialCommandGroup(
                    new InstantCommand(()-> verticalSlide.setSetPoint(value.slidePos)),
                    new InstantCommand(()-> arm.setSetPoint(value.armRPos,value.armLPos)),
                    new InstantCommand(()-> claw.setSetPoint(value.turnPos, value.clawPos))
                );
        }
    }

    public void periodic(){
        telemetry.addData("Outtake Position:", value);
        telemetry.addData("SlideSetPoint:"+ verticalSlide.getSetPoint()+ "; SlideR Encoder: ", verticalSlide.getEncoderDistance());
//        telemetry.addData("Slide Motor Output:", verticalSlide.output* verticalSlide.multiplier);
        telemetry.addData("ArmR Pos: " + arm.getRPosition() +"; ArmLPos: "+ arm.getLPosition()+"; PivotPos:",pivot.getPosition());
        telemetry.addData("Turn Servo: " + claw.getTurnPos() + "; Claw: ", claw.getClawPos());
    }
}
