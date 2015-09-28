/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.listener;

import br.janela.CompiladorGUI;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author felipes
 */
public class Lexico implements ActionListener {

    CompiladorGUI cp;

    static int linha;
    static int posicaoAtual;
    static String[] terminais = {null, "while", "var", "to", "then", "string", "real", "read",
        "program", "procedure", "print", "nreal", "nint", "literal", "integer", "if", "ident",
        " ", "for", "end", "else", "do", "const", "begin", ">=", ">", "=", "<>", "<=", "<", "+",
        ";", ":=", ":", "/", ".", ",", "*", ")", "(", "$", "-", "%%", "@|", "|@"};
    char[] charDoTexto;
    static String terminalAtual;
    int testeReal;
    boolean terminado;
    String txtSemEspaco;
    
    public Lexico(CompiladorGUI cp) {
        this.cp = cp;
    }

    public void actionPerformed(ActionEvent lal) {
        switch (lal.getActionCommand()) {
            case "Analisar": {
                terminado = false;
                //Captura texto do Editor, substituindo quebras de linha e espaços por simbolos
                txtSemEspaco = cp.getEditor().getText().replaceAll("\n", "¬");
                txtSemEspaco = txtSemEspaco.replaceAll(" ", "¢");
                //Adiciona final de arquivo ao texto capturado
                txtSemEspaco += "$";
                //Converte texto para um vetor de Characters
                charDoTexto = txtSemEspaco.toCharArray();
                //Define como estando na linha numero 1
                linha = 1;
                //Zera posição no vetor de characteres
                posicaoAtual = 0;
                //Limpa Console
                cp.cleanConsole();
                //Manda pra inicio da analise
                Inicio();
            }
        }
    }

    private void Inicio() {
        //Zera terminal atual
        terminalAtual = "";
        //se for espaço, pula ele
        if (charDoTexto[posicaoAtual] == '¢') {
            posicaoAtual++;
        }

        //se for quebra de linha, pula ela e diz que estamos na proxima linha
        if (charDoTexto[posicaoAtual] == '¬') {
            posicaoAtual++;
            linha++;
        }

        //Caso seja um número
        if (Character.isDigit(charDoTexto[posicaoAtual])) {
            terminalAtual += charDoTexto[posicaoAtual];
            J();
        }//caso seja uma letra
        else if (Character.isLetter(charDoTexto[posicaoAtual])) {
            terminalAtual += charDoTexto[posicaoAtual];
            A();
        } else if (charDoTexto[posicaoAtual] == '<') {
            terminalAtual += charDoTexto[posicaoAtual];
            C();
        } else if (charDoTexto[posicaoAtual] == '>') {
            terminalAtual += charDoTexto[posicaoAtual];
            D();
        } else if (charDoTexto[posicaoAtual] == '=') {
            terminalAtual += charDoTexto[posicaoAtual];
            E();
        } else if (charDoTexto[posicaoAtual] == '@') {
            F();
        } else if (charDoTexto[posicaoAtual] == '%') {
            L();
        } else if (charDoTexto[posicaoAtual] == ':') {
            terminalAtual += charDoTexto[posicaoAtual];
            N();
        } else if (charDoTexto[posicaoAtual] == ',' || charDoTexto[posicaoAtual] == ';'
                || charDoTexto[posicaoAtual] == ')' || charDoTexto[posicaoAtual] == '(' || charDoTexto[posicaoAtual] == '.') {
            terminalAtual += charDoTexto[posicaoAtual];
            P();
        } else if (charDoTexto[posicaoAtual] == '+' || charDoTexto[posicaoAtual] == '-'
                || charDoTexto[posicaoAtual] == '/' || charDoTexto[posicaoAtual] == '*'){
            terminalAtual += charDoTexto[posicaoAtual];
            Q();
        }//Final de arquivo
        else if (charDoTexto[posicaoAtual] == '$') {
            if (!terminado) {
                int i = 0;
                for (String palavraCorrente : terminais) {
                    if (palavraCorrente == "$") {
                        cp.setConsole(i, "$", linha, "Final de Arquivo");
                        terminado = true;
                        break;
                    }
                    i++;
                }
            }
        } else {
            cp.setConsole(0, "Um Erro Foi encontrado", linha, "Caracter invalido encontrado");
            terminado = true;
            posicaoAtual = txtSemEspaco.length() - 1;

        }
    }

