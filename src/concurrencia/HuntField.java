package concurrencia;

public class HuntField {

    private final FieldItem[][] field;
    //Inicialmente el tablero no contiene elementos. 
    //En cada casilla sólo puede haber un elemento cada vez.

    public HuntField(int line, int column) {
        field = new FieldItem[line][column];
    }

    //Devuelve el número de columnas que tiene el tablero
    public int getXLength() {
        return field.length;
    }

    //Devuelve el número de filas que tiene el tablero
    public int getYLength() {
        return field[0].length;
    }

    //Se le pasa un elemento a situar en el tablero y la posición. 
    //La posición se pasa en forma de objeto de la clase Position. 
    //Si la posición es errónea, o está ocupada, o el objeto ya está en otra posición, devuelve falso.
    public synchronized boolean setItem(FieldItem item, Position position) {
        if (insideBounds(position) && this.getItemType(position) == ' ' && !isPositioned(item)) {
            field[position.getX()][position.getY()] = item;
            return true;
        }
        return false;
    }

    //Se hace un disparo sobre una posición, pasada por parámetro. 
    //Si la posición es errónea o está vacía devuelve falso, en otro caso, 
    //devuelve el resultado de llamar a fired en el objeto situado en la posición correspondiente.
    public synchronized boolean shot(Position position) {
        if (insideBounds(position) && getItemType(position) != ' ') {
            return field[position.getX()][position.getY()].fired();
        }
        return false;
    }

    //Se le pasa un objeto y la posición en que está en el tablero. 
    //Elimina del tablero el objeto en la posición pasada, si coincide con el objeto pasado.
    public synchronized boolean removeItem(FieldItem item, Position position) {
        if (insideBounds(position) && this.getItemType(position) == item.getType()) { //MIRAR EL MISMO OBJETO, NO SOLO QUE SEA UN PATO
            field[position.getX()][position.getY()] = null;
            return true;
        }
        return false;
    }

    //Devuelve el carácter que representa el tipo del objeto en la posición pasada o un espacio, en otro caso.
    public synchronized char getItemType(Position position) {
        if (field[position.getX()][position.getY()] == null) {
            return ' ';
        }
        return field[position.getX()][position.getY()].getType();
    }

    //Se le pasa un objeto en el tablero su posición actual y la nueva. 
    //Si alguna de las posiciones es errónea o el objeto no está en la inicial, se devuelve falso. 
    //Durante, como mucho, un segundo, se intenta mover a la nueva posición. 
    //El movimiento se producirá cuando la posición destino esté vacía. 
    //Si se logra mover devuelve verdadero, si no, se devuelve falso.
    public synchronized boolean moveItem(FieldItem item, Position fromPosition, Position toPosition) {
        if (insideBounds(fromPosition) && insideBounds(toPosition)) {
            int transcurredTime = 0;
            while (getItemType(toPosition) != ' ') {
                try {
                    long time = System.nanoTime();
                    wait(0, 999999 - transcurredTime);
                    transcurredTime += System.nanoTime() - time;
                    if (transcurredTime > 1000000) {
                        return false;
                    }
                } catch (InterruptedException ex) {
                }
            }
            if (item == field[fromPosition.getX()][fromPosition.getY()]) {
                field[fromPosition.getX()][fromPosition.getY()] = null;
                field[toPosition.getX()][toPosition.getY()] = item;
                notifyAll();
                return true;
            }
        }
        return false;
    }

    //Se le pasa un carácter y devuelve el número de items en el tablero del tipo del carácter pasado.
    public int getNumberOfItems(char type) {
        int number = 0;
        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field[0].length; j++) {
                if (type == getItemType(new Position(i, j))) {
                    number++;
                }
            }
        }
        return number;
    }

    //Comprueba si la posicion pasada esta dentro de los margenes del HuntField
    private boolean insideBounds(Position position) {
        if (position.getX() >= 0 && position.getX() < field.length && position.getY() >= 0 && position.getY() < field[0].length) {
            return true;
        }
        return false;
    }

    //Comprueba si el item pasado ya esta colocado dentro del HuntField
    private boolean isPositioned(FieldItem item) {
        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field[0].length; j++) {
                if (item == field[i][j]) {
                    return true;
                }
            }
        }
        return false;
    }

    //Se devuelve una ristra que representa el tablero. 
    //Cada línea en la ristra representa una fila en el tablero (al final de cada línea se sitúa el carácter '\n'). 
    //Cada carácter en la línea representa una celda en la columna de la fila correspondiente, 
    //y es el carácter que representa el tipo del objeto en dicha celda, o un espacio, si está vacía.
    @Override
    public String toString() {
        String result = "";
        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field[0].length; j++) {
                result = result + "[" + this.getItemType(new Position(i, j)) + "]";
            }
            result = result + "\n";
        }
        return result;
    }
}
