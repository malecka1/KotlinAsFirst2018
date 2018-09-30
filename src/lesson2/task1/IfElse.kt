@file:Suppress("UNUSED_PARAMETER")

package lesson2.task1

import lesson1.task1.discriminant
import java.lang.Math.abs
import java.lang.Math.pow
import kotlin.math.max
import kotlin.math.sqrt

/**
 * Пример
 *
 * Найти число корней квадратного уравнения ax^2 + bx + c = 0
 */
fun quadraticRootNumber(a: Double, b: Double, c: Double): Int {
    val discriminant = discriminant(a, b, c)
    return when {
        discriminant > 0.0 -> 2
        discriminant == 0.0 -> 1
        else -> 0
    }
}

/**
 * Пример
 *
 * Получить строковую нотацию для оценки по пятибалльной системе
 */
fun gradeNotation(grade: Int): String = when (grade) {
    5 -> "отлично"
    4 -> "хорошо"
    3 -> "удовлетворительно"
    2 -> "неудовлетворительно"
    else -> "несуществующая оценка $grade"
}

/**
 * Пример
 *
 * Найти наименьший корень биквадратного уравнения ax^4 + bx^2 + c = 0
 */
fun minBiRoot(a: Double, b: Double, c: Double): Double {
    // 1: в главной ветке if выполняется НЕСКОЛЬКО операторов
    if (a == 0.0) {
        if (b == 0.0) return Double.NaN // ... и ничего больше не делать
        val bc = -c / b
        if (bc < 0.0) return Double.NaN // ... и ничего больше не делать
        return -sqrt(bc)
        // Дальше функция при a == 0.0 не идёт
    }
    val d = discriminant(a, b, c)   // 2
    if (d < 0.0) return Double.NaN  // 3
    // 4
    val y1 = (-b + sqrt(d)) / (2 * a)
    val y2 = (-b - sqrt(d)) / (2 * a)
    val y3 = max(y1, y2)       // 5
    if (y3 < 0.0) return Double.NaN // 6
    return -sqrt(y3)           // 7
}

/**
 * Простая
 *
 * Мой возраст. Для заданного 0 < n < 200, рассматриваемого как возраст человека,
 * вернуть строку вида: «21 год», «32 года», «12 лет».
 */
fun ageDescription(age: Int): String =
        if (age % 10 == 1 && age % 100 != 11) {
            "$age год"
        } else if ((age % 10 == 2 || age % 10 == 3 || age % 10 == 4)
                && age % 100 != 12 && age % 100 != 13 && age % 100 != 14) {
            "$age года"
        } else {
            "$age лет"
        }

/**
 * Простая
 *
 * Путник двигался t1 часов со скоростью v1 км/час, затем t2 часов — со скоростью v2 км/час
 * и t3 часов — со скоростью v3 км/час.
 * Определить, за какое время он одолел первую половину пути?
 */
fun timeForHalfWay(t1: Double, v1: Double,
                   t2: Double, v2: Double,
                   t3: Double, v3: Double): Double {
    var distance1 = t1 * v1
    var distance2 = t2 * v2
    var distance3 = t3 * v3
    val totalKmHalf = (distance1 + distance2 + distance3) / 2.0

    return if (totalKmHalf <= distance1) {
        totalKmHalf / v1
    } else if (totalKmHalf - distance1 <= distance2) {
        t1 + (totalKmHalf - distance1) / v2
    } else {
        t1 + t2 + (totalKmHalf - distance1 - distance2) / v3
    }
}

/**
 * Простая
 *
 * Нa шахматной доске стоят черный король и две белые ладьи (ладья бьет по горизонтали и вертикали).
 * Определить, не находится ли король под боем, а если есть угроза, то от кого именно.
 * Вернуть 0, если угрозы нет, 1, если угроза только от первой ладьи, 2, если только от второй ладьи,
 * и 3, если угроза от обеих ладей.
 * Считать, что ладьи не могут загораживать друг друга
 */
fun whichRookThreatens(kingX: Int, kingY: Int,
                       rookX1: Int, rookY1: Int,
                       rookX2: Int, rookY2: Int): Int {
    var flagX1 = false
    var result = 0
    if (kingX == rookX1 || kingY == rookY1) flagX1 = true
    if (kingX == rookX2 || kingY == rookY2) {
        if (flagX1) {
            result++
        }
        result += 2
    }

    if (flagX1 && result == 0) {
        result = 1
    }

    return result
}

/**
 * Простая
 *
 * На шахматной доске стоят черный король и белые ладья и слон
 * (ладья бьет по горизонтали и вертикали, слон — по диагоналям).
 * Проверить, есть ли угроза королю и если есть, то от кого именно.
 * Вернуть 0, если угрозы нет, 1, если угроза только от ладьи, 2, если только от слона,
 * и 3, если угроза есть и от ладьи и от слона.
 * Считать, что ладья и слон не могут загораживать друг друга.
 */
fun rookOrBishopThreatens(kingX: Int, kingY: Int,
                          rookX: Int, rookY: Int,
                          bishopX: Int, bishopY: Int): Int {
    val threadFromRook = whichRookThreatens(kingX, kingY, rookX, rookY, Int.MIN_VALUE, Int.MIN_VALUE) == 1

    var result = 0
    if (abs(kingX - bishopX) == abs(kingY - bishopY)) {
        if (threadFromRook) {
            result++
        }
        result += 2
    }

    if (threadFromRook && result == 0) {
        result = 1
    }

    return result
}

/**
 * Простая
 *
 * Треугольник задан длинами своих сторон a, b, c.
 * Проверить, является ли данный треугольник остроугольным (вернуть 0),
 * прямоугольным (вернуть 1) или тупоугольным (вернуть 2).
 * Если такой треугольник не существует, вернуть -1.
 */
fun triangleKind(a: Double, b: Double, c: Double): Int {
    // a^2 + b^2 = c^2
    val max = maxOf(a, b, c)
    val rightSide = pow(max, 2.0)
    val leftSide = when {
        a == max -> {
            if (a > b + c) return -1
            pow(b, 2.0) + pow(c, 2.0)
        }
        b == max -> {
            if (b > a + c) return -1
            pow(a, 2.0) + pow(c, 2.0)
        }
        else -> {
            if (c > a + b) return -1
            pow(a, 2.0) + pow(b, 2.0)
        }
    }

    return when {
        leftSide == rightSide -> // right triangle
            1
        leftSide < rightSide -> // obtuse
            2
        else -> // acute
            0
    }
}

/**
 * Средняя
 *
 * Даны четыре точки на одной прямой: A, B, C и D.
 * Координаты точек a, b, c, d соответственно, b >= a, d >= c.
 * Найти длину пересечения отрезков AB и CD.
 * Если пересечения нет, вернуть -1.
 */
fun segmentLength(a: Int, b: Int, c: Int, d: Int): Int {
    val diff = Math.min(b, d) - Math.max(c, a)

    return if (diff < 0) {
        -1
    } else {
        diff
    }
}
