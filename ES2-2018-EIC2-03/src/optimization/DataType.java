package optimization;

public enum DataType {
	//O utilizador deverá poder escolher o tipo das variáveis de decisão do problema, 
	//entre os tipos possíveis definidos no framework jMetal (por exemplo, inteiro, binário, decimal, etc.), 
	//e nos casos em que seja possível, o intervalo de valores aceitável 
	//para as variáveis de decisão (por exemplo, para variáveis do tipo real, [-5;5]);
	/*Comentário Pessoal: Que outros tipos de dados podemos ter?! O que é aceite pelo jMetal? Em espera*/
	INTEGER, DOUBLE, BINARY;
}
