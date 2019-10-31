@file:Suppress("UNUSED_PARAMETER", "ConvertCallChainIntoSequence")

package lesson4.task1

import lesson1.task1.discriminant
import kotlin.math.sqrt

/**
 * Пример
 *
 * Найти все корни уравнения x^2 = y
 */
fun sqRoots(y: Double) =
    when {
        y < 0 -> listOf()
        y == 0.0 -> listOf(0.0)
        else -> {
            val root = sqrt(y)
            // Результат!
            listOf(-root, root)
        }
    }

/**
 * Пример
 *
 * Найти все корни биквадратного уравнения ax^4 + bx^2 + c = 0.
 * Вернуть список корней (пустой, если корней нет)
 */
fun biRoots(a: Double, b: Double, c: Double): List<Double> {
    if (a == 0.0) {
        return if (b == 0.0) listOf()
        else sqRoots(-c / b)
    }
    val d = discriminant(a, b, c)
    if (d < 0.0) return listOf()
    if (d == 0.0) return sqRoots(-b / (2 * a))
    val y1 = (-b + sqrt(d)) / (2 * a)
    val y2 = (-b - sqrt(d)) / (2 * a)
    return sqRoots(y1) + sqRoots(y2)
}

/**
 * Пример
 *
 * Выделить в список отрицательные элементы из заданного списка
 */
fun negativeList(list: List<Int>): List<Int> {
    val result = mutableListOf<Int>()
    for (element in list) {
        if (element < 0) {
            result.add(element)
        }
    }
    return result
}

/**
 * Пример
 *
 * Изменить знак для всех положительных элементов списка
 */
fun invertPositives(list: MutableList<Int>) {
    for (i in 0 until list.size) {
        val element = list[i]
        if (element > 0) {
            list[i] = -element
        }
    }
}

/**
 * Пример
 *
 * Из имеющегося списка целых чисел, сформировать список их квадратов
 */
fun squares(list: List<Int>) = list.map { it * it }

/**
 * Пример
 *
 * Из имеющихся целых чисел, заданного через vararg-параметр, сформировать массив их квадратов
 */
fun squares(vararg array: Int) = squares(array.toList()).toTypedArray()

/**
 * Пример
 *
 * По заданной строке str определить, является ли она палиндромом.
 * В палиндроме первый символ должен быть равен последнему, второй предпоследнему и т.д.
 * Одни и те же буквы в разном регистре следует считать равными с точки зрения данной задачи.
 * Пробелы не следует принимать во внимание при сравнении символов, например, строка
 * "А роза упала на лапу Азора" является палиндромом.
 */
fun isPalindrome(str: String): Boolean {
    val lowerCase = str.toLowerCase().filter { it != ' ' }
    for (i in 0..lowerCase.length / 2) {
        if (lowerCase[i] != lowerCase[lowerCase.length - i - 1]) return false
    }
    return true
}

/**
 * Пример
 *
 * По имеющемуся списку целых чисел, например [3, 6, 5, 4, 9], построить строку с примером их суммирования:
 * 3 + 6 + 5 + 4 + 9 = 27 в данном случае.
 */
fun buildSumExample(list: List<Int>) = list.joinToString(separator = " + ", postfix = " = ${list.sum()}")

/**
 * Простая
 *
 * Найти модуль заданного вектора, представленного в виде списка v,
 * по формуле abs = sqrt(a1^2 + a2^2 + ... + aN^2).
 * Модуль пустого вектора считать равным 0.0.
 */
fun abs(v: List<Double>): Double {
    var module = 0.0
    for (elem in v)
        module += elem * elem
    return sqrt(module)
}

/**
 * Простая
 *
 * Рассчитать среднее арифметическое элементов списка list. Вернуть 0.0, если список пуст
 */
fun mean(list: List<Double>): Double =
    if (list.isNotEmpty())
        list.sum() / list.size
    else
        0.0


/**
 * Средняя
 *
 * Центрировать заданный список list, уменьшив каждый элемент на среднее арифметическое всех элементов.
 * Если список пуст, не делать ничего. Вернуть изменённый список.
 *
 * Обратите внимание, что данная функция должна изменять содержание списка list, а не его копии.
 */
fun center(list: MutableList<Double>): MutableList<Double> {
    val average = mean(list)
    for (i in list.indices)
        list[i] -= average
    return list
}

