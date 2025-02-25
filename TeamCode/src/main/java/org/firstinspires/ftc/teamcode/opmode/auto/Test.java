package org.firstinspires.ftc.teamcode.opmode.auto;

import static org.firstinspires.ftc.teamcode.subsystems.outtake.Outtake.Value.HIGH_BUCKET;
import static org.firstinspires.ftc.teamcode.subsystems.outtake.Outtake.Value.START;
import static java.lang.Math.toRadians;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.firstinspires.ftc.teamcode.subsystems.drive.RoadrunnerMecanumDrive;
import org.firstinspires.ftc.teamcode.subsystems.intake.HorizontalSlide;
import org.firstinspires.ftc.teamcode.subsystems.intake.Intake;
import org.firstinspires.ftc.teamcode.subsystems.intake.IntakeServo;
import org.firstinspires.ftc.teamcode.subsystems.intake.PowerIntake;
import org.firstinspires.ftc.teamcode.subsystems.outtake.Arm;
import org.firstinspires.ftc.teamcode.subsystems.outtake.Claw;
import org.firstinspires.ftc.teamcode.subsystems.outtake.Outtake;
import org.firstinspires.ftc.teamcode.subsystems.outtake.Pivot;
import org.firstinspires.ftc.teamcode.subsystems.outtake.Slide;

@Autonomous
public final class Test extends LinearOpMode {
	
	// Declare subsystems and key poses as class members
	private RoadrunnerMecanumDrive drive;
	
	// Define key poses (adjust as needed)
	private final Pose2d beginPose = new Pose2d(33, 62, toRadians(270));
//	private final Pose2d dropSample = new Pose2d(58, 54, toRadians(225));
//	private final Pose2d parkPose = new Pose2d(26, 10, toRadians(0));
//	private final Pose2d backOut = new Pose2d(40, 38, toRadians(90));
	
	@Override
	public void runOpMode() throws InterruptedException {
		robotInit();
		// Initialize subsystems
		// Pre-match loop: waiting for start
		while (!opModeIsActive() && !isStopRequested()) {
		
		}
		
		waitForStart();
		// Set the initial pose estimate
		if (opModeIsActive()) {
			
			// Execute a single trajectory using the syntax you want.
			Actions.runBlocking(
				drive.actionBuilder(beginPose)
					.strafeTo(new Vector2d(60, 58))
					.build());
		}
	}
	
	/**
	 * Initializes the drive, intake, and outtake subsystems.
	 */
	public void robotInit() {
		// Initialize the drive using the starting pose
		drive = new RoadrunnerMecanumDrive(hardwareMap, beginPose);
		
	}
}