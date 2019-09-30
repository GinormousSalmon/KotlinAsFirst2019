@file:Suppress("UNUSED_PARAMETER", "ConvertCallChainIntoSequence")

package lesson6.task1

import lesson2.task2.daysInMonth
import java.lang.IllegalArgumentException
import java.lang.NumberFormatException
import java.util.*
import kotlin.*

/**
 * Пример
 *
 * Время представлено строкой вида "11:34:45", содержащей часы, минуты и секунды, разделённые двоеточием.
 * Разобрать эту строку и рассчитать количество секунд, прошедшее с начала дня.
 */
fun timeStrToSeconds(str: String): Int {
    val parts = str.split(":")
    var result = 0
    for (part in parts) {
        val number = part.toInt()
        result = result * 60 + number
    }
    return result
}

/**
 * Пример
 *
 * Дано число n от 0 до 99.
 * Вернуть его же в виде двухсимвольной строки, от "00" до "99"
 */
fun twoDigitStr(n: Int) = if (n in 0..9) "0$n" else "$n"

/**
 * Пример
 *
 * Дано seconds -- время в секундах, прошедшее с начала дня.
 * Вернуть текущее время в виде строки в формате "ЧЧ:ММ:СС".
 */
fun timeSecondsToStr(seconds: Int): String {
    val hour = seconds / 3600
    val minute = (seconds % 3600) / 60
    val second = seconds % 60
    return String.format("%02d:%02d:%02d", hour, minute, second)
}

/**
 * Пример: консольный ввод
 */
fun main() {
    println("Введите время в формате ЧЧ:ММ:СС")
    val line = readLine()
    if (line != null) {
        val seconds = timeStrToSeconds(line)
        if (seconds == -1) {
            println("Введённая строка $line не соответствует формату ЧЧ:ММ:СС")
        } else {
            println("Прошло секунд с начала суток: $seconds")
        }
    } else {
        println("Достигнут <конец файла> в процессе чтения строки. Программа прервана")
    }
}


/**
 * Средняя
 *
 * Дата представлена строкой вида "15 июля 2016".
 * Перевести её в цифровой формат "15.07.2016".
 * День и месяц всегда представлять двумя цифрами, например: 03.04.2011.
 * При неверном формате входной строки вернуть пустую строку.
 *
 * Обратите внимание: некорректная с точки зрения календаря дата (например, 30.02.2009) считается неверными
 * входными данными.
 */
fun dateStrToDigit(str: String): String {
    try {
        val splitted = str.split(" ")
        if (splitted.size != 3)
            return ""
        val day = splitted[0].toInt()
        val month = when (splitted[1]) {
            "января" -> "01"
            "февраля" -> "02"
            "марта" -> "03"
            "апреля" -> "04"
            "мая" -> "05"
            "июня" -> "06"
            "июля" -> "07"
            "августа" -> "08"
            "сентября" -> "09"
            "октября" -> "10"
            "ноября" -> "11"
            "декабря" -> "12"
            else -> return ""
        }
        val year = splitted[2]

        if (day > daysInMonth(month.toInt(), year.toInt()))
            return ""
        return "${twoDigitStr(day)}.$month.$year"
    } catch (e: NumberFormatException) {
        return ""
    }
}

/**
 * Средняя
 *
 * Дата представлена строкой вида "15.07.2016".
 * Перевести её в строковый формат вида "15 июля 2016".
 * При неверном формате входной строки вернуть пустую строку
 *
 * Обратите внимание: некорректная с точки зрения календаря дата (например, 30 февраля 2009) считается неверными
 * входными данными.
 */
