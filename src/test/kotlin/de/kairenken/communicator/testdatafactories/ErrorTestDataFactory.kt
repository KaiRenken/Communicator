package de.kairenken.communicator.testdatafactories

import de.kairenken.communicator.infrastructure.common.rest.ErrorResponseDto
import org.json.JSONObject

val ERROR_MSG = "test-error-msg"

fun aTestErrorResponseDto(
    msg: String = ERROR_MSG,
): ErrorResponseDto = ErrorResponseDto(
    msg = msg,
)

fun ErrorResponseDto.toJson(): String = JSONObject()
    .put("msg", this.msg)
    .toString()