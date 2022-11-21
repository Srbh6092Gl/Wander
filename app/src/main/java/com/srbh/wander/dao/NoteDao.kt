package com.srbh.wander.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.srbh.wander.model.Note
import java.nio.charset.CodingErrorAction.REPLACE

@Dao
interface NoteDao {

    @Insert
    suspend fun insertNote(note: Note)

    @Update
    suspend fun updateNote(note: Note)

    @Delete
    suspend fun deleteNote(note: Note)

    @Query("DELETE FROM note WHERE sender= :currentUserEmail")
    suspend fun deleteAll(currentUserEmail: String)

    @Query("SELECT * FROM note WHERE sender= :currentUserEmail")
    fun getAllNotes(currentUserEmail: String): LiveData<List<Note>>

}