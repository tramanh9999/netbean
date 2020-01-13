/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.paperpark.crawler.kit168;

import com.paperpark.contants.ConfigConstants;
import com.paperpark.crawler.BaseCrawler;
import com.paperpark.dao.tag.TagDAO;
import com.paperpark.entity.Category;
import com.paperpark.entity.Model;
import com.paperpark.entity.Tag;
import com.paperpark.utils.ElementChecker;
import com.paperpark.utils.ParseUtils;
import com.paperpark.utils.TextUtils;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

/**
 *
 * @author NhanTT
 */
public class Kit168ModelCrawler extends BaseCrawler {

    private String pageUrl;
    private Category category;

    public Kit168ModelCrawler(ServletContext context, String pageUrl, Category category) {
        super(context);
        this.pageUrl = pageUrl;
        this.category = category;
    }

    public Model getModel() {
        BufferedReader reader = null;
        Model model = null;
        try {
            reader = getBufferedReaderForUrl(pageUrl);
            String document = getModelDocument(reader);
            return stAXParserForModel(document);
        } catch (IOException | XMLStreamException ex) {
            Logger.getLogger(Kit168ModelCrawler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return model;
    }

    private Model stAXParserForModel(String document) 
            throws XMLStreamException, UnsupportedEncodingException {
        
        document = TextUtils.refineHtml(document);
        
        if (ConfigConstants.DEBUG && ConfigConstants.DEBUG_PRINT_DOC) {
            System.out.println("DEBUG: model document: " + document);
        }
        
        XMLEventReader eventReader = parseStringToXMLEventReader(document);
        
        String name = getModelName(eventReader);
        if (name == null) {
            return null;
        }
        
        String imageSrc = getModelImageSource(eventReader);
        Integer numOfSheets = getNumOfSheets(eventReader);
        Integer numOfParts = null;
        Boolean hasInstruction = hasInstruction(eventReader);
        String format = getFormat(eventReader);
        String link = pageUrl;
        List<Tag> tags = getTags(eventReader);
        Integer difficulty = getDifficulty(eventReader);
        
        Model model = new Model(0, name, numOfSheets, numOfParts, difficulty, format, 
                imageSrc, link, hasInstruction, tags, category);
        
        return model;
    }

    private String getModelDocument(BufferedReader reader) throws IOException {
        String document = "<modelDocument>";
        String line = "";
        boolean isStart = false;

        while ((line = reader.readLine()) != null) {
            if (!isStart && line.startsWith("<article")) {
                isStart = true;
            }
            
            if (isStart) {
                document += line + " ";
            }
            if (isStart && line.contains("</article>")) {
                break;
            }
        }

        isStart = false;
        while ((line = reader.readLine()) != null) {
            if (isStart && line.contains("<div class=\"review-short-summary\">")) {
                break;
            }
            if (!isStart && line.contains("<div class=\"review-final-score\">")) {
                isStart = true;
            }
            if (isStart) {
                document += line.trim();
            }
        }
        document += "</modelDocument>";

        return document;
    }
    
    private String getModelName(XMLEventReader eventReader) throws XMLStreamException {
        String name = null;
        XMLEvent event;
        while (eventReader.hasNext()) {
            event = (XMLEvent) eventReader.next();
            if (event.isStartElement()) {
                StartElement startElement = event.asStartElement();
                if (ElementChecker.isElementWith(startElement, "h1", "class", "heading1")) {
                    event = (XMLEvent) eventReader.next();
                    Characters nameChars = event.asCharacters();
                    
                    name = nameChars.getData();
                    return name;
                }
            }
        }
        
        return name;
    }
    
    private String getModelImageSource(XMLEventReader eventReader) {
        XMLEvent event;
        String src = null;
        while (eventReader.hasNext()) {
            event = (XMLEvent) eventReader.next();
            if (event.isStartElement()) {
                StartElement startElement = event.asStartElement();
                if (ElementChecker.isElementWith(startElement, "img", "class", 
                        "attachment-blog-post-thumb-inside wp-post-image")) {
                    Attribute srcAttr = startElement.getAttributeByName(new QName("src"));
                    src = srcAttr.getValue();
                    return src;
                }
            }
        }
        return src;
    }
    
    private Integer getNumOfSheets(XMLEventReader eventReader) {
        int numOfSheet = 0;
        XMLEvent event;
        while (eventReader.hasNext()) {
            event = (XMLEvent) eventReader.next();
            if (event.isStartElement()) {
                StartElement startElement = event.asStartElement();
                if (ElementChecker.isElementWith(startElement, "p")) {
                    event = (XMLEvent) eventReader.next();
                    if (event.isEndElement()) {
                        continue; // skip <p></p>
                    }
                    Characters chars = event.asCharacters();
                    String text = chars.getData();
                    
                    if (text.contains("Số tờ kit")) {
                        numOfSheet = ParseUtils.extractNumber(text);
                        return numOfSheet;
                    }
                }
            }
        }
        return numOfSheet;
    }
    
    private Boolean hasInstruction(XMLEventReader eventReader) {
        XMLEvent event;
        while (eventReader.hasNext()) {
            event = (XMLEvent) eventReader.next();
            if (event.isStartElement()) {
                StartElement startElement = event.asStartElement();
                if (ElementChecker.isElementWith(startElement, "p")) {
                    event = (XMLEvent) eventReader.next();
                    Characters chars = event.asCharacters();
                    String text = chars.getData();
                    
                    if (text.contains("Hướng dẫn")) {
                        return !text.toLowerCase().contains("không");
                    }
                }
            }
        }
        return false;
    }
    
    private String getFormat(XMLEventReader eventReader) {
        XMLEvent event;
        String format = null;
        while (eventReader.hasNext()) {
            event = (XMLEvent) eventReader.next();
            if (event.isStartElement()) {
                StartElement element = event.asStartElement();
                if (ElementChecker.isElementWith(element, "p")) {
                    event = (XMLEvent) eventReader.next();
                    Characters chars = event.asCharacters();
                    String text = chars.getData();
                    
                    if (text.contains("Định dạng file")) {
                        format = text.split("[:.]")[1].trim();
                        return format;
                    }
                }
            }
        }
        return format;
    }
    
    private List<Tag> getTags(XMLEventReader eventReader) {
        List<Tag> tags = new ArrayList<>();
        XMLEvent event;
        while (eventReader.hasNext()) {
            event = (XMLEvent) eventReader.next();
            if (event.isStartElement()) {
                StartElement element = event.asStartElement();
                if (ElementChecker.isElementWith(element, "a", "rel", "tag")) {
                    event = (XMLEvent) eventReader.next();
                    Characters chars = event.asCharacters();
                    
                    String tagName = chars.getData();
                    Tag tag = getTag(tagName);
                    tags.add(tag);
                }
            } else if (event.isEndElement()) {
                EndElement element = event.asEndElement();
                if (ElementChecker.isElementWith(element, "article")) {
                    break;
                }
            }
        }
        return tags;
    }
    
    private Tag getTag(String tagName) {
        TagDAO tagDAO = TagDAO.getInstance();
        return tagDAO.getAndInsertIfNewTag(tagName);
    }
    
    private Integer getDifficulty(XMLEventReader eventReader) {
        XMLEvent event;
        while (eventReader.hasNext()) {
            event = (XMLEvent) eventReader.next();
            if (event.isStartElement()) {
                StartElement element = event.asStartElement();
                if (ElementChecker.isElementWith(element, "span", "class", "post-large-rate stars-large")) {
                    event = (XMLEvent) eventReader.next();
                    element = event.asStartElement();
                    
                    Attribute styleAttr = element.getAttributeByName(new QName("style"));
                    String style = styleAttr.getValue();
                    Integer difficultPercent = ParseUtils.extractNumber(style);
                    
                    return difficultPercent / 10;
                }
            }
        }
        return 0;
    }
}
