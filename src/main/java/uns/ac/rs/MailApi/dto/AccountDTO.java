package uns.ac.rs.MailApi.dto;

import uns.ac.rs.MailApi.entity.Account;

public class AccountDTO {

    private Integer id;
    private String smtpAddress;
    private int smtpPort;
    private int inServerType;
    private String inServerAddress;
    private int inServerPort;
    private String username;
    private String password;
    private String displayName;

    public AccountDTO(Account account) {
        super();
        this.id = account.getId();
        this.smtpAddress = account.getSmtpAddress();
        this.smtpPort = account.getSmtpPort();
        this.inServerType = account.getInServerType();
        this.inServerAddress = account.getInServerAddress();
        this.inServerPort = account.getInServerPort();
        this.username = account.getUsername();
        this.password = account.getPassword();
        this.displayName = account.getDisplayName();
    }

    public AccountDTO() {

    }

    // ----- GETTERS AND SETTERS

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSmtpAddress() {
        return smtpAddress;
    }

    public void setSmtpAddress(String smtpAddress) {
        this.smtpAddress = smtpAddress;
    }

    public int getSmtpPort() {
        return smtpPort;
    }

    public void setSmtpPort(int smtpPort) {
        this.smtpPort = smtpPort;
    }

    public int getInServerType() {
        return inServerType;
    }

    public void setInServerType(int inServerType) {
        this.inServerType = inServerType;
    }

    public String getInServerAddress() {
        return inServerAddress;
    }

    public void setInServerAddress(String inServerAddress) {
        this.inServerAddress = inServerAddress;
    }

    public int getInServerPort() {
        return inServerPort;
    }

    public void setInServerPort(int inServerPort) {
        this.inServerPort = inServerPort;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }
}
