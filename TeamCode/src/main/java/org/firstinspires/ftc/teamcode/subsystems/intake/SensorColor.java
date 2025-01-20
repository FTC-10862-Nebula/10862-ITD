package org.firstinspires.ftc.teamcode.subsystems.intake;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
    public class SensorColor extends SubsystemBase {
        private final ColorSensor colorSensor;
        private final Telemetry telemetry;
        
        //red (255,0,0)
        //blue (0,0,255)
        //yellow (0,255, 255)
        
        public SensorColor(Telemetry tl, HardwareMap hardwareMap) {
            this.colorSensor = hardwareMap.get(ColorSensor.class, "colorS");
            this.telemetry = tl;
            this.colorSensor.enableLed(true);
        }

        public void periodic() {
           telemetry.addData("grabSample:", grabbedYellowSample());

            telemetry.update();
        }

        public boolean grabbedYellowSample() {
            telemetry.addLine("Got yellow Sample");
            return (colorSensor.blue() > 130 &&
                    colorSensor.red() > 470 &&
                    colorSensor.green() > 555);
        }

        public boolean grabbedSample(Intake.Sample sample) {
            if(Intake.Sample.BLUE==sample){
                return (colorSensor.blue() > 290 &&
                        colorSensor.green() > 135)&&
                        colorSensor.red()>70;
                
            } else if(Intake.Sample.RED==sample){
                return (colorSensor.red() > 355 &&
                    colorSensor.green() > 165 &&
                    colorSensor.blue() > 75);
            } else{
                return false;
            }
        }
        public int red(){
            return colorSensor.red();
        }
        public int blue(){
            return colorSensor.blue();
        }
        public int green(){
            return colorSensor.green();
        }
        
//        public Intake.Sample getSampleColors(){
//            if(colorSensor.red()>255){
//                return Intake.Sample.RED; //TODO: Works?
//            } else if(colorSensor.green()>255&&colorSensor.blue()>255){
//                return Intake.Sample.YELLOW; //TODO: Works?
//            } else if(colorSensor.blue()>255){
//                return Intake.Sample.BLUE; //TODO: Works?
//            } else return  Intake.Sample.NONE;
//        }
    }
