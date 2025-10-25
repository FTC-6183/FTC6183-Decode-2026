package org.firstinspires.ftc.teamcode.OLDSubsystems;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;


public class RobotOLD {

    public DriveTrainOLD driveTrain;
    public IntakeOLD intake;
    public ShooterOLD shooter;
    public TransferOLD transfer;

    private int ballCounter;

    public void initiate(){
        driveTrain.initiate(hardwareMap);
        intake.initiate(hardwareMap);
        shooter.initiate(hardwareMap);
        transfer.initiate(hardwareMap);
    }
    public void shooterMode(double velocity, double threshold){
        shooter.setTargetVelocity(velocity);
        intake.setGate(IntakeOLD.GateStates.CLOSE);
        if((Math.abs(velocity)-Math.abs(shooter.getVelocity()))<threshold){
            intake.run(-1);
            transfer.run(-1);
        }
    }
    public void intakeMode(){
        shooter.setTargetVelocity(0);
        intake.setGate(IntakeOLD.GateStates.OPEN);
        intake.run(1);
        transfer.run(1);
    }
    public void off(){
        shooter.setTargetVelocity(0);
        intake.run(0);
        transfer.run(0);
    }


    public void fireBall(){
        intake.setGate(IntakeOLD.GateStates.CLOSE);
        transfer.run(1);
        boolean ballShot = false;
        while(!ballShot){
            double difference = shooter.getTargetVelocity()-shooter.getVelocity();
            if(difference > shooter.getDropThreshold()){ballShot = true;}
        }
        intake.run(-1);
        ballShot = false;
        while(!ballShot){
            intake.run(0);
            double difference = shooter.getTargetVelocity()-shooter.getVelocity();
            if(difference > shooter.getDropThreshold()){ballShot = true;}
        }
        //transfer.setKicker(Transfer.KickerStates.UP);
        while(!ballShot){
            intake.run(0);
            double difference = shooter.getTargetVelocity()-shooter.getVelocity();
            if(difference > shooter.getDropThreshold()){ballShot = true;}
        }
        activeIntake();
    }
    public void activeIntake(){
        intake.setGate(IntakeOLD.GateStates.OPEN);
        transfer.run(-1);
        intake.run(1);
    }
}
