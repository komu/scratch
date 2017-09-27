package examples

import org.dalesbred.Database
import org.dalesbred.integration.kotlin.findUnique
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import kotlin.test.assertEquals

class DalesbredHsqlTest {

    private val db = Database.forUrlAndCredentials("jdbc:hsqldb:mem:.", "", "")

    @Test
    fun `greeting`() {
        assertEquals("hello, world!", db.findUnique("values 'hello, world!'"))
    }

    @ParameterizedTest(name = "2 * {arguments}")
    @ValueSource(ints = intArrayOf(1, 2, 3, 4, 5))
    fun `calculation in database`(x: Int) {
        assertEquals(2 * x, db.findUniqueInt("values 2 * ?", x))
    }
}
