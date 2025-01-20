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
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion;
import org.firstinspires.ftc.robotcore.external.Telemetry;

import java.util.Set;

import kotlin.contracts.ConditionalEffect;

public class Intake {

    private Telemetry telemetry;

    public static double DOWN = 0;
    public static double UP = 0.7;
    public static double INIT = 0.95;
    
    
    public enum Sample{
        RED,
        BLUE,
        YELLOW,
        NONE;
    }
    
    public enum Value implements Command {
        START (0, INIT,0),
        OUTTAKE (500, DOWN,-0.6),
        INTAKE  (1000, DOWN,0.65),
        STOP    (0,UP,0),
        SAMPLE(250,UP,0);
        



        public final double horizontalSlide, rPos, lPos, intakePower;
        Value(double horizontalSlide, double rPos, double intakePower) {
            this.horizontalSlide = horizontalSlide;
            this.rPos = rPos;
            this.lPos=rPos;
            this.intakePower = intakePower;
        }
//        Value(double slidePower, Value value) {
//            this.slidePower = value.slidePower;
//            this.rPos = value.rPos;
//            this.lPos=value.lPos;
//            this.intakePower = value.intakePower;
//        }

        @Override
        public Set<Subsystem> getRequirements() {
            return null;
        }
    }
    public Value value = Value.START;
    public IntakeServo intakeServo;
    public PowerIntake powerIntake;
    public HorizontalSlide horizontalSlide;

    public Intake(HorizontalSlide horizontalSlide, IntakeServo intakeServo, PowerIntake powerIntake, Telemetry telemetry){
        this.horizontalSlide = horizontalSlide;
        this.intakeServo = intakeServo;
        this.powerIntake = powerIntake;
        this.telemetry = telemetry;
    }

    public Command setPosition(Value value){
        switch(value) {
            case OUTTAKE:
                return new SequentialCommandGroup(
                        new InstantCommand(()-> horizontalSlide.setSetPoint(value.horizontalSlide)),
                        new InstantCommand(()-> intakeServo.setSetPoint(value.rPos,value.lPos)),
                    new WaitCommand(500),
                    new InstantCommand(()-> powerIntake.setSetPoint(value.intakePower))
                );
            default:
                return new SequentialCommandGroup(
                        new InstantCommand(()-> horizontalSlide.setSetPoint(value.horizontalSlide)),
                        new InstantCommand(()-> intakeServo.setSetPoint(value.rPos,value.lPos)),
                        new InstantCommand(()-> powerIntake.setSetPoint(value.intakePower))
                );
            
        }
    }
    public Action setPositionAction(Value value){
        return (Action) setPosition(value);
    }

    public void periodic(){
//        telemetry.addData("Red:", powerIntake.red());
//        telemetry.addData("Green:" , powerIntake.green());
//        telemetry.addData("Blue:", powerIntake.blue());
    }
    public void init(){
        intakeServo.setSetPoint(INIT,INIT);
        powerIntake.setSetPoint(0);
        horizontalSlide.setPower(0);
    }
    public Command hslidePower(HorizontalSlide.Value value){
        return new InstantCommand(()->horizontalSlide.setSetPoint(value.pos));
    }
}
