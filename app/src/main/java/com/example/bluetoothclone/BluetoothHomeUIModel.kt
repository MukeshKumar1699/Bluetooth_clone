package com.example.bluetoothclone

sealed class BluetoothHomeUIModel{

    data class Connected(val bluetoothDeviceList: List<BluetoothDeviceData>) : BluetoothHomeUIModel()

    data class scanForDevices(val bluetoothDeviceList: List<BluetoothDeviceData>) : BluetoothHomeUIModel()

}
