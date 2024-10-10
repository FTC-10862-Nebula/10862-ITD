package org.firstinspires.ftc.teamcode.subsystems.outtake;

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

    public VerticalSlide verticalSlide;
    public Arm arm;
    public Claw claw;

    public Outtake(VerticalSlide verticalSlide, Arm arm, Claw claw){
        this.verticalSlide = verticalSlide;
        this.arm = arm;
        this.claw = claw;

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
}
