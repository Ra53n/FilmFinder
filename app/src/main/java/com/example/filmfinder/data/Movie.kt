package com.example.filmfinder.data

import android.graphics.drawable.Drawable
import com.example.filmfinder.R
import javax.xml.transform.Source

data class Movie(
    val id: Int = 103786, val movieName: String = "Encanto",
    val movieDescription: String = "Удивительная семья Мадригаль живет " +
            "в спрятанном в горах Колумбии волшебном доме, расположенном " +
            "в чудесном и очаровательном уголке под названием Энканто. " +
            "Каждого ребёнка в семье Мадригаль магия этого места благословила " +
            "уникальным даром — от суперсилы до способности исцелять. " +
            "Увы, магия обошла стороной одну лишь юную Мирабель. " +
            "Обнаружив, что магия Энканто находится в опасности, " +
            "Мирабель решает, что именно она может быть последней надеждой на " +
            "спасение своей особенной семьи.",
    val movieYear: Int = 2021, val movieRating: Double = 7.7, val image : Int = R.mipmap.encanto_foreground
)