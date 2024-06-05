package io.github.wzk.utils;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class xmlProcessor {

    private Document doc;
    public xmlProcessor() throws IOException, SAXException, ParserConfigurationException {
        String projectDir = System.getProperty("user.dir");
        Path projectPath = Paths.get(projectDir);
        Path parentPath = projectPath.getParent();
        String parentDir = parentPath.toString() + File.separator + "conf" + File.separator  + "settings.xml";
        File xmlFile = new File(parentDir);
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        doc = dBuilder.parse(xmlFile);
        doc.getDocumentElement().normalize();
    }

    public Document getDoc() {
        return doc;
    }

}