fun dateDigitToStr(digital: String): String {
    try {
        val splitted = digital.split(".")
        if (splitted.size != 3)
            return ""
        val day = splitted[0].toInt()
        val month = when (splitted[1].toInt()) {
            1 -> "января"
            2 -> "февраля"
            3 -> "марта"
            4 -> "апреля"
            5 -> "мая"
            6 -> "июня"
            7 -> "июля"
            8 -> "августа"
            9 -> "сентября"
            10 -> "октября"
            11 -> "ноября"
            12 -> "декабря"
            else -> return ""
        }
        val year = splitted[2].toInt()
        if (day > daysInMonth(splitted[1].toInt(), year))
            return ""
        return "$day $month $year"
    } catch (e: NumberFormatException) {
        return ""
    }
}

/**
 * Средняя
 *
 * Номер телефона задан строкой вида "+7 (921) 123-45-67".
 * Префикс (+7) может отсутствовать, код города (в скобках) также может отсутствовать.
 * Может присутствовать неограниченное количество пробелов и чёрточек,
 * например, номер 12 --  34- 5 -- 67 -89 тоже следует считать легальным.
 * Перевести номер в формат без скобок, пробелов и чёрточек (но с +), например,
 * "+79211234567" или "123456789" для приведённых примеров.
 * Все символы в номере, кроме цифр, пробелов и +-(), считать недопустимыми.
 * При неверном формате вернуть пустую строку.
 *
 * PS: Дополнительные примеры работы функции можно посмотреть в соответствующих тестах.
 */
fun flattenPhoneNumber(phone: String): String {
//    val input = phone.filter { it !in "- " }
//    return if (Regex("""\d+\(\d+\)\d+""").matches(input) ||
//        Regex("""\+\d+\(\d+\)\d+""").matches(input) ||
//        Regex("""\d+""").matches(input) ||
//        Regex("""\+\d+""").matches(input)
//    )
//        input.filter { it in "1234567890+" }
//    else
//        ""
    val input = phone.filter { it !in "- " }
    return if (Regex("""\+*\d+\(\d+\)\d+""").matches(input) ||
        Regex("""\+*\d+""").matches(input)
    )
        input.filter { it in "1234567890+" }
    else
        ""
}

/**
 * Средняя
 *
 * Результаты спортсмена на соревнованиях в прыжках в длину представлены строкой вида
 * "706 - % 717 % 703".
 * В строке могут присутствовать числа, черточки - и знаки процента %, разделённые пробелами;
 * число соответствует удачному прыжку, - пропущенной попытке, % заступу.
 * Прочитать строку и вернуть максимальное присутствующее в ней число (717 в примере).
 * При нарушении формата входной строки или при отсутствии в ней чисел, вернуть -1.
 */
fun bestLongJump(jumps: String): Int {
    val splitted = jumps.split(" ")
    val nums = mutableListOf<Int>()
    print("12".contains("12"))
    for (i in splitted) {
        try {
            nums.add(i.toInt())
        } catch (e: NumberFormatException) {
            if (i != "%" && i != "-")
                return -1
        }
    }
    return if (nums.isNotEmpty())
        nums.max()!!
    else
        -1
}

/**
 * Сложная
 *
 * Результаты спортсмена на соревнованиях в прыжках в высоту представлены строкой вида
 * "220 + 224 %+ 228 %- 230 + 232 %%- 234 %".
 * Здесь + соответствует удачной попытке, % неудачной, - пропущенной.
 * Высота и соответствующие ей попытки разделяются пробелом.
 * Прочитать строку и вернуть максимальную взятую высоту (230 в примере).
 * При нарушении формата входной строки, а также в случае отсутствия удачных попыток,
 * вернуть -1.
 */
