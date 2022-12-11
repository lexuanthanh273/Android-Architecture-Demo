package com.architecture.domain.file

import com.architecture.domain.UiResource
import java.io.File

interface FileRepository {
    suspend fun uploadFile(file: File): UiResource<UploadFile>
}