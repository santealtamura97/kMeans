import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.URL;
import java.rmi.ServerException;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 * <p>Description:Tale classe definisce una JAPPLET
 * @author sante
 */
public class KMeans extends JFrame{
	private ObjectOutputStream out; 
	private ObjectInputStream in ;
	Socket socket;
	
	public static void main(String [] args) {
		KMeans Applet = new KMeans();
		Applet.init();
	}	
	/**
	 * Inizializza la componente grafica della interfaccia grafica  istanziando un oggetto della classe  
	 * JtabbedPane e aggiungendolo al container della JApplet
	 */
	public void init(){
		TabbedPane tab=new TabbedPane();
		getContentPane().setLayout(new GridLayout(1,1));
		getContentPane().add(tab);
		this.setSize(800,800);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.show();
	}
	private class TabbedPane extends JPanel{
		private JPanelCluster panelDB;
		private JPanelCluster panelFile; 
		private class JPanelCluster extends JPanel{
			private JTextField tableText=new JTextField(20); 
			private JTextField kText=new JTextField(10);
			private JTextArea clusterOutput=new JTextArea(""); 
			private JButton executeButton;
			/**
			 * Inizializza il pannello. 
			 * Aggiunge l'ascoltatore a al bottone executeButton.
			 * @param buttonName Nome del bottone
			 * @param a Rappresenta l'ascoltatore dell'executebutton
			 */
			JPanelCluster(String buttonName, ActionListener a){
				JScrollPane scroll = new JScrollPane (clusterOutput, 
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
				executeButton = new JButton(buttonName);
				executeButton.addActionListener(a);
				clusterOutput.setColumns(30);
				clusterOutput.setRows(30);
				clusterOutput.setAutoscrolls(true);
				
				JPanel upPanel = new JPanel();
				JPanel downPanel = new JPanel();	
				JLabel nameTableText = new JLabel("Table");
				JLabel nameKText = new JLabel("K");
				
				
				upPanel.add(nameTableText);
				upPanel.add(tableText);
				if(buttonName.equals("MINE")) {
					upPanel.add(nameKText);
					upPanel.add(kText);
				}
				downPanel.add(executeButton);
				
				this.setLayout(new BorderLayout());
				this.add(upPanel,BorderLayout.PAGE_START);
				this.add(scroll,BorderLayout.CENTER);
				this.add(downPanel,BorderLayout.PAGE_END);
			}
		}
		/**
		 * Avvia la richiesta di connessione al Server ed inizializza i flussi di comunicazione (membri dato  in e out).
		 * Inizializza i membri panelDB e panelFile e li aggiunge ad un oggetto istanza della classe TabbedPane. 
		 * Tale oggetto (TabbedPane) è quindi inserito nel pannello che si sta costruendo. 
		 */
		TabbedPane() {
			super(new GridLayout(1, 1)); 
			boolean flagServerConnection;
			do {
				String ip=this.getParameter("ServerIP");
				int port = 8080;
				try
				{
					InetAddress addr = InetAddress.getByName(ip); //ip
					System.out.println("addr = " + addr);
					socket = new Socket(addr, port); //Port
					System.out.println(socket);
				
					out = new ObjectOutputStream(socket.getOutputStream());
					in = new ObjectInputStream(socket.getInputStream()); 
				flagServerConnection = true;
				}catch(IOException e){
					flagServerConnection = false;
				}
			}while(!flagServerConnection);
			
			JTabbedPane tabbedPane = new JTabbedPane();
			URL imgURL = getClass().getResource("db.jpg");
			ImageIcon iconDB = new ImageIcon(imgURL);
			panelDB = new JPanelCluster("MINE", new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try{
						learningFromDBAction();
					}
					catch (SocketException e1) {
						System.out.println(e1);

					}
					catch (FileNotFoundException e1) {
						System.out.println(e1);
						
					} catch (IOException e1) {
						System.out.println(e1);
						
					} catch (ClassNotFoundException e1) {
						System.out.println(e1);
						
					}
				}
		      });
	        tabbedPane.addTab("DB", iconDB, panelDB,
	                "Does nothing");
	      
