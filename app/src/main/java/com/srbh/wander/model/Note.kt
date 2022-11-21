package com.srbh.wander.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "note")
data class Note(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val sender: String,
    val topic: String,
    val description: String
)
