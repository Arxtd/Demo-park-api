package com.arthur.demoparkapi.web.controller;

import com.arthur.demoparkapi.entity.Usuario;
import com.arthur.demoparkapi.service.UsuarioService;
import com.arthur.demoparkapi.web.controller.dto.UsuarioCreateDto;
import com.arthur.demoparkapi.web.controller.dto.UsuarioResponseDTO;
import com.arthur.demoparkapi.web.controller.dto.UsuarioSenhaDto;
import com.arthur.demoparkapi.web.controller.dto.mapper.UsuarioMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    @PostMapping
    public ResponseEntity<UsuarioResponseDTO> create(@Valid @RequestBody UsuarioCreateDto usuarioCreateDto) {
        Usuario user = usuarioService.salvar(UsuarioMapper.toUsuario(usuarioCreateDto));
        return  ResponseEntity.status(HttpStatus.CREATED).body(UsuarioMapper.toDto(user));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> getById( @PathVariable Long id) {
        Usuario user = usuarioService.buscarPorId(id);
        return  ResponseEntity.ok(UsuarioMapper.toDto(user));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> updatedPassword(@PathVariable Long id, @RequestBody UsuarioSenhaDto dto) {
        Usuario user = usuarioService.editarSenha(id, dto.getSenhaAtual(), dto.getNovaSenha(), dto.getConfirmaSenha());
        return  ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<UsuarioResponseDTO>> getAll() {
        List<Usuario> users = usuarioService.buscarTodos();
        return  ResponseEntity.ok(UsuarioMapper.toListDto(users));
    }
}
