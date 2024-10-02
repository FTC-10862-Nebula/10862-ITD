package org.firstinspires.ftc.teamcode.commands.drive.teleop;

import com.arcrobotics.ftclib.command.CommandBase;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import org.firstinspires.ftc.teamcode.subsystems.drive.mec.Drivetrain;
public class DefaultDriveCommand extends CommandBase {
    private Drivetrain drive;
    private GamepadEx driverGamepad;

    protected double multiplier;
    boolean isFieldCentric;
    double offset = 0;

    public DefaultDriveCommand(Drivetrain drive,
                               GamepadEx driverGamepad,
                               boolean isFieldCentric) {

        this.drive = drive;
        this.driverGamepad = driverGamepad;
        this.multiplier = 1.0;
        addRequirements(this.drive);

        this.isFieldCentric = isFieldCentric;
    }

    @Override
    public void execute() {
        if(driverGamepad.getButton(GamepadKeys.Button.RIGHT_BUMPER)) {
            multiplier = 0.55;
        } else {
            multiplier = 10;//5.5
        }
        if(driverGamepad.getButton(GamepadKeys.Button.A)) {
            drive.reInitializeIMU();
            offset = 0;
        }
        if(isFieldCentric) {
            drive.fieldCentric(
                    driverGamepad.getLeftY(),// * multiplier,
                    driverGamepad.getLeftX(),// * multiplier,
                    -driverGamepad.getRightX(),// * multiplier,
                    multiplier,
                    offset
            );
        }
//        else {
//            drive.mecDrive(
//                    driverGamepad.getLeftY() * multiplier,
//                    driverGamepad.getLeftX() * multiplier,
//                    driverGamepad.getRightX() * multiplier
//            );
//        }
    }



    @Override
    public void end(boolean interrupted) {
        drive.stop();
    }
}
