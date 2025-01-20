package org.firstinspires.ftc.teamcode.opmode.teleop;

import static org.firstinspires.ftc.teamcode.subsystems.intake.Intake.Value.INTAKE;
import static org.firstinspires.ftc.teamcode.subsystems.intake.Intake.Value.OUTTAKE;
import static org.firstinspires.ftc.teamcode.subsystems.intake.Intake.Value.STOP;
import static org.firstinspires.ftc.teamcode.subsystems.outtake.Claw.Value.CLOSE;
import static org.firstinspires.ftc.teamcode.subsystems.outtake.Claw.Value.OPEN;
import static org.firstinspires.ftc.teamcode.subsystems.outtake.Outtake.Value.HIGH_BUCKET;
import static org.firstinspires.ftc.teamcode.subsystems.outtake.Outtake.Value.HIGH_RUNG;
import static org.firstinspires.ftc.teamcode.subsystems.outtake.Outtake.Value.LOW_BUCKET;
import static org.firstinspires.ftc.teamcode.subsystems.outtake.Outtake.Value.LOW_RUNG;
import static org.firstinspires.ftc.teamcode.subsystems.outtake.Outtake.Value.SAMPLE_WAIT;
import static org.firstinspires.ftc.teamcode.subsystems.outtake.Outtake.Value.SPECIMEN_WALL;

import android.graphics.Path;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.Pose2d;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.ParallelCommandGroup;
import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;
import com.arcrobotics.ftclib.command.WaitUntilCommand;
import com.arcrobotics.ftclib.command.button.Button;
import com.arcrobotics.ftclib.command.button.GamepadButton;
import com.arcrobotics.ftclib.command.button.Trigger;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.commands.ColorIntakeCommand;
import org.firstinspires.ftc.teamcode.commands.ScoreCommand;
import org.firstinspires.ftc.teamcode.commands.manual.DefaultDriveCommand;
import org.firstinspires.ftc.teamcode.commands.manual.SlideHorizontalManual;
import org.firstinspires.ftc.teamcode.commands.manual.SlidePowerHorizontalManual;
import org.firstinspires.ftc.teamcode.commands.manual.SlideVerticalManual;
import org.firstinspires.ftc.teamcode.subsystems.drive.MecDrive;
import org.firstinspires.ftc.teamcode.subsystems.drive.RoadrunnerMecanumDrive;
import org.firstinspires.ftc.teamcode.subsystems.intake.HorizontalSlide;
import org.firstinspires.ftc.teamcode.subsystems.intake.Intake;
import org.firstinspires.ftc.teamcode.subsystems.intake.IntakeServo;
import org.firstinspires.ftc.teamcode.subsystems.intake.PowerIntake;
import org.firstinspires.ftc.teamcode.subsystems.intake.SensorColor;
import org.firstinspires.ftc.teamcode.subsystems.outtake.Arm;
import org.firstinspires.ftc.teamcode.subsystems.outtake.Claw;
import org.firstinspires.ftc.teamcode.subsystems.outtake.Outtake;
import org.firstinspires.ftc.teamcode.subsystems.outtake.Pivot;
import org.firstinspires.ftc.teamcode.subsystems.outtake.Slide;
import org.firstinspires.ftc.teamcode.util.teleop.GamepadTrigger;
import org.firstinspires.ftc.teamcode.util.teleop.MatchOpMode;


@Config
@TeleOp
public class TeleOpMain extends MatchOpMode {
	//TODO: Add a on/off switch for drivetrain
	private GamepadEx driverGamepad, operatorGamepad;
	
	private Outtake outtake;
	private MecDrive drive;
	//    private Climber climb;
	private Intake intake;
	private SensorColor sensorColor;
	
	public TeleOpMain() {
	}
	