    private void A() {
        //Passa para próxima posição
        posicaoAtual++;
        //Caso seja um número, passa para a próxima função, que tratará apenas variáveis
        if (Character.isDigit(charDoTexto[posicaoAtual])) {
            terminalAtual += charDoTexto[posicaoAtual];
            B();
        } //;caso seja uma letra, continuará até encontrar um varacter de outro tipo diferente
        else if (Character.isLetter(charDoTexto[posicaoAtual])) {
            terminalAtual += charDoTexto[posicaoAtual];
            A();
        } else {
            int i = 0;
            //Verifica se o texto armazenado se encontra nas palavras reservadas, adicionando ele ao console.
            for (String palavraCorrente : terminais) {
                if (terminalAtual.equals(palavraCorrente)) {
                    cp.setConsole(i, terminalAtual, linha, "Palavra reservada");
                    Inicio();
                }
                i++;
            }
            //Caso não esteja entre as palavras reservadas, é uma variável
            //Caso o terminal tenha um tamanho inferior a 20, adiciona ele ao console
            if (terminalAtual.length() <= 20) {
                //Correção de bug
                if (terminalAtual != "" && !terminado) {
                    cp.setConsole(16, terminalAtual, linha, "Nome de Variavel");
                    Inicio();
                }
            } else {
                cp.setConsole(0, "Um Erro Foi encontrado", linha, "Variável com tamanho inválido");
                terminado = true;
                posicaoAtual = txtSemEspaco.length() - 1;
            }
        }
    }

    private void B() {
        //Passa para próxima posição
        posicaoAtual++;
        //Caso seja um número ou letra fica adicionando ao terminal atual
        if (Character.isDigit(charDoTexto[posicaoAtual]) || Character.isLetter(charDoTexto[posicaoAtual])) {
            terminalAtual += charDoTexto[posicaoAtual];
            B();
        }//Ao encontrar algo diferente de número ao letra adiciona ao console que uma variável foi identificada 
        else if (terminalAtual.length() <= 20) {
            //Correção de bug
            if (terminalAtual != "" && !terminado) {
                cp.setConsole(16, terminalAtual, linha, "Nome de Variavel");
                Inicio();
            }
        } else {
            cp.setConsole(0, "Um Erro Foi encontrado", linha, "Variável com tamanho inválido");
            terminado = true;
            posicaoAtual = txtSemEspaco.length() - 1;
        }
    }

    private void C() {
        //Passa para próxima posição
        posicaoAtual++;
        //Verifica se existe alguma das duas possibilidades que acompanham esse caracter, caso exista, ele adiciona ele para o terminal atual
        if (charDoTexto[posicaoAtual] == '>' || charDoTexto[posicaoAtual] == '=') {
            terminalAtual += charDoTexto[posicaoAtual];
            //Passa para próxima posição
            posicaoAtual++;
        }

        int i = 0;
        //Verifica se o texto armazenado se encontra nas palavras reservadas, adicionando ele ao console.
        for (String palavraCorrente : terminais) {
            if (terminalAtual.equals(palavraCorrente)) {
                cp.setConsole(i, terminalAtual, linha, "Operador Lógico");
                Inicio();
            }
            i++;
        }

    }

    private void D() {
        //Passa para próxima posição
        posicaoAtual++;
        //Verifica se existe alguma das duas possibilidades que acompanham esse caracter, caso exista, ele adiciona ele para o terminal atual
        if (charDoTexto[posicaoAtual] == '=') {
            terminalAtual += charDoTexto[posicaoAtual];
            //Passa para próxima posição
            posicaoAtual++;
        }

        int i = 0;
        //Verifica se o texto armazenado se encontra nas palavras reservadas, adicionando ele ao console.
        for (String palavraCorrente : terminais) {
            if (terminalAtual.equals(palavraCorrente)) {
                cp.setConsole(i, terminalAtual, linha, "Operador Lógico");
                Inicio();
            }
            i++;
        }
    }

