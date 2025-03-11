-- Limpeza dos dados
DROP TABLE IF EXISTS reserva_mesa;
DROP TABLE IF EXISTS reserva;
DROP TABLE IF EXISTS mesa;
DROP TABLE IF EXISTS restaurante;
DROP TABLE IF EXISTS endereco;
DROP TABLE IF EXISTS usuario;
DROP TABLE IF EXISTS avaliacao;


---- Reiniciar as sequências
--ALTER SEQUENCE reserva_id_seq RESTART WITH 1;
--ALTER SEQUENCE usuario_id_seq RESTART WITH 1;
--ALTER SEQUENCE endereco_id_seq RESTART WITH 1;
--ALTER SEQUENCE restaurante_id_seq RESTART WITH 1;

--DELETE FROM reserva;
--DELETE FROM mesa;
--DELETE FROM reserva_mesa;
--DELETE FROM restaurante;
--DELETE FROM endereco;
--DELETE FROM usuario;
--DELETE FROM avaliacao;
