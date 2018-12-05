@file:Suppress("UNUSED_PARAMETER", "ConvertCallChainIntoSequence")

package lesson5.task1

import java.util.*

/**
 * Пример
 *
 * Для заданного списка покупок `shoppingList` посчитать его общую стоимость
 * на основе цен из `costs`. В случае неизвестной цены считать, что товар
 * игнорируется.
 */
fun shoppingListCost(
        shoppingList: List<String>,
        costs: Map<String, Double>): Double {
    var totalCost = 0.0

    for (item in shoppingList) {
        val itemCost = costs[item]
        if (itemCost != null) {
            totalCost += itemCost
        }
    }

    return totalCost
}

/**
 * Пример
 *
 * Для набора "имя"-"номер телефона" `phoneBook` оставить только такие пары,
 * для которых телефон начинается с заданного кода страны `countryCode`
 */
fun filterByCountryCode(
        phoneBook: MutableMap<String, String>,
        countryCode: String) {
    val namesToRemove = mutableListOf<String>()

    for ((name, phone) in phoneBook) {
        if (!phone.startsWith(countryCode)) {
            namesToRemove.add(name)
        }
    }

    for (name in namesToRemove) {
        phoneBook.remove(name)
    }
}

/**
 * Пример
 *
 * Для заданного текста `text` убрать заданные слова-паразиты `fillerWords`
 * и вернуть отфильтрованный текст
 */
fun removeFillerWords(
        text: List<String>,
        vararg fillerWords: String): List<String> {
    val fillerWordSet = setOf(*fillerWords)

    val res = mutableListOf<String>()
    for (word in text) {
        if (word !in fillerWordSet) {
            res += word
        }
    }
    return res
}

/**
 * Пример
 *
 * Для заданного текста `text` построить множество встречающихся в нем слов
 */
fun buildWordSet(text: List<String>): MutableSet<String> {
    val res = mutableSetOf<String>()
    for (word in text) res.add(word)
    return res
}

/**
 * Средняя
 *
 * Объединить два ассоциативных массива `mapA` и `mapB` с парами
 * "имя"-"номер телефона" в итоговый ассоциативный массив, склеивая
 * значения для повторяющихся ключей через запятую.
 * В случае повторяющихся *ключей* значение из mapA должно быть
 * перед значением из mapB.
 *
 * Повторяющиеся *значения* следует добавлять только один раз.
 *
 * Например:
 *   mergePhoneBooks(
 *     mapOf("Emergency" to "112", "Police" to "02"),
 *     mapOf("Emergency" to "911", "Police" to "02")
 *   ) -> mapOf("Emergency" to "112, 911", "Police" to "02")
 */
fun mergePhoneBooks(mapA: Map<String, String>, mapB: Map<String, String>): Map<String, String> {
    val outputMap: MutableMap<String, String> = mapA.toMutableMap()
    mapB.forEach { (t, u) ->
        val v = outputMap.getOrPut(t) { u }
        if (v != u && mapA[t] != u) {
            outputMap[t] = outputMap[t] + ", " + u
        }
    }

    return outputMap
}

/**
 * Простая
 *
 * По заданному ассоциативному массиву "студент"-"оценка за экзамен" построить
 * обратный массив "оценка за экзамен"-"список студентов с этой оценкой".
 *
 * Например:
 *   buildGrades(mapOf("Марат" to 3, "Семён" to 5, "Михаил" to 5))
 *     -> mapOf(5 to listOf("Семён", "Михаил"), 3 to listOf("Марат"))
 */
fun buildGrades(grades: Map<String, Int>): Map<Int, List<String>> {
    val outputMap: MutableMap<Int, MutableList<String>> = mutableMapOf()
    grades.forEach { (t, u) ->
        val v = outputMap.getOrPut(u) { mutableListOf(t) }
        if (!v.contains(t)) {
            outputMap[u]!!.add(t)
        }
    }

    outputMap.forEach { _, u -> u.sortDescending() }
    return outputMap
}

/**
 * Простая
 *
 * Определить, входит ли ассоциативный массив a в ассоциативный массив b;
 * это выполняется, если все ключи из a содержатся в b с такими же значениями.
 *
 * Например:
 *   containsIn(mapOf("a" to "z"), mapOf("a" to "z", "b" to "sweet")) -> true
 *   containsIn(mapOf("a" to "z"), mapOf("a" to "zee", "b" to "sweet")) -> false
 */
fun containsIn(a: Map<String, String>, b: Map<String, String>): Boolean =
        a.all { (key, value) -> b[key] == value } // destructed pair instead of param "pair"

/**
 * Средняя
 *
 * Для заданного списка пар "акция"-"стоимость" вернуть ассоциативный массив,
 * содержащий для каждой акции ее усредненную стоимость.
 *
 * Например:
 *   averageStockPrice(listOf("MSFT" to 100.0, "MSFT" to 200.0, "NFLX" to 40.0))
 *     -> mapOf("MSFT" to 150.0, "NFLX" to 40.0)
 */
