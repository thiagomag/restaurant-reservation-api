-- Limpeza dos dados
DELETE FROM reserva_mesa;
DELETE FROM reserva;
DELETE FROM mesa;
DELETE FROM restaurante;
DELETE FROM endereco;
DELETE FROM usuario;

-- Reiniciar as sequências
ALTER SEQUENCE reserva_id_seq RESTART WITH 1;
ALTER SEQUENCE usuario_id_seq RESTART WITH 1;
ALTER SEQUENCE endereco_id_seq RESTART WITH 1;
ALTER SEQUENCE restaurante_id_seq RESTART WITH 1;