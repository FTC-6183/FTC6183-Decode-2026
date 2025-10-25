package org.firstinspires.ftc.teamcode.OLDSubsystems;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

@Config
public class TransferOLD {
    DcMotor transfer;
    //Servo kicker;

    public enum KickerStates{
        UP,
        DOWN
    }

    public static double kickUpPosition = 1;
    public static double kickDownPosition = 0;

    private boolean transferFlag;
    private KickerStates currentKickerState = KickerStates.UP;

    public void initiate(HardwareMap hardwareMap){
        transfer = hardwareMap.dcMotor.get("trans");
    }

    public KickerStates getKickerState(){return currentKickerState;}
/*
public void setKicker(KickerStates input){
        if(input == KickerStates.UP){
            kicker.setPosition(kickUpPosition);
        }
        else if(input == KickerStates.DOWN){
            kicker.setPosition(kickDownPosition);
        }
    }


 */

    public void run(double power){
        transfer.setPower(power);
    }

    public void transferBallConditional(double power, boolean current, boolean previous){
        if (current && !previous) {
            transferFlag = !transferFlag;
        }
        if (transferFlag) {
            transfer.setPower(1);
        }
        else{
            transfer.setPower(-1);
        }
    }
    public void transferReverseBallConditional(double power, boolean current, boolean previous){
        if (current && !previous) {
            transferFlag = !transferFlag;
        }
        if (transferFlag) {
            transfer.setPower(-power);
        }
        else{
            transfer.setPower(0);
        }
    }



}
