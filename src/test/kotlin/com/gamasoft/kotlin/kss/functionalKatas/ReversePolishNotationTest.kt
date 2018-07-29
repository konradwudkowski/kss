package com.gamasoft.kotlin.kss.functionalKatas

import assertk.assert
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.Test
import java.util.*

class ReversePolishNotationTest {

    fun rpn(exp: String): Int {
        val split = exp.split(" ")

        var op1 = 0
        var op2 = 0

        val stack = Stack<Int>()

        split.forEach {
            when (it) {
                "*" -> {
                    op1 = stack.pop()
                    op2 = stack.pop()
                    stack.push(op1 * op2)
                }
                "+" -> {
                    op1 = stack.pop()
                    op2 = stack.pop()
                    stack.push(op1 + op2)
                }
                "-" -> {
                    op1 = stack.pop()
                    op2 = stack.pop()
                    stack.push(op2 - op1)
                }
                else -> {
                    stack.push(it.toInt())
                }
            }
        }

        return stack.pop()
    }

    fun rpn2(exp: String): Int {

        val stack: Stack<Int> = exp.split(" ").fold(Stack()) { stack, current ->

            fun process(operation: (Int, Int) -> Int): Stack<Int> {
                val op1 = stack.pop()
                val op2 = stack.pop()
                stack.push(operation(op2, op1))
                return stack
            }

            when(current) {
                "*" -> process(Int::times)
                "+" -> process(Int::plus)
                "-" -> process(Int::minus)
                else -> {
                    stack.push(current.toInt())
                    stack
                }
            }

        }

        return stack.pop()

    }

    @Test
    fun simpleOperations(){
        assert(rpn("3 4 +")).isEqualTo(7)
        assert(rpn("-5 20 +")).isEqualTo(15)
        assert(rpn("13 4 -")).isEqualTo(9)
        assert(rpn("4 5 *")).isEqualTo(20)
        assert(rpn("-2 10 *")).isEqualTo(-20)
    }

    @Test
    fun longerExp(){
        assert(rpn("3 4 5 * -")).isEqualTo(-17)
        assert(rpn("5 3 4 - *")).isEqualTo(-5)
        assert(rpn("2 3 4 + *")).isEqualTo(14)
        assert(rpn("4 2 3 4 + * 4 - * 2 +")).isEqualTo(42)
        assert(rpn("7 1 1 + - 3 * 2 1 1 + + -")).isEqualTo(11)
    }

    @Test
    fun simpleOperations2(){
        assert(rpn2("3 4 +")).isEqualTo(7)
        assert(rpn2("-5 20 +")).isEqualTo(15)
        assert(rpn2("13 4 -")).isEqualTo(9)
        assert(rpn2("4 5 *")).isEqualTo(20)
        assert(rpn2("-2 10 *")).isEqualTo(-20)
    }

    @Test
    fun longerExp2(){
        assert(rpn2("3 4 5 * -")).isEqualTo(-17)
        assert(rpn2("5 3 4 - *")).isEqualTo(-5)
        assert(rpn2("2 3 4 + *")).isEqualTo(14)
        assert(rpn2("4 2 3 4 + * 4 - * 2 +")).isEqualTo(42)
        assert(rpn2("7 1 1 + - 3 * 2 1 1 + + -")).isEqualTo(11)
    }

}
