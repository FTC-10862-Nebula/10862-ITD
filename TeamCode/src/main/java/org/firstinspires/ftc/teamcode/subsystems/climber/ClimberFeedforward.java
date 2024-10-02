package org.firstinspires.ftc.teamcode.subsystems.climber;

import com.arcrobotics.ftclib.controller.wpilibcontroller.SimpleMotorFeedforward;
import com.arcrobotics.ftclib.trajectory.TrapezoidProfile;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.util.NebulaConstants;
@Deprecated
public class ClimberFeedforward extends Climber {
    protected SimpleMotorFeedforward feedforward;
    protected TrapezoidProfile.State start = new TrapezoidProfile.State(getEncoderDistance(), climber.getVelocity());
    protected TrapezoidProfile.State goal;
    protected TrapezoidProfile.Constraints constraints;
    protected TrapezoidProfile trapezoidProfile;
    public ClimberFeedforward(Telemetry tl, HardwareMap hw, boolean isEnabled) {
        super(tl, hw, isEnabled);
        feedforward = new SimpleMotorFeedforward(
            NebulaConstants.Climber.ks,
            NebulaConstants.Climber.kv,
            NebulaConstants.Climber.ka);
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
        double output = (climberController.calculate(getEncoderDistance()) +
            (feedforward.calculate(start.position, start.velocity)));
        setPower(output);//TODO: Probably shouldn't be like this


        telemetry.addData("CLimber Encoder: ", climber.getPosition());
        telemetry.addData("Climber SetPoint:", getSetPoint());
    }
}