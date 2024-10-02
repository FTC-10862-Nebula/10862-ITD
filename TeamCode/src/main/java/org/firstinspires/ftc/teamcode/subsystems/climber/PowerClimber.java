package org.firstinspires.ftc.teamcode.subsystems.climber;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.command.Command;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.util.NebulaConstants;
import org.firstinspires.ftc.teamcode.util.nebulaHardware.NebulaMotor;
//@Disabled
@Config
public class PowerClimber extends SubsystemBase {
    protected Telemetry telemetry;
    protected NebulaMotor climber;

    public enum ClimbPower {
        UP(-1),
        DOWN(0.75);
        public final double power;
        ClimbPower(double power) {
            this.power = power;
        }
    }


    protected static ClimbPower climbPos;

    public PowerClimber(Telemetry tl, HardwareMap hw, boolean isEnabled) {
        climber = new NebulaMotor(hw,
            NebulaConstants.Climber.climberName,
            NebulaConstants.Climber.climberType,
            NebulaConstants.Climber.climberDirection,
            NebulaConstants.Climber.climberIdleMode,
            isEnabled);

        climber.setDistancePerPulse(NebulaConstants.Climber.climberDistancePerPulse);

        this.telemetry = tl;
    }

    @Override
    public void periodic() {
        telemetry.addData("Climber Encoder: ", climber.getPosition());
    }

    public void setPower(double power) {
        climber.setPower(power);
    }

    public void stopSlide() {
        climber.stop();
    }
    /****************************************************************************************/
    
    public void resetEncoder() {
        climber.resetEncoder();
    }
    
    public Command setPowerCommand(double power) {
        return new InstantCommand(()->{setPower(power);});
    }
    public Command setPowerCommand(ClimbPower pos) {
        return new InstantCommand(()->{setPower(pos.power);});
    }
}