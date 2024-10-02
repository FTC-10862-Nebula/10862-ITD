package org.firstinspires.ftc.teamcode.subsystems.vision.ff;

public class TeamMarkerPipeline extends FFRectMarkerPipeline {

//    public static double MIN = 105;
//    public static double MAX = 110;

    public void setLeftRectangle(double x, double y) {
        setLeftRectHeightPercentage(y);
        setLeftRectWidthPercentage(x);
    }
    public void setCenterRectangle(double x, double y) {
        setCenterRectHeightPercentage(y);
        setCenterRectWidthPercentage(x);
    }
    public void setRightRectangle(double x, double y) {
        setRightRectHeightPercentage(y);
        setRightRectWidthPercentage(x);
    }
    public void setRectangleSize(int w, int h) {
        setRectangleHeight(h);
        setRectangleWidth(w);
    }

    public FFPosition getPosition() {
//        Util.logger(this, Level.INFO, "Left Avg: ", getLeftAverage());

        if(getLeftAverage() > getCenterAverage() && getLeftAverage() > getRightAverage()){
            return FFPosition.LEFT;
        }
        else if(getCenterAverage() > getLeftAverage() && getCenterAverage() > getRightAverage()){
            return FFPosition.MIDDLE;
        }
        else if(getRightAverage() > getLeftAverage() && getRightAverage() > getCenterAverage()){
            return FFPosition.RIGHT;
        }
        else{
            return FFPosition.LEFT;
        }
    }

    public enum FFPosition  {
        LEFT,
        MIDDLE,
        RIGHT;
    }

}