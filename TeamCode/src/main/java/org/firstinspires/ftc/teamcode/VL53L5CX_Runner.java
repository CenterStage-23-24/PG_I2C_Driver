package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

public class VL53L5CX_Runner extends LinearOpMode {

    private VL53L5CX sensor;

    public void runOpMode() throws InterruptedException {
        sensor = hardwareMap.get(VL53L5CX.class, "ToF Sensor");
    }

}