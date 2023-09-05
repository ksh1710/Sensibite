package com.example.innogeeks_team_project

import android.content.Intent
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import com.example.innogeeks_team_project.databinding.ActivityUnpackedScannerBinding
import com.example.innogeeks_team_project.ml.LiteModelAiyVisionClassifierFoodV11
import org.tensorflow.lite.support.image.TensorImage

@Suppress("DEPRECATION")
class unpackedScannerActivity : AppCompatActivity() {
    lateinit var bitmap: Bitmap
    lateinit var binding: ActivityUnpackedScannerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUnpackedScannerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.imgbtn.setOnClickListener {
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "image/*"
            startActivityForResult(intent, 420)
        }


        binding.mlbtn.setOnClickListener {
            val model = LiteModelAiyVisionClassifierFoodV11.newInstance(this)
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
            bitmap = MediaStore.Images.Media.getBitmap(contentResolver, uri)
            binding.imgViu.setImageBitmap(bitmap)

        }
    }

}