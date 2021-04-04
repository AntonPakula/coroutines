package com.apakula.coroutines.repository

import androidx.lifecycle.LiveData
import com.apakula.coroutines.api.Builder
import com.apakula.coroutines.model.User
import kotlinx.coroutines.*

object Repository {

    var job: CompletableJob? = null

    fun getUser(userId: String): LiveData<User> {

        job = Job()

        return object : LiveData<User>() {
            override fun onActive() {
                super.onActive()

                job?.let { fetchUserJob ->
                    CoroutineScope(Dispatchers.IO + fetchUserJob).launch {

                        try {
                            val user = Builder.userService.getUser(userId)

                            postValue(user)
                            fetchUserJob.complete()
                        } catch (e: Exception) {
                            // Probably a network issue, handle gracefully :)
                            postValue(e)
                            fetchUserJob.complete()
                        }
                    }
                }
            }

        }
    }

    fun cancel() {
        job?.cancel()
    }
}