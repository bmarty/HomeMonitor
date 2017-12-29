package com.bmarty.homemonitor

import com.google.gson.annotations.SerializedName

data class Message(
        @SerializedName("fc") val fromClient: Boolean,
        @SerializedName("t") val type: String,
        @SerializedName("i") val info: String
)