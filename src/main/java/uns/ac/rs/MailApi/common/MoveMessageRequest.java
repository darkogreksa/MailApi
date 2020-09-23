package uns.ac.rs.MailApi.common;

/**
 * This is a wrapper class for a user request
 * to move a message from one folder to another.
 *
 * Contains message id and target folder id.
 */
public class MoveMessageRequest {

    private int messageId;
    private int folderId;

    public int getMessageId() {
        return messageId;
    }

    public void setMessageId(int messageId) {
        this.messageId = messageId;
    }

    public int getFolderId() {
        return folderId;
    }

    public void setFolderId(int folderId) {
        this.folderId = folderId;
    }

}
