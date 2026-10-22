package org.firstinspires.ftc.teamcode.Subsystems;

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.util.ElapsedTime;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.OpMode.AutoBlue;
import org.firstinspires.ftc.teamcode.OpMode.AutoRed;

public class RobotClass {
    public DcMotorEx frontLeft, frontRight, backRight, backLeft;
    public IMU imu;

    // Encoder calculation constants
    public static final double COUNTS_PER_MOTOR_REV = 28.0;
    public static final double DRIVE_GEAR_REDUCTION = 1.0;
    public static final double WHEEL_DIAMETER_INCHES = 4.0;
    public static final double COUNTS_PER_INCH = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) / (WHEEL_DIAMETER_INCHES * Math.PI);

    public RobotClass(HardwareMap hardwareMap) {
        // Configure motors
        frontLeft = hardwareMap.get(DcMotorEx.class, "frontLeft");
        frontRight = hardwareMap.get(DcMotorEx.class, "frontRight");
        backLeft = hardwareMap.get(DcMotorEx.class, "backLeft");
        backRight = hardwareMap.get(DcMotorEx.class, "backRight");

        // Set motor directions
        backRight.setDirection(DcMotorSimple.Direction.REVERSE);
        frontRight.setDirection(DcMotorSimple.Direction.REVERSE);

        // Reset and prepare encoders
        resetEncoders();

        // Retrieve and initialize the IMU
        imu = hardwareMap.get(IMU.class, "imu");
        IMU.Parameters parameters = new IMU.Parameters(new RevHubOrientationOnRobot(
                RevHubOrientationOnRobot.LogoFacingDirection.UP,
                RevHubOrientationOnRobot.UsbFacingDirection.BACKWARD));
        imu.initialize(parameters);
    }


     //Resets encoder positions on all drivetrain motors and sets mode to RUN_USING_ENCODER.
    public void resetEncoders() {
        frontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        frontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }


     //Drives all four wheels independently using motor encoders.
    public void encoderDrive(LinearOpMode opMode, double speed,
                             double flInches, double frInches,
                             double blInches, double brInches, double timeoutS) {
        int newFLTarget, newFRTarget, newBLTarget, newBRTarget;
        ElapsedTime runtime = new ElapsedTime();

        if (opMode.opModeIsActive()) {
            // Calculate target tick positions
            newFLTarget = frontLeft.getCurrentPosition() + (int) (flInches * COUNTS_PER_INCH);
            newFRTarget = frontRight.getCurrentPosition() + (int) (frInches * COUNTS_PER_INCH);
            newBLTarget = backLeft.getCurrentPosition() + (int) (blInches * COUNTS_PER_INCH);
            newBRTarget = backRight.getCurrentPosition() + (int) (brInches * COUNTS_PER_INCH);

            runtime.reset();
            frontLeft.setPower(speed);
            frontRight.setPower(speed);
            backLeft.setPower(speed);
            backRight.setPower(speed);

            // Wait until movement is complete or timeout reached
            while (opMode.opModeIsActive() && (runtime.seconds() < timeoutS) &&
                    (frontLeft.isBusy() && frontRight.isBusy() && backLeft.isBusy() && backRight.isBusy())) {
                opMode.telemetry.addData("Running to", "FL:%d FR:%d BL:%d BR:%d",
                        newFLTarget, newFRTarget, newBLTarget, newBRTarget);
                opMode.telemetry.addData("Currently at", "FL:%d FR:%d BL:%d BR:%d",
                        frontLeft.getCurrentPosition(), frontRight.getCurrentPosition(),
                        backLeft.getCurrentPosition(), backRight.getCurrentPosition());
                opMode.telemetry.update();
            }

            // Stop movement
            stopMotors();

            // Reset mode back to RUN_USING_ENCODER
            frontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            frontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            backLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            backRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        }
    }


     //Stops power to all drivetrain motors.
    public void stopMotors() {
        frontLeft.setPower(0);
        frontRight.setPower(0);
        backLeft.setPower(0);
        backRight.setPower(0);
    }

    public double getHeading() {
        return imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES);
    }

    public void encoderDrive(AutoBlue autoBlue, double v, int i, int i1, double v1) {
    }

    public void encoderDrive(AutoRed autoRed, double v, int i, int i1, double v1) {

    }
}
