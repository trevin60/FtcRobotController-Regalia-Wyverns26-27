package org.firstinspires.ftc.teamcode.OpMode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Subsystems.RobotClass;
import org.firstinspires.ftc.teamcode.Subsystems.Tools;

@Autonomous(name = "AutoRed", group = "Autonomous")
public class AutoRed extends LinearOpMode {

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

            // Drive forward 24 inches using encoders
            robotClass.encoderDrive(this, 0.5, 24, 24, 5.0);

            // Turn Right using encoders (+8 inches left side, -8 inches right side)
            robotClass.encoderDrive(this, 0.5, 8, -8, 3.0);

            // Shooter for AprilTags
            tools.shooter.setPower(0.5);
            sleep(1000);
            tools.shooter.setPower(0);

            // Turn Right again
            robotClass.encoderDrive(this, 0.5, 8, -8, 3.0);

            // Drive forward 24 inches
            robotClass.encoderDrive(this, 0.5, 24, 24, 5.0);

            // Stop all motors
            robotClass.stopMotors();
            tools.shooter.setPower(0);
            tools.intake.setPower(0);
        }
    }
}
