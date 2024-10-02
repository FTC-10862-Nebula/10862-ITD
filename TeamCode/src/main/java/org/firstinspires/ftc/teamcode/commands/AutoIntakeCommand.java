package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.ParallelCommandGroup;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;
import com.arcrobotics.ftclib.command.WaitUntilCommand;

import org.firstinspires.ftc.teamcode.commands.drive.trajectory.sequence.DisplacementCommand;
import org.firstinspires.ftc.teamcode.commands.drive.trajectory.sequence.MarkerCommand;
import org.firstinspires.ftc.teamcode.commands.drive.trajectory.sequence.TrajectorySequenceContainerFollowCommand;
import org.firstinspires.ftc.teamcode.opmode.auto.Speed;
import org.firstinspires.ftc.teamcode.subsystems.Claw;
import org.firstinspires.ftc.teamcode.subsystems.drive.mec.Drivetrain;
import org.firstinspires.ftc.teamcode.subsystems.intake.PowerIntake;
import org.firstinspires.ftc.teamcode.subsystems.sensor.SensorColor;
import org.firstinspires.ftc.teamcode.util.trajectorysequence.container.Back;
import org.firstinspires.ftc.teamcode.util.trajectorysequence.container.Forward;
import org.firstinspires.ftc.teamcode.util.trajectorysequence.container.StrafeLeft;
import org.firstinspires.ftc.teamcode.util.trajectorysequence.container.TrajectorySequenceContainer;

public class AutoIntakeCommand extends SequentialCommandGroup{
    public AutoIntakeCommand(Claw claw, PowerIntake intake, SensorColor sensorColor, Drivetrain drivetrain){
        addCommands(
                intake.setSetPointCommand(PowerIntake.IntakePower.AUTO_INTAKE),
                new WaitUntilCommand(sensorColor::grabbedFrontPixel).withTimeout(2600),
                claw.setBothClaw(Claw.ClawPos.CLOSE_POS),
                new ParallelCommandGroup(
                    new TrajectorySequenceContainerFollowCommand(drivetrain,
                        new TrajectorySequenceContainer(Speed::getFastConstraints,
          //                  new StrafeLeft(27),
        //                    new Back(120)),
                                new Forward(0.1)),

        new DisplacementCommand(5,
                            intake.setSetPointCommand(PowerIntake.IntakePower.AUTO_OUTTAKE)))
//                    intake.setSetPointCommand(PowerIntake.IntakePower.AUTO_OUTTAKE)
                ),
//                new WaitCommand(2000),
                intake.setSetPointCommand(PowerIntake.IntakePower.STOP)
        );
    }
}