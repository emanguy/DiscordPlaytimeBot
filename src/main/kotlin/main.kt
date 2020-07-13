package org.erittenhouse.hotmessdetector

import com.gitlab.kordlib.common.entity.Snowflake
import com.gitlab.kordlib.core.Kord
import com.gitlab.kordlib.core.event.PresenceUpdateEvent
import com.gitlab.kordlib.core.on
import com.gitlab.kordlib.gateway.Intent
import com.gitlab.kordlib.gateway.PrivilegedIntent
import io.github.cdimascio.dotenv.dotenv

@OptIn(PrivilegedIntent::class)
suspend fun main() {
    val environment = dotenv()
    val discordToken = environment["DP_TOKEN"] ?: run {
        println("No token found. Please set the environment variable DP_TOKEN.")
        return
    }
    val discordClient = Kord(discordToken) {
        intents {
            +Intent.GuildPresences
        }
    }

    discordClient.on<PresenceUpdateEvent> {
        val gameData = presence.data.game
        val userData = kord.getUser(Snowflake(user.id))

        println("Presence update. User ${userData?.username} has activity: $gameData")
    }

    discordClient.login()
}
