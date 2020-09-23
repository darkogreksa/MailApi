package uns.ac.rs.MailApi.common;

import uns.ac.rs.MailApi.entity.Folder;
import uns.ac.rs.MailApi.entity.Message;

import java.util.List;

public interface RuleHandler {

    public void applyRule(List<Message> messages, int index, Folder folder);

}
