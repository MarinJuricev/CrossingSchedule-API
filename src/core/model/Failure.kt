package com.example.core.model

data class Failure(
    val errorMessage: String
){
    companion object{
        val EMPTY = Failure("")
    }
}
