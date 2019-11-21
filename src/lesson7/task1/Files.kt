@file:Suppress("UNUSED_PARAMETER", "ConvertCallChainIntoSequence")

package lesson7.task1

import java.io.File
import java.util.*
import kotlin.math.max
import kotlin.math.pow

/**
 * Пример
 *
 * Во входном файле с именем inputName содержится некоторый текст.
 * Вывести его в выходной файл с именем outputName, выровняв по левому краю,
 * чтобы длина каждой строки не превосходила lineLength.
 * Слова в слишком длинных строках следует переносить на следующую строку.
 * Слишком короткие строки следует дополнять словами из следующей строки.
 * Пустые строки во входном файле обозначают конец абзаца,
 * их следует сохранить и в выходном файле
 */
fun alignFile(inputName: String, lineLength: Int, outputName: String) {
    val outputStream = File(outputName).bufferedWriter()
    var currentLineLength = 0
    for (line in File(inputName).readLines()) {
        if (line.isEmpty()) {
            outputStream.newLine()
            if (currentLineLength > 0) {
                outputStream.newLine()
                currentLineLength = 0
            }
            continue
        }
        for (word in line.split(" ")) {
            if (currentLineLength > 0) {
                if (word.length + currentLineLength >= lineLength) {
                    outputStream.newLine()
                    currentLineLength = 0
                } else {
                    outputStream.write(" ")
                    currentLineLength++
                }
            }
            outputStream.write(word)
            currentLineLength += word.length
        }
    }
    outputStream.close()
}

/**
 * Средняя
 *
 * Во входном файле с именем inputName содержится некоторый текст.
 * На вход подаётся список строк substrings.
 * Вернуть ассоциативный массив с числом вхождений каждой из строк в текст.
 * Регистр букв игнорировать, то есть буквы е и Е считать одинаковыми.
 *
 */
fun countSubstrings(inputName: String, substrings: List<String>): Map<String, Int> {
    val answer = mutableMapOf<String, Int>()
    val substringsFixed = substrings.toSet()
    substringsFixed.forEach { answer[it] = 0 }
    val data = File(inputName).readLines().map { it.toLowerCase() }
    for (str in data)
        for (word in substringsFixed)
            if (str.contains(word.toLowerCase()))
                answer[word] = answer[word]!! + entries(word.toLowerCase(), str)
    return answer
}

fun entries(word: String, string: String): Int {
    var count = 0
    for (i in 0..string.length - word.length)
        count += string.substring(i).startsWith(word).toInt()
    return count
}

fun Boolean.toInt() = if (this) 1 else 0

/**
 * Средняя
 *
 * В русском языке, как правило, после букв Ж, Ч, Ш, Щ пишется И, А, У, а не Ы, Я, Ю.
 * Во входном файле с именем inputName содержится некоторый текст на русском языке.
 * Проверить текст во входном файле на соблюдение данного правила и вывести в выходной
 * файл outputName текст с исправленными ошибками.
 *
 * Регистр заменённых букв следует сохранять.
 *
 * Исключения (жюри, брошюра, парашют) в рамках данного задания обрабатывать не нужно
 *
 */

fun sibilants(inputName: String, outputName: String) {
    val outputStream = File(outputName).bufferedWriter()
    val consonants = "[ЖЧШЩжчшщ]"
    val replacements = mapOf('Ы' to 'И', 'Ю' to 'У', 'Я' to 'А', 'ы' to 'и', 'ю' to 'у', 'я' to 'а')
    val vowelsStr = "ЫЮЯыюя"
    val vowels = mutableListOf<Pair<Char, Regex>>()
    for (i in vowelsStr)
        vowels.add(Pair(i, (consonants + i.toString()).toRegex()))
    for (strIn in File(inputName).readLines()) {
        val strOut = strIn.toMutableList()
        for ((char, pattern) in vowels)
            pattern.findAll(strIn).forEach {
                strOut[it.range.last] = replacements.getValue(char)
            }
        outputStream.write(strOut.joinToString(separator = ""))
        outputStream.newLine()
    }
    outputStream.close()
}

