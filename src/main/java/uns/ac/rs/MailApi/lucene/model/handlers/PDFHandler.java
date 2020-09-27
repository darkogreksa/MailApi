package uns.ac.rs.MailApi.lucene.model.handlers;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import org.apache.lucene.document.DateTools;
import org.apache.pdfbox.io.RandomAccessFile;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentInformation;
import org.apache.pdfbox.text.PDFTextStripper;

import uns.ac.rs.MailApi.lucene.model.IndexUnit;
import uns.ac.rs.MailApi.lucene.model.UploadModel;


public class PDFHandler extends DocumentHandler {

    @Override
    public IndexUnit getIndexUnit(File file) {
        IndexUnit retVal = new IndexUnit();
        PDDocument pdf = null;
        try {
            PDFParser parser = new PDFParser(new RandomAccessFile(file, "r"));
            parser.parse();
            String text = getText(parser);
            retVal.setText(text);

            // metadata extraction
            pdf = parser.getPDDocument();
            PDDocumentInformation info = pdf.getDocumentInformation();

            String title = ""+info.getTitle();
            retVal.setTitle(title);

//            retVal.setFilename(file.getCanonicalPath());

            String modificationDate=DateTools.dateToString(new Date(file.lastModified()),DateTools.Resolution.DAY);
//            retVal.setFiledate(modificationDate);

            pdf.close();
        } catch (IOException e) {
            System.out.println("Greksa pri konvertovanju dokumenta u pdf");
        }

        return retVal;
    }

    @Override
    public String getText(File file) {
        try {
            PDFParser parser = new PDFParser(new RandomAccessFile(file, "r"));
            parser.parse();
            PDFTextStripper textStripper = new PDFTextStripper();
            String text = textStripper.getText(parser.getPDDocument());
            return text;
        } catch (IOException e) {
            System.out.println("Greksa pri konvertovanju dokumenta u pdf");
        }
        return null;
    }

    public String getText(PDFParser parser) {
        try {
            PDFTextStripper textStripper = new PDFTextStripper();
            String text = textStripper.getText(parser.getPDDocument());
            return text;
        } catch (IOException e) {
            System.out.println("Greksa pri konvertovanju dokumenta u pdf");
        }
        return null;
    }
    
    @Override
    public UploadModel getModel(File file) {
    	UploadModel retVal = new UploadModel();
    	PDDocument pdf = null;
    	try {
			PDFParser parser = new PDFParser(new RandomAccessFile(file, "r"));
			parser.parse();
			
			pdf = parser.getPDDocument();
			PDDocumentInformation information = pdf.getDocumentInformation();
			String title = "" + information.getTitle();
			retVal.setTitle(title);
			
			String keywords = "" + information.getKeywords();
//			retVal.setKeywords(keywords);
			String author = "" + information.getAuthor();
//			retVal.setAuthor(author);
//			retVal.setFilename(file.getCanonicalPath());
			
			pdf.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
    	
    	return retVal;
    }

}