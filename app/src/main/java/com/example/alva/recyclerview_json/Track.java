package com.example.alva.recyclerview_json;

public class Track {
    private String nombreTrack;
    private String nombreArtista;
    private String duracion;
    private String mp3;

    public Track(String nombreTrack, String nombreArtista, String rank, String mp3) {
        this.nombreTrack = nombreTrack;
        this.nombreArtista = nombreArtista;
        this.duracion = rank;
        this.mp3 = mp3;
    }

    public String getNombreTrack() {
        return nombreTrack;
    }

    public String getNombreArtista() {
        return nombreArtista;
    }

    public String getDuracion() {
        return duracion;
    }

    public String getMp3() {
        return mp3;
    }
}
