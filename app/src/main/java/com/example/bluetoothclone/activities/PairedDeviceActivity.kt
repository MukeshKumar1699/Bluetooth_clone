package com.example.bluetoothclone.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bluetoothclone.*
import com.example.bluetoothclone.UIModel.BluetoothHomeUIModel
import com.example.bluetoothclone.ViewModel.BluetoothHomeViewModel
import com.example.bluetoothclone.databinding.ActivityPairedDeviceBinding
import com.example.bluetoothclone.dataclass.BluetoothDeviceData
import com.example.bluetoothclone.listeners.ItemClickListener
import java.util.*

class PairedDeviceActivity : AppCompatActivity(), ItemClickListener {

    private lateinit var binding: ActivityPairedDeviceBinding

    private lateinit var bluetoothHomeViewModel: BluetoothHomeViewModel

    private lateinit var recyclerAdapter: AvailBluetoothDeviceAdapter
    private var bluetoothDeviceDataList = ArrayList<BluetoothDeviceData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPairedDeviceBinding.inflate(layoutInflater)
        setContentView(binding.root)

        init()
        initRecycler()
    }

    private fun init() {

        bluetoothHomeViewModel = ViewModelProvider(this).get(BluetoothHomeViewModel::class.java)

        bluetoothHomeViewModel.getPairedDevices()
        observeLiveData()

    }

    private fun observeLiveData() {

        bluetoothHomeViewModel.liveData.observe(this, {
            when (it) {

                is BluetoothHomeUIModel.Connected -> {
                    recyclerAdapter.updateRecylcerData(it.bluetoothDeviceList as ArrayList<BluetoothDeviceData>)
                }
            }
        })
    }

    private fun initRecycler() {
        recyclerAdapter = AvailBluetoothDeviceAdapter(bluetoothDeviceDataList, this)
        val layoutManager = LinearLayoutManager(this@PairedDeviceActivity)

        binding.rcView.apply {
            adapter = recyclerAdapter
            this.layoutManager = layoutManager
        }

    }

    override fun onBluetoothDeviceItemCLicked(
        position: Int,
        bluetoothDeviceData: BluetoothDeviceData
    ) {
    }
}