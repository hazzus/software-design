package calc

import calc.token.Tokenizer
import calc.visitor.CalcVisitor
import calc.visitor.ParserVisitor
import org.junit.Assert
import org.junit.Test

class CalculatorTest {

    private fun calculate(data: String): Long {
        val tokenizer = Tokenizer()
        tokenizer.tokenize(data)
        val parser = ParserVisitor()
        tokenizer.result.forEach { it.accept(parser) }
        val calculator = CalcVisitor()
        parser.result.forEach { it.accept(calculator) }
        return calculator.result
    }

    private fun test(exp: Long, expr: String) {
        Assert.assertEquals(exp, calculate(expr))
    }

    @Test
    fun calculateTest() {
        test(0, "0")
        test(2, "1 + 1")
        test(-3, "5 - 8")
        test(16, "4 * 4")
        test(4, "256 / 2 / 4 / 8")
        test(256, "2 * 4 * 4 * (8 - 0)")
        test(5, "(0 + 1) * 5")
        test(121, "((3 + 1) / 2 + 9) * 11")
        test(1129206, "1129214 * 1 - 85 * 3 / (45 - 5 * 12 + 45)")
    }

    private fun exceptionTest(expr: String) {
        try {
            calculate(expr)
            Assert.assertFalse("Expected illegal state exception", true)
        } catch (e: IllegalStateException) {
            Assert.assertTrue(true)
        } catch (e: ArithmeticException) {
            Assert.assertTrue(true)
        }
    }

    @Test
    fun badCalculateTest() {
        exceptionTest("(1 + 2")
        exceptionTest("1 + 2)")
        exceptionTest("12 +")
        exceptionTest("- 3")
        exceptionTest(" 2 / 0")
        exceptionTest("1.2")
    }
}