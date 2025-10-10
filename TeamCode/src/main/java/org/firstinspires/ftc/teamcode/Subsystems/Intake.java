package org.firstinspires.ftc.teamcode.Subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Intake {
    DcMotor intakeMotor;
    private boolean intakeFlag;

    public void initiate(HardwareMap hardwareMap) {
        intakeMotor = hardwareMap.dcMotor.get("fr");
    }

    public void run(double power){
        intakeMotor.setPower(power);
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