fun bestHighJump(jumps: String): Int {
    val overcome = mutableListOf<Int>()
    val stack = Stack<String>()
    val data = mutableListOf<String>()
    for (i in jumps.split(" "))
        try {
            i.toInt()
            data.add(i)
        } catch (e: NumberFormatException) {
            for (j in i.split(""))
                if (j.isNotEmpty())
                    if (j != "%" && j != "+" && j != "-")
                        return -1
                    else data.add(j)
        }
    for (i in data) {
        if (stack.isEmpty())
            if (isNumber(i)) {
                stack.push(i)
                continue
            } else return -1
        if (isNumber(i))
            when (stack.peek()) {
                "%" -> return -1
                "-" -> stack.push(i)
                "+" -> stack.push(i)
                else -> return -1
            }
        if (i == "%" || i == "+")
            when (stack.peek()) {
                "%" -> stack.push(i)
                "-" -> return -1
                "+" -> return -1
                else -> stack.push(i)
            }
        if (i == "-")
            when (stack.peek()) {
                "%" -> stack.push(i)
                "-" -> return -1
                "+" -> return -1
                else -> return -1
            }
    }
    var old = 0
    for (i in data) {
        if (isNumber(i))
            old = i.toInt()
        if (i == "+")
            overcome.add(old)
    }
    return if (overcome.isEmpty())
        -1
    else
        overcome.max()!!
}


fun isNumber(data: String): Boolean {
    if (data.isEmpty())
        return false
    else if (data[0] == '+' || data[0] == '-')
        return false
    try {
        data.toInt()
    } catch (e: NumberFormatException) {
        return false
    }
    return true
}

/**
 * Сложная
 *
 * В строке представлено выражение вида "2 + 31 - 40 + 13",
 * использующее целые положительные числа, плюсы и минусы, разделённые пробелами.
 * Наличие двух знаков подряд "13 + + 10" или двух чисел подряд "1 2" не допускается.
 * Вернуть значение выражения (6 для примера).
 * Про нарушении формата входной строки бросить исключение IllegalArgumentException
 */
fun plusMinus(expression: String): Int {
    if (Regex("""\d+((\s[-+]\s\d+)+)*""").matches(expression) ||
        Regex("""-\s\d+((\s[-+]\s\d+)+)*""").matches(expression)
    ) {
        var sign = "+"
        var answer = 0
        for (i in expression.split(" "))
            if (isNumber(i))
                answer += i.toInt() * if (sign == "+") 1 else -1
            else sign = i
        return answer
    } else throw IllegalArgumentException()
}

/**
 * Сложная
 *
 * Строка состоит из набора слов, отделённых друг от друга одним пробелом.
 * Определить, имеются ли в строке повторяющиеся слова, идущие друг за другом.
 * Слова, отличающиеся только регистром, считать совпадающими.
 * Вернуть индекс начала первого повторяющегося слова, или -1, если повторов нет.
 * Пример: "Он пошёл в в школу" => результат 9 (индекс первого 'в')
 */
fun firstDuplicateIndex(str: String): Int {
    val data = str.split(" ")
    var index = 0
    for (i in 1 until data.size) {
        index = str.indexOf(data[i - 1], startIndex = index)
        if (data[i - 1].toLowerCase() == data[i].toLowerCase())
            return index
    }
    return -1
}

/**
 * Сложная
 *
 * Строка содержит названия товаров и цены на них в формате вида
 * "Хлеб 39.9; Молоко 62; Курица 184.0; Конфеты 89.9".
 * То есть, название товара отделено от цены пробелом,
 * а цена отделена от названия следующего товара точкой с запятой и пробелом.
 * Вернуть название самого дорогого товара в списке (в примере это Курица),
 * или пустую строку при нарушении формата строки.
 * Все цены должны быть больше либо равны нуля.
 */
fun mostExpensive(description: String): String {
    val positions = description.split("; ")
    var max = Pair<String, Double>("", -1.0)
    for (i in positions) {
        val data = i.split(" ")
        if (data.size != 2)
            return ""
        try {
            val cost = data[1].toDouble()
            if (cost > max.second)
                max = Pair(data[0], cost)
        } catch (e: NumberFormatException) {
            return ""
        }
    }
    return max.first
}

