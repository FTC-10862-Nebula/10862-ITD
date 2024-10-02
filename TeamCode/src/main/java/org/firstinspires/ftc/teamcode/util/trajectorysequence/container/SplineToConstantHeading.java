package org.firstinspires.ftc.teamcode.util.trajectorysequence.container;

public class SplineToConstantHeading extends PathSegment {
    public volatile double x, y, endHeading;
    public SplineToConstantHeading(double x, double y, double endHeadingDegrees) {
        this.x = x;
        this.y = y;
        this.endHeading = endHeadingDegrees;
    }
}
