package com.architecture.repository.data.file

import com.architecture.domain.file.UploadFile
import com.architecture.remote.api.file.UploadFileModel
import com.architecture.repository.ModelMapper
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UploadFileModelMapper @Inject constructor() : ModelMapper<UploadFileModel, UploadFile> {
    override fun toData(model: UploadFileModel): UploadFile {
        return UploadFile(
            path = model.path
        )
    }
}