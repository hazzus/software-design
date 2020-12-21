package calc.visitor

import calc.token.*

class CalcVisitor : TokenVisitor {
    private val tokens = ArrayDeque<Token>()
    val result: Long
        get() {
            if (tokens.size != 1) {
                error("Calculation stack is not single-sized")
            }
            return (tokens.removeFirst() as? NumberToken ?: error("Last element is not number")).value
        }

    override fun visit(token: NumberToken) {
        tokens.addFirst(token)
    }

    override fun visit(operation: OperationToken) {
        if (tokens.size < 2) {
            error("Calculating operation requires 2 operands")
        }
        val second = tokens.removeFirst() as? NumberToken ?: error("Second operand is not number")
        val first = tokens.removeFirst() as? NumberToken ?: error("First operand is not number")

        tokens.addFirst(NumberToken(operation.getFunction()(first.value, second.value)))
    }

    override fun visit(bracket: BracketToken) = error("No brackets expected in calculating RPN")
}