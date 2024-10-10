package org.firstinspires.ftc.teamcode.opmode.auto;


import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.subsystems.vision.Vision;
import org.firstinspires.ftc.teamcode.util.teleop.MatchOpMode;

//@Disabled
@Autonomous(preselectTeleOp = "TeleOpMain")
public class NewVision extends MatchOpMode {
    private Vision vision = new Vision(hardwareMap, telemetry);
    @Override
    public void robotInit() {
        vision.init();

    }

    @Override
    public void disabledPeriodic() {

    }

    public void matchStart() {
        schedule(
//            new ParallelCommandGroup(
//                new InstantCommand(
//                ()-> Actions.runBlocking(
//                    new SequentialAction(
//                        pathOne, // Example of a drive action
//                        new ParallelAction( // several actions being run in parallel
//                            pathTwo
////                            intake.setPositionAction(Intake.Value.INTAKE)//TODO: Does this work
//                        )
//                    )
//                ))
////                intake.setPosition(Intake.Value.INTAKE)//TODO: Or This
//            ),
//            run(() -> MecDrive.currentPose = drive.getPose()),
//            run(this::stop)
        );
    }
}