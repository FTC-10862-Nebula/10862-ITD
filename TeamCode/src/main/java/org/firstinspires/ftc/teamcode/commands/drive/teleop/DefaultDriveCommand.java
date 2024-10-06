package org.firstinspires.ftc.teamcode.commands.drive.teleop;

import com.arcrobotics.ftclib.command.CommandBase;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;

import org.firstinspires.ftc.teamcode.subsystems.drive.MecDrive;

public class DefaultDriveCommand extends CommandBase {
    private final MecDrive drive;
    private final GamepadEx driverGamepad;

    protected double multiplier;
    boolean isFieldCentric;

    public DefaultDriveCommand(MecDrive drive,
                               GamepadEx driverGamepad,
                               boolean isFieldCentric) {

        this.drive = drive;
        this.driverGamepad = driverGamepad;
        addRequirements(this.drive);

        this.isFieldCentric = isFieldCentric;
    }

    @Override
    public void execute() {
        if(driverGamepad.getButton(GamepadKeys.Button.RIGHT_BUMPER)) {
            multiplier = 0.55;
        } else {
            multiplier = 10;
        }

        if(driverGamepad.getButton(GamepadKeys.Button.A)) {
            drive.drivetrain.resetIMU();
        }

        if(isFieldCentric) {
            drive.driveFieldCentric(
                    driverGamepad.getLeftX() * multiplier,
                    driverGamepad.getLeftY() * multiplier,
                    -driverGamepad.getRightX() * multiplier,
                    drive.drivetrain.getYaw(),
                    true
            );
        }
        else {
            drive.driveRobotCentric(driverGamepad.getLeftX() * multiplier,
                    driverGamepad.getLeftY() * multiplier,
                    -driverGamepad.getRightX() * multiplier,
                    true);
        }
    }



    @Override
    public void end(boolean interrupted) {
        drive.stop();
    }
}
