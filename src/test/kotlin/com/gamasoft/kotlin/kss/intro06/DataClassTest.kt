package com.gamasoft.kotlin.kss.intro06

import assertk.assert
import assertk.assertions.hasSize
import assertk.assertions.isBetween
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.Test

class DataClassTest {

    data class Person(val name: String, val age: Int)

    data class Point(val x: Int, val y: Int) {

        private fun foo2(x1: Int, y1: Int): Double {
            return Math.sqrt ((x1*x1 + y1*y1).toDouble())
        }

        val foo = foo2(x,y)

        val dist: Double
        init {
            dist = Math.sqrt ((x*x + y*y).toDouble())
        }
    }

    sealed class FinancialInstrument(val name: String) {
        data class Option(val underlying: String, val type: String) : FinancialInstrument("Option")
        data class Future(val underlying: String, val expiry: Int) : FinancialInstrument("Future")
        data class EquitySwap(val underlying: String) : FinancialInstrument("TRS")
    }

    @Test
    fun aSimpleDataClass(){

        val people = listOf(
                Person("joe", 29),
                Person("anne", 42),
                Person("mary", 42))

        val under30 = people.filter { it.age < 30 }

        assert (under30).hasSize(1)

    }

    @Test
    fun dataClassAreImmutable(){

        val joey = Person("joey", 25)

//        joe.name = "joe" doesn't compile

        val joe = joey.copy(name = "joe", age = 27)

        assert (joe.name).isEqualTo("joe")
        assert (joe.age).isEqualTo(27)
    }

    @Test
    fun dataClassInitialized(){

        val p1 = Point(3, 4)
        val p2 = Point(6, 8)

        assert (p1.dist).isEqualTo(5.0)
        assert (p1.foo).isEqualTo(5.0)

        assert (p2.dist).isEqualTo(10.0)
    }

    class Person2(val name: String, val age: Int) {
        operator fun component1() = name
        operator fun component2(): Int = age

        operator fun plus(p: Person2): Person2 = Person2("$name-${p.name}", age + p.age)
    }

    @Test
    fun destructor(){


        val people = listOf(
                Person("joe", 24),
                Person("anne", 25),
                Person("mary", 26)
        )


        val profiles= people.map { (n, age) -> "$n: $age" }

        assert (profiles[0]).isEqualTo("joe: 24")
        assert (profiles[1]).isEqualTo("anne: 25")
        assert (profiles[2]).isEqualTo("mary: 26")


        val people2 = listOf(
            "joe" to 24,
            "anne" to 25,
            "mary" to 26
        )

        val profiles2 = people2.map {
            (name, age) -> "$name: $age"
        }

        val people3 = listOf(
            Person2("joe" ,24),
            Person2("anne", 25),
            Person2("mary", 26)
        )

        val profiles3 = people3.map {
            (name, age) -> "$name: $age"
        }

        //assert(Person2("a", 1) + Person2("b", 2) ).isEqualTo(Person2("ab", 3))
    }


    @Test
    fun dataClassSealed(){

        fun getName(instr: FinancialInstrument): String {
            return instr.name
        }

        assert (getName(FinancialInstrument.Option("VOD.L", "Call"))).isEqualTo("Option")
        assert (getName(FinancialInstrument.Future("BT.L", 60))).isEqualTo("Future")
    }



    @Test
    fun whenAndSealedClasses(){

        fun fullDescription(instr: FinancialInstrument): String = when (instr) {
            is FinancialInstrument.Option -> "${instr.name} ${instr.type} ${instr.underlying}"
            is FinancialInstrument.Future -> "${instr.name} ${instr.expiry} days ${instr.underlying}"
            is FinancialInstrument.EquitySwap -> "${instr.name} ${instr.underlying}"
        }

        assert (fullDescription(FinancialInstrument.Option("VOD.L", "Call"))).isEqualTo("Option Call VOD.L")
        assert (fullDescription(FinancialInstrument.Future("BT.L", 60))).isEqualTo("Future 60 days BT.L")
    }

    @Test
    fun returnAsTuple(){

        fun splitDate(date: String): Triple<Int, Int, Int> {
            val components = date.split("/").map { it.toInt() }
            return Triple(components[0], components[1], components[2])
        }

        val (day, month, year) = splitDate("12/05/2018")
        assert(day).isEqualTo(12)
        assert(month).isEqualTo(5)
        assert(year).isEqualTo(2018)

    }


}
