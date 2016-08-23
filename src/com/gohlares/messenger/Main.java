package com.gohlares.messenger;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.text.BadLocationException;
import javax.swing.text.Element;
import javax.swing.text.StyleConstants;
import javax.swing.text.html.HTML;
import javax.swing.text.html.HTMLDocument;
import rmi.Message;
import rmi.PeerInfo;
import rmi.interfaces.MessageInterface;
import rmi.interfaces.PeerInfoInterface;
import rmi.interfaces.PeerInterface;

public class Main extends javax.swing.JFrame {
    
    static Server server;
    static Client client;
    static PeerInfo myInfo;
    static String user = null;
    
    private DefaultListModel usersModel = new DefaultListModel<>();
    private PeerInfo currentChat = null;
    private Map<String, ArrayList<MessageTuple>> messages = new HashMap<>();

    Thread discoveryThread = new Thread(DiscoveryThread.getInstance());

    /**
     * Creates new form Main
     */
    public Main() {
        initComponents();

        // TODO: get port and username other settings
        server = new Server(1099, user);
        server.listen(this::receiveMessage);

        // TODO: get port and username other settings
        client = new Client(1099, user);

        discoveryThread.start();
        DiscoveryThread.addListener(this::updateList);

        messageField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER)
                    sendMessage();
            }
        });
        usersList.addListSelectionListener((ListSelectionEvent e) -> {
            this.currentChat = (PeerInfo) usersModel.get(usersList.getSelectedIndex());
            this.loadChat();
        });

        usersList.setModel(usersModel);

        usersModel.add(0, Server.getInfo());
    }

    /**
     * Insere um usuário na lista, caso ele ainda não esteja
     * @param ip O IP do usuário a ser adicionado
     * @param uuid o UUID do usuário a ser adicionado
     */
    private void updateList(String ip, String uuid) {

        try {
            PeerInterface found = client.get(ip);
            if (found == null) {
                //TODO: Remover da lista
                System.err.println("REMOVER "+ip);
            } else {
                PeerInfoInterface foundInfo = found.getInfo();
                if (!usersModel.contains(foundInfo)) {
                    usersModel.add(usersModel.getSize(), foundInfo);
                } else {
                    usersModel.set(usersModel.indexOf(foundInfo), foundInfo);
                }
            }
        } catch (RemoteException ex) {
            System.err.println("IP "+ ip +" não contém os objetos necessarios.");
        }
    }
    
    /**
     * Carrega o HTML do chat que estiver em foco
     */
    private void loadChat() {

        if (this.currentChat == null)
            return;
        String key = this.currentChat.getUUID();

        HTMLDocument doc = (HTMLDocument) messageArea.getDocument();
        Element body = doc.getElement(doc.getDefaultRootElement(), StyleConstants.NameAttribute, HTML.Tag.BODY);

        try {
            doc.setInnerHTML(body, "<p style='margin-top:-10px'></p>");
        } catch (BadLocationException | IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        if (this.messages.containsKey(key)) {
            this.messages.get(key).forEach((m) -> {

                String from;
                if (m.other) {
                    from = "<font color='green' style='font-weight:bold;'>" + this.currentChat.getUserName() + ":</font> ";
                } else {
                    from = "<font color='navy' style='font-weight:bold;'>Você:</font> ";
                }

                try {
                    doc.insertBeforeEnd(body, "<p style='margin-top:0'>" + from + m.message.getBody() + "</p>");

                } catch (BadLocationException | IOException ex) {
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
        }
    }
    
    /**
     * Adiciona uma mensagem à lista de conversas
     * @param key O UUID do usuário com o qual acontece a conversa
     * @param other `true` se a mensagem for do outro usuário e `false` se for o próprio usuário
     * @param body O conteúdo da mensagem
     */
    private void addMessage(String key, boolean other, String body) {
        if (!this.messages.containsKey(key)) {
            this.messages.put(key, new ArrayList<>());
        }

        this.messages.get(key).add(new MessageTuple(other, body));
//        this.messages.forEach((s, messageTuples) -> messageTuples.forEach(messageTuple -> System.out.println(s+": "+messageTuple.other+" - "+messageTuple.message.getBody())));
    }

    /**
     * Envia a mensagem contida no campo de texto para o destinatário referente
     * à conversa aberta. Além disso, salva a mensagem enviada e exibe no HTML.
     */
    private void sendMessage() {
        String key = this.currentChat.getUUID();
        String body = messageField.getText();
        addMessage(key, false, body);
        
        messageField.setText("");
        
        client.send(this.currentChat.getIp(), body);
        
        loadChat();
    }

    /**
     * Adiciona à lista de conversas uma mensagem recebida e a exibe no HTML.
     * @param from Remetente da mensagem.
     * @param message A mensagem recebida.
     */
    private void receiveMessage(PeerInfoInterface from, MessageInterface message) {
        String key = from.getUUID();
        String body = message.getBody();
        addMessage(key, true, body);

        loadChat();
    }

    /**
     * This method is called other within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jSplitPane = new javax.swing.JSplitPane();
        rightPanel = new javax.swing.JPanel();
        messageField = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        messageArea = new javax.swing.JEditorPane();
        leftPanel = new javax.swing.JPanel();
        userToolbar = new javax.swing.JToolBar();
        filler2 = new javax.swing.Box.Filler(new java.awt.Dimension(8, 0), new java.awt.Dimension(8, 0), new java.awt.Dimension(8, 32767));
        userName = new javax.swing.JLabel();
        filler1 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(32767, 0));
        usersPane = new javax.swing.JScrollPane();
        usersList = new javax.swing.JList<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(600, 450));

        jSplitPane.setContinuousLayout(true);
        jSplitPane.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        jScrollPane1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        messageArea.setEditable(false);
        messageArea.setContentType("text/html"); // NOI18N
        messageArea.setText("<html>\n  <head>\n\n  </head>\n  <body>\n    <p style=\"margin-top: 0\">\n      Escolha um contato na lista à esquerda.\n    </p>\n  </body>\n</html>\n");
        jScrollPane1.setViewportView(messageArea);

        javax.swing.GroupLayout rightPanelLayout = new javax.swing.GroupLayout(rightPanel);
        rightPanel.setLayout(rightPanelLayout);
        rightPanelLayout.setHorizontalGroup(
            rightPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, rightPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(messageField, javax.swing.GroupLayout.DEFAULT_SIZE, 399, Short.MAX_VALUE)
                .addContainerGap())
            .addComponent(jScrollPane1)
        );
        rightPanelLayout.setVerticalGroup(
            rightPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(rightPanelLayout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 307, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(messageField, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jSplitPane.setRightComponent(rightPanel);

        leftPanel.setMinimumSize(new java.awt.Dimension(150, 0));

        userToolbar.setFloatable(false);
        userToolbar.setRollover(true);
        userToolbar.add(filler2);

        userName.setText(user);
        userToolbar.add(userName);
        userToolbar.add(filler1);

        usersList.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        usersList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        usersPane.setViewportView(usersList);

        javax.swing.GroupLayout leftPanelLayout = new javax.swing.GroupLayout(leftPanel);
        leftPanel.setLayout(leftPanelLayout);
        leftPanelLayout.setHorizontalGroup(
            leftPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(userToolbar, javax.swing.GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE)
            .addComponent(usersPane)
        );
        leftPanelLayout.setVerticalGroup(
            leftPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(leftPanelLayout.createSequentialGroup()
                .addComponent(userToolbar, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(usersPane, javax.swing.GroupLayout.DEFAULT_SIZE, 334, Short.MAX_VALUE))
        );

        jSplitPane.setLeftComponent(leftPanel);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSplitPane)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSplitPane)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    public static void main(String args[]) {
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
        * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
        */
        try {
        for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
        /*if ("Nimbus".equals(info.getName())) {
        javax.swing.UIManager.setLookAndFeel(info.getClassName());
        break;
        }*/
        javax.swing.UIManager.setLookAndFeel(javax.swing.UIManager.getSystemLookAndFeelClassName());
        }
        } catch (ClassNotFoundException ex) {
        java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
        java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
        java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
        java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        user = (String) JOptionPane.showInputDialog(null, "Escolha um nome de usuário: ", "RMI Chat", JOptionPane.YES_OPTION);
        
        if (user == null) {
            return;
        }
        
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new Main().setVisible(true);
        });
    }

    public class MessageTuple {
        public final boolean other;
        public final Message message;
        
        public MessageTuple(boolean other, Message message) {
            this.other = other;
            this.message = message; 
        }
        
        public MessageTuple(boolean other, String body) {
            this.other = other;
            this.message = new Message(body);
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.Box.Filler filler1;
    private javax.swing.Box.Filler filler2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSplitPane jSplitPane;
    private javax.swing.JPanel leftPanel;
    private javax.swing.JEditorPane messageArea;
    private javax.swing.JTextField messageField;
    private javax.swing.JPanel rightPanel;
    private javax.swing.JLabel userName;
    private javax.swing.JToolBar userToolbar;
    private javax.swing.JList<String> usersList;
    private javax.swing.JScrollPane usersPane;
    // End of variables declaration//GEN-END:variables
}
