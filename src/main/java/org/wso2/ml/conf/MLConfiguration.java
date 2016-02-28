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

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.wso2.ml.client.MLClient;
import org.wso2.ml.constants.MLConstants;

import java.io.FileReader;
import java.util.HashMap;

public class MLConfiguration {

    private HashMap<String, JSONObject> confMapObjectValued = new HashMap<String, JSONObject>();
    private HashMap<String, JSONArray> confMapArrayValued = new HashMap<String, JSONArray>();
    private static GlobalConfiguration globalConf = GlobalConfiguration.getInstance();
    private MLClient mlClient = new MLClient();

    public void readMLConfigurationFile() {
        JSONParser parser = new JSONParser();

        try {
            Object obj = parser.parse(new FileReader(globalConf.getMlConfPath()));
            JSONObject jsonObject = (JSONObject) obj;

            JSONObject dataset = (JSONObject) jsonObject.get(MLConstants.DATASET_CONF);
            JSONObject project = (JSONObject) jsonObject.get(MLConstants.PROJECT_CONF);
            JSONObject analysis = (JSONObject) jsonObject.get(MLConstants.ANALYSIS_CONF);
            JSONObject model = (JSONObject) jsonObject.get(MLConstants.MODEL_CONF);
            JSONArray modelConfig = (JSONArray) jsonObject.get(MLConstants.MODELCONFIG_CONF);

            confMapObjectValued.put(MLConstants.KEY_DATASET, dataset);
            confMapObjectValued.put(MLConstants.KEY_PROJECT, project);
            confMapObjectValued.put(MLConstants.KEY_ANALYSIS, analysis);
            confMapObjectValued.put(MLConstants.KEY_MODEL, model);

            confMapArrayValued.put(MLConstants.KEY_MODELCONFIG, modelConfig);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void runConfiguration() {
        try {
            System.out.println("========== Uploading Dataset ============");
            mlClient.createdDataSet(confMapObjectValued.get(MLConstants.KEY_DATASET));
            Thread.sleep(2000);

            System.out.println("========== Creating Project =============");
            mlClient.createProject(confMapObjectValued.get(MLConstants.KEY_PROJECT));

            System.out.println("========== Getting projectID =============");
            CloseableHttpResponse response = mlClient.getProject(confMapObjectValued.get(MLConstants.KEY_PROJECT));
            String bodyAsString = EntityUtils.toString(response.getEntity());
            Thread.sleep(2000);

            JSONParser parser = new JSONParser();
            JSONObject o = (JSONObject) parser.parse(bodyAsString);
            long projectId = (Long) o.get("id");

            System.out.println("========== Creating Analysis =============");
            mlClient.createAnalysis(confMapObjectValued.get(MLConstants.KEY_ANALYSIS), projectId);

            System.out.println("========== Getting AnalysisID =============");
            response = mlClient.getAnalysis(confMapObjectValued.get(MLConstants.KEY_ANALYSIS), projectId);
            bodyAsString = EntityUtils.toString(response.getEntity());
            Thread.sleep(2000);

            o = (JSONObject) parser.parse(bodyAsString);
            long analysisId = (Long) o.get("id");

            System.out.println("========== Setting Model Configs =============");
            mlClient.setModelConfigs(confMapArrayValued.get(MLConstants.KEY_MODELCONFIG), analysisId);

            System.out.println("========== Setting Default Hyper-Params =============");
            mlClient.setDefaultHyperParams(analysisId);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
