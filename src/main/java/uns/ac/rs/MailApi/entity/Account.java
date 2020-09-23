package uns.ac.rs.MailApi.entity;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.LAZY;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;


	@Entity
	@Table(name = "accounts")
	public class Account {

		// ----- FIELDS

		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		@Column(name = "id", columnDefinition = "BIGINT", unique = true, nullable = false)
		private Integer id;

		@Column(name = "smtp_address", columnDefinition = "VARCHAR(250)", nullable = false)
		private String smtpAddress;

		@Column(name = "smtp_port", columnDefinition = "INT", nullable = false)
		private int smtpPort;

		@Column(name = "in_server_type", columnDefinition = "TINYINT", nullable = false)
		private int inServerType;

		@Column(name = "in_server_address", columnDefinition = "VARCHAR(250)", nullable = false)
		private String inServerAddress;

		@Column(name = "in_server_port", columnDefinition = "INT", nullable = false)
		private int inServerPort;

		@Column(name = "username", columnDefinition = "VARCHAR(20)", unique = true, nullable = true)
		private String username;

		@Column(name = "password", columnDefinition = "VARCHAR(20)", nullable = true)
		private String password;

		@Column(name = "display_name", columnDefinition = "VARCHAR(100)", nullable = false)
		private String displayName;

		@OneToMany(cascade = { ALL }, fetch = LAZY, mappedBy = "account")
		private List<Folder> folders;

		@ManyToOne
		@JoinColumn(name = "_user", referencedColumnName = "id", nullable = false)
		private User user;

		@Column(name = "last_sync_time", columnDefinition = "TIMESTAMP", nullable = true)
		private Timestamp lastSyncTime;

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

		public List<Folder> getFolders() {
			return folders;
		}

		public void setFolders(List<Folder> folders) {
			this.folders = folders;
		}

		public User getUser() {
			return user;
		}

		public void setUser(User user) {
			this.user = user;
		}

		public Timestamp getLastSyncTime() {
			return lastSyncTime;
		}

		public void setLastSyncTime(Timestamp lastSyncTime) {
			this.lastSyncTime = lastSyncTime;
		}

		@Override
		public String toString() {
			return "Account [username=" + username + ", password=" + password + ", displayName=" + displayName + "]";
		}

	}


