------------------
-- Setup
------------------
insert into address (id, street, suite, zipcode, city, state, latitude, longitude) values (-999, 'Rua Hum', 'Apto 1', '13171130', 'Sumare','SP',-22.83139154639112, -47.26588062973378);
insert into author (id, name, username, email, address_id) values (-999, 'John', 'john.doe', 'john.doe@email.com', -999);
insert into post (id, title, body, author_id) values (-999, 'My First Post', 'This is my first post!', -999);
commit;
