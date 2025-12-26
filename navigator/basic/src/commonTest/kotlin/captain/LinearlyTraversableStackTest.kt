package captain

import captain.stack.traversableStackOf
import kommander.expect
import kotlin.test.Test

class LinearlyTraversableStackTest {

    @Test
    fun should_be_able_to_push_an_element_to_the_stack() {
        val stack = traversableStackOf<Int>()
        stack.push(0) // [{0}]
        expect(stack).toBeOfSize(1)
        expect(stack.first()).toBe(0)
        expect(stack.firstOrNull()).toBe(0)
    }

    @Test
    fun should_be_able_to_push_multiple_elements_to_the_stack() {
        val stack = traversableStackOf<Int>()
        stack.push(0) // [{0}]
        stack.push(1) // [{0},1]
        stack.push(2) // [{0},1,2]
        expect(stack).toBeOfSize(3)
        expect(stack.top()).toBe(2)
    }

    @Test
    fun should_be_able_to_pop_an_element_out_of_the_stack() {
        val stack = traversableStackOf<Int>()
        stack.push(0)               // [{0}]
        stack.push(1)               // [{0},1]
        stack.push(2)               // [{0},1,2]
        val canPop = stack.canPop()
        expect(canPop).toBe(true)
        val element = stack.pop()           // [{0},1] -> 2
        expect(stack).toBeOfSize(2)
        expect(stack.top()).toBe(1)
        expect(element).toBe(2)
    }

    @Test
    fun inserting_an_element_should_insert_and_move_the_cursor() {
        val stack = traversableStackOf<Int>()
        stack.insert(0)                 // [{0}]
        expect(stack.toString()).toBe("[{0}]")
        stack.insert(1)                 // [0,{1}]
        expect(stack.toString()).toBe("[0,{1}]")
        stack.insert(2)                 // [0,1,{2}]
        expect(stack.toString()).toBe("[0,1,{2}]")
        expect(stack).toBeOfSize(3)
        expect(stack.top()).toBe(2)
        expect(stack.backward()).toBe(1)    // [0,{1},2]
    }

    @Test
    fun should_be_able_to_traverse_the_stack_backward() {
        val stack = traversableStackOf<Int>()
        stack.insert(0)
        expect(stack.toString()).toBe("[{0}]")
        stack.insert(1)                 // [0,{1}]
        expect(stack.toString()).toBe("[0,{1}]")
        stack.insert(2)
        expect(stack.toString()).toBe("[0,1,{2}]")
        expect(stack).toBeOfSize(3)
        expect(stack.top()).toBe(2)
        expect(stack.backward()).toBe(1)    // [0,{1},2]
    }

    @Test
    fun should_insert_elements_at_the_cursors_position() {
        val stack = traversableStackOf<Int>()
        repeat(3) { stack.push(it) }        // [{0},1,2]
        stack.go(1)                         // [0,{1},2]
        expect(stack.toString()).toBe("[0,{1},2]")
        stack.insert(3)                   // [0,1,{3},2]
        expect(stack.toString()).toBe("[0,1,{3},2]")
        expect(stack.current()).toBe(3)
        expect(stack).toBeOfSize(4)
        expect(stack.top()).toBe(2)
        expect(stack.backward()).toBe(1)     // [0,{1},3,2]
    }

    @Test
    fun should_be_able_to_traverse_the_stack_forward_after_it_has_been_traversed_backwards() {
        val stack = traversableStackOf<Int>()
        stack.push(0)   // [0]
        stack.push(1)   // [0,1]
        stack.push(2)   // [0,1,2]
        stack.backward()            // [0,{1},2]
        stack.backward()            // [{0},1,2]
        val el = stack.forward()// [0,{1},2]
        expect(stack).toBeOfSize(3)
        expect(stack.top()).toBe(2)
        expect(el).toBe(1)
    }

    @Test
    fun should_be_able_to_traverse_the_stack_multiple_steps_backwards() {
        val stack = traversableStackOf<Int>()
        repeat(10) { stack.insert(it) }
        val el = stack.go(-5)
        expect(stack).toBeOfSize(10)
        expect(stack.top()).toBe(9)
        expect(el).toBe(4)
    }

    @Test
    fun should_be_able_to_traverse_the_stack_multiple_steps_forward() {
        val stack = traversableStackOf<Int>()
        repeat(10) { stack.insert(it) }
        stack.go(-7)
        val el = stack.go(5)
        expect(stack).toBeOfSize(10)
        expect(stack.top()).toBe(9)
        expect(el).toBe(7)
    }

    @Test
    fun should_be_able_to_stand_in_position_when_told_to_zero_steps() {
        val stack = traversableStackOf<Int>()
        repeat(10) { stack.push(it) }
        val el = stack.go(0)
        expect(stack).toBeOfSize(10)
        expect(stack.top()).toBe(9)
        expect(el).toBe(0)
    }

    @Test
    fun should_insertTrim_an_empty_stack_and_set_the_cursor() {
        val stack = traversableStackOf<String>()    // []
        stack.insertTrimmingTop("home")
        val el = stack.go(0)
        expect(el).toBe("home")
        expect(stack).toBeOfSize(1)
    }

    @Test
    fun should_move_the_cursor_forward_after_each_insertTrimmingTop() {
        val stack = traversableStackOf<String>()    // []
        stack.insertTrimmingTop("home")     // [{home}]
        expect(stack.current()).toBe("home")
        stack.insertTrimmingTop("about")    // [home,{about}]
        expect(stack.current()).toBe("about")
        expect(stack).toBeOfSize(2)
    }

    @Test
    fun should_retain_cursor_position_during_injections() {
        val stack = traversableStackOf<String>()               // []
        stack.insertTrimmingTop("home")                       // [{home}]
        expect(stack.current()).toBe("home")
        stack.insertTrimmingTop("about")                      // [home,{about}]
        expect(stack.current()).toBe("about")
        expect(stack.backward()).toBe("home")             // [{home},about]
        stack.insertTrimmingTop("contacts")                   // [home,{contacts}]
        expect(stack.current()).toBe("contacts")
        expect(stack.go(-1)).toBe("home")       // [{home},contacts]
        expect(stack.go(1)).toBe("contacts")    // [home,{contacts}]
        stack.insertTrimmingTop("demo")                       // [home,contacts,{demo}]
        expect(stack.current()).toBe("demo")
        expect(stack.go(-2)).toBe("home")       // [{home},contacts,demo]
        expect(stack.backward()).toBeNull()                        //
    }

    @Test
    fun should_be_able_to_move_the_cursor_properly_while_trimming_the_bottom_stack() {
        val stack = traversableStackOf<String>()
        stack.insertTrimmingBottom("home")
        expect(stack.toString()).toBe("[{home}]")
        stack.insertTrimmingBottom("about")
        expect(stack.toString()).toBe("[{about}]")
        expect(stack.backward()).toBe(null)
        stack.insert("contacts")
        expect(stack.toString()).toBe("[about,{contacts}]")
        stack.go(-1)
        expect(stack.toString()).toBe("[{about},contacts]")
        stack.go(1)
        expect(stack.toString()).toBe("[about,{contacts}]")
//        stack.insertTrimmingBottom("demo")             // [{demo}]
//        expect(stack.toString()).toBe("[{demo}]")
    }
}