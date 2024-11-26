package org.firstinspires.ftc.teamcode.subsystems.outtake;

import static com.arcrobotics.ftclib.gamepad.GamepadKeys.Button.BACK;
import static org.firstinspires.ftc.robotcore.external.hardware.camera.BuiltinCameraDirection.FRONT;

import com.arcrobotics.ftclib.command.Command;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.ParallelCommandGroup;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class Outtake {
    private final Telemetry telemetry;
    //Arm
    private static final double INTAKE =0.05,
        SCORE_SAMPLE = 1,
        SPECIMEN = 1;

    //Turn
    private static final double INTAKET =0.635,
        OUTTAKET = 0.285,
        FLIP_POS = 0.635;//Similar to FLIP

    public enum Value{
        START   (0,0.05,INTAKET,0.1),

        INTAKE_SAMPLE(0,0.05,INTAKET,0),
//        STOP    (0,0,0,0,CLOSE),
//        HOLD    (0,0,0,0,CLOSE),
        READY_TO_INTAKE_SAMPLE(0,0.05,FLIP_POS,0.1),
        LOW_BUCKET(1500,1, OUTTAKET,0.17),
        HIGH_BUCKET(3600,1, OUTTAKET,0.17),
//        OUTTAKE_BUCKET(0,1,OUTTAKE_POS,0.17),



        READY_TO_INTAKE_SPECIMEN(500,1, OUTTAKET,0.26),
        INTAKE_SPECIMEN(500,1, OUTTAKET,0.26),
        LOW_RUNG(1200,1, OUTTAKET,0),
        HIGH_RUNG(2500,1, OUTTAKET,0),
        SPECIMEN_LOW_BAR(LOW_RUNG.slidePos-10, LOW_RUNG),
        SPECIMEN_HIGH_BAR(HIGH_RUNG.slidePos-10, HIGH_RUNG),

//        OUTTAKE_SPECIMEN_BAR(0,1,INTAKE_SPECIMEN_POS,0.26),



        CLIMB_LOW_RUNG(0,0,1,0),
        CLIMB_UP(0,0,1,0.9);




        public final double slidePos, armPos, turnPos, pivotPos;
        Value(double slidePos, double armPos, double turnPos, double pivotPos) {
            this.slidePos = slidePos;
            this.pivotPos = pivotPos;
            this.armPos = armPos;
            this.turnPos = turnPos;
        }
        Value(double slidePos, Value value){
            this.slidePos = slidePos;
            this.armPos = value.armPos;
            this.turnPos = value.turnPos;
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
        this.pivot=pivot;
        this.telemetry = telemetry;
    }

    public Command setPosition(Value value){
        this.value=value;
        switch(value) {
            case SPECIMEN_LOW_BAR:
            case SPECIMEN_HIGH_BAR:
                return new SequentialCommandGroup(
                    new InstantCommand(()-> verticalSlide.setSetPoint(value.slidePos)),
                    new WaitCommand(100),
                    new InstantCommand(()-> arm.setSetPoint(value.armPos,value.armPos)),
                    new InstantCommand(()-> pivot.setSetPoint(value.pivotPos)),
                    new InstantCommand(()-> claw.setTurnSetPoint(value.turnPos))
                );
            default:
                return new ParallelCommandGroup(
                    new InstantCommand(()-> verticalSlide.setSetPoint(value.slidePos)),
                    new InstantCommand(()-> arm.setSetPoint(value.armPos,value.armPos)),
                    new InstantCommand(()-> pivot.setSetPoint(value.pivotPos)),
                    new InstantCommand(()-> claw.setTurnSetPoint(value.turnPos))
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
        claw.setTurnSetPoint(0.635);
        claw.setClawSetPoint(Claw.Value.CLOSE_SAMPLE);
        arm.setSetPoint(0.05,0.05);
        pivot.setSetPoint(0.1);
        value= Value.START;
    }

    public boolean getIfHigh(){
        return (value==Outtake.Value.HIGH_RUNG);
    }
}