    private void E() {
        //Passa para próxima posição
        posicaoAtual++;

        int i = 0;
        //Verifica se o texto armazenado se encontra nas palavras reservadas, adicionando ele ao console.
        for (String palavraCorrente : terminais) {
            if (terminalAtual.equals(palavraCorrente)) {
                cp.setConsole(i, terminalAtual, linha, "Operador Lógico");
                Inicio();
            }
            i++;
        }
    }

    private void F() {
        //Passa para próxima posição
        posicaoAtual++;
        //Verifica se o próximo caracter encontrado é o que inicia o comentário de bloco
        if (charDoTexto[posicaoAtual] == '|') {
            G();
        }//Caso não seja, um literal será iniciado
        else {
            I();
        }

    }

    private void G() {
        //Roda o while enquanto não encontra o caracter que inicia o fim do comentário    
        do {
            //Caso encontre final de arquivo, é gerado um erro
            if (charDoTexto[posicaoAtual] == '$') {
                break;
            }
            //passa para próxima posição
            posicaoAtual++;
            //caso encontre quebra de linha, passa para próxima linha
            if (charDoTexto[posicaoAtual] == '¬') {
                linha++;
            }

        } while (charDoTexto[posicaoAtual] != '|');
        //Se o bloco de comentário não tiver fim, da erro e finaliza
        if (charDoTexto[posicaoAtual] == '$') {
            cp.setConsole(0, "Um Erro Foi encontrado", linha, "Bloco de comentário não terminado");
            terminado = true;
            posicaoAtual = txtSemEspaco.length() - 1;
        } else {
            H();
        }

    }

    private void H() {
        //Passa para próxima posição
        posicaoAtual++;
        //Verifica se encontra o caracter que fecha o comentário de bloco
        if (charDoTexto[posicaoAtual] == '@') {
            //Passa para próxima posição
            posicaoAtual++;
            Inicio();
        }//Casa não encontre o caracter pra fechar o comentário, volta a função anterior
        else {
            G();
        }
    }
    //ERRO

    private void I() {
        //Armazena os caracteres encontrados até encontrar um @
        do {
            if (charDoTexto[posicaoAtual] != '$') {
                terminalAtual += charDoTexto[posicaoAtual];
                //Passa para próxima posição
                posicaoAtual++;
                //caso encontre quebra de linha, passa para próxima linha
                if (charDoTexto[posicaoAtual] == '¬') {
                    linha++;
                }
                //Caso acabe o codigo, da erro
            } else {
                break;
            }

        } while (charDoTexto[posicaoAtual] != '@');
        if (terminalAtual.length() <= 80 && charDoTexto[posicaoAtual] != '$') {
            int i = 0;
            //Verifica se o texto armazenado se encontra nas palavras reservadas, adicionando ele ao console.
            for (String palavraCorrente : terminais) {
                if (palavraCorrente == "literal") {
                    cp.setConsole(i, terminalAtual, linha, "Literal");
                    posicaoAtual++;
                    Inicio();
                }
                i++;
            }
        } else if (charDoTexto[posicaoAtual] != '$') {
            cp.setConsole(0, "Um Erro Foi encontrado", linha, "Literal ultrapassa limite de caracteres");
            terminado = true;
            posicaoAtual = txtSemEspaco.length() - 1;
        } else {
            cp.setConsole(0, "Um Erro Foi encontrado", linha, "Literal não terminado");
            terminado = true;
            posicaoAtual = txtSemEspaco.length() - 1;
        }
    }

    private void J() {
        //Passa para próxima posição
        posicaoAtual++;
        //Caso seja um numero, adiciona ele ao terminal atual
        if (Character.isDigit(charDoTexto[posicaoAtual])) {
            terminalAtual += charDoTexto[posicaoAtual];
            J();
        }//Caso encontre uma , ele passa a ser real, e ira para um anova função 
        else if (charDoTexto[posicaoAtual] == ',' && terminalAtual.length() <= 2) {
            terminalAtual += charDoTexto[posicaoAtual];
            testeReal = 2;
            K();
        }//Ao achar um caracter diferente, ira identificar ele 
        else if (terminalAtual.length() <= 2) {
            int i = 0;
            //Verifica se o texto armazenado se encontra nas palavras reservadas, adicionando ele ao console.
            for (String palavraCorrente : terminais) {
                if (palavraCorrente == "nint") {
                    cp.setConsole(i, terminalAtual, linha, "Inteiro");
                    Inicio();
                }
                i++;
            }
        } else {
            cp.setConsole(0, "Um Erro Foi encontrado", linha, "Inteiro fora dos limites permitidos");
            terminado = true;
            posicaoAtual = txtSemEspaco.length() - 1;
        }
    }

