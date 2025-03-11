-- Criar sequências
CREATE SEQUENCE IF NOT EXISTS endereco_id_seq START WITH 1;
CREATE SEQUENCE IF NOT EXISTS restaurante_id_seq START WITH 1;
CREATE SEQUENCE IF NOT EXISTS usuario_id_seq START WITH 1;
CREATE SEQUENCE IF NOT EXISTS reserva_id_seq START WITH 1;


-- Criação das tabelas
CREATE TABLE endereco (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    logradouro VARCHAR(255),
    numero INT,
    bairro VARCHAR(255),
    cidade VARCHAR(255),
    estado VARCHAR(255),
    cep VARCHAR(20)
);

CREATE TABLE restaurante (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(255),
    endereco_id BIGINT,
    tipo_cozinha VARCHAR(50),
    horarioFuncionamento VARCHAR(50),
    capacidade INT,
    FOREIGN KEY (endereco_id) REFERENCES endereco(id)
);

CREATE TABLE mesa (
    id VARCHAR(7) PRIMARY KEY,
    restaurante_id BIGINT,
    numero_mesa INT,
    capacidade INT,
    FOREIGN KEY (restaurante_id) REFERENCES restaurante(id)
);

CREATE TABLE usuario (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(255),
    email VARCHAR(255),
    telefone VARCHAR(20)
);

CREATE TABLE reserva (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    usuario_id BIGINT,
    restaurante_id BIGINT,
    data_hora_reserva TIMESTAMP,
    numero_de_pessoas INT,
    FOREIGN KEY (usuario_id) REFERENCES usuario(id),
    FOREIGN KEY (restaurante_id) REFERENCES restaurante(id)
);

CREATE TABLE reserva_mesa (
    reserva_id BIGINT,
    mesa_id VARCHAR(20),
    PRIMARY KEY (reserva_id, mesa_id),
    FOREIGN KEY (reserva_id) REFERENCES reserva(id),
    FOREIGN KEY (mesa_id) REFERENCES mesa(id)
);

CREATE TABLE avaliacao (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    reserva_id BIGINT UNIQUE,
    nota INT,
    comentario VARCHAR(255),
    FOREIGN KEY (reserva_id) REFERENCES reserva(id)
);


