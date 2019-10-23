package crypto.msd117c.com.cryptocurrency.utils

import android.content.Context
import android.util.Log
import androidx.annotation.VisibleForTesting
import crypto.msd117c.com.cryptocurrency.utils.json.JsonGetter
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.RecordedRequest
import javax.inject.Singleton

@VisibleForTesting
class MockWebServerDispatcher {

    @Singleton
    class RequestDispatcher(private val context: Context?) : Dispatcher() {

        override fun dispatch(request: RecordedRequest?): MockResponse {
            val mockedResponse = MockResponse()
            mockedResponse.setResponseCode(200)
            Log.d("PATH", request?.path)
            if (request != null) {
                val path = if (request.path.contains("?")) {
                    request.path.split("?")[0]
                } else {
                    request.path
                }
                context?.let {
                    mockedResponse.setBody(
                        JsonGetter().getJSON(
                            "json$path/valid/response.json",
                            context
                        )
                    )
                    return mockedResponse
                }
                mockedResponse.setBody(JsonGetter().getJSON("json$path/valid/response.json"))
            } else {
                mockedResponse.setBody("")
            }
            return mockedResponse
        }

    }

    @Singleton
    class RequestExpectedDispatcher(
        private val context: Context?,
        private val responseCodes: Array<Int>
    ) : Dispatcher() {

        private var counter = 0

        override fun dispatch(request: RecordedRequest?): MockResponse {
            val mockedResponse = MockResponse()
            mockedResponse.setResponseCode(responseCodes[counter++])
            if (counter == responseCodes.size) {
                counter = 0
            }

            if (request != null) {
                val path = if (request.path.contains("?")) {
                    request.path.split("?")[0]
                } else {
                    request.path
                }
                if (context != null) {
                    mockedResponse.setBody(
                        JsonGetter().getJSON(
                            "json$path/valid/response.json",
                            context
                        )
                    )
                } else {
                    mockedResponse.setBody(JsonGetter().getJSON("json$path/valid/response.json"))
                }
            } else {
                mockedResponse.setBody("")
            }
            return mockedResponse
        }

    }

    @Singleton
    class RequestInvalidDispatcher(private val context: Context?) : Dispatcher() {

        override fun dispatch(request: RecordedRequest?): MockResponse {
            val mockedResponse = MockResponse()
            mockedResponse.setResponseCode(200)
            if (request != null) {
                val path = if (request.path.contains("?")) {
                    request.path.split("?")[0]
                } else {
                    request.path
                }
                if (context != null) {
                    mockedResponse.setBody(
                        JsonGetter().getJSON(
                            "json$path/invalid/response.json",
                            context
                        )
                    )
                } else {
                    mockedResponse.setBody(JsonGetter().getJSON("json$path/invalid/response.json"))
                }
            } else {
                mockedResponse.setBody("")
            }
            return mockedResponse
        }

    }

    @Singleton
    class RequestUnauthorizedDispatcher(private val context: Context?) : Dispatcher() {

        override fun dispatch(request: RecordedRequest?): MockResponse {
            val mockedResponse = MockResponse()
            mockedResponse.setResponseCode(422)
            if (request != null) {
                val path = if (request.path.contains("?")) {
                    request.path.split("?")[0]
                } else {
                    request.path
                }
                if (context != null) {
                    mockedResponse.setBody(
                        JsonGetter().getJSON(
                            "json$path/invalid/response.json",
                            context
                        )
                    )
                } else {
                    mockedResponse.setBody(JsonGetter().getJSON("json$path/invalid/response.json"))
                }
            } else {
                mockedResponse.setBody("")
            }
            return mockedResponse
        }

    }

    @Singleton
    class RequestMalformedDispatcher(private val context: Context?) : Dispatcher() {

        override fun dispatch(request: RecordedRequest?): MockResponse {
            val mockedResponse = MockResponse()
            mockedResponse.setResponseCode(200)
            if (request != null) {
                val path = if (request.path.contains("?")) {
                    request.path.split("?")[0]
                } else {
                    request.path
                }
                if (context != null) {
                    mockedResponse.setBody(
                        JsonGetter().getJSON(
                            "json$path/malformed/response.json",
                            context
                        )
                    )
                } else {
                    mockedResponse.setBody(JsonGetter().getJSON("json$path/malformed/response.json"))
                }
            } else {
                mockedResponse.setBody("")
            }
            return mockedResponse
        }


    }

}