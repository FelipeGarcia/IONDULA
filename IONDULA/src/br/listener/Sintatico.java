/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.listener;

import java.util.Stack;

/**
 *
 * @author Felipe
 */
public class Sintatico {
    
    int x, a;
    static Stack pilha = new Stack();
    
    public void NovaPilha(){
     while(!pilha.empty()){
         pilha.pop();
     }
     pilha.push(40);
     pilha.push(42);
    }
    
    public void Entrada(int cod, String token, int linha){
        
        a = cod;
    }
}
