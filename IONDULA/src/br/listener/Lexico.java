package br.listener;

import br.janela.CompiladorGUI;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Lexico implements ActionListener {

    CompiladorGUI cp;

    static int linha;
    static int posicaoAtual;
    static String[] terminais = {null, "while", "var", "to", "then", "string", "real", "read",
        "program", "procedure", "print", "nreal", "nint", "literal", "integer", "if", "ident",
        " ", "for", "end", "else", "do", "const", "begin", ">=", ">", "=", "<>", "<=", "<", "+",
        ";", ":=", ":", "/", ".", ",", "*", ")", "(", "$", "-"};
    char[] charDoTexto;
    static String terminalAtual;
    int testeReal;
    boolean terminado;
    String txtSemEspaco;
    Sintatico as;

    public Lexico(CompiladorGUI cp) {
        this.cp = cp;
        this.as = new Sintatico(cp);
    }

    public void actionPerformed(ActionEvent lal) {
        switch (lal.getActionCommand()) {
            case "Analisar": {
                terminado = false;
                //Captura texto do Editor, substituindo quebras de linha e espa�os por simbolos
                txtSemEspaco = cp.getEditor().getText().replaceAll("\n", "�");
                txtSemEspaco = txtSemEspaco.replaceAll(" ", "�");
                //Adiciona final de arquivo ao texto capturado
                txtSemEspaco += "$";
                //Converte texto para um vetor de Characters
                charDoTexto = txtSemEspaco.toCharArray();
                //Define como estando na linha numero 1
                linha = 1;
                //Zera posi��o no vetor de characteres
                posicaoAtual = 0;
                //Limpa Console
                cp.cleanConsole();
                as.NovaPilha();
                //Manda pra inicio da analise
                Inicio();
            }
        }
    }

    private void Inicio() {
        //Zera terminal atual
        terminalAtual = "";
        //se for espa�o, pula ele
        if (charDoTexto[posicaoAtual] == '�') {
            posicaoAtual++;
            Inicio();
        }

        //se for quebra de linha, pula ela e diz que estamos na proxima linha
        if (charDoTexto[posicaoAtual] == '�') {
            posicaoAtual++;
            linha++;
            Inicio();
        }

        //Caso seja um n�mero
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
                || charDoTexto[posicaoAtual] == '/' || charDoTexto[posicaoAtual] == '*') {
            terminalAtual += charDoTexto[posicaoAtual];
            Q();
        }//Final de arquivo
        else if (charDoTexto[posicaoAtual] == '$') {
            if (!terminado) {
                int i = 0;
                for (String palavraCorrente : terminais) {
                    if (palavraCorrente == "$") {
                        if (as.Entrada(i, txtSemEspaco, linha)) {
                            cp.setConsole(i, "$", linha, "Final de Arquivo");
                        } else {
                            cp.setConsole(0, "Erro Sint�tico", linha, "");
                        }
                        terminado = true;
                        break;
                    }
                    i++;
                }
            }
        } else {
            cp.setConsole(0, "Um Erro Foi encontrado", linha, "Caracter invalido encontrado" + charDoTexto[posicaoAtual]);
            terminado = true;
            posicaoAtual = txtSemEspaco.length() - 1;

        }
    }

    private void A() {
        //Passa para pr�xima posi��o
        posicaoAtual++;
        //Caso seja um n�mero, passa para a pr�xima fun��o, que tratar� apenas vari�veis
        if (Character.isDigit(charDoTexto[posicaoAtual])) {
            terminalAtual += charDoTexto[posicaoAtual];
            B();
        } //;caso seja uma letra, continuar� at� encontrar um varacter de outro tipo diferente
        else if (Character.isLetter(charDoTexto[posicaoAtual])) {
            terminalAtual += charDoTexto[posicaoAtual];
            A();
        } else {
            int i = 0;
            //Verifica se o texto armazenado se encontra nas palavras reservadas, adicionando ele ao console.
            for (String palavraCorrente : terminais) {
                if (terminalAtual.toLowerCase().equals(palavraCorrente)) {
                    if (as.Entrada(i, txtSemEspaco, linha)) {
                        cp.setConsole(i, terminalAtual, linha, "Palavra reservada");
                    } else {
                        cp.setConsole(0, "Erro Sint�tico", linha, "");
                        terminado = true;
                        posicaoAtual = txtSemEspaco.length() - 1;
                    }
                    Inicio();
                }
                i++;
            }
            //Caso n�o esteja entre as palavras reservadas, � uma vari�vel
            //Caso o terminal tenha um tamanho inferior a 20, adiciona ele ao console
            if (terminalAtual.length() <= 20) {
                //Corre��o de bug
                if (terminalAtual != "" && !terminado) {
                    if (as.Entrada(16, txtSemEspaco, linha)) {
                        cp.setConsole(16, terminalAtual, linha, "Nome de Variavel");
                    } else {
                        cp.setConsole(0, "Erro Sint�tico", linha, "");
                        terminado = true;
                        posicaoAtual = txtSemEspaco.length() - 1;
                    }
                    Inicio();
                }
            } else {
                cp.setConsole(0, "Um Erro Foi encontrado", linha, "Vari�vel com tamanho inv�lido");
                terminado = true;
                posicaoAtual = txtSemEspaco.length() - 1;
            }
        }
    }

    private void B() {
        //Passa para pr�xima posi��o
        posicaoAtual++;
        //Caso seja um n�mero ou letra fica adicionando ao terminal atual
        if (Character.isDigit(charDoTexto[posicaoAtual]) || Character.isLetter(charDoTexto[posicaoAtual])) {
            terminalAtual += charDoTexto[posicaoAtual];
            B();
        }//Ao encontrar algo diferente de n�mero ao letra adiciona ao console que uma vari�vel foi identificada 
        else if (terminalAtual.length() <= 20) {
            //Corre��o de bug
            if (terminalAtual != "" && !terminado) {
                if (as.Entrada(16, txtSemEspaco, linha)) {
                    cp.setConsole(16, terminalAtual, linha, "Nome de Variavel");
                } else {
                    cp.setConsole(0, "Erro Sint�tico", linha, "");
                    terminado = true;
                    posicaoAtual = txtSemEspaco.length() - 1;
                }
                Inicio();
            }
        } else {
            cp.setConsole(0, "Um Erro Foi encontrado", linha, "Vari�vel com tamanho inv�lido");
            terminado = true;
            posicaoAtual = txtSemEspaco.length() - 1;
        }
    }

    private void C() {
        //Passa para pr�xima posi��o
        posicaoAtual++;
        //Verifica se existe alguma das duas possibilidades que acompanham esse caracter, caso exista, ele adiciona ele para o terminal atual
        if (charDoTexto[posicaoAtual] == '>' || charDoTexto[posicaoAtual] == '=') {
            terminalAtual += charDoTexto[posicaoAtual];
            //Passa para pr�xima posi��o
            posicaoAtual++;
        }

        int i = 0;
        //Verifica se o texto armazenado se encontra nas palavras reservadas, adicionando ele ao console.
        for (String palavraCorrente : terminais) {
            if (terminalAtual.toLowerCase().equals(palavraCorrente)) {
                if (as.Entrada(i, txtSemEspaco, linha)) {
                    cp.setConsole(i, terminalAtual, linha, "Operador L�gico");
                } else {
                    cp.setConsole(0, "Erro Sint�tico", linha, "");
                    terminado = true;
                    posicaoAtual = txtSemEspaco.length() - 1;
                }
                Inicio();
            }
            i++;
        }

    }

    private void D() {
        //Passa para pr�xima posi��o
        posicaoAtual++;
        //Verifica se existe alguma das duas possibilidades que acompanham esse caracter, caso exista, ele adiciona ele para o terminal atual
        if (charDoTexto[posicaoAtual] == '=') {
            terminalAtual += charDoTexto[posicaoAtual];
            //Passa para pr�xima posi��o
            posicaoAtual++;
        }

        int i = 0;
        //Verifica se o texto armazenado se encontra nas palavras reservadas, adicionando ele ao console.
        for (String palavraCorrente : terminais) {
            if (terminalAtual.toLowerCase().equals(palavraCorrente)) {
                if (as.Entrada(i, txtSemEspaco, linha)) {
                    cp.setConsole(i, terminalAtual, linha, "Operador L�gico");
                } else {
                    cp.setConsole(0, "Erro Sint�tico", linha, "");
                    terminado = true;
                    posicaoAtual = txtSemEspaco.length() - 1;
                }
                Inicio();
            }
            i++;
        }
    }

    private void E() {
        //Passa para pr�xima posi��o
        posicaoAtual++;

        int i = 0;
        //Verifica se o texto armazenado se encontra nas palavras reservadas, adicionando ele ao console.
        for (String palavraCorrente : terminais) {
            if (terminalAtual.toLowerCase().equals(palavraCorrente)) {
                if (as.Entrada(i, txtSemEspaco, linha)) {
                    cp.setConsole(i, terminalAtual, linha, "Operador L�gico");
                } else {
                    cp.setConsole(0, "Erro Sint�tico", linha, "");
                    terminado = true;
                    posicaoAtual = txtSemEspaco.length() - 1;
                }
                Inicio();
            }
            i++;
        }
    }

    private void F() {
        //Passa para pr�xima posi��o
        posicaoAtual++;
        //Verifica se o pr�ximo caracter encontrado � o que inicia o coment�rio de bloco
        if (charDoTexto[posicaoAtual] == '|') {
            G();
        }//Caso n�o seja, um literal ser� iniciado
        else {
            I();
        }

    }

    private void G() {
        //Roda o while enquanto n�o encontra o caracter que inicia o fim do coment�rio    
        do {
            //Caso encontre final de arquivo, � gerado um erro
            if (charDoTexto[posicaoAtual] == '$') {
                break;
            }
            //passa para pr�xima posi��o
            posicaoAtual++;
            //caso encontre quebra de linha, passa para pr�xima linha
            if (charDoTexto[posicaoAtual] == '�') {
                linha++;
            }

        } while (charDoTexto[posicaoAtual] != '|');
        //Se o bloco de coment�rio n�o tiver fim, da erro e finaliza
        if (charDoTexto[posicaoAtual] == '$') {
            cp.setConsole(0, "Um Erro Foi encontrado", linha, "Bloco de coment�rio n�o terminado");
            terminado = true;
            posicaoAtual = txtSemEspaco.length() - 1;
        } else {
            H();
        }

    }

    private void H() {
        //Passa para pr�xima posi��o
        posicaoAtual++;
        //Verifica se encontra o caracter que fecha o coment�rio de bloco
        if (charDoTexto[posicaoAtual] == '@') {
            //Passa para pr�xima posi��o
            posicaoAtual++;
            Inicio();
        }//Casa n�o encontre o caracter pra fechar o coment�rio, volta a fun��o anterior
        else {
            G();
        }
    }
    //ERRO

    private void I() {
        //Armazena os caracteres encontrados at� encontrar um @
        do {
            if (charDoTexto[posicaoAtual] != '$') {
                terminalAtual += charDoTexto[posicaoAtual];
                //Passa para pr�xima posi��o
                posicaoAtual++;
                //caso encontre quebra de linha, passa para pr�xima linha
                if (charDoTexto[posicaoAtual] == '�') {
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
                    if (as.Entrada(i, txtSemEspaco, linha)) {
                        cp.setConsole(i, terminalAtual, linha, "Literal");
                        posicaoAtual++;
                    } else {
                        cp.setConsole(0, "Erro Sint�tico", linha, "");
                        terminado = true;
                        posicaoAtual = txtSemEspaco.length() - 1;
                    }
                    Inicio();
                }
                i++;
            }
        } else if (charDoTexto[posicaoAtual] != '$') {
            cp.setConsole(0, "Um Erro Foi encontrado", linha, "Literal ultrapassa limite de caracteres");
            terminado = true;
            posicaoAtual = txtSemEspaco.length() - 1;
        } else {
            cp.setConsole(0, "Um Erro Foi encontrado", linha, "Literal n�o terminado");
            terminado = true;
            posicaoAtual = txtSemEspaco.length() - 1;
        }
    }

    private void J() {
        //Passa para pr�xima posi��o
        posicaoAtual++;
        //Caso seja um numero, adiciona ele ao terminal atual
        if (Character.isDigit(charDoTexto[posicaoAtual])) {
            terminalAtual += charDoTexto[posicaoAtual];
            J();
        }//Caso encontre uma , ele passa a ser real, e ira para um anova fun��o 
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
                    if (as.Entrada(i, txtSemEspaco, linha)) {
                        cp.setConsole(i, terminalAtual, linha, "Inteiro");
                    } else {
                        cp.setConsole(0, "Erro Sint�tico", linha, "");
                        terminado = true;
                        posicaoAtual = txtSemEspaco.length() - 1;
                    }
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
        //Passa para pr�xima posi��o
        posicaoAtual++;
        //Caso seja um n�mero, armazena ele
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
                    if (as.Entrada(i, txtSemEspaco, linha)) {
                        cp.setConsole(i, terminalAtual, linha, "Real");
                    } else {
                        cp.setConsole(0, "Erro Sint�tico", linha, "");
                        terminado = true;
                        posicaoAtual = txtSemEspaco.length() - 1;
                    }
                    Inicio();
                }
                i++;
            }
        }
    }

    private void L() {
        //Verifica se o pr�ximo caracter necess�rio para iniciar um coment�rio de linha existe, mando para a pr�xima fun��o
        if (charDoTexto[posicaoAtual] == '%') {
            //Passa para pr�xima posi��o
            posicaoAtual++;
            M();
        } else {
            cp.setConsole(0, "Um Erro Foi encontrado", linha, "Caracter invalido encontrado");
            terminado = true;
            posicaoAtual = txtSemEspaco.length() - 1;
        }
    }

    private void M() {
        //Enquanto uma quebra de linha n�o � encontrada, passamos pelo coment�rio
        if (charDoTexto[posicaoAtual] == '�' || charDoTexto[posicaoAtual] == '$') {

            Inicio();
        } else {
            posicaoAtual++;

            M();
        }
        //Passa para pr�xima linha

    }

    private void N() {
        //Passa para pr�xima posi��o
        posicaoAtual++;
        //Verifica se o pr�ximo caracter necess�rio para o simbolo de atribui��o existe, mandando para uma nova fun��o
        if (charDoTexto[posicaoAtual] == '=') {
            terminalAtual += charDoTexto[posicaoAtual];
            O();
        }//Sen�o classifica-o como simbolo
        int i = 0;
        //Verifica se o texto armazenado se encontra nas palavras reservadas, adicionando ele ao console.
        for (String palavraCorrente : terminais) {
            if (terminalAtual.toLowerCase().equals(palavraCorrente)) {
                if (as.Entrada(i, txtSemEspaco, linha)) {
                    cp.setConsole(i, terminalAtual, linha, "Simbolo");
                } else {
                    cp.setConsole(0, "Erro Sint�tico", linha, "");
                    terminado = true;
                    posicaoAtual = txtSemEspaco.length() - 1;
                }
                Inicio();
            }
            i++;
        }
    }

    private void O() {
        //Passa para pr�xima linha
        posicaoAtual++;
        int i = 0;
        //Verifica se o texto armazenado se encontra nas palavras reservadas, adicionando ele ao console.
        for (String palavraCorrente : terminais) {
            if (terminalAtual.toLowerCase().equals(palavraCorrente)) {
                if (as.Entrada(i, txtSemEspaco, linha)) {
                    cp.setConsole(i, terminalAtual, linha, "Operador de Atribui��o");
                } else {
                    cp.setConsole(0, "Erro Sint�tico", linha, "");
                    terminado = true;
                    posicaoAtual = txtSemEspaco.length() - 1;
                }
                Inicio();
            }
            i++;
        }
    }

    private void P() {
        //Passa para pr�xima linha
        posicaoAtual++;
        int i = 0;
        //Verifica se o texto armazenado se encontra nas palavras reservadas, adicionando ele ao console.
        for (String palavraCorrente : terminais) {
            if (terminalAtual.toLowerCase().equals(palavraCorrente)) {
                if (as.Entrada(i, txtSemEspaco, linha)) {
                    cp.setConsole(i, terminalAtual, linha, "Simbolo");
                } else {
                    cp.setConsole(0, "Erro Sint�tico", linha, "");
                    terminado = true;
                    posicaoAtual = txtSemEspaco.length() - 1;
                }
                Inicio();
            }
            i++;
        }
    }

    private void Q() {
        //Passa para pr�xima linha
        posicaoAtual++;
        int i = 0;
        //Verifica se o texto armazenado se encontra nas palavras reservadas, adicionando ele ao console.
        for (String palavraCorrente : terminais) {
            if (terminalAtual.toLowerCase().equals(palavraCorrente)) {
                if (as.Entrada(i, txtSemEspaco, linha)) {
                    cp.setConsole(i, terminalAtual, linha, "Simbolo Aritim�tico");
                } else {
                    cp.setConsole(0, "Erro Sint�tico", linha, "");
                    terminado = true;
                    posicaoAtual = txtSemEspaco.length() - 1;
                }
                Inicio();
            }
            i++;
        }
    }

}
