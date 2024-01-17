package com.example.weather

 data class Model (
     var main: MainModel,
     var wind:MainWind,
     var name:String
 )

data class MainWind (
    var speed:Double,

)

data class MainModel(
    var temp:Double,
    var feels_like: Double,
)