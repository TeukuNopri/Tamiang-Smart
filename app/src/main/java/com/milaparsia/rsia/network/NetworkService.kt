package com.milaparsia.rsia.network

import com.milaparsia.rsia.models.*
import retrofit2.Call
import retrofit2.http.*


interface NetworkService {

    // clear
    @FormUrlEncoded
    @POST("cek_login")
    fun cekLogin(
        @Field("no_rkm_medik") no_rkm_medik: String?,
        @Field("nik") nik: String?,
        @Field("tgl_lahir") tgl_lahir: String?,
        @Field("token") token: String?,
        @Field("device") device: String?
    ):Call<LoginResponse>

    //clear
    @FormUrlEncoded
    @POST("referensi_poliklinik")
    fun getReferensiPoli(
        @Field("tgl_rencana_berobat") tgl_rencana_berobat: String?
    ):Call<ReferensiPoliResponse>

    //clear
    @FormUrlEncoded
    @POST("referensi_dokter")
    fun getReferensiDokter(
        @Field("tgl_rencana_berobat") tgl_rencana_berobat: String?,
        @Field("kd_poli") kd_poli: String?
    ):Call<ReferensiDokterResponse>

    //clear
    @FormUrlEncoded
    @POST("ambulance")
    fun sendLocation(
        @Field("no_rkm_medik") no_rkm_medik: String?,
        @Field("longitude") longitude: String?,
        @Field("latitude") latitude: String?,
        @Field("keluhan") keluhan: String?
    ):Call<AmbulanceResponse>

    // clear
    @FormUrlEncoded
    @POST("daftar_berobat")
    fun saveDaftarBerobat(
        @Field("no_rkm_medis") no_rkm_medis: String?,
        @Field("tgl_lahir") tgl_lahir: String?,
        @Field("tgl_rencana_berobat") tgl_rencana_berobat: String?,
        @Field("kd_poli") kd_poli: String?,
        @Field("referensi_jns_bayar") referensi_jns_bayar: String?,
        @Field("kd_dokter") kd_dokter: String?,
        @Field("asalrujukan") asalrujukan: String?,
    ):Call<DaftarBerobatResponse>

    //clear
    @GET("informasi_kamar")
    fun getInfoKamar():Call<InfoKamarResponse>

    //clear
    @GET("cek_antrian_poli/{no_rkm_medis}")
    fun getCekAntrianPoli(
        @Path("no_rkm_medis") no_rkm_medis:String?
    ):Call<AntrianPoliResponse>

    //clear
    @GET("cek_antrian_resep/{no_rkm_medis}")
    fun getAntrianResep(
        @Path("no_rkm_medis") no_rkm_medis:String?
    ):Call<AntrianResepResponse>

}