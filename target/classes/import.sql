INSERT INTO users (first_name, last_name, username, password) VALUES ("pera", "pera", "pera", "$2a$04$zUzf/oVgOCvnm4FJK0TnxOLkIxjaFKiPhmFbRsIrZZ2c12tVed/DC");
INSERT INTO users (first_name, last_name, username, password) VALUES ("Vule","Vule","vule","$2a$10$NyaltQLE9xiwjHb9EdNFW.J8DvXrfr0wKKPXDwXzA4Cj6epBhBXou");

INSERT INTO accounts (smtp_address, smtp_port, in_server_type, in_server_address, in_server_port, username, password, display_name, _user) VALUES ("smtp.gmail.com", 587, 1, "imap.gmail.com", 993, "mail@gmail.com", "password", "MailApi Acc", 1);
INSERT INTO accounts (smtp_address, smtp_port, in_server_type, in_server_address, in_server_port, username, password, display_name, _user) VALUES ("smtp.gmail.com", 587, 1, "imap.gmail.com", 993, "email@gmail.com", "password", "MailApi Acc 2", 2);

INSERT INTO folders (name, parent_folder, account) VALUES ("Inbox", null, 1);
INSERT INTO folders (name, parent_folder, account) VALUES ("Outbox", null, 1);
INSERT INTO folders (name, parent_folder, account) VALUES ("Drafts", null, 1);

INSERT INTO folders (name, parent_folder, account) VALUES ("Primary", 1, 1);
INSERT INTO folders (name, parent_folder, account) VALUES ("Spam", 1, 1);

INSERT INTO folders (name, parent_folder, account) VALUES ("Inbox", null, 0);
INSERT INTO folders (name, parent_folder, account) VALUES ("Outbox", null, 0);
INSERT INTO folders (name, parent_folder, account) VALUES ("Drafts", null, 0);

INSERT INTO contacts (display_name, email, first_name, last_name, note, user) VALUES ("Darko", "greksadarko@gmail.com", "Darko", "Greksa", "Tasdasfsd", 2);
INSERT INTO contacts (display_name, email, first_name, last_name, note, user) VALUES ("Laza", "laza@gmail.com", "Laza", "Lazic", "ASDsafas", 1);
INSERT INTO contacts (display_name, email, first_name, last_name, note, user) VALUES ("Zika", "zika@gmail.com", "Zika", "Zikic", "ASDsaffa", 1);

INSERT INTO photos (path, contact) VALUES ("images1.jpg", 1);
INSERT INTO photos (path, contact) VALUES ("image2.jpg", 2);
INSERT INTO photos (path, contact) VALUES ("image3.jpg", 3);

INSERT INTO authority (name) VALUES ("USER");

INSERT INTO user_authority (user_id, authority_id) VALUES (1, 1);
INSERT INTO user_authority (user_id, authority_id) VALUES (2, 1);

INSERT INTO tags (name, user) VALUES ("tag1", 1);

INSERT INTO messages (message_from, message_to, bcc, cc, subject, content, account, unread, folder) VALUES ("greksadarko@gmail.com", "mailapi@gmail.com", "neko@gmail.com", "neko@gmail.com", "Subject 1", "Test 1", 1, true, 1);
INSERT INTO messages (message_from, message_to, bcc, cc, subject, content, account, unread, folder) VALUES ("laza@gmail.com", "mailapi@gmail.com", "nesto@gmail.com", "neko@gmail.com", "Subject 2", "Test 2", 2, true, 1);
INSERT INTO messages (message_from, message_to, bcc, cc, subject, content, account, unread, folder) VALUES ("nekotreci@gmail.com", "mailapi@gmail.com", "nesto@gmail.com", "neko@gmail.com", "Subject 3", "Test 3", 2, true, 1);