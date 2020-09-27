INSERT INTO users (first_name, last_name, username, password) VALUES ("pera", "pera", "pera", "$2a$04$zUzf/oVgOCvnm4FJK0TnxOLkIxjaFKiPhmFbRsIrZZ2c12tVed/DC");
INSERT INTO users (first_name, last_name, username, password) VALUES ("Vule","Vule","vule","$2a$10$NyaltQLE9xiwjHb9EdNFW.J8DvXrfr0wKKPXDwXzA4Cj6epBhBXou");

INSERT INTO accounts (smtp_address, smtp_port, in_server_type, in_server_address, in_server_port, username, password, display_name, _user) VALUES ("smtp.gmail.com", 587, 1, "imap.gmail.com", 993, "vas mail do gmail", "tvoja i djidjijeva sifra", "MVS Account 1", 1);
INSERT INTO accounts (smtp_address, smtp_port, in_server_type, in_server_address, in_server_port, username, password, display_name, _user) VALUES ("smtp.gmail.com", 587, 1, "imap.gmail.com", 993, "jovanovicvukasin99", "tvoja i djidjijeva sifra", "MVS Account 2", 2);

INSERT INTO folders (name, parent_folder, account) VALUES ("Inbox", null, 1);
INSERT INTO folders (name, parent_folder, account) VALUES ("Outbox", null, 1);
INSERT INTO folders (name, parent_folder, account) VALUES ("Drafts", null, 1);

INSERT INTO folders (name, parent_folder, account) VALUES ("Primary", 1, 1);
INSERT INTO folders (name, parent_folder, account) VALUES ("Spam", 1, 1);

--INSERT INTO rules (rule_condition, rule_operation, value, folder) VALUES (1, 0, "slankamenacmilica@gmail.com", 4);
--INSERT INTO rules (rule_condition, rule_operation, value, folder) VALUES (1, 2, "mvsuser1", 1);
--INSERT INTO rules (rule_condition, rule_operation, value, folder) VALUES (1, 1, "mvsuseeer", 4);
--INSERT INTO rules (rule_condition, rule_operation, value, folder) VALUES (1, 0, "mvsuser3@gmail.com", 5);

INSERT INTO folders (name, parent_folder, account) VALUES ("Inbox", null, 0);
INSERT INTO folders (name, parent_folder, account) VALUES ("Outbox", null, 0);
INSERT INTO folders (name, parent_folder, account) VALUES ("Drafts", null, 0);

INSERT INTO contacts (display_name, email, first_name, last_name, note, user) VALUES ("Milica", "slankamenacmilica@gmail.com", "Milica", "Slankamenac", "Test", 2);
INSERT INTO contacts (display_name, email, first_name, last_name, note, user) VALUES ("Vukasin", "jovanovicvukasin99@gmail.com", "Vukasin", "Jovanovic", "Test", 1);
INSERT INTO contacts (display_name, email, first_name, last_name, note, user) VALUES ("Snezana", "snezanaperovic1998@gmail.com", "Snezana", "Perovic", "Test", 1);

INSERT INTO photos (path, contact) VALUES ("images1.jpg", 1);
INSERT INTO photos (path, contact) VALUES ("image2.jpg", 2);
INSERT INTO photos (path, contact) VALUES ("image3.jpg", 3);

INSERT INTO authority (name) VALUES ("USER");

INSERT INTO user_authority (user_id, authority_id) VALUES (1, 1);
INSERT INTO user_authority (user_id, authority_id) VALUES (2, 1);

INSERT INTO tags (name, user) VALUES ("tag1", 1);

INSERT INTO messages (message_from, message_to, bcc, cc, subject, content, account, unread, folder) VALUES ("slanamenacmilica@gmail.com", "mvsuser1@gmail.com, nijeUContacts@gmail.com", "slankamenacmilica@gmail.com", "nijeUContacts@gmail.com", "Subject 1", "Test 1", 1, true, 1);
INSERT INTO messages (message_from, message_to, bcc, cc, subject, content, account, unread, folder) VALUES ("snezanaperovic1998@gmail.com", "mvsuser1@gmail.com", "snezanaperovic1998@gmail.com", "slankamenacmilica@gmail.com", "Subject 2", "Test 2", 2, true, 6);
INSERT INTO messages (message_from, message_to, bcc, cc, subject, content, account, unread, folder) VALUES ("jovanovicvukasin99@gmail.com", "mvsuser22@gmail.com", NULL, NULL, "Subject", "Test3", 1, false, 1);

--INSERT INTO attachments (data, mime_type, name, message) VALUES ("data", "mime type", "name", 1);

--INSERT INTO message_tags (message_id, tag_id) VALUES (1,1);