/**
 * Средняя
 *
 * Во входном файле с именем inputName содержится некоторый текст (в том числе, и на русском языке).
 * Вывести его в выходной файл с именем outputName, выровняв по центру
 * относительно самой длинной строки.
 *
 * Выравнивание следует производить путём добавления пробелов в начало строки.
 *
 *
 * Следующие правила должны быть выполнены:
 * 1) Пробелы в начале и в конце всех строк не следует сохранять.
 * 2) В случае невозможности выравнивания строго по центру, строка должна быть сдвинута в ЛЕВУЮ сторону
 * 3) Пустые строки не являются особым случаем, их тоже следует выравнивать
 * 4) Число строк в выходном файле должно быть равно числу строк во входном (в т. ч. пустых)
 *
 */
fun centerFile(inputName: String, outputName: String) {
    val outputStream = File(outputName).bufferedWriter()
    val data = File(inputName).readLines().map { it.trim() }
    if (data.isNotEmpty()) {
        val maxLen = data.maxBy { it.length }!!.length
        for (i in data)
            outputStream.write(spaces(((maxLen - i.length) / 2.0).toInt()) + i + "\n")
    } else {
        outputStream.write("")
    }
    outputStream.close()
}

fun spaces(n: Int) = " ".repeat(n)


/**
 * Сложная
 *
 * Во входном файле с именем inputName содержится некоторый текст (в том числе, и на русском языке).
 * Вывести его в выходной файл с именем outputName, выровняв по левому и правому краю относительно
 * самой длинной строки.
 * Выравнивание производить, вставляя дополнительные пробелы между словами: равномерно по всей строке
 *
 * Слова внутри строки отделяются друг от друга одним или более пробелом.
 *
 * Следующие правила должны быть выполнены:
 * 1) Каждая строка входного и выходного файла не должна начинаться или заканчиваться пробелом.
 * 2) Пустые строки или строки из пробелов трансформируются в пустые строки без пробелов.
 * 3) Строки из одного слова выводятся без пробелов.
 * 4) Число строк в выходном файле должно быть равно числу строк во входном (в т. ч. пустых).
 *
 * Равномерность определяется следующими формальными правилами:
 * 5) Число пробелов между каждыми двумя парами соседних слов не должно отличаться более, чем на 1.
 * 6) Число пробелов между более левой парой соседних слов должно быть больше или равно числу пробелов
 *    между более правой парой соседних слов.
 *
 * Следует учесть, что входной файл может содержать последовательности из нескольких пробелов  между слвоами. Такие
 * последовательности следует учитывать при выравнивании и при необходимости избавляться от лишних пробелов.
 * Из этого следуют следующие правила:
 * 7) В самой длинной строке каждая пара соседних слов должна быть отделена В ТОЧНОСТИ одним пробелом
 * 8) Если входной файл удовлетворяет требованиям 1-7, то он должен быть в точности идентичен выходному файлу
 */
fun alignFileByWidth(inputName: String, outputName: String) {
    val outputStream = File(outputName).bufferedWriter()
    val data = File(inputName).readLines().map { it.trim() }
    if (data.isNotEmpty()) {
        val targetLen = Regex("""\s+""").replace(
            data.maxBy { it -> (it.filter { it != ' ' }.length + Regex("""\s+""").split(it).size) }!!,
            " "
        ).length
        for (string in data) {
            if (string.isEmpty() || " " !in string) {
                outputStream.write(string + "\n")
                continue
            }
            val delta = targetLen - string.filter { it != ' ' }.length
            val words = Regex("""\s+""").split(string)
            val numberOfIntervals = words.size - 1
            for (i in 0 until numberOfIntervals)
                outputStream.write(words[i] + spaces(delta / numberOfIntervals + (delta % numberOfIntervals > i).toInt()))
            outputStream.write(words.last())
            outputStream.newLine()
        }
    }
    outputStream.close()
}

/**
АЫАББ: АЫАББ: бабъч
аыабб  аыабб; аыабб: аыабб
бабъч; ВАВ; аыабб  ВАВ
ВАВ
бабъч
аыабб
БАБЪЧ - вАв / БАБЪЧ бабъч --

АЫАББ:    АЫАББ:    бабъч
аыабб аыабб; аыабб: аыабб
бабъч;   ВАВ;  аыабб  ВАВ
ВАВ
бабъч
аыабб
БАБЪЧ - вАв /БАБЪЧбабъч--

АЫАББ:      АЫАББ:     бабъч
аыабб  аыабб;  аыабб:  аыабб
бабъч;    ВАВ;   аыабб   ВАВ
ВАВ
бабъч
аыабб
БАБЪЧ - вАв / БАБЪЧ бабъч --


 */


