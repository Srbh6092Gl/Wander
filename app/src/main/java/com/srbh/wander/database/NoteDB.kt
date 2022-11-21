package com.srbh.wander.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.srbh.wander.dao.NoteDao
import com.srbh.wander.model.Note

@Database(entities = [Note::class], version = 1)
abstract class NoteDB: RoomDatabase() {

    abstract val noteDao: NoteDao

    companion object{
        @Volatile
        private var INSTANCE: NoteDB? = null

        fun getDB(context: Context): NoteDB {

            //Returning INSTANCE if it is not null
            //Returning INSTANCE by creating a synchronized function
            return INSTANCE?: synchronized(this){

                //Creating instance
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    NoteDB::class.java,
                    "note_db"
                ).build()

                //Copying instance into INSTANCE
                INSTANCE = instance

                //Returning instance
                instance
            }
        }
    }

}