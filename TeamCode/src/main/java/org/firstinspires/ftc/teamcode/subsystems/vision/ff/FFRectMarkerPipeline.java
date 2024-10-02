package org.firstinspires.ftc.teamcode.subsystems.vision.ff;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvPipeline;

public class FFRectMarkerPipeline extends OpenCvPipeline {

    //We declare the mats on top so we can reuse them later to avoid memory leaks
    private Mat matHSV = new Mat();
    private Mat matHsvLeft = new Mat();
    private Mat matHsvCenter = new Mat();
    private Mat matHsvRight = new Mat();
    private Mat leftBlock = new Mat();
    private Mat centerBlock = new Mat();
    private Mat rightBlock = new Mat();

    //Where the average value of the rectangles are stored
    private double leftAverage;
    private double centerAverage;
    private double rightAverage;

    //The max difference allowed inside the rectangles
    private int threshold = 15;

    //The position related to the screen
    public double leftRectWidthPercentage = 0.05;
    public double leftRectHeightPercentage = 0.3;
    public double centerRectWidthPercentage = 0.47;
    public double centerRectHeightPercentage = 0.3;
    public double rightRectWidthPercentage = 0.72;
    public double rightRectHeightPercentage = 0.3;

    //The width and height of the rectangles in terms of pixels
    public int rectangleWidth = 10;
    public int rectangleHeight = 10;
    
    private final int thickness = 2;//Thickness of Line for Rectangles

    @Override
    public Mat processFrame(Mat input) {
        /**
         *input which is in RGB is the frame the camera gives
         *We convert the input frame to the color space matYCrCb
         *Then we store this converted color space in the mat matYCrCb
         *For all the color spaces go to
         *https://docs.opencv.org/3.4/d8/d01/group__imgproc__color__conversions.html
         */
        Imgproc.cvtColor(input, matHSV, Imgproc.COLOR_RGB2HSV);
        //^^Change this for different image
        //COLOR_RGB2HSV
        //CV_CHAIN_CODE
        //COLOR_COLORCVT_MAX - no work
        //COLOR_GRAY2RGB - no work
        //COLOR_RGBA2GRAY no work
        //COLOR_BGR2HSV - Works well for red and blue

        Mat display = new Mat();
        Core.extractChannel(matHSV, display, 1);

        //The points needed for the rectangles are calculated here
        Rect leftRect = new Rect(
                (int) (matHSV.width() * leftRectWidthPercentage),
                (int) (matHSV.height() * leftRectHeightPercentage),
                rectangleWidth,
                rectangleHeight
        );

        //The points needed for the rectangles are calculated here
        Rect centerRect = new Rect(
                (int) (matHSV.width() * centerRectWidthPercentage),
                (int) (matHSV.height() * centerRectHeightPercentage),
                rectangleWidth,
                rectangleHeight
        );

        Rect rightRect = new Rect(
                (int) (matHSV.width() * rightRectWidthPercentage),
                (int) (matHSV.height() * rightRectHeightPercentage),
                rectangleWidth,
                rectangleHeight
        );

        
        drawRectOnToMat(display, leftRect, new Scalar(255, 255, 225));
        drawRectOnToMat(display, centerRect, new Scalar(255, 225, 255));
        drawRectOnToMat(display, rightRect, new Scalar(255, 255, 225));

        //We crop the image so it is only everything inside the rectangles
        // and find the cb value inside of them
        //Hmm, what is the cb value
        leftBlock = matHSV.submat(leftRect);
        centerBlock = matHSV.submat(centerRect);
        rightBlock = matHSV.submat(rightRect);
        Core.extractChannel(leftBlock, matHsvLeft, 1);
        Core.extractChannel(centerBlock, matHsvCenter, 1);
        Core.extractChannel(rightBlock, matHsvRight, 1);

        //We take the average
        Scalar leftMean = Core.mean(matHsvLeft);
        Scalar centerMean = Core.mean(matHsvCenter);
        Scalar rightMean = Core.mean(matHsvRight);

        leftAverage = leftMean.val[0];
        centerAverage = centerMean.val[0];
        rightAverage = rightMean.val[0];

        //return the mat to be shown onto the screen
        return display;
    }

    /**
     * Draw the rectangle onto the desired mat
     *
     * @param mat   The mat that the rectangle should be drawn on
     * @param rect  The rectangle
     * @param color The color the rectangle will be
     */
    private void drawRectOnToMat(Mat mat, Rect rect, Scalar color) {
        Imgproc.rectangle(mat, rect, color, thickness); //Thickness of Line
    }

    public double getLeftAverage() {
        return leftAverage;
    }

    public double getCenterAverage() {
        return centerAverage;
    }

    public double getRightAverage() {
        return rightAverage;
    }

    public void setThreshold(int threshold) {
        this.threshold = threshold;
    }

    public int getThreshold() {
        return threshold;
    }

    public void setLeftRectWidthPercentage(double topRectWidthPercentage) {
        this.leftRectWidthPercentage = topRectWidthPercentage;
    }

    public void setLeftRectHeightPercentage(double topRectWidthPercentage) {
        this.leftRectHeightPercentage = topRectWidthPercentage;
    }

    public void setCenterRectWidthPercentage(double topRectHeightPercentage) {
        this.centerRectWidthPercentage = topRectHeightPercentage;
    }

    public void setCenterRectHeightPercentage(double topRectHeightPercentage) {
        this.centerRectHeightPercentage = topRectHeightPercentage;
    }

    public void setRightRectWidthPercentage(double topRectHeightPercentage) {
        this.rightRectWidthPercentage = topRectHeightPercentage;
    }

    public void setRightRectHeightPercentage(double topRectHeightPercentage) {
        this.rightRectHeightPercentage = topRectHeightPercentage;
    }

    public void setRectangleWidth(int rectangleWidth) {
        this.rectangleWidth = rectangleWidth;
    }

    public void setRectangleHeight(int rectangleHeight) {
        this.rectangleHeight = rectangleHeight;
    }

}
