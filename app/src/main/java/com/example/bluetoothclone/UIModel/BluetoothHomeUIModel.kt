package com.example.bluetoothclone.UIModel

import com.example.bluetoothclone.dataclass.BluetoothDeviceData

sealed class BluetoothHomeUIModel{

    data class Connected(val bluetoothDeviceList: List<BluetoothDeviceData>) : BluetoothHomeUIModel()

    data class scanForDevices(val bluetoothDeviceList: List<BluetoothDeviceData>) : BluetoothHomeUIModel()

}
