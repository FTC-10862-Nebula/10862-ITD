package org.firstinspires.ftc.teamcode.subsystems.intake;

import static android.transition.Fade.IN;
import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;

import com.acmerobotics.roadrunner.Action;
import com.arcrobotics.ftclib.command.Command;
import com.arcrobotics.ftclib.command.ConditionalCommand;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.Subsystem;
import com.arcrobotics.ftclib.command.WaitCommand;

import org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion;
import org.firstinspires.ftc.robotcore.external.Telemetry;

import java.util.Set;

import kotlin.contracts.ConditionalEffect;

public class Intake {

    private final Telemetry telemetry;

    public static double DOWN = 0;
    public static double UP = 0.6;

    public enum Sample{
        RED,
        BLUE,
        YELLOW,
        NONE;
    }
    public enum Value implements Command {
        START (0,UP,UP,0),
        OUTTAKE (500,DOWN,DOWN,-0.5),
        INTAKE  (500,DOWN,DOWN,0.5),
        SUBINT (2500,DOWN,DOWN,0.5),
        SUBOUT(2500,DOWN,DOWN,0.5),
        STOP    (0,UP,UP,0);


        public final double slidePos, rPos, lPos, intakePower;
        Value(double slidePos, double rPos, double lPos, double intakePower) {
            this.slidePos = slidePos;
            this.rPos = rPos;
            this.lPos=lPos;
            this.intakePower = intakePower;
        }
        Value(double slidePos, Value value) {
            this.slidePos = value.slidePos;
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

    public HorizontalSlide horizontalSlide;
    public IntakeServo intakeServo;
    public PowerIntake powerIntake;

    public Intake(HorizontalSlide horizontalSlide, IntakeServo intakeServo, PowerIntake powerIntake, Telemetry telemetry){
        this.horizontalSlide = horizontalSlide;
        this.intakeServo = intakeServo;
        this.powerIntake = powerIntake;
        this.telemetry = telemetry;
    }

    public Command setPosition(Value value){
        switch(value) {
//                return new SequentialCommandGroup();
            default:
                return new SequentialCommandGroup(
                        new InstantCommand(()-> horizontalSlide.setSetPoint(value.slidePos)),
                        new InstantCommand(()-> intakeServo.setSetPoint(value.rPos,value.lPos)),
                        new InstantCommand(()-> powerIntake.setSetPoint(value.intakePower))
                );
            case OUTTAKE:
                return new SequentialCommandGroup(
                        new InstantCommand(()-> horizontalSlide.setSetPoint(value.slidePos)),
                        new InstantCommand(()-> intakeServo.setSetPoint(value.rPos,value.lPos)),
                        new WaitCommand(500),
                        new InstantCommand(()-> powerIntake.setSetPoint(value.intakePower))
                );
        }
    }
    public Action setPositionAction(Value value){
        return (Action) setPosition(value);
    }

    public void periodic(){
        telemetry.addData("Red:" + powerIntake.red() + "; Green:" + powerIntake.green() + "Blue:", powerIntake.blue());

    }
    public void init(){
        horizontalSlide.setSetPoint(0);
        intakeServo.setSetPoint(UP,UP);
        powerIntake.setSetPoint(0);
    }

}
