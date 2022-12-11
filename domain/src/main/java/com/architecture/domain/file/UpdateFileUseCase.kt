package com.architecture.domain.file

import com.architecture.domain.UiResource
import java.io.File
import javax.inject.Inject

class UpdateFileUseCase @Inject constructor(
    private val fileRepository: FileRepository
) {
    suspend operator fun invoke(file: File): UiResource<UploadFile> {
        return fileRepository.uploadFile(file)
    }
}