package com.tamiang.smart.models

import com.google.gson.annotations.SerializedName

data class LoginRequest(
    @SerializedName("no_rkm_medik") val no_rkm_medik: String?,
    @SerializedName("nik") val nik: String?,
    @SerializedName("tgl_lahir") val tgl_lahir: String?
)
