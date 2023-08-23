package com.example.innogeeks_team_project

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.innogeeks_team_project.api.apiservice
import com.example.innogeeks_team_project.api.helper
import com.example.innogeeks_team_project.databinding.FragmentScanBinding
import com.example.innogeeks_team_project.models.Product
import com.example.innogeeks_team_project.models.itemDetails
import com.example.innogeeks_team_project.repository.itemRepo
import com.example.innogeeks_team_project.viewmodels.mainViewModel
import com.example.innogeeks_team_project.viewmodels.mainViewModelFactory
import com.google.mlkit.vision.barcode.common.Barcode

@Suppress("DEPRECATION")
class scanFrag : Fragment() {
    lateinit var mainviewmodel: mainViewModel
    private var BC: String = "8906002001118"
    private var _binding: FragmentScanBinding? = null
    private val binding get() = _binding!!

    private val camerapermission = android.Manifest.permission.CAMERA
    private val resultactivitylauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) {
            if (it) {
                scannerFragment.startScanner(this.requireContext()) { barcodes ->
                    barcodes.forEach { barcode ->
                        when (barcode.valueType) {
                            Barcode.TYPE_TEXT -> {

                            }

                            else -> {
                                Toast.makeText(this.context, "working", Toast.LENGTH_SHORT).show()
                                binding.resTV.text = barcode.rawValue.toString()
                                BC = barcode.rawValue.toString()
                                Log.d("idk", BC)


                            }
                        }
                    }

                }
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentScanBinding.inflate(inflater, container, false)

        return binding.root
    }

    @SuppressLint("UseRequireInsteadOfGet")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val service = helper.getInstance().create(apiservice::class.java)
        val repo = itemRepo(service)
        mainviewmodel =
            ViewModelProvider(this, mainViewModelFactory(repo)).get(mainViewModel::class.java)



        binding.btnhu.setOnClickListener {
            val transaction = childFragmentManager.beginTransaction()
            transaction.replace(R.id.raita, scannerFragment())
            transaction.commit()
            requestCameraAndStartScanner()

        }




        binding.dusrabtn.setOnClickListener {
            Log.d("idk", BC)
            mainviewmodel.selectItem(BC)
//            binding.allergenTV.text  = mainviewmodel.fooditem.value?.product?.brands
            Log.d("idk", mainviewmodel.fooditem.value.toString())

        }



        mainviewmodel.fooditem.observe(viewLifecycleOwner, Observer {
            Log.d("idk",it.toString())
            binding.allergenTV.text = repo.fooditem.value?.product?.brands.toString()
            Log.d("idk", it.toString())
        })

    }


    private fun requestCameraAndStartScanner() {

        if (requireActivity().isPermissionGranted(camerapermission)) {
            scannerFragment.startScanner(this.requireContext()) { barcodes ->
                barcodes.forEach { barcode ->
                    when (barcode.valueType) {
                        Barcode.TYPE_TEXT -> {
                        }

                        else -> {
                            Toast.makeText(this.context, "working", Toast.LENGTH_SHORT).show()
                            binding.resTV.text = barcode.rawValue.toString()
                            BC = barcode.rawValue.toString()
                            Log.d("idk", BC)
                        }
                    }
                }
            }
        } else {
            requestCameraPermissionn()
        }

    }

    private fun requestCameraPermissionn() {
        if (shouldShowRequestPermissionRationale(camerapermission)) {
            requireActivity().cameraPermissionRequest {
                requireActivity().openPermissionSetting()
            }
        } else {
            resultactivitylauncher.launch(camerapermission)

        }
    }
}

