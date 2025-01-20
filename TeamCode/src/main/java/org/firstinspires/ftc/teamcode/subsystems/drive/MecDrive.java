package org.firstinspires.ftc.teamcode.subsystems.drive;

import static org.firstinspires.ftc.teamcode.subsystems.drive.RoadrunnerMecanumDrive.lFNum;
import static org.firstinspires.ftc.teamcode.subsystems.drive.RoadrunnerMecanumDrive.lRNum;
import static org.firstinspires.ftc.teamcode.subsystems.drive.RoadrunnerMecanumDrive.rFNum;
import static org.firstinspires.ftc.teamcode.subsystems.drive.RoadrunnerMecanumDrive.rRNum;

import com.acmerobotics.roadrunner.Pose2d;
import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class MecDrive extends SubsystemBase {
    
    // Static field for storing the robot's pose across op modes
    public static Pose2d currentPose = new Pose2d(0, 0, 0);
    
    // Array to hold motor power values
    private final double[] powers = new double[4];
    
    // Drivetrain object
    public final RoadrunnerMecanumDrive drivetrain;
    
    public MecDrive(HardwareMap hardwareMap) {
        drivetrain = new RoadrunnerMecanumDrive(hardwareMap, new Pose2d(0, 0, 0));
    }
    
    @Override
    public void periodic() {
        drivetrain.localizer.update();
    }
    
    /**
     * Drives the robot in a field-centric manner.
     *
     * @param y          Forward/backward input (positive = forward)
     * @param x          Strafe input (positive = right)
     * @param rx         Rotation input (positive = clockwise)
     * @param multiplier A scaling factor for the overall motor power
     */
    public void driveFieldCentric(double y, double x, double rx, double multiplier) {
        // Get the robot's heading from the drivetrain (in radians)
        double theta = -Math.toRadians(drivetrain.getYaw()); // Negative for counterclockwise
        
        // Apply the field-centric transformation
        double rotX = x * Math.cos(theta) - y * Math.sin(theta);
        double rotY = x * Math.sin(theta) + y * Math.cos(theta);
        
        // Calculate the denominator for scaling
        double denominator = Math.max(Math.abs(rotY) + Math.abs(rotX) + Math.abs(rx), 1);
        
        // Calculate motor powers based on transformed inputs
        powers[lFNum] = (rotY + rotX - rx) / denominator;
        powers[lRNum] = (rotY - rotX - rx) / denominator;
        powers[rFNum] = (rotY - rotX + rx) / denominator;
        powers[rRNum] = (rotY + rotX + rx) / denominator;
        
        // Set motor powers with the multiplier applied
        drivetrain.setDrivePowers(
            powers[lFNum] * multiplier,
            powers[lRNum] * multiplier,
            powers[rFNum] * multiplier,
            powers[rRNum] * multiplier
        );
    }
    
    /**
     * Drives the motors directly with specified motor powers.
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
    
    /**
     * Stops the drivetrain motors.
     */
    public void stop() {
        driveWithMotorPowers(0, 0, 0, 0);
    }
}
