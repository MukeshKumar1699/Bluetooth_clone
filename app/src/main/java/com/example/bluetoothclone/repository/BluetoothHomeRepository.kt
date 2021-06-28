package com.example.bluetoothclone.repository

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import com.example.bluetoothclone.dataclass.BluetoothDeviceData

class BluetoothHomeRepository() {

    private var bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()

    fun getPairedDevices(): List<BluetoothDeviceData> {


        var bluetoothDeviceDataList = ArrayList<BluetoothDeviceData>()

        val pairedDevices: Set<BluetoothDevice>? = bluetoothAdapter?.bondedDevices

        pairedDevices?.forEach { device ->
            val bluetoothDeviceData = BluetoothDeviceData(
                deviceName = device.name,
                deviceAddress = device.address
            )
            if (!bluetoothDeviceDataList.contains(bluetoothDeviceData)) {
                bluetoothDeviceDataList.add(bluetoothDeviceData)
            }
        }
        return bluetoothDeviceDataList
    }

    fun scanForDevices(): List<BluetoothDeviceData> {

        var bluetoothDeviceDataList = ArrayList<BluetoothDeviceData>()



        return bluetoothDeviceDataList
    }
}