/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.listener;

import br.Matriz.Matriz;
import br.Matriz.Regras;
import br.janela.CompiladorGUI;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

/**
 *
 * @author Felipe
 */
public class Sintatico {
    
    int x, a;
    static Stack pilha = new Stack();
    Regras regras = new Regras();
    Matriz matriz = new Matriz();
    Stack aux;
    CompiladorGUI cp;
    
    public Sintatico(CompiladorGUI cp) {
        this.cp = cp;
    }
    
    public void NovaPilha(){
     while(!pilha.empty()){
         pilha.pop();
     }
     pilha.push(40);
     pilha.push(42);
     regras.inicia();
     regras.populaStack();
     matriz.popula();
    }
    
    public boolean Entrada(int cod, String token, int linha){
        
        a = cod;
        x =  (int) pilha.peek();
        int ret = Analise();
        if(ret == 1){
            return true;
        }
        return false;
    }
    
    public int Analise(){
        regras.inicia();
        regras.populaStack();
        do{
        if(x == 17){
            pilha.pop();
            x = (int) pilha.peek();
        }else if(x < 42){
            if(x == a){
                pilha.pop();
                return 1;
            }else{
                return 0;
            }
        }else{
           if(matriz.buscar(x, a) != 0){
               pilha.pop();
               aux = regras.busca(matriz.buscar(x, a));
               cp.setSintatico(x, a,matriz.buscar(x, a));
               while(!aux.empty()){
                   pilha.push(aux.pop());
                   }
               x = (int) pilha.peek();
           }else{
               return 0;
           }
            
        }}while(x != 40);
        return 0;
    }
}
