
package org.firstinspires.ftc.teamcode.subsystems.drive;


import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.configuration.typecontainers.MotorConfigurationType;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import java.util.Arrays;
import java.util.List;

public class OldDrive extends SubsystemBase {

//    private final OldMec drive;
    private final DcMotorEx leftFront, leftRear, rightRear, rightFront;

    private Telemetry telemetry;
    //    private BNO055IMU imu;
    private final int LFVal = 0,
            LRVal = 1,
            RFVal = 2,
            RRVal = 3;
    double[] powers = new double[4];
    private final List<DcMotorEx> motors;



    public OldDrive(HardwareMap hardwareMap,Telemetry tl) {
        this.telemetry = tl;
//        imu = hardwareMap.get(BNO055IMU.class, "imu");
        leftFront = hardwareMap.get(DcMotorEx.class, "LF");
        leftRear = hardwareMap.get(DcMotorEx.class, "LB");
        rightRear = hardwareMap.get(DcMotorEx.class, "RB");
        rightFront = hardwareMap.get(DcMotorEx.class, "RF");

        motors = Arrays.asList(leftFront, leftRear, rightRear, rightFront);

        for (DcMotorEx motor : motors) {
            MotorConfigurationType motorConfigurationType = motor.getMotorType().clone();
            motorConfigurationType.setAchieveableMaxRPMFraction(1.0);
            motor.setMotorType(motorConfigurationType);
        }
    }

//    public void init() {
//        new Pose2d(0,0,0);
//        drive.setMotorPowers(0, 0, 0, 0);
//        setPoseEstimate(new Pose2d(0, 0, Math.toRadians(0)));
//        PoseStorage.currentPose = (new Pose2d(0, 0, Math.toRadians(0)));
//    }
//
//    public void reInitializeIMU() {
//        drive.resetImu();
//    }
//
//
//    public void setPowers(double leftF, double leftR, double rightR, double rightF) {
//        drive.setMotorPowers(leftF, leftR, rightR, rightF);
//    }

    public void mecDrive(double y, double x, double rx) {
        double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1);

        powers [LFVal] = (y + x + rx) / denominator;    //fLPower
        powers [LRVal] = (y - x + rx) / denominator;    //bLPower
        powers [RFVal] = (y - x - rx) / denominator;    //fRPower
        powers [RRVal] = (y + x - rx) / denominator;    //bRPower
        setMotorPowers(powers[LFVal], powers[LRVal], powers[RFVal], powers[RRVal]);
    }

    public void  fieldCentric(double leftY, double y, double x, double rx, double multiplier, double offset){
//        double theta = -imu.getAngularOrientation().firstAngle;
        double theta =0;
  //      double theta = -drive.getExternalHeading()+offset;//Ok?

        double rotX = x * Math.cos(theta) - y * Math.sin(theta);
        double rotY = x * Math.sin(theta) + y * Math.cos(theta);
        double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1);

        // ^^^^^^ Denominator is the largest motor power (absolute value) or 1
        // This ensures all the powers maintain the same ratio, but only when
        // at least one is out of the range [-1, 1]

        powers [LFVal] = (rotY + rotX - rx) / denominator;
        powers [LRVal] = (rotY - rotX - rx) / denominator;
        powers [RFVal] = (rotY + rotX + rx) / denominator;
        powers [RRVal] = (rotY - rotX + rx) / denominator;


        if(Math.abs(powers[LFVal])<0.5&
                Math.abs(powers[LRVal])<0.5&
                Math.abs(powers[RFVal])<0.5&
                Math.abs(powers[RRVal])<0.5){
            for (int i = 0; i <= 3; i++) {
                powers[i] = squareInput(powers[i]);
//                powers[i] = cubeInput(powers[i]);
            }
        }
        setMotorPowers(powers[LFVal]* multiplier,
                powers[LRVal]* multiplier,
                powers[RFVal]* multiplier,
                powers[RRVal]* multiplier);

    }

    private double squareInput(double power) {
        return power * Math.abs(power);
    }
    private double cubeInput(double power) {
        return power*Math.abs(power)*Math.abs(power);
    }

