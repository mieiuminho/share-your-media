package model;

import java.util.HashSet;
import java.util.Set;

public final class Playlist {

    private String name;
    private String creatorEmail;
    private String criteria;
    private Set<String> mediafiles;

    /**
     * Constructor
     * 
     * @param name Playlist's name.
     * @param creatorEmail Playlist's creatorEmail.
     * @param criteria Playlist's criteria. TODO: Não vamos querer criar o Set vazio. Podíamos depoir chamar aqui as
     *            funções que geram as playlists de acordo com o critério pedido pelo utilizador.
     */
    public Playlist(final String name, final String creatorEmail, final String criteria) {
        this.name = name;
        this.creatorEmail = creatorEmail;
        this.criteria = criteria;
        this.mediafiles = new HashSet<>();
    }
}
