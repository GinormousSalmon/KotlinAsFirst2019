@file:Suppress("UNUSED_PARAMETER", "ConvertCallChainIntoSequence")

package lesson4.task1

import lesson1.task1.discriminant
import lesson3.task1.minDivisor
import kotlin.math.pow
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
    if (list.isNotEmpty()) {
        var average = 0.0
        for (elem in list)
            average += elem
        average / list.size
    } else
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


fun pow(a: Int, n: Int): Int = a.toDouble().pow(n).toInt()

/**
 * Средняя
 *
 * Рассчитать значение многочлена при заданном x:
 * p(x) = p0 + p1*x + p2*x^2 + p3*x^3 + ... + pN*x^N.
 * Коэффициенты многочлена заданы списком p: (p0, p1, p2, p3, ..., pN).
 * Значение пустого многочлена равно 0 при любом x.
 */
fun polynom(p: List<Int>, x: Int): Int {
    if (p.isEmpty())
        return 0
    var sum = 0
    for (i in p.indices)
        sum += p[i] * pow(x, i)
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
    var accum = 0
    for (i in 0 until list.size) {
        list[i] += accum
        accum = list[i]
    }
    return list
}

/**
 * Средняя
 *
 * Разложить заданное натуральное число n > 1 на простые множители.
 * Результат разложения вернуть в виде списка множителей, например 75 -> (3, 5, 5).
 * Множители в списке должны располагаться по возрастанию.
 */
fun factorize(n: Int): List<Int> {
    var num = n
    val list = mutableListOf<Int>()
    do {
        val div = minDivisor(num)
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
fun decimal(digits: List<Int>, base: Int): Int {
    var dec = 0
    val len = digits.size
    for (i in digits.indices)
        dec += digits[i] * pow(base, len - i - 1)
    return dec
}

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
    var item = n / 1000
    for (i in 0 until item)
        answer += 'M'
    item = (n / 100) % 10
    answer += when (item) {
        9 -> "CM"
        8 -> "DCCC"
        7 -> "DCC"
        6 -> "DC"
        5 -> "D"
        4 -> "CD"
        3 -> "CCC"
        2 -> "CC"
        1 -> "C"
        else -> ""
    }
    item = (n / 10) % 10
    answer += when (item) {
        9 -> "XC"
        8 -> "LXXX"
        7 -> "LXX"
        6 -> "LX"
        5 -> "L"
        4 -> "XL"
        3 -> "XXX"
        2 -> "XX"
        1 -> "X"
        else -> ""
    }
    item = n % 10
    answer += when (item) {
        9 -> "IX"
        8 -> "VIII"
        7 -> "VII"
        6 -> "VI"
        5 -> "V"
        4 -> "IV"
        3 -> "III"
        2 -> "II"
        1 -> "I"
        else -> ""
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
fun russian(n: Int): String {
    var answer = when (n / 100000) {    //сотни тысяч
        1 -> "сто "
        2 -> "двести "
        3 -> "триста "
        4 -> "четыреста "
        5 -> "пятьсот "
        6 -> "шестьсот "
        7 -> "семьсот "
        8 -> "восемьсот "
        9 -> "девятьсот "
        else -> ""
    }
    if ((n % 100000) / 1000 in 11..19)
        answer += when ((n % 100000) / 1000) {
            11 -> "одиннадцать тысяч"
            12 -> "двенадцать тысяч"
            13 -> "тринадцать тысяч"
            14 -> "четырнадцать тысяч"
            15 -> "пятнадцать тысяч"
            16 -> "шестнадцать тысяч"
            17 -> "семнадцать тысяч"
            18 -> "восемнадцать тысяч"
            19 -> "девятнадцать тысяч"
            else -> ""
        } + if (n % 1000 > 0) " " else ""
    else {
        answer += when ((n % 100000) / 10000) {    //десятки тысяч
            1 -> "десять "
            2 -> "двадцать "
            3 -> "тридцать "
            4 -> "сорок "
            5 -> "пятьдесят "
            6 -> "шестьдесят "
            7 -> "семьдесят "
            8 -> "восемьдесят "
            9 -> "девяносто "
            else -> ""
        }
        answer += when ((n % 10000) / 1000) {    //тысячи
            1 -> "одна тысяча"
            2 -> "две тысячи"
            3 -> "три тысячи"
            4 -> "четыре тысячи"
            5 -> "пять тысяч"
            6 -> "шесть тысяч"
            7 -> "семь тысяч"
            8 -> "восемь тысяч"
            9 -> "девять тысяч"
            else -> if (answer.isNotEmpty()) "тысяч" else ""
        }
        answer += if (n % 1000 > 0 && answer.isNotEmpty()) " " else ""
    }
    answer += when ((n % 1000) / 100) {     //сотни
        1 -> "сто"
        2 -> "двести"
        3 -> "триста"
        4 -> "четыреста"
        5 -> "пятьсот"
        6 -> "шестьсот"
        7 -> "семьсот"
        8 -> "восемьсот"
        9 -> "девятьсот"
        else -> ""
    } + if (n % 100 > 0 && (n % 1000) / 100 != 0) " " else ""
    if (n % 100 in 11..19)
        answer += when (n % 100) {
            11 -> "одиннадцать"
            12 -> "двенадцать"
            13 -> "тринадцать"
            14 -> "четырнадцать"
            15 -> "пятнадцать"
            16 -> "шестнадцать"
            17 -> "семнадцать"
            18 -> "восемнадцать"
            19 -> "девятнадцать"
            else -> ""
        }
    else {
        answer += when ((n % 100) / 10) {    //десятки
            1 -> "десять"
            2 -> "двадцать"
            3 -> "тридцать"
            4 -> "сорок"
            5 -> "пятьдесят"
            6 -> "шестьдесят"
            7 -> "семьдесят"
            8 -> "восемьдесят"
            9 -> "девяносто"
            else -> ""
        } + if (n % 10 > 0 && (n % 100) / 10 != 0) " " else ""
        answer += when (n % 10) {    //единицы
            1 -> "один"
            2 -> "два"
            3 -> "три"
            4 -> "четыре"
            5 -> "пять"
            6 -> "шесть"
            7 -> "семь"
            8 -> "восемь"
            9 -> "девять"
            else -> ""
        }
    }
    return answer
}
