package xml;

import java.io.File;
import java.io.FileOutputStream;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
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

import objects.VariableTable;

import org.w3c.dom.Node;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;

public class XMLParser {
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

			/*Example of getting the info from the Administrador (so you can send him an e-mail)*/
			//			XPathExpression expr = xpath.compile("/XML/Administrator/@*");
			//			NodeList nl = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
			//			for (int i = 0; i < nl.getLength(); i++) {
			//				System.out.print(nl.item(i).getNodeName()  + ":");	
			//				System.out.println(nl.item(i).getFirstChild().getNodeValue()  + " ");
			//			}

			/*Getting the jMetal path*/
			XPathExpression expr = xpath.compile("/XML/Paths/jMetalPath");
			String str = (String) expr.evaluate(doc, XPathConstants.STRING);
			System.out.println("jMetalPath:" + str);




		} catch (Exception e) {
		}

		return currentXML;
	}

	public void saveConfiguration() {
		try {
			currentXML = new VariableTable("New Variable Group Configuration");
			File inputFile = new File("config.xml");
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(inputFile);
			doc.getDocumentElement().normalize();   
			XPathFactory xpathFactory = XPathFactory.newInstance();
			XPath xpath = xpathFactory.newXPath();

			/*Inserting new Elements*/
			Element newElement1 = doc.createElement("Administrator");
			newElement1.setAttribute("Name", "Joaquim");
			newElement1.setAttribute("email", "joaquim@iscte-iul.pt");

			// Adding new element NewData to the XML document (root node)
			System.out.println("----- Adding new element <NewData> to the XML document -----");
			Element newElement2 = doc.createElement("NewData");
			newElement2.setTextContent("More new data"); 

			// Add new nodes to XML document (root element)
			System.out.println("Root element :" + doc.getDocumentElement().getNodeName());         
			Node n = doc.getDocumentElement();
			n.appendChild(newElement1);
			n.appendChild(newElement2);   

			// Save XML document
			System.out.println("\nSave XML document.");
			Transformer transformer = TransformerFactory.newInstance().newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			StreamResult result = new StreamResult(new FileOutputStream("config.xml"));
			DOMSource source = new DOMSource(doc);
			transformer.transform(source, result);
		}catch(Exception e) {}
	}

	public static void main(String[] args) {
		XMLParser xml = new XMLParser();
		xml.openConfiguration("config.xml");
		xml.saveConfiguration();
	}

}
