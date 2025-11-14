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
import dev.nextftc.core.commands.conditionals.IfElseCommand;
import dev.nextftc.core.commands.delays.WaitUntil;
import dev.nextftc.core.commands.groups.ParallelGroup;
import dev.nextftc.core.commands.groups.SequentialGroup;
import dev.nextftc.core.commands.utility.InstantCommand;
import dev.nextftc.core.components.BindingsComponent;
import dev.nextftc.core.components.SubsystemComponent;
import dev.nextftc.ftc.Gamepads;
import dev.nextftc.ftc.NextFTCOpMode;
import dev.nextftc.ftc.components.BulkReadComponent;

@TeleOp(name = "RobotIn5WeeksTeleOp")
public class RobotIn5WeeksTeleOp extends NextFTCOpMode {
    private static double velocity = 1500;
    private static double escapeVelocity = 300;
    private boolean onOffFlag = true;
    private String intakeStatus = "";
    private String onStatus = "";

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
        //Button onOffButton = button(()->gamepad1.right_bumper);
        Command waitUntilOn = new WaitUntil(()->onOffFlag);
        Command completeShootSequence = new SequentialGroup(
                new InstantCommand(() -> intakeStatus = "SHOOTER MODE"),
                waitUntilOn,
                new ParallelGroup(
                        Intake.INSTANCE.gateClose()//,
                        //Shooter.INSTANCE.setVelocity(velocity)
                ),
                //new WaitUntil(() ->
                //Math.abs(Math.abs(Shooter.INSTANCE.getVelocity()) - Math.abs(velocity)) < threshold),
                new ParallelGroup(
                        Intake.INSTANCE.transferBall(),
                        Transfer.INSTANCE.transferShoot()
                )
        );
        Command intakeSequence = new SequentialGroup(
                new InstantCommand(() -> intakeStatus = "INTAKE MODE"),
                waitUntilOn,
                new ParallelGroup(
                        Shooter.INSTANCE.setVelocity(0),
                        Intake.INSTANCE.gateOpen(),
                        Intake.INSTANCE.intakeBall(),
                        Transfer.INSTANCE.transferIntake()
                )
        );
        /*
        Command statusOnSwitch = new IfElseCommand(
                ()->intakeStatus.equals("INTAKE STATUS"),
                intakeSequence,
                completeShootSequence
        );*/

        Command onSequence = new SequentialGroup(
                new InstantCommand(()->onOffFlag=true),
                new InstantCommand(()->onStatus = "ON")
        );
        Command offSequence = new ParallelGroup(
                new InstantCommand(()->onOffFlag=false),
                new InstantCommand(()->onStatus = "OFF"),
                Shooter.INSTANCE.setVelocity(0),
                Intake.INSTANCE.off(),
                Transfer.INSTANCE.off()
        );
        Gamepads.gamepad1().rightBumper()
                        .toggleOnBecomesTrue()
                        .whenBecomesTrue(onSequence)
                        .whenBecomesFalse(offSequence);
        /*
        onOffButton
                .toggleOnBecomesTrue()
                .whenBecomesTrue(new InstantCommand(()->onOffFlag=true).and(new InstantCommand(()->onStatus.set("ON"))))
                .whenBecomesFalse(new InstantCommand(()->onOffFlag=false).and(new InstantCommand(()->onStatus.set("OFF"))));

         */

        /*
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
         */
        //Intake Command


        /*
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
        */
        //Button shootIntakeButton = button(()->gamepad1.left_bumper);

        //The Left Bumper Switches from Shooting to Intaking
        Gamepads.gamepad1().leftBumper()
                .toggleOnBecomesTrue()
                .whenBecomesTrue(intakeSequence)
                .whenBecomesFalse(completeShootSequence);

        //Button toggleGate = button(()->gamepad1.y);
        //The Y button toggles the gate
        Gamepads.gamepad1().y()
                .toggleOnBecomesTrue()
                .whenBecomesTrue(Intake.INSTANCE.gateOpen())
                .whenBecomesFalse(Intake.INSTANCE.gateClose());

        //Right Trigger releases the ball
        Gamepads.gamepad1().rightTrigger().greaterThan(0.3)
                .whenBecomesTrue(Shooter.INSTANCE.setVelocity(escapeVelocity));

        //Button releaseBall = button(()->(gamepad1.right_trigger > 0.3));
        //releaseBall

    }

    @Override public void onUpdate(){
        BindingManager.update();
        Drivetrain.INSTANCE.run(-gamepad1.left_stick_y, gamepad1.left_stick_x, gamepad1.right_stick_x);
        Shooter.INSTANCE.periodic();
        Transfer.INSTANCE.periodic();
        Intake.INSTANCE.periodic();
        telemetry.addData("INTAKE/SHOOTER", intakeStatus);
        telemetry.addData("ON/OFF",onStatus);
        telemetry.addData("Flywheel Velocity", Shooter.INSTANCE.getVelocity());
        telemetry.addData("Target Velocity",velocity);
        telemetry.update();
    }
    @Override public void onStop(){}


}
