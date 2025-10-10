package org.firstinspires.ftc.teamcode.Subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class Transfer {
    DcMotor transfer;
    Servo kicker;

    public enum KickerStates{
        UP,
        DOWN
    }

    private double kickUpPosition = 1;
    private double kickDownPosition = 0;
    private double kickPosition;

    private boolean transferFlag;
    private KickerStates currentKickerState = KickerStates.UP;

    public void initiate(HardwareMap hardwareMap){
        transfer = hardwareMap.dcMotor.get("trans");
        kicker = hardwareMap.servo.get("kick");
    }

    public KickerStates getKickerState(){return currentKickerState;}

    public void setKicker(KickerStates input){
        if(input == KickerStates.UP){
            kicker.setPosition(kickUpPosition);
        }
        else if(input == KickerStates.DOWN){
            kicker.setPosition(kickDownPosition);
        }
    }

    public void run(double power){
        transfer.setPower(power);
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


}
