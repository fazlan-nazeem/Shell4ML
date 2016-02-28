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
package org.wso2.ml.constants;

public class MLConstants {

    public static final String DATASET_PATH = "abalone.csv";
    public static final String DATASET_NAME = "abalone_dataset";
    public static final String PROJECT_NAME = "linear_regression_project";
    public static final String ANALYISIS_NAME = "analysis_1";
    public static final String HOST = "http://localhost:9763";
    public static final String DATASET_VERSION = "1.0.0";

    public static final String DATASET_CONF = "dataset";
    public static final String PROJECT_CONF = "project";
    public static final String ANALYSIS_CONF = "analysis";
    public static final String MODEL_CONF = "model";
    public static final String MODELCONFIG_CONF = "modelconfig";

    // Constants related to REST calls
    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String BASIC = "Basic ";
    public static final String CONTENT_TYPE = "Content-Type";
    public static final String CONTENT_TYPE_APPLICATION_JSON = "application/json";
    public static final String ORIGIN_HEADER = "Origin";
    public static final String ORIGIN_HEADER_VALUE = "http://example.com";

    // model configs
    public static final String ALGORITHM_NAME = "algorithmName";
    public static final String ALGORITHM_TYPE = "algorithmType";
    public static final String RESPONSE_VARIABLE = "responseVariable";
    public static final String TRAIN_DATA_FRACTION = "trainDataFraction";

    public static final String KEY = "key";
    public static final String VALUE = "value";

    public static final String WARNING_ML_CONF_PATH = "Please provide ML configuration file path";
    public static final String WARNING_DATASET_PATH = "Please provide dataset file path";
    public static final String WARNING_HOST_URI = "Please provide ML host URI";
    public static final String SYSTEM_SHUTDOWN = "System shutting down";

    public static final String KEY_DATASET = "dataset";
    public static final String KEY_PROJECT = "project";
    public static final String KEY_ANALYSIS = "analysis";
    public static final String KEY_MODEL = "model";
    public static final String KEY_MODELCONFIG = "modelconfig";

}
