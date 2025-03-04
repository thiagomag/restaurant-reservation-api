-- Inserindo endereços
INSERT INTO endereco (id, logradouro, numero, bairro, cidade, estado, cep) VALUES
(1, 'Rua A', 100, 'Centro', 'São Paulo', 'SP', '01000-000'),
(2, 'Rua B', 200, 'Bela Vista', 'Rio de Janeiro', 'RJ', '22000-000'),
(3, 'Rua C', 300, 'Savassi', 'Belo Horizonte', 'MG', '30100-000');

-- Inserindo restaurantes
INSERT INTO restaurante (id, nome, endereco_id, tipoCozinha, horarioFuncionamento, capacidade) VALUES
(1, 'Restaurante Sabor', 1, 'ITALIANA', '10:00 - 22:00', 50),
(2, 'Cantina da Esquina', 2, 'BRASILEIRA', '11:00 - 23:00', 40),
(3, 'Churrascaria Fogo Alto', 3, 'CHURRASCO', '12:00 - 23:00', 60);

-- Inserindo mesas (2 mesas por restaurante)
INSERT INTO mesa (id, restaurante_id, numeroMesa, capacidade) VALUES
('001-001', 1, 1, 2),
('001-002', 1, 2, 2),
('002-001', 2, 1, 2),
('002-002', 2, 2, 2),
('003-001', 3, 1, 2),
('003-002', 3, 2, 2);

-- Inserindo usuários
INSERT INTO usuario (id, nome, email, telefone) VALUES
(1, 'Carlos Silva', 'carlos@email.com', '(11) 99999-1111'),
(2, 'Mariana Souza', 'mariana@email.com', '(21) 98888-2222'),
(3, 'Ricardo Mendes', 'ricardo@email.com', '(31) 97777-3333');


-- Inserindo reservas
INSERT INTO reserva (id, usuario_id, restaurante_id, dataHoraReserva, numeroDePessoas) VALUES
(1, 1, 1, '2025-03-05 19:00:00', 2),
(2, 2, 2, '2025-03-06 20:30:00', 4),
(3, 3, 3, '2025-03-07 21:00:00', 3);

-- Associando reservas às mesas
INSERT INTO reserva_mesa (reserva_id, mesa_id) VALUES
(1, '001-001'), -- Usuário 1 reservou mesa 001-001 no Restaurante 1
(2, '002-001'), -- Usuário 2 reservou mesa 002-002 no Restaurante 2
(3, '003-001'); -- Usuário 3 reservou mesa 003-001 no Restaurante 3