package org.firstinspires.ftc.teamcode.Subsystems;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.navigation.Pose3D;

public class Limelight {
    private final Limelight3A limelight;

    public Limelight(HardwareMap hardwareMap) {
        limelight = hardwareMap.get(Limelight3A.class, "limelight");
        limelight.pipelineSwitch(0);
        limelight.start();
    }

     //Gets the latest result from the Limelight camera.
    public LLResult getLatestResult() {
        return limelight.getLatestResult();
    }


     //Checks if the Limelight currently sees a AprilTag.

    public boolean hasTarget() {
        LLResult result = getLatestResult();
        return result != null && result.isValid();
    }

     //Returns the 3D position (x, y, z, orientation) of the robot on the field.
     //Returns null if no valid target or AprilTag is in view.

    public Pose3D getBotPose() {
        LLResult result = getLatestResult();
        if (result != null && result.isValid()) {
            return result.getBotpose();
        }
        return null;
    }

     //Returns the horizontal angle offset (tx) to the target in degrees.

    public double getTx() {
        LLResult result = getLatestResult();
        if (result != null && result.isValid()) {
            return result.getTx();
        }
        return 0.0;
    }

     //Returns the vertical angle offset (ty) to the target in degrees.

    public double getTy() {
        LLResult result = getLatestResult();
        if (result != null && result.isValid()) {
            return result.getTy();
        }
        return 0.0;
    }

     //Stops the Limelight pipeline when the OpMode ends.

    public void stop() {
        if (limelight != null) {
            limelight.stop();
        }
    }
}
