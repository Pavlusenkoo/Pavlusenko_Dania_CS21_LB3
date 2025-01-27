package task2;

import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

public class SimpleErrorHandler implements ErrorHandler {

    public void warning(SAXParseException e)  {
        System.out.println(e.getMessage());
    }

    public void error(SAXParseException e)   {
        System.out.println(e.getMessage());
    }

    public void fatalError(SAXParseException e)   {
        System.out.println(e.getMessage());
    }
}

