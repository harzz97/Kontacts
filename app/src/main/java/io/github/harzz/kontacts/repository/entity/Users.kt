package io.github.harzz.kontacts.repository.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users_table")
data class Users(
    var userName : String,
    var userPassword : String
){

    @PrimaryKey(autoGenerate = true)
    var id : Int = 0
}