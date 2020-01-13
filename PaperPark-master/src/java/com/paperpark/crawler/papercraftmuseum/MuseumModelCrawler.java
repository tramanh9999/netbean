/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.paperpark.crawler.papercraftmuseum;

import com.paperpark.contants.ConfigConstants;
import com.paperpark.contants.URLConstants;
import com.paperpark.crawler.BaseCrawler;
import com.paperpark.crawler.BaseThread;
import com.paperpark.dao.model.ModelDAO;
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
public class MuseumModelCrawler extends BaseCrawler implements Runnable {

    private String pageUrl;
    private Category category;

    public MuseumModelCrawler(ServletContext context, String pageUrl, Category category) {
        super(context);
        this.pageUrl = pageUrl.replaceAll(" ", "%20");
        this.category = category;
    }

    @Override
    public void run() {
        BufferedReader reader;
        try {
            reader = getBufferedReaderForUrl(pageUrl);
            String document = getModelsDocument(reader);
            
            document = TextUtils.refineHtml(document);
            
            parseAndSaveModels(document);
        } catch (IOException | XMLStreamException | InterruptedException ex) {
            Logger.getLogger(MuseumModelCrawler.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
    }

    private String getModelsDocument(BufferedReader reader) throws IOException {
        String line;
        String document = "<models>";
        boolean isStart = false;
        while ((line = reader.readLine()) != null) {
            if (!isStart && line.contains("<tbody>")) {
                isStart = true;
            }
            if (isStart) {
                document += line.trim();
            }
            if (isStart && line.contains("</tbody>")) {
                break;
            }
        }
        document += "</models>";

        return document;
    }

    private void parseAndSaveModels(String document)
            throws UnsupportedEncodingException, XMLStreamException, InterruptedException {
        XMLEventReader eventReader = parseStringToXMLEventReader(document);
        XMLEvent event;
        while (eventReader.hasNext()) {
            event = (XMLEvent) eventReader.next();
            if (event.isStartElement()) {
                StartElement startElement = event.asStartElement();
                if (ElementChecker.isElementWith(startElement, "tr")) {
                    Model model = parseModel(eventReader);
                    if (model == null) {
                        continue;
                    }

                    ModelDAO.getInstance().saveModelWhileCrawling(getContext(), model);

                    if (ConfigConstants.DEBUG) {
                        System.out.println("DEBUG saved model " + model.getLink());
                    }
                    
                    synchronized (BaseThread.getInstance()) {
                        while (BaseThread.isSuspended()) {
                            BaseThread.getInstance().wait();
                        }
                    }
                }
            }
        }
    }

    private Model parseModel(XMLEventReader eventReader) {
        String link = getModelLink(eventReader);
        String imageSrc = null;
        if (link != null) {
            imageSrc = getImageSrc(eventReader);
        } else {
            return null;
        }

        String name = getName(eventReader);
        List<Tag> tags = getTags(eventReader);
        Integer numOfParts = getNumOfParts(eventReader);
        Integer numOfSheets = getNumOfSheets(eventReader);
        String format = null;
        Integer difficulty = 0;
        Boolean hasInstruction = null;

        Model model = new Model(0, name, numOfSheets, numOfParts, difficulty, format, 
                imageSrc, link, hasInstruction, tags, category);

        return model;
    }

    private String getModelLink(XMLEventReader eventReader) {
        XMLEvent event;
        while (eventReader.hasNext()) {
            event = (XMLEvent) eventReader.next();
            if (event.isStartElement()) {
                StartElement element = event.asStartElement();
                if (ElementChecker.isElementWith(element, "td", "class", "m_1")) {
                    eventReader.next();
                    event = (XMLEvent) eventReader.next();

                    
                    element = event.asStartElement();
                    String link = getHref(element);

                    return link;
                }
            }
        }
        return null;
    }

    private String getHref(StartElement element) {
        Attribute hrefAttr = element.getAttributeByName(new QName("href"));
        return hrefAttr == null ? null : hrefAttr.getValue();
    }

    private String getSrc(StartElement element) {
        Attribute srcAttr = element.getAttributeByName(new QName("src"));
        return srcAttr == null ? null : srcAttr.getValue();
    }

    private String getImageSrc(XMLEventReader eventReader) {
        XMLEvent event;
        while (eventReader.hasNext()) {
            event = (XMLEvent) eventReader.next();
            if (event.isStartElement()) {
                StartElement element = event.asStartElement();
                if (ElementChecker.isElementWith(element, "img")) {
                    String src = getSrc(element);
                    return URLConstants.PAPERCRAFTMUSEUM + src;
                }
            }
        }
        return null;
    }

    private String getName(XMLEventReader eventReader) {
        XMLEvent event;
        while (eventReader.hasNext()) {
            event = (XMLEvent) eventReader.next();
            if (event.isStartElement()) {
                StartElement element = event.asStartElement();
                if (ElementChecker.isElementWith(element, "td", "class", "m_2")) {
                    event = (XMLEvent) eventReader.next();
                    Characters nameChars = event.asCharacters();
                    return nameChars.getData();
                }
            }
        }
        return null;
    }

    private List<Tag> getTags(XMLEventReader eventReader) {
        List<Tag> tags = new ArrayList<>();
        XMLEvent event;

        while (eventReader.hasNext()) {
            event = (XMLEvent) eventReader.next();
            if (event.isStartElement()) {
                StartElement element = event.asStartElement();
                if (ElementChecker.isElementWith(element, "a")) {
                    event = (XMLEvent) eventReader.next();
                    Characters tagChars = event.asCharacters();
                    String tagName = tagChars.getData();

                    Tag tag = getTag(tagName);
                    tags.add(tag);
                }
            } else if (event.isEndElement()) {
                EndElement element = event.asEndElement();
                if (ElementChecker.isElementWith(element, "td")) {
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
    
    private Integer getNumOfParts(XMLEventReader eventReader) {
        XMLEvent event;
        while (eventReader.hasNext()) {
            event = (XMLEvent) eventReader.next();
            if (event.isStartElement()) {
                StartElement element = event.asStartElement();
                if (ElementChecker.isElementWith(element, "td", "class", "m_4")) {
                    event = (XMLEvent) eventReader.next();
                    Characters numChars = event.asCharacters();
                    
                    String numStr = numChars.getData();
                    Integer numOfParts = ParseUtils.extractNumber(numStr);
                    
                    return numOfParts;
                }
            }
        }
        return null;
    }
    
    private Integer getNumOfSheets(XMLEventReader eventReader) {
        XMLEvent event;
        while (eventReader.hasNext()) {
            event = (XMLEvent) eventReader.next();
            if (event.isStartElement()) {
                StartElement element = event.asStartElement();
                if (ElementChecker.isElementWith(element, "td", "class", "m_5")) {
                    event = (XMLEvent) eventReader.next();
                    Characters numChars = event.asCharacters();
                    
                    String numStr = numChars.getData();
                    Integer numOfSheets = ParseUtils.extractNumber(numStr);
                    
                    return numOfSheets;
                }
            }
        }
        return null;
    }
}
