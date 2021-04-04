package com.apakula.coroutines.api

import com.apakula.coroutines.model.User
import retrofit2.http.GET
import retrofit2.http.Path

interface UserService {

    @GET("/user")
    suspend fun getUsers(): List<User>

    @GET("/user/{userId}")
    suspend fun getUser(
        @Path("userId") userId: String
    ): User

}