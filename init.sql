CREATE TABLE endereco (
                          id BIGSERIAL PRIMARY KEY,
                          created_at TIMESTAMP WITHOUT TIME ZONE DEFAULT CURRENT_TIMESTAMP,
                          updated_at TIMESTAMP WITHOUT TIME ZONE DEFAULT CURRENT_TIMESTAMP,
                          deleted_tmsp TIMESTAMP WITHOUT TIME ZONE,
                          logradouro VARCHAR(255) NOT NULL,
                          numero INT NOT NULL,
                          bairro VARCHAR(100) NOT NULL,
                          cidade VARCHAR(100) NOT NULL,
                          estado VARCHAR(50) NOT NULL,
                          cep VARCHAR(20) NOT NULL
);

CREATE TABLE restaurante (
                             id BIGSERIAL PRIMARY KEY,
                             created_at TIMESTAMP WITHOUT TIME ZONE DEFAULT CURRENT_TIMESTAMP,
                             updated_at TIMESTAMP WITHOUT TIME ZONE DEFAULT CURRENT_TIMESTAMP,
                             deleted_tmsp TIMESTAMP WITHOUT TIME ZONE,
                             nome VARCHAR(255) NOT NULL,
                             endereco_id BIGINT UNIQUE,
                             tipo_cozinha VARCHAR(50) NOT NULL,
                             horario_funcionamento VARCHAR(255),
                             capacidade INT NOT NULL,
                             CONSTRAINT fk_restaurante_endereco FOREIGN KEY (endereco_id) REFERENCES endereco(id)
);

CREATE INDEX idx_restaurante_deleted_tmsp ON restaurante(deleted_tmsp);




CREATE INDEX idx_endereco_deleted_tmsp ON endereco(deleted_tmsp);

CREATE TABLE usuario (
                         id BIGSERIAL PRIMARY KEY,
                         created_at TIMESTAMP WITHOUT TIME ZONE DEFAULT CURRENT_TIMESTAMP,
                         updated_at TIMESTAMP WITHOUT TIME ZONE DEFAULT CURRENT_TIMESTAMP,
                         deleted_tmsp TIMESTAMP WITHOUT TIME ZONE,
                         nome VARCHAR(255) NOT NULL,
                         email VARCHAR(255) UNIQUE NOT NULL,
                         telefone VARCHAR(20)
);

CREATE INDEX idx_usuario_deleted_tmsp ON usuario(deleted_tmsp);

CREATE TABLE mesa (
                      id VARCHAR(255) PRIMARY KEY,
                      created_at TIMESTAMP WITHOUT TIME ZONE DEFAULT CURRENT_TIMESTAMP,
                      updated_at TIMESTAMP WITHOUT TIME ZONE DEFAULT CURRENT_TIMESTAMP,
                      deleted_tmsp TIMESTAMP WITHOUT TIME ZONE,
                      restaurante_id BIGINT NOT NULL,
                      numero_mesa INT,
                      capacidade INT DEFAULT 2 NOT NULL,
                      CONSTRAINT fk_mesa_restaurante FOREIGN KEY (restaurante_id) REFERENCES restaurante(id)
);

CREATE INDEX idx_mesa_deleted_tmsp ON mesa(deleted_tmsp);


CREATE TABLE reserva (
                         id BIGSERIAL PRIMARY KEY,
                         created_at TIMESTAMP WITHOUT TIME ZONE DEFAULT CURRENT_TIMESTAMP,
                         updated_at TIMESTAMP WITHOUT TIME ZONE DEFAULT CURRENT_TIMESTAMP,
                         deleted_tmsp TIMESTAMP WITHOUT TIME ZONE,
                         usuario_id BIGINT NOT NULL,
                         restaurante_id BIGINT NOT NULL,
                         data_hora_reserva TIMESTAMP WITHOUT TIME ZONE NOT NULL,
                         numero_de_pessoas INT NOT NULL,
                         CONSTRAINT fk_reserva_usuario FOREIGN KEY (usuario_id) REFERENCES usuario(id),
                         CONSTRAINT fk_reserva_restaurante FOREIGN KEY (restaurante_id) REFERENCES restaurante(id)
);

CREATE TABLE reserva_mesa (
                              reserva_id BIGINT NOT NULL,
                              mesa_id VARCHAR(255) NOT NULL,
                              PRIMARY KEY (reserva_id, mesa_id),
                              CONSTRAINT fk_reserva_mesa_reserva FOREIGN KEY (reserva_id) REFERENCES reserva(id) ON DELETE CASCADE,
                              CONSTRAINT fk_reserva_mesa_mesa FOREIGN KEY (mesa_id) REFERENCES mesa(id) ON DELETE CASCADE
);

CREATE INDEX idx_reserva_deleted_tmsp ON reserva(deleted_tmsp);


CREATE TABLE avaliacao (
                           id BIGSERIAL PRIMARY KEY,
                           created_at TIMESTAMP WITHOUT TIME ZONE DEFAULT CURRENT_TIMESTAMP,
                           updated_at TIMESTAMP WITHOUT TIME ZONE DEFAULT CURRENT_TIMESTAMP,
                           deleted_tmsp TIMESTAMP WITHOUT TIME ZONE,
                           reserva_id BIGINT NOT NULL UNIQUE,
                           nota INT NOT NULL,
                           comentario TEXT DEFAULT '',
                           CONSTRAINT fk_avaliacao_reserva FOREIGN KEY (reserva_id) REFERENCES reserva(id) ON DELETE CASCADE
);

CREATE INDEX idx_avaliacao_deleted_tmsp ON avaliacao(deleted_tmsp);