fun averageStockPrice(stockPrices: List<Pair<String, Double>>): Map<String, Double> {
    var code: String? = null
    var counter = 0
    var amount = 0.0
    val outputMap: MutableMap<String, Double> = mutableMapOf()
    for ((key, value) in stockPrices.toList().sortedBy { (first) -> first }) {
        if (code != key) {
            if (code != null)
                outputMap[code] = amount / counter
            code = key
            counter = 1
            amount = value
        } else {
            counter++
            amount += value
        }
    }
    if (code != null)
        outputMap[code] = amount / counter

    return outputMap.toMap()
}

/**
 * Средняя
 *
 * Входными данными является ассоциативный массив
 * "название товара"-"пара (тип товара, цена товара)"
 * и тип интересующего нас товара.
 * Необходимо вернуть название товара заданного типа с минимальной стоимостью
 * или null в случае, если товаров такого типа нет.
 *
 * Например:
 *   findCheapestStuff(
 *     mapOf("Мария" to ("печенье" to 20.0), "Орео" to ("печенье" to 100.0)),
 *     "печенье"
 *   ) -> "Мария"
 */
fun findCheapestStuff(stuff: Map<String, Pair<String, Double>>, kind: String): String? =
        stuff.filter { (_, value) -> value.first == kind }.minBy { (_, value) -> value.second }?.key

/**
 * Сложная
 *
 * Для заданного ассоциативного массива знакомых через одно рукопожатие `friends`
 * необходимо построить его максимальное расширение по рукопожатиям, то есть,
 * для каждого человека найти всех людей, с которыми он знаком через любое
 * количество рукопожатий.
 * Считать, что все имена людей являются уникальными, а также что рукопожатия
 * являются направленными, то есть, если Марат знает Свету, то это не означает,
 * что Света знает Марата.
 *
 * Например:
 *   propagateHandshakes(
 *     mapOf(
 *       "Marat" to setOf("Mikhail", "Sveta"),
 *       "Sveta" to setOf("Marat"),
 *       "Mikhail" to setOf("Sveta")
 *     )
 *   ) -> mapOf(
 *          "Marat" to setOf("Mikhail", "Sveta"),
 *          "Sveta" to setOf("Marat", "Mikhail"),
 *          "Mikhail" to setOf("Sveta", "Marat")
 *        )
 */
fun propagateHandshakes(friends: Map<String, Set<String>>): Map<String, Set<String>> {
    val outputMap: MutableMap<String, MutableSet<String>> = mutableMapOf()

    for ((key, values) in friends) {
        outputMap[key] = values.toMutableSet()
        val queue: Queue<String> = ArrayDeque<String>(values)
        while (queue.isNotEmpty()) {
            val c = queue.poll()
            outputMap.putIfAbsent(c, mutableSetOf())
            if (friends[c] != null) {
                friends[c]!!.forEach { it ->
                    if (!outputMap[key]!!.contains(it) && key != it) {
                        queue.add(it)
                        outputMap[key]!!.add(it)
                    }
                }
            }
        }
    }

    return outputMap
}

/**
 * Простая
 *
 * Удалить из изменяемого ассоциативного массива все записи,
 * которые встречаются в заданном ассоциативном массиве.
 * Записи считать одинаковыми, если и ключи, и значения совпадают.
 *
 * ВАЖНО: необходимо изменить переданный в качестве аргумента
 *        изменяемый ассоциативный массив
 *
 * Например:
 *   subtractOf(a = mutableMapOf("a" to "z"), mapOf("a" to "z"))
 *     -> a changes to mutableMapOf() aka becomes empty
 */
fun subtractOf(a: MutableMap<String, String>, b: Map<String, String>): Unit =
        b.forEach { key, value -> a.remove(key, value) }

/**
 * Простая
 *
 * Для двух списков людей найти людей, встречающихся в обоих списках
 */
fun whoAreInBoth(a: List<String>, b: List<String>): List<String> =
        a.intersect(b).toList()

/**
 * Средняя
 *
 * Для заданного набора символов определить, можно ли составить из него
 * указанное слово (регистр символов игнорируется)
 *
 * Например:
 *   canBuildFrom(listOf('a', 'b', 'o'), "baobab") -> true
 */
fun canBuildFrom(chars: List<Char>, word: String): Boolean =
        chars.containsAll(word.toSet())

/**
 * Средняя
 *
 * Найти в заданном списке повторяющиеся элементы и вернуть
 * ассоциативный массив с информацией о числе повторений
 * для каждого повторяющегося элемента.
 * Если элемент встречается только один раз, включать его в результат
 * не следует.
 *
 * Например:
 *   extractRepeats(listOf("a", "b", "a")) -> mapOf("a" to 2)
 */
fun extractRepeats(list: List<String>): Map<String, Int> =
        list.groupingBy { it }.eachCount().filter { it.value > 1 }

/**
 * Средняя
 *
 * Для заданного списка слов определить, содержит ли он анаграммы
 * (два слова являются анаграммами, если одно можно составить из второго)
 *
 * Например:
 *   hasAnagrams(listOf("тор", "свет", "рот")) -> true
 */
