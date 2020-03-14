package org.erittenhouse.hotmessdetector

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty

data class HeartbeatMessage(
    @JsonProperty("heartbeat_interval")
    val heartbeatInterval: Int
)

@JsonInclude(JsonInclude.Include.NON_NULL)
data class DiscordUser(
    val id: String,
    val username: String,
    val discriminator: Int,
    @JsonProperty("avatar")
    val avatarHash: String?,
    val bot: Boolean?,
    val system: Boolean?,
    val mfaEnabled: Boolean?,
    val locale: String?,
    val verified: Boolean?,
    val email: String?,
    val flags: Int?,
    val premiumType: Byte?
)

@JsonInclude(JsonInclude.Include.NON_NULL)
data class Timestamps(
    val start: Long? = null,
    val stop: Long? = null
)

@JsonInclude(JsonInclude.Include.NON_NULL)
data class Emoji(
    val name: String,
    val id: String? = null,
    val animated: Boolean? = null
)

@JsonInclude(JsonInclude.Include.NON_NULL)
data class Party(
    val id: String? = null,
    val size: List<Int>? = null
)

@JsonInclude(JsonInclude.Include.NON_NULL)
data class Assets(
    val largeImage: String? = null,
    val largeText: String? = null,
    val smallImage: String? = null,
    val smallText: String? = null
)

@JsonInclude(JsonInclude.Include.NON_NULL)
data class Secrets(
    val join: String? = null,
    val spectate: String? = null,
    val match: String? = null
)

@JsonInclude(JsonInclude.Include.NON_NULL)
data class DiscordUserActivity(
    val name: String,
    val type: Int,
    @JsonProperty("created_at")
    val createdAt: Long,
    val timestamps: Timestamps,
    @JsonProperty("application_id")
    val url: String? = null,
    @JsonProperty()
    val applicationID: String? = null,
    val details: String? = null,
    val state: String? = null,
    val emoji: Emoji? = null,
    val party: Party? = null,
    val assets: Assets? = null,
    val secrets: Secrets? = null,
    val instance: Boolean? = null,
    val flags: Int? = null
)

data class UnavailableGuild(
    val id: String,
    val unavailable: Boolean
)

data class ReadyMessage(
    @JsonProperty("v")
    val version: Int,
    val user: DiscordUser,
    @JsonProperty("private_channels")
    val privateChannels: List<String>,
    val guilds: List<UnavailableGuild>,
    @JsonProperty("session_id")
    val sessionID: String,
    val shard: List<Int>?
)

data class IdentifyConnectionProperties(
    @JsonProperty("\$os")
    val operatingSystem: String,
    @JsonProperty("\$browser")
    val browser: String,
    @JsonProperty("\$device")
    val device: String
)

data class ClientPresenceUpdate(
    val since: Long?,
    val activity: DiscordUserActivity?
    // More to come later...
)

data class IdentifyMessage(
    val token: String,
    val properties: IdentifyConnectionProperties,
    val compress: Boolean? = null,
    val largeThreshold: Int? = null,
    val shard: List<Int>? = null,

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
