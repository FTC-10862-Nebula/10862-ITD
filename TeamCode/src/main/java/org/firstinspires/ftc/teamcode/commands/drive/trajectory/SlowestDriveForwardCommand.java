package org.firstinspires.ftc.teamcode.commands.drive.trajectory;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.acmerobotics.roadrunner.trajectory.TrajectoryBuilder;
import com.acmerobotics.roadrunner.trajectory.constraints.MinVelocityConstraint;
import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.subsystems.drive.mec.Drivetrain;
import org.firstinspires.ftc.teamcode.util.Trajectories;
import org.firstinspires.ftc.teamcode.util.PoseStorage;

@Config
public class SlowestDriveForwardCommand extends CommandBase{

    Drivetrain drive;
    double distance;
    Trajectory trajectory;
    MinVelocityConstraint constraint;

    public SlowestDriveForwardCommand(Drivetrain drive, double distance) {
        this.drive = drive;
        this.distance = distance;
        constraint = Trajectories.slowestVelConstraint;
        this.addRequirements(drive);
    }

    public SlowestDriveForwardCommand(Drivetrain drive, double distance, MinVelocityConstraint constraint) {
        this.drive = drive;
        this.distance = distance;
        this.constraint = constraint;
        this.addRequirements(drive);
    }

    @Override
    public void initialize() {
//        if (distance > 0)
//            trajectory = new TrajectoryBuilder(drive.getPoseEstimate(), constraint, Trajectories.accelConstraint).forward(distance).build();
//        else
//            trajectory = new TrajectoryBuilder(drive.getPoseEstimate(), constraint, Trajectories.accelConstraint).back(-distance).build();

        if (distance > 0)
            trajectory = new TrajectoryBuilder(PoseStorage.currentPose, constraint, Trajectories.accelConstraint).forward(distance).build();
        else
            trajectory = new TrajectoryBuilder(PoseStorage.currentPose, constraint, Trajectories.accelConstraint).back(-distance).build();

        drive.followTrajectory(trajectory);

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
        PoseStorage.currentPose = trajectory.end();
        return !drive.isBusy();
    }
}