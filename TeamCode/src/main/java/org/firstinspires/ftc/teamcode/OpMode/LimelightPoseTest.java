package org.firstinspires.ftc.teamcode.OpMode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Pose3D;
import org.firstinspires.ftc.robotcore.external.navigation.Position;
import org.firstinspires.ftc.teamcode.Subsystems.Limelight;

import com.qualcomm.hardware.limelightvision.LLResult;

/**
 * Stationary diagnostic OpMode.
 * Displays whether the Limelight is returning a field-relative robot pose.
 * It does not move the drivetrain or activate any mechanisms.
 */
@TeleOp(name = "LimelightPoseTest", group = "Test")
public class LimelightPoseTest extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {
        Limelight limelight = new Limelight(hardwareMap);

        try {
            telemetry.addLine("Limelight pose test - robot will not move.");
            telemetry.addLine("Place the robot on the field, then check Field X/Y.");
            telemetry.update();

            while (!isStarted() && !isStopRequested()) {
                showLimelightData(limelight);
                telemetry.update();
                sleep(100);
            }

            waitForStart();

            while (opModeIsActive()) {
                showLimelightData(limelight);
                telemetry.update();
                sleep(100);
            }
        } finally {
            limelight.stop();
        }
    }

    private void showLimelightData(Limelight limelight) {
        LLResult result = limelight.getLatestResult();

        if (result == null) {
            telemetry.addData("Limelight result", "No data yet");
            telemetry.addData("AprilTags", 0);
            telemetry.addData("Field pose", "Waiting for data");
            return;
        }

        telemetry.addData("Limelight result", result.isValid() ? "Valid" : "Invalid");

        int tagCount = 0;
        if (result.isValid() && result.getFiducialResults() != null) {
            tagCount = result.getFiducialResults().size();
        }
        telemetry.addData("AprilTags detected", tagCount);

        Pose3D pose = limelight.getBotPose();
        if (pose == null) {
            telemetry.addData("Field pose", "UNAVAILABLE");
            telemetry.addLine("Tags may be detected without field localization.");
            return;
        }

        Position position = pose.getPosition().toUnit(DistanceUnit.INCH);
        telemetry.addData("Field X", "%.1f in", position.x);
        telemetry.addData("Field Y", "%.1f in", position.y);
        telemetry.addData("Field Z", "%.1f in", position.z);
        telemetry.addLine("Pose is being reported. Keep robot still for stable values.");
    }
}