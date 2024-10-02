package org.firstinspires.ftc.teamcode.subsystems.slide;

import com.arcrobotics.ftclib.controller.wpilibcontroller.ElevatorFeedforward;
import com.arcrobotics.ftclib.trajectory.TrapezoidProfile;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.util.NebulaConstants;
@Deprecated
public class SlideFeedforward extends Slide {
    protected ElevatorFeedforward slideFeedforward;
    protected TrapezoidProfile.State start = new TrapezoidProfile.State(getEncoderDistance(), slideR.getVelocity());
    protected TrapezoidProfile.State goal;
    protected TrapezoidProfile.Constraints constraints;
    protected TrapezoidProfile trapezoidProfile;
    public SlideFeedforward(Telemetry tl, HardwareMap hw, boolean isEnabled) {
        super(tl, hw, isEnabled);
        slideFeedforward = new ElevatorFeedforward(
            NebulaConstants.Slide.ks,
            NebulaConstants.Slide.kcos,
            NebulaConstants.Slide.ka,
            NebulaConstants.Slide.kv);
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
        double output = (slideController.calculate(getEncoderDistance()) +
            (slideFeedforward.calculate(start.position, start.velocity)));
        setPower(output);//TODO: Probably shouldn't be like this


        telemetry.addData("Slide Motor Output:", output);
        telemetry.addData("Slide1 Encoder: ", slideR.getPosition());
        telemetry.addData("Slide Pos:", getSetPoint());
    }
}