package org.firstinspires.ftc.teamcode.Subsystems;

import org.firstinspires.ftc.teamcode.OLDSubsystems.TransferOLD;

import dev.nextftc.core.commands.Command;
import dev.nextftc.core.commands.utility.InstantCommand;
import dev.nextftc.core.subsystems.Subsystem;
import dev.nextftc.ftc.ActiveOpMode;
import dev.nextftc.hardware.impl.MotorEx;
import dev.nextftc.hardware.impl.ServoEx;
import dev.nextftc.hardware.powerable.SetPower;

public class Transfer implements Subsystem {
    public static final Transfer INSTANCE = new Transfer();
    private Transfer(){}
    private MotorEx transfer = new MotorEx("trans");

    private double power = 0;

    public Command transferIntake(){
        return new SetPower(transfer,1);
        //return new InstantCommand(()-> power=1);
    }

    public Command off(){
        return new SetPower(transfer, 0);
    }

    public Command transferShoot()
    {
        return new SetPower(transfer,-1);
        //return new InstantCommand(()-> power=-1);
    }

}
