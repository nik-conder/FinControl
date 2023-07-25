package com.app.myfincontrol.domain.useCases

import android.os.Environment
import com.app.myfincontrol.data.enums.ChartSort
import com.app.myfincontrol.data.repositories.TransactionRepository
import java.io.File
import java.io.FileWriter
import java.io.IOException
import javax.inject.Inject


class DataExchangeUseCase @Inject constructor(
    private val transactionRepository: TransactionRepository
) {

    suspend fun exportToCsv(sort: ChartSort) {
        try {
            val fileName = "my_file.csv" // Имя файла
            val documentsDir =
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS)
            val file = File(documentsDir, fileName)

            // Открываем файл для записи
            val writer = FileWriter(file)

            // Записываем данные (пример записи в CSV)
            writer.append("Header 1,")
            writer.append("Header 2")
            writer.append("\n")
            writer.append("Data 1,")
            writer.append("Data 2")
            writer.append("\n")
            // Добавьте другие строки с данными по необходимости

            // Закрываем файл
            writer.flush()
            writer.close()

            println(fileName)
            println(documentsDir)

        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    // Метод для экспорта списка объектов Transaction в файл формата XLSX
    fun exportToXlsx(sort: ChartSort) {
//        // Создаем новую рабочую книгу XLSX
//        val workbook = XSSFWorkbook()
//        val sheet = workbook.createSheet("Transactions")
//
//        val data = transactionRepository.getTransactions()
//        // Заголовки столбцов
//        val headerRow = sheet.createRow(0)
//        headerRow.createCell(0).setCellValue("ID")
//        headerRow.createCell(1).setCellValue("Profile ID")
//        headerRow.createCell(2).setCellValue("Type")
//        headerRow.createCell(3).setCellValue("Amount")
//        headerRow.createCell(4).setCellValue("Datetime")
//        headerRow.createCell(5).setCellValue("Category")
//
//        // Данные
//        data.forEachIndexed { index, transaction ->
//            val row = sheet.createRow(index + 1)
//            row.createCell(0).setCellValue(transaction.id.toDouble())
//            row.createCell(1).setCellValue(transaction.profileId.toDouble())
//            row.createCell(2).setCellValue(transaction.type.name)
//            row.createCell(3).setCellValue(transaction.amount.toDouble())
//            row.createCell(4).setCellValue(transaction.datetime.toDouble())
//            row.createCell(5).setCellValue(transaction.category)
//        }
//
//        println(data)
        //saveWorkbookToFile(workbook, fileName, "xlsx")
    }

}

/*
import android.os.Environment
import org.apache.poi.ss.usermodel.*
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

fun writeDataToExcel() {
    // Создаем новую рабочую книгу XLSX
    val workbook = XSSFWorkbook()

    // Создаем новый лист
    val sheet: Sheet = workbook.createSheet("Sheet1")

    // Добавляем данные в лист
    val data = arrayOf(
        arrayOf("Имя", "Возраст", "Город"),
        arrayOf("Алексей", "30", "Москва"),
        arrayOf("Мария", "25", "Санкт-Петербург"),
        arrayOf("Иван", "28", "Казань")
    )

    var rowIdx = 0
    for (rowArr in data) {
        val row: Row = sheet.createRow(rowIdx++)
        var cellIdx = 0
        for (cellValue in rowArr) {
            val cell: Cell = row.createCell(cellIdx++)
            cell.setCellValue(cellValue.toString())
        }
    }

    // Сохраняем файл
    val filePath = "${Environment.getExternalStorageDirectory()}/data.xlsx"
    val file = File(filePath)

    try {
        val fileOut = FileOutputStream(file)
        workbook.write(fileOut)
        fileOut.close()
        println("Файл успешно сохранен по пути: $filePath")
    } catch (e: IOException) {
        e.printStackTrace()
        println("Произошла ошибка при сохранении файла.")
    }
}

 */