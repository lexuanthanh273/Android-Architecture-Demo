package com.architecture.repository.data.file

import com.architecture.domain.UiResource
import com.architecture.domain.file.FileRepository
import com.architecture.domain.file.UploadFile
import com.architecture.remote.api.file.FileService
import com.architecture.repository.executor.Executor
import com.architecture.repository.util.toUiResource
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import javax.inject.Inject

class FileRepositoryImpl @Inject constructor(
    private val fileService: FileService,
    private val uploadFileModelMapper: UploadFileModelMapper,
    private val executor: Executor
) : FileRepository {
    override suspend fun uploadFile(file: File): UiResource<UploadFile> = withContext(executor.io()) {
        val fileToUpload = MultipartBody.Part.createFormData(
            name = "file",
            filename = file.name,
            body = file.asRequestBody("image/*".toMediaTypeOrNull())
        )
        fileService.uploadFile(fileToUpload).toUiResource {
            uploadFileModelMapper.toData(it)
        }
    }
}