package org.firstinspires.ftc.teamcode.subsystems.vision;


import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.hardware.ServoEx;
import com.arcrobotics.ftclib.hardware.SimpleServo;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

@Config
public class CameraServo extends SubsystemBase {
    //Claw Variables
    public final double blueRight = 0.4,
                        blueLeft = 0.98;

    Telemetry telemetry;
    private final ServoEx camServo;

    public CameraServo(HardwareMap hw, Telemetry tl) {

        this.camServo = new SimpleServo(hw, "camServo", 0, 360);
        this.camServo.setPosition(0.5);  //Port
        this.telemetry = tl;
    }

    @Override
    public void periodic() {
        telemetry.addData("CAM Servo 1 Pos: ", camServo.getPosition());
    }

    public void set(double clawServo1Pos) {
        camServo.setPosition(clawServo1Pos);
    }

}