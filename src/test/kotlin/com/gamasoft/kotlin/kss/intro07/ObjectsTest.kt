package com.gamasoft.kotlin.kss.intro07

import assertk.assert
import assertk.assertions.isEqualTo
import assertk.assertions.isLessThan
import org.junit.jupiter.api.Test
import java.time.Instant

class ObjectsTest {

    data class User (val name: String, val since: Instant) {
        companion object {
            fun createRnd():User = User("foo", Instant.now())
        }
    }

    @Test
    fun companionObjectLikeStaticMethod(){

        val u1 = User.createRnd()
        Thread.sleep(1)
        val u2 = User.createRnd()

        assertk.assert(u1.since).isLessThan(u2.since)

    }

    sealed class OptionType{
        object Call: OptionType()
        object Put: OptionType()
    }

    @Test
    fun objectLikeEnum(){

        val u1 = User.createRnd()
        Thread.sleep(1)
        val u2 = User.createRnd()

        assertk.assert(u1.since).isLessThan(u2.since)

    }

    sealed class TreeNode{
        object Root: TreeNode()
        data class Node(val parent: TreeNode, val value: Int): TreeNode()
    }

    @Test
    fun navigateAndBuildTree(){
        val n1 = TreeNode.Node(TreeNode.Root, 3)
        val n2 = TreeNode.Node(n1, 4)
        val n3 = TreeNode.Node(n1, 5)
        val n4 = TreeNode.Node(n2, 6)

        fun sumToRoot(node: TreeNode): Int = when(node) {
          is TreeNode.Root             -> 0
          is ObjectsTest.TreeNode.Node -> node.value + sumToRoot(node.parent)
        }

        //return the sum of values of all parents from node to root

        assert(sumToRoot(n3)).isEqualTo(8)
        assert(sumToRoot(n4)).isEqualTo(13)

    }

    object MyCache: HashMap<String, Int>()


    @Test
    fun objectAsSingleton(){

        MyCache["ten"] = 10
        MyCache["five"] = 5

        assert(MyCache["ten"]).isEqualTo(10)
        assert(MyCache["five"]).isEqualTo(5)

    }

    @Test
    fun objectAsInstanceOfAnonClass(){

        val firstOddNumbers = object : Comparator<Int> {
            override fun compare(o1: Int?, o2: Int?): Int {
                return if (o2 == null || o1 == null) {
                    -1
                } else {
                    if (o1 % 2 != 0 && o2 % 2 != 0) {
                        0
                    } else if (o1 % 2 != 0) {
                        -1
                    } else {
                        1
                    }
                }
            }

        }

        val sortedList = listOf(4,6,2,6,5,9,0,7).sortedWith(firstOddNumbers)

        assert(sortedList.first()).isEqualTo(5)

    }


}
