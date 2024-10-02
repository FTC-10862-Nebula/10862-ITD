package org.firstinspires.ftc.teamcode.subsystems.intake;

import com.arcrobotics.ftclib.controller.wpilibcontroller.SimpleMotorFeedforward;
import com.arcrobotics.ftclib.trajectory.TrapezoidProfile;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.util.NebulaConstants;
@Deprecated
public class IntakeFeedforward extends Intake {
    public SimpleMotorFeedforward intakeFeedforward;

    TrapezoidProfile.State start = new TrapezoidProfile.State(getShooterRPM(), getShooterRPM());
    TrapezoidProfile.State goal;
    TrapezoidProfile.Constraints constraints;
    TrapezoidProfile trapezoidProfile;
    Telemetry telemetry;
    public IntakeFeedforward(Telemetry tl, HardwareMap hw, boolean isEnabled) {
        super(tl, hw, isEnabled);
        intakeFeedforward = new SimpleMotorFeedforward(
            NebulaConstants.Intake.ks,
            NebulaConstants.Intake.ka,
            NebulaConstants.Intake.kv);
        constraints = new TrapezoidProfile.Constraints(
            0,// radians per second
            0);//radians per second per second

        trapezoidProfile = new TrapezoidProfile(constraints, goal, start);
    }

    @Override
    public void periodic() {
//        slideController.setF(NebulaConstants.Slide.slidePID.f * Math.cos(Math.toRadians(slideController.getSetPoint())));
        trapezoidProfile = new TrapezoidProfile(constraints, goal, start);
        start = trapezoidProfile.calculate(0.02);
        double output = (controller.calculate(getShooterRPM()) +
            (intakeFeedforward.calculate(start.position, start.velocity)));
        motor.setPower(output);
        telemetry.addData("Intake RPM:", getShooterRPM());
        telemetry.addData("Intake Required RPM:", controller.getSetPoint());
    }
}