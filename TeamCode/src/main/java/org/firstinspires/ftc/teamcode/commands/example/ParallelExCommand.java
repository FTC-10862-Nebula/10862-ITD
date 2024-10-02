package org.firstinspires.ftc.teamcode.commands.example;

import com.arcrobotics.ftclib.command.ParallelCommandGroup;
@Deprecated

public class ParallelExCommand extends ParallelCommandGroup{
    public ParallelExCommand(){
        addRequirements();    //Add Subsystems that you need to run this Command
        addCommands(
            //Commands that will run automatically - Each subsystem can only be used once
            //Can also be implemented like below and/or by extending ParallelCommandGroup
            new ParallelCommandGroup(
                    //Commands
            )
        );
    }
}