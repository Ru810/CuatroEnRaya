package org.iesalandalus.programacion.cuatroenraya.vista;

import org.iesalandalus.programacion.cuatroenraya.modelo.Ficha;
import org.iesalandalus.programacion.cuatroenraya.modelo.Jugador;
import org.iesalandalus.programacion.utilidades.Entrada;

public class Consola {
    private Consola() {}

    public static String leerNombre() {
        System.out.print("Introduce el nombre del jugador: ");
        return Entrada.cadena();
    }

    public static Ficha elegirColorFichas() {
        int eleccion;
        do {
            System.out.print("Elige el color de tus fichas (0-AZUL, 1-VERDE)");
            eleccion = Entrada.entero();
        } while (eleccion < 0 || eleccion > 1);

        if (eleccion == 0) {
            return Ficha.AZUL;
        } else {
            return Ficha.VERDE;
        }


    }

    public static Jugador leerJugador() {
        return new Jugador(leerNombre(),elegirColorFichas());
    }

    public static Jugador leerJugador(Ficha ficha) {
        return new Jugador(leerNombre(), ficha);
    }
    public static int leerColumna(Jugador jugador) {
        int eleccion;
        do {
            System.out.print(jugador.nombre() + " introduce la columna en la que deseas introducir la ficha (0 - 6): ");
            eleccion = Entrada.entero();
        } while (eleccion < 0 || eleccion > 6);
        return eleccion;
    }

}
