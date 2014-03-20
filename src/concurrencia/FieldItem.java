package concurrencia;


interface FieldItem {
	abstract public boolean fired(); // Devuelve verdadero si murió con el disparo
	abstract public char getType();  // Devuelve el carácter que representa el tipo de objeto
}