package com.javier.forohubchallengebackend.servicio;

import com.javier.forohubchallengebackend.dto.TopicoRequest;
import com.javier.forohubchallengebackend.dto.TopicoResponse;
import com.javier.forohubchallengebackend.entities.Topico;
import com.javier.forohubchallengebackend.repositorio.TopicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.javier.forohubchallengebackend.dto.RegistroTopicoDTO;
import java.util.NoSuchElementException;


import java.util.List;

@Service
public class TopicoService {

    @Autowired
    private TopicoRepository topicoRepository;

    public TopicoResponse registrarTopico(TopicoRequest request) {
        if (topicoRepository.existsByTituloAndMensaje(request.getTitulo(), request.getMensaje())) {
            throw new IllegalArgumentException("Ya existe un tópico con el mismo título y mensaje.");
        }

        Topico topico = new Topico(
                request.getTitulo(),
                request.getMensaje(),
                request.getAutor(),
                request.getCurso()
        );

        Topico guardado = topicoRepository.save(topico);

        return new TopicoResponse(
                guardado.getId(),
                guardado.getTitulo(),
                guardado.getMensaje(),
                guardado.getAutor(),
                guardado.getCurso(),
                guardado.getFechaCreacion() // ✅ nuevo campo
        );
    }

    public List<TopicoResponse> listar() {
        List<Topico> topicos = topicoRepository.findAll();
        return topicos.stream()
                .map(topico -> new TopicoResponse(
                        topico.getId(),
                        topico.getTitulo(),
                        topico.getMensaje(),
                        topico.getAutor(),
                        topico.getCurso(),
                        topico.getFechaCreacion() // ✅ nuevo campo
                ))
                .toList();
    }

    public void eliminarTopico(Long id) {
        topicoRepository.deleteById(id);
    }

    public TopicoResponse actualizarTopico(Long id, TopicoRequest request) {
        Topico topico = topicoRepository.findById(id).orElseThrow();
        topico.setTitulo(request.getTitulo());
        topico.setMensaje(request.getMensaje());
        topico.setAutor(request.getAutor());
        topico.setCurso(request.getCurso());
        Topico actualizado = topicoRepository.save(topico);
        return new TopicoResponse(
                actualizado.getId(),
                actualizado.getTitulo(),
                actualizado.getMensaje(),
                actualizado.getAutor(),
                actualizado.getCurso(),
                actualizado.getFechaCreacion()
        );
    }
    public Topico obtenerTopicoPorId(Long id) {
        return topicoRepository.findById(id).orElseThrow();
    }

    public RegistroTopicoDTO obtenerDTOParaEdicion(Long id) {
        Topico topico = topicoRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("No se encontró el tópico con ID: " + id));

        return new RegistroTopicoDTO(
                topico.getTitulo(),
                topico.getMensaje(),
                topico.getAutor(),
                topico.getCurso()
        );
    }




}