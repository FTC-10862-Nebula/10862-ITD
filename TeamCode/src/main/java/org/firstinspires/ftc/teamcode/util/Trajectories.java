package org.firstinspires.ftc.teamcode.util;


import static org.firstinspires.ftc.teamcode.subsystems.drive.mec.DriveConstants.MAX_ACCEL;
import static org.firstinspires.ftc.teamcode.subsystems.drive.mec.DriveConstants.MAX_ANG_VEL;
import static org.firstinspires.ftc.teamcode.subsystems.drive.mec.DriveConstants.MAX_VEL;
import static org.firstinspires.ftc.teamcode.subsystems.drive.mec.DriveConstants.TRACK_WIDTH;

//import com.acmerobotics.roadrunner.trajectory.constraints.AngularVelocityConstraint;
//import com.acmerobotics.roadrunner.trajectory.constraints.MinVelocityConstraint;
//import com.acmerobotics.roadrunner.trajectory.constraints.ProfileAccelerationConstraint;
//import com.acmerobotics.roadrunner.trajectory.constraints.TankVelocityConstraint;

import com.acmerobotics.roadrunner.MinVelConstraint;
import com.acmerobotics.roadrunner.AngularVelConstraint;
import com.acmerobotics.roadrunner.ProfileAccelConstraint;

import java.util.Arrays;

public class Trajectories {

    public static MinVelConstraint velConstraint = new MinVelConstraint(Arrays.asList(
            new AngularVelConstraint(MAX_ANG_VEL),
            new TankVelocityConstraint(MAX_VEL, TRACK_WIDTH)
    ));
    public static MinVelocityConstraint kindaSlowVelConstraint = new MinVelConstraint(Arrays.asList(
            new AngularVelConstraint(MAX_ANG_VEL),
            new TankVelocityConstraint(MAX_VEL/1.3, TRACK_WIDTH)
    ));    public static MinVelConstraint slowVelConstraint = new MinVelConstraint(Arrays.asList(
            new AngularVelConstraint(MAX_ANG_VEL),
            new TankVelocityConstraint(MAX_VEL/1.4, TRACK_WIDTH)
    ));
    public static MinVelConstraint slowestVelConstraint = new MinVelConstraint(Arrays.asList(
            new AngularVelConstraint(MAX_ANG_VEL),
            new TankVelocityConstraint(MAX_VEL/10, TRACK_WIDTH)
    ));
    public static ProfileAccelConstraint accelConstraint = new ProfileAccelConstraint(1, MAX_ACCEL);
}