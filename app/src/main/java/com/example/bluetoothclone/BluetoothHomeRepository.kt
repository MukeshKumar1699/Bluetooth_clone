package com.example.bluetoothclone

import android.bluetooth.BluetoothAdapter

class BluetoothHomeRepository() {

    private lateinit var bluetoothAdapter: BluetoothAdapter
    private var bluetoothDeviceDataList = ArrayList<BluetoothDeviceData>()


    fun getPairedDevices(): List<BluetoothDeviceData> {

        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
        val devices = bluetoothAdapter.bondedDevices

        for (device in devices) {

            val bluetoothDeviceData = BluetoothDeviceData(
                deviceName = device.name,
                deviceAddress = device.address
            )
            bluetoothDeviceDataList.add(bluetoothDeviceData)
        }
        return bluetoothDeviceDataList
    }
}