package uns.ac.rs.MailApi.common;

import uns.ac.rs.MailApi.entity.Folder;
import uns.ac.rs.MailApi.entity.Message;

import java.util.List;

public class CopyRuleHandler implements RuleHandler {

    /**
     * Copies an e-mail to the passed folder.
     *
     * @param List containing all messages.
     * @param Message index in that list.
     * @param Folder in which you want to copy that message.
     */
    @Override
    public void applyRule(List<Message> messages, int index, Folder folder) {
        Message copy = new Message(messages.get(index));

        copy.setId(null);
        copy.setFolder(folder);

        folder.getMessages().add(copy);
    }

}
