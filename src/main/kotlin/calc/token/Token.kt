package calc.token

import calc.visitor.TokenVisitor

interface Token {
    fun accept(visitor: TokenVisitor)
}

class NumberToken(val value: Long) : Token {
    override fun accept(visitor: TokenVisitor) = visitor.visit(this)
}


class OperationToken(val value: Char) : Token {
    override fun accept(visitor: TokenVisitor) = visitor.visit(this)

    fun getFunction() : (Long, Long) -> Long {
        return when (value) {
            '+'  -> {a, b -> a + b}
            '-'  -> {a, b -> a - b}
            '/'  -> {a, b -> a / b}
            '*'  -> {a, b -> a * b}
            else -> error("Unknown operation: '$value'")
        }
    }
}

class BracketToken(val value: Char) : Token {
    override fun accept(visitor: TokenVisitor) = visitor.visit(this)

    fun isOpen(): Boolean {
        return value == '(';
    }
}