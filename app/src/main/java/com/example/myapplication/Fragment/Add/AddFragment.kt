package com.example.myapplication.Fragment.Add

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.myapplication.model.Recipe
import com.example.myapplication.viewmodel.RecipeViewModel
import com.example.myapplication.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class AddFragment : Fragment() {

    private lateinit var RViewModel: RecipeViewModel
    private var currentImagePath: String? = null
    private lateinit var imageButton: ImageButton
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_add, container, false)

        RViewModel = ViewModelProvider(this).get(RecipeViewModel::class.java)
        view.findViewById<Button>(R.id.buttonadd).setOnClickListener {
            insertDataToDataBase()
        }
        imageButton = view.findViewById<ImageButton>(R.id.imgbtn)
        imageButton.setOnClickListener {
            ImageSelection()
        }
        return view
    }

    private fun ImageSelection() {
        if (currentImagePath != null) {
            replaceCurrentImage(currentImagePath)
        }
        openGalleryForImage()
    }
    private fun replaceCurrentImage(imagePath: String?) {
        lifecycleScope.launch(Dispatchers.IO) {
            deleteImage(currentImagePath)
        }
    }
    private fun deleteImage(imagePath: String?) {
        imagePath?.takeIf { it.isNotBlank() }?.let { nonNullOrBlankImagePath ->
            val imageFile = File(nonNullOrBlankImagePath)
            if (imageFile.exists()) {
                imageFile.delete()
            }
        }
    }
    private fun openGalleryForImage() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        resultLauncher.launch(intent)
    }

    private val resultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                result.data?.data?.let { uri ->
                    try {
                        currentImagePath = saveImageToDatabase(uri)
                        currentImagePath?.let { path ->
                            setImageToImageButton(path)
                        }
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                }
            }
        }
    private fun setImageToImageButton(imagePath: String) {
        val imageFile = File(imagePath)
        if (imageFile.exists()) {
            val imageBitmap = BitmapFactory.decodeFile(imagePath)
            imageButton.setImageBitmap(imageBitmap)
        } else {
            Toast.makeText(requireContext(),"не удалось получить изображение!",Toast.LENGTH_LONG ).show()
        }
    }
    @Throws(IOException::class)
    private fun saveImageToDatabase(uri: Uri): String {
        val inputStream = requireActivity().contentResolver.openInputStream(uri)
        return inputStream?.use { stream ->
            val selectedImageBitmap = BitmapFactory.decodeStream(stream)
            // Масштабирование изображения до 215x215 пикселей
            val scaledBitmap = Bitmap.createScaledBitmap(selectedImageBitmap, 450, 450, false)
            val storageDir = File(
                requireActivity().getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS),
                "Wardrobe app"
            )
            if (!storageDir.exists()) {
                storageDir.mkdirs()
            }
            val imageFile = File.createTempFile("IMG_", ".png", storageDir)
            val outputStream = FileOutputStream(imageFile)
            scaledBitmap.compress(Bitmap.CompressFormat.PNG, 80, outputStream)
            outputStream.close()
            imageFile.absolutePath
        } ?: throw IOException("Input stream is null")
    }
    private fun insertDataToDataBase() {
        val Name = view?.findViewById<EditText>(R.id.txtname)?.text.toString()
        val Discription = view?.findViewById<EditText>(R.id.txtdiscription)?.text.toString()
        if(inputCheck(Name,Discription)){
            val recipe = Recipe(0, Name,currentImagePath , Discription)
            RViewModel.addRecipe(recipe)
            Toast.makeText(requireContext(),"Добавление прошло успешно",Toast.LENGTH_LONG ).show()
        findNavController().navigate(R.id.action_addFragment_to_listFragment)
        }else{
            Toast.makeText(requireContext(),"Please fill out all fields.", Toast.LENGTH_LONG).show()
        }
    }

    private fun inputCheck(name: String, discription: String): Boolean {
        return !(TextUtils.isEmpty(name) &&  TextUtils.isEmpty(discription))
    }
}