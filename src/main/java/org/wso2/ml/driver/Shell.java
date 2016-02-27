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
package org.wso2.ml.driver;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.wso2.ml.client.MLClient;
import org.wso2.ml.constants.MLConstants;


import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

public class Shell {

    private static String configurationFile= "configuration.json";
    private static String resourcePath;
    private static MLClient mlClient = new MLClient();
    private static ArrayList<JSONObject> confArrayList = new ArrayList<JSONObject>();
    private static JSONArray jsonArray = new JSONArray();

    public static void main(String[] args) {
        resourcePath = getResourcePath();
        readConfigurationFile();
        try {
            System.out.println("========== Uploading Dataset ============");
            mlClient.createdDataSet(confArrayList.get(0));
            Thread.sleep(2000);

            System.out.println("========== Creating Project =============");
            mlClient.createProject(confArrayList.get(1));

            System.out.println("========== Getting projectID =============");
            CloseableHttpResponse response = mlClient.getProject(confArrayList.get(1));
            String bodyAsString = EntityUtils.toString(response.getEntity());
            Thread.sleep(2000);

            JSONParser parser = new JSONParser();
            JSONObject o = (JSONObject)parser.parse(bodyAsString);
            long projectId = (Long)o.get("id");


            System.out.println("========== Creating Analysis =============");
            mlClient.createAnalysis(confArrayList.get(2),projectId);

            System.out.println("========== Getting AnalysisID =============");
            response = mlClient.getAnalysis(confArrayList.get(2),projectId);
            bodyAsString = EntityUtils.toString(response.getEntity());
            Thread.sleep(2000);

            o = (JSONObject)parser.parse(bodyAsString);
            long analysisId = (Long)o.get("id");


            System.out.println("========== Setting Model Configs =============");
            mlClient.setModelConfigs(jsonArray, analysisId);

            System.out.println("========== Setting Default Hyper-Params =============");
            mlClient.setDefaultHyperParams(analysisId);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void readConfigurationFile() {
        JSONParser parser = new JSONParser();
        try {
            Object obj = parser.parse(new FileReader(
                    resourcePath+configurationFile));
            JSONObject jsonObject = (JSONObject) obj;

            JSONObject dataset = (JSONObject)jsonObject.get(MLConstants.DATASET_CONF);
            JSONObject project = (JSONObject)jsonObject.get(MLConstants.PROJECT_CONF);
            JSONObject analysis = (JSONObject)jsonObject.get(MLConstants.ANALYSIS_CONF);
            JSONObject model = (JSONObject)jsonObject.get(MLConstants.MODEL_CONF);
            JSONArray modelConfig = (JSONArray)jsonObject.get(MLConstants.MODELCONFIG_CONF);

            confArrayList.add(dataset);
            confArrayList.add(project);
            confArrayList.add(analysis);
            confArrayList.add(model);
            jsonArray = modelConfig;
            System.out.println(jsonArray);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getResourcePath(){
        String filePath = new File("").getAbsolutePath();
        return filePath + "/src//main/resources/";
    }
}