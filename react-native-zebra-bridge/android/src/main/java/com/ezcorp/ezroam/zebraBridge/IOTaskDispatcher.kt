package com.ezcorp.ezroam.zebraBridge

import android.util.Log
import com.ezcorp.ezroam.zebraBridge.di.ApplicationSingleton
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Runnable
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking


//Executes the task on an IO thread, to avoid blocking the react native thread
fun dispatchNewTask(runnable: Runnable) = runBlocking(Dispatchers.IO) {
    val job = launch {
        runnable.run()
    }
    ApplicationSingleton.jobs.add(job)
}
