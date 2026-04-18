package br.com.movie.movielist.data.service

import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.test.Test
import kotlin.test.assertEquals

class MovieServiceTest {

    private lateinit var mockWebServer: MockWebServer
    private lateinit var service: MovieService

    @Before
    fun setup() {
        mockWebServer = MockWebServer()

        service = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MovieService::class.java)
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun `when getMyLists is called then it should map fields correctly`() = runTest {
        val jsonResponse = """
            {
                "page": 1,
                "results": [
                    {
                        "id": 120174,
                        "name": "Test Alpha Sort",
                        "description": "Filmes para teste",
                        "poster_path": "/path.jpg"
                    }
                ]
            }
        """.trimIndent()

        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody(jsonResponse)
        )

        val response = service.getMyLists(page = 1)

        assertEquals(1, response.page)
        assertEquals(120174, response.results?.first()?.id)
        assertEquals("Test Alpha Sort", response.results?.first()?.name)
    }

}