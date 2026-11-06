package org.firstinspires.ftc.teamcode.OpMode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Subsystems.Limelight;
import org.firstinspires.ftc.teamcode.Subsystems.RobotClass;
import org.firstinspires.ftc.teamcode.Subsystems.Tools;

@Autonomous(name = "AutoBlue", group = "Autonomous")
public class AutoBlue extends LinearOpMode {

    public RobotClass robotClass;
    public Tools tools;
    public Limelight limelight;

    @Override
    public void runOpMode() throws InterruptedException {

        robotClass = new RobotClass(hardwareMap);
        tools = new Tools(hardwareMap);
        limelight = new Limelight(hardwareMap);

        telemetry.addLine("AutoBlue ready. Verify Limelight pipeline 0 is the BIO BUZZ AprilTag pipeline.");
        telemetry.update();
        waitForStart();

        try {
            if (opModeIsActive()) {
                tools.intake.setPower(0.8);

                // Drive forward 24 inches.
                robotClass.encoderDrive(this, 0.5, 24, 24, 24, 24);

                // Turn left: left wheels backward, right wheels forward.
                robotClass.encoderDrive(this, 0.5, -8, 8, -8, 8);

                // Use a fresh valid AprilTag range to choose shooter power.
                double distanceInches = limelight.getDistanceInches();
                double shooterPower = tools.getShooterPowerForDistance(distanceInches);
                if (Double.isFinite(distanceInches) && Double.isFinite(shooterPower)) {
                    telemetry.addData("AprilTag distance", "%.1f in", distanceInches);
                    telemetry.addData("Shooter power", "%.2f", shooterPower);
                    telemetry.update();
                    tools.shooter.setPower(shooterPower);
                    sleep(1000);
                    tools.shooter.setPower(0.0);
                } else {
                    telemetry.addLine("No valid AprilTag distance; skipping the shot.");
                    telemetry.update();
                }

                // Turn left again, then drive forward 24 inches.
                robotClass.encoderDrive(this, 0.5, -8, 8, -8, 8);
                robotClass.encoderDrive(this, 0.5, 24, 24, 24, 24);
            }
        } finally {
            robotClass.stopMotors();
            tools.shooter.setPower(0.0);
            tools.intake.setPower(0.0);
            limelight.stop();
        }
    }
}
