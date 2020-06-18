package pl.mrz.umpa.service

import com.github.kittinunf.fuel.core.deserializers.StringDeserializer
import com.github.kittinunf.fuel.core.FuelManager
import com.github.kittinunf.fuel.rx.rxResponseObject
import io.reactivex.Single
import pl.mrz.umpa.model.Rain
import pl.mrz.umpa.model.StationConfig
import pl.mrz.umpa.util.JsonMapper
import java.security.cert.X509Certificate
import javax.net.ssl.HostnameVerifier
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

object ApiService {

    private const val HOST = "http://192.168.137.5"
    private const val CONFIG_ENDPOINT = "$HOST/configuration"
    private const val RAIN_ENDPOINT = "$HOST/rain"

    private val http: FuelManager = buildHttpsFuelManager()
    private val stationConfigJsonMapper = JsonMapper(StationConfig::class.java)
    private val rainJsonMapper = JsonMapper(Rain::class.java)

    fun getConfiguration(): Single<StationConfig> {
        return http
            .get(CONFIG_ENDPOINT)
            .rxResponseObject(stationConfigJsonMapper)
    }

    fun getRain(): Single<Rain> {
        return http
            .get(RAIN_ENDPOINT)
            .rxResponseObject(rainJsonMapper)
    }

    fun updateConfiguration(config: StationConfig): Single<Any> {
        return http
            .put(CONFIG_ENDPOINT)
            .body(stationConfigJsonMapper.serialize(config))
            .rxResponseObject(StringDeserializer())
    }

    private fun buildHttpsFuelManager(): FuelManager {
        return FuelManager().apply {
            val trustAllCerts = arrayOf<TrustManager>(object : X509TrustManager {
                override fun getAcceptedIssuers(): Array<X509Certificate>? = null
                override fun checkClientTrusted(chain: Array<X509Certificate>, authType: String) =
                    Unit

                override fun checkServerTrusted(chain: Array<X509Certificate>, authType: String) =
                    Unit
            })

            socketFactory = SSLContext.getInstance("SSL").apply {
                init(null, trustAllCerts, java.security.SecureRandom())
            }.socketFactory

            hostnameVerifier = HostnameVerifier { _, _ -> true }
        }
    }

}