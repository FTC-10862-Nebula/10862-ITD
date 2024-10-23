package org.firstinspires.ftc.teamcode.opmode;

import com.arcrobotics.ftclib.command.CommandBase;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;

import org.firstinspires.ftc.teamcode.subsystems.drive.OldDrive;

public class DriveCommand extends CommandBase {
    private OldDrive drive;
    private GamepadEx driverGamepad;

    protected double multiplier;
    boolean isFieldCentric;
    double offset = 0;

    public DriveCommand(OldDrive drive,
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
        if(driverGamepad.getButton(GamepadKeys.Button.LEFT_BUMPER)) {
            multiplier = 0.25;
        } else {
            multiplier = 12;//5.5
        }
//        if(driverGamepad.getButton(GamepadKeys.Button.A)) {
//            drive.reInitializeIMU();
//            offset = 0;
//        }
        if(isFieldCentric) {
            drive.fieldCentric(
                    driverGamepad.getLeftY(),// * multiplier,
                    driverGamepad.getLeftX(),// * multiplier,
                    driverGamepad.getRightY(),//*multiplier,
                    -driverGamepad.getRightX(),// * multiplier,
                    multiplier,
                    offset
            );
        }
    }



    @Override
    public void end(boolean interrupted) {
        drive.stop();
    }
}