//    public double getHeading() {
//        return Math.toDegrees(drive.getExternalHeading());
//    }
    /**
     * Returns minimum range value if the given value is less than
     * the set minimum. If the value is greater than the set maximum,
     * then the method returns the maximum value.
     *
     * value - The value to clip.
     */
//    public double clipRange(double value) {
//        return value <= -1 ? -1
//                : value >= 1 ? 1
//                : value;
//    }

    /*protected void normalize(double[] wheelSpeeds, double magnitude) {
        double maxMagnitude = Math.abs(wheelSpeeds[0]);
        for (int i = 1; i < wheelSpeeds.length; i++) {
            double temp = Math.abs(wheelSpeeds[i]);
            if (maxMagnitude < temp) {
                maxMagnitude = temp;
            }
        }
        for (int i = 0; i < wheelSpeeds.length; i++) {
            wheelSpeeds[i] = (wheelSpeeds[i] / maxMagnitude) * magnitude;
        }

    }

//     Normalize the wheel speeds

    protected void normalize(double[] wheelSpeeds) {
        double maxMagnitude = Math.abs(wheelSpeeds[0]);
        for (int i = 1; i < wheelSpeeds.length; i++) {
            double temp = Math.abs(wheelSpeeds[i]);
            if (maxMagnitude < temp) {
                maxMagnitude = temp;
            }
        }
        if(maxMagnitude > 1) {
            for (int i = 0; i < wheelSpeeds.length; i++) {
                wheelSpeeds[i] = (wheelSpeeds[i] / maxMagnitude);
            }
        }
    }*/

    @Override
    public void periodic() {
//        update();
//        drive.returnData();//TODO:What does this do?
    }

    public void setMotorPowers(double lFP, double lRP, double rFP, double rRP) {
        leftFront.setPower(lFP);
        leftRear.setPower(lRP);
        rightRear.setPower(rRP);
        rightFront.setPower(rFP);
    }
    public void stop() {
        setMotorPowers(0, 0, 0, 0);
    }

    private double clamp(double val, double min, double max) {
        return Math.max(min, Math.min(max, val));
    }

    /**
     * Returns minimum range value if the given value is less than
     * the set minimum. If the value is greater than the set maximum,
     * then the method returns the maximum value.
     *
     * @param value The value to clip.
     */
    public double clipRange(double value) {
        return value <= -1 ? -1
                : value >= 1 ? 1
                : value;
    }

    /**
     * Normalize the wheel speeds
     */
    protected void normalize(double[] wheelSpeeds, double magnitude) {
        double maxMagnitude = Math.abs(wheelSpeeds[0]);
        for (int i = 1; i < wheelSpeeds.length; i++) {
            double temp = Math.abs(wheelSpeeds[i]);
            if (maxMagnitude < temp) {
                maxMagnitude = temp;
            }
        }
        for (int i = 0; i < wheelSpeeds.length; i++) {
            wheelSpeeds[i] = (wheelSpeeds[i] / maxMagnitude) * magnitude;
        }

    }

    /**
     * Normalize the wheel speeds
     */
    protected void normalize(double[] wheelSpeeds) {
        double maxMagnitude = Math.abs(wheelSpeeds[0]);
        for (int i = 1; i < wheelSpeeds.length; i++) {
            double temp = Math.abs(wheelSpeeds[i]);
            if (maxMagnitude < temp) {
                maxMagnitude = temp;
            }
        }
        if(maxMagnitude > 1) {
            for (int i = 0; i < wheelSpeeds.length; i++) {
                wheelSpeeds[i] = (wheelSpeeds[i] / maxMagnitude);
            }
        }
    }


}