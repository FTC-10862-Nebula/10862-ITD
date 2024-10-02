package org.firstinspires.ftc.teamcode.util.trajectorysequence.container;

public class Turn extends PathSegment {
    public volatile double angleDegrees;
    public Turn(double angleDegrees) {
        this.angleDegrees = angleDegrees;
    }
}
