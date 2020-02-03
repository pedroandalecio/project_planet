package br.com.planet.exceptions;

public class PlanetException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public PlanetException(String msg) {
        super(msg);
    }

}
