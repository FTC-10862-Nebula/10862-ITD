package org.firstinspires.ftc.teamcode.util;


import static org.firstinspires.ftc.teamcode.subsystems.drive.mec.DriveConstants.MAX_ACCEL;
import static org.firstinspires.ftc.teamcode.subsystems.drive.mec.DriveConstants.MAX_ANG_VEL;
import static org.firstinspires.ftc.teamcode.subsystems.drive.mec.DriveConstants.MAX_VEL;
import static org.firstinspires.ftc.teamcode.subsystems.drive.mec.DriveConstants.TRACK_WIDTH;

import com.acmerobotics.roadrunner.trajectory.constraints.AngularVelocityConstraint;
import com.acmerobotics.roadrunner.trajectory.constraints.MinVelocityConstraint;
import com.acmerobotics.roadrunner.trajectory.constraints.ProfileAccelerationConstraint;
import com.acmerobotics.roadrunner.trajectory.constraints.TankVelocityConstraint;

import java.util.Arrays;

public class Trajectories {

    public static MinVelocityConstraint velConstraint = new MinVelocityConstraint(Arrays.asList(
            new AngularVelocityConstraint(MAX_ANG_VEL),
            new TankVelocityConstraint(MAX_VEL, TRACK_WIDTH)
    ));
    public static MinVelocityConstraint kindaSlowVelConstraint = new MinVelocityConstraint(Arrays.asList(
            new AngularVelocityConstraint(MAX_ANG_VEL),
            new TankVelocityConstraint(MAX_VEL/1.3, TRACK_WIDTH)
    ));    public static MinVelocityConstraint slowVelConstraint = new MinVelocityConstraint(Arrays.asList(
            new AngularVelocityConstraint(MAX_ANG_VEL),
            new TankVelocityConstraint(MAX_VEL/1.4, TRACK_WIDTH)
    ));
    public static MinVelocityConstraint slowestVelConstraint = new MinVelocityConstraint(Arrays.asList(
            new AngularVelocityConstraint(MAX_ANG_VEL),
            new TankVelocityConstraint(MAX_VEL/10, TRACK_WIDTH)
    ));


    public static ProfileAccelerationConstraint accelConstraint = new ProfileAccelerationConstraint(MAX_ACCEL);
}