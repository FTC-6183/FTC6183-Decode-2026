package org.firstinspires.ftc.teamcode.Subsystems;

import dev.nextftc.core.subsystems.Subsystem;
import dev.nextftc.ftc.Gamepads;
import dev.nextftc.hardware.driving.DriverControlledCommand;
import dev.nextftc.hardware.driving.MecanumDriverControlled;
import dev.nextftc.hardware.impl.MotorEx;

public class Drivetrain implements Subsystem {

    public static final Drivetrain INSTANCE = new Drivetrain();
    private Drivetrain(){}

    private MotorEx frontLeftMotor;
    private MotorEx backLeftMotor;
    private MotorEx frontRightMotor;
    private MotorEx backRightMotor;

    @Override
    public void initialize(){
        frontLeftMotor = new MotorEx("fl").brakeMode().reversed();
        backLeftMotor = new MotorEx("bl").brakeMode().reversed();
        frontRightMotor = new MotorEx("fr").brakeMode();
        backRightMotor = new MotorEx("br").brakeMode();
    }

    public DriverControlledCommand startDrive = new MecanumDriverControlled(
            frontLeftMotor,
            backLeftMotor,
            frontRightMotor,
            backRightMotor,
            Gamepads.gamepad1().leftStickY().negate(),
            Gamepads.gamepad1().leftStickX(),
            Gamepads.gamepad1().rightStickX()
    );
}
