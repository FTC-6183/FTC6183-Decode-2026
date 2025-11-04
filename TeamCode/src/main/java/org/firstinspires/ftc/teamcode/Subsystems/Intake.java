package org.firstinspires.ftc.teamcode.Subsystems;

import dev.nextftc.bindings.Button;
import dev.nextftc.core.commands.Command;
import dev.nextftc.core.subsystems.Subsystem;
import dev.nextftc.hardware.impl.MotorEx;
import dev.nextftc.hardware.impl.ServoEx;
import dev.nextftc.hardware.positionable.SetPosition;
import dev.nextftc.hardware.powerable.SetPower;

import static dev.nextftc.bindings.Bindings.*;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import java.util.function.BooleanSupplier;


public class Intake implements Subsystem {
    public static final Intake INSTANCE = new Intake();
    private Intake(){}

    public static double gateOpenPosition = 0.5;
    public static double gateClosePosition = 0.35;

    private MotorEx intakeMotor = new MotorEx("intake");;
    private ServoEx gate = new ServoEx("gate");


    public Command gateOpen = new SetPosition(gate,gateOpenPosition).requires(gate);
    public Command gateClose = new SetPosition(gate,gateClosePosition).requires(gate);

    public Command intakeBall = new SetPower(intakeMotor,1).requires(intakeMotor);
    public Command transferBall = new SetPower(intakeMotor,-1).requires(intakeMotor);
    public String getGateState(){
        double gatePosition = gate.getPosition();
        if(gatePosition == gateOpenPosition){
            return "Open";
        }
        else if(gatePosition == gateClosePosition){
            return "Close";
        }
        return "Unknown";
    }
    public void status(Telemetry telemetry) {
        telemetry.addData("Gate State", getGateState());
    }
    public Button toggleGate(boolean input){
        Button toggleGate = button(() -> input);
        toggleGate.toggleOnBecomesTrue()
                .whenBecomesTrue(() -> gateOpen.schedule())
                .whenBecomesFalse(() -> gateClose.schedule());
        return toggleGate;
    }
}
