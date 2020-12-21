package calc

import calc.token.Tokenizer
import calc.visitor.CalcVisitor
import calc.visitor.ParserVisitor
import calc.visitor.PrintVisitor

fun main(args: Array<String>) {
    val tok = Tokenizer()
    val data = args.firstOrNull() ?: "(1 + 2) * 3"
    tok.tokenize(data)

    val parser = ParserVisitor()
    tok.result.forEach { it.accept(parser) }

    val printer = PrintVisitor()
    parser.result.forEach { it.accept(printer) }
    println("RPN: ${printer.result}")

    val calculator = CalcVisitor()
    parser.result.forEach { it.accept(calculator) }
    println("Result: ${calculator.result}")
}