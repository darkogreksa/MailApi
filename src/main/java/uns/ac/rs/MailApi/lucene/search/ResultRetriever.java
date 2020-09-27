package uns.ac.rs.MailApi.lucene.search;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopScoreDocCollector;
import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.InvalidTokenOffsetsException;
import org.apache.lucene.search.highlight.QueryScorer;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.SimpleFSDirectory;

import uns.ac.rs.MailApi.lucene.indexing.analysers.SerbianAnalyzer;
import uns.ac.rs.MailApi.lucene.model.RequiredHighlight;
import uns.ac.rs.MailApi.lucene.model.ResultData;
import uns.ac.rs.MailApi.lucene.model.handlers.DocumentHandler;
import uns.ac.rs.MailApi.lucene.model.handlers.PDFHandler;

public class ResultRetriever {

    private TopScoreDocCollector collector;
    private static int maxHits = 10;

    public ResultRetriever(){
        collector=TopScoreDocCollector.create(10);
    }

    public static void setMaxHits(int maxHits) {
        ResultRetriever.maxHits = maxHits;
    }

    public static int getMaxHits() {
        return ResultRetriever.maxHits;
    }

    public static List<ResultData> getResults(Query query,
                                              List<RequiredHighlight> requiredHighlights) {
        if (query == null) {
            return null;
        }
        try {
            System.out.println("DA LI ULAZIS");
            Directory indexDir = new SimpleFSDirectory(FileSystems.getDefault().getPath(ResourceBundle
                    .getBundle("application").getString("index")));
            System.out.println("VAMOOOOOO 1" + indexDir);
            DirectoryReader reader = DirectoryReader.open(indexDir);
            IndexSearcher is = new IndexSearcher(reader);
            System.out.println("VAMOOOOOO 3" + is.toString() + "   " + reader.toString());
            TopScoreDocCollector collector = TopScoreDocCollector.create(
                    maxHits);
            System.out.println("VAMOOOOOO 2" + collector.toString());
            List<ResultData> results = new ArrayList<ResultData>();

            is.search(query, collector);
            ScoreDoc[] hits = collector.topDocs().scoreDocs;

            ResultData rd;
            Document doc;
            Highlighter hl;
            SerbianAnalyzer sa = new SerbianAnalyzer();
            System.out.println("VAMOOOOOO 4" + hits.toString());
            for (ScoreDoc sd : hits) {
                System.out.println("VAMOOOOOO 5");
                doc = is.doc(sd.doc);
                System.out.println("AAA 1");
                String title = doc.get("title");
                String sender = doc.get("sender");
                String receiver = doc.get("receiver");
                String text = doc.get("text");
                String pdf = doc.get("pdf");
                System.out.println(" CCCC  2");
                String contactName = doc.get("contactName");
                String contactLastName = doc.get("contactLastName");
                String contactNote = doc.get("contactNote");
//                int category = Integer.parseInt(doc.get("category"));
//                Integer year = Integer.parseInt(doc.get("year").trim());
                
                String highlight = "";
                for (RequiredHighlight rh : requiredHighlights) {
                    hl = new Highlighter(new QueryScorer(query, reader, rh.getFieldName()));
                    try{
                        highlight += hl.getBestFragment(sa, rh.getFieldName(),
                                "");
                    }catch (InvalidTokenOffsetsException e) {
                        //throw new IllegalArgumentException("Unable to make highlight");
                    }
                }
                if (title != null ){
                    System.out.println(" BBBB  3");
                    rd = new ResultData(title,sender,receiver, text, pdf);
                    System.out.println("neki " + rd.toString());
                    results.add(rd);
                } else {
                    System.out.println("ovde ce");
                    rd = new ResultData(contactName, contactLastName, contactNote);
                    results.add(rd);
                    System.out.println("rd  " + rd.toString() + "   " + results.toString());
                }

            }
            reader.close();
            return results;

        } catch (IOException e) {
            throw new IllegalArgumentException(
                    "U prosledjenom direktorijumu ne postoje indeksi ili je direktorijum zakljucan");
        }
    }

    public String printSearchResults(Query query, File indexDir){
        StringBuilder retVal = new StringBuilder();
        try{
            Directory fsDir=new SimpleFSDirectory(FileSystems.getDefault().getPath(indexDir.getAbsolutePath()));
            DirectoryReader ireader = DirectoryReader.open(fsDir);
            IndexSearcher is = new IndexSearcher(ireader);
            is.search(query, collector);

            ScoreDoc[] hits=collector.topDocs().scoreDocs;
            System.err.println("Found " + hits.length + " document(s) that matched query '" + query + "':");
            for (int i = 0; i < collector.getTotalHits(); i++) {
                int docId=hits[i].doc;
                Document doc = is.doc(docId);
                retVal.append("\t"+doc.get("title")+" ("+doc.get("filedate")+")\n");
                retVal.append("\t"+doc.get("filename")+"\n\n");
            }
        }catch(IOException ioe){
            retVal.append(ioe.getMessage() + "\n");
        }
        return retVal.toString();
    }

    public String printHTMLSearchResults(Query query, File indexDir){
        StringBuilder retVal = new StringBuilder();
        retVal.append("<html><body>");
        try{
            Directory fsDir=new SimpleFSDirectory(FileSystems.getDefault().getPath(indexDir.getAbsolutePath()));
            DirectoryReader ireader = DirectoryReader.open(fsDir);
            IndexSearcher is = new IndexSearcher(ireader);
            is.search(query, collector);

            ScoreDoc[] hits=collector.topDocs().scoreDocs;
            System.err.println("Found " + hits.length + " document(s) that matched query '" + query + "':");
            for (int i = 0; i < collector.getTotalHits(); i++) {
                int docId=hits[i].doc;
                Document doc = is.doc(docId);
                retVal.append("<p><h3>"+doc.get("title")+" ("+doc.get("filedate")+")</h3><br/>");
                retVal.append(doc.get("filename")+"<br/><br/></p>");
            }
        }catch(IOException ioe){
            retVal.append(ioe.getMessage() + "<br/>");
        }
        retVal.append("</body></html>");
        return retVal.toString();
    }

        private static String getDocumentText(String location){
            File file = new File(location);
            DocumentHandler handler = getHandler(location);
            return handler.getText(file);
    }

    protected static DocumentHandler getHandler(String fileName){
        if(fileName.endsWith(".pdf")){
            return new PDFHandler();
        }else{
            return null;
        }
    }
}

