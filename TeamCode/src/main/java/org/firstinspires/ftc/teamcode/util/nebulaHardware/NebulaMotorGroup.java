package org.firstinspires.ftc.teamcode.util.nebulaHardware;

/**
 * Allows multiple {@link NebulaMotor} objects to be linked together
 * as a single group. Multiple motors will act together.
 *
 */
//NO WORK -TO FIX
public class NebulaMotorGroup {
    private final NebulaMotor[] group;
//    private final MotorGroup group2;

    /**
     * Create a new NebulaMotorGroup with the provided Motors.
     *
     * @param motors The motors to add.
     */
    public NebulaMotorGroup(NebulaMotor... motors) {
        group = motors;
//        group = new MotorGroup(group[0].getMotor(), group[1].getMotor());
    }


    /**
     * Set the speed for each motor in the group
     *
     * @param speed The speed to set. Value should be between -1.0 and 1.0.
     */
    public void setPower(double speed) {
        for (NebulaMotor x : group) {//TODO:Test?
//            x.setPower(x.getInverted() ? -speed : speed);
            x.setPower(speed);
        }
    }

    /**
     * Stops all motors in the group.
     */
    public void stop() {
        for (NebulaMotor x : group) {
            x.stop();
        }
    }
    public int getPosition() {
        return group[0].getPosition();
    }
    public double getVelocity() {
        return group[0].getVelocity();
    }
    public double getCorrectedVelocity() {
        return group[0].getCorrectedVelocity();
    }
    public void resetEncoder() {
        for (NebulaMotor x : group) {
            x.resetEncoder();
        }
    }
    /**
     * Disables all the motor devices.
     */
    public void disable(){
        for (NebulaMotor x : group) {
            x.disable();
        }
    }
    public void setDistancePerPulse(double pulse) {
        for (NebulaMotor x : group) {
            x.setDistancePerPulse(pulse);
        }
    }

}
