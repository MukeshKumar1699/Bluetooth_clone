package com.example.bluetoothclone.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.bluetoothclone.UIModel.BluetoothHomeUIModel
import com.example.bluetoothclone.repository.BluetoothHomeRepository

class BluetoothHomeViewModel : ViewModel() {

    private val repository = BluetoothHomeRepository()

    private var mutableLiveData = MutableLiveData<BluetoothHomeUIModel>()

    val liveData: LiveData<BluetoothHomeUIModel> = mutableLiveData


    fun getPairedDevices() {

        mutableLiveData.value = BluetoothHomeUIModel.Connected(repository.getPairedDevices())
    }

    fun scanForDevices() {
        mutableLiveData.value = BluetoothHomeUIModel.scanForDevices(repository.scanForDevices())
    }
}