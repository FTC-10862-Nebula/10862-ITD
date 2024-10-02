package org.firstinspires.ftc.teamcode.subsystems.drive.mec;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;
import static org.firstinspires.ftc.teamcode.util.NebulaConstants.squareInput;

import com.acmerobotics.roadrunner.Pose2d;
import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.geometry.Vector2d;


public class MecDrive extends SubsystemBase {
    private final RoadrunnerMecanumDrive mecDrive =
            new RoadrunnerMecanumDrive(hardwareMap, new Pose2d(0,0,0));
    public MecDrive(){
    }

    /**
     * Drives the robot from the perspective of the robot itself rather than that
     * of the driver.
     *
     * @param strafeSpeed  the horizontal speed of the robot, derived from input
     * @param forwardSpeed the vertical speed of the robot, derived from input
     * @param turnSpeed    the turn speed of the robot, derived from input
     */
    public void driveRobotCentric(double strafeSpeed, double forwardSpeed, double turnSpeed) {
        driveFieldCentric(strafeSpeed, forwardSpeed, turnSpeed, 0.0);
    }

    /**
     * Drives the robot from the perspective of the robot itself rather than that
     * of the driver.
     *
     * @param strafeSpeed  the horizontal speed of the robot, derived from input
     * @param forwardSpeed the vertical speed of the robot, derived from input
     * @param turnSpeed    the turn speed of the robot, derived from input
     * @param squareInputs Square joystick inputs for finer control
     */
    public void driveRobotCentric(double strafeSpeed, double forwardSpeed, double turnSpeed, boolean squareInputs) {
        strafeSpeed = squareInputs ? (squareInput(strafeSpeed)) : (strafeSpeed);
        forwardSpeed = squareInputs ? (squareInput(forwardSpeed)) : (forwardSpeed);
        turnSpeed = squareInputs ? (squareInput(turnSpeed)) : (turnSpeed);

        driveRobotCentric(strafeSpeed, forwardSpeed, turnSpeed);
    }

    /**
     * Drives the robot from the perspective of the driver. No matter the orientation of the
     * robot, pushing forward on the drive stick will always drive the robot away
     * from the driver.
     *
     * @param strafeSpeed  the horizontal speed of the robot, derived from input
     * @param forwardSpeed the vertical speed of the robot, derived from input
     * @param turnSpeed    the turn speed of the robot, derived from input
     * @param gyroAngle    the heading of the robot, derived from the gyro
     */
    public void driveFieldCentric(double strafeSpeed, double forwardSpeed,
                                  double turnSpeed, double gyroAngle) {

        Vector2d input = new Vector2d(strafeSpeed, forwardSpeed);
        input = input.rotateBy(-gyroAngle);

        double theta = input.angle();

        double[] wheelSpeeds = new double[4];
        wheelSpeeds[RoadrunnerMecanumDrive.lFNum] = Math.sin(theta + Math.PI / 4);
        wheelSpeeds[RoadrunnerMecanumDrive.lRNum] = Math.sin(theta - Math.PI / 4);
        wheelSpeeds[RoadrunnerMecanumDrive.rRNum] = Math.sin(theta - Math.PI / 4);
        wheelSpeeds[RoadrunnerMecanumDrive.rFNum] = Math.sin(theta + Math.PI / 4);

        wheelSpeeds[RoadrunnerMecanumDrive.lFNum] += turnSpeed;
        wheelSpeeds[RoadrunnerMecanumDrive.lRNum] -= turnSpeed;
        wheelSpeeds[RoadrunnerMecanumDrive.rRNum] += turnSpeed;
        wheelSpeeds[RoadrunnerMecanumDrive.rFNum] -= turnSpeed;

        driveWithMotorPowers(
                wheelSpeeds[RoadrunnerMecanumDrive.lFNum],
                wheelSpeeds[RoadrunnerMecanumDrive.lRNum],
                wheelSpeeds[RoadrunnerMecanumDrive.rRNum],
                wheelSpeeds[RoadrunnerMecanumDrive.rFNum]
        );
    }

    /**
     * Drives the robot from the perspective of the driver. No matter the orientation of the
     * robot, pushing forward on the drive stick will always drive the robot away
     * from the driver.
     *
     * @param xSpeed       the horizontal speed of the robot, derived from input
     * @param ySpeed       the vertical speed of the robot, derived from input
     * @param turnSpeed    the turn speed of the robot, derived from input
     * @param gyroAngle    the heading of the robot, derived from the gyro
     * @param squareInputs Square the value of the input to allow for finer control
     */
    public void driveFieldCentric(double xSpeed, double ySpeed, double turnSpeed, double gyroAngle, boolean squareInputs) {
        xSpeed = squareInputs ? (squareInput(xSpeed)) : (xSpeed);
        ySpeed = squareInputs ? (squareInput(ySpeed)) : (ySpeed);
        turnSpeed = squareInputs ? (squareInput(turnSpeed)) : (turnSpeed);

        driveFieldCentric(xSpeed, ySpeed, turnSpeed, gyroAngle);
    }

    /**
     * Drives the motors directly with the specified motor powers.
     *
     * @param frontLeftSpeed    the speed of the front left motor
     * @param frontRightSpeed   the speed of the front right motor
     * @param backLeftSpeed     the speed of the back left motor
     * @param backRightSpeed    the speed of the back right motor
     */
    public void driveWithMotorPowers(double frontLeftSpeed, double frontRightSpeed,
                                     double backLeftSpeed, double backRightSpeed) {
        mecDrive.motors[RoadrunnerMecanumDrive.lFNum].setPower(frontLeftSpeed);
        mecDrive.motors[RoadrunnerMecanumDrive.lRNum].setPower(frontRightSpeed);
        mecDrive.motors[RoadrunnerMecanumDrive.rRNum].setPower(backLeftSpeed);
        mecDrive.motors[RoadrunnerMecanumDrive.rFNum].setPower(backRightSpeed);
    }

    public void setPose(){
        mecDrive.updatePoseEstimate();
    }
}
