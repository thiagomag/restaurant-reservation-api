-- Inserindo endereços
INSERT INTO endereco (logradouro, numero, bairro, cidade, estado, cep) VALUES
('Rua A', 100, 'Centro', 'Sao Paulo', 'SP', '01000-000'),
('Rua B', 200, 'Bela Vista', 'Rio de Janeiro', 'RJ', '22000-000'),
('Rua C', 300, 'Savassi', 'Belo Horizonte', 'MG', '30100-000');

-- Inserindo usuários
INSERT INTO usuario ( nome, email, telefone) VALUES
('Carlos Silva', 'carlos@email.com', '(11) 99999-1111'),
('Mariana Souza', 'mariana@email.com', '(21) 98888-2222'),
('Ricardo Mendes', 'ricardo@email.com', '(31) 97777-3333');

---- Inserindo restaurantes
INSERT INTO restaurante (nome, endereco_id, tipo_cozinha, horarioFuncionamento, capacidade) VALUES
('Restaurante Sabor', 1, 'ITALIANA', '10:00 - 22:00', 50),
( 'Cantina da Esquina', 2, 'FRANCESA', '11:00 - 23:00', 40),
( 'Churrascaria Fogo Alto', 3, 'STEAKHOUSE', '12:00 - 23:00', 60);

-- Inserindo mesas (2 mesas por restaurante)
INSERT INTO mesa (id, restaurante_id, numero_mesa, capacidade) VALUES
('001-001', 1, 1, 2),
('001-002', 1, 2, 2),
('002-001', 2, 1, 2),
('002-002', 2, 2, 2),
('003-001', 3, 1, 2),
('003-002', 3, 2, 2);


-- Inserindo reservas
INSERT INTO reserva (id ,usuario_id, restaurante_id, data_hora_reserva, numero_de_pessoas) VALUES
(1 ,1, 1, '2025-03-05 19:00:00', 2),
(2 ,2, 2, '2025-03-06 20:30:00', 2),
(3 ,3, 3, '2025-03-07 21:00:00', 2);

-- Associando reservas às mesas
INSERT INTO reserva_mesa (reserva_id, mesa_id) VALUES
(1, '001-001'), -- Usuário 1 reservou mesa 001-001 no Restaurante 1
(2, '002-001'), -- Usuário 2 reservou mesa 002-002 no Restaurante 2
(3, '003-001'); -- Usuário 3 reservou mesa 003-001 no Restaurante 3

INSERT INTO avaliacao (reserva_id, nota, comentario) VALUES
(1, 5, 'Excelente atendimento!'),
(2, 4, 'Comida muito boa!'),
(3, 3, 'Preço alto.');

ALTER SEQUENCE reserva_id_seq RESTART WITH 4;
ALTER SEQUENCE restaurante_id_seq RESTART WITH 4;
ALTER SEQUENCE endereco_id_seq RESTART WITH 4;
ALTER SEQUENCE usuario_id_seq RESTART WITH 4;
ALTER SEQUENCE avaliacao_id_seq RESTART WITH 4;