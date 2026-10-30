package org.firstinspires.ftc.teamcode.Subsystems;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.LLResultTypes;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Pose3D;
import org.firstinspires.ftc.robotcore.external.navigation.Position;

import java.util.List;

public class Limelight {
    private final Limelight3A limelight;

    public Limelight(HardwareMap hardwareMap) {
        limelight = hardwareMap.get(Limelight3A.class, "limelight");
        // Pipeline 0 must be configured as the BIO BUZZ AprilTag pipeline on the Limelight.
        limelight.pipelineSwitch(0);
        limelight.start();
    }

    //Gets the latest result from the Limelight camera
    public LLResult getLatestResult() {
        return limelight.getLatestResult();
    }

    //Returns true when the Limelight has a valid current result
    public boolean hasTarget() {
        LLResult result = getLatestResult();
        return result != null && result.isValid() && !result.getFiducialResults().isEmpty();
    }

     //Returns the distance in inches from the camera to the closest detected AprilTag.
     //Returns NaN when no valid AprilTag pose is available. This is a camera-to-tag range,
     //so shooter power still needs to be calibrated on the physical robot.

    public double getDistanceInches() {
        LLResult result = getLatestResult();
        if (result == null || !result.isValid()) {
            return Double.NaN;
        }

        List<LLResultTypes.FiducialResult> fiducials = result.getFiducialResults();
        if (fiducials == null || fiducials.isEmpty()) {
            return Double.NaN;
        }

        double closestDistance = Double.POSITIVE_INFINITY;
        for (LLResultTypes.FiducialResult fiducial : fiducials) {
            if (fiducial == null) {
                continue;
            }

            Pose3D targetPose = fiducial.getTargetPoseCameraSpace();
            if (targetPose == null) {
                continue;
            }

            Position position = targetPose.getPosition().toUnit(DistanceUnit.INCH);
            double x = position.x;
            double y = position.y;
            double z = position.z;
            double distance = Math.sqrt(x * x + y * y + z * z);

            if (Double.isFinite(distance) && distance > 0.0 && distance < closestDistance) {
                closestDistance = distance;
            }
        }

        return Double.isFinite(closestDistance) ? closestDistance : Double.NaN;
    }

    //Returns the 3D position of the robot on the field, or null if unavailable
    public Pose3D getBotPose() {
        LLResult result = getLatestResult();
        if (result != null && result.isValid()) {
            return result.getBotpose();
        }
        return null;
    }

    //Returns the horizontal angle offset (tx) to the target in degrees
    public double getTx() {
        LLResult result = getLatestResult();
        if (result != null && result.isValid()) {
            return result.getTx();
        }
        return 0.0;
    }

    //Returns the vertical angle offset (ty) to the target in degrees
    public double getTy() {
        LLResult result = getLatestResult();
        if (result != null && result.isValid()) {
            return result.getTy();
        }
        return 0.0;
    }

    //Stops the Limelight pipeline when the OpMode ends
    public void stop() {
        limelight.stop();
    }
}
