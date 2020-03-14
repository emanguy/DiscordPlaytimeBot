package org.erittenhouse.hotmessdetector

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.*
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.features.json.JacksonSerializer
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.websocket.ClientWebSocketSession
import io.ktor.client.features.websocket.WebSockets
import io.ktor.client.features.websocket.webSocket
import io.ktor.http.cio.websocket.Frame
import io.ktor.http.cio.websocket.close
import io.ktor.http.cio.websocket.readText
import io.ktor.http.cio.websocket.send
import io.ktor.util.KtorExperimentalAPI
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.SendChannel
import kotlinx.coroutines.selects.select
import kotlin.coroutines.coroutineContext

@OptIn(KtorExperimentalAPI::class, ExperimentalCoroutinesApi::class)
fun main() {
    val objectSerializer = ObjectMapper().registerKotlinModule().apply {
        configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, false)
        configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
    }

    runBlocking {
        val client = HttpClient(CIO) {
            install(WebSockets)
            install(JsonFeature) {
                serializer = JacksonSerializer()
            }
        }

        client.webSocket("wss://gateway.discord.gg/?v=6&encoding=json") {
            val heartbeatRequestStr = incoming.receive()
            val heartbeatRequest = if (heartbeatRequestStr is Frame.Text) {
                objectSerializer.readValue<DiscordPayload<HeartbeatMessage>>(heartbeatRequestStr.readText())
            } else {
                close()
                throw Exception("Got a non-text initial frame!")
            }

            coroutineScope {
                val sequenceChannel = Channel<Int>()

                launch {
                    doHeartbeat(this@webSocket, sequenceChannel, heartbeatRequest.sequenceNum, heartbeatRequest.data.heartbeatInterval.toLong())
                }

                // TODO do opcode IDENTIFY and pass token to authenticate WebSocket session

                // TODO start printing messages to console to see what we're dealing with
            }
        }
    }
}

@OptIn(ExperimentalCoroutinesApi::class)
@Suppress("BlockingMethodInNonBlockingContext")
suspend fun doHeartbeat(webSocketSession: ClientWebSocketSession, sequenceChannel: ReceiveChannel<Int>, initialSequenceNum: Int?, heartbeatInterval: Long) {
    val objectSerializer = ObjectMapper().registerKotlinModule()
    var lastSequenceNum = initialSequenceNum
    var lastTimeSent = System.currentTimeMillis() - heartbeatInterval - 100

    while (coroutineContext.isActive) {
        val remainingWaitTime = (heartbeatInterval.toLong() - (System.currentTimeMillis() - lastTimeSent))
            .coerceAtLeast(0)
        val shouldSkip = select<Boolean> {
            sequenceChannel.onReceive { seqNum ->
                lastSequenceNum = seqNum
                true
            }
            onTimeout(remainingWaitTime) { false }
        }
        if (shouldSkip) continue

        val nextHeartbeat = DiscordPayload(DiscordOpcode.HEARTBEAT, lastSequenceNum)
        webSocketSession.send(objectSerializer.writeValueAsString(nextHeartbeat))
        lastTimeSent = System.currentTimeMillis()
    }
}

suspend fun printMessagesToConsole(webSocketSession: ClientWebSocketSession, sequenceChannel: SendChannel<Int>) {
}