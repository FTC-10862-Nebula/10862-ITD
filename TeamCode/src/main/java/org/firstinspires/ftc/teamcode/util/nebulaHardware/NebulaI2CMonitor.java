package org.firstinspires.ftc.teamcode.util.nebulaHardware;

import androidx.annotation.NonNull;

import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

public class NebulaI2CMonitor {
    DistanceSensor sensor;
    int cutOff, defaultOutput, brokeCount;
    boolean on;
    double readValue;

    public NebulaI2CMonitor(String name, HardwareMap hw, int cutOff, int defaultOutput){
        on = true;
        sensor = hw.get(DistanceSensor.class, name);
        this.cutOff = cutOff;
        this.defaultOutput = defaultOutput;
        brokeCount = 0;
    }

    public NebulaI2CMonitor(String name, HardwareMap hw){
        new NebulaI2CMonitor(name, hw,30, 15);
    }

    public double check(){
        readValue = defaultOutput;
        if(on){
            double startTime = System.currentTimeMillis();
            double temp = getDistance(DistanceUnit.INCH);
            if(System.currentTimeMillis()-startTime > cutOff){
                if(brokeCount++ > 5)   on = false;
            }else{
                readValue = temp;
            }
        }
        return readValue;
    }

    public double getDistance(DistanceUnit unit){
        return sensor.getDistance(unit);
    }

    public boolean working(){ return on; }

    @NonNull
    public String toString(){ return sensor.toString(); }
    public void close(){ sensor.close(); }
}
