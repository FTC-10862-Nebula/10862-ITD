package org.firstinspires.ftc.teamcode.subsystems.outtake;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.command.Command;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.controller.PIDFController;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.util.nebulaHardware.NebulaMotor;

@Config
public class VerticalSlidePower extends SubsystemBase {
    protected Telemetry telemetry;
    protected NebulaMotor vRSlide,vLSlide;

    public VerticalSlidePower(Telemetry tl, HardwareMap hw, boolean isEnabled) {
        this.telemetry=tl;
        vRSlide = new NebulaMotor(hw,
            "vRSlide",
            NebulaMotor.MotorType.RPM_435, NebulaMotor.Direction.Reverse,
            NebulaMotor.IdleMode.Coast, isEnabled);
        vLSlide = new NebulaMotor(hw,
                "vLSlide",
                NebulaMotor.MotorType.RPM_435, NebulaMotor.Direction.Forward,
                NebulaMotor.IdleMode.Coast, isEnabled);

    }

    @Override
    public void periodic() {
    
    }
    public Command setPower(double power) {
        return new SequentialCommandGroup(
                new InstantCommand(
                        ()->vRSlide.setPower(power)
                ),
                new InstantCommand(
                        ()->vLSlide.setPower(power)
                )
        );
    }

}