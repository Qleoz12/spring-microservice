use microservices;
CREATE TABLE IF NOT EXISTS clients (
  client_id varchar(255) NOT NULL,
  full_name varchar(255),
  PRIMARY KEY (client_id)
);

-- Products

CREATE TABLE IF NOT EXISTS products (
  product_id varchar(6) NOT NULL,
  document_client varchar(255),
  card_id varchar(255),
  PRIMARY KEY (product_id),
  UNIQUE KEY card_unique (card_id),
  FOREIGN KEY (document_client) REFERENCES clients (client_id)
    ON UPDATE NO ACTION
    ON DELETE NO ACTION
);

-- Cards

CREATE TABLE IF NOT EXISTS cards (
  card_id varchar(255) NOT NULL,
  type_of_card varchar(10),
  titular_name varchar(100),
  is_activated boolean,
  is_blocked boolean,
  balance int,
  pre_balance int,
  product_id varchar(6),
  expiration_date date,
  PRIMARY KEY (card_id),
  UNIQUE KEY product_unique (product_id),
  FOREIGN KEY (product_id) REFERENCES products (product_id)
    ON UPDATE NO ACTION
    ON DELETE NO ACTION
);

-- Transactions

CREATE TABLE IF NOT EXISTS transactions (
  transaction_id int NOT NULL,
  card_id varchar(255),
  transaction_date DATETIME,
  is_valid boolean,
  price int,
  PRIMARY KEY (transaction_id),
  FOREIGN KEY (card_id) REFERENCES cards (card_id)
    ON UPDATE NO ACTION
    ON DELETE NO ACTION
);

-- tables for auth
CREATE TABLE IF NOT EXISTS profile (
 profile_id BIGINT AUTO_INCREMENT PRIMARY KEY,
 name varchar(80) NOT NULL
);

CREATE TABLE IF NOT EXISTS permission (
 permission_id BIGINT AUTO_INCREMENT PRIMARY KEY,
 name varchar(80) NOT NULL,
 code varchar(80) NOT NULL,
 ACTION varchar(80) NOT NULL
);

CREATE TABLE IF NOT EXISTS usuario (
 usuario_id BIGINT AUTO_INCREMENT PRIMARY KEY,
 name varchar(80) NOT NULL,
 username varchar(80) NOT NULL,
 password varchar(80) NOT NULL,
 department varchar(80) NOT NULL,
 position varchar(80) NOT NULL,
 domain varchar(80) NOT NULL,
 authentication_type varchar(80) NOT NULL,
 company_id bigint NOT NULL,
 profile_id bigint NOT NULL
);

CREATE TABLE IF NOT EXISTS `company` (
  company_id bigint NOT NULL AUTO_INCREMENT,
  account varchar(255) NOT NULL,
  caixa varchar(255) NOT NULL,
  name varchar(255) NOT NULL,
  password varchar(255) NOT NULL,
  username varchar(255) NOT NULL,
  PRIMARY KEY (company_id)
);


-- ALTERs

ALTER TABLE microservices.products ADD CONSTRAINT products_FK FOREIGN KEY (card_id) REFERENCES microservices.cards(card_id);

ALTER TABLE microservices.transactions MODIFY COLUMN transaction_id VARCHAR(36) NOT NULL;

-- seeds 

INSERT INTO microservices.clients (client_id, full_name) VALUES('102030', 'demo');
INSERT INTO microservices.products (product_id, document_client, card_id) VALUES('102030', '102030', NULL);

INSERT INTO microservices.profile (name) VALUES('Administrador');
INSERT INTO microservices.profile (name) VALUES( 'Usuario');

INSERT INTO microservices.permission (name, code, action) VALUES('Inserir', 'I', '/post');
INSERT INTO microservices.permission (name, code, action) VALUES( 'Alterar', 'A', '/put');
INSERT INTO microservices.permission (name, code, action) VALUES('Excluir', 'E', '/delete');

INSERT INTO microservices.usuario(name, username, password, department, `position`, `domain`, authentication_type, company_id, profile_id)  VALUES('Administrador', 'admin', '$2a$10$J2/0CvwznORU1TOfEJjo0uxMaJn1ThxTTzfmHjKhxBJ8jkFXwbF.2', 'Diretoria', 'Presidente', 'empresaAdministrador', 'LDAP', 1, 1);

INSERT INTO microservices.company ( name, account, username, password, caixa) VALUES('Empresa de Tecnologia Ltda', 'Caixa', 'tecnologia', '', 'Empresa muito rica...');
