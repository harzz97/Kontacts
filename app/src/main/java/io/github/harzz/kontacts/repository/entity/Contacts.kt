package io.github.harzz.kontacts.repository.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "contacts_table")
data class Contacts (
    var owner : String,
    var user_name : String,
    var user_phone_number: String
){
    @PrimaryKey(autoGenerate = true)
    var id:Int = 0
}
