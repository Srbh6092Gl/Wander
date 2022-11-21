package com.srbh.wander.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "note")
data class Note(
    @PrimaryKey(autoGenerate = true)
    var id: Long,
    var sender: String,
    var topic: String,
    var description: String
)
