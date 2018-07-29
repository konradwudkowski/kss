package com.gamasoft.kotlin.kss.intro08

import assertk.assert
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.Test

class AdvancedFunTest {

    tailrec fun sillyMulti(x: Int, times: Int, a: Int = 0): Int {
        return if (times == 0) a
        else sillyMulti(x, times - 1, a + x)
    }


    @Test
    fun tailRecursionSafe(){

        val res = sillyMulti(3, 50000)

        assert(res).isEqualTo(150000)

    }

    @Test
    fun classExtension(){

        fun Int.hello(): String = "Hi, I'm $this"

        assertk.assert(5.hello()).isEqualTo("Hi, I'm 5")
        assertk.assert(42.hello()).isEqualTo("Hi, I'm 42")

    }

    @Test
    fun nullableClassExtension(){

        fun Int?.hello(): String = "Hi, I'm ${this ?: "no one"}"

        assertk.assert(5.hello()).isEqualTo("Hi, I'm 5")
        assertk.assert(null.hello()).isEqualTo("Hi, I'm no one")

    }

    @Test
    fun infixNotation(){

        infix fun <A,B>A.pp(other: B): Pair<A,B> = Pair(this, other)

        assertk.assert(5 pp 8).isEqualTo(Pair(5, 8))
        assertk.assert("joe" pp 45).isEqualTo(Pair("joe", 45))

    }


    @Test
    fun funNames(){

        infix fun <A,B>A.`@`(other: B): Pair<A,B> = Pair(this, other)

        assertk.assert(5 `@` 8).isEqualTo(Pair(5, 8))
        assertk.assert("joe" `@` 45).isEqualTo(Pair("joe", 45))

    }


    interface DbContext{
        fun fetchFromDb(key: String):String
    }

    class InMemDb: DbContext {
        override fun fetchFromDb(key: String): String {
            return "joe"
        }
    }

    @Test
    fun extFunInScoped(){
        val dbConn = InMemDb()

        dbConn.apply {
            val userName = fetchFromDb("u1234")

            assert(userName).isEqualTo("joe")
        }

    }


    inline fun <reified T> getOrDefault(value: T?):T {
        return if (value == null)
            return if (String::javaClass == T::class.java) {
                "abc" as T
            } else {
                T::class.java.getDeclaredConstructor().newInstance()
            }

        else
            value
    }

    class Foo

    @Test
    fun reifiedGeneric(){

        val s1 = getOrDefault("abc")
        val s2 = getOrDefault<String>(null)
        println(getOrDefault<Foo>(null))

        assert(s1).isEqualTo(s2)

    }

}
