package Lex;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Scanner;

public class Lex {
		
	String letras = "ABCDEFGHIJKLMNÑOPQRSTUVWXYZabcdefghijklmnñopqrstuvwxyz";
	String digitos = "0123456789";
	String caracteres = "+-=;[],:(){}";
	String lenguaje = "LDC/%=<>!&|\". \t\n";
	String codigo;
	int NumeroDeLinea = 1;
	LinkedList<String[]> TablaDeTokens = new LinkedList();
	String matriz [][] = {
			{"1", "9", "Y", "12", "-25", "2", "3", "4", "5", "6", "7", "8", "10", "ERROR", "ERROR", "0", "0", "0", "0"},
			{"1", "1", "X", "X", "-62", "X", "X", "X", "X", "-63", "X", "X", "X", "-64", "-61", "X", "X", "X", "X"},
			{"-26D", "-26D", "-26D", "-26D", "-26D", "-35", "-26D", "-26D", "-26D", "-26D", "-26D", "-26D", "-26D", "-26D", "-26D", "-26D", "-26D", "-26D", "-26D"},
			{"-31D", "-31D", "-31D", "-31D", "-31D", "-32", "-31D", "-31D", "-31D", "-31D", "-31D", "-31D", "-31D", "-31D", "-31D", "-31D", "-31D", "-31D", "-31D"},
			{"-33D", "-33D", "-33D", "-33D", "-33D", "-34", "-33D", "-33D", "-33D", "-33D", "-33D", "-33D", "-33D", "-33D", "-33D", "-33D", "-33D", "-33D", "-33D"},
			{"-41D", "-41D", "-41D", "-41D", "-41D", "-36", "-41D", "-41D", "-41D", "-41D", "-41D", "-41D", "-41D", "-41D", "-41D", "-41D", "-41D", "-41D", "-41D"},
			{"ERROR", "ERROR", "ERROR", "ERROR", "ERROR", "ERROR", "ERROR", "ERROR", "ERROR", "-42", "ERROR", "ERROR", "ERROR", "ERROR", "ERROR", "ERROR", "ERROR", "ERROR", "ERROR"},
			{"ERROR", "ERROR", "ERROR", "ERROR", "ERROR", "ERROR", "ERROR", "ERROR", "ERROR", "ERROR", "-43", "ERROR", "ERROR", "ERROR", "ERROR", "ERROR", "ERROR", "ERROR", "ERROR"},
			{"8", "8", "8", "8", "8", "8", "8", "8", "8", "8", "8", "-71", "8", "8", "8", "8", "8", "ERROR", "ERROR"},
			{"ERROR", "9", "-73D", "-73D", "-73D", "-73D", "-73D", "-73D", "-73D", "-73D", "-73D", "-73D", "10", "-73D", "-73D", "-73D", "-73D", "-73D", "-73D"},
			{"ERROR", "11", "ERROR", "ERROR", "ERROR", "ERROR", "ERROR", "ERROR", "ERROR", "ERROR", "ERROR", "ERROR", "ERROR", "ERROR", "ERROR", "ERROR", "ERROR", "ERROR", "ERROR"},
			{"ERROR", "11", "-72D", "-72D", "-72D", "-72D", "-72D", "-72D", "-72D", "-72D", "-72D", "-72D", "ERROR", "-72D", "-72D", "-72D", "-72D", "-72D", "-72D"},
			{"-24", "-24", "-24", "13", "-24", "-24", "-24", "-24", "-24", "-24", "-24", "-24", "-24", "-24", "-24", "-24", "-24", "-24", "-24"},
			{"13", "13", "13", "13", "13", "13", "13", "13", "13", "13", "13", "13", "13", "13", "13", "13", "13", "-74D", "-74D"}
	};
	String x [][] = {{"programa", "-1"}, {"inicio", "-2"}, {"fin", "-3"}, {"leer", "-4"}, {"escribir", "-5"}, {"si", "-6"}, {"sino", "-7"}, {"mientras", "-8"}, {"repetir", "-9"}, {"hasta", "-10"},{"limpiar", "-11"}, {"ejecutar", "-12"}, {"posxy", "-13"}, {"proc", "-14"}, {"var", "-15"}, {"encaso", "-16"}, {"valor", "-17"}};
	String y [][] = {{"+", "-21"}, {"-", "-22"}, {"*", "-23"}, {";", "-51"}, {"[", "-52"}, {"]", "-53"},{",", "-54"},{":", "-55"}, {"(", "-56"}, {")", "-57"}, {"{", "-58"}, {"}", "-59"}};

