#language: pt

Funcionalidade: Avaliação de Reservas

  Cenario: Avaliar uma reserva com sucesso
    Dado que existe uma reserva válida
    Quando eu envio a requisição de avaliação
    Então o sistema deve retornar status 201
    E a resposta deve conter os dados da avaliação