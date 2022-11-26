package com.srbh.wander.contentprovider

import android.content.*
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import android.database.sqlite.SQLiteQueryBuilder
import android.net.Uri
import android.os.Build
import androidx.room.Room
import com.srbh.wander.dao.NoteDao
import com.srbh.wander.database.NoteDB
import com.srbh.wander.model.Note
import com.srbh.wander.service.NoteTableService
import kotlinx.coroutines.CoroutineStart

class NoteContentProvider: ContentProvider() {

    private var noteDB: SQLiteDatabase? = null

    private class DatabaseHelper
    internal constructor(context: Context?): SQLiteOpenHelper(
        context,
        DATABASE_NAME,
        null,
        DATABASE_VERSION
    ) {
        override fun onCreate(db: SQLiteDatabase?) {
            db!!.execSQL(CREATE_DB_TABLE)
        }

        override fun onUpgrade(db: SQLiteDatabase?, oldVer: Int, newVer: Int) {
            db!!.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
            onCreate(db)
        }

    }

    companion object{
        const val PROVIDER_NAME = "com.srbh.note.provider"
        const val URL = "content://$PROVIDER_NAME/note"

        val CONTENT_URI = Uri.parse(URL)
        const val id = "id"
        const val name = "name"
        const val URI_CODE_ALL = 1
        const val URI_CODE_ROW = 2
        var uriMatcher: UriMatcher? =null
        private val values: HashMap<String, String>? =null

        const val DATABASE_NAME = "note_db"
        const val TABLE_NAME = "note"
        const val DATABASE_VERSION = 1
        const val CREATE_DB_TABLE = ("CREATE TABLE $TABLE_NAME (id INTEGER PRIMARY KEY AUTOINCREMENT, sender TEXT, topic TEXT, description TEXT);")
        init {
            uriMatcher = UriMatcher(UriMatcher.NO_MATCH).apply {
                addURI(
                    PROVIDER_NAME,
                    "note",
                    URI_CODE_ALL
                )

                addURI(
                    PROVIDER_NAME,
                    "note/*",
                    URI_CODE_ROW
                )
            }
        }

    }

    override fun onCreate(): Boolean {
        val context = context
        val dbHelper = DatabaseHelper(context)
        noteDB = dbHelper.writableDatabase
        return noteDB != null
    }

    override fun query(
        uri: Uri,
        projection: Array<out String>?,
        selection: String?,
        selectionArgs: Array<out String>?,
        sortOrder: String?
    ): Cursor? {
        sendDbUseNotification()
        var sortBy =sortOrder
        val queryBuilder = SQLiteQueryBuilder()
        queryBuilder.tables = TABLE_NAME
        when(uriMatcher!!.match(uri)) {
            URI_CODE_ALL -> queryBuilder.projectionMap = values
            else -> throw IllegalArgumentException("Unknown URI $uri")
        }
        if( sortBy==null || sortBy=="" )
            sortBy = id
        val cursor = queryBuilder.query(noteDB, projection, selection, selectionArgs, null, null, sortBy)
//        val cursor: Cursor = dao!!.getNotes()
        cursor.setNotificationUri(context!!.contentResolver,uri)
        return cursor
    }

    override fun getType(uri: Uri): String? = when(uriMatcher!!.match(uri)) {
            URI_CODE_ALL -> "vnd.android.cursor.dir/note"
            URI_CODE_ROW -> "vnd.android.cursor.item/note"
            else -> throw IllegalArgumentException("Unsupported URI: $uri")
        }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        sendDbUseNotification()
        val rowId = noteDB!!.insert(TABLE_NAME,"", values)
        if(rowId > 0){
            val _uri = ContentUris.withAppendedId(CONTENT_URI,rowId)
            context!!.contentResolver.notifyChange(_uri,null)
            return _uri
        }
        throw SQLiteException("Failed to add a record into $uri")
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int {
        sendDbUseNotification()
        var count = 0
        count = when(uriMatcher!!.match(uri)) {
            URI_CODE_ALL -> noteDB!!.delete(TABLE_NAME,selection,selectionArgs)
            else -> throw IllegalArgumentException("Unknown URI: $uri")
        }
        context!!.contentResolver.notifyChange(uri,null)
        return count
    }

    override fun update(uri: Uri, values: ContentValues?, selection: String?, selectionArgs: Array<out String>?): Int {
        sendDbUseNotification()
        var count = 0
        count = when(uriMatcher!!.match(uri)) {
            URI_CODE_ALL -> noteDB!!.update(TABLE_NAME,values,selection,selectionArgs)
            else -> throw IllegalArgumentException("Unknown URI: $uri")
        }
        context!!.contentResolver.notifyChange(uri,null)
        return count
    }

    fun sendDbUseNotification() {
        Intent(context!!,NoteTableService::class.java).also {
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
                context!!.startForegroundService(it)
            else
                context!!.startService(it)
        }

    }
}