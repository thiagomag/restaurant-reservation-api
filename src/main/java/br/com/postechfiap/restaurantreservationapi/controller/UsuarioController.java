package br.com.postechfiap.restaurantreservationapi.controller;

import br.com.postechfiap.restaurantreservationapi.dto.usuario.UsuarioRequest;
import br.com.postechfiap.restaurantreservationapi.dto.usuario.UsuarioResponse;
import br.com.postechfiap.restaurantreservationapi.interfaces.usuario.AtualizarCadastroUsuarioUseCase;
import br.com.postechfiap.restaurantreservationapi.interfaces.usuario.BuscarUsuarioPorEmailUseCase;
import br.com.postechfiap.restaurantreservationapi.interfaces.usuario.CadastrarUsuarioUseCase;
import br.com.postechfiap.restaurantreservationapi.interfaces.usuario.DeletarUsuarioUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/usuario")
@RequiredArgsConstructor
@Validated
@Tag(name = "Usuário", description = "API para gerenciar usuários")
public class UsuarioController {

    private final CadastrarUsuarioUseCase cadastrarUsuarioUseCase;
    private final BuscarUsuarioPorEmailUseCase buscarUsuarioPorEmailUseCase;
    private final AtualizarCadastroUsuarioUseCase atualizarCadastroUsuarioUseCase;
    private final DeletarUsuarioUseCase deletarUsuarioUseCase;

    @PostMapping
    @Operation(summary = "Cadastrar Usuário", description = "Cadastra um novo usuário.")
    public ResponseEntity<UsuarioResponse> cadastrarUsuario(@RequestBody @Valid UsuarioRequest dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(cadastrarUsuarioUseCase.execute(dto));
    }

    @GetMapping("/findByEmail")
    @Operation(summary = "Buscar Usuário por Email", description = "Busca usuário por email.")
    public ResponseEntity<UsuarioResponse> buscarUsuarioPorEmail(@RequestParam String email) {
        var response = buscarUsuarioPorEmailUseCase.execute(email);
        return ResponseEntity.ok(response);
    }

    @PutMapping
    @Operation(summary = "Atualizar Cadastro de Usuário", description = "Atualiza o cadastro de um usuário.")
    public ResponseEntity<UsuarioResponse> atualizarCadastroUsuario(@RequestBody UsuarioRequest dto) {
        return ResponseEntity.ok(atualizarCadastroUsuarioUseCase.execute(dto));
    }

    @DeleteMapping("/{usuarioId}")
    @Operation(summary = "Deletar Usuário", description = "Deleta um usuário.")
    public ResponseEntity<Void> deletarUsuario(@RequestParam Long usuarioId) {
        deletarUsuarioUseCase.execute(usuarioId);
        return ResponseEntity.noContent().build();
    }
}
