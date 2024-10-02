package org.firstinspires.ftc.teamcode.util.trajectorysequence.container;

public class LineToSplineHeading extends PathSegment {
    public volatile double x, y, heading;
    public LineToSplineHeading(double x, double y, double headingDegrees) {
        this.x = x;
        this.y = y;
        this.heading = headingDegrees;
    }
}
