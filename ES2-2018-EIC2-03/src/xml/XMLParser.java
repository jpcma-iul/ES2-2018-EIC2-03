package xml;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.net.PasswordAuthentication;
import java.util.Calendar;
import java.util.Date;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;
import javax.xml.parsers.DocumentBuilder;

import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.UserDataHandler;
import org.xml.sax.SAXException;

import objects.DataType;
import objects.Variable;
import objects.VariableTable;

import org.w3c.dom.Node;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;

public class XMLParser {
	private String pathToJMetal;
	private String pathToJars;
	private String emailAdministrator;
	private String passwordAdministrator;
	private String nameOfLastProblemSaved;
	

	VariableTable currentXML;
	public VariableTable openConfiguration(String pathToConfiguration){
		try {
			/*Init*/
			currentXML = new VariableTable("New Variable Group Configuration");
			File inputFile = new File(pathToConfiguration);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(inputFile);
			doc.getDocumentElement().normalize();   
			XPathFactory xpathFactory = XPathFactory.newInstance();
			XPath xpath = xpathFactory.newXPath();
			/* Get administrator e-mail */
			XPathExpression expr = xpath.compile("/XML/email-do-administrador");
			emailAdministrator = (String) expr.evaluate(doc, XPathConstants.STRING);
			/*Getting the jMetal path*/
			expr = xpath.compile("/XML/Paths/jMetalPath");
			pathToJMetal = (String) expr.evaluate(doc, XPathConstants.STRING);
			expr = xpath.compile("/XML/Paths/jarsPath");
			pathToJars = (String) expr.evaluate(doc, XPathConstants.STRING);
			System.out.println("jMetalPath:" + pathToJMetal+" jarsPath:"+pathToJars);
		} catch (Exception e) {
		}

		return currentXML;
	}

