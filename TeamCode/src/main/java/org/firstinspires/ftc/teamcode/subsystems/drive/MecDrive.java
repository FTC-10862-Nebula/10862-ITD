package org.firstinspires.ftc.teamcode.subsystems.drive;

import static org.firstinspires.ftc.teamcode.subsystems.drive.RoadrunnerMecanumDrive.lFNum;
import static org.firstinspires.ftc.teamcode.subsystems.drive.RoadrunnerMecanumDrive.lRNum;
import static org.firstinspires.ftc.teamcode.subsystems.drive.RoadrunnerMecanumDrive.rFNum;
import static org.firstinspires.ftc.teamcode.subsystems.drive.RoadrunnerMecanumDrive.rRNum;

import com.acmerobotics.roadrunner.Pose2d;
import com.arcrobotics.ftclib.geometry.Vector2d;
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
//    private final int LFVal = 0,
//            LRVal = 1,
//            RFVal = 2,
//            RRVal = 3;
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


    public void  driveFieldCentric(double y, double x, double rx, double multiplier){
//        double theta = -imu.getAngularOrientation().firstAngle;
//       double theta = -drivetrain.getYaw();//Ok?
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


//        if(Math.abs(powers[lFNum])<0.5&
//                Math.abs(powers[lRNum])<0.5&
//                Math.abs(powers[rFNum])<0.5&
//                Math.abs(powers[rRNum])<0.5){
//            for (int i = 0; i <= 3; i++) {
//                powers[i] = squareInput(powers[i]);
////                powers[i] = cubeInput(powers[i]);
//            }
//        }
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
        drivetrain.motors[lRNum].setPower(backLeftSpeed);
        drivetrain.motors[rRNum].setPower(backRightSpeed);
        drivetrain.motors[rFNum].setPower(frontRightSpeed);
    }

    public void stop() {
        driveWithMotorPowers(0,0,0,0);
    }
    public Pose2d getPose(){
        return new Pose2d(drivetrain.updatePoseEstimate().component1(),
                drivetrain.updatePoseEstimate().component2());
    }
}
