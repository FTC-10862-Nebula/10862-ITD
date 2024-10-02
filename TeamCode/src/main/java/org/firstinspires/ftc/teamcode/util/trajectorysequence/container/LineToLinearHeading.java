package org.firstinspires.ftc.teamcode.util.trajectorysequence.container;

public class LineToLinearHeading extends PathSegment {
    public volatile double x, y, heading;
    public LineToLinearHeading(double x, double y, double headingDegrees) {
        this.x = x;
        this.y = y;
        this.heading = headingDegrees;
    }
}
