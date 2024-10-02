package org.firstinspires.ftc.teamcode.commands.drive.trajectory;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.acmerobotics.roadrunner.trajectory.TrajectoryBuilder;
import com.acmerobotics.roadrunner.trajectory.constraints.MinVelocityConstraint;
import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.subsystems.drive.mec.Drivetrain;
import org.firstinspires.ftc.teamcode.util.Trajectories;
import org.firstinspires.ftc.teamcode.util.PoseStorage;

@Config
public class SplineCommand extends CommandBase{

    Drivetrain drive;
    Trajectory trajectory;
    boolean reverse;
    Vector2d splinePos;
    double endHeading;
    Pose2d poseToUse;

    MinVelocityConstraint maxVelConstraint;
    public SplineCommand(Drivetrain drive, MinVelocityConstraint constraint, boolean reverse, Vector2d splinePos, double endHeading, Pose2d poseToUse) {
        this.drive = drive;
        this.reverse = reverse;
        this.splinePos = splinePos;
        this.endHeading = endHeading;
        this.maxVelConstraint = constraint;
        this.poseToUse=poseToUse;
        this.addRequirements(drive);

    }

    public SplineCommand(Drivetrain drive, Vector2d splinePos, double endHeading) {
        this(drive, Trajectories.velConstraint, false, splinePos, endHeading, PoseStorage.currentPose);
    }

    public SplineCommand(Drivetrain drive, Vector2d splinePos, double endHeading, boolean reverse) {
        this(drive, Trajectories.velConstraint, reverse, splinePos, endHeading, PoseStorage.currentPose);
    }

    public SplineCommand(Drivetrain drive, Vector2d splinePos, double endHeading, Pose2d poseToUse) {
        this(drive, Trajectories.velConstraint, false, splinePos, endHeading, poseToUse);
    }

    public SplineCommand(Drivetrain drive, Vector2d splinePos, double endHeading, Pose2d poseToUse, boolean reverse) {
        this(drive, Trajectories.velConstraint, reverse, splinePos, endHeading, poseToUse);
    }


    @Override
    public void initialize() {
//        trajectory = new TrajectoryBuilder
//                (drive.getPoseEstimate(), reverse, maxVelConstraint, Trajectories.accelConstraint)
//                .splineTo(splinePos, endHeading)
//                .build();

        trajectory = new TrajectoryBuilder
                (PoseStorage.currentPose, reverse, maxVelConstraint, Trajectories.accelConstraint)
                .splineTo(splinePos, endHeading)
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
//        new ResetPoseCommand(drive, splinePos, endHeading);
//        PoseStorage.currentPose = new Pose2d(splinePos.getX(), splinePos.getY(), endHeading);
        PoseStorage.currentPose = trajectory.end();

        return !drive.isBusy();
    }
}