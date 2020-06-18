package pl.mrz.umpa.util
import com.fasterxml.jackson.databind.ObjectMapper
import com.github.kittinunf.fuel.core.Deserializable
import com.github.kittinunf.fuel.core.Response
import java.io.IOException

/**
 * Generic implementation of json object deserializer
 */
class JsonMapper<T : Any>(private val targetClass: Class<T>) : Deserializable<T> {

    private val mapper = ObjectMapper()

    override fun deserialize(response: Response): T {
        return try {
            mapper.readValue(response.data, targetClass)
        } catch (e: IOException) {
            throw RuntimeException(e.message, e.cause)
        }
    }

    fun serialize(value: T): String {

        println(mapper.writeValueAsString(value))
        return mapper.writeValueAsString(value)
    }


}