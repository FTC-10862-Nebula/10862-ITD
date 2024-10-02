package org.firstinspires.ftc.teamcode.util.odoPod;

import androidx.annotation.NonNull;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.localization.ThreeTrackingWheelLocalizer;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import java.util.Arrays;
import java.util.List;

/*
 * Sample tracking wheel localizer implementation assuming the standard configuration:
 *
 *    /--------------\
 *    |     ____     |
 *    |     ----     |
 *    | ||        || |
 *    | ||        || |
 *    |              |
 *    |              |
 *    \--------------/
 *
 */
//@Config
public class StandardTrackingWheelLocalizer extends ThreeTrackingWheelLocalizer {
    public static double TICKS_PER_REV = 8192;
    //CPR (counts per rev or cycles per rev) is 4096 - Odo-pod: The REV Through Bore Encoder-TICKS_PER_REV of 8192
    public static double WHEEL_RADIUS = 0.79; // in - Radius is 20mm
    public static double GEAR_RATIO = 1;
    // output (wheel) speed / input (encoder) speed

    public static double X_MULTIPLIER = 0.9827; // Multiplier in the X direction
    public static double Y_MULTIPLIER = 0.9726456; // Multiplier in the Y direction

    public static double LATERAL_DISTANCE = 11.8; //11.25
    // in; distance between the left and right wheels - Odo-pod: the distance from the left and right wheels.
    public static double FORWARD_OFFSET = 6.25;
    // in; offset of the lateral wheel - positive when in front of the wheels and negative when behind the wheels (closer to the back).

    private final Encoder leftEncoder, rightEncoder, frontEncoder;
    private final Telemetry telemetry;

//    public static int leftEncoderPos, rightEncoderPos, frontEncoderPos;

    public StandardTrackingWheelLocalizer(HardwareMap hardwareMap, Telemetry tl) {
        super(Arrays.asList(
                new Pose2d(0, LATERAL_DISTANCE / 2, 0), // left
                new Pose2d(0, -LATERAL_DISTANCE / 2, 0), // right
                new Pose2d(FORWARD_OFFSET, 0, Math.toRadians(90)) // front
        ));

        this.telemetry = tl;

        leftEncoder = new Encoder(hardwareMap.get(DcMotorEx.class, "leftFront"));
        rightEncoder = new Encoder(hardwareMap.get(DcMotorEx.class, "rightRear"));
        frontEncoder = new Encoder(hardwareMap.get(DcMotorEx.class, "leftRear"));

        leftEncoder.setDirection(Encoder.Direction.REVERSE);
        rightEncoder.setDirection(Encoder.Direction.REVERSE);
        frontEncoder.setDirection(Encoder.Direction.FORWARD);
    }

    public static double encoderTicksToInches(double ticks) {
        return WHEEL_RADIUS * 2 * Math.PI * GEAR_RATIO * ticks / TICKS_PER_REV;
    }

    @NonNull
    @Override
    public List<Double> getWheelPositions() {
        return Arrays.asList(
                encoderTicksToInches(leftEncoder.getCurrentPosition()* X_MULTIPLIER),
                encoderTicksToInches(rightEncoder.getCurrentPosition()* X_MULTIPLIER),
                encoderTicksToInches(frontEncoder.getCurrentPosition()* Y_MULTIPLIER)
        );
    }

    @NonNull
    @Override
    public List<Double> getWheelVelocities() {
        //  If your encoder velocity can exceed 32767 counts / second (such as the REV Through Bore and other
        //  competing magnetic encoders), change Encoder.getRawVelocity() to Encoder.getCorrectedVelocity() to enable a
        //  compensation method

        return Arrays.asList(
                encoderTicksToInches(leftEncoder.getCorrectedVelocity()* X_MULTIPLIER),
                encoderTicksToInches(rightEncoder.getCorrectedVelocity()* X_MULTIPLIER),
                encoderTicksToInches(frontEncoder.getCorrectedVelocity()* Y_MULTIPLIER)
        );
    }


    //    @Override
    public void periodic() {
        telemetry.addData("Front Encoder: ", frontEncoder.getCurrentPosition());
        telemetry.addData("Left Encoder: ", leftEncoder.getCurrentPosition());
        telemetry.addData("Right Encoder: ", rightEncoder.getCurrentPosition());
    }

    public double returnFrontPos() {
        return frontEncoder.getCurrentPosition();
    }
    public double returnLeftPos() {
        return leftEncoder.getCurrentPosition();
    }
    public double returnRightPos() {
        return rightEncoder.getCurrentPosition();
    }
}