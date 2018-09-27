@file:Suppress("UNUSED_PARAMETER")

package lesson3.task1

import kotlin.math.PI
import kotlin.math.sqrt

/**
 * Пример
 *
 * Вычисление факториала
 */
fun factorial(n: Int): Double {
    var result = 1.0
    for (i in 1..n) {
        result = result * i // Please do not fix in master
    }
    return result
}

/**
 * Пример
 *
 * Проверка числа на простоту -- результат true, если число простое
 */
fun isPrime(n: Int): Boolean {
    if (n < 2) return false
    if (n == 2) return true
    if (n % 2 == 0) return false
    for (m in 3..sqrt(n.toDouble()).toInt() step 2) {
        if (n % m == 0) return false
    }
    return true
}

/**
 * Пример
 *
 * Проверка числа на совершенность -- результат true, если число совершенное
 */
fun isPerfect(n: Int): Boolean {
    var sum = 1
    for (m in 2..n / 2) {
        if (n % m > 0) continue
        sum += m
        if (sum > n) break
    }
    return sum == n
}

/**
 * Пример
 *
 * Найти число вхождений цифры m в число n
 */
fun digitCountInNumber(n: Int, m: Int): Int =
        when {
            n == m -> 1
            n < 10 -> 0
            else -> digitCountInNumber(n / 10, m) + digitCountInNumber(n % 10, m)
        }

/**
 * Тривиальная
 *
 * Найти количество цифр в заданном числе n.
 * Например, число 1 содержит 1 цифру, 456 -- 3 цифры, 65536 -- 5 цифр.
 *
 * Использовать операции со строками в этой задаче запрещается.
 */
fun digitNumber(n: Int): Int {
    if (n == 0) return 1

    var orig = n
    var c = 0

    while (orig != 0) {
        c++
        orig /= 10
    }

    return c
}

/**
 * Простая
 *
 * Найти число Фибоначчи из ряда 1, 1, 2, 3, 5, 8, 13, 21, ... с номером n.
 * Ряд Фибоначчи определён следующим образом: fib(1) = 1, fib(2) = 1, fib(n+2) = fib(n) + fib(n+1)
 */
fun fib(n: Int): Int {
    if (n == 0) return 0

    var a = 0
    var b = 1

    for (i in 2..n) {
        val c = a + b
        a = b
        b = c
    }

    return b
}

/**
 * Простая
 *
 * Для заданных чисел m и n найти наименьшее общее кратное, то есть,
 * минимальное число k, которое делится и на m и на n без остатка
 */
fun lcm(m: Int, n: Int): Int =
        m / gcd(Math.max(n, m), Math.min(n, m)) * n

// Euclidean Algorithm, n > m
fun gcd(n: Int, m: Int): Int =
        if (m == 0) {
            n
        } else {
            gcd(m, n % m)
        }

/**
 * Простая
 *
 * Для заданного числа n > 1 найти минимальный делитель, превышающий 1
 */
fun minDivisor(n: Int): Int {
    if (n % 2 == 0) {
        return 2
    } else {
        for (i in 3..Math.floor(Math.sqrt(n.toDouble())).toInt() step 2) {
            if (isPrime(i) && n % i == 0) {
                return i
            }
        }
    }

    return n
}

/**
 * Простая
 *
 * Для заданного числа n > 1 найти максимальный делитель, меньший n
 */
fun maxDivisor(n: Int): Int =
        if (isPrime(n)) {
            1
        } else {
            n / minDivisor(n)
        }

/**
 * Простая
 *
 * Определить, являются ли два заданных числа m и n взаимно простыми.
 * Взаимно простые числа не имеют общих делителей, кроме 1.
 * Например, 25 и 49 взаимно простые, а 6 и 8 -- нет.
 */
fun isCoPrime(m: Int, n: Int): Boolean =
        gcd(Math.max(n, m), Math.min(n, m)) == 1

/**
 * Простая
 *
 * Для заданных чисел m и n, m <= n, определить, имеется ли хотя бы один точный квадрат между m и n,
 * то есть, существует ли такое целое k, что m <= k*k <= n.
 * Например, для интервала 21..28 21 <= 5*5 <= 28, а для интервала 51..61 квадрата не существует.
 */
fun squareBetweenExists(m: Int, n: Int): Boolean =
        Math.pow(Math.ceil(Math.sqrt(m.toDouble())), 2.0) <= n

/**
 * Средняя
 *
 * Гипотеза Коллатца. Рекуррентная последовательность чисел задана следующим образом:
 *
 *   ЕСЛИ (X четное)
 *     Xслед = X /2
 *   ИНАЧЕ
 *     Xслед = 3 * X + 1
 *
 * например
 *   15 46 23 70 35 106 53 160 80 40 20 10 5 16 8 4 2 1 4 2 1 4 2 1 ...
 * Данная последовательность рано или поздно встречает X == 1.
 * Написать функцию, которая находит, сколько шагов требуется для
 * этого для какого-либо начального X > 0.
 */
