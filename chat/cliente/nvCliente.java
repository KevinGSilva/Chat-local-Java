package chat.cliente;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.Socket;
import javax.swing.*;
import java.awt.SystemColor;
import java.awt.Font;
import javax.swing.border.EtchedBorder;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

//Classe que estende JFrame para criação gráfica do chat
public class nvCliente extends JFrame implements ActionListener, KeyListener {

	private static final long serialVersionUID = 1L;
	private JTextArea texto;
	private JTextField txtMsg;
	private JButton btnSend;
	private JButton btnSair;
	private JPanel pnlContent;
	private Socket socket;
	private OutputStream ou ;
	private Writer ouw;
	private BufferedWriter bfw;
	private JTextField txtIP;
	private JTextField txtPorta;
	private JTextField txtNome;
	private JLabel lbl_mover;
	private int xx, xy;
	private JLabel lblStatus;
	private JLabel lblUser;
	private JLabel lblNewLabel;

	
	
	public nvCliente() throws IOException{
		setUndecorated(true);
		setFont(new Font("Dialog", Font.BOLD, 12));
		setIconImage(Toolkit.getDefaultToolkit().getImage(nvCliente.class.getResource("/chat/cliente/Sem T\u00EDtulo-3.png")));
		setVisible(true);
    	JLabel lblMessage = new JLabel("Verificar!");
    	txtIP = new JTextField("127.0.0.1");
    	JLabel lblChave = new JLabel("Insira a porta:");
    	txtPorta = new JTextField("52500");
    	JLabel lblnomeCliente = new JLabel("Insira seu nome:");
    	txtNome = new JTextField("");
    	Object[] texts = {lblMessage, lblChave, txtPorta, lblnomeCliente, txtNome };
    	JOptionPane.showMessageDialog(null, texts);
    	 pnlContent = new JPanel();
    	 pnlContent.setForeground(Color.LIGHT_GRAY);
    	 texto              = new JTextArea(20,80);
    	 texto.setWrapStyleWord(true);
    	 texto.setFont(new Font("Microsoft New Tai Lue", Font.BOLD, 15));
    	 texto.setDisabledTextColor(SystemColor.textInactiveText);
    	 texto.setForeground(SystemColor.inactiveCaptionBorder);
    	 texto.setSelectedTextColor(Color.WHITE);
    	 texto.setEditable(false);
    	 texto.setBackground(new Color(27, 27, 27));
    	 txtMsg                       = new JTextField(40);
    	 txtMsg.setCaretColor(Color.RED);
    	 txtMsg.setDisabledTextColor(Color.LIGHT_GRAY);
    	 txtMsg.setForeground(Color.WHITE);
    	 txtMsg.setBounds(40, 476, 279, 36);
    	 txtMsg.setBackground(new Color(36, 36, 36));
    	 txtMsg.setFont(new Font("Tahoma", Font.PLAIN, 16));
    	 btnSair           = new JButton("X");
    	 btnSair.setBorder(null);
    	 btnSair.setBackground(Color.RED);
    	 btnSair.setForeground(Color.WHITE);
    	 btnSair.setFont(new Font("Tahoma", Font.PLAIN, 13));
    	 btnSair.setBounds(379, -1, 41, 23);
    	 btnSair.setToolTipText("Sair do Chat");
    	 btnSair.addActionListener(this);
    	 txtMsg.addKeyListener(this);
    	 pnlContent.setLayout(null);
    	 JScrollPane scroll = new JScrollPane(texto);
    	 scroll.setBorder(null);
    	 scroll.setBackground(Color.DARK_GRAY);
    	 scroll.setBounds(29, 56, 366, 383);
    	 texto.setLineWrap(true);
    	 pnlContent.add(scroll);
    	 pnlContent.add(txtMsg);
    	 btnSend                     = new JButton("");
    	 btnSend.setIconTextGap(-6);
    	 btnSend.setFont(new Font("Tahoma", Font.PLAIN, 9));
    	 btnSend.setForeground(Color.WHITE);
    	 btnSend.setBackground(new Color(95, 95, 95));
    	 btnSend.setIcon(new ImageIcon(nvCliente.class.getResource("/chat/cliente/btnEnviar.png")));
    	 btnSend.setBounds(323, 483, 65, 23);
    	 btnSend.setToolTipText("Tecle ENTER para enviar");
    	 btnSend.addActionListener(this);
    	 btnSend.addKeyListener(this);
    	 pnlContent.add(btnSend);
    	 pnlContent.add(btnSair);
    	 pnlContent.setBackground(new Color(44, 44, 44));
    	 texto.setBorder(new EtchedBorder(EtchedBorder.LOWERED, SystemColor.menuText, SystemColor.windowText));
    	 txtMsg.setBorder(null);
    	 setTitle("Chat Corporativo Local");
    	 setContentPane(pnlContent);
    	 
    	
    	 //Função para movimentação da janela utilizando um Label  
    	 lbl_mover = new JLabel("");
    	 lbl_mover.addMouseListener(new MouseAdapter() {
    	 	@Override
    	 	public void mousePressed(MouseEvent arg0) {
    	 		xx = arg0.getX();
				xy = arg0.getY();
    	 	}
    	 });
    	 lbl_mover.addMouseMotionListener(new MouseMotionAdapter() {
    	 	@Override
    	 	public void mouseDragged(MouseEvent e) {
    	 		int x = e.getXOnScreen();
				int y = e.getYOnScreen();
				
				nvCliente.this.setLocation(x - xx, y - xy);
    	 	}
    	 });
    	 lbl_mover.setIcon(null);
    	 lbl_mover.setBounds(0, 0, 420, 28);
    	 pnlContent.add(lbl_mover);
    	 
    	 lblStatus = new JLabel("");
    	 lblStatus.setFont(new Font("Tahoma", Font.BOLD, 14));
    	 lblStatus.setForeground(Color.WHITE);
    	 lblStatus.setBounds(10, 551, 113, 23);
    	 pnlContent.add(lblStatus);
    	 
    	 lblUser = new JLabel("");
    	 lblUser.setFont(new Font("Tahoma", Font.BOLD, 14));
    	 lblUser.setForeground(Color.WHITE);
    	 lblUser.setBounds(29, 31, 273, 23);
    	 pnlContent.add(lblUser);
    	 
    	 lblNewLabel = new JLabel("");
    	 lblNewLabel.setIcon(new ImageIcon(nvCliente.class.getResource("/chat/cliente/Background.png")));
    	 lblNewLabel.setBounds(0, -1, 420, 580);
    	 pnlContent.add(lblNewLabel);
    	 setLocationRelativeTo(null);
    	 setResizable(false);
    	 setSize(420,580);
    	 setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	/*
    Método usado para conectar no server socket.
  */
	public void conectar() throws IOException{

  	socket = new Socket(txtIP.getText(),Integer.parseInt(txtPorta.getText()));
  	ou = socket.getOutputStream();
  	ouw = new OutputStreamWriter(ou);
  	bfw = new BufferedWriter(ouw);
  	bfw.write(txtNome.getText()+"\r\n");
  	bfw.flush();
  	lblStatus.setText("Conectado...");
  	lblUser.setText("Usuário: " + txtNome.getText());
	}

	/*
   Método usado para enviar mensagem para o server socket
   IOException retorna uma mensagem caso dê algum erro.
   */
  	public void enviarMensagem(String msg) throws IOException{

    	if(msg.equals("close_application")){
      		bfw.write(txtNome.getText() + " foi desconectado \r\n");
      		texto.append("Desconectado... \r\n");
      		lblStatus.setText("Desconectado...");
      		JOptionPane.showMessageDialog(null, "Você foi desconectado!");
      		
      		
    	}
    	else{
      		bfw.write(msg+"\r\n");
      		texto.append( txtNome.getText() + ": " +         txtMsg.getText()+"\r\n");
    	}
     	bfw.flush();
     	txtMsg.setText("");
	}

	/**
 * Método usado para receber mensagem do servidor
 * /throws IOException retorna IO Exception caso dê algum erro.
 */
	public void escutar() throws IOException{

   InputStream in = socket.getInputStream();
   InputStreamReader inr = new InputStreamReader(in);
   BufferedReader bfr = new BufferedReader(inr);
   String msg = "";

    while(!"close_application".equalsIgnoreCase(msg))

       if(bfr.ready()){
         msg = bfr.readLine();
       if(msg.equals("close_application"))
         texto.append("Servidor caiu! \r\n");
        else
         texto.append(msg+"\r\n");
        }
    
	}

	/*
    Método usado quando o usuário clica em sair
    throws IOException retorna IO Exception caso dê algum erro.
    */
  	public void sair() throws IOException{

   		enviarMensagem("close_application");
   		bfw.close();
   		ouw.close();
   		ou.close();
   		socket.close();
   		
   		
   		System.exit(0);
	}

	//@Override
	public void actionPerformed(ActionEvent e) {

  try {
     if(e.getActionCommand().equals(btnSend.getActionCommand()))
        enviarMensagem(txtMsg.getText());
     else
        if(e.getActionCommand().equals(btnSair.getActionCommand()))
        sair();
     } catch (IOException e1) {
          // TODO Auto-generated catch block
          e1.printStackTrace();
     }
	}

	//@Override
	public void keyPressed(KeyEvent e) {

    	if(e.getKeyCode() == KeyEvent.VK_ENTER){
       		try {
          		enviarMensagem(txtMsg.getText());
       		} 
       		catch (IOException e1) {
           // TODO Auto-generated catch block
           		e1.printStackTrace();
       		}
   		}
	}

	//@Override
	public void keyReleased(KeyEvent arg0) {
  		// TODO Auto-generated method stub
	}

	//@Override
	public void keyTyped(KeyEvent arg0) {
  		// TODO Auto-generated method stub
	}

	public static void main(String []args) throws IOException{

   		nvCliente app = new nvCliente();
   		app.conectar();
   		app.escutar();
	}
}