/**
 * Средняя
 *
 * Во входном файле с именем inputName содержится некоторый текст (в том числе, и на русском языке).
 *
 * Вернуть ассоциативный массив, содержащий 20 наиболее часто встречающихся слов с их количеством.
 * Если в тексте менее 20 различных слов, вернуть все слова.
 *
 * Словом считается непрерывная последовательность из букв (кириллических,
 * либо латинских, без знаков препинания и цифр).
 * Цифры, пробелы, знаки препинания считаются разделителями слов:
 * Привет, привет42, привет!!! -привет?!
 * ^ В этой строчке слово привет встречается 4 раза.
 *
 * Регистр букв игнорировать, то есть буквы е и Е считать одинаковыми.
 * Ключи в ассоциативном массиве должны быть в нижнем регистре.
 *
 */
fun top20Words(inputName: String): Map<String, Int> {
    var count = 0
    fun inc(n: Int): Boolean {
        count += n
        return count <= 20
    }

    val data = mutableListOf<String>()
    File(inputName).readLines().forEach { i ->
        Regex("""[A-Za-zА-Яа-яёЁ]+""").findAll(i).forEach { data.add(it.value.toLowerCase()) }
    }
    val topWords = mutableMapOf<String, Int>()
    for (i in data)
        topWords[i] = topWords.getOrDefault(i, 0) + 1
    val topSorted = topWords.toList().groupBy { it.second }.toList().sortedBy { it.first }.reversed()
        .takeWhile { inc(it.second.size) }
    val top20 = mutableListOf<Pair<String, Int>>()
    topSorted.map { it.second }.forEach { top20.addAll(it) }
    return top20.toMap()
}

/**
 * Средняя
 *
 * Реализовать транслитерацию текста из входного файла в выходной файл посредством динамически задаваемых правил.

 * Во входном файле с именем inputName содержится некоторый текст (в том числе, и на русском языке).
 *
 * В ассоциативном массиве dictionary содержится словарь, в котором некоторым символам
 * ставится в соответствие строчка из символов, например
 * mapOf('з' to "zz", 'р' to "r", 'д' to "d", 'й' to "y", 'М' to "m", 'и' to "yy", '!' to "!!!")
 *
 * Необходимо вывести в итоговый файл с именем outputName
 * содержимое текста с заменой всех символов из словаря на соответствующие им строки.
 *
 * При этом регистр символов в словаре должен игнорироваться,
 * но при выводе символ в верхнем регистре отображается в строку, начинающуюся с символа в верхнем регистре.
 *
 * Пример.
 * Входной текст: Здравствуй, мир!
 *
 * заменяется на
 *
 * Выходной текст: Zzdrавствуy, mир!!!
 *
 * Пример 2.
 *
 * Входной текст: Здравствуй, мир!
 * Словарь: mapOf('з' to "zZ", 'р' to "r", 'д' to "d", 'й' to "y", 'М' to "m", 'и' to "YY", '!' to "!!!")
 *
 * заменяется на
 *
 * Выходной текст: Zzdrавствуy, mир!!!
 *
 * Обратите внимание: данная функция не имеет возвращаемого значения
 */
fun transliterate(inputName: String, dictionary: Map<Char, String>, outputName: String) {
    val dictionaryFixed = dictionary.map { Pair(it.key.toLowerCase(), it.value.toLowerCase()) }.toMap()
    val outputStream = File(outputName).bufferedWriter()
    var temp = ""
    for (char in File(inputName).readText()) {
        val chrLow = char.toLowerCase()
        if (dictionaryFixed.containsKey(chrLow)) {
            if (dictionaryFixed.getValue(chrLow).isNotEmpty())
                temp +=
                    if (char.isUpperCase())
                        dictionaryFixed.getValue(chrLow).capitalize()
                    else
                        dictionaryFixed.getValue(chrLow)
        } else {
            temp += char
        }
    }
    outputStream.write(temp)
    outputStream.close()
}

/**
 * Средняя
 *
 * Во входном файле с именем inputName имеется словарь с одним словом в каждой строчке.
 * Выбрать из данного словаря наиболее длинное слово,
 * в котором все буквы разные, например: Неряшливость, Четырёхдюймовка.
 * Вывести его в выходной файл с именем outputName.
 * Если во входном файле имеется несколько слов с одинаковой длиной, в которых все буквы разные,
 * в выходной файл следует вывести их все через запятую.
 * Регистр букв игнорировать, то есть буквы е и Е считать одинаковыми.
 *
 * Пример входного файла:
 * Карминовый
 * Боязливый
 * Некрасивый
 * Остроумный
 * БелогЛазый
 * ФиолетОвый

 * Соответствующий выходной файл:
 * Карминовый, Некрасивый
 *
 * Обратите внимание: данная функция не имеет возвращаемого значения
 */
