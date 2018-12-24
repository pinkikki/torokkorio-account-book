package jp.pinkikki.account.book

import android.app.Application
import jp.pinkikki.account.book.http.AccountBookService
import okhttp3.OkHttpClient
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.androidModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class App : Application(), KodeinAware {
    override val kodein = Kodein.lazy {
        val apiHost = resources.getString(R.string.api_host)

        import(androidModule(this@App))

        bind<OkHttpClient>() with singleton { OkHttpClient.Builder().build() }

        bind<Retrofit>() with singleton {
            Retrofit.Builder()
                .client(instance())
                .baseUrl(apiHost)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
        }

        bind<AccountBookService>() with singleton { instance<Retrofit>().create(AccountBookService::class.java) }
    }
}