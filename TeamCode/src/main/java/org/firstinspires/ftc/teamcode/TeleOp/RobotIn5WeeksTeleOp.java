package org.firstinspires.ftc.teamcode.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.Subsystems.DriveTrain;
import org.firstinspires.ftc.teamcode.Subsystems.Intake;
import org.firstinspires.ftc.teamcode.Subsystems.Shooter;
import org.firstinspires.ftc.teamcode.Subsystems.Transfer;


@TeleOp
public class RobotIn5WeeksTeleOp extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException{
        Gamepad currentGamepad1 = new Gamepad();
        Gamepad previousGamepad1 = new Gamepad();
        DriveTrain driveTrain = new DriveTrain();
        Intake intake = new Intake();
        Shooter shooter = new Shooter();
        Transfer transfer = new Transfer();

        driveTrain.initiate(hardwareMap);
        intake.initiate(hardwareMap);
        shooter.initiate(hardwareMap);
        transfer.initiate(hardwareMap);

        waitForStart();
        while(opModeIsActive()){
            previousGamepad1.copy(currentGamepad1);
            currentGamepad1.copy(gamepad1);
            driveTrain.run(-gamepad1.left_stick_y,gamepad1.left_stick_x,gamepad1.right_stick_x);

            shooter.setTarVelocity(20);
            shooter.run();


        }
    }
}
