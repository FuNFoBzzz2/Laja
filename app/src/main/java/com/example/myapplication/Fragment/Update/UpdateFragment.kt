package com.example.myapplication.Fragment.Update

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.text.Editable
import android.text.SpannableStringBuilder
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.get
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.myapplication.R
import com.example.myapplication.model.Recipe
import com.example.myapplication.viewmodel.RecipeViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

@Suppress("DEPRECATION")
class UpdateFragment : Fragment() {
    private val args by navArgs<UpdateFragmentArgs>()
    private lateinit var RViewModel: RecipeViewModel
    private var currentImagePath: String? = null
    private lateinit var imageButton: ImageButton
    private lateinit var goback: ImageButton
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_update, container, false)
        RViewModel = ViewModelProvider(this).get(com.example.myapplication.viewmodel.RecipeViewModel::class.java)

        view.findViewById<EditText>(R.id.txtnameupdate).setText(args.currentRecipe.RecipeName)

        view.findViewById<EditText>(R.id.txtdiscriptionupdate).setText(args.currentRecipe.Discription)

        view.findViewById<ImageButton>(R.id.imgbtn).setImageBitmap(BitmapFactory.decodeFile(args.currentRecipe.Image))
        currentImagePath = args.currentRecipe.Image

        goback = view.findViewById<ImageButton>(R.id.goback)
        goback.setOnClickListener{findNavController().navigate(R.id.action_updateFragment_to_listFragment)}

        view.findViewById<Button>(R.id.buttondelete).setOnClickListener{
            deleteRecipe() }
        view.findViewById<Button>(R.id.buttonaddupdate).setOnClickListener{
            updateItem()
        }
        imageButton = view.findViewById<ImageButton>(R.id.imgbtn)
        imageButton.setOnClickListener {
            ImageSelection() }
        setHasOptionsMenu(true)
        return view
    }
    private fun ImageSelection() {
        if(currentImagePath == null) {
            replaceCurrentImage(currentImagePath)
        }
        openGalleryForImage()
    }
    private fun replaceCurrentImage(imagePath: String?) {
        lifecycleScope.launch(Dispatchers.IO) {
            val isImagePathUsed = RViewModel.isImage(currentImagePath)
            if (!isImagePathUsed) {
                deleteImage(currentImagePath)
            }
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

            val scaledBitmap = Bitmap.createScaledBitmap(selectedImageBitmap, 500, 500, false)
            val storageDir = File(
                requireActivity().getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS),
                "Wardrobe app"
            )
            if (!storageDir.exists()) {
                storageDir.mkdirs()
            }
            val imageFile = File.createTempFile("IMG_", ".jpeg", storageDir)
            val outputStream = FileOutputStream(imageFile)
            scaledBitmap.compress(Bitmap.CompressFormat.PNG, 80, outputStream)
            outputStream.close()
            imageFile.absolutePath
        } ?: throw IOException("Input stream is null")
    }
    private fun updateItem(){
        val RName = view?.findViewById<EditText>(R.id.txtnameupdate)?.text.toString()
        val RDiscription = view?.findViewById<EditText>(R.id.txtdiscriptionupdate)?.text.toString()
        if(inputCheck(RName, RDiscription)){
            val updateRecipe = Recipe(args.currentRecipe.IDRecipe, RName, currentImagePath, RDiscription)
            RViewModel.updateRecipe(updateRecipe)
            Toast.makeText(requireContext(), "Обновление прошло успешно!",Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_updateFragment_to_listFragment)
        }else{
            Toast.makeText(requireContext(), "Убедитесь что все поля заполнены!",Toast.LENGTH_SHORT).show()
        }
    }
    private fun inputCheck(RName: String, RDiscription: String): Boolean {
        var oo = false
        if (RName.isNotEmpty() &&  RDiscription.isNotEmpty()){
            oo = true
        }
        return oo
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.invalidateOptionsMenu()
    }
    private fun deleteRecipe() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setNegativeButton("ДА"){_, _ ->
            RViewModel.deleteRecipe(args.currentRecipe)
            Toast.makeText(requireContext(), "Удаление: ${args.currentRecipe.RecipeName}",Toast.LENGTH_SHORT ).show()
            findNavController().navigate(R.id.action_updateFragment_to_listFragment)
        }
        builder.setPositiveButton("Нет"){_, _ -> }
        builder.setTitle("Удалить ${args.currentRecipe.RecipeName}")
        builder.setMessage("Вы уверены что хотите удалить ${args.currentRecipe.RecipeName}")
        builder.create().show()
    }
}