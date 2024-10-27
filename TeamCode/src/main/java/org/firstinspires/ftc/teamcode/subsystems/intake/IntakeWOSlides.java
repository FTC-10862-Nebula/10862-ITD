package org.firstinspires.ftc.teamcode.subsystems.intake;

import com.acmerobotics.roadrunner.Action;
import com.arcrobotics.ftclib.command.Command;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.Subsystem;

import org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion;

import java.util.Set;

public class IntakeWOSlides {
    public enum Sample{
        RED,
        BLUE,
        YELLOW,
        NONE;
    }
    public enum Value implements Command {
        START (0.90,0.90,0),
        OUTTAKE (0,0,-0.8),
        INTAKE  (0,0,0.9),
        POOP  (0.90,0.90,0),
        STOP    (0.90,0.90,0),
        HOLD    (0.90,0.90,0);


        public final double  rPos, lPos, intakePower;
        Value(double rPos, double lPos, double intakePower) {

            this.rPos = rPos;
            this.lPos=lPos;
            this.intakePower = intakePower;
        }
        Value(Value value) {

            this.rPos = value.rPos;
            this.lPos=value.lPos;
            this.intakePower = value.intakePower;
        }

        @Override
        public Set<Subsystem> getRequirements() {
            return null;
        }
    }
    public Value value = Value.START;

    public IntakeServo intakeServo;
    public PowerIntake powerIntake;

    public IntakeWOSlides(IntakeServo intakeServo, PowerIntake powerIntake){
        this.intakeServo = intakeServo;
        this.powerIntake = powerIntake;
    }

    public Command setPosition(Value value){
        switch(value) {
//            case INTAKE:

//            case OUTTAKE:
//                return new InstantCommand();
            default:
                return new SequentialCommandGroup(
                        new InstantCommand(()-> intakeServo.setSetPoint(value.rPos,value.lPos)),
                        new InstantCommand(()-> powerIntake.setSetPoint(value.intakePower))
                );
        }
    }
    public Action setPositionAction(Value value){
        return (Action) setPosition(value);
    }

    public void periodic(){
        BlocksOpModeCompanion.telemetry.addData("Intake Position:", value);
//        telemetry.addData("Slide SetPoint:" + horizontalSlide.getSetPoint()+"; Encoder: ", horizontalSlide.hSlide.getPosition());
//        telemetry.addData("Slide Motor Output:", horizontalSlide.output* horizontalSlide.multiplier);
//        telemetry.addData("Red:" + powerIntake.red() + "; Green:" + powerIntake.green() + "Blue:", powerIntake.blue());
//        telemetry.addData("IntakeServoPos: ", intakeServo.getPos());
    }
}
