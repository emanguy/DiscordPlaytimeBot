package org.erittenhouse.hotmessdetector

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty

data class HeartbeatMessage(
    @JsonProperty("heartbeat_interval")
    val heartbeatInterval: Int
)

data class DiscordUser(
    val id: String,
    val username: String,
    val discriminator: Int
)

@JsonInclude(JsonInclude.Include.NON_NULL)
data class DiscordUserActivity(
    val name: String,
    val type: Int,
    val details: String? = null,
    @JsonProperty("created_at")
    val createdAt: Long
)

data class UnavailableGuild(
    val id: String,
    val unavailable: Boolean
)

data class ReadyMessage(
    @JsonProperty("v")
    val version: Int,
    val user: DiscordUser,
    val guilds: List<UnavailableGuild>
)

data class IdentifyConnectionProperties(
    @JsonProperty("\$os")
    val operatingSystem: String,
    @JsonProperty("\$browser")
    val browser: String,
    @JsonProperty("\$device")
    val device: String
)

data class UserPresence(
    val user: DiscordUser,
    @JsonProperty("game")
    val activity: DiscordUserActivity?,
    val status: DiscordStatus,
    @JsonProperty("guild_id")
    val guildID: Long
)

data class PresenceUpdate(
    val status: DiscordStatus,
    // Only fill if you set status to Idle
    val since: Long? = null
)

@JsonInclude(JsonInclude.Include.NON_NULL)
data class IdentifyMessage(
    val token: String,
    val properties: IdentifyConnectionProperties,
    val intents: Int? = null,
    val presence: PresenceUpdate? = null
)

data class Guild(
    @JsonProperty("id")
    val ID: Long,
    val name: String
)

@JsonInclude(JsonInclude.Include.NON_NULL)
data class DiscordPayloadMetadata(
    @JsonProperty("op")
    val opcode: Int,
    @JsonProperty("s")
    val sequenceNum: Int?,
    @JsonProperty("t")
    val eventName: String?
)

@JsonInclude(JsonInclude.Include.NON_NULL)
data class DiscordPayload<T>(
    @JsonProperty("op")
    val opcode: Int,
    @JsonProperty("d")
    val data: T,
    @JsonProperty("s")
    val sequenceNum: Int? = null,
    @JsonProperty("t")
    val eventName: String? = null
)
