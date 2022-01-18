import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;
import java.util.Map.Entry;
import java.awt.event.ActionEvent;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

public class VentanaSocios extends JFrame {

	private JPanel contentPane;
	protected JPanel panel;
	protected JLabel lblNewLabel;
	protected JTextField txtNombre;
	protected JLabel lblApellido;
	protected JTextField txtApellido;
	protected JLabel lblDni;
	protected JTextField txtDNI;
	protected JLabel lblFecha;
	protected JTextField txtFecha;
	protected JButton btnBuscarS;
	protected JTextField txtNumSocio;
	protected JButton btnAgregar;
	protected JButton btnQuitarS;
	protected JButton btnFinalizar;
	protected JLabel lblId;
	//Capa de negocios:
	static ControlSocios miControl = new ControlSocios();
	 //Formateo de texto:
	static DateTimeFormatter dForma = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	//Creo un array de socios para llenar la tabla
	protected String[][] arraySocios;
	protected JLabel lblSocioActivo;
	protected JTextField txtSocioActivo;
	protected JScrollPane scrollPane;
	protected static JTable tablaSocios;
	protected int filaSeleccionada;
	protected JButton btnCargarLista;
	protected JButton btnGuardarLista;
	protected String txtBackUp = "D:\\OneDrive - frp.utn.edu.ar\\JAVA_workspace\\LaboratorioIII_EjemploParcial2_Ej1\\src\\backUp.txt";
	protected String[] linea; //Temporal para leer los datos del archivo.
	protected File archivo = new File(txtBackUp); //Para verificar si existe archivo de entrada y crearlo en cualquier caso.
	/**
	 * Launch the application.
	 */
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentanaSocios frame = new VentanaSocios();
					frame.setVisible(true);
					//CargarUsuarios();
					//muestroTabla();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public VentanaSocios() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 469);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		panel = new JPanel();
		panel.setBounds(10, 11, 414, 180);
		panel.setBackground(Color.LIGHT_GRAY);
		contentPane.add(panel);
		panel.setLayout(null);
		
		lblNewLabel = new JLabel("Nombre:");
		lblNewLabel.setBounds(10, 11, 78, 14);
		panel.add(lblNewLabel);
		
		txtNombre = new JTextField();
		txtNombre.setBounds(96, 8, 146, 20);
		panel.add(txtNombre);
		txtNombre.setColumns(10);
		
		lblApellido = new JLabel("Apellido:");
		lblApellido.setBounds(10, 39, 78, 14);
		panel.add(lblApellido);
		
		txtApellido = new JTextField();
		txtApellido.setColumns(10);
		txtApellido.setBounds(96, 36, 146, 20);
		panel.add(txtApellido);
		
		lblDni = new JLabel("DNI:");
		lblDni.setBounds(10, 67, 78, 14);
		panel.add(lblDni);
		
		txtDNI = new JTextField();
		txtDNI.setColumns(10);
		txtDNI.setBounds(96, 64, 146, 20);
		panel.add(txtDNI);
		
		lblFecha = new JLabel("Fecha:");
		lblFecha.setBounds(10, 98, 78, 14);
		panel.add(lblFecha);
		
		txtFecha = new JTextField();
		txtFecha.setColumns(10);
		txtFecha.setBounds(96, 95, 146, 20);
		panel.add(txtFecha);
		