	@Override
	public void robotInit() {
		drive = new MecDrive(hardwareMap);
		driverGamepad = new GamepadEx(gamepad1);
		operatorGamepad = new GamepadEx(gamepad2);
		sensorColor = new SensorColor(telemetry, hardwareMap);
		
		
		intake = new Intake(
			new HorizontalSlide(telemetry, hardwareMap, false),
			new IntakeServo(telemetry, hardwareMap, false),
			new PowerIntake(telemetry, hardwareMap, false),
			telemetry
		);
		outtake = new Outtake(
			new Slide(telemetry, hardwareMap, true),
			new Arm(telemetry, hardwareMap, true),
			new Claw(telemetry, hardwareMap, true),
			new Pivot(telemetry, hardwareMap, true),
			telemetry
		);
		
		intake.init();
		outtake.init();
	}
	
	@Override
	public void robotPeriodic() {
		outtake.periodic();
		intake.periodic();
	}
	
	@Override
	public void configureButtons() {
		
		      drive.setDefaultCommand(new DefaultDriveCommand(drive, driverGamepad));
    //INTAKE
		Trigger IntakeOuttake = (new GamepadTrigger(driverGamepad, GamepadKeys.Trigger.LEFT_TRIGGER))
			.whenHeld(intake.setPosition(INTAKE))
			.whenReleased(intake.setPosition(STOP));
		Trigger IntakeIntake = (new GamepadTrigger(driverGamepad, GamepadKeys.Trigger.RIGHT_TRIGGER))
			.whenHeld(intake.setPosition(OUTTAKE))
			.whenReleased(intake.setPosition(STOP));
		
    //OUTTAKE
		
		//BUCKET
		Button LowBucket = (new GamepadButton(operatorGamepad, GamepadKeys.Button.X)
			.whenPressed(outtake.setClawSetPoint(CLOSE))
			.whenPressed(outtake.setPosition(LOW_BUCKET)));
		
		Button HighBucket = (new GamepadButton(operatorGamepad, GamepadKeys.Button.Y)
			.whenPressed(outtake.setClawSetPoint(CLOSE)))
			.whenPressed(outtake.setPosition(HIGH_BUCKET));
		
        //SPECIMEN
		Button IntakeSpecimen = (new GamepadButton(operatorGamepad, GamepadKeys.Button.LEFT_BUMPER)
			.whenPressed(outtake.setClawSetPoint(OPEN))
			.whenPressed(outtake.setPosition(SPECIMEN_WALL)));
		Button HighRung = (new GamepadButton(operatorGamepad, GamepadKeys.Button.DPAD_UP)
			.whenPressed(outtake.setClawSetPoint(CLOSE))
			.whenPressed(outtake.setPosition(HIGH_RUNG)));
		Button lowRung = (new GamepadButton(operatorGamepad, GamepadKeys.Button.DPAD_LEFT)
			.whenPressed(outtake.setClawSetPoint(CLOSE))
			.whenPressed(outtake.setPosition(LOW_RUNG)));
		
		Button reset = (new GamepadButton(operatorGamepad, GamepadKeys.Button.DPAD_DOWN)
			.whenPressed(outtake.setPosition(SAMPLE_WAIT)));
		
		Button score = (new GamepadButton(operatorGamepad, GamepadKeys.Button.RIGHT_BUMPER)
			.whenPressed(new ScoreCommand(outtake)));
		
		Trigger specimenWall = (new GamepadTrigger(operatorGamepad, GamepadKeys.Trigger.RIGHT_TRIGGER))
			.whenPressed(outtake.setClawSetPoint(CLOSE));
		Trigger forSpecimen = (new GamepadTrigger(operatorGamepad, GamepadKeys.Trigger.LEFT_TRIGGER)
			.whenPressed(outtake.setClawSetPoint(OPEN)));
	//SLIDE MANUALS
		outtake.verticalSlide.setDefaultCommand(new SlideVerticalManual(
			outtake.verticalSlide, operatorGamepad::getRightY));
//        intake.horizontalSlide.setDefaultCommand(new SlideHorizontalManual(intake.horizontalSlide,
//                operatorGamepad::getLeftX));

//        Trigger Climb =(new GamepadButton(operatorGamepad,GamepadKeys.Button.DPAD_DOWN)
//                .whenPressed(outtake.setPosition(START)));
		
		outtake.claw.setDefaultCommand(new ColorIntakeCommand(intake,outtake, sensorColor, Intake.Sample.RED));
		

	}
	
	
	@Override
	public void matchStart() {
	}
	
}