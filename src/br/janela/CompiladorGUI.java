package br.janela;

import br.Matriz.Matriz;
import br.Matriz.Regras;
import br.arquivo.arquivo;
import br.listener.Lexico;
import br.listener.Sintatico;
import java.awt.Color;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.File;
import java.net.URL;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

public class CompiladorGUI extends javax.swing.JFrame {

    Lexico lal;

    DefaultTableModel model, model2;
    public CompiladorGUI() {
        initComponents();
       
        model = (DefaultTableModel) console.getModel();
        model2 = (DefaultTableModel) Sintatico.getModel();
        lal = new Lexico(this);
        arquivo arq = new arquivo(this);
        Analisar.addActionListener(lal);
        Analisar.setActionCommand("Analisar");
        Abrir.addActionListener(arq);
        Abrir.setActionCommand("Abrir");
        Salvar.addActionListener(arq);
        Salvar.setActionCommand("Salvar");
        
        URL url = this.getClass().getResource("imagem.png"); 
        Image imagemTitulo = Toolkit.getDefaultToolkit().getImage(url);  
        this.setIconImage(imagemTitulo);  
    }

    public void setConsole(int c, String t, int l, String i){
       model.addRow(new Object[]{String.valueOf(c),t,String.valueOf(l),i});
       console.setModel(model);
    }
    
    public void setSintatico(int x, int a, int regra){
       model2.addRow(new Object[]{String.valueOf(x),String.valueOf(a),String.valueOf(regra)});
       Sintatico.setModel(model2);
    }
    
    public void cleanConsole(){
        model.setNumRows(0);
        model2.setNumRows(0);
    }
    public JTextArea getEditor() {
        return txtAreaEditor;
    }

    public void setEditor(String linha) {
        linha = linha + "\n";
        this.txtAreaEditor.append(linha);
    }
    
    public void cleanEditor(){
        txtAreaEditor.setText("");
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        txtAreaEditor = new javax.swing.JTextArea();
        Analisar = new javax.swing.JButton();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jScrollPane3 = new javax.swing.JScrollPane();
        console = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        Sintatico = new javax.swing.JTable();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        Abrir = new javax.swing.JMenuItem();
        Salvar = new javax.swing.JMenuItem();
        jMenuItem5 = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenu4 = new javax.swing.JMenu();
        jMenuItem3 = new javax.swing.JMenuItem();
        jMenuItem6 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("I.O.N.D.U.L.A - Insira o Nome de Uma Linguagem Aqui");
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setResizable(false);

        txtAreaEditor.setColumns(20);
        txtAreaEditor.setRows(5);
        jScrollPane1.setViewportView(txtAreaEditor);

        Analisar.setText("Analisar");
        Analisar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AnalisarActionPerformed(evt);
            }
        });

        jScrollPane3.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane3.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        jScrollPane3.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        console.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Codigo Token", "Token", "Linha", "Identificação"
            }
        ));
        console.setEnabled(false);
        console.getTableHeader().setReorderingAllowed(false);
        jScrollPane3.setViewportView(console);

        jTabbedPane1.addTab("Analise Lexica", jScrollPane3);

        Sintatico.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "X", "A", "Regra Empilhada"
            }
        ));
        Sintatico.setEnabled(false);
        Sintatico.getTableHeader().setReorderingAllowed(false);
        jScrollPane2.setViewportView(Sintatico);

        jTabbedPane1.addTab("Analise Sintatica", jScrollPane2);

        jMenu1.setText("Arquivo");

        Abrir.setText("Abrir");
        Abrir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AbrirActionPerformed(evt);
            }
        });
        jMenu1.add(Abrir);

        Salvar.setText("Salvar");
        jMenu1.add(Salvar);

        jMenuItem5.setText("Sair");
        jMenuItem5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem5ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem5);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Ajuda");

        jMenuItem1.setText("Ajuda");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem1);

        jMenuItem2.setText("Sobre");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem2);

        jMenuBar1.add(jMenu2);

        jMenu4.setText("Design");

        jMenuItem3.setText("Dark");
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        jMenu4.add(jMenuItem3);

        jMenuItem6.setText("Default");
        jMenuItem6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem6ActionPerformed(evt);
            }
        });
        jMenu4.add(jMenuItem6);

        jMenuBar1.add(jMenu4);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 911, Short.MAX_VALUE)
                .addComponent(Analisar, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addComponent(jTabbedPane1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 360, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(Analisar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 237, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void AnalisarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AnalisarActionPerformed

       
    }//GEN-LAST:event_AnalisarActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
    JOptionPane.showMessageDialog(rootPane, String.format("<html><center>Para maiores informações sobre a linguagem,<br> acesse o manual disponivel em:<br>http://bit.do/IONDULA</center></html>"), "Ajuda", 3);
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
    JOptionPane.showMessageDialog(rootPane, String.format("<html><center>Desenvolvido por:<br>Felipe Garcia de Souza<br>Luiz Veríssimo da Silva de Souza<br><br>Copyright © 2015</center></html>"), "Sobre", JOptionPane.PLAIN_MESSAGE);
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void AbrirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AbrirActionPerformed

    }//GEN-LAST:event_AbrirActionPerformed

    private void jMenuItem5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem5ActionPerformed
    System.exit(0);
    }//GEN-LAST:event_jMenuItem5ActionPerformed

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
    txtAreaEditor.setForeground(Color.green);
    txtAreaEditor.setBackground(Color.black);
    }//GEN-LAST:event_jMenuItem3ActionPerformed

    private void jMenuItem6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem6ActionPerformed
    txtAreaEditor.setForeground(Color.black);
    txtAreaEditor.setBackground(Color.white);
    }//GEN-LAST:event_jMenuItem6ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem Abrir;
    private javax.swing.JButton Analisar;
    private javax.swing.JMenuItem Salvar;
    private javax.swing.JTable Sintatico;
    private javax.swing.JTable console;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem5;
    private javax.swing.JMenuItem jMenuItem6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTextArea txtAreaEditor;
    // End of variables declaration//GEN-END:variables
}