		btnBuscarS = new JButton("Buscar Socio");
		btnBuscarS.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				T buscado=miControl.buscarSocio(Integer.parseInt(txtNumSocio.getText()));
				if(buscado!=null) {
					txtNombre.setText(buscado.getNombre());
					txtApellido.setText(buscado.getApellido());
					txtDNI.setText(buscado.getDni().toString());
					txtFecha.setText(buscado.getFechaIns().format(dForma));
					txtSocioActivo.setText(buscado.getSocioActual().toString());
				}else {
					JOptionPane.showMessageDialog(null, "El Socio no existe!");
				}
			}
		});
		btnBuscarS.setBounds(295, 35, 109, 23);
		panel.add(btnBuscarS);
		
		txtNumSocio = new JTextField();
		txtNumSocio.setColumns(10);
		txtNumSocio.setBounds(330, 8, 74, 20);
		panel.add(txtNumSocio);
		
		btnAgregar = new JButton("Agregar Socio");
		btnAgregar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String nom=txtNombre.getText();
				String ape=txtApellido.getText();
				Long dni=Long.parseLong(txtDNI.getText());
				LocalDate fecha=LocalDate.parse(txtFecha.getText(), dForma);
				Integer numSocio;
				if(txtNumSocio.getText().equals("")){
					numSocio=null;
				}else {
					numSocio=Integer.parseInt(txtNumSocio.getText());
				}	
				Socio nuevoSocio=miControl.agregarSocio(nom, ape, dni, fecha,numSocio);
				muestroTabla();
				limpioCampos();
				JOptionPane.showMessageDialog(null, "Nuevo Socio: "+nuevoSocio);
			}

		});
		btnAgregar.setBounds(295, 63, 109, 23);
		panel.add(btnAgregar);
		
		btnQuitarS = new JButton("Quitar Socio");
		btnQuitarS.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Socio socioQuitado=miControl.quitarSocio(Integer.parseInt(txtNumSocio.getText()));
				if(socioQuitado!=null) {
					muestroTabla();
					JOptionPane.showMessageDialog(null, "El socio N°:"+socioQuitado.getNroSocio()+" ha sido quitado.");
					lblSocioActivo.setText(socioQuitado.getSocioActual().toString());
				}else {
					JOptionPane.showMessageDialog(null, "El socio no existe!");
				}
			}
		});
		btnQuitarS.setBounds(295, 94, 109, 23);
		panel.add(btnQuitarS);
		
		lblId = new JLabel("ID:");
		lblId.setBounds(295, 11, 25, 14);
		panel.add(lblId);
		
		lblSocioActivo = new JLabel("Socio Activo:");
		lblSocioActivo.setBounds(10, 126, 78, 14);
		panel.add(lblSocioActivo);
		
		txtSocioActivo = new JTextField();
		txtSocioActivo.setColumns(10);
		txtSocioActivo.setBounds(96, 123, 146, 20);
		panel.add(txtSocioActivo);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 202, 414, 180);
		contentPane.add(scrollPane);
		
		tablaSocios = new JTable();
		tablaSocios.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				filaSeleccionada=-1;
				//Toma el numero de fila seleccionada de la JTable:
				filaSeleccionada=tablaSocios.getSelectedRow();
				//Manda los datos de la fila hacia los TextFields:
				txtNombre.setText(tablaSocios.getValueAt(filaSeleccionada,0).toString());
				txtApellido.setText(tablaSocios.getValueAt(filaSeleccionada,1).toString());
				txtDNI.setText(tablaSocios.getValueAt(filaSeleccionada,2).toString());
				txtFecha.setText(tablaSocios.getValueAt(filaSeleccionada,3).toString());
				txtSocioActivo.setText(tablaSocios.getValueAt(filaSeleccionada,4).toString());
				txtNumSocio.setText(tablaSocios.getValueAt(filaSeleccionada,5).toString());
			}
		});
		//Se copia
		tablaSocios.setModel(new DefaultTableModel(
				//Se reemplaza por el array
			new Object[][] {
				{null, null, null, null, null, null},
				{null, null, null, null, null, null},
				{null, null, null, null, null, null},
				{null, null, null, null, null, null},
				{null, null, null, null, null, null},
			},
			new String[] {
				"Nombre", "Apellido", "DNI", "Inscripci\u00F3n", "Socio activo", "ID Socio"
			}
		));
		//Se copia hasta ahí
		scrollPane.setViewportView(tablaSocios);
		
		btnFinalizar = new JButton("FINALIZAR");
		btnFinalizar.setBounds(315, 393, 109, 30);
		contentPane.add(btnFinalizar);
		btnFinalizar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		btnFinalizar.setBackground(Color.RED);
		btnFinalizar.setForeground(Color.BLACK);
		
		btnCargarLista = new JButton("Cargar Lista");
		btnCargarLista.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					//1ro chequeo que el archivo existe:
					if(!archivo.exists()) {
						//Si no existe lo creo. No puedo hacer nada mas porque no contiene datos.
						archivo.createNewFile();
						JOptionPane.showMessageDialog(null, "Se creó el archivo: "+archivo.getName()+" en la ruta: "+archivo.getAbsolutePath());
					}else {
						//Si existe (Contiene datos) llamo al método para leer las líneas:
						metodoRLinea(txtBackUp); //ReadLine
						//Luego carga la tabla:
						muestroTabla();
					}
				}catch(Exception e2) {
					JOptionPane.showMessageDialog(null, e2.getMessage());
				}
			}
		});
		btnCargarLista.setBounds(10, 393, 109, 23);
		contentPane.add(btnCargarLista);
		
		btnGuardarLista = new JButton("GuardarLista");
		btnGuardarLista.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				metodoWLinea(txtBackUp);
				JOptionPane.showMessageDialog(null, "Archivo guardado en: "+archivo.getAbsolutePath()
				+"\n Ultima actualización Fecha: "+LocalDate.now().format(dForma)
				+" - Hora: "+LocalTime.now().getHour()+":"+LocalTime.now().getMinute());
			}
		});
		btnGuardarLista.setBounds(129, 393, 109, 23);
		contentPane.add(btnGuardarLista);
	}
	public static void CargarUsuarios() {
		miControl.agregarSocio("Rodrigo", "Sosa", 31017204L, LocalDate.of(1984,7,19),null);
		miControl.agregarSocio("Pedro", "Gómez", 30254984L, LocalDate.of(1990,9,29),null);
		miControl.agregarSocio("Pablo", "Luna", 42000325L, LocalDate.of(1911,6,9),null);
		miControl.agregarSocio("Gino", "Galliussi", 28654123L, LocalDate.of(1992,8,12),null);
	}
	
	public void limpioCampos() {
		txtNombre.setText("");
		txtApellido.setText("");
		txtDNI.setText("");
		txtFecha.setText("");
		txtSocioActivo.setText("");
		txtNumSocio.setText("");
	}
	
	private void metodoRLinea(String txtBackUp) {
		// TODO Auto-generated method stub
		try {
			FileReader entrada=new FileReader(txtBackUp); //Creo el vinculo a la dirección del archivo.
			BufferedReader bufferLectura=new BufferedReader(entrada); //Creo el buffer de lectura.
			//String salida="";
			String linea="";
			while(linea!=null) {
				linea=bufferLectura.readLine(); //Leo una linea.
				if(linea!=null) {
					String[]campos=linea.split(";"); //Utilizo Split() para dividir los campos.
					//Asigno los campos a las variables:
					//nombre + ";" + apellido + ";" + dni + ";" + nroSocio+ ";" + fechaIns.format(dForma) + ";" + socioActual;
					String nombre= campos[0];
					String apellido= campos[1];
					Long dni=Long.parseLong(campos[2]);
					Integer nroSocio=Integer.parseInt(campos[3]);
					LocalDate fechaInsc=LocalDate.parse(campos[4], dForma);
					boolean socioActual=Boolean.parseBoolean(campos[5]);
					
					//Creo el objeto y lo agrego al Map de Socios:
					//String nombre, String apellido, Long dni, LocalDate fechaIns, Integer numSocio
					Socio nuevoSocio=new Socio(nombre, apellido, dni, nroSocio, fechaInsc, socioActual);
					miControl.getMapSocios().put(nroSocio, nuevoSocio);
				}
			}
			bufferLectura.close();
			
		}catch(Exception e) {
			
		}
	
	}
	
	private void metodoWLinea(String txtBackUp) {
		// TODO Auto-generated method stub
		try {
			File archivo = new File(txtBackUp); 
			if (!archivo.exists()) {
				// Si el archivo no existe es creado
				archivo.createNewFile();
				JOptionPane.showMessageDialog(null, "Se creó el archivo: "+archivo.getName()+" en el directorio: "+archivo.getAbsolutePath());
			}
			FileWriter salida = new FileWriter(txtBackUp); //Creo una conexión con el archivo de salida.
			BufferedWriter bufferEscritura = new BufferedWriter(salida);// Conecta con el buffer de escritura.
			//Recorro la colección Map y copio los elementos. (El toString de Socios debe ser separado por ";")
			
			for (Iterator<Socio> it = miControl.mapSocios.values().iterator();it.hasNext(); ) {
				Socio s = (Socio) it.next();
				bufferEscritura.write(s.toString());//Escribe.
				bufferEscritura.newLine(); //Cambia de linea.
				bufferEscritura.flush(); //Vacía el buffer escribiendo cualquier salida almacenada en búfer en el buffer subyacente.
			}
			bufferEscritura.close(); //Cierra la conexón con el buffer.

		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	public static void muestroTabla() {
		// TODO Auto-generated method stub
		String[][] arraySocios=new String[miControl.mapSocios.size()][6];
		int columna=0;
		Socio socioTemp;
		
		for(Iterator it = miControl.mapSocios.entrySet().iterator();it.hasNext();) {
			Entry ee=(Entry) it.next();
			socioTemp=(Socio)ee.getValue();
			arraySocios[columna][0]=String.valueOf(socioTemp.getNombre());
			arraySocios[columna][1]=String.valueOf(socioTemp.getApellido());
			arraySocios[columna][2]=String.valueOf(socioTemp.getDni());
			arraySocios[columna][3]=String.valueOf(socioTemp.getFechaIns().format(dForma));
			arraySocios[columna][4]=String.valueOf(socioTemp.getSocioActual());
			arraySocios[columna][5]=String.valueOf(socioTemp.getNroSocio());
			columna++;
		}
		
		tablaSocios.setModel(new DefaultTableModel(
				//Se reemplaza por el array
			arraySocios, //ES UNA COMA! no un (;)
			new String[] {
				"Nombre", "Apellido", "DNI", "Inscripci\u00F3n", "Socio activo", "ID Socio"
			}
		));
		
	}
}
