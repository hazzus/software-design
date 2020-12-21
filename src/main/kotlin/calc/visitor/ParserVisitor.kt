package calc.visitor

import calc.token.*

class ParserVisitor : TokenVisitor {
    private val tokens = mutableListOf<Token>()
    private val operations = ArrayDeque<Token>()
    val result: List<Token>
        get() {
            while (operations.isNotEmpty()) {
                tokens += operations.removeFirst()
            }
            return tokens
        }


    override fun visit(token: NumberToken) {
        tokens += token
    }

    override fun visit(operation: OperationToken) {
        while (true) {
            val current = operations.firstOrNull()
            if (current is OperationToken) {
                val operator = current.value
                if (priorities[operator]!! >= priorities[operation.value]!!) {
                    tokens += operations.removeFirst()
                    continue
                }
            }
            break
        }
        operations.addFirst(operation)
    }

    override fun visit(bracket: BracketToken) {
        if (bracket.isOpen()) {
            operations.addFirst(bracket)
            return
        }

        while (true) {
            if (operations.isEmpty()) {
                error("Closing bracket without opening found")
            }
            val operationsTop = operations.removeFirst()
            if (operationsTop is BracketToken && operationsTop.isOpen()) break
            tokens += operationsTop
        }
    }

    companion object {
        val priorities = mapOf('+' to 1, '-' to 1, '/' to 2, '*' to 2)
    }
}