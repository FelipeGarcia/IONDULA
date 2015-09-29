
package br.Matriz;


public class Matriz {
   
    static int[][] matriz = new int[27][41];
    
    public void popula(){
        matriz[0][8] = 1;
        matriz[1][2] = 2;
        matriz[2][22] = 2;
        matriz[3][23] = 3;
        matriz[4][2] = 5;
        matriz[4][22] = 4;
        
    }
    
}
