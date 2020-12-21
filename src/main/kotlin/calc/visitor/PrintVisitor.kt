package calc.visitor

import calc.token.*

class PrintVisitor : TokenVisitor {
    private val resBuilder = StringBuilder()
    val result: String
        get() {
            return resBuilder.toString()
        }

    override fun visit(token: NumberToken) {
        resBuilder.append(token.value)
        resBuilder.append(' ')
    }

    override fun visit(operation: OperationToken) {
        resBuilder.append(operation.value)
        resBuilder.append(' ')
    }

    override fun visit(bracket: BracketToken) {
        resBuilder.append(bracket.value)
        resBuilder.append(' ')
    }

}