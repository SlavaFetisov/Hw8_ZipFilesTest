
import com.codeborne.pdftest.PDF;
import com.codeborne.xlstest.XLS;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.RFC4180ParserBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class ZipFileParsingTests {
    private final ClassLoader cl = ZipFileParsingTests.class.getClassLoader();


    @Test
    void zipPdfParsingTest() throws Exception {
        boolean found = false;
        try (InputStream is = cl.getResourceAsStream("testFiles.zip")) {
            Assertions.assertNotNull(is);
            try (ZipInputStream zis = new ZipInputStream(is)) {
                ZipEntry entry;
                while ((entry = zis.getNextEntry()) != null) {
                    if (entry.getName().endsWith(".pdf")) {
                        found = true;
                        PDF pdf = new PDF(zis);
                        Assertions.assertEquals("TrendUp", pdf.author);
                    }
                }
            }
        }
        Assertions.assertTrue(found, "PDF файл не найден в архиве testFiles.zip");
    }

    @Test
    void zipXlsParsingTest() throws Exception {
        boolean found = false;
        try (InputStream is = cl.getResourceAsStream("testFiles.zip");
             ZipInputStream zis = new ZipInputStream(is)) {
            ZipEntry entry;
            while ((entry = zis.getNextEntry()) != null) {
                if (entry.getName().endsWith(".xls") || entry.getName().endsWith(".xlsx")) {
                    found = true;
                    XLS xls = new XLS(zis);
                    String actualValue = xls.excel.getSheetAt(0).getRow(1).getCell(2).getStringCellValue();
                    Assertions.assertTrue(actualValue.contains("120 ч"), "Значение не найдено"
                    );
                }
            }
        }
        Assertions.assertTrue(found, "XLS-файл файл не найден в архиве testFiles.zip");
    }


    @Test
    void zipCsvParsingTest() throws Exception {
        boolean found = false;
        try (InputStream is = cl.getResourceAsStream("testFiles.zip");
             ZipInputStream zis = new ZipInputStream(is)) {
            ZipEntry entry;
            while ((entry = zis.getNextEntry()) != null) {
                if (entry.getName().endsWith(".csv")) {
                    found = true;
                    CSVReader csvReader = new CSVReaderBuilder(
                            new InputStreamReader(zis, StandardCharsets.UTF_8))
                            .withCSVParser(new RFC4180ParserBuilder()
                                    .withSeparator(';')
                                    .withQuoteChar('"')
                                    .build())
                            .build();

                    List<String[]> data = csvReader.readAll();
                    Assertions.assertFalse(data.isEmpty(), "CSV файл пустой");
                    String[] header = data.get(0);
                    String[] bugRow = null;
                    for (String[] row : data) {
                        if (row.length > 0 && "1".equals(row[0].trim())) {
                            bugRow = row;
                            break;
                        }
                    }
                    Assertions.assertNotNull(bugRow, "Не найдена строка с баг-репортом ID=1");
                    Assertions.assertEquals("Req: 23423.56.b", bugRow[1].trim(), "Неверный номер требования");
                    Assertions.assertEquals("Link 'Wiki' leads to 404", bugRow[2].trim(), "Неверный Summary");
                    Assertions.assertTrue(bugRow[3].contains("404 error"), "Не найдено описание ошибки");
                    Assertions.assertTrue(bugRow[4].contains("http://www.mantisbt.org/demo"),
                            "Не найдена ссылка на демо-сайт");

                    Assertions.assertEquals("Act: erorr 404.", bugRow[5].trim(), "Неверный AR");
                    Assertions.assertEquals("Exp: normal Wiki-page.", bugRow[6].trim(), "Неверный EX");
                    Assertions.assertEquals("Medium", bugRow[7].trim(), "Неверная критичность");
                    Assertions.assertEquals("Normal", bugRow[8].trim(), "Неверный приоритет");
                }
                zis.closeEntry();
            }
        }
        Assertions.assertTrue(found, "CSV файл не найден в архиве testFiles.zip");
    }
}