	public VariableTable openProblem(String pathToProblem){
		try {
			/*Init*/
			String[] split1 = pathToProblem.split("[^A-Za-z0-9- ]");
			String split2 = split1[split1.length-2];
			String getName = split2.split(" ")[0];
			currentXML = new VariableTable(getName);
			File inputFile = new File(pathToProblem);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(inputFile);
			doc.getDocumentElement().normalize();   
			XPathFactory xpathFactory = XPathFactory.newInstance();
			XPath xpath = xpathFactory.newXPath();
			/*Gets the data from every single field <Variable /> of the XML*/
			XPathExpression expr = xpath.compile("/XML/Variables/Variable/@*");
			NodeList nl = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
			for (int i = 0; i < nl.getLength(); i+=5) {
				if(nl.item(i).getFirstChild().getNodeValue().equals("Binary")) {
					Variable v = new Variable(nl.item(i+3).getFirstChild().getNodeValue(),DataType.BINARY);
					v.setHighestLimit(Integer.valueOf(nl.item(i+1).getFirstChild().getNodeValue()));
					v.setLowestLimit(Integer.valueOf(nl.item(i+2).getFirstChild().getNodeValue()));
					v.setBinaryValue(Integer.valueOf(nl.item(i+4).getFirstChild().getNodeValue()));
					currentXML.addVariable(v);
				}else if(nl.item(i).getFirstChild().getNodeValue().equals("Integer")) {
					Variable v = new Variable(nl.item(i+3).getFirstChild().getNodeValue(),DataType.INTEGER);
					v.setHighestLimit(Integer.valueOf(nl.item(i+1).getFirstChild().getNodeValue()));
					v.setLowestLimit(Integer.valueOf(nl.item(i+2).getFirstChild().getNodeValue()));
					v.setValue(Integer.valueOf(nl.item(i+4).getFirstChild().getNodeValue()));
					currentXML.addVariable(v);
				}else {
					Variable v = new Variable(nl.item(i+3).getFirstChild().getNodeValue(),DataType.REAL);
					v.setHighestRealLimit(Double.valueOf(nl.item(i+1).getFirstChild().getNodeValue()));
					v.setLowestRealLimit(Double.valueOf(nl.item(i+2).getFirstChild().getNodeValue()));
					v.setValue(Double.valueOf(nl.item(i+4).getFirstChild().getNodeValue()));
					currentXML.addVariable(v);
				}
			}
			System.out.println("Name of Loaded File: "+split2);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return currentXML;
	}

	public void saveProblem(String problemName, VariableTable vt) {
		currentXML = vt;
		Date day = new Date();
		String dateToString = ""+(day.getYear()+1900)+"-"+(day.getMonth()+1)+"-"+day.getDate()+" "+day.getHours()+"-"+day.getMinutes()+"-"+day.getSeconds();
		try {
			/*Create new File with desired name and fill it with <XML> tags*/
			File inputFile = new File("../Problems/"+problemName+" "+dateToString+".xml");
			inputFile.createNewFile();
			FileWriter fw = new FileWriter(inputFile);
			fw.write("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>");
			fw.write(System.getProperty("line.separator"));
			fw.write("<XML>");
			fw.write(System.getProperty("line.separator"));
			fw.write("</XML>");
			fw.close();

			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(inputFile);
			doc.getDocumentElement().normalize();   
			XPathFactory xpathFactory = XPathFactory.newInstance();
			XPath xpath = xpathFactory.newXPath();

			/*Inserting new Variables in a new Node <Variables>*/
			Element variablesNode = doc.createElement("Variables");
			Node xmlnode = doc.getDocumentElement(); //The <XML> node.
			xmlnode.appendChild(variablesNode);
			Node varnode = doc.getDocumentElement().getChildNodes().item(1);// The child node <Variables>
			for(int i=0;i<currentXML.getVariables().size();i++) {
				Element newVariable = doc.createElement("Variable");
				newVariable.setAttribute("Name", currentXML.getVariables().get(i).getVarName());
				if(currentXML.getVariables().get(i).getType().equals(DataType.BINARY)) {
					newVariable.setAttribute("DataType", "Binary");
					newVariable.setAttribute("Value", String.valueOf(currentXML.getVariables().get(i).getBinaryValue()));
					newVariable.setAttribute("HigherLimit", "1");
					newVariable.setAttribute("LowerLimit", "0");
				}else if(currentXML.getVariables().get(i).getType().equals(DataType.INTEGER)) {
					newVariable.setAttribute("DataType", "Integer");
					newVariable.setAttribute("Value", String.valueOf(currentXML.getVariables().get(i).getIntegerValue()));
					newVariable.setAttribute("HigherLimit", String.valueOf(currentXML.getVariables().get(i).getHighestLimit()));
					newVariable.setAttribute("LowerLimit", String.valueOf(currentXML.getVariables().get(i).getLowestLimit()));
				}else{
					newVariable.setAttribute("DataType", "Real");
					newVariable.setAttribute("Value", String.valueOf(currentXML.getVariables().get(i).getRealValue()));
					newVariable.setAttribute("HigherLimit", String.valueOf(currentXML.getVariables().get(i).getHighestRealLimit()));
					newVariable.setAttribute("LowerLimit", String.valueOf(currentXML.getVariables().get(i).getLowestRealLimit()));
				}
				varnode.appendChild(newVariable);
			}
			// Save XML document
			Transformer transformer = TransformerFactory.newInstance().newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			StreamResult result = new StreamResult(new FileOutputStream(inputFile.getCanonicalPath()));
			DOMSource source = new DOMSource(doc);
			transformer.transform(source, result);
			System.out.println("Name of Saved File: "+problemName+" "+dateToString);
			nameOfLastProblemSaved = problemName+" "+dateToString;
		}catch(Exception e) {e.printStackTrace();}
	}

	public String getEmailAdministrator() {
		return emailAdministrator;
	}
	
	public String getPathToJars() {
		return pathToJars;
	}
	
	public String getPathToJMetal() {
		return pathToJMetal;
	}
	
	public VariableTable getCurrentXML() {
		return currentXML;
	}
	
	public String getNameOfLastProblemSaved() {
		return nameOfLastProblemSaved;
	}

}