fun chooseLongestChaoticWord(inputName: String, outputName: String) {
    val output = File(outputName).bufferedWriter()
    val data = File(inputName).readLines()
    val dataFiltered = mutableListOf<String>()
    data.forEach { if (it.length == it.toLowerCase().toSet().size) dataFiltered.add(it) }
    dataFiltered.sortBy { it.length }
    val dataGroup = dataFiltered.groupBy { it.length }
    if (dataGroup.isNotEmpty())
        output.write(dataGroup[dataGroup.keys.max()]!!.joinToString(separator = ", "))
    output.close()
}

/**
 * Сложная
 *
 * Реализовать транслитерацию текста в заданном формате разметки в формат разметки HTML.
 *
 * Во входном файле с именем inputName содержится текст, содержащий в себе элементы текстовой разметки следующих типов:
 * - *текст в курсивном начертании* -- курсив
 * - **текст в полужирном начертании** -- полужирный
 * - ~~зачёркнутый текст~~ -- зачёркивание
 *
 * Следует вывести в выходной файл этот же текст в формате HTML:
 * - <i>текст в курсивном начертании</i>
 * - <b>текст в полужирном начертании</b>
 * - <s>зачёркнутый текст</s>
 *
 * Кроме того, все абзацы исходного текста, отделённые друг от друга пустыми строками, следует обернуть в теги <p>...</p>,
 * а весь текст целиком в теги <html><body>...</body></html>.
 *
 * Все остальные части исходного текста должны остаться неизменными с точностью до наборов пробелов и переносов строк.
 * Отдельно следует заметить, что открывающая последовательность из трёх звёздочек (***) должна трактоваться как "<b><i>"
 * и никак иначе.
 *
 * При решении этой и двух следующих задач полезно прочитать статью Википедии "Стек".
 *
 * Пример входного файла:
Lorem ipsum *dolor sit amet*, consectetur **adipiscing** elit.
Vestibulum lobortis, ~~Est vehicula rutrum *suscipit*~~, ipsum ~~lib~~ero *placerat **tortor***,

Suspendisse ~~et elit in enim tempus iaculis~~.
 *
 * Соответствующий выходной файл:
<html>
<body>
<p>
Lorem ipsum <i>dolor sit amet</i>, consectetur <b>adipiscing</b> elit.
Vestibulum lobortis. <s>Est vehicula rutrum <i>suscipit</i></s>, ipsum <s>lib</s>ero <i>placerat <b>tortor</b></i>.
</p>
<p>
Suspendisse <s>et elit in enim tempus iaculis</s>.
</p>
</body>
</html>
 *
 * (Отступы и переносы строк в примере добавлены для наглядности, при решении задачи их реализовывать не обязательно)
 */

