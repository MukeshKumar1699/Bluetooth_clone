package com.example.bluetoothclone.activities

import android.annotation.SuppressLint
import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bluetoothclone.AvailBluetoothDeviceAdapter
import com.example.bluetoothclone.dataclass.BluetoothDeviceData
import com.example.bluetoothclone.listeners.ItemClickListener
import com.example.bluetoothclone.R
import com.example.bluetoothclone.databinding.ActivityBluetoothHomeBinding


class BluetoothHomeActivity : AppCompatActivity(), ItemClickListener {

    private lateinit var binding: ActivityBluetoothHomeBinding

    private lateinit var bluetoothAdapter: BluetoothAdapter
    private val REQUEST_ENABLE_BT: Int = 1


    private lateinit var recyclerAdapter: AvailBluetoothDeviceAdapter
    private var bluetoothDeviceDataList = ArrayList<BluetoothDeviceData>()

    private var scanState: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBluetoothHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)


        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()


        if (bluetoothAdapter == null) {
            Toast.makeText(
                this@BluetoothHomeActivity,
                "Bluetooth Hardware Not Detected",
                Toast.LENGTH_SHORT
            )
                .show()
        } else {

            initRecycler()
            initDefaultsAndBluetoothState()
        }

    }

    private fun initRecycler() {

        recyclerAdapter = AvailBluetoothDeviceAdapter(bluetoothDeviceDataList, this)
        val layoutManager =
            LinearLayoutManager(this@BluetoothHomeActivity)

        binding.rcView.apply {

            this.adapter = recyclerAdapter
            this.layoutManager = layoutManager
        }
    }

    @SuppressLint("SetTextI18n")
    private fun initDefaultsAndBluetoothState() {

        if (bluetoothAdapter.isEnabled) {
            binding.toolbar.swBluetoothOP.isChecked = true
            updateUI(View.VISIBLE, R.drawable.ic_bluetooth_on)
        }

        val filter = IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED)
        registerReceiver(receiveBluetoothStateChange, filter)

        binding.toolbar.swBluetoothOP.setOnCheckedChangeListener { buttonView, isChecked ->

            if (isChecked) {
                if (!bluetoothAdapter.isEnabled) {
                    val enableBtIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
                    startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT)
                } else {
                    updateUI(View.VISIBLE, R.drawable.ic_bluetooth_on)
                }
            } else {
                bluetoothAdapter.disable()
                updateUI(View.GONE, R.drawable.ic_bluetooth_off)
            }

        }

        binding.btnDiscover.setOnClickListener {

            if (scanState) {
                cancelDiscovery()
            } else {
                Toast.makeText(this@BluetoothHomeActivity, "Search will be available for 120 sec", Toast.LENGTH_SHORT).show()
                startDiscovery()

                Handler(Looper.getMainLooper()).postDelayed({
                    cancelDiscovery()
                }, 1200000)

            }
        }

        binding.btnConnectedDevices.setOnClickListener {

            val intent = Intent(this@BluetoothHomeActivity, PairedDeviceActivity::class.java)
            startActivity(intent)

        }

    }

    @SuppressLint("SetTextI18n")
    private fun cancelDiscovery() {
        binding.btnDiscover.text = "Start Scanning Device"
        scanState = false
        bluetoothAdapter.isDiscovering
        bluetoothAdapter.cancelDiscovery()
        recyclerAdapter.deleteRecyclerData()
    }

    @SuppressLint("SetTextI18n")
    private fun startDiscovery() {
        binding.btnDiscover.text = "Stop Scanning Device"
        scanState = true
        bluetoothAdapter.startDiscovery()

        val filter1 = IntentFilter(BluetoothDevice.ACTION_FOUND)
        registerReceiver(receiveBluetoothDevices, filter1)
    }

    // Create a BroadcastReceiver for ACTION_FOUND.
    private val receiveBluetoothDevices = object : BroadcastReceiver() {

        override fun onReceive(context: Context, intent: Intent) {
            val action: String? = intent.action

            when (action) {
                BluetoothDevice.ACTION_FOUND -> {
                    // Discovery has found a device. Get the BluetoothDevice
                    // object and its info from the Intent.
                    val device: BluetoothDevice? =
                        intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE)
//                    Toast.makeText(this@BluetoothHomeActivity, device?.name, Toast.LENGTH_SHORT).show()
                    val bluetoothDeviceData = BluetoothDeviceData(
                        deviceName = device!!.name,
                        deviceAddress = device.address
                    )
                    if (!bluetoothDeviceDataList.contains(bluetoothDeviceData)) {
                        bluetoothDeviceDataList.add(bluetoothDeviceData)
                    }
                    updateRecyclerData(bluetoothDeviceDataList)
                }
            }
        }

    }
    private val receiveBluetoothStateChange: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent) {
            val action = intent.action

            when (action) {

                BluetoothAdapter.ACTION_STATE_CHANGED -> {

                    val state: Int =
                        intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, BluetoothAdapter.ERROR);

                    when (state) {
                        BluetoothAdapter.STATE_OFF -> {
//                            Toast.makeText(
//                                this@BluetoothHomeActivity,
//                                "Bluetooth State OFF",
//                                Toast.LENGTH_SHORT
//                            ).show()
                            binding.ivStatus.setImageResource(R.drawable.ic_bluetooth_off)
                            binding.toolbar.swBluetoothOP.isChecked = false
                        }

                        BluetoothAdapter.STATE_TURNING_OFF -> {
//                            Toast.makeText(
//                                this@BluetoothHomeActivity,
//                                "Bluetooth Turning OFF",
//                                Toast.LENGTH_SHORT
//                            ).show()

                        }

                        BluetoothAdapter.STATE_ON -> {
//                            Toast.makeText(
//                                this@BluetoothHomeActivity,
//                                "Bluetooth State ON",
//                                Toast.LENGTH_SHORT
//                            ).show()
                            binding.ivStatus.setImageResource(R.drawable.ic_bluetooth_on)
                            binding.toolbar.swBluetoothOP.isChecked = true


                        }
                        BluetoothAdapter.STATE_TURNING_ON -> {
//                            Toast.makeText(
//                                this@BluetoothHomeActivity,
//                                "Bluetooth Turning ON",
//                                Toast.LENGTH_SHORT
//                            ).show()
                        }
                    }
                }
            }
        }
    }

    fun updateRecyclerData(bluetoothDeviceDataList: ArrayList<BluetoothDeviceData>) {
        recyclerAdapter.updateRecylcerData(bluetoothDeviceDataList = bluetoothDeviceDataList)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        when (requestCode) {

            REQUEST_ENABLE_BT ->

                if (resultCode == Activity.RESULT_OK) {
                    bluetoothAdapter.isEnabled
                    updateUI(View.VISIBLE, R.drawable.ic_bluetooth_on)
                } else {
                    bluetoothAdapter.disable()
                    updateUI(View.GONE, R.drawable.ic_bluetooth_off)
                }
        }
        super.onActivityResult(requestCode, resultCode, data)

    }

    private fun updateUI(view: Int, icBluetoothStatus: Int) {

        binding.ivStatus.setImageResource(icBluetoothStatus)
        binding.btnDiscover.visibility = view
        binding.btnConnectedDevices.visibility = view
        binding.rcView.visibility = view
    }

    override fun onBluetoothDeviceItemCLicked(position: Int, bluetoothDeviceData: BluetoothDeviceData) {

        Toast.makeText(
            this@BluetoothHomeActivity,
            bluetoothDeviceData.deviceName,
            Toast.LENGTH_SHORT
        ).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(receiveBluetoothStateChange)
        unregisterReceiver(receiveBluetoothDevices)
    }


}