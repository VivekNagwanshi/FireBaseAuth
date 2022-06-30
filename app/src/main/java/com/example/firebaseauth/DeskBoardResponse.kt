package com.example.firebaseauth

import com.google.gson.annotations.SerializedName

data class DeskBoardResponse(

	@field:SerializedName("response")
	val response: Response? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: Boolean? = null
)

data class ItemDataItem(

	@field:SerializedName("flag")
	val flag: String? = null,

	@field:SerializedName("heading")
	val heading: String? = null,

	@field:SerializedName("item_for")
	val itemFor: String? = null,

	@field:SerializedName("externallink")
	val externallink: String? = null,

	@field:SerializedName("like_dislike_share")
	val likeDislikeShare: Any? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("userid")
	val userid: Any? = null,

	@field:SerializedName("media_file")
	val mediaFile: String? = null
)

data class Response(

	@field:SerializedName("title")
	val title: String? = null,

	@field:SerializedName("item_data")
	val itemData: List<ItemDataItem?>? = null
)