fun markdownToHtmlSimple(inputName: String, outputName: String) {
    val output = File(outputName).bufferedWriter()
    val strings = File(inputName).readLines().toMutableList()
    val stack = ArrayDeque<String>()
    val equalities = mapOf("*" to Pair("<i>", "</i>"), "**" to Pair("<b>", "</b>"), "~~" to Pair("<s>", "</s>"))
    val pattern = """([^*~])+""".toRegex()
    val data = mutableListOf<String>()
    val dataConverted = mutableListOf<String>()
    for (string in strings)
        pattern.split(string).filter { it.isNotEmpty() }
            .forEach { if ("~" in it) data.addAll(split(it)) else data.add(it) }
    var index = 0
    val temp = data.toMutableList()
    while (index < temp.size) {
        if (temp[index] == stack.peek()) {
            dataConverted.add(equalities.getValue(temp[index]).second)
            stack.pop()
        } else {
            if (temp[index] == "***") {
                if ("*" in stack.firstOrNull().toString()) {
                    if (stack.peek() == "*") {
                        stack.pop()
                        dataConverted.add(equalities.getValue("*").second)
                        temp.add(index + 1, "**")
                    } else {
                        stack.pop()
                        dataConverted.add(equalities.getValue("**").second)
                        temp.add(index + 1, "*")
                    }
                } else {
                    stack.push("**")
                    stack.push("*")
                    dataConverted.add(equalities.getValue("**").first)
                    dataConverted.add(equalities.getValue("*").first)
                }
            } else {
                stack.push(temp[index])
                dataConverted.add(equalities.getValue(temp[index]).first)
            }
        }
        index += 1
    }
    index = 0
    var offset = 0
    for ((i, value) in data.withIndex()) {
        while (index < strings.size) {
            if (strings[index].contains(value)) {
                strings[index] = if (value == "***") {
                    offset += 1
                    strings[index].replaceFirst(value, dataConverted[i + offset - 1] + dataConverted[i + offset])
                } else {
                    strings[index].replaceFirst(value, dataConverted[i + offset])
                }
                break
            } else {
                index += 1
            }
        }
    }
    output.write("<html>\n")
    output.write("   <body>\n")
    if (strings[0] != "")
        output.write("      <p>\n")
    var paragraph = strings[0] != ""
    for (i in strings)
        if (i == "") {
            if (paragraph) {
                output.write("      </p>\n")
                paragraph = false
            }
        } else {
            if (!paragraph) {
                output.write("      <p>\n")
                paragraph = true
            }
            output.write("         $i\n")
        }
    output.write("      </p>\n")
    output.write("   </body>\n")
    output.write("</html>\n")
    output.close()
}

fun split(string: String): List<String> {
    val result = mutableListOf<String>()
    var temp = string[0].toString()
    for (i in 1 until string.length) {
        if (string[i] == string[i - 1]) {
            temp += string[i]
        } else {
            result.add(temp)
            temp = string[i].toString()
        }
    }
    result.add(temp)
    var index = 0
    while (index < result.size) {
        if ("~" in result[index] && result[index].length > 2) {
            result.addAll(index + 1, List(result[index].length / 2) { "~~" })
            result.removeAt(index)
        }
        index += 1
    }
    return result
}


/**
 * Сложная
 *
 * Реализовать транслитерацию текста в заданном формате разметки в формат разметки HTML.
 *
 * Во входном файле с именем inputName содержится текст, содержащий в себе набор вложенных друг в друга списков.
 * Списки бывают двух типов: нумерованные и ненумерованные.
 *
 * Каждый элемент ненумерованного списка начинается с новой строки и символа '*', каждый элемент нумерованного списка --
 * с новой строки, числа и точки. Каждый элемент вложенного списка начинается с отступа из пробелов, на 4 пробела большего,
 * чем список-родитель. Максимально глубина вложенности списков может достигать 6. "Верхние" списки файла начинются
 * прямо с начала строки.
 *
 * Следует вывести этот же текст в выходной файл в формате HTML:
 * Нумерованный список:
 * <ol>
 *     <li>Раз</li>
 *     <li>Два</li>
 *     <li>Три</li>
 * </ol>
 *
 * Ненумерованный список:
 * <ul>
 *     <li>Раз</li>
 *     <li>Два</li>
 *     <li>Три</li>
 * </ul>
 *
 * Кроме того, весь текст целиком следует обернуть в теги <html><body>...</body></html>
 *
 * Все остальные части исходного текста должны остаться неизменными с точностью до наборов пробелов и переносов строк.
 *
 * Пример входного файла:
///////////////////////////////начало файла/////////////////////////////////////////////////////////////////////////////
 * Утка по-пекински
 * Утка
 * Соус
 * Салат Оливье
1. Мясо
 * Или колбаса
2. Майонез
3. Картофель
4. Что-то там ещё
 * Помидоры
 * Фрукты
1. Бананы
23. Яблоки
1. Красные
2. Зелёные
///////////////////////////////конец файла//////////////////////////////////////////////////////////////////////////////
 *
 *
 * Соответствующий выходной файл:
///////////////////////////////начало файла/////////////////////////////////////////////////////////////////////////////
<html>
<body>
<ul>
<li>
Утка по-пекински
<ul>
<li>Утка</li>
<li>Соус</li>
</ul>
</li>
<li>
Салат Оливье
<ol>
<li>Мясо
<ul>
<li>
Или колбаса
</li>
</ul>
</li>
<li>Майонез</li>
<li>Картофель</li>
<li>Что-то там ещё</li>
</ol>
</li>
<li>Помидоры</li>
<li>
Фрукты
<ol>
<li>Бананы</li>
<li>
Яблоки
<ol>
<li>Красные</li>
<li>Зелёные</li>
</ol>
</li>
</ol>
</li>
</ul>
</body>
</html>
///////////////////////////////конец файла//////////////////////////////////////////////////////////////////////////////
 * (Отступы и переносы строк в примере добавлены для наглядности, при решении задачи их реализовывать не обязательно)
 */
