package org.firstinspires.ftc.teamcode.Subsystems;

import com.qualcomm.robotcore.hardware.HardwareMap;


public class Robot{

    public Drivetrain driveTrain;
    public Intake intake;
    public Shooter shooter;
    public Transfer transfer;

    public void initiate(HardwareMap hardwareMap){
        driveTrain.initiate(hardwareMap);
        intake.initiate(hardwareMap);
        shooter.initiate(hardwareMap);
        transfer.initiate(hardwareMap);
    }

}
