package com.example.lequan.lichvannien.network;

import android.util.Log;
import java.io.IOException;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class XMLParser {
    public String getXmlFromUrl(String url) {
        String xml = null;
        try {
            xml = EntityUtils.toString(new DefaultHttpClient().execute(new HttpPost(url)).getEntity());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e2) {
            e2.printStackTrace();
        } catch (IOException e3) {
            e3.printStackTrace();
        }
        return xml;
    }

    public Document getDomElement(String xml) {
        try {
            DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            InputSource is = new InputSource();
            is.setCharacterStream(new StringReader(xml));
            return db.parse(is);
        } catch (ParserConfigurationException e) {
            Log.e("Error: ", e.getMessage());
            return null;
        } catch (SAXException e2) {
            Log.e("Error: ", e2.getMessage());
            return null;
        } catch (IOException e3) {
            Log.e("Error: ", e3.getMessage());
            return null;
        }
    }

    public final String getElementValue(Node elem) {
        if (elem != null && elem.hasChildNodes()) {
            for (Node child = elem.getFirstChild(); child != null; child = child.getNextSibling()) {
                if (child.getNodeType() == (short) 3) {
                    return child.getNodeValue();
                }
            }
        }
        return "";
    }

    public String getValue(Element item, String str) {
        return getElementValue(item.getElementsByTagName(str).item(0));
    }
}