    private void K() {
        //Passa para próxima posição
        posicaoAtual++;
        //Caso seja um número, armazena ele
        if (Character.isDigit(charDoTexto[posicaoAtual]) && testeReal > 0) {
            terminalAtual += charDoTexto[posicaoAtual];
            testeReal--;
            K();
        } else if (Character.isDigit(charDoTexto[posicaoAtual])) {
            cp.setConsole(0, "Um Erro Foi encontrado", linha, "Real fora dos limites permitidos");
            terminado = true;
            posicaoAtual = txtSemEspaco.length() - 1;
        } else {

            int i = 0;
            //Verifica se o texto armazenado se encontra nas palavras reservadas, adicionando ele ao console.
            for (String palavraCorrente : terminais) {
                if (palavraCorrente == "nreal") {
                    cp.setConsole(i, terminalAtual, linha, "Real");
                    Inicio();
                }
                i++;
            }
        }
    }

    private void L() {
        //Verifica se o próximo caracter necessário para iniciar um comentário de linha existe, mando para a próxima função
        if (charDoTexto[posicaoAtual] == '%') {
            //Passa para próxima posição
            posicaoAtual++;
            M();
        } else{
            cp.setConsole(0, "Um Erro Foi encontrado", linha, "Caracter invalido encontrado");
            terminado = true;
            posicaoAtual = txtSemEspaco.length() - 1;
        }
    }

    private void M() {
        //Enquanto uma quebra de linha não é encontrada, passamos pelo comentário
        if(charDoTexto[posicaoAtual] == '¬' || charDoTexto[posicaoAtual] == '$'){
           linha++;
        Inicio();
        }
        else{
            System.out.println(charDoTexto[posicaoAtual]);
            posicaoAtual++;
           
           M();
        }
        //Passa para próxima linha
        
    }

    private void N() {
        //Passa para próxima posição
        posicaoAtual++;
        //Verifica se o próximo caracter necessário para o simbolo de atribuição existe, mandando para uma nova função
        if (charDoTexto[posicaoAtual] == '=') {
            terminalAtual += charDoTexto[posicaoAtual];
            O();
        }//Senão classifica-o como simbolo
        int i = 0;
        //Verifica se o texto armazenado se encontra nas palavras reservadas, adicionando ele ao console.
        for (String palavraCorrente : terminais) {
            if (terminalAtual.equals(palavraCorrente)) {
                cp.setConsole(i, terminalAtual, linha, "Simbolo");
                Inicio();
            }
            i++;
        }
    }

    private void O() {
        //Passa para próxima linha
        posicaoAtual++;
        int i = 0;
        //Verifica se o texto armazenado se encontra nas palavras reservadas, adicionando ele ao console.
        for (String palavraCorrente : terminais) {
            if (terminalAtual.equals(palavraCorrente)) {
                cp.setConsole(i, terminalAtual, linha, "Operador de Atribuição");
                Inicio();
            }
            i++;
        }
    }

    private void P() {
        //Passa para próxima linha
        posicaoAtual++;
        int i = 0;
        //Verifica se o texto armazenado se encontra nas palavras reservadas, adicionando ele ao console.
        for (String palavraCorrente : terminais) {
            if (terminalAtual.equals(palavraCorrente)) {
                cp.setConsole(i, terminalAtual, linha, "Simbolo");
                Inicio();
            }
            i++;
        }
    }
    
    private void Q() {
        //Passa para próxima linha
        posicaoAtual++;
        int i = 0;
        //Verifica se o texto armazenado se encontra nas palavras reservadas, adicionando ele ao console.
        for (String palavraCorrente : terminais) {
            if (terminalAtual.equals(palavraCorrente)) {
                cp.setConsole(i, terminalAtual, linha, "Simbolo Aritimético");
                Inicio();
            }
            i++;
        }
    }

}
