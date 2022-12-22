package moe.dazecake.entity

import kotlinx.serialization.Serializable

@Serializable
data class Sender(
    val image: String,
    val to: Long,
    val info: String
)
