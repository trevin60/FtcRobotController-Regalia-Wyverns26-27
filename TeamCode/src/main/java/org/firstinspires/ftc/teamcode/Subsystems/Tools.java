package org.firstinspires.ftc.teamcode.Subsystems;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Tools {
    public DcMotorEx shooter, intake, flywheel;

    // STARTING-POINT VALUES ONLY. Calibrate these on the actual robot before competition.
    // Distance is measured from the Limelight camera to an AprilTag, in inches.
    private static final double[] SHOOTER_DISTANCE_INCHES = {12.0, 24.0, 36.0, 48.0, 60.0};
    private static final double[] SHOOTER_POWER = {0.35, 0.50, 0.65, 0.80, 1.00};

    public Tools(HardwareMap hardwareMap) {
        shooter = hardwareMap.get(DcMotorEx.class, "shooter");
        intake = hardwareMap.get(DcMotorEx.class, "intake");
        flywheel = hardwareMap.get(DcMotorEx.class, "flywheel");
    }

     //Converts a valid camera-to-AprilTag distance into shooter motor power using linear
     //interpolation between the tuning points above. Values beyond the table are clamped.
     //Returns NaN for an invalid distance so callers can avoid firing on stale/missing data.
    public double getShooterPowerForDistance(double distanceInches) {//RPM !!!!!!!!!!!!!!!!!
        if (!Double.isFinite(distanceInches) || distanceInches <= 0.0) {
            return Double.NaN;
        }

        if (distanceInches <= SHOOTER_DISTANCE_INCHES[0]) {
            return SHOOTER_POWER[0];
        }

        int lastIndex = SHOOTER_DISTANCE_INCHES.length - 1;
        if (distanceInches >= SHOOTER_DISTANCE_INCHES[lastIndex]) {
            return SHOOTER_POWER[lastIndex];
        }

        for (int i = 0; i < lastIndex; i++) {
            double lowerDistance = SHOOTER_DISTANCE_INCHES[i];
            double upperDistance = SHOOTER_DISTANCE_INCHES[i + 1];
            if (distanceInches <= upperDistance) {
                double fraction = (distanceInches - lowerDistance) / (upperDistance - lowerDistance);
                double power = SHOOTER_POWER[i]
                        + fraction * (SHOOTER_POWER[i + 1] - SHOOTER_POWER[i]);
                return Math.max(0.0, Math.min(1.0, power));
            }
        }

        return Double.NaN;
    }
}
