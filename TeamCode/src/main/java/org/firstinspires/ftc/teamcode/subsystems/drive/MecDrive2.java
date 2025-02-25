package org.firstinspires.ftc.teamcode.subsystems.drive;

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

public class MecDrive2 {
	private final DcMotor leftFront, leftBack, rightFront, rightBack;
	public final IMU imu;
	private final Gamepad driver1;
	private final Gamepad driver2;
	private final HardwareMap hardwareMap;
	private final Telemetry telemetry;
	
	private double speed = 1.0; // Default speed
	
	public MecDrive2(OpMode opMode) {
		driver1 = opMode.gamepad1;
		driver2 = opMode.gamepad2;
		hardwareMap = opMode.hardwareMap;
		telemetry = opMode.telemetry;
		
		
		imu = hardwareMap.get(IMU.class, "imu");
		leftFront = hardwareMap.get(DcMotor.class, "FL");
		leftBack = hardwareMap.get(DcMotor.class, "BL");
		rightFront = hardwareMap.get(DcMotor.class, "FR");
		rightBack = hardwareMap.get(DcMotor.class, "BR");
		
		leftFront.setDirection(DcMotorSimple.Direction.REVERSE);
		leftBack.setDirection(DcMotorSimple.Direction.REVERSE);
		rightFront.setDirection(DcMotorSimple.Direction.FORWARD);
		rightBack.setDirection(DcMotorSimple.Direction.FORWARD);
		
		rightBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
		leftBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
		rightFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
		leftFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
		
		IMU.Parameters parameters = new IMU.Parameters(new RevHubOrientationOnRobot(
			RevHubOrientationOnRobot.LogoFacingDirection.UP,
			RevHubOrientationOnRobot.UsbFacingDirection.LEFT));
		imu.initialize(parameters);
	}
	
	public void setSpeed(double speed) {
		this.speed = speed;
	}
	
	public void fieldCentric() {
		// Read joystick inputs
		double y = -driver1.left_stick_y; // Reversed because joystick forward is negative
		double x = driver1.left_stick_x * 1.1; // Counteract imperfect strafing
		double rx = driver1.right_stick_x;
		
//
		
		// Reset yaw if the START button is pressed
		if (driver1.start) {
			imu.resetYaw();
		}
		if (driver1.left_bumper){
			setSpeed(0.75);
		} else setSpeed(1.0);
		
		// Get the robot's current heading in radians
		double botHeading = imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.RADIANS);
		
		// Rotate the joystick input to match the field's coordinate system
		double rotX = x * Math.cos(-botHeading) - y * Math.sin(-botHeading);
		double rotY = x * Math.sin(-botHeading) + y * Math.cos(-botHeading);
		
		// Normalize and calculate motor powers
		double denominator = Math.max(Math.abs(rotY) + Math.abs(rotX) + Math.abs(rx), 1.0);
		double frontLeftPower = (rotY + rotX + rx) / denominator * speed;
		double backLeftPower = (rotY - rotX + rx) / denominator * speed;
		double frontRightPower = (rotY - rotX - rx) / denominator * speed;
		double backRightPower = (rotY + rotX - rx) / denominator * speed;
		
		// Set motor powers
		leftFront.setPower(frontLeftPower);
		leftBack.setPower(backLeftPower);
		rightFront.setPower(frontRightPower);
		rightBack.setPower(backRightPower);
		
		telemetry.addData("rF", rightFront.getCurrentPosition());
		telemetry.addData("rR", rightBack.getCurrentPosition());
		telemetry.addData("lF", leftFront.getCurrentPosition());
		telemetry.addData("lR", leftBack.getCurrentPosition());
	}
}