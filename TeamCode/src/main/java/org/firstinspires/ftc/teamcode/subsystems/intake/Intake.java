package org.firstinspires.ftc.teamcode.subsystems.intake;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;

import com.acmerobotics.roadrunner.Action;
import com.arcrobotics.ftclib.command.Command;
import com.arcrobotics.ftclib.command.ConditionalCommand;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.Subsystem;

import org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion;

import java.util.Set;

import kotlin.contracts.ConditionalEffect;

public class Intake {
    public enum Sample{
        RED,
        BLUE,
        YELLOW,
        NONE;
    }
    public enum Value implements Command {
        START (0,0,0),
        OUTTAKE (0,0,0),
        INTAKE  (0,0,0),
        POOP  (0,0,0),
        STOP    (0,0,0),
        HOLD    (0,0,0);


        public final double slidePos, servoPos, intakePower;
        Value(double slidePos, double servoPose, double intakePower) {
            this.slidePos = slidePos;
            this.servoPos = servoPose;
            this.intakePower = intakePower;
        }
        Value(double slidePos, Value value) {
            this.slidePos = value.slidePos;
            this.servoPos = value.servoPos;
            this.intakePower = value.intakePower;
        }

        @Override
        public Set<Subsystem> getRequirements() {
            return null;
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
//            case INTAKE:

            case OUTTAKE:
                return new InstantCommand();
            case HOLD:
                return new SequentialCommandGroup();
            default:
                return new SequentialCommandGroup(
                        new InstantCommand(()-> horizontalSlide.setSetPoint(value.slidePos)),
                        new InstantCommand(()-> intakeServo.setSetPoint(value.servoPos)),
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
