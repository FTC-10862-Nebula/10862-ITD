package org.firstinspires.ftc.teamcode.util;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.util.nebulaHardware.NebulaCRServo;
import org.firstinspires.ftc.teamcode.util.nebulaHardware.NebulaMotor;
import org.firstinspires.ftc.teamcode.util.nebulaHardware.NebulaServo;


//This will be used to store all Initialization Values for Subsystems, etc.
//Positions will
@Config
public class NebulaConstants {
    //TODO: make some things final
    // TODO: servos = have set angles
    //Arm Distance Per Pulse
    //Math.PI/2
    //(Counts per revolution*Gear ratio(5:1=5 / 1:5))/(Turns per revolution * 2π)

    //Slide Distance Per Pulse
    //(COUNTS_PER_PULSE * GEAR_RATIO) / (GEAR_DIAMETER_INCHES * Math.PI);

    /** Arm **/
    public static Arm arm;
    public static class Arm {
        public static final  String armRName = "armR";  //
        public static final NebulaServo.Direction armRDirection = NebulaServo.Direction.Reverse;
        public static double minAngle = 0, maxAngle = 360;
        public static final String armLName = "armL";  //
        public static final NebulaServo.Direction armLDirection = NebulaServo.Direction.Forward;
    }

    /** AutoDropper **/
    public static AutoDropper autoDropper;
    public static class AutoDropper {
        public static final  String dropperName = "dropper";  //
        public static final NebulaServo.Direction dropperDirection = NebulaServo.Direction.Forward;
        public static double minAngle = 0, maxAngle = 360;
    }

    /** Claw **/
    public static Claw claw;
    public static class Claw {
        public static final String clawFName = "clawF";  //
        public static final NebulaServo.Direction clawFDirection = NebulaServo.Direction.Forward;
        public static double minAngle = 0, maxAngle = 360;
        public final static String clawBName = "clawB";
        public final static NebulaServo.Direction clawBDirection = NebulaServo.Direction.Forward;
    }

    /** Slide **/
    public static Slide slide;
    public static class Slide {
        public static final String slideRName = "slideR";
        public static final String slideLName = "slideL";
        public static final NebulaMotor.Direction slideRDirection = NebulaMotor.Direction.Reverse,
                slideLDirection = NebulaMotor.Direction.Forward;
        public static final PIDFCoefficients slidePID = new PIDFCoefficients(0.005, 0.00, 0,0);//.0075, 0., .003, 0)
        public static final int slideTolerance = 10;
        //        public int slideDistancePerPulse = (COUNTS_PER_PULSE * GEAR_RATIO) / (GEAR_DIAMETER_INCHES * Math.PI);
//        public int slideDistancePerPulse = (GEAR_DIAMETER_INCHES * Math.PI);
        public static final double slideDistancePerPulse = 1;//(365/751.8)
        public static final NebulaMotor.IdleMode slideIdleMode = NebulaMotor.IdleMode.Brake;
        public static final NebulaMotor.MotorType slideType = NebulaMotor.MotorType.RPM_312;
        public static final double ks=0,
                kcos=0,
                ka=0,
                kv=0;
        public static final double maxVelocity = 0,
                maxAcceleration = 0,
                MIN_POSITION = 0,//mm
                MAX_POSITION = 0;
    }

    /** Drive **/
    public static Drive drive;
    public static class Drive {//TODO:FIX CONSTANTS
        public final static String
                leftFrontM = "FL",
                leftRearM = "BL",
                rightRearM = "BR",
                rightFrontM = "FR";
        //NOT BEING USEDA
        public static NebulaMotor.Direction leftFrontDir = NebulaMotor.Direction.Reverse,
                leftRearDir = NebulaMotor.Direction.Reverse,
                rightRearDir = NebulaMotor.Direction.Forward,
                rightFrontDir = NebulaMotor.Direction.Reverse;
//        leftFront.setDirection(DcMotor.Direction.FORWARD);
//        leftRear.setDirection(DcMotor.Direction.FORWARD);
//        rightFront.setDirection(DcMotor.Direction.FORWARD);
//        rightRear.setDirection(DcMotor.Direction.REVERSE);
        public static NebulaMotor.IdleMode driveIdleMode = NebulaMotor.IdleMode.Brake;
        public final static NebulaMotor.MotorType driveType = NebulaMotor.MotorType.RPM_435;
        public static boolean isSquaredInputs = true;
        public static double tippingTolerance = 3;//This probably needs to be less
    }