fun markdownToHtmlLists(inputName: String = "", outputName: String = "") {
    TODO()
}

/**
 * Очень сложная
 *
 * Реализовать преобразования из двух предыдущих задач одновременно над одним и тем же файлом.
 * Следует помнить, что:
 * - Списки, отделённые друг от друга пустой строкой, являются разными и должны оказаться в разных параграфах выходного файла.
 *
 */
fun markdownToHtml(inputName: String, outputName: String) {
    TODO()
}

/**
 * Средняя
 *
 * Вывести в выходной файл процесс умножения столбиком числа lhv (> 0) на число rhv (> 0).
 *
 * Пример (для lhv == 19935, rhv == 111):
19935
 *    111
--------
19935
+ 19935
+19935
--------
2212785
 * Используемые пробелы, отступы и дефисы должны в точности соответствовать примеру.
 * Нули в множителе обрабатывать так же, как и остальные цифры:
235
 *  10
-----
0
+235
-----
2350
 *
 */
fun printMultiplicationProcess(lhv: Int, rhv: Int, outputName: String) {
    val output = File(outputName).bufferedWriter()
    val len = max((lhv * rhv).toString().length, rhv.toString().length) + 1
    output.write("%${len}s\n".format(lhv))
    output.write("*%${len - 1}s\n".format(rhv))
    output.write("-".repeat(len) + "\n")
    val data = rhv.toString().toList().map { it.toString().toInt() }.reversed()
    var plus = false
    for (i in len downTo len - rhv.toString().length + 1)
        if (plus) {
            output.write("+%${i - 1}s\n".format(lhv * data[len - i]))
        } else {
            plus = true
            output.write("%${i}s\n".format(lhv * data[len - i]))
        }
    output.write("-".repeat(len) + "\n")
    output.write("%${len}s\n".format(rhv * lhv))
    output.close()
}

/**
 * Сложная
 *
 * Вывести в выходной файл процесс деления столбиком числа lhv (> 0) на число rhv (> 0).
 *
 * Пример (для lhv == 19935, rhv == 22):
19935 | 22
-198     906
----
13
-0
--
135
-132
----
3

 * Используемые пробелы, отступы и дефисы должны в точности соответствовать примеру.
 *
 */
fun printDivisionProcess(lhv: Int, rhv: Int, outputName: String) {
    val output = File(outputName).bufferedWriter()
    var subDiv = findSubDividend(lhv, rhv)
    var first = true
    var indent = len(subDiv)
    var oldPos = 0
    if (len(subDiv) == len(subDiv / rhv * rhv))
        output.write(" ")
    output.write("$lhv | $rhv\n")
    for (str in lhv.toString().split("").subList(len(subDiv) + 1, lhv.toString().lastIndex + 3)) {
        val item = subDiv / rhv
        output.write("%${indent}s".format("-${item * rhv}"))
        if (first)
            output.write(" ".repeat(len(lhv) - len(subDiv) + 3) + (lhv / rhv).toString())
        output.newLine()
        val n = indent - oldPos + (constrain(len(subDiv), 2 - first.toInt()) == len(item * rhv)).toInt()
        output.write("%${indent}s".format("-".repeat(n)) + "\n")
        if (first) {
            first = false
            indent = len(item * rhv) + 2
        } else {
            indent += 1
        }
        if (str != "") {
            output.write("%${indent}s".format((subDiv - item * rhv).toString() + str) + "\n")
            oldPos = indent - len(subDiv - item * rhv) - 1
            subDiv = (subDiv - item * rhv) * 10 + str.toInt()
        } else {
            output.write("%${indent - 1}s".format((subDiv - item * rhv)))
        }
    }
    output.close()
}

fun len(a: Int): Int = a.toString().length

fun pow(a: Int, b: Int): Int = a.toDouble().pow(b).toInt()

fun findSubDividend(a: Int, b: Int): Int {
    var divider = pow(10, a.toString().length - 1)
    while (a / divider < b && divider != 1)
        divider /= 10
    return a / divider
}

fun constrain(num: Int, min: Int) = if (num < min) min else num
