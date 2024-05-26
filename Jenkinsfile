pipeline{
    agent{
        label "Built-In Node"
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
    }

}