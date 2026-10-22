package org.firstinspires.ftc.teamcode.Subsystems;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Tools {
    public DcMotorEx shooter, intake, flywheel;
    public Tools(HardwareMap hardwareMap) {
        shooter =  hardwareMap.get(DcMotorEx.class, "shooter");
        intake =  hardwareMap.get(DcMotorEx.class, "intake");
        flywheel = 
hardwareMap.get(DcMotorEx.class, "flywheel);


    }

}