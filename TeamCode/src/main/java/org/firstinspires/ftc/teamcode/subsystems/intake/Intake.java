package org.firstinspires.ftc.teamcode.subsystems.intake;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;

import com.arcrobotics.ftclib.command.Command;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;

public class Intake {
    public enum Value {
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

    public VerticalSlide verticalSlide;
    public IntakeServo intakeServo;
    public PowerIntake powerIntake;

    public Intake(VerticalSlide verticalSlide, IntakeServo intakeServo, PowerIntake powerIntake){
        this.verticalSlide = verticalSlide;
        this.intakeServo = intakeServo;
        this.powerIntake = powerIntake;

    }

    public Command setPosition(Value value){
        return new SequentialCommandGroup(
                new InstantCommand(()->verticalSlide.setSetPoint(value.slidePos)),
                new InstantCommand(()-> intakeServo.setSetPoint(value.servoPose)),
                new InstantCommand(()-> powerIntake.setSetPoint(value.intakePower))
        );
    }

    public Command intakePeriodic(){
        //TODO: Get the color of the colorSensor
        if(powerIntake.green()<45){
            //do this
            telemetry.speak("Got it!"); //TODO: Works?

        }
        return new InstantCommand();
    }
}
