package com.example.bluetoothclone

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.bluetoothclone.databinding.AvailBluetoothDeviceBinding

class AvailBluetoothDeviceAdapter(var bluetoothDeviceDataList: ArrayList<BluetoothDeviceData>) :
    RecyclerView.Adapter<AvailBluetoothDeviceViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AvailBluetoothDeviceViewHolder {

        val binding =
            AvailBluetoothDeviceBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AvailBluetoothDeviceViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AvailBluetoothDeviceViewHolder, position: Int) {

        val bluetoothDeviceData = bluetoothDeviceDataList[position]
        holder.setData(bluetoothDeviceData)
    }

    override fun getItemCount(): Int {

        return bluetoothDeviceDataList.size
    }

    fun updateRecylcerData(bluetoothDeviceDataList: ArrayList<BluetoothDeviceData>) {
//        this.bluetoothDeviceDataList.clear()
        this.bluetoothDeviceDataList = bluetoothDeviceDataList
        notifyDataSetChanged()
    }


}

class AvailBluetoothDeviceViewHolder(val binding: AvailBluetoothDeviceBinding) :

    RecyclerView.ViewHolder(binding.root) {

    @SuppressLint("SetTextI18n")
    fun setData(bluetoothDeviceData: BluetoothDeviceData) {

        binding.tvDeviceName.text = bluetoothDeviceData.deviceName
        binding.tvDeviceAddress.text = bluetoothDeviceData.deviceAddress
    }

}