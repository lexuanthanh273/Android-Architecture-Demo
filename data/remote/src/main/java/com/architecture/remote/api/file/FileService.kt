package com.architecture.remote.api.file

import com.architecture.remote.ApiConstants
import com.architecture.remote.GenericApiResponse
import com.architecture.remote.GenericResponse
import okhttp3.MultipartBody
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface FileService {
    @Multipart
    @POST(ApiConstants.File.UPLOAD_FILE)
    suspend fun uploadFile(@Part file: MultipartBody.Part): GenericResponse<GenericApiResponse<UploadFileModel>>
}