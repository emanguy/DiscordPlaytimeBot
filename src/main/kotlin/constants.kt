package org.erittenhouse.hotmessdetector

object DiscordIntent {
    const val GUILDS = 1 shl 0
    const val GUILD_MEMBERS = 1 shl 1
    const val GUILD_BANS = 1 shl 2
    const val GUILD_EMOJIS = 1 shl 3
    const val GUILD_INTEGRATIONS = 1 shl 4
    const val GUILD_WEBHOOKS = 1 shl 5
    const val GUILD_INVITES = 1 shl 6
    const val GUILD_VOICE_STATES = 1 shl 7
    const val GUILD_PRESENCES = 1 shl 8
    const val GUILD_MESSAGES = 1 shl 9
    const val GUILD_MESSAGE_REACTIONS = 1 shl 10
    const val GUILD_MESSAGE_TYPING = 1 shl 11
    const val DIRECT_MESSAGES = 1 shl 12
    const val DIRECT_MESSAGE_REACTIONS = 1 shl 13
    const val DIRECT_MESSAGE_TYPING = 1 shl 14
}

object DiscordOpcode {
    const val DISPATCH = 0
    const val HEARTBEAT = 1
    const val IDENTIFY = 2
    const val PRESENCE_UPDATE = 3
    const val VOICE_STATE_UPDATE = 4
    const val RESUME = 6
    const val RECONNECT = 7
    const val REQUEST_GUILD_MEMBERS = 8
    const val INVALID_SESSION = 9
    const val HELLO = 10
    const val HEARTBEAT_ACK = 11
}

object DiscordEvent {
    object Send {
        const val IDENTIFY = "IDENTIFY"
        const val RESUME = "RESUME"
        const val HEARTBEAT = "HEARTBEAT"
        const val REQUEST_GUILD_MEMBERS = "REQUEST_GUILD_MEMBERS"
        const val UPDATE_VOICE_STATE = "UPDATE_VOICE_STATE"
        const val UPDATE_STATUS = "UPDATE_STATUS"
    }
    object Receive {
        const val HELLO = "HELLO"
        const val READY = "READY"
        const val RESUMED = "RESUMED"
        const val RECONNECT = "RECONNECT"
        const val INVALID_SESSION = "INVALID_SESSION"
        const val CHANNEL_CREATE = "CHANNEL_CREATE"
        const val CHANNEL_UPDATE = "CHANNEL_UPDATE"
        const val CHANNEL_DELETE = "CHANNEL_DELETE"
        const val CHANNEL_PINS_UPDATE = "CHANNEL_PINS_UPDATE"
        const val GUILD_CREATE = "GUILD_CREATE"
        const val GUILD_UPDATE = "GUILD_UPDATE"
        const val GUILD_DELETE = "GUILD_DELETE"
        const val GUILD_BAN_ADD = "GUILD_BAN_ADD"
        const val GUILD_BAN_REMOVE = "GUILD_BAN_REMOVE"
        const val GUILD_EMOJIS_UPDATE = "GUILD_EMOJIS_UPDATE"
        const val GUILD_INTEGRATIONS_UPDATE = "GUILD_INTEGRATIONS_UPDATE"
        const val GUILD_MEMBER_ADD = "GUILD_MEMBER_ADD"
        const val GUILD_MEMBER_REMOVE = "GUILD_MEMBER_REMOVE"
        const val GUILD_MEMBER_UPDATE = "GUILD_MEMBER_UPDATE"
        const val GUILD_MEMBERS_CHUNK = "GUILD_MEMBERS_CHUNK"
        const val GUILD_ROLE_CREATE = "GUILD_ROLE_CREATE"
        const val GUILD_ROLE_UPDATE = "GUILD_ROLE_UPDATE"
        const val GUILD_ROLE_DELETE = "GUILD_ROLE_DELETE"
        const val INVITE_CREATE = "INVITE_CREATE"
        const val INVITE_DELETE = "INVITE_DELETE"
        const val MESSAGE_CREATE = "MESSAGE_CREATE"
        const val MESSAGE_UPDATE = "MESSAGE_UPDATE"
        const val MESSAGE_DELETE = "MESSAGE_DELETE"
        const val MESSAGE_DELETE_BULK = "MESSAGE_DELETE_BULK"
        const val MESSAGE_REACTION_ADD = "MESSAGE_REACTION_ADD"
        const val MESSAGE_REACTION_REMOVE = "MESSAGE_REACTION_REMOVE"
        const val MESSAGE_REACTION_REMOVE_ALL = "MESSAGE_REACTION_REMOVE_ALL"
        const val MESSAGE_REACTION_REMOVE_EMOJI = "MESSAGE_REACTION_REMOVE_EMOJI"
        const val PRESENCE_UPDATE = "PRESENCE_UPDATE"
        const val TYPING_START = "TYPING_START"
        const val USER_UPDATE = "USER_UPDATE"
        const val VOICE_STATE_UPDATE = "VOICE_STATE_UPDATE"
        const val VOICE_SERVER_UPDATE = "VOICE_SERVER_UPDATE"
        const val WEBHOOKS_UPDATE = "WEBHOOKS_UPDATE"
    }
}

object DiscordActivity {
    object Flags {
        const val INSTANCE = 1
        const val JOIN = 1 shl 1
        const val SPECTATE = 1 shl 2
        const val JOIN_REQUEST = 1 shl 3
        const val SYNC = 1 shl 4
        const val PLAY = 1 shl 5
    }
    object Type {
        const val GAME = 0
        const val STREAMING = 1
        const val LISTENING = 2
        const val CUSTOM = 4
    }
}