	public void Lexemas(String Lexema, int posicion, int estado, int Linea) {
		
		NumeroDeLinea = Linea;
		int columna = -1;
		if(estado == 0) {
			Lexema = "";
		}
		if(codigo.length()<= posicion) {
			return;
		}
		if(codigo.charAt(posicion) == '\n') {
			Linea++;
		}
		if(letras.contains(codigo.charAt(posicion)+"")) {
			columna = 0;
		}
		
		else if(lenguaje.contains(codigo.charAt(posicion)+"")) {
			columna = lenguaje.indexOf(codigo.charAt(posicion));
		}
		else if(digitos.contains(codigo.charAt(posicion)+"")) {
			columna = 1;
	}
		else if(caracteres.contains(codigo.charAt(posicion)+"")) {
			columna = 2;
		}
		try {
			int NuevoEstado = Integer.parseInt(matriz[estado][columna]);
			if (NuevoEstado>=0) {
				
				this.Lexemas(Lexema+codigo.charAt(posicion), posicion+1, NuevoEstado, Linea);
			}
			
			else {
				if((int)(NuevoEstado/10) == -6) {
					TablaDeTokens.add(new String[] {Lexema+codigo.charAt(posicion), NuevoEstado+"" , "-2" , "" + NumeroDeLinea });
				}
				else {
					TablaDeTokens.add(new String[] {Lexema+codigo.charAt(posicion), NuevoEstado+"" , "-1" , "" + NumeroDeLinea });
				}
				
				this.Lexemas("", posicion+1, 0, Linea);
			
		}}
		catch(Exception exs ) {
			try {
				int NuevoEstado = Integer.parseInt(matriz[estado][columna].substring(0, 3));
				if(NuevoEstado == -73) {
					System.out.println(Lexema);
					if(Double.parseDouble(Lexema)>32767 || Double.parseDouble(Lexema)< -32768) {
						NuevoEstado = -72;
					}
				}
				TablaDeTokens.add(new String[] {Lexema, NuevoEstado+"" , "-1" , "" + NumeroDeLinea });
				this.Lexemas("", posicion, 0, Linea);
			}
			catch(Exception exs2) {
				String exe = matriz[estado][columna];
				if(exe.compareTo("X") == 0) {
					TablaDeTokens.add(new String[] {Lexema, ""+BuscarX(Lexema) , "-1" , "" + NumeroDeLinea });
					this.Lexemas("", posicion, 0, Linea);
				}
				else if(exe.compareTo("Y") == 0) {
					TablaDeTokens.add(new String[] {Lexema+codigo.charAt(posicion), ""+BuscarY(Lexema+codigo.charAt(posicion)) , "-1" , "" + NumeroDeLinea });
					this.Lexemas("", posicion+1, 0, Linea);
				}
				else {
					TablaDeTokens.add(new String[] {Lexema+codigo.charAt(posicion), exe, "-1" , "" + NumeroDeLinea });
					this.Lexemas("", posicion+1, 0, Linea);
				}
				
			}
			
		}
		
		}
	public String BuscarX(String Lexema) {
		for(String[] Fila: x) {
			if(Fila[0].compareTo(Lexema)==0){
				return Fila[1];
			}
		}
		return "ERROR";
	}
	public String BuscarY(String Lexema) {
		for(String[] Fila: y) {
			if(Fila[0].compareTo(Lexema)==0){
				return Fila[1];
			}
		}
		return "ERROR";
	}
	
	public void Escribir() throws IOException {
		File xd = new File("Tabla de tokens.txt");
		if(!xd.exists()) {
			xd.createNewFile();
		}
		FileWriter fw = new FileWriter(xd);
		BufferedWriter bw = new BufferedWriter(fw);
		bw.write("Lexema \t|Token\t|Pertenece\t|#Linea\n");
		for(String[] linea:TablaDeTokens) {
			for(String celda: linea) {
				bw.write(celda+"\t|");
			}
			bw.write("\n");
		}
		bw.close();
	}
	public void Leer(String nombre) throws FileNotFoundException {
		File xd = new File(nombre);
		if(xd.exists()) {
			Scanner s = new Scanner(xd);
			codigo = "";
			
			while(s.hasNext()) {
				codigo += s.nextLine() +  "\n";
			}
		}
	}
	public static void main (String[] args) throws IOException {
	
		Lex L = new Lex();
		L.Leer("texto.txt");
		System.out.print(L.codigo);
		L.Lexemas("", 0, 0, 1);
		L.Escribir();
	}
	
	
	
}

	
