package com.gamasoft.kotlin.kss.intro01

import assertk.assert
import assertk.assertions.containsExactly
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.Test

class BasicTypesTest {


    @Test
    fun valOrVar(){

        var iCanChange = 5

        val iCannotChange = "42".toInt()

        //add something here to make it pass
        iCanChange = 42

        assert(iCanChange).isEqualTo(iCannotChange)

    }


    @Test
    fun multilineString(){

        //change string to pass the test
        val multiline = """
            line 1
            line 2
            line 3
            line 4
        """.trimIndent()

        assert(multiline.lines().size).isEqualTo(4)

    }

    @Test
    fun templateString(){

        //change string to pass the test
        val mid = "b c"
        val letters = "a $mid d".trimIndent()

        assert(letters).isEqualTo("a b c d")

    }


    @Test
    fun rangeChar(){

        val str = "hello123"
        var strip = ""
        for (c in str) {
            //fix the range to pass the test
            strip += if (c in 'a'..'z') c else '_'
        }

        assert(strip).isEqualTo("hello___")

    }

    @Test
    fun rangeNum(){

        var tot = 0

        fun findValue(total: Int, expectation: Int, upperLimit: Int): Int {
            return if (total == expectation) {
                upperLimit - 1
            } else {
                val newTotal = total + upperLimit * upperLimit
                findValue(newTotal, expectation, upperLimit + 1)
            }
        }

        val foundUpperLimit = findValue(total = 0, expectation = 70*70, upperLimit = 0)

        println("foundUpperLimit = $foundUpperLimit")

        //find the right range
        for (x in 1 .. foundUpperLimit) {
            tot += x * x
        }

        assert(tot).isEqualTo(70*70)

    }

    @Test
    fun downTo(){

        fun reverse(s: String): String{
            var rev = ""
            //find the right range
            for (i in (s.length - 1).downTo(0)) {
                rev += s[i]
            }
            return rev
        }

        assert(reverse("Hello")).isEqualTo("olleH")

    }

    @Test
    fun arrays(){
        // fix the arguments to make test pass
        val odds = arrayOf(1,3,5,7,9)
        val odds2 = (1 .. 10 step 2).toList().toTypedArray()

        assert(odds).containsExactly(*odds2)
    }


}
