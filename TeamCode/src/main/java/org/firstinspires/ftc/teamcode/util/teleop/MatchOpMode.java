package org.firstinspires.ftc.teamcode.util.teleop;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.canvas.Canvas;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.arcrobotics.ftclib.command.Command;
import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.InstantCommand;

import java.util.HashMap;
import java.util.Map;

public abstract class MatchOpMode extends CommandOpMode {

    protected FtcDashboard dashboard = FtcDashboard.getInstance();
    public static Map<String, Object> telemetryList = new HashMap<>();
    public static Canvas canvas = new Canvas();
    @Override
    public void initialize() {
        
        telemetryList.clear();
        canvas.clear();
        dashboard.setTelemetryTransmissionInterval(50);
        robotInit();
        configureButtons();
        while(!isStarted() && !isStopRequested()) {
            disabledPeriodic();
            robotPeriodic();
            telemetry.update();
        }
    }

    public void runOpMode() throws InterruptedException {
        initialize();

        waitForStart();
        matchStart();
        // run the scheduler
        while (!isStopRequested() && opModeIsActive()) {
            TelemetryPacket newPacket = new TelemetryPacket();
            canvas = newPacket.fieldOverlay();
            run();
            matchLoop();
            newPacket.putAll(telemetryList);
            telemetry.update();
            dashboard.sendTelemetryPacket(newPacket);
            robotPeriodic();
        }
        reset();
    }

    public abstract void robotInit();
    public void configureButtons() {}
    public void disabledPeriodic() {}
    public abstract void matchStart();
    public void matchLoop() {}
    public void robotPeriodic() {}
    protected Command run(Runnable runnable) {
        return new InstantCommand(runnable);
    }
}