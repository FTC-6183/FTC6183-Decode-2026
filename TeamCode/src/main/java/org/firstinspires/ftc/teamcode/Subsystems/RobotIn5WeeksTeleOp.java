package org.firstinspires.ftc.teamcode.Subsystems;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;

@TeleOp
public class RobotIn5WeeksTeleOp extends LinearOpMode {
    DcMotor transfer;

    @Override
    public void runOpMode() throws InterruptedException{
        Gamepad currentGamepad1 = new Gamepad();
        Gamepad previousGamepad1 = new Gamepad();

        Drivetrain driveTrain = new Drivetrain();
        Intake intake = new Intake();
        Shooter shooter = new Shooter();
        Transfer transfer = new Transfer();

        waitForStart();
        while(opModeIsActive()){
            previousGamepad1.copy(currentGamepad1);
            currentGamepad1.copy(gamepad1);

            driveTrain.initiate(hardwareMap);
            intake.initiate(hardwareMap);
            shooter.initiate(hardwareMap);
            transfer.initiate(hardwareMap);

            driveTrain.run(-gamepad1.left_stick_y,gamepad1.left_stick_x,gamepad1.right_stick_x);
            shooter.run();


        }
    }
}
