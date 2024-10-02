package org.firstinspires.ftc.teamcode.util;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.Pose2d;

/**
 * Simple static field serving as a storage medium for the bot's pose.
 * This allows different classes/opmodes to set and read from a central source of truth.
 * A static field allows data to persist between opmodes.
 */
@Config
public class PoseStorage {
    public static Pose2d currentPose = new Pose2d(0,0,0);
    public static Pose2d trajectoryPose = new Pose2d(0,0,0);
}