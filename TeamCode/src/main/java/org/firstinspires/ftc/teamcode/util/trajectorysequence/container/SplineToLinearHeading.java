package org.firstinspires.ftc.teamcode.util.trajectorysequence.container;

public class SplineToLinearHeading extends PathSegment {
    public volatile double x, y, heading, endHeading;
    public SplineToLinearHeading(double x, double y, double heading, double endHeadingDegrees) {
        this.x = x;
        this.y = y;
        this.heading = heading;
        this.endHeading = endHeadingDegrees;
    }
}
