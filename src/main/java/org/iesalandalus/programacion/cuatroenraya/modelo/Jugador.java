package org.iesalandalus.programacion.cuatroenraya.modelo;

public record Jugador(String nombre, Ficha colorFichas) {


    public Jugador {
        validarNombre(nombre);
        validarColorFichas(colorFichas);
        if (nombre.isBlank() && colorFichas != null ) {
            throw new IllegalArgumentException("El nombre no puede estar en blanco.");
        }
    }
    private String validarNombre(String nombre) {
        if (nombre == null) {
            throw new NullPointerException("El nombre no puede ser nulo.");
        }
        return nombre;
    }

    private Ficha validarColorFichas(Ficha colorFichas) {
        if (colorFichas == null) {
            throw new NullPointerException("El color de las fichas no puede ser nulo.");
        }
        return colorFichas;
    }

    @Override
    public String toString() {
        return String.format("%s (%s)", nombre, colorFichas);
    }
}
