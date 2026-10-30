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

public class RobotClass {
    public DcMotorEx frontLeft, frontRight, backRight, backLeft;
    public IMU imu;

    // Encoder calculation constants
    public static final double COUNTS_PER_MOTOR_REV = 28.0;
    public static final double DRIVE_GEAR_REDUCTION = 1.0;
    public static final double WHEEL_DIAMETER_INCHES = 4.0;
    public static final double COUNTS_PER_INCH = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION)
            / (WHEEL_DIAMETER_INCHES * Math.PI);

    public RobotClass(HardwareMap hardwareMap) {
        frontLeft = hardwareMap.get(DcMotorEx.class, "frontLeft");
        frontRight = hardwareMap.get(DcMotorEx.class, "frontRight");
        backLeft = hardwareMap.get(DcMotorEx.class, "backLeft");
        backRight = hardwareMap.get(DcMotorEx.class, "backRight");

        backRight.setDirection(DcMotorSimple.Direction.REVERSE);
        frontRight.setDirection(DcMotorSimple.Direction.REVERSE);

        resetEncoders();

        imu = hardwareMap.get(IMU.class, "imu");
        IMU.Parameters parameters = new IMU.Parameters(new RevHubOrientationOnRobot(
                RevHubOrientationOnRobot.LogoFacingDirection.UP,
                RevHubOrientationOnRobot.UsbFacingDirection.BACKWARD));
        imu.initialize(parameters);
    }

    //Resets drivetrain encoders and sets them to RUN_USING_ENCODER
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

    //Drives each wheel the requested distance using encoders and a timeout
    public void encoderDrive(LinearOpMode opMode, double speed,
                             double flInches, double frInches,
                             double blInches, double brInches, double timeoutS) {
        if (!opMode.opModeIsActive()) {
            return;
        }

        int newFLTarget = frontLeft.getCurrentPosition() + (int) Math.round(flInches * COUNTS_PER_INCH);
        int newFRTarget = frontRight.getCurrentPosition() + (int) Math.round(frInches * COUNTS_PER_INCH);
        int newBLTarget = backLeft.getCurrentPosition() + (int) Math.round(blInches * COUNTS_PER_INCH);
        int newBRTarget = backRight.getCurrentPosition() + (int) Math.round(brInches * COUNTS_PER_INCH);

        frontLeft.setTargetPosition(newFLTarget);
        frontRight.setTargetPosition(newFRTarget);
        backLeft.setTargetPosition(newBLTarget);
        backRight.setTargetPosition(newBRTarget);

        frontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        double positiveSpeed = Math.abs(speed);
        frontLeft.setPower(positiveSpeed);
        frontRight.setPower(positiveSpeed);
        backLeft.setPower(positiveSpeed);
        backRight.setPower(positiveSpeed);

        ElapsedTime runtime = new ElapsedTime();
        runtime.reset();
        while (opMode.opModeIsActive() && runtime.seconds() < timeoutS
                && (frontLeft.isBusy() || frontRight.isBusy() || backLeft.isBusy() || backRight.isBusy())) {
            opMode.telemetry.addData("Targets", "FL:%d FR:%d BL:%d BR:%d",
                    newFLTarget, newFRTarget, newBLTarget, newBRTarget);
            opMode.telemetry.addData("Positions", "FL:%d FR:%d BL:%d BR:%d",
                    frontLeft.getCurrentPosition(), frontRight.getCurrentPosition(),
                    backLeft.getCurrentPosition(), backRight.getCurrentPosition());
            opMode.telemetry.update();
            opMode.idle();
        }

        stopMotors();
        frontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    //Stops power to all drivetrain motors
    public void stopMotors() {
        frontLeft.setPower(0.0);
        frontRight.setPower(0.0);
        backLeft.setPower(0.0);
        backRight.setPower(0.0);
    }

    public double getHeading() {
        return imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES);
    }
}
