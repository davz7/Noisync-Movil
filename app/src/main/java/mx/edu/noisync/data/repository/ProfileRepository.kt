package mx.edu.noisync.data.repository

import mx.edu.noisync.data.model.ChangePasswordRequest
import mx.edu.noisync.data.model.ChangePasswordResponse
import mx.edu.noisync.data.model.UserProfile

interface ProfileRepository {
    suspend fun getProfile(): RepositoryResult<UserProfile>
    suspend fun changePassword(request: ChangePasswordRequest): RepositoryResult<ChangePasswordResponse>
}
