package org.firstinspires.ftc.teamcode.util.trajectorysequence.container;

import com.acmerobotics.roadrunner.trajectory.constraints.TrajectoryAccelerationConstraint;
import com.acmerobotics.roadrunner.trajectory.constraints.TrajectoryVelocityConstraint;

public class TrajectorySequenceConstraints {
    public volatile TrajectoryVelocityConstraint baseVelConstraint;
    public volatile TrajectoryAccelerationConstraint baseAccelConstraint;
    public volatile double baseTurnConstraintMaxAngVel;
    public volatile double baseTurnConstraintMaxAngAccel;

    public TrajectorySequenceConstraints(
            TrajectoryVelocityConstraint baseVelConstraint,
            TrajectoryAccelerationConstraint baseAccelConstraint,
            double baseTurnConstraintMaxAngVel,
            double baseTurnConstraintMaxAngAccel
    ) {
        this.baseVelConstraint = baseVelConstraint;
        this.baseAccelConstraint = baseAccelConstraint;
        this.baseTurnConstraintMaxAngVel = baseTurnConstraintMaxAngVel;
        this.baseTurnConstraintMaxAngAccel = baseTurnConstraintMaxAngAccel;
    }

    public TrajectorySequenceConstraints(
            double baseVelConstraint,
            double baseAccelConstraint,
            double baseTurnConstraintMaxAngVel,
            double baseTurnConstraintMaxAngAccel
    ) {
        this.baseVelConstraint = (s, pose2d, pose2d1, pose2d2) -> baseVelConstraint;
        this.baseAccelConstraint = (s, pose2d, pose2d1, pose2d2) -> baseAccelConstraint;
        this.baseTurnConstraintMaxAngVel = baseTurnConstraintMaxAngVel;
        this.baseTurnConstraintMaxAngAccel = baseTurnConstraintMaxAngAccel;
    }
}
