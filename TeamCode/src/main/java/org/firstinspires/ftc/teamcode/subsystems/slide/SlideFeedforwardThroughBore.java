package org.firstinspires.ftc.teamcode.subsystems.slide;

import com.arcrobotics.ftclib.trajectory.TrapezoidProfile;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.util.NebulaConstants;
import org.firstinspires.ftc.teamcode.util.odoPod.Encoder;
@Deprecated
//Do Not Delete
public class SlideFeedforwardThroughBore extends SlideFeedforward {
    protected final Encoder slideEncoder;// Plugged into Motor

    public SlideFeedforwardThroughBore(Telemetry tl, HardwareMap hw, boolean isEnabled) {
        super(tl, hw, isEnabled);
        slideEncoder = new Encoder(
            hw.get(DcMotorEx.class, NebulaConstants.Slide.slideRName));
        slideEncoder.getCurrentPosition();
        slideEncoder.setDistancePerPulse(NebulaConstants.Slide.slideDistancePerPulse);
    }

    @Override
    public void periodic() {
//        slideController.setF(NebulaConstants.Slide.slidePID.f * Math.cos(Math.toRadians(slideController.getSetPoint())));
        constraints = new TrapezoidProfile.Constraints(
            0,// radians per second
            0);//radians per second per second

//        trapezoidProfile = new TrapezoidProfile(constraints, goal, start);
        start = trapezoidProfile.calculate(0.02);
        double output = (slideController.calculate(getEncoderDistance()) +
            (slideFeedforward.calculate(start.position, start.velocity)));
        setPower(output);//TODO: Probably shouldn't be like this

        telemetry.addData("Slide Motor Output:", output);
        telemetry.addData("Slide1 Encoder: ", slideR.getPosition());
        telemetry.addData("Slide Pos:", getSetPoint());
    }

//    @Override
//    public void resetEncoder() {
//        encoderOffset = -(slideEncoder.getCurrentPosition()) + encoderOffset;
//    }
    @Override
    public double getEncoderDistance() {
        return slideEncoder.getCurrentPosition();
//        return slideEncoder.getCurrentPosition() - encoderOffset;
    }
}