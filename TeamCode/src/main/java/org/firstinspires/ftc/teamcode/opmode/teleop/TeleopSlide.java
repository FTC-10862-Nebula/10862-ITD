package org.firstinspires.ftc.teamcode.opmode.teleop;

import static org.firstinspires.ftc.teamcode.subsystems.intake.Intake.Value.INTAKE;
import static org.firstinspires.ftc.teamcode.subsystems.intake.Intake.Value.OUTTAKE;
import static org.firstinspires.ftc.teamcode.subsystems.intake.Intake.Value.STOP;
import static org.firstinspires.ftc.teamcode.subsystems.intake.Intake.Value.SUBINT;
import static org.firstinspires.ftc.teamcode.subsystems.intake.Intake.Value.SUBOUT;
import static org.firstinspires.ftc.teamcode.subsystems.outtake.Claw.Value.CLOSE;
import static org.firstinspires.ftc.teamcode.subsystems.outtake.Claw.Value.OPEN;
import static org.firstinspires.ftc.teamcode.subsystems.outtake.Outtake.Value.CLIMB;
import static org.firstinspires.ftc.teamcode.subsystems.outtake.Outtake.Value.HIGH_BUCKET;
import static org.firstinspires.ftc.teamcode.subsystems.outtake.Outtake.Value.HIGH_RUNG;
import static org.firstinspires.ftc.teamcode.subsystems.outtake.Outtake.Value.LOW_BUCKET;
import static org.firstinspires.ftc.teamcode.subsystems.outtake.Outtake.Value.LOW_RUNG;
import static org.firstinspires.ftc.teamcode.subsystems.outtake.Outtake.Value.SPECIMEN_HIGH_BAR;
import static org.firstinspires.ftc.teamcode.subsystems.outtake.Outtake.Value.SPECIMEN_LOW_BAR;
import static org.firstinspires.ftc.teamcode.subsystems.outtake.Outtake.Value.SPECIMEN_WALL;
import static org.firstinspires.ftc.teamcode.subsystems.outtake.Outtake.Value.START;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.command.Command;
import com.arcrobotics.ftclib.command.ConditionalCommand;
import com.arcrobotics.ftclib.command.SelectCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;
import com.arcrobotics.ftclib.command.button.Button;
import com.arcrobotics.ftclib.command.button.GamepadButton;
import com.arcrobotics.ftclib.command.button.Trigger;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.commands.manual.DefaultDriveCommand;
//import org.firstinspires.ftc.teamcode.commands.manual.SlideHorizontalManual;
import org.firstinspires.ftc.teamcode.commands.manual.SlideVerticalManual;
import org.firstinspires.ftc.teamcode.subsystems.climber.Climber;
import org.firstinspires.ftc.teamcode.subsystems.drive.MecDrive;
import org.firstinspires.ftc.teamcode.subsystems.intake.HorizontalSlide;
import org.firstinspires.ftc.teamcode.subsystems.intake.Intake;
import org.firstinspires.ftc.teamcode.subsystems.intake.IntakeServo;
import org.firstinspires.ftc.teamcode.subsystems.intake.PowerIntake;
import org.firstinspires.ftc.teamcode.subsystems.outtake.Arm;
import org.firstinspires.ftc.teamcode.subsystems.outtake.Claw;
import org.firstinspires.ftc.teamcode.subsystems.outtake.Outtake;
import org.firstinspires.ftc.teamcode.subsystems.outtake.Pivot;
import org.firstinspires.ftc.teamcode.subsystems.outtake.VerticalSlide;
import org.firstinspires.ftc.teamcode.subsystems.outtake.VerticalSlidePower;
import org.firstinspires.ftc.teamcode.util.teleop.GamepadTrigger;
import org.firstinspires.ftc.teamcode.util.teleop.MatchOpMode;

import java.util.function.Supplier;

@Config
@TeleOp
public class TeleopSlide extends MatchOpMode {
    //TODO: Add a on/off switch for drivetrain
    private GamepadEx driverGamepad, operatorGamepad;

    private VerticalSlidePower verticalSlidePower;

    public TeleopSlide() {}

    @Override
    public void robotInit() {
        driverGamepad = new GamepadEx(gamepad1);
        operatorGamepad = new GamepadEx(gamepad2);

        verticalSlidePower = new VerticalSlidePower(telemetry, hardwareMap, true);
    }


    @Override
    public void configureButtons() {
        //OUTTAKE
//        Button idkatp = (new GamepadButton(operatorGamepad,GamepadKeys.Button.DPAD_UP)
//                .whenPressed(outtake.verticalSlidePower())

        //HORIZONTAL POWER
        Button HoutPower = (new GamepadButton(operatorGamepad,GamepadKeys.Button.A)
                .whenPressed(verticalSlidePower.setPower(-1)))
                .whenReleased(verticalSlidePower.setPower(0));
        Button HinPower = (new GamepadButton(operatorGamepad,GamepadKeys.Button.B)
                .whenPressed(verticalSlidePower.setPower(1))
                .whenReleased(verticalSlidePower.setPower(0)));
    }


    @Override
    public void matchStart() {}

}
