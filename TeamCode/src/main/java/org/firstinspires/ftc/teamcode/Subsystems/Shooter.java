package org.firstinspires.ftc.teamcode.Subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.arcrobotics.ftclib.controller.PIDFController;

public class Shooter {

    private DcMotorEx shooterMotor;
    boolean shooterFlag;
    private double targetVelocity = -1500;
    private double dropThreshold;

    public static double kP = 0.05;
    public static double kI = 0;
    public static double kD = 0;
    public static double kF = 0;
    public static double velocityTolerance = 0;
    PIDFController pidfController = new PIDFController(kP, kI, kD, kF);

    public void initiate(HardwareMap hardwareMap){
       shooterMotor = hardwareMap.get(DcMotorEx.class,"shooter");
       reset();
    }

    public void reset(){
        shooterMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        shooterMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    public void run(){

            pidfController.setP(kP);
            pidfController.setI(kI);
            pidfController.setF(kF);
            pidfController.setD(kD);
            pidfController.setTolerance(velocityTolerance);
            double power = 0;
            double maxPower = 1;

            power = pidfController.calculate(shooterMotor.getVelocity(),targetVelocity);
            if(Math.abs(power)>maxPower){
                power = (maxPower * Math.signum(power));
            }
            if(targetVelocity == 0){
                shooterMotor.setPower(0);
            }
            else{
                shooterMotor.setPower(power);
            }
        }
    public void setTargetVelocity(double velocity){targetVelocity = velocity;}

    public double getTargetVelocity(){return targetVelocity;}

    public double getVelocity(){return shooterMotor.getVelocity();}

    public double getDropThreshold(){return dropThreshold;}
    }




