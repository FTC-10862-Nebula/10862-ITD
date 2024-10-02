package org.firstinspires.ftc.teamcode.opmode.auto.misc;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import org.firstinspires.ftc.teamcode.subsystems.vision.aprilTag.AprilTagVision;
import org.firstinspires.ftc.teamcode.subsystems.vision.ff.FFVision;
import org.firstinspires.ftc.teamcode.util.teleop.MatchOpMode;
@Disabled
@Autonomous
public class AprilTagAuto extends MatchOpMode {
    private AprilTagVision aprilTagVision;
    
    @Override
    public void robotInit() {
        aprilTagVision = new AprilTagVision(hardwareMap, telemetry);
    }
    
    @Override
    public void disabledPeriodic() {
//        aprilTagVision.setPosition(FFVision.getPosition());
//        aprilTagVision.periodic();
        telemetry.update();
    }
    
    public void matchStart() {
//        TeamMarkerPipeline.FFPosition position = vision.getPosition();
    }
}