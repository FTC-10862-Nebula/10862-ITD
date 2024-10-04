package org.firstinspires.ftc.teamcode.opmode.teleop;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.button.Button;
import com.arcrobotics.ftclib.command.button.GamepadButton;
import com.arcrobotics.ftclib.command.button.Trigger;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.commands.ClimberMoveManual;
import org.firstinspires.ftc.teamcode.commands.arm.position.ResetCommand;
import org.firstinspires.ftc.teamcode.commands.arm.position.SlideCommand;
import org.firstinspires.ftc.teamcode.commands.arm.slide.SlideMoveManual;
import org.firstinspires.ftc.teamcode.subsystems.Claw;
import org.firstinspires.ftc.teamcode.subsystems.Arm;
import org.firstinspires.ftc.teamcode.subsystems.climber.Climber;
import org.firstinspires.ftc.teamcode.subsystems.intake.PowerIntake;
import org.firstinspires.ftc.teamcode.subsystems.SensorColor;
import org.firstinspires.ftc.teamcode.subsystems.intake.HorizontalSlide;
import org.firstinspires.ftc.teamcode.util.teleop.GamepadTrigger;
import org.firstinspires.ftc.teamcode.util.teleop.MatchOpMode;

@Config
@TeleOp
public class TeleOpMain extends MatchOpMode {
    //TODO: Add a on/off switch for drivetrain
    private GamepadEx driverGamepad, operatorGamepad;
    private HorizontalSlide horizontalSlide;
    private PowerIntake intake;
    private Arm arm;
    private Claw claw;
    private Climber climb;
   private SensorColor sensorColor;
    public TeleOpMain() {}

    @Override
    public void robotInit() {
        driverGamepad = new GamepadEx(gamepad1);
        operatorGamepad = new GamepadEx(gamepad2);

        claw = new Claw(telemetry, hardwareMap, true);
        intake = new PowerIntake(telemetry, hardwareMap, true);
        climb = new Climber(telemetry,hardwareMap, true);
        arm = new Arm(telemetry, hardwareMap, true);
        horizontalSlide = new HorizontalSlide(telemetry, hardwareMap, true);
        sensorColor = new SensorColor(telemetry, hardwareMap);
    }


    @Override
    public void configureButtons() {
        //Claw
        Button closeF = (new GamepadTrigger(operatorGamepad, GamepadKeys.Trigger.LEFT_TRIGGER)
                .whenPressed(claw.setBothClaw(Claw.ClawPos.CLOSE_POS)));

        Button closeB= (new GamepadTrigger(operatorGamepad,  GamepadKeys.Trigger.RIGHT_TRIGGER)
                .whenPressed(claw.setBothClaw(Claw.ClawPos.OPEN_POS)));

//      Button openF = (new GamepadButton(operatorGamepad, GamepadKeys.Button.LEFT_BUMPER)
//               .whenPressed(claw.setFClaw(Claw.ClawPos.OPEN_POS)));
        Button openB= (new GamepadButton(operatorGamepad,  GamepadKeys.Button.RIGHT_BUMPER)
               .whenPressed(claw.setFClaw(Claw.ClawPos.OPEN_POS)));

        Trigger INTAKE = (new GamepadTrigger(driverGamepad, GamepadKeys.Trigger.RIGHT_TRIGGER)
            .whenPressed(new InstantCommand(intake::setDown))
            .whileHeld(intake.setSetPointCommand(PowerIntake.IntakePower.INTAKE)))
                .whenReleased(intake.setSetPointCommand(PowerIntake.IntakePower.STOP))
                .whenReleased(new InstantCommand(intake::setUp));
        Trigger OUTTAKE = (new GamepadTrigger(driverGamepad, GamepadKeys.Trigger.LEFT_TRIGGER)
            .whenPressed(new InstantCommand(intake::setDown))
            .whileHeld(intake.setSetPointCommand(PowerIntake.IntakePower.OUTTAKE)))
//                .whenPressed(cycleTracker.trackCycle())
            .whenReleased(intake.setSetPointCommand(PowerIntake.IntakePower.STOP))
            .whenReleased(new InstantCommand(intake::setUp));



        //Climber
//        Button climbButton  = (new GamepadButton(operatorGamepad, GamepadKeys.Button.DPAD_UP))
//                .whileHeld(climb.setSetPointCommand(Climber.ClimbEnum.CLIMB))
//                .whenReleased(climb.setSetPointCommand(Climber.ClimbEnum.REST));
//        Button climbUp  = (new GamepadButton(operatorGamepad, GamepadKeys.Button.DPAD_UP))
//                .whileHeld(climb.setSetPointCommand(Climber.ClimbEnum.CLIMB));
//        Button climbDown  = (new GamepadButton(operatorGamepad, GamepadKeys.Button.DPAD_DOWN))
//                .whileHeld(climb.setSetPointCommand(Climber.ClimbEnum.REST));
        climb.setDefaultCommand(new ClimberMoveManual(climb, operatorGamepad::getLeftY));
//        Button ClimbShooter = (new GamepadButton(operatorGamepad, GamepadKeys.Button.DPAD_UP))
//                .whenPressed(climb.setSetPointCommand(Climber.ClimbEnum.SHOOTER));
//        Button resetClimb  = (new GamepadButton(operatorGamepad, GamepadKeys.Button.BACK))
//                .whenPressed(new InstantCommand(()->climb.resetEncoder()));

        //Slide
        Button slideRest  = (new GamepadButton(operatorGamepad, GamepadKeys.Button.A))
            .whenPressed(new ResetCommand(horizontalSlide, arm, claw));
        Button slideLow  = (new GamepadButton(operatorGamepad, GamepadKeys.Button.X))
            .whenPressed(new SlideCommand(horizontalSlide,arm,claw, HorizontalSlide.SlideEnum.LOW));
        Button slideMid  = (new GamepadButton(operatorGamepad, GamepadKeys.Button.B))
            .whenPressed(new SlideCommand(horizontalSlide,arm, claw, HorizontalSlide.SlideEnum.MID));
        Button slideHigh  = (new GamepadButton(operatorGamepad, GamepadKeys.Button.Y))
            .whenPressed(new SlideCommand(horizontalSlide,arm, claw, HorizontalSlide.SlideEnum.HIGH));
        horizontalSlide.setDefaultCommand(new SlideMoveManual(horizontalSlide, operatorGamepad::getRightY));
        Button resetSlide  = (new GamepadButton(operatorGamepad, GamepadKeys.Button.START))
            .whenPressed(new InstantCommand(()-> horizontalSlide.resetEncoder()));

        //Driver
//        drivetrain.setDefaultCommand(new DefaultDriveCommand(drivetrain, driverGamepad, true);

        //y - up/dowm
        //x- right left
    }

    @Override
    public void matchStart() {}
}
