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


    lateinit var bitmap: Bitmap
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



        binding.btnhu.setOnClickListener {
            val transaction = childFragmentManager.beginTransaction()
            transaction.replace(R.id.raita, scannerFragment())
            transaction.commit()
            requestCameraAndStartScanner()

        }

        binding.dusrabtn.setOnClickListener {
            Log.d("idk", BC)

//            itemRepo(service).getFoodItem(
            mainviewmodel.fetchProductDetails(BC)

//            binding.allergenTV.text  = mainviewmodel.fooditem.value?.product?.brands
//            Log.d("idk", mainviewmodel.fooditem.value.toString())
//            binding.allergenTV.text = repo.fooditem.value?.product?.brands


        }


        mainviewmodel.productDetails.observe(viewLifecycleOwner) { productDetails ->

//            Log.d("idk-2", it.toString())
            binding.allergenTV.text = productDetails.product.brands
//            Log.d("idk-2", it?.product?.brands!!)
        }

//        mainviewmodel.fooditem.observe(viewLifecycleOwner, Observer { itemDetails ->
//            if (itemDetails != null) {
//                Log.d("idk-2", itemDetails.toString())
//                binding.allergenTV.text = itemDetails.code

        // Ensure product and brands are not null before accessing them
//                val productBrands = itemDetails.product.brands
//                if (productBrands != null) {
//                    Log.d("idk-2", productBrands)
//                } else {
//                    Log.d("idk-2", "Product brands are null")
//                }
//            } else {
//                Log.d("idk-2", "Item details are null")
//            }
//        })
        binding.imgbtn.setOnClickListener {
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "image/*"
            startActivityForResult(intent, 420)
        }


        binding.mlbtn.setOnClickListener {
            val model = LiteModelAiyVisionClassifierFoodV11.newInstance(context!!)
            val image = TensorImage.fromBitmap(bitmap)

            val outputs = model.process(image).probabilityAsCategoryList.apply {
                sortByDescending {
                    it.score
                }
            }
            val probabilityOP = outputs[0]
            binding.naam.text = probabilityOP.label
            Log.d("idk", outputs.toString())
            Log.d("idk", probabilityOP.label.toString())
            model.close()

        }
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 420) {
            var uri = data?.data
            bitmap = MediaStore.Images.Media.getBitmap(activity?.contentResolver, uri)
            binding.imgViu.setImageBitmap(bitmap)

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

