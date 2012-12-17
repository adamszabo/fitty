--DATABASE--
DROP DATABASE IF EXISTS fitness;
CREATE DATABASE fitness
  WITH OWNER = postgres
       ENCODING = 'UTF8'
       TABLESPACE = pg_default
       LC_COLLATE = 'Hungarian_Hungary.1250'
       LC_CTYPE = 'Hungarian_Hungary.1250'
       CONNECTION LIMIT = -1;
\c fitness

DROP SCHEMA IF EXISTS public CASCADE;

CREATE SCHEMA public
  AUTHORIZATION postgres;

GRANT ALL ON SCHEMA public TO postgres;
GRANT ALL ON SCHEMA public TO public;
COMMENT ON SCHEMA public
  IS 'standard public schema';

 --USER--
CREATE TABLE fitness_user
(
  id bigint NOT NULL,
  email character varying(255),
  enabled boolean,
  fullname character varying(255),
  lastlogin timestamp without time zone,
  lastloginip character varying(255),
  mobile character varying(255),
  numberofretries integer,
  password character varying(255),
  registration timestamp without time zone,
  username character varying(255),
  CONSTRAINT fitness_user_pkey PRIMARY KEY (id),
  CONSTRAINT fitness_user_email_key UNIQUE (email),
  CONSTRAINT fitness_user_username_key UNIQUE (username)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE fitness_user
  OWNER TO postgres;
       
       
--BASKET--
CREATE TABLE basket
(
  id bigint NOT NULL,
  delivered boolean,
  user_id bigint,
  CONSTRAINT basket_pkey PRIMARY KEY (id),
  CONSTRAINT fk762ca5e66c3b215d FOREIGN KEY (user_id)
      REFERENCES fitness_user (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE CASCADE
)
WITH (
  OIDS=FALSE
);
ALTER TABLE basket
  OWNER TO postgres;

--MEMBERSHIP--
  CREATE TABLE membership
(
  id bigint NOT NULL,
  expiredate timestamp without time zone,
  maxnumberofentries integer,
  numberofentries integer,
  price double precision,
  type character varying(255),
  basket_id bigint,
  CONSTRAINT membership_pkey PRIMARY KEY (id),
  CONSTRAINT fk26ef63f68dc21ee6 FOREIGN KEY (basket_id)
      REFERENCES basket (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE CASCADE
)
WITH (
  OIDS=FALSE
);
ALTER TABLE membership
  OWNER TO postgres;

   --PRODUCT--
  CREATE TABLE product
(
  id bigint NOT NULL,
  creation timestamp without time zone,
  details character varying(255),
  manufacturer character varying(255),
  name character varying(255),
  price double precision,
  CONSTRAINT product_pkey PRIMARY KEY (id)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE product
  OWNER TO postgres;
  
  --ORDERITEM--
  CREATE TABLE orderitem
(
  id bigint NOT NULL,
  quantity integer,
  basket_id bigint,
  product_id bigint,
  CONSTRAINT orderitem_pkey PRIMARY KEY (id),
  CONSTRAINT fk60163f617bbee2d FOREIGN KEY (product_id)
      REFERENCES product (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE CASCADE,
  CONSTRAINT fk60163f618dc21ee6 FOREIGN KEY (basket_id)
      REFERENCES basket (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE orderitem
  OWNER TO postgres;

 --ROLE--
  CREATE TABLE role
(
  id bigint NOT NULL,
  name character varying(255),
  user_id bigint,
  CONSTRAINT role_pkey PRIMARY KEY (id),
  CONSTRAINT fk26f4966c3b215d FOREIGN KEY (user_id)
      REFERENCES fitness_user (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE CASCADE
)
WITH (
  OIDS=FALSE
);
ALTER TABLE role
  OWNER TO postgres;
  
 --STORE--
  CREATE TABLE store
(
  id bigint NOT NULL,
  quantity integer,
  product_id bigint,
  CONSTRAINT store_pkey PRIMARY KEY (id),
  CONSTRAINT fk4c808c17bbee2d FOREIGN KEY (product_id)
      REFERENCES product (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE CASCADE
)
WITH (
  OIDS=FALSE
);
ALTER TABLE store
  OWNER TO postgres;
  
 --TRAINING--
  CREATE TABLE training
(
  id bigint NOT NULL,
  burnedcalories integer,
  isanalyzed boolean,
  review character varying(255),
  trainingstartdate timestamp without time zone,
  basket_id bigint,
  client_id bigint,
  trainer_id bigint,
  CONSTRAINT training_pkey PRIMARY KEY (id),
  CONSTRAINT fk4fea6cfa382195d FOREIGN KEY (client_id)
      REFERENCES fitness_user (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE CASCADE,
  CONSTRAINT fk4fea6cfa8dc21ee6 FOREIGN KEY (basket_id)
      REFERENCES basket (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE CASCADE,
  CONSTRAINT fk4fea6cfafcfad033 FOREIGN KEY (trainer_id)
      REFERENCES fitness_user (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE CASCADE
)
WITH (
  OIDS=FALSE
);
ALTER TABLE training
  OWNER TO postgres;

  
INSERT INTO fitness_user(
            id, email, enabled, fullname, lastlogin, lastloginip, mobile, 
            numberofretries, password, registration, username)
    VALUES (0, 'admin@fitness.hu', true, 'Admin', '2012-12-11 10:00:00.0', '0.0.0.0', '+36303464978', 
            0, 'admin', '2012-12-11 9:00:00.0', 'admin');
INSERT INTO role(
            id, name, user_id)
    VALUES (0, 'ROLE_USER', 0);
INSERT INTO product(
            id, creation, details, manufacturer, name, price)
    VALUES (3, '2012-12-11 11:00:00.0', 'Fekete színű', 'Nike', 'Kapus kesztyű', 12000);
INSERT INTO product(
            id, creation, details, manufacturer, name, price)
    VALUES (4, '2012-12-11 11:01:00.0', 'Fehér, piros csíkkal', 'Nike', 'Futó cipő', 24000);
INSERT INTO product(
            id, creation, details, manufacturer, name, price)
    VALUES (5, '2012-12-11 11:02:00.0', 'Kék színű', 'Adidas', 'Póló', 7000);
INSERT INTO product(
            id, creation, details, manufacturer, name, price)
    VALUES (6, '2012-12-11 11:02:00.0', 'Kék színű', 'Adidas', 'Póló', 7000);
    INSERT INTO product(
            id, creation, details, manufacturer, name, price)
    VALUES (7, '2012-12-11 11:02:00.0', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus sodales consectetur bibendum. Quisque quis tortor sed tortor pharetra pretium. Nunc vitae diam id ipsum gravida iaculis sagittis eu ligula.', 'Adidas', 'Póló', 7000);
INSERT INTO product(
            id, creation, details, manufacturer, name, price)
    VALUES (8, '2012-12-11 11:02:00.0', 'Kék színű', 'Adidas', 'Póló', 7000);
INSERT INTO product(
            id, creation, details, manufacturer, name, price)
    VALUES (9, '2012-12-11 11:02:00.0', 'Kék színű', 'Adidas', 'Póló', 7000);
INSERT INTO product(
            id, creation, details, manufacturer, name, price)
    VALUES (10, '2012-12-11 11:02:00.0', 'Kék színű', 'Adidas', 'Póló', 7000);
INSERT INTO product(
            id, creation, details, manufacturer, name, price)
    VALUES (11, '2012-12-11 11:02:00.0', 'Kék színű', 'Adidas', 'Póló', 7000);
INSERT INTO product(
            id, creation, details, manufacturer, name, price)
    VALUES (12, '2012-12-11 11:02:00.0', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus sodales consectetur bibendum. Quisque quis tortor sed tortor pharetra pretium. Nunc vitae diam id ipsum gravida iaculis sagittis eu ligula.', 'Adidas', 'Póló', 7000);
INSERT INTO product(
            id, creation, details, manufacturer, name, price)
    VALUES (13, '2012-12-11 11:02:00.0', 'Kék színű', 'Adidas', 'Póló', 7000);
INSERT INTO product(
            id, creation, details, manufacturer, name, price)
    VALUES (14, '2012-12-11 11:02:00.0', 'Kék színű', 'Adidas', 'Póló', 7000);
INSERT INTO product(
            id, creation, details, manufacturer, name, price)
    VALUES (15, '2012-12-11 11:02:00.0', 'Kék színű', 'Adidas', 'Póló', 7000);
INSERT INTO product(
            id, creation, details, manufacturer, name, price)
    VALUES (16, '2012-12-11 11:02:00.0', 'Kék színű', 'Adidas', 'Póló', 7000);
INSERT INTO product(
            id, creation, details, manufacturer, name, price)
    VALUES (17, '2012-12-11 11:02:00.0', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus sodales consectetur bibendum. Quisque quis tortor sed tortor pharetra pretium. Nunc vitae diam id ipsum gravida iaculis sagittis eu ligula.', 'Adidas', 'Póló', 7000);
INSERT INTO product(
            id, creation, details, manufacturer, name, price)
    VALUES (18, '2012-12-11 11:02:00.0', 'Kék színű', 'Adidas', 'Póló', 7000);
INSERT INTO product(
            id, creation, details, manufacturer, name, price)
    VALUES (19, '2012-12-11 11:02:00.0', 'Kék színű', 'Adidas', 'Póló', 7000);
INSERT INTO product(
            id, creation, details, manufacturer, name, price)
    VALUES (20, '2012-12-11 11:02:00.0', 'Kék színű', 'Adidas', 'Pólójaaaa', 7000);
