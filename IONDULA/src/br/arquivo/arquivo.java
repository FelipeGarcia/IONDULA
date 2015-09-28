package br.arquivo;

import br.janela.CompiladorGUI;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;


public class arquivo implements ActionListener {

    CompiladorGUI cp;

    public arquivo(CompiladorGUI cp) {
        this.cp = cp;
    }

    @Override
    public void actionPerformed(ActionEvent arq) {
        switch (arq.getActionCommand()) {
            case "Abrir": {
                Abrir();
                break;
            }
            case "Salvar": {
                Salvar();
                break;
            }
        }
    }

    public void Abrir() {
        CompiladorGUI gui = new CompiladorGUI();

        JFileChooser fc = new JFileChooser();
        //Define que apenar diretórios podem ser selecionados
        FileNameExtensionFilter filter = new FileNameExtensionFilter("IONDULA FILES", "ion", "IONDULA");
        fc.setFileFilter(filter);
        fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
        //Exibe o diálogo. Deve ser passado por parâmetro o JFrame de origem.
        fc.showOpenDialog(gui);
        //Captura o objeto File que representa o diretório selecionado.
        File file = fc.getSelectedFile();

        cp.cleanEditor();
        try {
            FileReader reader = new FileReader(file);
            BufferedReader input = new BufferedReader(reader);
            String linha;
            while ((linha = input.readLine()) != null) {
                cp.setEditor(linha);
            }
            input.close();
        } catch (IOException ioe) {
            System.out.println(ioe);
        }
    }

    public void Salvar() {

        String txtSemEspaco = cp.getEditor().getText();
        JFileChooser chooser = new JFileChooser();
        chooser.setCurrentDirectory(new File("/home/me/Documents"));
        int retrival = chooser.showSaveDialog(null);
        if (retrival == JFileChooser.APPROVE_OPTION) {
            try {
                FileWriter fw = new FileWriter(chooser.getSelectedFile() + ".ion");
                fw.write(txtSemEspaco.toString());
                fw.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}
