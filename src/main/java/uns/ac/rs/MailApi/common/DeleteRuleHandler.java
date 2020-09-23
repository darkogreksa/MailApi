package uns.ac.rs.MailApi.common;

import uns.ac.rs.MailApi.entity.Folder;
import uns.ac.rs.MailApi.entity.Message;

import java.util.List;

public class DeleteRuleHandler implements RuleHandler {

    /**
     * Deletes an e-mail from the passed folder.
     *
     * @param List containing all messages.
     * @param Message index in that list.
     * @param Folder in which the mail is located.
     */
    @Override
    public void applyRule(List<Message> messages, int index, Folder folder) {
        folder.getMessages().remove(messages.get(index));
    }

}
