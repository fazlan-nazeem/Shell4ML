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
package org.wso2.ml.client;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.wso2.ml.constants.MLConstants;
import org.wso2.ml.driver.Shell;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class MLClient {


    public CloseableHttpResponse createdDataSet(JSONObject datasetConf) throws IOException {
        CloseableHttpClient httpClient =  HttpClients.createDefault();

        HttpPost httpPost = new HttpPost(MLConstants.HOST + "/api/datasets/");
        httpPost.setHeader(MLConstants.AUTHORIZATION_HEADER, getBasicAuthKey());

        MultipartEntityBuilder multipartEntityBuilder = MultipartEntityBuilder.create();
        multipartEntityBuilder.addPart("description", new StringBody(datasetConf.get("description").toString(), ContentType.TEXT_PLAIN));
        multipartEntityBuilder.addPart("sourceType", new StringBody(datasetConf.get("sourceType").toString(), ContentType.TEXT_PLAIN));
        multipartEntityBuilder.addPart("destination", new StringBody(datasetConf.get("destination").toString(), ContentType.TEXT_PLAIN));
        multipartEntityBuilder.addPart("dataFormat", new StringBody(datasetConf.get("dataFormat").toString(), ContentType.TEXT_PLAIN));
        multipartEntityBuilder.addPart("containsHeader", new StringBody(datasetConf.get("containsHeader").toString(), ContentType.TEXT_PLAIN));
        multipartEntityBuilder.addPart("datasetName", new StringBody(datasetConf.get("datasetName").toString(), ContentType.TEXT_PLAIN));
        multipartEntityBuilder.addPart("version",
                new StringBody(datasetConf.get("version").toString(), ContentType.TEXT_PLAIN));

        File file = new File(Shell.getResourcePath()+datasetConf.get("file").toString());
        multipartEntityBuilder.addBinaryBody("file", file, ContentType.APPLICATION_OCTET_STREAM, datasetConf.get("file").toString());

        httpPost.setEntity(multipartEntityBuilder.build());
            return httpClient.execute(httpPost);

    }

    public CloseableHttpResponse createProject(JSONObject projectConf){
        CloseableHttpClient httpClient =  HttpClients.createDefault();

        HttpPost httpPost = new HttpPost(MLConstants.HOST + "/api/projects");
        httpPost.setHeader(MLConstants.AUTHORIZATION_HEADER, getBasicAuthKey());
        httpPost.setHeader(MLConstants.CONTENT_TYPE, MLConstants.CONTENT_TYPE_APPLICATION_JSON);

        String projectName = projectConf.get("name").toString();
        String datasetName = projectConf.get("datasetName").toString();

        try {
            String payload;
            if (projectName == null) {
                payload = "{\"description\" : \"Test Project\",\"datasetName\": \"" + datasetName + "\"}";
            } else if (datasetName == null) {
                payload = "{\"name\" : \"" + projectName + "\",\"description\" : \"Test Project\"}";
            } else {
                payload = "{\"name\" : \"" + projectName + "\",\"description\" : \"Test Project\",\"datasetName\": \""
                        + datasetName + "\"}";
            }

            StringEntity params = new StringEntity(payload);
            httpPost.setEntity(params);
            return httpClient.execute(httpPost);
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public CloseableHttpResponse getProject(JSONObject projectConf){
        CloseableHttpClient httpClient =  HttpClients.createDefault();

        HttpGet httpGet = new HttpGet(MLConstants.HOST + "/api/projects/"+ projectConf.get("name").toString());
        httpGet.setHeader(MLConstants.AUTHORIZATION_HEADER, getBasicAuthKey());
        httpGet.setHeader(MLConstants.CONTENT_TYPE, MLConstants.CONTENT_TYPE_APPLICATION_JSON);

        try {
            return httpClient.execute(httpGet);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    public CloseableHttpResponse createAnalysis(JSONObject analysesConf, long projectId){
        CloseableHttpClient httpClient =  HttpClients.createDefault();

        HttpPost httpPost = new HttpPost(MLConstants.HOST + "/api/analyses");
        httpPost.setHeader(MLConstants.AUTHORIZATION_HEADER, getBasicAuthKey());
        httpPost.setHeader(MLConstants.CONTENT_TYPE, MLConstants.CONTENT_TYPE_APPLICATION_JSON);

        String analysisName = analysesConf.get("name").toString();

        try {
            String payload;
            if (analysisName == null) {
                payload = "{\"comments\":\"Test Analysis\",\"projectId\":" + projectId + "}";
            } else if (projectId == -1) {
                payload = "{\"name\":\"" + analysisName + "\",\"comments\":\"Test Analysis\"}";
            } else {
                payload = "{\"name\":\"" + analysisName + "\",\"comments\":\"Test Analysis\",\"projectId\":" + projectId
                        + "}";
            }
            StringEntity params = new StringEntity(payload);
            httpPost.setEntity(params);
            return httpClient.execute(httpPost);
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static CloseableHttpResponse getAnalysis(JSONObject analysisConf, long projectId){
        CloseableHttpClient httpClient =  HttpClients.createDefault();

        HttpGet httpGet = new HttpGet(MLConstants.HOST + "/api/projects/"+ projectId + "/analyses/" + analysisConf.get("name").toString());
        httpGet.setHeader(MLConstants.AUTHORIZATION_HEADER, getBasicAuthKey());
        httpGet.setHeader(MLConstants.CONTENT_TYPE, MLConstants.CONTENT_TYPE_APPLICATION_JSON);

        try {
            return httpClient.execute(httpGet);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;

    }
    public static CloseableHttpResponse setModelConfigs(JSONArray modelConfigConf, long analysisId) {

        Map<String, String> configurations = new HashMap<String, String>();
        configurations.put(MLConstants.ALGORITHM_NAME,
                ((JSONObject) modelConfigConf.get(0)).get(MLConstants.VALUE).toString());
        configurations.put(MLConstants.ALGORITHM_TYPE,
                ((JSONObject) modelConfigConf.get(1)).get(MLConstants.VALUE).toString());
        configurations.put(MLConstants.RESPONSE_VARIABLE,
                ((JSONObject) modelConfigConf.get(2)).get(MLConstants.VALUE).toString());
        configurations.put(MLConstants.TRAIN_DATA_FRACTION,
                ((JSONObject) modelConfigConf.get(0)).get(MLConstants.VALUE).toString());

        String payload = "[";
        for (Map.Entry<String, String> property : configurations.entrySet()) {
            payload = payload + "{\"key\":\"" + property.getKey() + "\",\"value\":\"" + property.getValue() + "\"},";
        }
        payload = payload.substring(0, payload.length() - 1) + "]";

        CloseableHttpClient httpClient = HttpClients.createDefault();

        try {
            HttpPost httpPost = new HttpPost(MLConstants.HOST + "/api/analyses/" + analysisId + "/configurations");
            httpPost.setHeader(MLConstants.AUTHORIZATION_HEADER, getBasicAuthKey());
            httpPost.setHeader(MLConstants.CONTENT_TYPE, MLConstants.CONTENT_TYPE_APPLICATION_JSON);
            StringEntity params = new StringEntity(payload);

            httpPost.setEntity(params);
            return httpClient.execute(httpPost);
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static CloseableHttpResponse setDefaultHyperParams(long analysisId){
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(MLConstants.HOST + "/api/analyses/" + analysisId + "/hyperParams/defaults");
        httpPost.setHeader(MLConstants.AUTHORIZATION_HEADER, getBasicAuthKey());
        httpPost.setHeader(MLConstants.CONTENT_TYPE, MLConstants.CONTENT_TYPE_APPLICATION_JSON);
        try {
            return httpClient.execute(httpPost);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }




    public static String getBasicAuthKey() {
        String token = "admin" + ":" + "admin";
        byte[] tokenBytes = token.getBytes(StandardCharsets.UTF_8);
        String encodedToken = new String(Base64.encodeBase64(tokenBytes), StandardCharsets.UTF_8);
        return (MLConstants.BASIC + encodedToken);
    }
}
