package org.iesalandalus.programacion.cuatroenraya.modelo;

import java.util.Objects;

public class Tablero {
    public static final int FILAS = 6;
    public static final int COLUMNAS = 7;
    public static final int FICHAS_IGUALES_CONSECUTIVAS_NECESARIAS = 4;
    private Casilla[][] casillas;

    public Tablero() {
        casillas = new Casilla[FILAS][COLUMNAS];
        for (int i = 0; i < FILAS; i++) {
            for (int j = 0; j < COLUMNAS; j++) {
                casillas[i][j] = new Casilla();
            }
        }
    }

    private boolean columnaVacia(int columna) {
        for (int fila = 0; fila < FILAS; fila++) {
            if (casillas[fila][columna].estaOcupada()) {
                return false;
            }
        }
        return true;
    }
    
    public boolean estaVacio() {
        for (int fila = 0; fila < FILAS; fila++) {
            for (int columna = 0; columna < COLUMNAS; columna++) {
                if (casillas[fila][columna].estaOcupada()) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean columnaLlena(int columna) {
        for (int fila = 0; fila < FILAS; fila++) {
            if (!casillas[fila][columna].estaOcupada()) {
                return false;
            }
        }
        return true;
    }

    public boolean estaLleno() {
        for (int columna = 0; columna < COLUMNAS; columna++) {
            if (!columnaLlena(columna)) {
                return false;
            }
        }
        return true;
    }



    private void comprobarColumna(int columna) {
        if (columna < 0 || columna >= COLUMNAS) {
            throw new IllegalArgumentException("Columna incorrecta.");
        }
    }

    private int getPrimeraFilaVacia(int columna) {
        for (int fila = FILAS - 1; fila >= 0; fila--) {
            if(!casillas[fila][columna].estaOcupada()) {
                return fila;
            }
        }
        throw new RuntimeException("La fila esta llena.");
    }

    private void comprobarFicha(Ficha ficha) {
        Objects.requireNonNull(ficha,"La ficha no puede ser nula.");
    }

    public boolean introducirFicha(int columna, Ficha ficha) {
        if (ficha == null) {
            throw new NullPointerException("La ficha no puede ser nula.");
        }
        comprobarColumna(columna);

        if (columnaLlena(columna)) {
            throw new CuatroEnRayaExcepcion("Columna llena.");
        }

        int fila = getPrimeraFilaVacia(columna);
        casillas[fila][columna].setFicha(ficha);
        return comprobarTirada(fila, columna);
    }


    private boolean comprobarHorizontal(int fila, Ficha ficha) {
        int contador = 0;
        for (int columna = 0; columna < COLUMNAS; columna++) {
            if (casillas[fila][columna].estaOcupada() && casillas[fila][columna].getFicha().equals(ficha)) {
                contador++;
                if (objetivoAlcanzado(contador)) {
                    return true;
                }
            } else {
                contador = 0;
            }
        }
        return false;
    }

    private boolean comprobarVertical(int columna, Ficha ficha) {
        int contador = 0;
        for (int fila = 0; fila < FILAS; fila++) {
            if (casillas[fila][columna].estaOcupada() && casillas[fila][columna].getFicha().equals(ficha)) {
                contador++;
                if (objetivoAlcanzado(contador)) {
                    return true;
                }
            } else {
                contador = 0;
            }
        }
        return false;
    }

    private boolean objetivoAlcanzado(int fichasIgualesConsecutivas) {
        return fichasIgualesConsecutivas == FICHAS_IGUALES_CONSECUTIVAS_NECESARIAS;
    }

    private int menor(int fila, int columna) {
        return fila < columna ? fila : columna;
    }

    private boolean comprobarDiagonalNE(int filaActual,int columnaActual,Ficha ficha) {
        int desplazamiento = menor(filaActual,columnaActual);
        int filaInicial = filaActual - desplazamiento;
        int columnaInicial = columnaActual - desplazamiento;
        int contador = 0;

        for(int i = 0; i < FICHAS_IGUALES_CONSECUTIVAS_NECESARIAS; i++) {
            if (filaInicial + i >= FILAS || columnaInicial + i >= COLUMNAS) {
                return false;
            }
            if (casillas[filaInicial + i][columnaInicial + i].estaOcupada() &&
                    casillas[filaInicial + i][columnaInicial + i].getFicha().equals(ficha)) {
                contador++;
            } else {
                return false;
            }
        }
        return objetivoAlcanzado(contador);
    }

    private boolean comprobarDiagonalNO(int filaActual, int columnaActual, Ficha ficha) {
        int desplazamiento = menor(filaActual, COLUMNAS - 1 - columnaActual);
        int filaInicial = filaActual - desplazamiento;
        int columnaInicial = columnaActual + desplazamiento;
        int contador = 0;

        for (int i = 0; i < FICHAS_IGUALES_CONSECUTIVAS_NECESARIAS; i++) {
            if (filaInicial + i >= FILAS || columnaInicial - i < 0) {
                return false;
            }
            if (casillas[filaInicial + i][columnaInicial - i].estaOcupada() &&
                    casillas[filaInicial + i][columnaInicial - i].getFicha().equals(ficha)) {
                contador++;
            } else {
                return false;
            }
        }

        return objetivoAlcanzado(contador);
    }

    private boolean comprobarTirada(int fila, int columna) {
        Ficha ficha = casillas[fila][columna].getFicha();
        return comprobarHorizontal(fila, ficha) ||
                comprobarVertical(columna, ficha) ||
                comprobarDiagonalNE(fila, columna, ficha) ||
                comprobarDiagonalNO(fila, columna, ficha);
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        // Recorremos cada fila del tablero
        for (int i = 0; i < FILAS; i++) {
            sb.append("|");
            for (int j = 0; j < COLUMNAS; j++) {
                sb.append(casillas[i][j].estaOcupada() ? casillas[i][j].getFicha().toString() : " ");
            }
            sb.append("|");
            sb.append("\n");
        }
        sb.append(" -------\n");
        return sb.toString();
    }
}
