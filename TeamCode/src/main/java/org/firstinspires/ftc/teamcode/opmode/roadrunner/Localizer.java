package org.firstinspires.ftc.teamcode.opmode.roadrunner;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.PoseVelocity2d;

public interface Localizer {
    //2290.75/73
    void setPose(Pose2d pose);
    
    Pose2d getPose();
    
    PoseVelocity2d update();
}
