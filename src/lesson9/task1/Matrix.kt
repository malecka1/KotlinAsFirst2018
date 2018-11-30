@file:Suppress("UNUSED_PARAMETER", "unused")

package lesson9.task1

/**
 * Ячейка матрицы: row = ряд, column = колонка
 */
data class Cell(val row: Int, val column: Int)

/**
 * Интерфейс, описывающий возможности матрицы. E = тип элемента матрицы
 */
interface Matrix<E> {
    /** Высота */
    val height: Int

    /** Ширина */
    val width: Int

    /**
     * Доступ к ячейке.
     * Методы могут бросить исключение, если ячейка не существует или пуста
     */
    operator fun get(row: Int, column: Int): E

    operator fun get(cell: Cell): E

    /**
     * Запись в ячейку.
     * Методы могут бросить исключение, если ячейка не существует
     */
    operator fun set(row: Int, column: Int, value: E)

    operator fun set(cell: Cell, value: E)
}

/**
 * Простая
 *
 * Метод для создания матрицы, должен вернуть РЕАЛИЗАЦИЮ Matrix<E>.
 * height = высота, width = ширина, e = чем заполнить элементы.
 * Бросить исключение IllegalArgumentException, если height или width <= 0.
 */
fun <E> createMatrix(height: Int, width: Int, e: E): Matrix<E> {
    if (height <= 0 || width <= 0) {
        throw IllegalArgumentException()
    } else {
        return MatrixImpl(height, width, e)
    }
}

/**
 * Средняя сложность
 *
 * Реализация интерфейса "матрица"
 */
class MatrixImpl<E>(override val height: Int, override val width: Int, e: E) : Matrix<E> {

    val data = mutableMapOf<Cell, E>()

    init {
        for (i in 0 until height) {
            for (j in 0 until width) {
                data[Cell(i, j)] = e
            }
        }
    }

    override fun get(row: Int, column: Int): E =
            get(Cell(row, column))

    override fun get(cell: Cell): E {
        if (data.containsKey(cell)) {
            return data.getValue(cell)
        } else {
            throw IllegalArgumentException()
        }
    }

    override fun set(row: Int, column: Int, value: E) =
            set(Cell(row, column), value)

    override fun set(cell: Cell, value: E) {
        data[cell] = value
    }

    override fun equals(other: Any?): Boolean =
            other is MatrixImpl<*> && height == other.height && width == other.width && data == other.data

    override fun hashCode(): Int {
        var result = height
        result = 31 * result + width
        result = 31 * result + data.hashCode()
        return result
    }

    override fun toString(): String =
            "{\n  \"data\": " + data.map { entry -> entry.value }.joinToString(prefix = "[\n    ",
                    separator = ",\n    ", postfix = "\n  ],\n") + "  \"height\": " + height + ",\n  \"width\": " + width + "\n}"
}

