package com.example.bluetoothclone

sealed class BluetoothHomeUIModel{

    data class Success(val TopRatedList: List<BluetoothDeviceData>) : BluetoothHomeUIModel()

}
