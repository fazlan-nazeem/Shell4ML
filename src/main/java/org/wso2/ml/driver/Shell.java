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

import org.wso2.ml.conf.GlobalConfiguration;
import org.wso2.ml.conf.MLConfiguration;
import org.wso2.ml.constants.MLConstants;

public class Shell {

    private static GlobalConfiguration globalConf = GlobalConfiguration.getInstance();
    private static MLConfiguration mlConf = new MLConfiguration();

    public static void main(String[] args) {

        if (args != null) {
            for (int i = 0; i < args.length; i = i + 2) {
                if (args[i] == "-c") {
                    globalConf.setMlConfPath(args[i + 1]);
                } else if (args[i] == "-d") {
                    globalConf.setDatasetPath(args[i + 1]);
                } else if (args[i] == "-h") {
                    globalConf.setMlHost(args[i + 1]);
                }
            }
        }

        if (globalConf.getMlConfPath() == null) {
            System.out.println(MLConstants.WARNING_ML_CONF_PATH);
            System.out.println(MLConstants.SYSTEM_SHUTDOWN);
            return;
        }

        if (globalConf.getDatasetPath() == null) {
            System.out.println(MLConstants.WARNING_DATASET_PATH);
            System.out.println(MLConstants.SYSTEM_SHUTDOWN);
            return;
        }

        if (globalConf.getMlHost() == null) {
            System.out.println(MLConstants.WARNING_HOST_URI);
            System.out.println(MLConstants.SYSTEM_SHUTDOWN);
            return;
        }

        mlConf.readMLConfigurationFile();
        mlConf.runConfiguration();
    }

}