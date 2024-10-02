package org.firstinspires.ftc.teamcode.util.nebulaHardware;

import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.arcrobotics.ftclib.hardware.motors.MotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.navigation.CurrentUnit;

public class NebulaMotor{
    public enum MotorType {
        RPM_30(5264, 30), RPM_43(3892, 43), RPM_60(2786, 60), RPM_84(1993.6, 84),
        RPM_117(1425.2, 117), RPM_223(753.2, 223), RPM_312(537.6, 312), RPM_435(383.6, 435),
        RPM_1150(145.6, 1150), RPM_1620(103.6, 1620), BARE(28, 6000), NONE(0, 0);

        private double cpr, rpm;

        MotorType(double cpr, double rpm) {
            this.cpr = cpr;
            this.rpm = rpm;
        }
        public double getCPR() {
            return cpr;
        }
        public double getRPM() {
            return rpm;
        }
        public double getAchievableMaxTicksPerSecond() {
            return cpr * rpm / 60;
        }
        public static Motor.GoBILDA getType(MotorType type){
            switch(type){
                case RPM_30: return Motor.GoBILDA.RPM_30;
                case RPM_43: return Motor.GoBILDA.RPM_43;
                case RPM_60: return Motor.GoBILDA.RPM_60;
                case RPM_84: return Motor.GoBILDA.RPM_84;
                case RPM_117: return Motor.GoBILDA.RPM_117;
                case RPM_223: return Motor.GoBILDA.RPM_223;
                case RPM_312: return Motor.GoBILDA.RPM_312;
                case RPM_435: return Motor.GoBILDA.RPM_435;
                case RPM_1150: return Motor.GoBILDA.RPM_1150;
                case RPM_1620: return Motor.GoBILDA.RPM_1620;
                case BARE: return Motor.GoBILDA.BARE;
                case NONE:
                default: return Motor.GoBILDA.NONE;
            }
        }
    }
    public enum IdleMode {
        Coast,
        Brake
    }
    public enum Direction {
        Forward, //False to Inverted
        Reverse //True to Inverted
    }
//    public enum Encoder {
//        Arm,
//        Slide,
//        None
//    }
    private final MotorEx motor;
    private final Boolean isEnabled;
//    private final Direction direction;
//    private final Motor.GoBILDA type;
//    private static MotorType type;
//    private int gearing;
    /**
     * @param hM HardwareMap
     * @param type The MotorType or the Motor RPM
     * @param deviceId The Name in the Config File
     * @param direction Direction of Servo Turning
     * @param behavior Coast or Brake Mode
     * @param isEnabled On or Off
     */
    public NebulaMotor(HardwareMap hM, String deviceId,
                       MotorType type, Direction direction,
                       IdleMode behavior, Boolean isEnabled){
        motor = new MotorEx(hM, deviceId, MotorType.getType(type));
//        NebulaMotor.type = type;
        this.isEnabled = isEnabled;
//        this.gearing = gearing;

        switch (direction){ //Initialization of Motor Direction
            case Forward:
                setInverted(false);
                break;
            case Reverse:
                setInverted(true);
                break;
        }
        setIdleMode(behavior);
        motor.resetEncoder(); //Reset Encoder at the beginning of Initializatio
//        setDistancePerPulse(); //Do Seperately

        setPower(0); //Might be unnecessary - no risk in leaving it

    }

    public void setPower(double power) {
        if (!isEnabled) motor.stopMotor();
            else motor.set(power);
    }

    private void setInverted(boolean isInverted) {
        motor.setInverted(isInverted);
    }
    public int getPosition() {
        return motor.getCurrentPosition();
    }
    public double getVelocity() {
        return motor.getVelocity();
    }
    public double getCorrectedVelocity() {
        return motor.getCorrectedVelocity();
    }
    public void stop() {
        motor.stopMotor();
    }
    public void setDistancePerPulse(double distancePerPulse) {
        motor.setDistancePerPulse(distancePerPulse);
    }
    public void resetEncoder() {
        motor.resetEncoder();
    }

    public void setIdleMode(IdleMode behavior) {
        switch(behavior){
            case Coast: motor.setZeroPowerBehavior(Motor.ZeroPowerBehavior.FLOAT);
                break;
            case Brake: motor.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE);
                break;
        }
    }
    public double getDistance() {
        return motor.getDistance();
    }
    public boolean getInverted(Direction direction) {
        switch(direction){
            case Forward:
                return false;
            case Reverse:
                return true;
        }
        return false;
    }
//    public void setDistancePerPulse(Encoder encoder){
//        //TODO: What should the conversion be to get distance in mm
//        //How would this change?
//        //The below was used previously
//        switch(encoder){
//            case Arm:
//                motor.setDistancePerPulse(360/(gearing*type.getCPR()));
//                //Math.PI/2
//                //(Counts per revolution*Gear ratio(5:1=5 / 1:5))/(Turns per revolution * 2π)
//                break;
//            case Slide:
//                motor.setDistancePerPulse((type.getCPR()*gearing)/ (_______*Math.PI));
//                //public static final double ROT_TO_INCHES = (COUNTS_PER_PULSE * GEAR_RATIO) / (GEAR_DIAMETER_INCHES * Math.PI);
//                break;
//            case None:
//                motor.setDistancePerPulse(1);
//                break;
//        }
//        motor.setDistancePerPulse(360/(gearing*type.getCPR()));
//    }


//    public void setDistancePerPulse(int CPR){
//        //TODO: What should the conversion be to get distance in mm
//        //How would this change?
//        //The below was used previously
//        motor.setDistancePerPulse(360/CPR);
//    }
    public void disable(){
        motor.disable();
    }

    public void getCurrent(){
        motor.motorEx.getCurrent(CurrentUnit.AMPS);
    }   //TODO: How is this?
    public static NebulaMotor create(HardwareMap hM, String deviceId,
                                     MotorType type, Direction direction,
                                     IdleMode behavior, Boolean isEnabled){
        return new NebulaMotor(hM, deviceId, type, direction, behavior, isEnabled);
    }

    public Motor getMotor(){
        return motor;
    }
    public Motor.Encoder getEncoder(){
        return motor.encoder;
    }
}
