package org.firstinspires.ftc.teamcode.subsystems.drive.mec;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;

import com.acmerobotics.roadrunner.HolonomicController;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.TrajectoryBuilder;
import com.arcrobotics.ftclib.drivebase.MecanumDrive;
import com.arcrobotics.ftclib.hardware.motors.MotorEx;
import com.qualcomm.hardware.lynx.LynxModule;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.hardware.PIDCoefficients;
import com.qualcomm.robotcore.hardware.VoltageSensor;

import org.firstinspires.ftc.teamcode.util.misc.LynxModuleUtil;
import org.firstinspires.ftc.teamcode.util.trajectorysequence.TrajectorySequence;
import org.firstinspires.ftc.teamcode.util.trajectorysequence.TrajectorySequenceRunner;

import java.util.Arrays;
import java.util.List;


public class TestDrive{
    private final MotorEx leftFront = new MotorEx(hardwareMap, "leftFront"),
        leftRear = new MotorEx(hardwareMap, "leftRear"),
        rightRear = new MotorEx(hardwareMap, "rightRear"),
        rightFront = new MotorEx(hardwareMap, "rightFront");

    private final List<MotorEx> motors= Arrays.asList(leftFront, leftRear, rightRear, rightFront);
    private final MecanumDrive drive = new  MecanumDrive(leftFront, leftRear, rightRear, rightFront);


    private final IMU imu = hardwareMap.get(IMU.class, "imu");
    IMU.Parameters parameters = new IMU.Parameters(new RevHubOrientationOnRobot(
            DriveConstants.LOGO_FACING_DIR, DriveConstants.USB_FACING_DIR));
//    private VoltageSensor batteryVoltageSensor;

    public TestDrive(){
        imu.initialize(parameters);

        leftFront.setInverted(true);
        leftRear.setInverted(true);
        rightRear.setInverted(false);
        rightFront.setInverted(false);
    }

    public void robotCentric(double strafeSpeed, double forwardSpeed, double turnSpeed){
        drive.driveRobotCentric(strafeSpeed, forwardSpeed, turnSpeed, true);
    }
    public void fieldCentric(double strafeSpeed, double forwardSpeed, double turnSpeed){
        drive.driveFieldCentric(strafeSpeed, forwardSpeed, turnSpeed, imu.getRobotYawPitchRollAngles().getYaw(),true);
    }
    public void resetIMU(){
        imu.resetYaw();
    }
    public void stop() {
        drive.driveWithMotorPowers(0, 0, 0, 0);
    }
}
