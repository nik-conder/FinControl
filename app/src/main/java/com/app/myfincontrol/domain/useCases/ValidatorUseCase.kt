package com.app.myfincontrol.domain.useCases

import com.app.myfincontrol.data.Configuration
import com.app.myfincontrol.data.ErrorCode
import javax.inject.Inject
import javax.inject.Singleton

sealed class ValidationResult {
    data class Success(val code: Int, val message: String) : ValidationResult()
    data class Error(val code: ErrorCode, val message: String) : ValidationResult()
}

@Singleton
class ValidatorUseCase @Inject constructor() {
    fun validation(profile: com.app.myfincontrol.data.entities.Profile): ValidationResult {
        if (profile.name.isEmpty()) {
            return ValidationResult.Error(
                ErrorCode.EMPTY_PROFILE_NAME,
                "Название пустое"
            )
        } else if (profile.name.length > Configuration.Limits.LIMIT_CHARS_NAME_PROFILE) {
            return ValidationResult.Error(
                ErrorCode.LONG_PROFILE_NAME,
                "Название слишком длинное"
            )
        } else {
            return ValidationResult.Success(0, "Ok")
        }
    }
}