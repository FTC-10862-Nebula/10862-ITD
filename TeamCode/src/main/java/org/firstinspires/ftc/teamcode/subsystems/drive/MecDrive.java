package org.firstinspires.ftc.teamcode.subsystems.drive;

import static org.firstinspires.ftc.teamcode.subsystems.drive.RoadrunnerMecanumDrive.lFNum;
import static org.firstinspires.ftc.teamcode.subsystems.drive.RoadrunnerMecanumDrive.lRNum;
import static org.firstinspires.ftc.teamcode.subsystems.drive.RoadrunnerMecanumDrive.rFNum;
import static org.firstinspires.ftc.teamcode.subsystems.drive.RoadrunnerMecanumDrive.rRNum;

import com.acmerobotics.roadrunner.Pose2d;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.HardwareMap;


public class MecDrive extends SubsystemBase {
    /**
     * Simple static field serving as a storage medium for the bot's pose.
     * This allows different classes/opmodes to set and read from a central source of truth.
     * A static field allows data to persist between opmodes.
     */
    public static Pose2d currentPose = new Pose2d(0,0,0);
    private final int LFVal = 0,
            LRVal = 1,
            RFVal = 2,
            RRVal = 3;
    double[] powers = new double[4];
    public final RoadrunnerMecanumDrive drivetrain;
    public MecDrive(HardwareMap hardwareMap){
        drivetrain =
                new RoadrunnerMecanumDrive(hardwareMap, new Pose2d(0,0,0));
//        MecanumDrive mecanumDrive = new MecanumDrive((Motor) drivetrain.leftFront,
//                (Motor) drivetrain.leftFront,
//                (Motor) drivetrain.leftFront,
//                (Motor) drivetrain.leftFront);
        //TODO: Test ^^
    }

    @Override
    public void periodic() {
        drivetrain.localizer.update();
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
        driveRobotCentric(strafeSpeed, forwardSpeed, turnSpeed);
    }

    /**
     * Drives the robot from the perspective of the driver. No matter the orientation of the
     * robot, pushing forward on the drive stick will always drive the robot away
     * from the driver.
     *
     * @param rx  the horizontal speed of the robot, derived from input
     * @param multiplier the vertical speed of the robot, derived from input
     * @param x    the turn speed of the robot, derived from input
     * @param y    the heading of the robot, derived from the gyro
     */
//    public void driveFieldCentric(double strafeSpeed, double forwardSpeed,
//                                  double turnSpeed, double gyroAngle) {
//
//        Vector2d input = new Vector2d(strafeSpeed, forwardSpeed);
//        input = input.rotateBy(-gyroAngle);
//
//        double theta = input.angle();
//
//        double[] wheelSpeeds = new double[4];
//        wheelSpeeds[lFNum] = Math.sin(theta + Math.PI / 4);
//        wheelSpeeds[lRNum] = Math.sin(theta - Math.PI / 4);
//        wheelSpeeds[rRNum] = Math.sin(theta - Math.PI / 4);
//        wheelSpeeds[rFNum] = Math.sin(theta + Math.PI / 4);
//
//        wheelSpeeds[lFNum] += turnSpeed;
//        wheelSpeeds[lRNum] -= turnSpeed;
//        wheelSpeeds[rRNum] += turnSpeed;
//        wheelSpeeds[rFNum] -= turnSpeed;
//
//        driveWithMotorPowers(
//                wheelSpeeds[lFNum],
//                wheelSpeeds[lRNum],
//                wheelSpeeds[rRNum],
//                wheelSpeeds[rFNum]
//        );
//    }
//
//    /**
//     * Drives the robot from the perspective of the driver. No matter the orientation of the
//     * robot, pushing forward on the drive stick will always drive the robot away
//     * from the driver.
//     *
//     * @param xSpeed       the horizontal speed of the robot, derived from input
//     * @param ySpeed       the vertical speed of the robot, derived from input
//     * @param turnSpeed    the turn speed of the robot, derived from input
//     * @param gyroAngle    the heading of the robot, derived from the gyro
//     * @param squareInputs Square the value of the input to allow for finer control
//     */
//    public void driveFieldCentric(double xSpeed, double ySpeed, double turnSpeed, double gyroAngle, boolean squareInputs) {
//        xSpeed = squareInputs ? (squareInput(xSpeed)) : (xSpeed);
//        ySpeed = squareInputs ? (squareInput(ySpeed)) : (ySpeed);
//        turnSpeed = squareInputs ? (squareInput(turnSpeed)) : (turnSpeed);
//
//        driveFieldCentric(xSpeed, ySpeed, turnSpeed, gyroAngle);
//    }
//    public void setPowers(double leftF, double leftR, double rightR, double rightF) {
//        drivetrain.setDrivePowers(leftR, leftF, rightR, rightF);
//    }
//
//    public void mecDrive(double y, double x, double rx) {
//        double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1);
//        powers [lFNum] = (y + x + rx) / denominator;    //fLPower
//        powers [lRNum] = (y - x + rx) / denominator;    //bLPower
//        powers [rFNum] = (y - x - rx) / denominator;    //fRPower
//        powers [rRNum] = (y + x - rx) / denominator;    //bRPower
//        drivetrain.setDrivePowers(powers[lFNum], powers[lRNum], powers[rFNum], powers[rRNum]);
//    }
    public void  driveFieldCentric(double y, double x, double rx, double multiplier){
//        double theta = -imu.getAngularOrientation().firstAngle;
       double theta = -drivetrain.getYaw();//Ok?

        double rotX = x * Math.cos(theta) - y * Math.sin(theta);
        double rotY = x * Math.sin(theta) + y * Math.cos(theta);
        double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1);

        // ^^^^^^ Denominator is the largest motor power (absolute value) or 1
        // This ensures all the powers maintain the same ratio, but only when
        // at least one is out of the range [-1, 1]

        powers [lFNum] = (rotY + rotX - rx) / denominator;
        powers [lRNum] = (rotY - rotX - rx) / denominator;
        powers [rFNum] = (rotY + rotX + rx) / denominator;
        powers [rRNum] = (rotY - rotX + rx) / denominator;


        if(Math.abs(powers[lFNum])<0.5&
                Math.abs(powers[lRNum])<0.5&
                Math.abs(powers[rFNum])<0.5&
                Math.abs(powers[rRNum])<0.5){
            for (int i = 0; i <= 3; i++) {
                powers[i] = squareInput(powers[i]);
//                powers[i] = cubeInput(powers[i]);
            }
        }
        drivetrain.setDrivePowers(powers[lFNum]* multiplier,
                powers[lRNum]* multiplier,
                powers[rFNum]* multiplier,
                powers[rRNum]* multiplier);

    }

    private double squareInput(double power) {
        return power * Math.abs(power);
    }
    private double cubeInput(double power) {
        return power*Math.abs(power)*Math.abs(power);
    }

//    public double getHeading() {
//        return Math.toDegrees(drivetrain.getExternalHeading());
//    }


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
        drivetrain.motors[lFNum].setPower(frontLeftSpeed);
        drivetrain.motors[lRNum].setPower(frontRightSpeed);
        drivetrain.motors[rRNum].setPower(backLeftSpeed);
        drivetrain.motors[rFNum].setPower(backRightSpeed);
    }

    public void stop() {
        driveWithMotorPowers(0,0,0,0);
    }
    public Pose2d getPose(){
        return new Pose2d(drivetrain.updatePoseEstimate().component1(),
                drivetrain.updatePoseEstimate().component2());
//        return drivetrain.updatePoseEstimate().component1();
    }
}
