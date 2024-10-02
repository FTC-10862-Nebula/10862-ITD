package org.firstinspires.ftc.teamcode.opmode.auto.misc;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import org.firstinspires.ftc.teamcode.subsystems.Claw;
import org.firstinspires.ftc.teamcode.subsystems.arm.Arm;
import org.firstinspires.ftc.teamcode.subsystems.climber.Climber;
import org.firstinspires.ftc.teamcode.subsystems.drive.mec.Drivetrain;
import org.firstinspires.ftc.teamcode.subsystems.drive.mec.MecanumDrive;
import org.firstinspires.ftc.teamcode.subsystems.intake.PowerIntake;
import org.firstinspires.ftc.teamcode.subsystems.slide.Slide;
import org.firstinspires.ftc.teamcode.subsystems.vision.ff.FFVision;
import org.firstinspires.ftc.teamcode.util.teleop.MatchOpMode;
@Disabled
@Autonomous
public class TestAutonWithoutCam extends MatchOpMode {
    private Drivetrain drivetrain;
    //    private AprilTagVision aprilTagVision;
    private FFVision FFVision;
    private PowerIntake intake;
    private Climber climber;
    private Arm arm;
    //    private Shooter shooter;
    private Slide slide;
    private Claw claw;
    @Override
    public void robotInit() {

        drivetrain = new Drivetrain(new MecanumDrive(hardwareMap, telemetry), telemetry);  //Works

//        drivetrain.init();
        FFVision = new FFVision(hardwareMap, telemetry);
        intake = new PowerIntake(telemetry, hardwareMap, true);
        arm = new Arm(telemetry, hardwareMap, true);
        climber = new Climber(telemetry, hardwareMap, true);
        claw = new Claw(telemetry, hardwareMap, true);
        slide = new Slide(telemetry, hardwareMap, true);
//        shooter = new Shooter(telemetry, hardwareMap, true);
    
        climber.setSetPointCommand(Climber.ClimbEnum.REST);
//        shooter.ready();
        while (!isStarted() && !isStopRequested()) {
            FFVision.periodic();
            telemetry.update();
        }
        this.matchStart();
    }


    public void matchStart() {}
};