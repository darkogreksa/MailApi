package uns.ac.rs.MailApi.controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import org.apache.lucene.index.IndexableField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import uns.ac.rs.MailApi.entity.Message;
import uns.ac.rs.MailApi.lucene.Indexer;
import uns.ac.rs.MailApi.lucene.model.IndexUnit;
import uns.ac.rs.MailApi.lucene.model.UploadModel;
import uns.ac.rs.MailApi.lucene.model.handlers.PDFHandler;

@RestController
@RequestMapping(value = "/api")
@CrossOrigin("*")
public class IndexerController {

    private static String DATA_DIR_PATH;

    static {
        ResourceBundle rb=ResourceBundle.getBundle("application");
        DATA_DIR_PATH=rb.getString("dataDir");
    }

    @GetMapping("/reindex")
    public ResponseEntity<String> index() throws IOException {
        File dataDir = getResourceFilePath(DATA_DIR_PATH);
        long start = new Date().getTime();
        int numIndexed = Indexer.getInstance().index(dataDir);
        long end = new Date().getTime();
        String text = "Indexing " + numIndexed + " files took "
                + (end - start) + " milliseconds";
        return new ResponseEntity<String>(text, HttpStatus.OK);
    }

    private File getResourceFilePath(String path) {
        URL url = this.getClass().getClassLoader().getResource(path);
        File file = null;
        try {
            file = new File(url.toURI());
        } catch (URISyntaxException e) {
            file = new File(url.getPath());
        }
        return file;
    }



    @PostMapping(value="/index/add")
    public ResponseEntity<String> multiUploadFileModel(@ModelAttribute UploadModel model) {


        try {

            indexUploadedFile(model);

        } catch (IOException e) {
            return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<String>("Successfully uploaded!", HttpStatus.OK);

    }


    @PostMapping(value="/book")
    public ResponseEntity<UploadModel> book(@ModelAttribute UploadModel model) throws IOException{
    	PDFHandler pdf = new PDFHandler();
    	MultipartFile[] files = model.getFiles();
    	for (MultipartFile file : files) {
    		if(file.isEmpty()) {
    			continue;
    		}
    		String filename = saveUploadedFile(file);
    		System.out.println("FILEEEEEEENAME" + filename);
    		if (filename != null) {
    			model = pdf.getModel(new File(filename));
    		}
    	}
    	return new ResponseEntity<UploadModel>(model, HttpStatus.OK);
    }
    //save file
    private String saveUploadedFile(MultipartFile file) throws IOException {
        String retVal = null;
        if (! file.isEmpty()) {
            byte[] bytes = file.getBytes();
            Path path = Paths.get(getResourceFilePath(DATA_DIR_PATH).getAbsolutePath() + File.separator + file.getOriginalFilename());
            Files.write(path, bytes);
            retVal = path.toString();
            System.out.println("FILEE" + path);
        }
        return retVal;
    }

    private void indexUploadedFile(UploadModel model) throws IOException{

        for (MultipartFile file : model.getFiles()) {

            if (file.isEmpty()) {
                continue; //next please
            }
            String fileName = saveUploadedFile(file);
            if(fileName != null){
                IndexUnit indexUnit = Indexer.getInstance().getHandler(fileName).getIndexUnit(new File(fileName));
                indexUnit.setTitle(model.getTitle());
                indexUnit.setText(model.getText());
                indexUnit.setReceiver(model.getReceiver());
//                indexUnit.setKeywords(new ArrayList<String>(Arrays.asList(model.getKeywords().split(" "))));
                indexUnit.setSender(model.getSender());
//                indexUnit.setLanguage(model.getLanguage());
                Indexer.getInstance().add(indexUnit.getLuceneDocument());


                Message message = new Message();
                message.setSubject(model.getTitle());
                message.setContent(model.getText());
                message.setFrom(model.getSender());
                message.setTo(model.getReceiver());

//                eBook eBook = new eBook();
//
//                eBook.setTitle(model.getTitle());
//                eBook.setAuthor(model.getAuthor());
//                eBook.setKeywords(model.getKeywords());
//                eBook.setCategory_id(model.getCategory());
//                eBook.setLanguage_id(model.getLanguage());
//
//                eBook = eBookService.save(eBook);
            }
        }
    }

//    @PutMapping(value="/index/update/{filename}")
//    public ResponseEntity<UploadModel> update(@RequestBody UploadModel model, @PathVariable("filename") String filename){
//    	String path = getResourceFilePath(DATA_DIR_PATH).getAbsolutePath() + "\\" + filename + ".pdf";
//    	IndexUnit indexUnit = Indexer.getInstance().getHandler(path).getIndexUnit(new File(path));
//    	
//    	indexUnit.setTitle(model.getTitle());
//    	indexUnit.setAuthor(model.getAuthor());
//    	indexUnit.setKeywords(new ArrayList<String>(Arrays.asList(model.getKeywords().split(" "))));
////    	indexUnit.setLanguage(model.getLanguage());
//    	indexUnit.setCategory(model.getCategory());
//    	
//    	List<IndexableField> fields = new ArrayList<IndexableField>(Arrays.asList(indexUnit.getLuceneDocument().getField("title"),
//    			indexUnit.getLuceneDocument().getField("author"), indexUnit.getLuceneDocument().getField("keyword"), indexUnit.getLuceneDocument().getField("language"), indexUnit.getLuceneDocument().getField("category")));
//    	
//    	Indexer.getInstance().updateDocument(path, fields);
//    	
//    	return new ResponseEntity<UploadModel>(model, HttpStatus.OK);
//    }
    
    
    @GetMapping(value="/index/download/{filename}")
    public ResponseEntity<byte[]> download(@PathVariable("filename") String filename){
    	ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
    	
    	URL url = classLoader.getResource(DATA_DIR_PATH + "/" + filename + ".pdf");
    	System.out.println("URRRRRRRRRRRRRRL" + url);
    	File file = null;
    	try {
			file = new File(url.getFile());
		} catch (Exception e) {
			return new ResponseEntity<byte[]>(HttpStatus.NOT_FOUND);
		}
    	
    	HttpHeaders headers = new HttpHeaders();
    	headers.setContentType(MediaType.APPLICATION_JSON);
    	headers.add("filename", filename + ".pdf");
    	
    	byte[] files = readBytesFromFile(file.toString());
    
    	return ResponseEntity.ok().headers(headers).body(files);
    }
    
    private static byte[] readBytesFromFile(String filePath) {

		FileInputStream fileInputStream = null;
		byte[] bytesArray = null;
		try {

			File file = new File(filePath);
			bytesArray = new byte[(int) file.length()];

			// read file into bytes[]
			fileInputStream = new FileInputStream(file);
			fileInputStream.read(bytesArray);

			System.out.println("INPUUUT" + file);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (fileInputStream != null) {
				try {
					fileInputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}

		return bytesArray;

	}
}