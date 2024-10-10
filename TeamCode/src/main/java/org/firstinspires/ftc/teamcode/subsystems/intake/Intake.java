package org.firstinspires.ftc.teamcode.subsystems.intake;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;

import com.acmerobotics.roadrunner.Action;
import com.arcrobotics.ftclib.command.Command;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;

public class Intake {
    public enum Value {
        START (0,0,0),
        OUTTAKE (0,0,0),
        INTAKE  (0,0,0),
        STOP    (0,0,0),
        HOLD    (0,0,0);


        public final double slidePos, servoPose, intakePower;
        Value(double slidePos, double servoPose, double intakePower) {
            this.slidePos = slidePos;
            this.servoPose = servoPose;
            this.intakePower = intakePower;
        }
    }
    public Value value = Value.START;

    public HorizontalSlide horizontalSlide;
    public IntakeServo intakeServo;
    public PowerIntake powerIntake;

    public Intake(HorizontalSlide horizontalSlide, IntakeServo intakeServo, PowerIntake powerIntake){
        this.horizontalSlide = horizontalSlide;
        this.intakeServo = intakeServo;
        this.powerIntake = powerIntake;
    }

    public Command setPosition(Value value){
        switch(value) {
            case OUTTAKE:
                return new InstantCommand();
            case HOLD:
                return new SequentialCommandGroup();
            default:
                return new SequentialCommandGroup(
                        new InstantCommand(()-> horizontalSlide.setSetPoint(value.slidePos)),
                        new InstantCommand(()-> intakeServo.setSetPoint(value.servoPose)),
                        new InstantCommand(()-> powerIntake.setSetPoint(value.intakePower))
                );
        }
    }
    public Action setPositionAction(Value value){
        return (Action) setPosition(value);
    }

    public Command intakePeriodic(){
        //TODO: Get the color of the colorSensor
        if(powerIntake.red()>255){
            telemetry.speak("Got it, RED!"); //TODO: Works?
        } else if(powerIntake.green()>255&&powerIntake.blue()>255){
            telemetry.speak("Got it, YELLOW!"); //TODO: Works?
        } else if(powerIntake.blue()>255){
            telemetry.speak("Got it, BLUE!"); //TODO: Works?
        }
        return new InstantCommand();
    }

    public void periodic(){
        telemetry.addData("Intake Position:", value);
        telemetry.addData("Slide SetPoint:" + horizontalSlide.getSetPoint()+"; Encoder: ", horizontalSlide.hSlide.getPosition());
        telemetry.addData("Slide Motor Output:", horizontalSlide.output* horizontalSlide.multiplier);
        telemetry.addData("Red:" + powerIntake.red() + "; Green:" + powerIntake.green() + "Blue:", powerIntake.blue());
        telemetry.addData("IntakeServoPos: ", intakeServo.getPos());
    }
}
