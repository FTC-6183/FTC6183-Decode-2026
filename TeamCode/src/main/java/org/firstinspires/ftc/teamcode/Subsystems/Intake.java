package org.firstinspires.ftc.teamcode.Subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class Intake {
    DcMotor intakeMotor;
    Servo leftGate;
    Servo rightGate;


    private double leftGateOpenPosition = 0;
    private double leftGateClosePosition = 0;
    private double rightGateOpenPosition = 0;
    private double rightGateClosePosition = 0;

    private boolean intakeFlag;
    public enum GateStates{
        OPEN,
        CLOSE
    }

    private GateStates currentGateState = GateStates.OPEN;


    public void initiate(HardwareMap hardwareMap) {
        intakeMotor = hardwareMap.dcMotor.get("fr");
        leftGate = hardwareMap.servo.get("lg");
        rightGate = hardwareMap.servo.get("rg");
    }


    public void run(double power){
        intakeMotor.setPower(power);
    }

    public GateStates getGateState(){return currentGateState;}
    public void setGateState(GateStates input){currentGateState = input;}

    public void setGate(GateStates input){
        if(input == GateStates.OPEN){
            leftGate.setPosition(leftGateOpenPosition);
            rightGate.setPosition(rightGateOpenPosition);
        }
        else if(input == GateStates.CLOSE){
        leftGate.setPosition(leftGateClosePosition);
        rightGate.setPosition(rightGateClosePosition);
        }
    }


    public void activeIntakeConditional(double power, boolean current, boolean previous) {
        if (current && !previous) {
            intakeFlag = !intakeFlag;
        }
        if (intakeFlag) {
            intakeMotor.setPower(power);
        }
        else{
            intakeMotor.setPower(0);
        }
    }
}
