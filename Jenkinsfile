pipeline{
    agent{
        label "Default"
    }
    tools {
        jdk 'Java17'
        maven 'Maven3'
    }
    stages{
        stage("Cleanup Workspace"){
            steps{
                cleanWs()
            }
        }

        stage("Checkout from SCM"){
            steps{
                git branch: 'master', credentialsId: 'GITHUB_LOGIN', url: 'https://github.com/Guilherme-Vale-98/VUTTR-project'
            }
        }
        stage("Build Application"){
            steps{
                powershell "mvn clean package"
            }
        }

        stage("Test Application"){
            steps{
                powershell "mvn test"
            }
        }
    }

}