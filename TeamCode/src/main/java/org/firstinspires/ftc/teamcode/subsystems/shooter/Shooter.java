package org.firstinspires.ftc.teamcode.subsystems.shooter;


import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.command.Command;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.util.NebulaConstants;
import org.firstinspires.ftc.teamcode.util.nebulaHardware.NebulaServo;

@Config
public class Shooter extends SubsystemBase
{
    public enum ShooterPos {
        SHOOT(1),
        READY(0);

        public final double shooterPos;
        ShooterPos(double shooterPos) {
            this.shooterPos = shooterPos;
        }
    }
    Telemetry telemetry;
    private final NebulaServo shooter;

    public Shooter(Telemetry tl, HardwareMap hw, boolean isEnabled) {
        shooter = new NebulaServo(hw,
                NebulaConstants.Shooter.shooterSName,
                NebulaConstants.Shooter.shooterDirection,
                NebulaConstants.Shooter.minAngle,
                NebulaConstants.Shooter.maxAngle,
                isEnabled);
        shooter.setPosition(ShooterPos.READY.shooterPos);

        this.telemetry = tl;
    }

    @Override
    public void periodic() {
        telemetry.addData("Shooter Pos: ", shooter.getPosition());
    }

    public Command shoot() {return shooterSetPositionCommand(ShooterPos.SHOOT);}
    public Command ready() {
        return shooterSetPositionCommand(ShooterPos.READY);
    }

    private void shooterSetPosition(ShooterPos shooterPos) {
        shooter.setPosition(shooterPos.shooterPos);
    }
    public Command shooterSetPositionCommand(ShooterPos shooterPos) {
        return new InstantCommand(()->{shooterSetPosition(shooterPos);});
    }
}