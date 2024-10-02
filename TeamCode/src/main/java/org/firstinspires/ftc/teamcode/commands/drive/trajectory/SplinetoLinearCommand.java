package org.firstinspires.ftc.teamcode.commands.drive.trajectory;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.acmerobotics.roadrunner.trajectory.TrajectoryBuilder;
import com.acmerobotics.roadrunner.trajectory.constraints.MinVelocityConstraint;
import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.subsystems.drive.mec.Drivetrain;
import org.firstinspires.ftc.teamcode.util.Trajectories;
import org.firstinspires.ftc.teamcode.util.PoseStorage;

@Config
public class SplinetoLinearCommand extends CommandBase{

    Drivetrain drive;
    Trajectory trajectory;
    boolean reverse;
    Pose2d splinePos;
    double endHeading;

    MinVelocityConstraint maxVelConstraint;
    public SplinetoLinearCommand(Drivetrain drive, MinVelocityConstraint constraint, Pose2d splinePos, double endHeading, boolean reverse) {
        this.drive = drive;
        this.maxVelConstraint = constraint;
        this.reverse = reverse;
        this.splinePos = splinePos;
        this.endHeading = endHeading;

        this.addRequirements(drive);
    }

    public SplinetoLinearCommand(Drivetrain drive, Pose2d splinePos, double endHeading) {
        this(drive, Trajectories.velConstraint, splinePos, endHeading, false);
    }

    public SplinetoLinearCommand(Drivetrain drive, Pose2d splinePos, double endHeading, boolean reverse) {
        this(drive, Trajectories.velConstraint, splinePos, endHeading, reverse);
    }


    @Override
    public void initialize() {
//        trajectory = new TrajectoryBuilder(drive.getPoseEstimate(), reverse, maxVelConstraint, Trajectories.accelConstraint)
//                .splineToSplineHeading(splinePos, endHeading)
//                .build();

        trajectory = new TrajectoryBuilder(PoseStorage.currentPose, reverse, maxVelConstraint, Trajectories.accelConstraint)
                .splineToLinearHeading(splinePos, endHeading)
                .build();
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