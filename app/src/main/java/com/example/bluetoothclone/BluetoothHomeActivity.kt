package com.example.bluetoothclone

import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothDevice.ACTION_FOUND
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bluetoothclone.databinding.ActivityBluetoothHomeBinding


class BluetoothHomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBluetoothHomeBinding

    private lateinit var bluetoothAdapter: BluetoothAdapter
    private val REQUESTCODE_ENABLE_BT: Int = 1

    private lateinit var bluetoothHomeViewModel: BluetoothHomeViewModel


    private lateinit var recyclerAdapter: AvailBluetoothDeviceAdapter

    private var bluetoothDeviceDataList = ArrayList<BluetoothDeviceData>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityBluetoothHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initDefaults()
        initBlueTooth()
        initRecycler()
        observeLiveData()

    }

    private fun initDefaults() {

        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()

        bluetoothHomeViewModel = ViewModelProvider(this).get(BluetoothHomeViewModel::class.java)

        val filter = IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED)
        registerReceiver(mReceiver, filter)

//        binding.toolbar.swBluetoothOP.setOnCheckedChangeListener { buttonView, isChecked ->
//
//            if (bluetoothAdapter.isEnabled) {
//                binding.toolbar.swBluetoothOP.isChecked = true
//            }
//            if (isChecked) {
//                startActivityForResult(
//                    Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE),
//                    REQUESTCODE_ENABLE_BT
//                )
//            } else if (!isChecked) {
//
//                bluetoothAdapter.disable()
//                updateUI(View.GONE, R.drawable.ic_bluetooth_off)
//
//            }
//
//        }

    }

    private fun updateUI(view: Int, icBluetoothStatus: Int) {


        binding.ivStatus.setImageResource(icBluetoothStatus)
        binding.btnDiscover.visibility = view
        binding.btnConnectedDevices.visibility = view
        binding.rcView.visibility = view
    }

    private fun initRecycler() {

        recyclerAdapter = AvailBluetoothDeviceAdapter(bluetoothDeviceDataList)
        val layoutManager =
            LinearLayoutManager(this@BluetoothHomeActivity)

        binding.rcView.apply {

            this.adapter = recyclerAdapter
            this.layoutManager = layoutManager
        }
    }

    private fun initBlueTooth() {

        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()

        //check Bluetooth is enabled or Not
//        if (bluetoothAdapter.disable()) {
//            binding.ivStatus.setImageResource(R.drawable.ic_bluetooth_off)
//            Toast.makeText(applicationContext, "Turn On Bluetooth", Toast.LENGTH_SHORT).show()
//        } else {
//            binding.ivStatus.setImageResource(R.drawable.ic_bluetooth_on)
//        }


//        binding.btnDiscover.setOnClickListener {
//
//            if (bluetoothAdapter.isEnabled) {
//
//                bluetoothAdapter.startDiscovery()
//
//
//
//                recyclerAdapter.updateRecylcerData(bluetoothDeviceDataList = bluetoothDeviceDataList)
//
//            } else {
//                Toast.makeText(applicationContext, "Turn On Bluetooth", Toast.LENGTH_SHORT).show()
//
////                startActivityForResult(
////                    Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE),
////                    REQUESTCODE_ENABLE_BT
////                )
//
//            }
//        }

//        binding.btnConnectedDevices.setOnClickListener {
//
//            if (bluetoothAdapter.isEnabled) {
//                bluetoothHomeViewModel.getPairedDevices()
//            } else {
//                Toast.makeText(applicationContext, "Turn On Bluetooth", Toast.LENGTH_SHORT).show()
//            }
//            if (bluetoothAdapter.isEnabled) {
//
//                val devices = bluetoothAdapter.bondedDevices
//
//                for (device in devices) {
//
//                    val bluetoothDeviceData = BluetoothDeviceData(
//                        deviceName = device.name,
//                        deviceAddress = device.address
//                    )
//
//                    bluetoothDeviceDataList.add(bluetoothDeviceData)
//                }
//                recyclerAdapter.updateRecylcerData(bluetoothDeviceDataList = bluetoothDeviceDataList)
//
//            } else {
//                Toast.makeText(applicationContext, "Turn On Bluetooth", Toast.LENGTH_SHORT).show()
//            }
//        }
    }

    private val mReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent) {
            val action = intent.action

            when (action) {

                BluetoothAdapter.ACTION_STATE_CHANGED -> {

                    val state: Int =
                        intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, BluetoothAdapter.ERROR);

                    when (state) {
                        BluetoothAdapter.STATE_OFF -> {
                            binding.ivStatus.setImageResource(R.drawable.ic_bluetooth_off)
                            binding.toolbar.swBluetoothOP.isChecked = false
                        }

                        BluetoothAdapter.STATE_TURNING_OFF -> {
                            Toast.makeText(
                                this@BluetoothHomeActivity,
                                "Bluetooth Turning OFF",
                                Toast.LENGTH_SHORT
                            ).show()

                        }

                        BluetoothAdapter.STATE_ON -> {
                            binding.ivStatus.setImageResource(R.drawable.ic_bluetooth_on)
                            binding.toolbar.swBluetoothOP.isChecked = false


                        }
                        BluetoothAdapter.STATE_TURNING_ON -> {
                            Toast.makeText(
                                this@BluetoothHomeActivity,
                                "Bluetooth Turning ON",
                                Toast.LENGTH_SHORT
                            ).show()

                        }
                    }

                }


                ACTION_FOUND -> {

                    val device: BluetoothDevice? = intent
                        .getParcelableExtra(BluetoothDevice.EXTRA_DEVICE)

                    Toast.makeText(this@BluetoothHomeActivity, device!!.name, Toast.LENGTH_SHORT)
                        .show()

                    bluetoothDeviceDataList.add(
                        BluetoothDeviceData(
                            deviceName = device!!.name,
                            deviceAddress = device.address
                        )
                    )

                }
            }

        }
    }

        override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

            when (requestCode) {

                REQUESTCODE_ENABLE_BT ->

                    if (resultCode == Activity.RESULT_OK) {
                        binding.ivStatus.setImageResource(R.drawable.ic_bluetooth_on)
                        binding.btnDiscover.visibility = View.VISIBLE
                        binding.btnConnectedDevices.visibility = View.VISIBLE
                        binding.rcView.visibility = View.VISIBLE
                    } else {
                        binding.ivStatus.setImageResource(R.drawable.ic_bluetooth_off)
                    }
            }
            super.onActivityResult(requestCode, resultCode, data)

        }

        private fun observeLiveData() {

            bluetoothHomeViewModel.liveData.observe(this, {
                when (it) {

                    is BluetoothHomeUIModel.Success -> {
                        recyclerAdapter.updateRecylcerData(it.TopRatedList as ArrayList<BluetoothDeviceData>)
                    }

                }

            })
        }

        override fun onDestroy() {
            super.onDestroy()
            unregisterReceiver(mReceiver)
        }


    }