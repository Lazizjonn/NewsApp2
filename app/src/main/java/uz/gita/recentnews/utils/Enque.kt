package uz.gita.mynewsapp.utils

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


fun <T> Call<T>.myEnqueue(onSuccess: (response: Response<T>) -> Unit, onFailure: (t: Throwable) -> Unit) {
    this.enqueue(object : Callback<T> {
        override fun onResponse(call: Call<T>, response: Response<T>) {
            onSuccess.invoke(response)
        }

        override fun onFailure(call: Call<T>, t: Throwable) {
            onFailure.invoke(t)
        }
    })
}