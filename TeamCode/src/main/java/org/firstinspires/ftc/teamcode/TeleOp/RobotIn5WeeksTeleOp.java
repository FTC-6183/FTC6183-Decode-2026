package org.firstinspires.ftc.teamcode.TeleOp;


import static org.firstinspires.ftc.teamcode.Subsystems.Shooter.threshold;
import static java.lang.Math.abs;
import static dev.nextftc.bindings.Bindings.button;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.Subsystems.Drivetrain;
import org.firstinspires.ftc.teamcode.Subsystems.Intake;
import org.firstinspires.ftc.teamcode.Subsystems.Shooter;
import org.firstinspires.ftc.teamcode.Subsystems.Transfer;

import java.util.concurrent.atomic.AtomicReference;

import dev.nextftc.bindings.BindingManager;
import dev.nextftc.bindings.Button;
import dev.nextftc.core.commands.Command;
import dev.nextftc.core.commands.delays.WaitUntil;
import dev.nextftc.core.commands.groups.ParallelGroup;
import dev.nextftc.core.commands.groups.SequentialGroup;
import dev.nextftc.core.commands.utility.InstantCommand;
import dev.nextftc.core.components.BindingsComponent;
import dev.nextftc.core.components.SubsystemComponent;
import dev.nextftc.ftc.NextFTCOpMode;
import dev.nextftc.ftc.components.BulkReadComponent;

@TeleOp(name = "RobotIn5WeeksTeleOp")
public class RobotIn5WeeksTeleOp extends NextFTCOpMode {
    private static double velocity = -1500;
    private static double escapeVelocity = 300;
    private boolean onOffFlag = true;
    private AtomicReference<String> intakeStatus = new AtomicReference<>("");
    private AtomicReference<String> onStatus = new AtomicReference<>("");

    public RobotIn5WeeksTeleOp() {
        addComponents(
                new SubsystemComponent(Shooter.INSTANCE, Drivetrain.INSTANCE, Intake.INSTANCE, Transfer.INSTANCE),
                BulkReadComponent.INSTANCE,
                BindingsComponent.INSTANCE
        );
    }
    //@Override public void onInit(){ }
    //@Override public void onWaitForStart(){}
    @Override public void onStartButtonPressed(){
        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());
        Button onOffButton = button(()->gamepad1.right_bumper);

        onOffButton
                .toggleOnBecomesTrue()
                .whenBecomesTrue(new InstantCommand(()->onOffFlag=true).and(new InstantCommand(()->onStatus.set("ON"))))
                .whenBecomesFalse(new InstantCommand(()->onOffFlag=false).and(new InstantCommand(()->onStatus.set("OFF"))));

        Command waitUntilOn = new WaitUntil(()->onOffFlag);

        //Intake Command
        ParallelGroup intakeCommand = new ParallelGroup(
                Shooter.INSTANCE.setVelocity(0),
                Intake.INSTANCE.gateOpen,
                Intake.INSTANCE.intakeBall,
                Transfer.INSTANCE.transferIntake
        );

        SequentialGroup intakeSequence = new SequentialGroup(
                new InstantCommand(()-> intakeStatus.set("INTAKE MODE")),
                waitUntilOn,
                intakeCommand
        );

        //Shoot Commands
        ParallelGroup shootSequenceOne = new ParallelGroup(
                Intake.INSTANCE.gateClose,
                Shooter.INSTANCE.setVelocity(velocity)
        );

        ParallelGroup shootSequenceTwo = new ParallelGroup(
                Intake.INSTANCE.transferBall,
                Transfer.INSTANCE.transferShoot
                );

        Command shooterToSpeed = new WaitUntil(()->(Math.abs(Math.abs(Shooter.INSTANCE.getVelocity()) - Math.abs(velocity)))<threshold);

        SequentialGroup completeShootSequence = new SequentialGroup(
                new InstantCommand(()->intakeStatus.set("SHOOTER MODE")),
                waitUntilOn,
                shootSequenceOne,
                shooterToSpeed,
                shootSequenceTwo
        );

        Button shootIntakeButton = button(()->gamepad1.left_bumper);

        shootIntakeButton
                .toggleOnBecomesTrue()
                .whenBecomesTrue(intakeSequence)
                .whenBecomesFalse(completeShootSequence);

        Button toggleGate = button(()->gamepad1.y);
        toggleGate.toggleOnBecomesTrue()
                .whenBecomesTrue(Intake.INSTANCE.gateOpen)
                .whenBecomesFalse(Intake.INSTANCE.gateClose);

        Button releaseBall = button(()->(gamepad1.right_trigger > 0.3));
        releaseBall
                .whenBecomesTrue(Shooter.INSTANCE.setVelocity(escapeVelocity));
    }

    @Override public void onUpdate(){
        BindingManager.update();
        Drivetrain.INSTANCE.run(-gamepad1.left_stick_y, gamepad1.left_stick_x, gamepad1.right_stick_x);
        telemetry.addData("Flywheel Velocity", Shooter.INSTANCE.getVelocity());
        telemetry.addData("Target Velocity",velocity);
        telemetry.update();
    }
    @Override public void onStop(){}


}
