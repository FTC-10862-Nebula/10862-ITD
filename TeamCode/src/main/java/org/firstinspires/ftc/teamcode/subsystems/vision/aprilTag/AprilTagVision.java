package org.firstinspires.ftc.teamcode.subsystems.vision.aprilTag;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.openftc.apriltag.AprilTagDetection;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;

import java.util.ArrayList;

public class AprilTagVision extends SubsystemBase {
    public enum AprilTag {
        TWO(2),
        ONE(1),
        ZERO(0);
        
        public final int tagNum;
        AprilTag(int tagNum) {
            this.tagNum = tagNum;
        }
    }
    OpenCvCamera camera;
    private Telemetry telemetry;
    AprilTagDetectionPipeline aprilTagDetectionPipeline;

    static final double FEET_PER_METER = 3.28084;

    // Lens intrinsics
    // UNITS ARE PIXELS
    // NOTE: this calibration is for the C920 webcam at 800x448.
    // You will need to do your own calibration for other configurations!
    double fx = 578.272;
    double fy = 578.272;
    double cx = 402.145;
    double cy = 221.506;

    // UNITS ARE METERS
    double tagsize = 0.4;

    // Tag ID 1,2,3 from the 36h11 family
    int LEFT = 1,
        MIDDLE = 2,
        RIGHT = 3,
    tx,ty,rx;
    public AprilTag tagOfInterest = AprilTag.ZERO;

//    AprilTagDetection tagOfInterest = null;
    boolean tagFound = false;
    int tagFoundNum = 0;

    public AprilTagVision(HardwareMap hw, Telemetry tl)
    {
        this.telemetry=tl;

        int cameraMonitorViewId = hw.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hw.appContext.getPackageName());
        camera = OpenCvCameraFactory.getInstance().createWebcam(hw.get(WebcamName.class, "Webcam 1"), cameraMonitorViewId);
        aprilTagDetectionPipeline = new AprilTagDetectionPipeline(tagsize, fx, fy, cx, cy);

        camera.setPipeline(aprilTagDetectionPipeline);
        camera.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener()
        {
            @Override
            public void onOpened()
            {
                camera.startStreaming(1000,800, OpenCvCameraRotation.UPRIGHT);
//                camera.startStreaming(800,448, OpenCvCameraRotation.UPRIGHT);
            }

            @Override
            public void onError(int errorCode)
            {
                tl.addLine("ERROR for TagVision!");
            }
        });
    }

    @Override
    public void periodic()
    {
    
    }
    public void moveBot(){
    
    }

//    public int getTag() {
////        return tagFoundNum;
//
//        if (tagFoundNum == 1) {
//            telemetry.addLine("Found 1 - Left");
//            return 1;
//
//        } else if (tagFoundNum == 2) {
//            telemetry.addLine("Found 2 - Mid");
//            return 2;
//        } else if (tagFoundNum == 3) {
//            telemetry.addLine("Found 3 - Right");
//            return 3;
//        } else {
//            telemetry.addLine("Returning 1 - Left (Not Found)");
//            return 1;
//        }
//    }

//    public void updateTagOfInterest() {
//        ArrayList<AprilTagDetection> currentDetections = aprilTagDetectionPipeline.getLatestDetections();
//        tagFound = false;
//        if (currentDetections.size() == 0 || tagFoundNum == 0) {
//            telemetry.addLine("Tag In Sight: Not Found :( ");
//            return;
//        }
//
//        for(AprilTagDetection tag : currentDetections) {
//            if(tag.id == LEFT || tag.id == MIDDLE || tag.id == RIGHT) {
//                tagFound = true;
//                tagFoundNum = tag.id;
//            }
//            telemetry.addData("Tag In Sight: ", tagFoundNum);
//        }
//    }


    public void stopTagVision() {
        camera.closeCameraDevice();
    }
    public void changeTagOfInterest(AprilTag tagOfInterest){
        this.tagOfInterest = tagOfInterest;
        ArrayList<AprilTagDetection> currentDetections = aprilTagDetectionPipeline.getLatestDetections();
        
        for(AprilTagDetection tag : currentDetections) {
            if(tag.id == LEFT || tag.id == MIDDLE || tag.id == RIGHT) {
                tagFound = true;
                tagFoundNum = tag.id;
            }
            telemetry.addData("Tag In Sight: ", tagFoundNum);
        }
    
    }

}
