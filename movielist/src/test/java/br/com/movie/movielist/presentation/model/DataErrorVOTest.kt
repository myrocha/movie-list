package br.com.movie.movielist.presentation.model

import br.com.movie.movielist.R
import br.com.movie.movielist.domain.error.DataError
import org.junit.Assert.assertEquals
import org.junit.Test

class DataErrorVOTest {

    @Test
    fun `when error is NO_INTERNET should map to correct internet resources`() {
        val error = DataError.Network.NO_INTERNET

        val vo = error.toVO()

        assertEquals(R.string.error_no_internet_title, vo.title)
        assertEquals(R.string.error_no_internet_description, vo.description)
        assertEquals(R.drawable.icon_wifi_off, vo.imageRes)
    }

    @Test
    fun `when error is generic should map to correct alert resources`() {
        val genericErrors = listOf(
            DataError.Network.SERVICE_UNAVAILABLE,
            DataError.Network.UNKNOWN,
            DataError.Network.SERIALIZATION,
            DataError.Network.UNAUTHORIZED
        )

        genericErrors.forEach { error ->
            val vo = error.toVO()

            assertEquals(
                "Failed for error: $error",
                R.string.error_generic_title,
                vo.title
            )
            assertEquals(
                "Failed for error: $error",
                R.string.error_generic_description,
                vo.description
            )
            assertEquals(
                "Failed for error: $error",
                R.drawable.icon_alert_triangle,
                vo.imageRes
            )
        }
    }
}