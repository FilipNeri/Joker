package co.tiagoaguiar.tutorial.jokerappdev.data

import android.os.Handler
import android.os.Looper
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CategoryRemoteDataSource {
    fun findAllCategories(callback: ListCategoryCallback){
        /*
        Handler(Looper.getMainLooper()).postDelayed({
            val response = arrayListOf(
                "Categoria 1",
                "Categoria 2",
                "Categoria 3",
                "Categoria 4"
            )
            callback.onSuccess(response)
            callback.onError("Falha na conexão")
            callback.onComplete()
        }, 2000)
         */
        HTTPClient.retrofit()
            .create(ChuckNorrisAPI::class.java)
            .findAllCategories()
            .enqueue(object : Callback<List<String>>{
                override fun onResponse(
                    call: Call<List<String>>,
                    response: Response<List<String>>
                ) {
                    if (response.isSuccessful){
                        val categories = response.body()
                        callback.onSuccess(categories?: emptyList())
                    }else{
                        val error = response.errorBody()?.toString()
                        callback.onError(error ?: "Erro interno")
                    }
                    callback.onComplete()
                }

                override fun onFailure(call: Call<List<String>>, t: Throwable) {
                    callback.onError(t.message?:"Erro interno")
                    callback.onComplete()
                }

            })
    }
}