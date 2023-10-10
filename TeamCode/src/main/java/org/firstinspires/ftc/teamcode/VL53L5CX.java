package org.firstinspires.ftc.teamcode;

import android.app.backup.RestoreObserver;

import com.qualcomm.robotcore.hardware.I2cAddr;
import com.qualcomm.robotcore.hardware.I2cDeviceSynch;
import com.qualcomm.robotcore.hardware.I2cDeviceSynchDevice;
import com.qualcomm.robotcore.hardware.configuration.annotations.DeviceProperties;
import com.qualcomm.robotcore.hardware.configuration.annotations.I2cDeviceType;
import com.qualcomm.robotcore.util.TypeConversion;
import static com.qualcomm.hardware.stmicroelectronics.VL53L0X.*;


/**
 * Guide to write driver: https://github.com/FIRST-Tech-Challenge/ftcrobotcontroller/wiki/Writing-an-I2C-Driver
 * Example rev distance sensor: https://github.com/REVrobotics/2m-Distance-Sensor/blob/master/Source/src/main/java/com/revrobotics/Rev2mDistanceSensor.java
 */

@I2cDeviceType
@DeviceProperties(name = "VL53L5 ToF Sensor", description = "This is the time of flight sensor from STM", xmlTag = "VL53L5")

public class VL53L5CX extends I2cDeviceSynchDevice<I2cDeviceSynch> {
    public enum Register {
        FIRST(0),
        WRITE(0x52),
        READ(0x53),
        LAST(READ.bVal);

        public int bVal;

        Register(int bVal) {
            this.bVal = bVal;
        }
    }

    protected void setOptimalReadWindow() {
        // Sensor registers are read repeatedly and stored in a register. This method specifies the
        // registers and repeat read mode
        I2cDeviceSynch.ReadWindow readWindow = new I2cDeviceSynch.ReadWindow(
                Register.FIRST.bVal,
                Register.LAST.bVal - Register.FIRST.bVal + 1,
                I2cDeviceSynch.ReadMode.REPEAT);
        this.deviceClient.setReadWindow(readWindow);
    }

    public VL53L5CX(I2cDeviceSynch deviceClient, boolean deviceClientIsOwned) {
        super(deviceClient, deviceClientIsOwned);

        this.deviceClient.setI2cAddress(ADDRESS_I2C_DEFAULT);

        super.registerArmingStateCallback(false);
        this.deviceClient.engage();
    }

    public final static I2cAddr ADDRESS_I2C_DEFAULT = I2cAddr.create8bit(0x52);

    @Override
    public Manufacturer getManufacturer() {
        return Manufacturer.STMicroelectronics;
    }

    @Override
    protected synchronized boolean doInitialize() {
        return true;
    }

    @Override
    public String getDeviceName() {
        return "STMicroelectronics VL53L5 ToF Sensor";
    }

    protected void writeShort(final Register reg, short value)
    {
        deviceClient.write(reg.bVal, TypeConversion.shortToByteArray(value));
    }
    protected short read(Register reg) {

        return TypeConversion.byteArrayToShort(deviceClient.read(reg.bVal, 2));
    }

}