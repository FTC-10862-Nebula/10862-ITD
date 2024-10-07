package org.firstinspires.ftc.teamcode.opmode.auto;


import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.ParallelAction;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.ParallelCommandGroup;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.subsystems.drive.MecDrive;
import org.firstinspires.ftc.teamcode.subsystems.vision.SampleDetectionPipeline;
import org.firstinspires.ftc.teamcode.util.teleop.MatchOpMode;

//@Disabled
@Autonomous(preselectTeleOp = "TeleOpMain")
public class NewVision extends MatchOpMode {
    SampleDetectionPipeline pipeline = new SampleDetectionPipeline();
    @Override
    public void robotInit() {

    }

    @Override
    public void disabledPeriodic() {
//        pipeline.init();

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