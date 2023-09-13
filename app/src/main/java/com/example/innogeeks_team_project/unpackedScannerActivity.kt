package com.example.innogeeks_team_project

import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import coil.load
import com.example.innogeeks_team_project.databinding.ActivityUnpackedScannerBinding
import com.example.innogeeks_team_project.ml.LiteModelAiyVisionClassifierFoodV11
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import org.tensorflow.lite.support.image.TensorImage

@Suppress("DEPRECATION")
class unpackedScannerActivity : AppCompatActivity() {
    lateinit var bitmap: Bitmap
    lateinit var binding: ActivityUnpackedScannerBinding

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUnpackedScannerBinding.inflate(layoutInflater)
        setContentView(binding.root)
            getPerm()

        binding.imgbtn.setOnClickListener {
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "image/*"
            startActivityForResult(intent, 420)
        }


        binding.cameraBtn.setOnClickListener {
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(intent, 69)
        }


        binding.mlbtn.setOnClickListener {
            val model = LiteModelAiyVisionClassifierFoodV11.newInstance(this)
            bitmap = Bitmap.createScaledBitmap(bitmap,224,224,true)
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

    @RequiresApi(Build.VERSION_CODES.M)
    private fun getPerm() {
        if (checkSelfPermission(android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.CAMERA), 11)
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 11) {
            if (grantResults.isNotEmpty()) {
                if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    this.getPerm()
                }
            }
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 420) {
            if (data != null) {
                var uri = data?.data
                bitmap = MediaStore.Images.Media.getBitmap(contentResolver, uri)
                Log.d("idk", bitmap.toString())
                binding.imgViu.setImageBitmap(bitmap)
            } else {
                Toast.makeText(this, "no image input", Toast.LENGTH_SHORT).show()
            }
        } else if (requestCode == 69) {
            bitmap = data?.extras?.get("data") as Bitmap
            binding.imgViu.setImageBitmap(bitmap)
        }
    }
}