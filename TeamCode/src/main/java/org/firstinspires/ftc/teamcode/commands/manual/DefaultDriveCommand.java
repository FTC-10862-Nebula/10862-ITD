package org.firstinspires.ftc.teamcode.commands.manual;

import com.arcrobotics.ftclib.command.CommandBase;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;

import org.firstinspires.ftc.teamcode.subsystems.drive.MecDrive;

public class DefaultDriveCommand extends CommandBase {
    private final MecDrive drive;
    private final GamepadEx driverGamepad;

    protected double multiplier;

    public DefaultDriveCommand(MecDrive drive,
                               GamepadEx driverGamepad) {

        this.drive = drive;
        this.driverGamepad = driverGamepad;
        addRequirements(this.drive);
    }

    @Override
    public void execute() {
        if(driverGamepad.getButton(GamepadKeys.Button.LEFT_BUMPER)) {
            multiplier = 0.55;
        } else {
            multiplier = 1;//10
        }

        if(driverGamepad.getButton(GamepadKeys.Button.A)) {
            drive.drivetrain.resetIMU();
        }

            drive.driveFieldCentric(
//            0,0,0,0
                    driverGamepad.getLeftY(),
                    driverGamepad.getLeftX(),
                    -driverGamepad.getRightX(),//-
                   multiplier

            );
//        else {
//            drive.driveRobotCentric(
//                    driverGamepad.getLeftX() * multiplier,
//                    driverGamepad.getLeftY() * multiplier,
//                    -driverGamepad.getRightX() * multiplier,
//                    true);
//        }
   }



    @Override
    public void end(boolean interrupted) {
        drive.stop();
    }
}
