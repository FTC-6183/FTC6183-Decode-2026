package org.firstinspires.ftc.teamcode.OLDAutonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.OLDSubsystems.DriveTrainOLD;
import org.firstinspires.ftc.teamcode.OLDSubsystems.IntakeOLD;
import org.firstinspires.ftc.teamcode.OLDSubsystems.ShooterOLD;
import org.firstinspires.ftc.teamcode.OLDSubsystems.TransferOLD;

@Autonomous
public class OLDFirePreloadMoveLeft extends LinearOpMode{
    public static double threshold = 20;
    public static double velocity = -1500;
    ElapsedTime timer = new ElapsedTime();
    @Override
    public void runOpMode() throws InterruptedException {
        DriveTrainOLD driveTrain = new DriveTrainOLD();
        IntakeOLD intake = new IntakeOLD();
        ShooterOLD shooter = new ShooterOLD();
        TransferOLD transfer = new TransferOLD();

        driveTrain.initiate(hardwareMap);
        intake.initiate(hardwareMap);
        shooter.initiate(hardwareMap);
        transfer.initiate(hardwareMap);
        waitForStart();
        while(opModeIsActive()) {
            timer.reset();
            while(timer.time()<=20){
                shooter.setTargetVelocity(velocity);
                shooter.run();
                intake.setGate(IntakeOLD.GateStates.CLOSE);
                if ((Math.abs(velocity) - Math.abs(shooter.getVelocity())) < threshold) {
                    intake.run(-1);
                    transfer.run(-1);
                }
            }
            timer.reset();
            while(timer.time()<=0.5){
                shooter.setTargetVelocity(0);
                intake.run(0);
                transfer.run(0);
                driveTrain.run(0,-0.3,0);
            }
            sleep(30000);
        }
    }
}