/**
 * Средняя
 *
 * Найти скалярное произведение двух векторов равной размерности,
 * представленные в виде списков a и b. Скалярное произведение считать по формуле:
 * C = a1b1 + a2b2 + ... + aNbN. Произведение пустых векторов считать равным 0.
 */
fun times(a: List<Int>, b: List<Int>): Int {
    var composition = 0
    for (i in a.indices)
        composition += a[i] * b[i]
    return composition
}

/**
 * Средняя
 *
 * Рассчитать значение многочлена при заданном x:
 * p(x) = p0 + p1*x + p2*x^2 + p3*x^3 + ... + pN*x^N.
 * Коэффициенты многочлена заданы списком p: (p0, p1, p2, p3, ..., pN).
 * Значение пустого многочлена равно 0 при любом x.
 */
fun polynom(p: List<Int>, x: Int): Int {
    var sum = 0
    var elem = 1
    for (i in p.indices) {
        sum += p[i] * elem
        elem *= x
    }
    return sum
}

/**
 * Средняя
 *
 * В заданном списке list каждый элемент, кроме первого, заменить
 * суммой данного элемента и всех предыдущих.
 * Например: 1, 2, 3, 4 -> 1, 3, 6, 10.
 * Пустой список не следует изменять. Вернуть изменённый список.
 *
 * Обратите внимание, что данная функция должна изменять содержание списка list, а не его копии.
 */
fun accumulate(list: MutableList<Int>): MutableList<Int> {
    for (i in 1 until list.size)
        list[i] += list[i - 1]
    return list
}

/**
 * Средняя
 *
 * Разложить заданное натуральное число n > 1 на простые множители.
 * Результат разложения вернуть в виде списка множителей, например 75 -> (3, 5, 5).
 * Множители в списке должны располагаться по возрастанию.
 */

fun minDivisorOptimized(n: Int, start: Int): Int {
    var del = start
    while (n % del != 0)
        del += 1
    return del
}

fun factorize(n: Int): List<Int> {
    var num = n
    val list = mutableListOf<Int>()
    var div = 2
    do {
        div = minDivisorOptimized(num, div)
        list.add(div)
        num /= div
    } while (num != 1)
    return list
}

/**
 * Сложная
 *
 * Разложить заданное натуральное число n > 1 на простые множители.
 * Результат разложения вернуть в виде строки, например 75 -> 3*5*5
 * Множители в результирующей строке должны располагаться по возрастанию.
 */
fun factorizeToString(n: Int): String {
    val list = factorize(n)
    var str: String = list[0].toString()
    for (i in 1 until list.size)
        str += "*" + list[i].toString()
    return str
}

/**
 * Средняя
 *
 * Перевести заданное целое число n >= 0 в систему счисления с основанием base > 1.
 * Результат перевода вернуть в виде списка цифр в base-ичной системе от старшей к младшей,
 * например: n = 100, base = 4 -> (1, 2, 1, 0) или n = 250, base = 14 -> (1, 3, 12)
 */
fun convert(n: Int, base: Int): List<Int> {
    val list = mutableListOf<Int>()
    var num = n
    do {
        list.add(num % base)
        num /= base
    } while (num != 0)
    return list.reversed()
}
//97
/**
 * Сложная
 *
 * Перевести заданное целое число n >= 0 в систему счисления с основанием 1 < base < 37.
 * Результат перевода вернуть в виде строки, цифры более 9 представлять латинскими
 * строчными буквами: 10 -> a, 11 -> b, 12 -> c и так далее.
 * Например: n = 100, base = 4 -> 1210, n = 250, base = 14 -> 13c
 *
 * Использовать функции стандартной библиотеки, напрямую и полностью решающие данную задачу
 * (например, n.toString(base) и подобные), запрещается.
 */
fun convertToString(n: Int, base: Int): String {
    var str = ""
    for (i in convert(n, base)) {
        if (i > 9)
            str += (i + 87).toChar()
        else
            str += i.toString()
    }
    return str
}

/**
 * Средняя
 *
 * Перевести число, представленное списком цифр digits от старшей к младшей,
 * из системы счисления с основанием base в десятичную.
 * Например: digits = (1, 3, 12), base = 14 -> 250
 */
fun decimal(digits: List<Int>, base: Int): Int = polynom(digits.reversed(), base)

