package io.github.wzk.config;

import io.github.wzk.utils.xmlProcessor;
import lombok.Getter;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

@Component
public class Config {

    @Getter
    private int nettyServerPort;

    public Config(){
        try {
            Document doc = new xmlProcessor().getDoc();
            nettyServerPort = Integer.parseInt(doc.getElementsByTagName("nettyServerPort").item(0).getTextContent());
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (SAXException e) {
            throw new RuntimeException(e);
        } catch (ParserConfigurationException e) {
            throw new RuntimeException(e);
        }

    }
}
