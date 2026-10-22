package org.firstinspires.ftc.teamcode.OpMode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.firstinspires.ftc.teamcode.Subsystems.RobotClass;
import org.firstinspires.ftc.teamcode.Subsystems.Tools;

@Autonomous(name = "AutoBlue", group = "Autonomous")
public class AutoBlue extends LinearOpMode {

    public RobotClass robotClass;
    public Tools tools;

    @Override
    public void runOpMode() throws InterruptedException {
        robotClass = new RobotClass(hardwareMap);
        tools = new Tools(hardwareMap);

        waitForStart();

        if (opModeIsActive()) {
            // Start continuous intake
            tools.intake.setPower(0.8);

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

            // Shooter for AprilTags
            tools.shooter.setPower(0.5);
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

            // Stop all motors
            robotClass.frontLeft.setPower(0);
            robotClass.frontRight.setPower(0);
            robotClass.backLeft.setPower(0);
            robotClass.backRight.setPower(0);
            tools.shooter.setPower(0);
            tools.intake.setPower(0);
        }
    }
}
