# gosec-issue-review-toolkit
Command line tool for generating excel report from gosec's issue JSON file in SonarQube format

### Pre-requisite
- Before running the tool make sure that gosec result.json(anyname.json) file is available inside project root directory
- JSON file should be generated using following command
    - gosec -fmt=sonarqube -out results.json ./...
 
## How to use  

### 1) Export excel using source code

- Extract project  
- Import Project in Eclipse 
- Clean & package the application: mvn clean install
- Run ReportProcess.java file with below command-line arguments:  
    - Format:  
      - *InputJsonFile* *ProjectName(withoutspace)*
    
    - Example:  
      - results.json MyTestProject
   
- On success, check root directory of project for generated excel file.
