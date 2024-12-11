package ie.cillian.tushangout.database

import android.app.Application
import androidx.lifecycle.LiveData

class TUSHangoutRepository (application: Application){

    private val newsDao = TUSHangoutDatabase.getDatabase(application)!!.newsDao()
    private val recipeDao = TUSHangoutDatabase.getDatabase(application)!!.recipeDao()

    fun fetchNewsItems(): LiveData<List<News>> {
        return newsDao.getAllNews()
    }

    fun fetchRecipes(): LiveData<List<Recipe>> {
        return recipeDao.getAllRecipes()
    }

    fun fetchBurgerPlaces(): LiveData<List<BurgerPlace>> {
        return recipeDao.getBurgerPlaces()
    }





}