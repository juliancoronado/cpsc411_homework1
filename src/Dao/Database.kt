package com.cpsc411homework1.Dao

import com.almworks.sqlite4java.SQLiteConnection
import java.io.File

class Database constructor(var dbName: String = "") {
    init {
        // TO DO -- manually set by user
        dbName = "C:\\Users\\Julian Coronado\\Desktop\\testDB.db"
        val dbConn = SQLiteConnection(File(dbName))
        dbConn.open()

        // SQL Lite statement
        val sqlStmt = "create table if not exists claim (id text, title text, date text, isSolved integer)"
        dbConn.exec(sqlStmt)
    }

    fun getDbConnection() : SQLiteConnection {
        val dbConn = SQLiteConnection(File(dbName))
        dbConn.open()
        return dbConn
    }

    companion object {
        private var dbObj : Database? = null

        fun getInstance() : Database? {
            if (dbObj == null) {
                dbObj = Database()
            }
            return dbObj
        }
    }
}