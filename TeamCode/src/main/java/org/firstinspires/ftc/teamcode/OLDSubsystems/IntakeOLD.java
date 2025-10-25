package org.firstinspires.ftc.teamcode.OLDSubsystems;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

@Config
public class IntakeOLD {
    DcMotor intakeMotor;
    Servo gate;


    public static double gateOpenPosition = 0.5;
    public static double gateClosePosition = 0.35;
    public static double gatePosition = 0;

    private boolean intakeFlag;
    private boolean gateFlag;
    public enum GateStates{
        OPEN,
        CLOSE
    }

    private GateStates currentGateState = GateStates.OPEN;


    public void initiate(HardwareMap hardwareMap) {
        intakeMotor = hardwareMap.dcMotor.get("intake");
        gate = hardwareMap.servo.get("gate");
    }


    public void run(double power){
        intakeMotor.setPower(power);
    }

    public GateStates getGateState(){return currentGateState;}

    public void setGate(GateStates input){
        if(input == GateStates.OPEN){
            gate.setPosition(gateOpenPosition);
        }
        else if(input == GateStates.CLOSE){
        gate.setPosition(gateClosePosition);
        }
    }


    public void setGate(double input){
        gate.setPosition(input);
    }
    public double getPosition(){
        return gate.getPosition();
    }
    public void gateToggle( boolean current, boolean previous) {
        if (current && !previous) {
            gateFlag = !gateFlag;
        }
        if(gateFlag){
            if(currentGateState == GateStates.OPEN){
                gate.setPosition(gateClosePosition);
                currentGateState = GateStates.CLOSE;
            }

        }
        if(!gateFlag){
            gate.setPosition(gateOpenPosition);
            currentGateState = GateStates.OPEN;
        }

    }
    public void status(Telemetry telemetry) {
        telemetry.addData("Gate State", getGateState());
    }


    public void activeIntakeConditional(double power, boolean current, boolean previous) {
        if (current && !previous) {
            intakeFlag = !intakeFlag;
        }
        if (intakeFlag) {
            intakeMotor.setPower(1);
        }
        else{
            intakeMotor.setPower(-1);
        }
    }
    public void activeReverseIntakeConditional(double power, boolean current, boolean previous) {
        if (current && !previous) {
            intakeFlag = !intakeFlag;
        }
        if (intakeFlag) {
            intakeMotor.setPower(1);
        }
        else{
            intakeMotor.setPower(0);
        }
    }
}
