package org.firstinspires.ftc.teamcode.subsystems.drive.mec;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;

import com.acmerobotics.roadrunner.HolonomicController;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.TrajectoryBuilder;
import com.arcrobotics.ftclib.drivebase.MecanumDrive;
import com.arcrobotics.ftclib.hardware.motors.MotorEx;
import com.qualcomm.hardware.lynx.LynxModule;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.hardware.PIDCoefficients;
import com.qualcomm.robotcore.hardware.VoltageSensor;

import org.firstinspires.ftc.teamcode.util.misc.LynxModuleUtil;
import org.firstinspires.ftc.teamcode.util.trajectorysequence.TrajectorySequence;
import org.firstinspires.ftc.teamcode.util.trajectorysequence.TrajectorySequenceRunner;

import java.util.Arrays;
import java.util.List;


public class TestDrive{
    public static PIDCoefficients TRANSLATIONAL_PID = new PIDCoefficients(10, 0, 0); //6,1,0
    public static PIDCoefficients HEADING_PID = new PIDCoefficients(7, 0, 0);//6,0,0

    private final TrajectoryFollower follower = new HolonomicPIDVAFollower(TRANSLATIONAL_PID, TRANSLATIONAL_PID, HEADING_PID,
            new Pose2d(0.5, 0.5, Math.toRadians(5.0)), 0.5);;

    private final MotorEx leftFront = new MotorEx(hardwareMap, "leftFront"),
        leftRear = new MotorEx(hardwareMap, "leftRear"),
        rightRear = new MotorEx(hardwareMap, "rightRear"),
        rightFront = new MotorEx(hardwareMap, "rightFront");

    private final List<MotorEx> motors= Arrays.asList(leftFront, leftRear, rightRear, rightFront);
    private final MecanumDrive drive = new  MecanumDrive(leftFront, leftRear, rightRear, rightFront);

    private final IMU imu = hardwareMap.get(IMU.class, "imu");
    IMU.Parameters parameters = new IMU.Parameters(new RevHubOrientationOnRobot(
            DriveConstants.LOGO_FACING_DIR, DriveConstants.USB_FACING_DIR));
    private VoltageSensor batteryVoltageSensor;

    private final TrajectorySequenceRunner trajectorySequenceRunner = new TrajectorySequenceRunner(
            follower, HEADING_PID, batteryVoltageSensor,
            lastEncPositions, lastEncVels, lastTrackingEncPositions, lastTrackingEncVels
            );
    public TestDrive(){
        imu.initialize(parameters);

        leftFront.setInverted(true);
        leftRear.setInverted(true);
        rightRear.setInverted(false);
        rightFront.setInverted(false);

        LynxModuleUtil.ensureMinimumFirmwareVersion(hardwareMap);

        batteryVoltageSensor = hardwareMap.voltageSensor.iterator().next();

        for (LynxModule module : hardwareMap.getAll(LynxModule.class)) {
            module.setBulkCachingMode(LynxModule.BulkCachingMode.AUTO);
        }


        for (MotorEx motor : motors) {
            //Configurations
//            MotorConfigurationType motorConfigurationType = motor.motor.getMotorType();
//            motorConfigurationType.setAchieveableMaxRPMFraction(1.0);
//            motor.setMotorType(motorConfigurationType);
        }
    }

    public void robotCentric(double strafeSpeed, double forwardSpeed, double turnSpeed, boolean squareInputs){
        drive.driveRobotCentric(strafeSpeed, forwardSpeed, turnSpeed, squareInputs);
    }
    public void fieldCentric(double strafeSpeed, double forwardSpeed, double turnSpeed, boolean squareInputs){
        drive.driveFieldCentric(strafeSpeed, forwardSpeed, turnSpeed, imu.getRobotYawPitchRollAngles().getYaw(),squareInputs);
    }
    public void resetIMU(){
        imu.resetYaw();
    }

    public TrajectoryBuilder thing(){

    }

    public void followTrajectorySequenceAsync(TrajectorySequence trajectorySequence) {
        trajectorySequenceRunner.followTrajectorySequenceAsync(trajectorySequence);
    }
    public void stop() {
        drive.driveWithMotorPowers(0, 0, 0, 0);
    }
    public void setPoseEstimate(Pose2d pose) {
        drive.setPoseEstimate(pose);
    }
}
