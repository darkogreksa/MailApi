package uns.ac.rs.MailApi.lucene.model.handlers;

import java.io.File;

import uns.ac.rs.MailApi.lucene.model.IndexUnit;
import uns.ac.rs.MailApi.lucene.model.UploadModel;


public abstract class DocumentHandler {
    /**
     * Od prosledjene datoteke se konstruise Lucene Document
     *
     * @param file
     *            datoteka u kojoj se nalaze informacije
     * @return Lucene Document
     */
    public abstract IndexUnit getIndexUnit(File file);
    public abstract String getText(File file);
    public abstract UploadModel getModel(File file);

}

