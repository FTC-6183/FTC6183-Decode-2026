package org.firstinspires.ftc.teamcode.Subsystems;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class Transfer {
    DcMotor transfer;
    private boolean transferFlag;

    public void initiate(HardwareMap hardwareMap){
        transfer = hardwareMap.dcMotor.get("trans");
    }

    public void transferBallConditional(boolean current, boolean previous){
        if (current && !previous) {
            transferFlag = !transferFlag;
        }
        if (transferFlag) {
            transfer.setPower(1);
        }
        else{
            transfer.setPower(0);
        }
    }

    public void run(double power){
        transfer.setPower(power);
    }
}
