package org.TheGivingChild.Editor;

import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;

import org.TheGivingChild.Engine.*;

import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.utils.XmlWriter;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.XmlWriter;

public class XML_Writer {
	/* QUICK NOTES ON THE XML WRITER
	 * 1: Setup a StringWriter(called xml in this case)
	 * 2: Setup a XmlWriter and pass the constructor the StringWriter
	 * 3: xml_writer.element adds a new element
	 * 4: xml_writer.attribute("attribute name","attribute value") adds an attribute
	 * 5: xml_writer.pop() goes up a level in the tree
	 * EX: 	xml.element("ELEMENT_JUAN");
	 *		xml.attribute("ATTRIBUTE","VALUE");
	 *		xml.element("ELEMENT_TWO");
	 *		xml.attribute("ATTR","VAL");
	 *		xml.pop();
	 *		xml.pop();
	 *		
	 *		returns a STRING with the value of:
	 *		<ELEMENT_JUAN ATTRIBUTE="VALUE">
	 *			<ELEMENT_TWO ATTR="VAL"/>
	 *		</ELEMENT_JUAN>
	 *	6: then just write the string to the file with a FileWriter
	 */
	private String XML_String = "";
	private String filename = "";
	private String packageName = "";
	
	//main method for testing
	public static void main(String cheese[]){
		GameObject testObj1 = new GameObject(1,"testObj1FILENAME",new GridPoint2(1,1));
		testObj1.addValidAttribute("health", "100");
		GameObject testObj2 = new GameObject(2,"testObj2FILENAME",new GridPoint2(2,2));
		GameObject testObj3 = new GameObject(3,"testObj3FILENAME",new GridPoint2(3,3));
		testObj3.addValidAttribute("health", "9001");
		testObj3.addValidAttribute("health", "9002");
		testObj3.addValidAttribute("color","fuchesa");
		
		Array<GameObject> testObjectArray = new Array<GameObject>();
		testObjectArray.add(testObj1);
		testObjectArray.add(testObj2);
		testObjectArray.add(testObj3);
		
		XML_Writer sally = new XML_Writer();
		sally.writeToFile(testObjectArray,new Level("PLACEHOLDER","PLACEHOLDER","PLACEHOLDER",new LevelGoal(),new Array<GameObject>()));
	}
	
	public void setupNewFile(String newfilename,String packagename){
		filename = newfilename;
		packageName = packagename;
	}
	
	public void writeToFile(Array<GameObject> gameObjects, Level level){//sets up .xml file, calls the 2 compile methods, and adds their outputs together
		StringWriter stringWriter = new StringWriter();
		XmlWriter writer = new XmlWriter(stringWriter);
		try{//compile xml string
			writer.element("root");
			for(GameObject currentGameObject:gameObjects){
				System.out.println(currentGameObject);
			}
			writer.pop();
			
			//write to file
			FileWriter fileWriter = new FileWriter(filename);
			fileWriter.write(XML_String);
		}catch(Exception e){System.out.println("Error writing to file: " + e);}
	}
	
	private String compileGameObjectsToString(Array<GameObject> gameObjects){
		return "";
	}
	
	private String compileLevelToString(Level level){
		return "";
	}
	
	public void XML_test(){
		
	}
}
