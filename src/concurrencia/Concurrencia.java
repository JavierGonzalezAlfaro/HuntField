package concurrencia;

import concurrencia.swing.Swing;

public class Concurrencia {
    
	public static void printField(HuntField field){
			System.out.print("Hunters "+field.getNumberOfItems('H')+ " Ducks "+field.getNumberOfItems('D')+"\n");
			System.out.print(field);
	}
	public static void main(String[] args) throws InterruptedException {
		final int numberOfTrees= 10;
		final int numberOfDucks= 20;
		final int numberOfHunters= 10;
		HuntField field= new HuntField(12,24); //12x24
                Swing swing = new Swing(field); 
		for(int i=0; i<numberOfTrees; i++)	new Tree(field);
		for(int i=0; i<numberOfDucks; i++) new Duck(field).start();
		for(int i=0; i<numberOfHunters; i++) new Hunter(field).start();
		while(field.getNumberOfItems('D')>0){
			Thread.sleep(200);
                       swing.show(field);
			printField(field);
		}
		printField(field);

                
	}
}
