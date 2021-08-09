package dev.bombianating.bianmen

import dev.bombinating.bianmen.ResourceExt.div
import kotlin.test.Test
import kotlin.test.assertEquals

class ResourceExtTest {

    @Test
    fun `div path test`() {
        val path = "wicket/test"
        val resRef = ResourceExtTest::class / path
        assertEquals(ResourceExtTest::class.java.name, resRef.key.scope)
        assertEquals(path, resRef.key.name)
    }

}
