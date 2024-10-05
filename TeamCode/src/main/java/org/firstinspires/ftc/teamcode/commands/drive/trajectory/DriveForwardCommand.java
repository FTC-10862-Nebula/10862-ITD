package org.firstinspires.ftc.teamcode.commands.drive.trajectory;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.MinVelConstraint;
import com.acmerobotics.roadrunner.Trajectory;
import com.acmerobotics.roadrunner.TrajectoryBuilder;
import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.subsystems.drive.MecDrive;
import org.firstinspires.ftc.teamcode.util.Trajectories;
import org.firstinspires.ftc.teamcode.util.PoseStorage;

@Config
public class DriveForwardCommand extends CommandBase{

    MecDrive drive;
    double distance;
    Trajectory trajectory;
    MinVelConstraint constraint;
    public DriveForwardCommand(MecDrive drive, double distance) {
        this.drive = drive;
        this.distance = distance;
        constraint = Trajectories.velConstraint;
        this.addRequirements(drive);
//        new DriveForwardCommand(drive, distance, Trajectories.velConstraint);
    }

    public DriveForwardCommand(MecDrive drive, double distance, MinVelConstraint constraint) {
        this.drive = drive;
        this.distance = distance;
        this.constraint = constraint;
        this.addRequirements(drive);
    }

    @Override
    public void initialize() {
//        if (distance < 0)
//            trajectory = new TrajectoryBuilder
//                    (drive.getPoseEstimate(), constraint, Trajectories.accelConstraint).back(-distance).build();
//        else
//            trajectory = new TrajectoryBuilder
//                    (drive.getPoseEstimate(), constraint, Trajectories.accelConstraint).forward(distance).build();

        if (distance < 0)
            trajectory = new TrajectoryBuilder
                    (PoseStorage.currentPose, constraint, Trajectories.accelConstraint).back(-distance).build();
        else
            trajectory = new TrajectoryBuilder
                    (PoseStorage.currentPose, constraint, Trajectories.accelConstraint).forward(distance).build();

        drive.followTrajectory(trajectory);

    }

    @Override
    public void execute() {

    }

    @Override
    public void end(boolean interrupted) {
        if (interrupted) {
            drive.stop();
        }
    }

    @Override
    public boolean isFinished() {
        PoseStorage.currentPose = drive.getPose();
        return !drive.isBusy();
    }
}