package org.firstinspires.ftc.teamcode.subsystems.vision.ff;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvInternalCamera;

public class FFRectDetector {

    private OpenCvCamera camera;
    private boolean isUsingWebcam;
    private Telemetry telemetry;
    private final HardwareMap hardwareMap;
    private TeamMarkerPipeline ftclibPipeline;

    public static int CAMERA_WIDTH = 432, CAMERA_HEIGHT = 240;
    public static OpenCvCameraRotation ORIENTATION = OpenCvCameraRotation.UPRIGHT;

    // The constructor is overloaded to allow the use of webcam instead of the phone camera
    public FFRectDetector(HardwareMap hMap) {
        hardwareMap = hMap;
    }

    public FFRectDetector(HardwareMap hMap, Telemetry tl) {
        this.hardwareMap = hMap;
        this.isUsingWebcam = true;
        this.telemetry = tl;
    }

    public void init() {
        //This will instantiate an OpenCvCamera object for the camera we'll be using
        if (isUsingWebcam) {
            int cameraMonitorViewId = hardwareMap
                    .appContext.getResources()
                    .getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
            camera = OpenCvCameraFactory.getInstance()
                    .createWebcam(hardwareMap.get(WebcamName.class, "Webcam 1"), cameraMonitorViewId);
        } else {
            int cameraMonitorViewId = hardwareMap
                    .appContext.getResources()
                    .getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
            camera = OpenCvCameraFactory.getInstance()
                    .createInternalCamera(OpenCvInternalCamera.CameraDirection.BACK, cameraMonitorViewId);
        }

        //Set the pipeline the camera should use and start streaming
        camera.setPipeline(ftclibPipeline = new TeamMarkerPipeline());
        camera.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener() {
            @Override
            public void onOpened() {
                camera.startStreaming(CAMERA_WIDTH, CAMERA_HEIGHT, ORIENTATION);
            }

            @Override
            public void onError(int errorCode) {

            }
        });
    }

    public TeamMarkerPipeline.FFPosition getPosition() {
        return ftclibPipeline.getPosition();
    }

//    public void setThreshold(int threshold) {ftclibPipeline.setThreshold(threshold);}
//    public double getLeftAverage() {
//        return ftclibPipeline.getLeftAverage();
//    }
//    public double getCenterAverage() {
//        return ftclibPipeline.getCenterAverage();
//    }
//    public double getRightAverage() {
//        return ftclibPipeline.getRightAverage();
//    }

    public void setLeftRectangle(double x, double y) {
        ftclibPipeline.setLeftRectangle(x,y);
    }
    public void setCenterRectangle(double x, double y) {
        ftclibPipeline.setCenterRectangle(x,y);
    }
    public void setRightRectangle(double x, double y) {
        ftclibPipeline.setRightRectangle(x,y);
    }
    public void setRectangleSize(int w, int h) {
        ftclibPipeline.setRectangleSize(w,h);
    }
    public void stopFFVision() {
        camera.closeCameraDevice();
    }
}