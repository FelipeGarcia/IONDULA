package br.Matriz;

import java.util.Stack;

public class Regras {
    
   
    Stack[] regras = new Stack[63];
    Stack regrasCorrente = new Stack();
    
    
    public Stack[] populaStack() {
        regras[0].add(null);
        regrasCorrente.push(8);
        regrasCorrente.push(16);
        regrasCorrente.push(31);
        regrasCorrente.push(43);
        regrasCorrente.push(44);
        regrasCorrente.push(35);
        regras[1].add(regrasCorrente);
        this.limpaPilha(regrasCorrente);
        
        
        return regras;
    }
    
    public Stack limpaPilha(Stack regrasCorrentes) {
        
        while (!regrasCorrentes.empty()) {
            regrasCorrentes.pop();
        }
        
        return regrasCorrentes;
    }
    
}
