package org.firstinspires.ftc.teamcode.commands.example;

import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
@Deprecated
public class ThreadCommand extends SequentialCommandGroup{
    public ThreadCommand(){
        addRequirements();    //Add Subsystems that you need to run this Command - not necessary
        addCommands(
            new InstantCommand( //Better Idea to use Parallel Command
                () -> new Thread(() -> {
//                    pivot.moveIntakeBAuto();
//                    slide.slideResting();
                }).start()
            )
//        new InstantCommand(
//                () -> new Thread(() -> {
////                            pivot.moveInitializationPosition();
//                    pivot.moveIntakeBAuto();
//                    slide.slideResting();
//                    turnServo.setFClawPos();
//                }).start()
//        )
        );
    }
}