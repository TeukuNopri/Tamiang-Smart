package com.tamiang.smart.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ReferensiPoli(
    @field:SerializedName("kd_poli") val kd_poli: String?= null,
    @field:SerializedName("nm_poli") val nm_poli: String?= null,
    @field:SerializedName("hari_kerja") val hari_kerja: String?= null,
    @field:SerializedName("jam_mulai") val jam_mulai: String?= null,
    @field:SerializedName("jam_selesai") val jam_selesai: String?= null,
    @field:SerializedName("kuota") val kuota: String?= null,
): Parcelable