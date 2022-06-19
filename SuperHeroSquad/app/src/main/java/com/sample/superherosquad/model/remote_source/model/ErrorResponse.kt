package com.sample.superherosquad.model.remote_source.model

import com.google.gson.annotations.SerializedName

data class ErrorResponse(

	@field:SerializedName("code")
	val code: Int? = null,

	@field:SerializedName("status")
	val status: String? = null
)
