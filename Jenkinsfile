pipeline{
    agent none
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
    }
    stages{
        stage("Checkout from SCM"){
            steps{
                git branch: 'master', credentialsId: 'github', url: 'https://github.com/Guilherme-Vale-98/VUTTR-project'
            }
        }
    }
}