	        imgURL = getClass().getResource("file.jpg");
	        ImageIcon iconFile = new ImageIcon(imgURL);
	        panelFile = new JPanelCluster("STORE FROM FILE",new java.awt.event.ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try{
						learningFromFileAction();
					}
					catch (SocketException e1) {
						System.out.println(e1);
						
					}
					catch (FileNotFoundException e1) {
						System.out.println(e1);
						
					} catch (IOException e1) {
						System.out.println(e1);
						
					} catch (ClassNotFoundException e1) {
						System.out.println(e1);
						
					}		
				}
		      });
	        tabbedPane.addTab("FILE", iconFile, panelFile,
	                "Does nothing");
	        add(tabbedPane);         
	        tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
		}
		
		private String getParameter(String param) {
			String serverAddress="127.0.0.1";
			if(param == "ServerIP") {
			serverAddress = JOptionPane.showInputDialog(
			            "Inserire un indirizzo IP valido della macchina su cui è\n" +
			            "in esecuzione il servizio dati sulla porta 8080,altrimenti\n"
			            + "verrà utilizzato quello di default(127.0.0.1):");
			}
			return serverAddress;
		}
		
		
		/**
		 * Acquisisce  il numero di cluster  (panelDB.kText.getText()). 
		 * Qualora il valore acquisito non fosse un numero positivo visualizza 
		 * un messaggio di errore all'interno di una JOptionPane.
		 * Acquisisce in nome della tabella (da panelDB.tableText). 
		 * Trasmette al server il comando 0 e il nome della tabella. 
		 * Resta in attesa della risposta del server. 
		 * In caso di risposta diversa da "OK", visualizza un messaggio
		 * di errore in una JOptionPane e termina l'esecuzione del metodo. 
		 * Altrimenti, invia il comando 1 e il numero di cluster da scoprire
		 * e aspetta la risposta del server. In caso di risposta diversa da "OK",
		 * visualizza un messaggio di errore in una JOptionPane e termina l'esecuzione del metodo. 
		 * Altrimenti legge il numero di iterazioni e i cluster così come sono trasmessi da server 
		 * e li visualizza in panelDB.clusterOutput. Invia al server il comando 2 e 
		 * aspetta la risposta del server. In caso di risposta diversa da "OK", 
		 * visualizza un messaggio di errore in una JOptionPane e termina l'esecuzione del metodo,
		 * altrimenti visualizza un messaggio che confermi il successo della attività in una JOptionPane. 
		 * @throws SocketException
		 * @throws IOException
		 * @throws ClassNotFoundException
		 */
		private void learningFromDBAction()throws SocketException,IOException,ClassNotFoundException{
			Integer k; 
			try{  
				//k=new Integer(panelDB.kText.getText()).intValue(); 
				k = Integer.parseInt(panelDB.kText.getText());
				} catch(NumberFormatException e){   
					JOptionPane.showMessageDialog(this,"Numero di cluster K non valido.");
					return; 
				}  
			try {
				String tableName;
				tableName = new String(panelDB.tableText.getText());
				if(tableName.isEmpty())
					throw new ServerException("E' necessario inserire un identificatore di tabella.");
				out.writeObject(0);
				out.writeObject(tableName);
				String result = (String)in.readObject();
				if(!result.equals("OK"))
					throw new ServerException(result);
				out.writeObject(1);
				out.writeObject(k);
				result = (String)in.readObject();
				if(result.equals("OK")){
					String output = (String)in.readObject();
					panelDB.clusterOutput.setText(output);
					out.writeObject(2);
					String fileName = panelDB.tableText.getText()+".dmp";
					out.writeObject(fileName);
					result = (String)in.readObject();
					if(!result.equals("OK"))
						 throw new ServerException(result);
					JOptionPane.showMessageDialog(this,"Salvataggio avvenuto con successo.");
				}else throw new ServerException(result);	
			}catch(ServerException e) {
				JOptionPane.showMessageDialog(this,e.getMessage());
				return;
			}
		}
	
		/**
		 * Acquisisce il nome della tabella (da panelFile.tableText)
		 * e il numero dei cluster (da panelFile.kText).
		 * Invia al server il comando 3, il nome della tabella e il 
		 * numero dei cluster e aspetta la risposta del server. 
		 * In caso di risposta diversa da "OK", visualizza un 
		 * messaggio di errore in una JOptionPane e termina 
		 * l'esecuzione del metodo, altrimenti visualizza, 
		 * in una JOptionPane, un messaggio che confermi il successo
		 * della attività. 
		 * @throws SocketException
		 * @throws IOException
		 * @throws ClassNotFoundException
		 */
		private void learningFromFileAction() throws SocketException,IOException,ClassNotFoundException{
			try {
				out.writeObject(3);
				out.writeObject(panelFile.tableText.getText());
				String result = (String)in.readObject();
				if(result.equals("OK")) {
					String output = (String)in.readObject();
					panelFile.clusterOutput.setText(output);
				}else {
					throw new ServerException(result);
				}
				}catch(ServerException e) {
					JOptionPane.showMessageDialog(this,e.getMessage());
					return;
				}
		}
	}
}
	
	


