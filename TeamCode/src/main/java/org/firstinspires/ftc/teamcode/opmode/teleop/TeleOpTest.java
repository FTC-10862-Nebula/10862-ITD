package org.firstinspires.ftc.teamcode.opmode.teleop;

import com.acmerobotics.dashboard.config.Config;

import com.arcrobotics.ftclib.gamepad.GamepadEx;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.commands.manual.DefaultDriveCommand;

import org.firstinspires.ftc.teamcode.subsystems.drive.MecDrive;

import org.firstinspires.ftc.teamcode.util.teleop.MatchOpMode;

    @Config
    @TeleOp
    @Disabled
    public class TeleOpTest extends MatchOpMode {
        //TODO: Add a on/off switch for drivetrain
        private GamepadEx driverGamepad, operatorGamepad;
        private MecDrive drive;

        public TeleOpTest() {}

        @Override
        public void robotInit() {
            driverGamepad = new GamepadEx(gamepad1);
            operatorGamepad = new GamepadEx(gamepad2);
            drive = new MecDrive(hardwareMap);  //Works

        }


        @Override
        public void configureButtons() {

            //Driver
            drive.setDefaultCommand(new DefaultDriveCommand(drive, driverGamepad));


            //y - up/dowm
            //x- right left
        }

        @Override
        public void matchStart() {

        }

    /*@Override
    public void matchLoop() {}
    @Override
    public void disabledPeriodic() { }
    @Override
    public void robotPeriodic(){
    }*/
    }
