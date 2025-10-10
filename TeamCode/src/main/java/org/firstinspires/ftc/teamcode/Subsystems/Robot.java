package org.firstinspires.ftc.teamcode.Subsystems;

import com.qualcomm.robotcore.hardware.HardwareMap;


public class Robot{

    public DriveTrain driveTrain;
    public Intake intake;
    public Shooter shooter;
    public Transfer transfer;

    private int ballCounter;

    public void initiate(HardwareMap hardwareMap){
        driveTrain.initiate(hardwareMap);
        intake.initiate(hardwareMap);
        shooter.initiate(hardwareMap);
        transfer.initiate(hardwareMap);
    }

    public void fireBall(){
        // Should initially be open
        intake.setGate(Intake.GateStates.CLOSE);
        transfer.run(1);
        boolean ballShot = false;
        while(!ballShot){
            double difference = shooter.getTargetVelocity()-shooter.getVelocity();
            if(difference > shooter.getDropThreshold()){ballShot = true;}
        }
        intake.run(-1);
        transfer.setKicker(Transfer.KickerStates.DOWN);
        ballShot = false;
        while(!ballShot){
            intake.run(0);
            double difference = shooter.getTargetVelocity()-shooter.getVelocity();
            if(difference > shooter.getDropThreshold()){ballShot = true;}
        }
        transfer.setKicker(Transfer.KickerStates.UP);
        while(!ballShot){
            intake.run(0);
            double difference = shooter.getTargetVelocity()-shooter.getVelocity();
            if(difference > shooter.getDropThreshold()){ballShot = true;}
        }
        activeIntake();
    }
    public void activeIntake(){
        intake.setGate(Intake.GateStates.OPEN);
        transfer.run(-1);
        intake.run(1);
    }
}
