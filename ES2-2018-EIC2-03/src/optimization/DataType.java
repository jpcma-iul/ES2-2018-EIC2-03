package optimization;

public enum DataType {
	//O utilizador dever� poder escolher o tipo das vari�veis de decis�o do problema, 
	//entre os tipos poss�veis definidos no framework jMetal (por exemplo, inteiro, bin�rio, decimal, etc.), 
	//e nos casos em que seja poss�vel, o intervalo de valores aceit�vel 
	//para as vari�veis de decis�o (por exemplo, para vari�veis do tipo real, [-5;5]);
	/*Coment�rio Pessoal: Que outros tipos de dados podemos ter?! O que � aceite pelo jMetal? Em espera*/
	INTEGER, DOUBLE, BINARY;
}
