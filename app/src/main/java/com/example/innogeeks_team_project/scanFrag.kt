package com.example.innogeeks_team_project

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.innogeeks_team_project.api.ApiService
import com.example.innogeeks_team_project.api.helper
import com.example.innogeeks_team_project.databinding.FragmentScanBinding
import com.example.innogeeks_team_project.ml.LiteModelAiyVisionClassifierFoodV11
import com.example.innogeeks_team_project.repository.itemRepo
import com.example.innogeeks_team_project.viewmodels.ProductViewModel
import com.example.innogeeks_team_project.viewmodels.ProductViewModelFactory
import com.google.mlkit.vision.barcode.common.Barcode
import org.tensorflow.lite.support.image.TensorImage
import retrofit2.http.Path
import java.util.Locale.Category

@Suppress("DEPRECATION")
class scanFrag : Fragment() {

    lateinit var mainviewmodel: ProductViewModel
    private var BC: String = "737628064502"
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

        val service = helper.getInstance().create(ApiService::class.java)
        val repo = itemRepo(service)
        mainviewmodel =
            ViewModelProvider(
                this,
                ProductViewModelFactory(service)
            ).get(ProductViewModel::class.java)



        binding.UnpackedBtn.setOnClickListener {
            val intent: Intent = Intent(activity, unpackedScannerActivity::class.java)
            startActivity(intent)
        }


        binding.btnhu.setOnClickListener {
            val transaction = childFragmentManager.beginTransaction()
            transaction.replace(R.id.raita, scannerFragment())
            transaction.commit()
            requestCameraAndStartScanner()

        }

        binding.dusrabtn.setOnClickListener {

            mainviewmodel.fetchProductDetails(BC)
        }

        mainviewmodel.productDetails.observe(viewLifecycleOwner) { productDetails ->
            binding.brandTV.text = productDetails.product.brands
            binding.categoryTV.text = productDetails.product.categories
            binding.allergenTV.text = productDetails.product.allergens_from_ingredients
        }

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

