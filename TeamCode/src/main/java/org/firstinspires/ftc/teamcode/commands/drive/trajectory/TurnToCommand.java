package org.firstinspires.ftc.teamcode.commands.drive.trajectory;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.subsystems.drive.mec.Drivetrain;
import org.firstinspires.ftc.teamcode.util.misc.Util;

import java.util.logging.Level;

public class TurnToCommand extends CommandBase {

    private final Drivetrain drive;
    private final double angle;
    double desired = 0, firstAngle;
    boolean weird = false;
    Telemetry tl;
    public TurnToCommand(Drivetrain drive, double angle) {
        this.drive = drive;
        this.angle = angle;
        addRequirements(drive);
    }

    public TurnToCommand(Drivetrain drive, double angle, boolean weird) {
        this.drive = drive;
        this.angle = angle;
        this.weird = weird;
        addRequirements(drive);
    }

    @Override
    public void initialize() {
//        double firstAngle = (drive.getRightAngle()- drive.getLeftAngle())/(StandardTrackingWheelLocalizer.LATERAL_DISTANCE);
        double firstAngle = drive.getHeading();
        Util.logger(this, Level.INFO, "curr angle", firstAngle);
        if (weird && firstAngle > 180) firstAngle = firstAngle - 360;

        desired = angle - firstAngle;

        //Test
        /*if (weird) {
            angle = 360 -angle;
            desired = firstAngle - angle;
        }
        else{
            desired = angle - firstAngle;

        }*/

        Util.logger(this, Level.INFO, "adjusted angle", firstAngle);
        Util.logger(this, Level.INFO, "desired angle", desired);

        drive.turn(Math.toRadians(desired));
    }

    @Override
    public void end(boolean interrupted) {
        if (interrupted) {
            drive.stop();
        }
    }

    @Override
    public void execute() {
        drive.update();
    }

    @Override
    public boolean isFinished() {
        return Thread.currentThread().isInterrupted() || !drive.isBusy();
    }

}