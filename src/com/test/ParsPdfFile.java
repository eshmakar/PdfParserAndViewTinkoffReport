package com.test;

import org.apache.pdfbox.cos.COSDocument;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.util.PDFTextStripper;

import java.io.FileInputStream;

public class ParsPdfFile {
    public static void main(String args[]) {
        PDFParser parser = null;
        PDDocument pdDoc = null;
        COSDocument cosDoc = null;
        PDFTextStripper pdfStripper;

        String parsedText;
        String fileName = "D:\\Tempp\\a1.pdf";
        java.io.File file = new java.io.File(fileName);
        try {
            parser = new PDFParser(new FileInputStream(file));
            parser.parse();
            cosDoc = parser.getDocument();
            pdfStripper = new PDFTextStripper();
            pdDoc = new PDDocument(cosDoc);
            parsedText = pdfStripper.getText(pdDoc);
            String[] strings = parsedText.split("\n");
            for (String text : strings) {
                if (text.charAt(2) == '.')
                    if (Character.isDigit(text.charAt(0)))
                        System.out.println(text);
            }

        } catch (Exception e) {
            e.printStackTrace();
            try {
                if (cosDoc != null)
                    cosDoc.close();
                if (pdDoc != null)
                    pdDoc.close();
            } catch (Exception e1) {
                e1.printStackTrace();
            }

        }
    }
}
/*

OUTPUT:
20.12.2021 13:49 20.12.2021 - 422,27 ₽ - 422,27 ₽ Оплата в PYATEROCHKA 5348 Kazan RUS
20.12.2021 15:20 20.12.2021 - 1 000,00 ₽ - 1 000,00 ₽ Перевод по реквизитам карты другого банка
20.12.2021 15:53 20.12.2021 + 8 000,00 ₽ + 8 000,00 ₽ Пополнение. Система быстрых платежей
21.12.2021 17:32 21.12.2021 - 907,10 ₽ - 907,10 ₽ Оплата в YM*AliExpress Moscow RU
22.12.2021 13:38 22.12.2021 - 4 213,44 ₽ - 4 213,44 ₽ Оплата услуг mBank.ZHKH
24.12.2021 12:46 24.12.2021 - 656,40 ₽ - 656,40 ₽ Оплата в PYATEROCHKA 13316A Vysokaya Gora RUS
25.12.2021 15:51 25.12.2021 - 193,00 ₽ - 193,00 ₽ Оплата в NAZIL, DACHNAYA 1A Vysokaya Gora RUS
28.12.2021 20:50 28.12.2021 - 800,00 ₽ - 800,00 ₽ Перевод по реквизитам карты другого банка
30.12.2021 16:20 30.12.2021 - 491,31 ₽ - 491,31 ₽ Оплата в PYATEROCHKA 13316A Vysokaya Gora RUS


*/
