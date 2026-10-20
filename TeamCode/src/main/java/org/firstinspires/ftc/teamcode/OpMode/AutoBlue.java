package org.firstinspires.ftc.teamcode.OpMode;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Subsystems.RobotClass;
import org.firstinspires.ftc.teamcode.Subsystems.Tools;

@Autonomous
public class AutoBlue extends LinearOpMode{

    public IMU imu;
    public RobotClass robotClass;
    @Override
    public void runOpMode() throws InterruptedException {
        robotClass = new RobotClass(hardwareMap);
        Tools Tools = new Tools(hardwareMap);

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

            // Forward

            robotClass.frontLeft.setPower(0.5);
            robotClass.frontRight.setPower(0.5);
            robotClass.backLeft.setPower(0.5);
            robotClass.backRight.setPower(0.5);

            sleep(3000);

            // Turn Left

            robotClass.frontLeft.setPower(-0.5);
            robotClass.frontRight.setPower(0.5);
            robotClass.backLeft.setPower(-0.5);
            robotClass.backRight.setPower(0.5);

            sleep(500);

            //limelight for the AprilTags

            Tools.shooter.setPower(0.5);

            sleep(1000);

            // Turn Left

            robotClass.frontLeft.setPower(-0.5);
            robotClass.frontRight.setPower(0.5);
            robotClass.backLeft.setPower(-0.5);
            robotClass.backRight.setPower(0.5);

            sleep(500);

            // Forward

            robotClass.frontLeft.setPower(0.5);
            robotClass.frontRight.setPower(0.5);
            robotClass.backLeft.setPower(0.5);
            robotClass.backRight.setPower(0.5);

            sleep(3000);


        }
    }
}
