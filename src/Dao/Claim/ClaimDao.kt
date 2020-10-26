package com.cpsc411homework1.Dao.Claim

import com.cpsc411homework1.Dao.Dao
import com.cpsc411homework1.Dao.Database
import java.util.*

class ClaimDao : Dao() {
    fun addClaim(cObj : Claim) {
        val conn = Database.getInstance()?.getDbConnection()

        // convert Boolean to Int
        val cBoolInt = if (cObj.isSolved!!) 1 else 0

        sqlStmt = "insert into claim (id, title, date, isSolved) values " +
                "('${cObj.id.toString()}', '${cObj.title}', '${cObj.date}', '${cBoolInt}')"

        conn?.exec(sqlStmt)
    }

    fun getAll() : List<Claim> {

        val conn = Database.getInstance()?.getDbConnection()

        sqlStmt = "select id, title, date, isSolved from claim"

        val claimList : MutableList<Claim> = mutableListOf()

        val st = conn?.prepare(sqlStmt)

        while (st!!.step()) {

            val id = st.columnString(0)
            val title = st.columnString(1)
            val date = st.columnString(2)
            val isSolved = st.columnInt(3)

            claimList.add(Claim(UUID.fromString(id), title, date, isSolved.toString().toBoolean()))
        }

        return claimList

    }

}