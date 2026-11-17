package org.firstinspires.ftc.teamcode.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Subsystems.RobotClass;
import org.firstinspires.ftc.teamcode.Subsystems.Tools;

@TeleOp(name = "MecanumDrive", group = "TeleOp")
public class MecanumDrive extends LinearOpMode {
    private RobotClass robot;
    private Tools tools;

    @Override
    public void runOpMode() throws InterruptedException {
        // Driver 1 (Gamepad 1): Drivetrain
        // Driver 2 (Gamepad 2): Subsystems / Tools
        robot = new RobotClass(hardwareMap);
        tools = new Tools(hardwareMap);

        telemetry.addData("Status", "Initialized. Gamepad1 = Chassis | Gamepad2 = Tools");
        telemetry.update();

        waitForStart();

        while (opModeIsActive()) {
            // GAMEPAD 1 (DRIVER): Drivetrain & Chassis Control
            double strafe = gamepad1.left_stick_x;
            double drive = -gamepad1.left_stick_y; // Invert Y stick so forward is positive
            double turn = gamepad1.right_stick_x;

            // Reset field-centric orientation when Options or Back button is pressed on gamepad1
            if (gamepad1.options || gamepad1.back) {
                robot.resetYaw();
                gamepad1.rumble(200);
            }

            // Field-centric mecanum drive
            robot.driveFieldCentric(strafe, drive, turn);

            // GAMEPAD 2 (OPERATOR): Subsystems & Tools Control
            // Intake Control: Right trigger = Intake, Left trigger = Outtake
            if (gamepad2.right_trigger > 0.1) {
                tools.intake.setPower(gamepad2.right_trigger);
            } else if (gamepad2.left_trigger > 0.1) {
                tools.intake.setPower(-gamepad2.left_trigger);
            } else {
                tools.intake.setPower(0);
            }

            // Shooter Control: Right Bumper
            if (gamepad2.right_bumper) {
                tools.shooter.setPower(0.85);
            } else {
                tools.shooter.setPower(0);
            }

            // Flywheel Control: Left Bumper
            if (gamepad2.left_bumper) {
                tools.flywheel.setPower(1.0);
            } else {
                tools.flywheel.setPower(0);
            }

            // Display Telemetry
            telemetry.addData("Heading (deg)", "%.1f", robot.getHeading());
            telemetry.update();
        }
    }
}
