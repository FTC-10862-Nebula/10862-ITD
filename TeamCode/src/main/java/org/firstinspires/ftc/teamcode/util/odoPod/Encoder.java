package org.firstinspires.ftc.teamcode.util.odoPod;

import com.acmerobotics.roadrunner.util.NanoClock;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

/**
 * Wraps a motor instance to provide corrected velocity counts and allow reversing independently of the corresponding
 * slot's motor direction
 */
//Used for Odo and for any external sensors that are not the motor
public class Encoder {
    private final static int CPS_STEP = 0x10000;
    private double dpp;

    private static double inverseOverflow(double input, double estimate) {
        double real = input;
        while (Math.abs(estimate - real) > CPS_STEP / 2.0) {
            real += Math.signum(estimate - real) * CPS_STEP;
        }
        return real;
    }

    public enum Direction {
        FORWARD(1),
        REVERSE(-1);

        private int multiplier;

        Direction(int multiplier) {
            this.multiplier = multiplier;
        }

        public int getMultiplier() {
            return multiplier;
        }
    }

    private DcMotorEx motor;
    private NanoClock clock;

    private Direction direction;

    private int lastPosition;
    private double velocityEstimate;
    private double lastUpdateTime;
    private double distancePerPulse;

    public Encoder(DcMotorEx motor, NanoClock clock) {
        this.motor = motor;
        this.clock = clock;

        this.direction = Direction.FORWARD;

        this.lastPosition = 0;
        this.velocityEstimate = 0.0;
        this.lastUpdateTime = clock.seconds();
    }

    public Encoder(DcMotorEx motor) {
        this(motor, NanoClock.system());
    }

    public Direction getDirection() {
        return direction;
    }

    private int getMultiplier() {
        return getDirection().getMultiplier() * (motor.getDirection() == DcMotorSimple.Direction.FORWARD ? 1 : -1);
    }

    /**
     * Allows you to set the direction of the counts and velocity without modifying the motor's direction state
     * @param direction either reverse or forward depending on if encoder counts should be negated
     */
    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public double getCurrentPosition() {
        int multiplier = getMultiplier();
        int currentPosition = motor.getCurrentPosition() * multiplier;
        if (currentPosition != lastPosition) {
            double currentTime = clock.seconds();
            double dt = currentTime - lastUpdateTime;
            velocityEstimate = (currentPosition - lastPosition) / dt;
            lastPosition = currentPosition;
            lastUpdateTime = currentTime;
        }
//        return currentPosition; //TODO: Which one????? Check
        return currentPosition * dpp;
    }

    public double getRawVelocity() {
        int multiplier = getMultiplier();
        return motor.getVelocity() * multiplier;
    }

    public double getCorrectedVelocity() {
        return inverseOverflow(getRawVelocity(), velocityEstimate);
    }
    public double setDistancePerPulse(double distancePerPulse) {
        dpp = distancePerPulse;
        return dpp;
    }
//    public double getDistance() {
//        return dpp * getCurrentPosition();
//    }

}
