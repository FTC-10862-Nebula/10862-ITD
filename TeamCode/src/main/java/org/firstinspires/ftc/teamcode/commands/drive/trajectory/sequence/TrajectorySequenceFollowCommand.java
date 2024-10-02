package org.firstinspires.ftc.teamcode.commands.drive.trajectory.sequence;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.subsystems.drive.mec.Drivetrain;
import org.firstinspires.ftc.teamcode.util.PoseStorage;
import org.firstinspires.ftc.teamcode.util.trajectorysequence.TrajectorySequence;

public class TrajectorySequenceFollowCommand extends CommandBase {
    private final Drivetrain drive;
    private final TrajectorySequence trajectorySequence;
    public TrajectorySequenceFollowCommand(Drivetrain drive, TrajectorySequence trajectorySequence) {
        this.drive = drive;
        this.trajectorySequence = trajectorySequence;
    }

    @Override
    public void initialize() {
        drive.followTrajectorySequenceAsync(trajectorySequence);
    }

    @Override
    public void execute() {
        drive.update();
    }

    @Override
    public void end(boolean interrupted) {
        if (interrupted) {
            drive.stop();
        }
    }

    @Override
    public boolean isFinished() {
        PoseStorage.currentPose = trajectorySequence.end();
        return !drive.isBusy();
    }
}