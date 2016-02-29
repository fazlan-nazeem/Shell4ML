# Shell4ML
Shell for [WSO2 Machine Learner] (https://github.com/wso2/product-ml) (Alternative for the WSO2ML UI wizard)

## Features
* Covers end to end ML pipeline using a single JSON configuration file
* Supports creating dataset, project, analysis, model, prediction, all via the shell

## Sample Configuration File 

```json
{
  "dataset": {
    "datasetName": "indiansDiabetes-decision-tree-dataset",
    "version": "1.0",
    "description": "Pima Indians Diabetes Dataset",
    "sourceType": "file",
    "destination": "file",
    "dataFormat": "csv",
    "containsHeader": "true",
    "file": "IndiansDiabetes.csv"
  },
  "project":{
    "name" : "wso2-ml-decision-tree-sample-project",
    "description" : "This project tests ml workflow for decision tree",
    "datasetName" : "indiansDiabetes-decision-tree-dataset"
  },
  "analysis":{
    "name" : "wso2-ml-decision-tree-sample-analysis",
    "comments" : "Decision Tree for Pima Indians Diabetes"
  },
  "model": {
    "name": "wso2-ml-decision-tree-sample-analysis-model",
    "analysisId": "4",
    "versionSetId": "4"
  },
  "modelconfig": [
    {
      "key": "algorithmName",
      "value": "DECISION_TREE"
    },
    {
      "key": "algorithmType",
      "value": "Classification"
    },
    {
      "key": "responseVariable",
      "value": "Class"
    },
    {
      "key": "trainDataFraction",
      "value": "0.7"
    }
  ]
}
```

This file is available in the resources directory, and by default shell4ml runs this configuration.

## Future Milestones
* Support for multiple analyses within a single project
* Support for multiple models within a single analysis
* Hyper-Parameter configurations 


## How to Contribute
* Send your bug fixes pull requests to [master branch] (https://github.com/fazlan-nazeem/Shell4ML) 

## Contact us

* email : fazlann@wso2.com / fazlan.pera@gmail.com


