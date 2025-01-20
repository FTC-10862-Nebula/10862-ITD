package org.firstinspires.ftc.teamcode.opmode.auto;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.ParallelAction;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.firstinspires.ftc.teamcode.subsystems.drive.RoadrunnerMecanumDrive;


@Config
@Autonomous
public class Test extends LinearOpMode {

    @Override
	public void runOpMode() throws InterruptedException {
		
		Pose2d startPose = new Pose2d(0, 0, Math.toRadians(0));
        // Initialize subsystems
        RoadrunnerMecanumDrive rrdrive = new RoadrunnerMecanumDrive(hardwareMap,startPose);

       // Wait for the start command
        waitForStart();
		while (!opModeIsActive()&&!isStopRequested()) {
			// Execute actions if the op mode is active
			if (opModeIsActive()) {
				Actions.runBlocking(
					rrdrive.actionBuilder(startPose)
						.strafeTo(new Vector2d(10, 10))
						.build());
			}
		}
    }
}
