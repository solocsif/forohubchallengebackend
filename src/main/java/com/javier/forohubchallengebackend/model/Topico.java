package com.javier.forohubchallengebackend.model;

public class Topico {
    private String titulo;
    private String mensaje;
    private String autor;
    private String curso;

    public Topico(String titulo, String mensaje, String autor, String curso) {
        this.titulo = titulo;
        this.mensaje = mensaje;
        this.autor = autor;
        this.curso = curso;
    }

    // Getters
    public String getTitulo() { return titulo; }
    public String getMensaje() { return mensaje; }
    public String getAutor() { return autor; }
    public String getCurso() { return curso; }
}
