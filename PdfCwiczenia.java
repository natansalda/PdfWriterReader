//* Excersises in creating PDF files.
//* 
//* Autor: Natalia Nazaruk Data: 16.02.2018
//*

package pl.nataliana.pdf;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;

import com.ibm.jzos.FileFactory;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.BaseFont;

public class PdfCwiczenia {
	public static void main(String[] args) {
		PdfWriter writer = null;
		Document pdf = null;
		Paragraph paragraph = null;
		Date date;
		Font fnt10n, fnt10b, fnt12n, fnt18n;

		try {
			date = new Date();
			Date startDate = new Timestamp(date.getTime());
			System.out.println("Start: " + startDate);

			if (args.length < 1) {
				System.out.println("Program gets 2 arguments: " + "\n args[0] - path to output PDF file,"
						+ "\n args[1] - path to font file.");
				System.exit(20);
			}
			String os = System.getProperty("os.name");
			System.out.println("System: " + os);

			System.out.println("PDF file: " + args[0]);
			System.out.println("Font file: " + args[1]);

			// output PDF:
			pdf = new Document(PageSize.A4);
			writer = PdfWriter.getInstance(pdf, FileFactory.newOutputStream(args[0]));

			// Fonts:
			FontFactory.register(args[1], "pdfFont");
			Font font = FontFactory.getFont("pdfFont", BaseFont.CP1250, BaseFont.EMBEDDED);
			BaseFont bf = font.getBaseFont();

			fnt12n = new Font(bf, 12f, Font.NORMAL, BaseColor.GREEN);
			fnt10n = new Font(bf, 10f, Font.NORMAL, BaseColor.BLACK);
			fnt10b = new Font(bf, 10f, Font.BOLDITALIC, BaseColor.BLUE);
			fnt18n = new Font(bf, 18f, Font.BOLD, BaseColor.RED);

			// PDF
			writer.setPdfVersion(PdfWriter.VERSION_1_7);
			writer.createXmpMetadata();
			writer.setFullCompression();

			pdf.addTitle("Pierwszy PDF");
			pdf.addAuthor("Asseco DATA SYSTEMS SA");
			pdf.addSubject("Przyk³ad tworzenia pliku PDF");
			pdf.addKeywords("Metadata, Java, iText, PDF");
			pdf.addCreator("Program: FirstPdf");

			pdf.setMargins(50, 40, 26, 54);
			pdf.open();
			pdf.newPage();

			// I sposób definiowania paragrafu:
			paragraph = new Paragraph();
			paragraph.setLeading(20f);
			paragraph.setFont(fnt12n);
			paragraph.setAlignment(Element.ALIGN_CENTER);
			paragraph.add("Pierwsza strona\nTo jest pierwsza próba utworzenia pliku PDF");
			pdf.add(paragraph);
			paragraph.clear();

			// II sposób definiowania paragrafu:
			pdf.add(new Paragraph("Witaj w piêknym œwiecie plików PDF!", fnt12n));

			String txt = "\n\nJeœli coœ ma siê skoñczyæ to najpierw musia³o siê zacz¹æ, nie mów, ¿e nie!";
			pdf.add(new Paragraph(txt, fnt10b));
			pdf.add(new Paragraph(txt.toUpperCase(), fnt10b));

			pdf.newPage();
			paragraph.add("Druga strona");
			pdf.add(paragraph);
			String str = "\n\nKoñ by siê uœmia³: br¹zowy Ÿrebak z¿ar³ ca³y sêkacz !";
			pdf.add(new Paragraph(str + str.toUpperCase(), fnt10n));

		} catch (DocumentException | IOException e) {
			e.printStackTrace();
			System.exit(20);
		} finally {
			try {
				pdf.close();
				writer.close();
			} catch (Exception e) {
				e.printStackTrace();
				System.exit(20);
			}
			date = new Date();
			Date stopDate = new Timestamp(date.getTime());
			System.out.println("Stop: " + stopDate);
			System.out.println("OK.");
		}
	}
}