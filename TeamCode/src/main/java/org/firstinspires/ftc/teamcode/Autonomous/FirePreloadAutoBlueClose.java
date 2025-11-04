package org.firstinspires.ftc.teamcode.Autonomous;

import static org.firstinspires.ftc.teamcode.Subsystems.Shooter.shootVelocity;
import static org.firstinspires.ftc.teamcode.Subsystems.Shooter.threshold;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.Subsystems.Drivetrain;
import org.firstinspires.ftc.teamcode.Subsystems.Intake;
import org.firstinspires.ftc.teamcode.Subsystems.Shooter;
import org.firstinspires.ftc.teamcode.Subsystems.Transfer;

import dev.nextftc.core.commands.Command;
import dev.nextftc.core.commands.delays.WaitUntil;
import dev.nextftc.core.commands.groups.ParallelGroup;
import dev.nextftc.core.commands.groups.SequentialGroup;
import dev.nextftc.core.commands.utility.InstantCommand;
import dev.nextftc.core.commands.utility.PerpetualCommand;
import dev.nextftc.core.components.SubsystemComponent;
import dev.nextftc.ftc.NextFTCOpMode;
import dev.nextftc.ftc.components.BulkReadComponent;
/*
@Autonomous(name = "FirePreloadAutoCloseBlue",group ="Blue")
public class FirePreloadAutoBlueClose extends NextFTCOpMode {
    public FirePreloadAutoBlueClose() {
        addComponents(
                new SubsystemComponent(Shooter.INSTANCE, Drivetrain.INSTANCE, Intake.INSTANCE, Transfer.INSTANCE),
                BulkReadComponent.INSTANCE
        );
    }
    private ParallelGroup shootSequenceOne = new ParallelGroup(
                Intake.INSTANCE.gateClose,
                Shooter.INSTANCE.setVelocity(shootVelocity)
        );
    private ParallelGroup shootSequenceTwo = new ParallelGroup(
                Intake.INSTANCE.transferBall,
                Transfer.INSTANCE.transferShoot
        );
    private Command shooterToSpeed = new WaitUntil(()->(Math.abs(Shooter.INSTANCE.getVelocity())-shootVelocity)<threshold);
    private SequentialGroup shootSequence = new SequentialGroup(
                shootSequenceOne,
                shooterToSpeed,
                shootSequenceTwo
        );
    private int ballShot = 0;

    private Command ballsShotDetector = new InstantCommand(()->{if((shootVelocity-Shooter.INSTANCE.getVelocity())>threshold)});

    private Command shooterToSpeed = new WaitUntil(()->(Math.abs(Shooter.INSTANCE.getVelocity())-shootVelocity)<threshold);



}
*/
