package com.srbh.wander.repo

import com.srbh.wander.dao.NoteDao
import com.srbh.wander.model.Note

class NoteRepo(private val dao: NoteDao) {

    suspend fun getAllNote(currentUserEmail: String) =dao.getAllNotes(currentUserEmail)

    suspend fun add(note: Note){
        dao.insertNote(note)
    }

    suspend fun update(note: Note){
        dao.updateNote(note)
    }

    suspend fun delete(note: Note){
        dao.deleteNote(note)
    }

    suspend fun deleteAllNote(currentUserEmail: String){
        dao.deleteAll(currentUserEmail)
    }

}