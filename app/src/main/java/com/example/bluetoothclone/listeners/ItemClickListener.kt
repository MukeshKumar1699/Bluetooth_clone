package com.example.bluetoothclone.listeners

import com.example.bluetoothclone.dataclass.BluetoothDeviceData

interface ItemClickListener {

    fun onBluetoothDeviceItemCLicked(position: Int, bluetoothDeviceData: BluetoothDeviceData)
}