fun collatzSteps(x: Int): Int {
    if (x == 1) return 0
    var stepsCount = 0
    var res = x
    while (res != 1) {
        if (res % 2 == 0) {
            res /= 2
        } else {
            res = 3 * res + 1
        }
        stepsCount++
    }

    return stepsCount
}

/**
 * Средняя
 *
 * Для заданного x рассчитать с заданной точностью eps
 * sin(x) = x - x^3 / 3! + x^5 / 5! - x^7 / 7! + ...
 * Нужную точность считать достигнутой, если очередной член ряда меньше eps по модулю
 */
fun sin(x: Double, eps: Double): Double {
    var arg = x
    if (arg >= 2 * PI) {
        arg %= 2 * PI
    }
    if (arg == 0.0) return 0.0

    var output = 0.0
    var theLastNumber = Double.MAX_VALUE
    var i = 0
    while (Math.abs(theLastNumber) >= eps) {
        theLastNumber = (Math.pow(-1.0, i.toDouble()) * Math.pow(arg, 2 * i + 1.0)) / factorial(2 * i + 1)
        output += theLastNumber
        i++
    }

    return output - output % eps
}

/**
 * Средняя
 *
 * Для заданного x рассчитать с заданной точностью eps
 * cos(x) = 1 - x^2 / 2! + x^4 / 4! - x^6 / 6! + ...
 * Нужную точность считать достигнутой, если очередной член ряда меньше eps по модулю
 */
fun cos(x: Double, eps: Double): Double {
    var arg = x
    if (arg >= 2 * PI) {
        arg %= 2 * PI
    }
    if (arg == 0.0) return 1.0

    var output = 0.0
    var theLastNumber = Double.MAX_VALUE
    var i = 0
    while (Math.abs(theLastNumber) >= eps) {
        theLastNumber = (Math.pow(-1.0, i.toDouble()) * Math.pow(arg, 2.0 * i)) / factorial(2 * i)
        output += theLastNumber
        i++
    }

    return output - output % eps
}

/**
 * Средняя
 *
 * Поменять порядок цифр заданного числа n на обратный: 13478 -> 87431.
 *
 * Использовать операции со строками в этой задаче запрещается.
 */
fun revert(n: Int): Int {
    var orig = n
    var output = 0

    while (orig != 0) {
        val currentDigit = orig % 10
        output = output * 10 + currentDigit
        orig /= 10
    }

    return output
}

/**
 * Средняя
 *
 * Проверить, является ли заданное число n палиндромом:
 * первая цифра равна последней, вторая -- предпоследней и так далее.
 * 15751 -- палиндром, 3653 -- нет.
 *
 * Использовать операции со строками в этой задаче запрещается.
 */
fun isPalindrome(n: Int): Boolean =
        n == revert(n)

/**
 * Средняя
 *
 * Для заданного числа n определить, содержит ли оно различающиеся цифры.
 * Например, 54 и 323 состоят из разных цифр, а 111 и 0 из одинаковых.
 *
 * Использовать операции со строками в этой задаче запрещается.
 */
fun hasDifferentDigits(n: Int): Boolean {
    var orig = n
    val digits = mutableSetOf<Int>()

    while (orig != 0) {
        digits.add(orig % 10)
        orig /= 10
    }

    return digits.size > 1
}

/**
 * Сложная
 *
 * Найти n-ю цифру последовательности из квадратов целых чисел:
 * 149162536496481100121144...
 * Например, 2-я цифра равна 4, 7-я 5, 12-я 6.
 *
 * Использовать операции со строками в этой задаче запрещается.
 */
fun squareSequenceDigit(n: Int): Int {
    var nrDigitsPassed = 0
    for (i in 1..n) {
        var powOutput = i * i
        val digitNumber = digitNumber(powOutput)
        nrDigitsPassed += digitNumber

        if (nrDigitsPassed >= n) {
            return if (digitNumber == 1) powOutput else {
                var j = 0
                while (j < (nrDigitsPassed - n)) {
                    powOutput /= 10
                    j++
                }
                powOutput % 10
            }
        }
    }

    return -1 // nr lower than zero
}

/**
 * Сложная
 *
 * Найти n-ю цифру последовательности из чисел Фибоначчи (см. функцию fib выше):
 * 1123581321345589144...
 * Например, 2-я цифра равна 1, 9-я 2, 14-я 5.
 *
 * Использовать операции со строками в этой задаче запрещается.
 */
fun fibSequenceDigit(n: Int): Int {
    var nrDigitsPassed = 0
    for (i in 1..n) {
        var fibOutput = fib(i)
        val digitNumber = digitNumber(fibOutput)
        nrDigitsPassed += digitNumber

        if (nrDigitsPassed >= n) {
            return if (digitNumber == 1) fibOutput else {
                var j = 0
                while (j < (nrDigitsPassed - n)) {
                    fibOutput /= 10
                    j++
                }
                fibOutput % 10
            }
        }
    }

    return -1 // nr lower than zero
}
