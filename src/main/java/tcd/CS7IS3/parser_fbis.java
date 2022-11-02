package tcd.CS7IS3;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import javax.xml.parsers.DocumentBuilderFactory;

import java.io.InputStream;



import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.SequenceInputStream;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class parser_fbis{

public static void parse_(String parse_doc) throws Exception{
    List<InputStream> streams = Arrays.asList(
        new ByteArrayInputStream("<root>".getBytes()),
        new FileInputStream(parse_doc),
        new ByteArrayInputStream("</root>".getBytes())
);
InputStream is = new SequenceInputStream(Collections.enumeration(streams));
Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(is);
//Get all the DOCs from the dataset

// System.out.println("Root Element: "+doc.getDocumentElement().getNodeName());
NodeList dList = doc.getElementsByTagName("DOC");
// System.out.println("----------------------------");
for (int temp = 0; temp < dList.getLength(); temp++) {
    Node dNode = dList.item(temp);
    // System.out.println("\nCurrent Element :");
    // System.out.print(dNode.getNodeName());
    if(dNode.getNodeType() == Node.ELEMENT_NODE)
    {
        Element dElement = (Element) dNode;
        System.out.println(dElement.getElementsByTagName("DOCNO").item(0).getTextContent());
        System.out.println(dElement.getElementsByTagName("HT").item(0).getTextContent());
        // System.out.println(dElement.getElementsByTagName("HT").item(0).getTextContent());
        NodeList Header_List = dElement.getElementsByTagName("HEADER");
        for(int i=0;i<Header_List.getLength();i++)
        {
            Node hNode = Header_List.item(i);
            if(hNode.getNodeType() == Node.ELEMENT_NODE)
            {
                Element hElement = (Element) hNode;
                System.out.println(hElement.getElementsByTagName("DATE1").item(0).getTextContent());
                if (hElement.getElementsByTagName("H2").getLength()>0)
                {
                    System.out.println(hElement.getElementsByTagName("H2").item(0).getTextContent());
                }
                System.out.println(hElement.getElementsByTagName("H3").item(0).getTextContent());
            }
        }
    }
}

}
public static void main(String args[])
{
    try{
/*This kind of just works right now for this file. 
Although there is no hard coding done as such but there is some issue with
our use cases
*/
        parse_("Data/fbis/fb396001");
    }
    catch (Exception e){
            System.out.println(e);
    }
}
}