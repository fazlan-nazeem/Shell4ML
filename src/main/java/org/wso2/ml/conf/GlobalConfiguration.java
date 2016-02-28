/*
* Copyright (c) 2015, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
* http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/
package org.wso2.ml.conf;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.wso2.ml.constants.GlobalConstants;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

public class GlobalConfiguration {

    private String mlConfPath;
    private String datasetPath;
    private String mlHost;

    private static GlobalConfiguration singletonGlobalConfiguration = new GlobalConfiguration();

    private GlobalConfiguration() {
        readGlobalConfigurationFile();
    }

    public static GlobalConfiguration getInstance() {
        return singletonGlobalConfiguration;
    }

    public void readGlobalConfigurationFile() {
        File fXmlFile = new File(GlobalConstants.GLOBAL_CONFIG_PATH);
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = null;
        try {
            dBuilder = dbFactory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
        Document doc = null;
        try {
            doc = dBuilder.parse(fXmlFile);
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        doc.getDocumentElement().normalize();
        NodeList nList = doc.getElementsByTagName(GlobalConstants.ROOT_TAG);
        for (int temp = 0; temp < nList.getLength(); temp++) {
            Node nNode = nList.item(temp);
            if (nNode.getNodeType() == Node.ELEMENT_NODE) {

                Element eElement = (Element) nNode;
                this.mlConfPath = eElement.getElementsByTagName(GlobalConstants.ML_CONFIG_PATH_TAG).item(0)
                        .getTextContent();
                this.datasetPath = eElement.getElementsByTagName(GlobalConstants.DATASET_PATH_TAG).item(0)
                        .getTextContent();
                this.mlHost = eElement.getElementsByTagName(GlobalConstants.ML_HOST_TAG).item(0).getTextContent();
            }
        }
    }

    public String getDatasetPath() {
        return datasetPath;
    }

    public void setDatasetPath(String datasetPath) {
        this.datasetPath = datasetPath;
    }

    public String getMlConfPath() {
        return mlConfPath;
    }

    public void setMlConfPath(String mlConfPath) {
        this.mlConfPath = mlConfPath;
    }

    public String getMlHost() {
        return mlHost;
    }

    public void setMlHost(String mlHost) {
        this.mlHost = mlHost;
    }
}
