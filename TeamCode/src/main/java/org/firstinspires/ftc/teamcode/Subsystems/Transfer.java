package org.firstinspires.ftc.teamcode.Subsystems;

import org.firstinspires.ftc.teamcode.OLDSubsystems.TransferOLD;

import dev.nextftc.core.commands.Command;
import dev.nextftc.core.subsystems.Subsystem;
import dev.nextftc.hardware.impl.MotorEx;
import dev.nextftc.hardware.impl.ServoEx;
import dev.nextftc.hardware.powerable.SetPower;

public class Transfer implements Subsystem {
    public static final Transfer INSTANCE = new Transfer();
    private Transfer(){}
    private MotorEx transfer = new MotorEx("trans");

    public Command transferIntake = new SetPower(transfer,1).requires(this);
    public Command transferShoot = new SetPower(transfer,-1).requires(this);

}
