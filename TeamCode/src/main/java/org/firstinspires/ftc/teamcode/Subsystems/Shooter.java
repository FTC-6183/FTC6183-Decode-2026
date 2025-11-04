package org.firstinspires.ftc.teamcode.Subsystems;

import dev.nextftc.control.ControlSystem;
import com.qualcomm.robotcore.hardware.DcMotorEx;

import org.firstinspires.ftc.teamcode.OLDSubsystems.ShooterOLD;

import dev.nextftc.control.KineticState;
import dev.nextftc.control.feedback.FeedbackType;
import dev.nextftc.control.feedback.PIDCoefficients;
import dev.nextftc.control.feedback.PIDElement;
import dev.nextftc.core.commands.Command;
import dev.nextftc.core.subsystems.Subsystem;
import dev.nextftc.hardware.controllable.RunToState;
import dev.nextftc.hardware.controllable.RunToVelocity;
import dev.nextftc.hardware.impl.MotorEx;
import dev.nextftc.hardware.powerable.SetPower;

public class Shooter implements Subsystem {
    public static final Shooter INSTANCE = new Shooter();
    private Shooter(){}
    private MotorEx shooterMotor = new MotorEx("shooter");
    public static double kP = 0.05;
    public static double kI = 0;
    public static double kD = 0;
    public static double kF = 0;
    public static double kV = 0;
    public static double kA = 0;
    public static double kS = 0;
    public static double shootVelocity = 0;
    public static double ballDrop = 0;
    public static double threshold = 10;
    private ControlSystem velocityControl = ControlSystem.builder()
            .feedback(new PIDElement(FeedbackType.VELOCITY,new PIDCoefficients(kP, kI, kD)))
            .basicFF(kV,kA,kS)
            .build();
    //TODO
    public double distanceToVelocity(double distance){return 0;}
    public Command setVelocity(double velocity){
        return new RunToState(
                velocityControl,
                new KineticState(
                        0,
                        velocity,
                        0
                ),
                new KineticState(
                        Double.POSITIVE_INFINITY,
                        velocity,
                        Double.POSITIVE_INFINITY
                )

        ).requires(this);
    }
    public double getVelocity(){return shooterMotor.getVelocity();}
}
