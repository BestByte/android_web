package com.cool.andoroidall.web.xml;

import android.util.Log;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class MyContentHandler extends DefaultHandler {
private String nodeName;
private StringBuilder id;
private StringBuilder name;
private StringBuilder version;
    @Override
    public void startDocument() throws SAXException {
        super.startDocument();
        id=new StringBuilder();
        name=new StringBuilder();
        version=new StringBuilder();
    }

    @Override
    public void endDocument() throws SAXException {
        super.endDocument();
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        super.startElement(uri, localName, qName, attributes);

        nodeName=localName;
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        super.endElement(uri, localName, qName);
        if("app".equals(localName)){
            Log.d("MyContentHandler", "endElement: id is"+id.toString().trim());
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        super.characters(ch, start, length);

        if("id".equals(nodeName)){
            id.append(ch,start,length);
        }
        else  if ("name".equals(nodeName)){
            name.append(ch,start,length);
        }else if("version".equals(nodeName)){
            name.append(ch,start,length);
        }
    }
}
