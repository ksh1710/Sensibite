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
import com.example.innogeeks_team_project.ml.TfLiteModel
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import org.tensorflow.lite.DataType
import org.tensorflow.lite.support.image.ImageProcessor
import org.tensorflow.lite.support.image.TensorImage
import org.tensorflow.lite.support.image.ops.ResizeOp
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer

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


        var imageProcessor =
            ImageProcessor.Builder().add(ResizeOp(299, 299, ResizeOp.ResizeMethod.BILINEAR)).build()



        binding.mlbtn.setOnClickListener {
            binding.avoidTV.text = " "
            binding.diseaseTV.text = " "
            binding.allergenTV.text = " "


            val model = LiteModelAiyVisionClassifierFoodV11.newInstance(this)
            bitmap = Bitmap.createScaledBitmap(bitmap, 224, 224, true)
            val image = TensorImage.fromBitmap(bitmap)

            val outputs = model.process(image).probabilityAsCategoryList.apply {
                sortByDescending {
                    it.score
                }
            }
            val probabilityOP = outputs[0]
            binding.naam.text = probabilityOP.label

            if (probabilityOP.label == "Cheese sandwich") {
                binding.avoidTV.text =
                    "Suffering from phenylketonuria, gluten sensitivity or Lactose Intolerance\n"
                binding.diseaseTV.text =
                    "Celiac disease(by wheat)\n, itching, hives(in mild cases) or anaphylaxis(severe)"
                binding.allergenTV.text = "Casein (cheese)\n Gluten (bread) \n Glycinin(Soy)"
            }
            else if (probabilityOP.label == "Club sandwich") {
            binding.avoidTV.text =
                "Suffering from phenylketonuria, gluten sensitivity or Egg allergies\n"
            binding.diseaseTV.text =
                "Celiac disease(by wheat)\n, itching, hives(in mild cases) or anaphylaxis(severe)"
            binding.allergenTV.text = "Gluten (bread) \n Glycinin(Soy)\n Egg(Myonnaise)"
        } else if (probabilityOP.label == "Samosa") {
                binding.avoidTV.text =
                    "Suffering from Irritable Bowel Syndrome,Gallbladder Issues,Pancreatitis,Gastroesophageal Reflux Disease , Diabetes, Hypertension, Cardiovascular Disease"
                binding.diseaseTV.text =
                    "Celiac disease(by wheat)\nskin reactions (hives, itching), gastrointestinal symptoms (nausea, vomiting, diarrhea), respiratory issues (coughing, wheezing), and in severe cases, anaphylaxis.\n Oral Allergy Syndrome (potatoes)"
                binding.allergenTV.text =
                    "Gluten(shell),\n Glycinin( soyabean  oil),\n vicilins, legumins, albumins and profilins(tree nuts)"

            } else if (probabilityOP.label == "Hamit burger") {
                binding.avoidTV.text =
                    "Suffering from Angiodema,Red meat allergen , Diabetes, Hypertension, Cardiovascular Disease, Constipation"
                binding.diseaseTV.text =
                    "skin reactions (hives, itching),\ngastrointestinal symptoms (nausea, vomiting, diarrhea)\n,Celiac disease(by wheat)\n and in severe cases, anaphylaxis.\n Oral Allergy Syndrome (potatoes)"
                binding.allergenTV.text =
                    "Gluten(shell),\n Glycinin( soyabean  oil),\n vicilins, legumins, albumins and profilins(tree nuts)"

            } else if (probabilityOP.label == "Gulab jamun") {
                binding.avoidTV.text =
                    "Suffering from Allergic from tree nuts,Gallbladder Issues,Pancreatitis,Gastroesophageal Reflux Disease , Diabetes, Hypertension, Irritable Bowel Syndrome"
                binding.diseaseTV.text =
                    "skin reactions (hives, itching),Hormonal (imbalance), and in severe cases, anaphylaxis.\n, gastrointestinal symptoms (nausea, vomiting, diarrhea)"
                binding.allergenTV.text =
                    "Gluten(Maida/wheat),\n Glycinin( soyabean  oil),\n  Albumins and profilins(tree nuts)\n vicilins, legumins,"

            } else if (probabilityOP.label == "Jalebi") {
                binding.avoidTV.text =
                    "Suffering Hepatities B,Pancreatitis,Gastroesophageal Reflux Disease , Diabetes, Hypertension, Cardiovascular Disease, Gallbladder Issues,"
                binding.diseaseTV.text =
                    "Celiac disease(by wheat)\nskin reactions (hives, itching), gastrointestinal symptoms (nausea, vomiting, diarrhea), respiratory issues (coughing, wheezing), and in severe cases, anaphylaxis.\n Oral Allergy Syndrome (potatoes)"
                binding.allergenTV.text =
                    "Gluten(shell),\n Glycinin( soyabean  oil),\n vicilins, legumins, albumins and profilins(tree nuts)"

            } else if (probabilityOP.label == "Hot dog") {
                binding.avoidTV.text =
                    "Suffering from Pancreatitis, Gastroesophageal Reflux Disease,Constipation,Gallbladder Issues, Hypertension, Lathargy"
                binding.diseaseTV.text =
                    "Celiac disease(by wheat)\n gastrointestinal symptoms (nausea, vomiting, diarrhea),skin reactions (hives, itching), and in severe cases, anaphylaxis.\n Oral Allergy Syndrome (potatoes)"
                binding.allergenTV.text =
                    "Gluten(Bread),\n Glycinin(soy),\n vicilins, legumins, Red meat"

            } else {
                Toast.makeText(this, "no information for this item", Toast.LENGTH_SHORT).show()
            }
            model.close()

        }

    }


    @RequiresApi(Build.VERSION_CODES.M)
    private fun getPerm() {
        if (checkSelfPermission(android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(android.Manifest.permission.CAMERA),
                11
            )
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