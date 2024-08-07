package com.example.appointment.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import com.example.appointment.R
import com.example.appointment.databinding.ActivityCategoryBinding
import com.example.appointment.Adapter.CategoryAdapter
import com.example.appointment.Models.CategoryModel

class Category : AppCompatActivity() {

    lateinit var binding: ActivityCategoryBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val arrCategory = ArrayList<CategoryModel>()

        arrCategory.add(CategoryModel(R.drawable.heart , "Cardiology"))
        arrCategory.add(CategoryModel(R.drawable.tooth , "Dentist"))
        arrCategory.add(CategoryModel(R.drawable.knee , "Orthopadic"))
        arrCategory.add(CategoryModel(R.drawable.eye , "ophthalmologist"))
        arrCategory.add(CategoryModel(R.drawable.lungs , "pulmonologist"))
        arrCategory.add(CategoryModel(R.drawable.neurology , "Neurology"))
        arrCategory.add(CategoryModel(R.drawable.ear , "Otology"))
        arrCategory.add(CategoryModel(R.drawable.stomach , "Gastroenterology"))
        arrCategory.add(CategoryModel(R.drawable.nose , "otolaryngologist"))
        arrCategory.add(CategoryModel(R.drawable.gynecology , "Gynecology"))
        arrCategory.add(CategoryModel(R.drawable.urologist , "urologist"))


        binding.categoryRecycler.layoutManager = GridLayoutManager(this , 3)

        val recyclerAdapter = CategoryAdapter(this , arrCategory)
        binding.categoryRecycler.adapter = recyclerAdapter

        binding.backImage.setOnClickListener {
//            val i = Intent(this , HomePage::class.java)
//            i.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
//            startActivity(i)
            finish()
        }

    }
}