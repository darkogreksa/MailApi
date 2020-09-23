package uns.ac.rs.MailApi.common;

import uns.ac.rs.MailApi.entity.Folder;
import uns.ac.rs.MailApi.entity.Message;

import java.util.List;

public class MoveRuleHandler implements RuleHandler {

    /**
     * Moves an e-mail to the passed folder.
     *
     * @param List containing all messages.
     * @param Message index in that list.
     * @param Folder where you want to move the e-mail.
     */
    @Override
    public void applyRule(List<Message> messages, int index, Folder folder) {
        Message message = messages.get(index);

        message.setFolder(folder);
        folder.getMessages().add(message);
    }

}
