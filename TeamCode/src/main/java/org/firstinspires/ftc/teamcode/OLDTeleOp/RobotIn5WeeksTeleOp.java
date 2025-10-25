package org.firstinspires.ftc.teamcode.OLDTeleOp;

import static org.firstinspires.ftc.teamcode.OLDSubsystems.IntakeOLD.gateClosePosition;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.OLDSubsystems.DriveTrainOLD;
import org.firstinspires.ftc.teamcode.OLDSubsystems.IntakeOLD;
import org.firstinspires.ftc.teamcode.OLDSubsystems.ShooterOLD;
import org.firstinspires.ftc.teamcode.OLDSubsystems.TransferOLD;

@Config
@TeleOp
public class RobotIn5WeeksTeleOp extends LinearOpMode {
    public static double velocity = -1500;
    public static double escapeVelocity = 300;
    public static double intakePower = 1;
    public static double transferPower = 1;
    public static double threshold = 10;
    private boolean intakeToggle = true;
    private boolean onOffToggle = true;
    ElapsedTime timer = new ElapsedTime();
    public static double delay = 1;

    @Override
    public void runOpMode() throws InterruptedException {
        Gamepad currentGamepad1 = new Gamepad();
        Gamepad previousGamepad1 = new Gamepad();
        DriveTrainOLD driveTrain = new DriveTrainOLD();
        IntakeOLD intake = new IntakeOLD();
        ShooterOLD shooter = new ShooterOLD();
        TransferOLD transfer = new TransferOLD();
        telemetry = new MultipleTelemetry(telemetry,FtcDashboard.getInstance().getTelemetry());
        waitForStart();
        while (opModeIsActive()) {
            driveTrain.initiate(hardwareMap);
            intake.initiate(hardwareMap);
            shooter.initiate(hardwareMap);
            transfer.initiate(hardwareMap);

            previousGamepad1.copy(currentGamepad1);
            currentGamepad1.copy(gamepad1);
            if (currentGamepad1.left_bumper && !previousGamepad1.left_bumper) {
                intakeToggle = !intakeToggle;
            }
            if (currentGamepad1.right_bumper && !previousGamepad1.right_bumper) {
                onOffToggle = !onOffToggle;
            }
            if (intakeToggle) {
                telemetry.addData("TRANSFER/INTAKE", "INTAKE MODE");
                if (!onOffToggle) {
                    shooter.setTargetVelocity(0);
                    intake.setGate(IntakeOLD.GateStates.OPEN);
                    intake.run(1);
                    transfer.run(1);
                }
            } else {
                telemetry.addData("TRANSFER/INTAKE", "SHOOTER MODE");
                if (!onOffToggle) {
                    intake.setGate(IntakeOLD.GateStates.CLOSE);
                    if (intake.getPosition() == gateClosePosition) {
                        shooter.setTargetVelocity(velocity);
                        if ((Math.abs(velocity) - Math.abs(shooter.getVelocity())) < threshold) {
                            timer.reset();
                            telemetry.addData("Timer", timer.time());
                            if (timer.time() >= delay) {
                                intake.run(-1);
                                transfer.run(-1);
                            }
                        }
                    }
                }
            }


                if (onOffToggle) {
                    shooter.setTargetVelocity(0);
                    intake.run(0);
                    transfer.run(0);
                    telemetry.addData("TRANSFER/INTAKE", "OFF");
                }
                if (currentGamepad1.right_trigger > 0.3) {
                    shooter.setTargetVelocity(escapeVelocity);
                }

                driveTrain.run(-gamepad1.left_stick_y, gamepad1.left_stick_x, gamepad1.right_stick_x);
                shooter.run();
                intake.gateToggle(currentGamepad1.y, previousGamepad1.y);
                intake.status(telemetry);
                telemetry.addData("Flywheel Velocity", shooter.getVelocity());
                telemetry.update();
                telemetry.update();

        }
    }
}

    /*
    public void activeIntake(){
        intake.setGate(Intake.GateStates.OPEN);
        transfer.run(-1);
        intake.run(1);
    }
    public void shooterMode(double velocity, double threshold){
        shooter.setTargetVelocity(velocity);
        intake.setGate(Intake.GateStates.CLOSE);
        if((Math.abs(velocity)-Math.abs(shooter.getVelocity()))<threshold){
            intake.run(-1);
            transfer.run(-1);
        }
    }
    public void intakeMode(){
        shooter.setTargetVelocity(0);
        intake.setGate(Intake.GateStates.OPEN);
        intake.run(1);
        transfer.run(1);
    }
    public void off(){
        shooter.setTargetVelocity(0);
        intake.run(0);
        transfer.run(0);
    }
    */



