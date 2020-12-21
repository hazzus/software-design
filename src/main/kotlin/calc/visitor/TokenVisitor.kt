package calc.visitor

import calc.token.*

interface TokenVisitor {
    fun visit(token: NumberToken)

    fun visit(operation: OperationToken)

    fun visit(bracket: BracketToken)
}