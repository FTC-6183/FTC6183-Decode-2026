package org.firstinspires.ftc.teamcode.Subsystems;

import dev.nextftc.bindings.Button;
import dev.nextftc.core.commands.Command;
import dev.nextftc.core.subsystems.Subsystem;
import dev.nextftc.hardware.impl.MotorEx;
import dev.nextftc.hardware.impl.ServoEx;
import dev.nextftc.hardware.positionable.SetPosition;

import static dev.nextftc.bindings.Bindings.*;

import java.util.function.BooleanSupplier;


public class Intake implements Subsystem {
    public static final Intake INSTANCE = new Intake();
    private Intake(){}

    public static double gateOpenPosition = 0.5;
    public static double gateClosePosition = 0.35;

    private MotorEx intakeMotor = new MotorEx("intake");;
    private ServoEx gate = new ServoEx("gate");


    public Command gateOpen = new SetPosition(gate,gateOpenPosition);
    public Command gateClose = new SetPosition(gate,gateClosePosition);

    public Button toggleGate(boolean input){
        Button toggleGate = button(() -> input);
        toggleGate.toggleOnBecomesTrue()
                .whenBecomesTrue(() -> gateOpen.schedule())
                .whenBecomesFalse(() -> gateClose.schedule());
        return toggleGate;
    }





    @Override
    public void periodic(){

    }
}
