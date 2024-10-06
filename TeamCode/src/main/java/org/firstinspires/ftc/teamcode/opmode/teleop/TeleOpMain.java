package org.firstinspires.ftc.teamcode.opmode.teleop;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.commands.manual.DefaultDriveCommand;
import org.firstinspires.ftc.teamcode.subsystems.Outtake.Claw;
import org.firstinspires.ftc.teamcode.subsystems.Outtake.Arm;
import org.firstinspires.ftc.teamcode.subsystems.climber.Climber;
import org.firstinspires.ftc.teamcode.subsystems.drive.MecDrive;
import org.firstinspires.ftc.teamcode.subsystems.intake.PowerIntake;
import org.firstinspires.ftc.teamcode.subsystems.intake.HorizontalSlide;
import org.firstinspires.ftc.teamcode.util.teleop.MatchOpMode;

@Config
@TeleOp
public class TeleOpMain extends MatchOpMode {
    //TODO: Add a on/off switch for drivetrain
    private GamepadEx driverGamepad, operatorGamepad;
    private MecDrive drive;
    private HorizontalSlide horizontalSlide;
    private PowerIntake intake;
    private Arm arm;
    private Claw claw;
    private Climber climb;
    public TeleOpMain() {}

    @Override
    public void robotInit() {
        driverGamepad = new GamepadEx(gamepad1);
        operatorGamepad = new GamepadEx(gamepad2);
        drive = new MecDrive();

        claw = new Claw(telemetry, hardwareMap, true);
        intake = new PowerIntake(telemetry, hardwareMap, true);
        climb = new Climber(telemetry,hardwareMap, true);
        arm = new Arm(telemetry, hardwareMap, true);
        horizontalSlide = new HorizontalSlide(telemetry, hardwareMap, true);
    }


    @Override
    public void configureButtons() {
        drive.setDefaultCommand(new DefaultDriveCommand(drive,driverGamepad,true));
//        Trigger OUTTAKE = (new GamepadTrigger(driverGamepad, GamepadKeys.Trigger.LEFT_TRIGGER)
//            .whenPressed(new InstantCommand(intake::setDown))
//            .whileHeld(intake.setSetPointCommand(PowerIntake.IntakePower.OUTTAKE)))
////                .whenPressed(cycleTracker.trackCycle())
//            .whenReleased(intake.setSetPointCommand(PowerIntake.IntakePower.STOP))
//            .whenReleased(new InstantCommand(intake::setUp));
//
////        Button climbDown  = (new GamepadButton(operatorGamepad, GamepadKeys.Button.DPAD_DOWN))
////                .whileHeld(climb.setSetPointCommand(Climber.ClimbEnum.REST));
//        climb.setDefaultCommand(new ClimberMoveManual(climb, operatorGamepad::getLeftY));

        //y - up/dowm
        //x- right left
    }

    @Override
    public void matchStart() {}
}
