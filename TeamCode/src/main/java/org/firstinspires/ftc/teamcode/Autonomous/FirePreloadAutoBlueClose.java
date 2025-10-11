package org.firstinspires.ftc.teamcode.Autonomous;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.PathChain;
import com.pedropathing.util.Timer;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@Autonomous(name = "FirePreloadAutoBlueClose", group = "BLUE")
public class FirePreloadAutoBlueClose extends LinearOpMode {
    private Follower follower;
    private Timer pathTimer, actionTimer, opmodeTimer;
    private int pathState;
    private PathChain shootPath, endPath;

    private final Pose startPose = new Pose(28.416,132.096,Math.toRadians(145));
    private final Pose shootPose = new Pose(32.256,122.496,Math.toRadians(145));
    private final Pose endPose = new Pose(62.208,132.864,Math.toRadians(145));

    public void buildPaths(){
        shootPath = follower.pathBuilder()
                .addPath(new BezierLine(startPose,shootPose))
                .setConstantHeadingInterpolation(startPose.getHeading())
                .build();
        endPath = follower.pathBuilder()
                .addPath(new BezierLine(shootPose,endPose))
                .setLinearHeadingInterpolation(shootPose.getHeading(),endPose.getHeading())
                .build();

    }

    @Override
    public void runOpMode() throws InterruptedException {

    }
}