/**
 * Сложная
 *
 * Перевести число roman, заданное в римской системе счисления,
 * в десятичную систему и вернуть как результат.
 * Римские цифры: 1 = I, 4 = IV, 5 = V, 9 = IX, 10 = X, 40 = XL, 50 = L,
 * 90 = XC, 100 = C, 400 = CD, 500 = D, 900 = CM, 1000 = M.
 * Например: XXIII = 23, XLIV = 44, C = 100
 *
 * Вернуть -1, если roman не является корректным римским числом
 */
fun fromRoman(roman: String): Int {
    var str = roman
    var answer = 0
    if (str.isEmpty())
        return -1
    for (i in str) {
        if (i !in "IVXLCDM")
            return -1
    }
    for ((key, value) in listOf("CM" to 900, "CD" to 400, "XC" to 90, "XL" to 40, "IX" to 9, "IV" to 4))
        if (str.contains(key)) {
            str = remove(str, key)
            answer += value
        }
    for (i in str)
        when (i) {
            'I' -> answer += 1
            'V' -> answer += 5
            'X' -> answer += 10
            'L' -> answer += 50
            'C' -> answer += 100
            'D' -> answer += 500
            'M' -> answer += 1000
        }
    return answer
}

fun remove(str: String, delete: String): String {
    var answer = ""
    if (str.contains(delete)) {
        val index = str.indexOf(delete)
        for (i in 0 until index)
            answer += str[i]
        for (i in index + delete.length until str.length)
            answer += str[i]
        return answer
    }
    return str
}

/**
 * Очень сложная
 *
 * Имеется специальное устройство, представляющее собой
 * конвейер из cells ячеек (нумеруются от 0 до cells - 1 слева направо) и датчик, двигающийся над этим конвейером.
 * Строка commands содержит последовательность команд, выполняемых данным устройством, например +>+>+>+>+
 * Каждая команда кодируется одним специальным символом:
 *	> - сдвиг датчика вправо на 1 ячейку;
 *  < - сдвиг датчика влево на 1 ячейку;
 *	+ - увеличение значения в ячейке под датчиком на 1 ед.;
 *	- - уменьшение значения в ячейке под датчиком на 1 ед.;
 *	[ - если значение под датчиком равно 0, в качестве следующей команды следует воспринимать
 *  	не следующую по порядку, а идущую за соответствующей следующей командой ']' (с учётом вложенности);
 *	] - если значение под датчиком не равно 0, в качестве следующей команды следует воспринимать
 *  	не следующую по порядку, а идущую за соответствующей предыдущей командой '[' (с учётом вложенности);
 *      (комбинация [] имитирует цикл)
 *  пробел - пустая команда
 *
 * Изначально все ячейки заполнены значением 0 и датчик стоит на ячейке с номером N/2 (округлять вниз)
 *
 * После выполнения limit команд или всех команд из commands следует прекратить выполнение последовательности команд.
 * Учитываются все команды, в том числе несостоявшиеся переходы ("[" при значении под датчиком не равном 0 и "]" при
 * значении под датчиком равном 0) и пробелы.
 *
 * Вернуть список размера cells, содержащий элементы ячеек устройства после завершения выполнения последовательности.
 * Например, для 10 ячеек и командной строки +>+>+>+>+ результат должен быть 0,0,0,0,0,1,1,1,1,1
 *
 * Все прочие символы следует считать ошибочными и формировать исключение IllegalArgumentException.
 * То же исключение формируется, если у символов [ ] не оказывается пары.
 * Выход за границу конвейера также следует считать ошибкой и формировать исключение IllegalStateException.
 * Считать, что ошибочные символы и непарные скобки являются более приоритетной ошибкой чем выход за границу ленты,
 * то есть если в программе присутствует некорректный символ или непарная скобка, то должно быть выброшено
 * IllegalArgumentException.
 * IllegalArgumentException должен бросаться даже если ошибочная команда не была достигнута в ходе выполнения.
 *
 */
fun computeDeviceCells(cells: Int, commands: String, limit: Int): List<Int> = TODO()
