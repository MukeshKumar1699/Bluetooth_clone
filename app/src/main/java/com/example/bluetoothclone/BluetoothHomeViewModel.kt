package com.example.bluetoothclone

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class BluetoothHomeViewModel : ViewModel() {

    private val repository = BluetoothHomeRepository()

    private var mutableLiveData = MutableLiveData<BluetoothHomeUIModel>()

    val liveData: LiveData<BluetoothHomeUIModel> = mutableLiveData


    fun getPairedDevices() {

        mutableLiveData.value = BluetoothHomeUIModel.Success(repository.getPairedDevices())
    }
}