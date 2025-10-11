package org.firstinspires.ftc.teamcode.TeleOp;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.Subsystems.DriveTrain;
import org.firstinspires.ftc.teamcode.Subsystems.Intake;
import org.firstinspires.ftc.teamcode.Subsystems.Shooter;
import org.firstinspires.ftc.teamcode.Subsystems.Transfer;

@Config
@TeleOp
public class RobotIn5WeeksTeleOp extends LinearOpMode {
    public static double velocity = -1250;
    public static double intakePower = 1;
    public static double transferPower = 1;
    public static double threshold = 200;
    private boolean shooterToggle = true;
    private boolean intakeToggle = true;
    private boolean onOffToggle = true;
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

            if (currentGamepad1.b && !previousGamepad1.b) {
                intakeToggle = !intakeToggle;
            }
            if (intakeToggle) {
                telemetry.addData("TRANSFER/INTAKE","INTAKE MODE");
                if(!onOffToggle){
                    intake.run(1);
                    transfer.run(1);
                    shooter.setTargetVelocity(0);
                }
            }
            else if(!intakeToggle){
                telemetry.addData("TRANSFER/INTAKE","SHOOTER MODE");
                if(!onOffToggle){
                    shooter.setTargetVelocity(velocity);
                    intake.setGate(Intake.GateStates.CLOSE);
                    if((Math.abs(velocity)-Math.abs(shooter.getVelocity()))<threshold){
                        intake.run(-1);
                        transfer.run(-1);
                    }
                }
            }
            if (currentGamepad1.x && !previousGamepad1.x) {
                onOffToggle = !onOffToggle;
            }
            if (onOffToggle) {
               intake.run(0);
               transfer.run(0);
               shooter.setTargetVelocity(0);
                telemetry.addData("TRANSFER/INTAKE","OFF");
            }

            driveTrain.run(-gamepad1.left_stick_y,gamepad1.left_stick_x,gamepad1.right_stick_x);
            shooter.run();
            intake.gateToggle(currentGamepad1.y,previousGamepad1.y);
            intake.status(telemetry);
            telemetry.addData("Flywheel Velocity",shooter.getVelocity());
            telemetry.update();



        }
    }

}