fun hasAnagrams(words: List<String>): Boolean {
    val db: MutableSet<String> = mutableSetOf()
    for (word in words) {
        val curChars = word.groupingBy { it }.eachCount().keys.sorted().toString()
        if (db.contains(curChars)) {
            return true
        } else {
            db.add(curChars)
        }
    }
    return false
}

/**
 * Сложная
 *
 * Для заданного списка неотрицательных чисел и числа определить,
 * есть ли в списке пара чисел таких, что их сумма равна заданному числу.
 * Если да, верните их индексы в виде Pair<Int, Int>;
 * если нет, верните пару Pair(-1, -1).
 *
 * Индексы в результате должны следовать в порядке (меньший, больший).
 *
 * Постарайтесь сделать ваше решение как можно более эффективным,
 * используя то, что вы узнали в данном уроке.
 *
 * Например:
 *   findSumOfTwo(listOf(1, 2, 3), 4) -> Pair(0, 2)
 *   findSumOfTwo(listOf(1, 2, 3), 6) -> Pair(-1, -1)
 */
fun findSumOfTwo(list: List<Int>, number: Int): Pair<Int, Int> {
    val halfList = mutableListOf<Int>()
    val tmpMap = mutableMapOf<Int, Int>()
    for ((index, value) in list.withIndex()) {
        if (value <= number) {
            tmpMap[value] = index
        }
        if (number / 2 == value) {
            halfList.add(index)
        }
    }

    tmpMap.forEach { (key, value) ->
        val i = tmpMap[number - key]
        if (i != null) {
            if (i != value) {
                return if (i < value) {
                    Pair(i, value)
                } else {
                    Pair(value, i)
                }
            } else {
                if (halfList.size >= 2) {
                    return Pair(halfList[0], halfList[1])
                }
            }
        }
    }

    return Pair(-1, -1)
}

/**
 * Очень сложная
 *
 * Входными данными является ассоциативный массив
 * "название сокровища"-"пара (вес сокровища, цена сокровища)"
 * и вместимость вашего рюкзака.
 * Необходимо вернуть множество сокровищ с максимальной суммарной стоимостью,
 * которые вы можете унести в рюкзаке.
 *
 * Например:
 *   bagPacking(
 *     mapOf("Кубок" to (500 to 2000), "Слиток" to (1000 to 5000)),
 *     850
 *   ) -> setOf("Кубок")
 *   bagPacking(
 *     mapOf("Кубок" to (500 to 2000), "Слиток" to (1000 to 5000)),
 *     450
 *   ) -> emptySet()
 */
class BagPackSolution(capacity: Int) {
    var value: Int = 0
    var items: IntArray = IntArray(capacity)
}

fun bagPacking(treasures: Map<String, Pair<Int, Int>>, capacity: Int): Set<String> {
    // NP-complete so let's try branch and bound method
    val bestSol = BagPackSolution(treasures.size)
    val weightArray = IntArray(treasures.size)
    val valueArray = IntArray(treasures.size)
    val currentItems = IntArray(treasures.size)
    var j = 0
    for ((_, value) in treasures) {
        weightArray[j] = value.first
        valueArray[j] = value.second
        currentItems[j] = 0
        bestSol.items[j] = 0
        j++
    }

    // start recursions
    for (i in 0 until treasures.size) {
        if (weightArray[i] <= capacity) {
            solveBB(i, 0, 0, capacity, treasures.size, currentItems, weightArray, valueArray, bestSol)
        }
    }

    // map to items' names
    val outputSet = mutableSetOf<String>()
    for (i in 0 until treasures.size) {
        if (bestSol.items[i] == 1) {
            outputSet.add(treasures.keys.elementAt(i))
        }
    }

    return outputSet
}

/**
 * Helper method for BB solution below.
 *
 * @param currentItemIndex
 * @param weight              current weight sum
 * @param value               current value sum
 * @param instanceMaxCapacity
 * @param instanceStuffCount
 * @param currentItems
 * @param weightArray
 * @param valueArray
 * @param bestSol
 */
private fun solveBB(currentItemIndex: Int, weight: Int, value: Int, instanceMaxCapacity: Int, instanceStuffCount: Int,
                    currentItems: IntArray, weightArray: IntArray, valueArray: IntArray, bestSol: BagPackSolution) {
    currentItems[currentItemIndex] += 1 // add one instance to solution
    val weightTmp = weight + weightArray[currentItemIndex]
    val valueTmp = value + valueArray[currentItemIndex]

    if (weightTmp <= instanceMaxCapacity) {
        if (valueTmp > bestSol.value) {
            bestSol.value = valueTmp
            bestSol.items = Arrays.copyOf(currentItems, currentItems.size)
        }

        for (i in currentItemIndex + 1 until instanceStuffCount) { // 0/1 variant, otherwise without adding
            var restValue = 0
            for (j in i until instanceStuffCount) {
                restValue += valueArray[j]
            }
            // do not call recursion if there cannot be any better solution in this branch
            if (valueTmp + restValue > bestSol.value) {
                solveBB(i, weightTmp, valueTmp, instanceMaxCapacity, instanceStuffCount, currentItems, weightArray, valueArray, bestSol)
            }
        }
    }

    currentItems[currentItemIndex] -= 1 // remove one instance from solution
}
