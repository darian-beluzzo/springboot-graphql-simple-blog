create sequence seq_address;
create sequence seq_author;
create sequence seq_post;
create sequence seq_comment;

CREATE TABLE address (
  id        BIGINT default seq_address.nextval primary key,
  street    VARCHAR(100) NOT NULL,
  suite     VARCHAR(100) NOT NULL,
  zipcode   VARCHAR(100) NOT NULL,
  city      VARCHAR(100) NOT NULL,
  state     CHAR(2) NOT NULL,
  latitude  NUMBER(20,16) NOT NULL,
  longitude NUMBER(20,16) NOT NULL
);

CREATE TABLE author (
  id       BIGINT primary key,
  name     VARCHAR(100) NOT NULL,
  username VARCHAR(100) NOT NULL,
  email    VARCHAR(100) NOT NULL,
  address_id BIGINT
);
ALTER TABLE author ADD FOREIGN KEY (address_id) REFERENCES address(id);
ALTER TABLE author ADD UNIQUE KEY (username);

CREATE TABLE post (
  id      BIGINT default seq_post.nextval primary key,
  title   VARCHAR(200) NOT NULL,
  body    VARCHAR(2000) NOT NULL,
  author_id BIGINT NOT NULL
);
ALTER TABLE post ADD FOREIGN KEY (author_id) REFERENCES author(id);

CREATE TABLE comment (
  id      BIGINT default seq_post.nextval primary key,
  name    VARCHAR(200) NOT NULL,
  email   VARCHAR(150) NOT NULL,
  body    VARCHAR(2000) NOT NULL,
  post_id BIGINT NOT NULL
);
ALTER TABLE comment ADD FOREIGN KEY (post_id) REFERENCES post(id);