/**
 * Сложная
 *
 * Перевести число, представленное цифровой строкой str,
 * из системы счисления с основанием base в десятичную.
 * Цифры более 9 представляются латинскими строчными буквами:
 * 10 -> a, 11 -> b, 12 -> c и так далее.
 * Например: str = "13c", base = 14 -> 250
 *
 * Использовать функции стандартной библиотеки, напрямую и полностью решающие данную задачу
 * (например, str.toInt(base)), запрещается.
 */
//48--57 -> 0--9      97--122 -> a--z
fun decimalFromString(str: String, base: Int): Int {
    val digits = mutableListOf<Int>()
    for (i in str) {
        if (i in '0'..'9')
            digits.add(i.toInt() - 48)
        else
            digits.add(i.toInt() - 87)
    }
    return decimal(digits, base)
}

/**
 * Сложная
 *
 * Перевести натуральное число n > 0 в римскую систему.
 * Римские цифры: 1 = I, 4 = IV, 5 = V, 9 = IX, 10 = X, 40 = XL, 50 = L,
 * 90 = XC, 100 = C, 400 = CD, 500 = D, 900 = CM, 1000 = M.
 * Например: 23 = XXIII, 44 = XLIV, 100 = C
 */

fun roman(n: Int): String {
    var answer = ""
    var number = n
    val equalities = mapOf(
        1000 to "M",
        900 to "CM",
        500 to "D",
        400 to "CD",
        100 to "C",
        90 to "XC",
        50 to "L",
        40 to "XL",
        10 to "X",
        9 to "IX",
        5 to "V",
        4 to "IV",
        1 to "I"
    )
    for (i in equalities.keys) {
        val count = number / i
        answer += equalities.getValue(i).repeat(count)
        number -= i * count
    }
    return answer
}

/**
 * Очень сложная
 *
 * Записать заданное натуральное число 1..999999 прописью по-русски.
 * Например, 375 = "триста семьдесят пять",
 * 23964 = "двадцать три тысячи девятьсот шестьдесят четыре"
 */

fun getDigits(n: Int): List<Int> {
    val result = mutableListOf<Int>()
    var number = n
    while (number > 0) {
        result.add(number % 10)
        number /= 10
    }
    return result
}

fun russian(n: Int): String {
    val data = mapOf(
        3 to "три ",
        4 to "четыре ",
        5 to "пять ",
        6 to "шесть ",
        7 to "семь ",
        8 to "восемь ",
        9 to "девять ",
        10 to "десять ",
        11 to "одиннадцать ",
        12 to "двенадцать ",
        13 to "тринадцать ",
        14 to "четырнадцать ",
        15 to "пятнадцать ",
        16 to "шестнадцать ",
        17 to "семнадцать ",
        18 to "восемнадцать ",
        19 to "девятнадцать ",
        20 to "двадцать ",
        30 to "тридцать ",
        40 to "сорок ",
        50 to "пятьдесят ",
        60 to "шестьдесят ",
        70 to "семьдесят ",
        80 to "восемьдесят ",
        90 to "девяносто ",
        100 to "сто ",
        200 to "двести ",
        300 to "триста ",
        400 to "четыреста ",
        500 to "пятьсот ",
        600 to "шестьсот ",
        700 to "семьсот ",
        800 to "восемьсот ",
        900 to "девятьсот ",
        0 to ""
    )
    val digits = getDigits(n).reversed()
    val digitsFormatted = mutableListOf<Int>()
    var index = 0
    while (index < digits.size) {
        if (digits[index] == 1 && (digits.size - index - 1) % 3 == 1) {
            digitsFormatted.add(digits[index] * 10 + digits[index + 1])
            index += 1
        } else if (digits[index] in 2..9 && (digits.size - index - 1) % 3 == 1)
            digitsFormatted.add(digits[index] * 10)
        else if (digits[index] in 1..9 && (digits.size - index - 1) % 3 == 2)
            digitsFormatted.add(digits[index] * 100)
        else digitsFormatted.add(digits[index])
        index += 1
    }
    println(digitsFormatted)
    var answer = ""
    for ((i, value) in digitsFormatted.withIndex()) {
        answer += when (value) {
            1 -> if (i == digitsFormatted.size - 1) "один " else "одна "
            2 -> if (i == digitsFormatted.size - 1) "два " else "две "
            else -> data[value]
        }
        if (digits.size - i == 4)
            answer += when (value) {
                1 -> "тысяча "
                in 2..4 -> "тысячи "
                else -> "тысяч "
            }
    }
    return answer.trim()
}

fun main() {
    println(russian(224411))
}