    /** GamePad **/
    public static class GamePad {
        public static double isDriverOneDeadband(double value) {
            if(Math.abs(value)<0.1){
                return 0;
            } else return value;
        }
        public static double isDriverTwoDeadband(double value) {
            if(Math.abs(value)<0.1){
                return 0;
            } else return value;
        }
        //TODO: Maybe should remove all Safety Stuff
        public static boolean overrideSafety = false;
    }

    /** Shooter **/
    public static Shooter shooter;
    public static class Shooter {
        public final static String shooterSName = "shooterServo";  //EH3
        public final static NebulaServo.Direction shooterDirection = NebulaServo.Direction.Reverse;
        public static double minAngle = 0, maxAngle = 360;
    }

    /** Intake **/
//    public static Intake intake;
    public static class Intake {
        public final static String intakeMName = "intake";
        public static NebulaMotor.Direction intakeDirection = NebulaMotor.Direction.Reverse;
//        public int pivotDistancePerPulse = 360 / (gear_ratio * pulses_per_rev);// For Internal Encoder
        public static NebulaMotor.IdleMode intakeIdleMode = NebulaMotor.IdleMode.Brake;
        public final static NebulaMotor.MotorType intakeType = NebulaMotor.MotorType.RPM_312;
        public static PIDFCoefficients intakePID = new PIDFCoefficients(.005, 0.00, 0.0,0);
        public static int intakeTolerance = 1;
        public static double ks=0,
                ka=0,
                kv=0;
        public static double maxVelocity = 0,
                maxAcceleration = 0;
        public static ElapsedTime intakeTime = new ElapsedTime(0);

        public final static String intakeRName = "intakeR";  //
        public final static NebulaServo.Direction intakeRDirection = NebulaServo.Direction.Reverse;
        public static double minAngle = 0, maxAngle = 360;
        public final static String intakeLName = "intakeL";  //
        public final static NebulaServo.Direction intakeLDirection = NebulaServo.Direction.Forward;
    
        public final static String rollerName = "roller";
        public static NebulaMotor.Direction rollerDirection = NebulaMotor.Direction.Forward;
        public static NebulaMotor.IdleMode rollerIdleMode = NebulaMotor.IdleMode.Coast;
    }

    /** Climber **/
    public static Climber climber;
    public static class Climber {
        public final static String climberName = "climb";
        public static NebulaMotor.Direction climberDirection = NebulaMotor.Direction.Forward;
        public static int climberDistancePerPulse = 1;
        //1, 1425, 360/1425
        //        public int pivotDistancePerPulse = 360 / (gear_ratio * pulses_per_rev);// For Internal Encoder
        public static NebulaMotor.IdleMode climberIdleMode = NebulaMotor.IdleMode.Brake;
        public final static NebulaMotor.MotorType climberType = NebulaMotor.MotorType.RPM_117;
        public static PIDFCoefficients climberPID = new PIDFCoefficients(0.002, 0.015, 0.0,0);
        //0.005, 0.015
        public static int climberTolerance = 1;
        public static double ks=0,
                ka=0,
                kv=0;
        public static double maxVelocity = 0,
                maxAcceleration = 0;
//                MIN_SPEED = -400,
//                MAX_SPEED = 400;
    }

    /** CAM Servo **/
    public static CamServo camServo;
    public static class CamServo {
        public final static String camSName = "cam";  //EH3
        public final static NebulaServo.Direction camDirection = NebulaServo.Direction.Reverse;
        public static double minAngle = 0, maxAngle = 360;
    }

    /** General Functions **/
    //From RobotAutoDriveByGyro_Linear.java
//    // Calculate the COUNTS_PER_INCH for your specific drive train.
//    // Go to your motor vendor website to determine your motor's COUNTS_PER_MOTOR_REV
//    // For external drive gearing, set DRIVE_GEAR_REDUCTION as needed.
//    // For example, use a value of 2.0 for a 12-tooth spur gear driving a 24-tooth spur gear.
//    // This is gearing DOWN for less speed and more torque.
//    // For gearing UP, use a gear ratio less than 1.0. Note this will affect the direction of wheel rotation.
//    static final double     COUNTS_PER_MOTOR_REV    = 537.7 ;   // eg: GoBILDA 312 RPM Yellow Jacket
//    static final double     DRIVE_GEAR_REDUCTION    = 1.0 ;     // No External Gearing.
//    static final double     WHEEL_DIAMETER_INCHES   = 4.0 ;     // For figuring circumference
//    static final double     COUNTS_PER_INCH         = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) /
//        (WHEEL_DIAMETER_INCHES * 3.1415);

    public static double squareInput(double value) {
        return value * Math.abs(value);
    }
    public static double cubeInput(double value) {
        return value * Math.abs(value) * Math.abs(value);
    }

}
