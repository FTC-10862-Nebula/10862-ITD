package org.firstinspires.ftc.teamcode.opmode.teleop.misc;


import static com.qualcomm.robotcore.hardware.DcMotorSimple.Direction.FORWARD;
import static com.qualcomm.robotcore.hardware.DcMotorSimple.Direction.REVERSE;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.button.Trigger;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.configuration.typecontainers.MotorConfigurationType;

import org.firstinspires.ftc.teamcode.commands.drive.teleop.DefaultDriveCommand;
import org.firstinspires.ftc.teamcode.subsystems.drive.mec.Drivetrain;
import org.firstinspires.ftc.teamcode.subsystems.drive.mec.MecanumDrive;
import org.firstinspires.ftc.teamcode.subsystems.intake.PowerIntake;
import org.firstinspires.ftc.teamcode.util.teleop.GamepadTrigger;
import org.firstinspires.ftc.teamcode.util.teleop.MatchOpMode;

import java.util.Arrays;
import java.util.List;

//@Disabled
@Disabled
@Config
@TeleOp
public class SetDriveMotors extends MatchOpMode {

    // Gamepad
    private GamepadEx driverGamepad;
    private DcMotorEx leftFront, leftRear, rightRear, rightFront;
    private  List<DcMotorEx> motors;


    @Override
    public void robotInit() {
        driverGamepad = new GamepadEx(gamepad1);
        leftFront = hardwareMap.get(DcMotorEx.class, "FL");
        leftRear = hardwareMap.get(DcMotorEx.class, "BL");
        rightRear = hardwareMap.get(DcMotorEx.class, "BR");
        rightFront = hardwareMap.get(DcMotorEx.class, "FR");
        motors = Arrays.asList(leftFront, leftRear, rightRear, rightFront);

        leftFront.setDirection(REVERSE);
        leftRear.setDirection(REVERSE);
        rightFront.setDirection(FORWARD);
        rightRear.setDirection(FORWARD);
    }


    @Override
    public void configureButtons() {
        Trigger forward = (new GamepadTrigger(driverGamepad, GamepadKeys.Trigger.RIGHT_TRIGGER)
                .whileHeld(new InstantCommand(()->{
                    for (DcMotorEx motor : motors) {
                        motor.setPower(1);
                    }
                }))
                .whenReleased(new InstantCommand(()->{
                    for (DcMotorEx motor : motors) {
                        motor.setPower(0);
                    }
                }))
        );
        Trigger back = (new GamepadTrigger(driverGamepad, GamepadKeys.Trigger.LEFT_TRIGGER)
                .whileHeld(new InstantCommand(()->{
                    for (DcMotorEx motor : motors) {
                        motor.setPower(-1);
                    }
                }))
                .whenReleased(new InstantCommand(()->{
                    for (DcMotorEx motor : motors) {
                        motor.setPower(0);
                    }
                }))
        );
//        while (driverGamepad.getButton(GamepadKeys.Button.B)){
//            for (DcMotorEx motor : motors) {
//                motor.setPower(1);
//            }
//
//        }
//        while(driverGamepad.getButton(GamepadKeys.Button.A)){
//            for (DcMotorEx motor : motors) {
//                motor.setPower(-1);
//            }
//
//        }
    }

    @Override
    public void matchLoop() {
    }
    @Override
    public void disabledPeriodic() { }
    @Override
    public void matchStart() { }
    @Override
    public void robotPeriodic(){
    }
}
