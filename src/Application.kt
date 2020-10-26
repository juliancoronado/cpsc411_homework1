// CPSC 411-01 Dr. Shen
// Julian Coronado
// Homework #1

package com.cpsc411homework1

import com.cpsc411homework1.Dao.Claim.Claim
import com.cpsc411homework1.Dao.Claim.ClaimDao
import com.google.gson.Gson
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.utils.io.*
import java.util.UUID.randomUUID

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads

fun Application.module() {
    routing {

        this.get("/ClaimService/getAll") {
            // HTTP Request
            // format: N/A
            // data: N/A

            // gets List of Claim objects
            val cList = ClaimDao().getAll()

            // outputs size of List
            println("Number of claims: ${cList.size}")

            // HTTP Response
            // format: plain text
            // data: same as the server we implemented

            // JSON Serialization (Claim -> JSON)
            val respJsonStr = Gson().toJson(cList)
            call.respondText(respJsonStr, status = HttpStatusCode.OK, contentType = ContentType.Application.Json)
        }

        this.post("/ClaimService/add") {
            // HTTP Request
            // format: JSON
            // data: claim object without ID and isSolved (encoded String)

            val data = call.request.receiveChannel()
            val dataLength = data.availableForRead
            val output = ByteArray(dataLength)
            data.readAvailable(output)
            val str = String(output)

            // TASK: convert STr into Claim object and add to local DB

            // creates Claim object
            val cObj : Claim
            // deserializes the JSON string to a Claim object
            cObj = Gson().fromJson(str, Claim::class.java)

            // sets Claim variables as needed, not grabbed from HTTP Request
            cObj.id = randomUUID()
            cObj.isSolved = false

            // adds Claim object to local Database
            val dao = ClaimDao().addClaim(cObj)

            // HTTP Response
            // format: plain text
            // data: same as the server we implemented -- response msg and success msg

            val response = String.format("Claim -- id: ${cObj.id}, title: ${cObj.title}, date: ${cObj.date} and isSolved: ${cObj.isSolved}")
            call.respondText("The POST request was processed successfully!\n${response}", status= HttpStatusCode.OK, contentType = ContentType.Text.Plain)

        }

    }
}

