package moe.dazecake.entity

import kotlinx.serialization.Serializable

@Serializable
data class Sender(
    var image: String,
    var to: Long,
    var info: String
)
