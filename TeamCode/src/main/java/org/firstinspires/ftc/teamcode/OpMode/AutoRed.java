package org.firstinspires.ftc.teamcode.OpMode;
import static java.lang.Thread.sleep;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Subsystems.RobotClass;

@Autonomous
public class AutoRed extends LinearOpMode{
    public DcMotorEx frontLeft, frontRight, backRight, backLeft;

    public IMU imu;
    public RobotClass robotClass;
    @Override
    public void runOpMode() throws InterruptedException {

        waitForStart();

        while (opModeIsActive()) {

            // Turning Left

            //frontLeft.setPower(-0.5);
            //frontRight.setPower(0.5);
            //backLeft.setPower(-0.5);
            //backRight.setPower(0.5);

            // Turning Right

            //frontLeft.setPower(0.5);
            //frontRight.setPower(-0.5);
            //backLeft.setPower(0.5);
            //backRight.setPower(-0.5);

            // Not moving

            //frontLeft.setPower(0);
            //frontRight.setPower(0);
            //backLeft.setPower(0);
            //backRight.setPower(0);

            // Forward

            //frontLeft.setPower(0.5);
            //frontRight.setPower(0.5);
            //backLeft.setPower(0.5);
            //backRight.setPower(0.5);

            // Backward

            //frontLeft.setPower(-0.5);
            //frontRight.setPower(-0.5);
            //backLeft.setPower(-0.5);
            //backRight.setPower(-0.5);

            // Actual code

            frontLeft.setPower(0.5);
            frontRight.setPower(0.5);
            backLeft.setPower(0.5);
            backRight.setPower(0.5);

            sleep(3000);

            //Turn

            frontLeft.setPower(0.5);
            frontRight.setPower(-0.5);
            backLeft.setPower(0.5);
            backRight.setPower(-0.5);

            sleep(500);

            //limelight for the AprilTags

            //START CODING THE SHOOTER

            }
        }
    }