package org.firstinspires.ftc.teamcode.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Subsystems.DriveTrain;
import org.firstinspires.ftc.teamcode.Subsystems.Intake;
import org.firstinspires.ftc.teamcode.Subsystems.Shooter;
import org.firstinspires.ftc.teamcode.Subsystems.Transfer;

@Autonomous
public class FirePreloadMoveRight extends LinearOpMode{
    public static double threshold = 20;
    public static double velocity = -1500;
    ElapsedTime timer = new ElapsedTime();
    @Override
    public void runOpMode() throws InterruptedException {
        DriveTrain driveTrain = new DriveTrain();
        Intake intake = new Intake();
        Shooter shooter = new Shooter();
        Transfer transfer = new Transfer();

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
                intake.setGate(Intake.GateStates.CLOSE);
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
                driveTrain.run(0,0.3,0);
            }
            sleep(30000);
        }
    }
}