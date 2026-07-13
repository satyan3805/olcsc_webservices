#!/usr/bin/env groovy

// Make sure to use the following configuration in the Jenkins pipeline configuration UI...
// - Build Triggers (Advanced), Filter branches by regex :   ^.*(?<!-RELEASE)$
//    - This causes builds to NOT trigger when there is a commit to a "...-RELEASE" branch.
// - Build Triggers (Advanced), Enable [ci-skip]         :   true
// - Pipeline, Branch Specifier                          :   refs/heads/${gitlabSourceBranch}

/*
import org.jenkinsci.plugins.builduser.BuildUser
import hudson.model.*
import hudson.EnvVars
import groovy.json.JsonSlurperClassic
import groovy.json.JsonBuilder
import groovy.json.JsonOutput
import java.net.URL
*/

def DEBUG=false
def CLEAN_WORKSPACE=false
def TAG_RELEASES=true
def NEXUS_PULL_REPO="nexus01.etcc.com:18443"
def MAVEN_GOAL="deploy" // "install" or "deploy"
def DOCKER_FLAG="" // Leave empty if no docker build needed.  Use -DdockerBuild if you need a docker build.
def DEPLOY_WITH_KUBERNETES=false // Only valid if above MAVEN_GOAL="deploy"
def SEND_EMAILS=true
def CR_HLP_FILE="CrHlp.tmp"
def SETTINGS_XML_FILE="settings.xml"
def CONSOLE_LOG="${JENKINS_HOME}/jobs/${JOB_NAME}/builds/${BUILD_NUMBER}/log"
def BRANCH_LOCKING_DIR="/logs/branch_locking"
def BRANCH_LOCKING_FILE=""
def USER_EMAIL=""
def BUILD_EMAIL_LIST=""

node() {

    // sh "env"

    if ("${gitlabBefore}" != "0000000000000000000000000000000000000000") { // All zeros means new branch triggered this push.

        gitlabCommitStatus(name: 'build') {

            try {

                stage ('\u2776 Stage 1'){

                    echo "\u2600 BUILD_URL=${BUILD_URL}\n" +                             /* http://rchq-etcdoc01-vmli.etcc.local:8080/job/one_pipeline_to_rule_them_all/71/ */
                         "\u2600 WORKSPACE=${WORKSPACE}\n" +                             /* /var/jenkins_home/workspace/one_pipeline_to_rule_them_all                       */
                         "\u2600 gitlabActionType=${gitlabActionType}\n" +               /* PUSH                                                                            */
                         "\u2600 gitlabSourceBranch=${gitlabSourceBranch}\n" +           /* testing_jenkins_branch                                                          */
                         "\u2600 gitlabBefore=${gitlabBefore}\n" +                       /* 5e90a63a2b8a65d7fa9261613037135a367325a0                                        */
                         "\u2600 gitlabAfter=${gitlabAfter}\n" +                         /* 9a33731ec0a79d49afb44b1f90c071864f53d983                                        */
                         "\u2600 gitlabSourceRepoHttpUrl=${gitlabSourceRepoHttpUrl}\n" + /* http://rchq-etcdoc01-vmli.etcc.local/NTTA/HOST_NTTA.git                         */
                         "\u2600 gitlabSourceNamespace=${gitlabSourceNamespace}\n" +     /* NTTA                                                                            */
                         "\u2600 gitlabSourceRepoName=${gitlabSourceRepoName}\n" +       /* HOST_NTTA                                                                       */
                         "\u2600 gitlabUserName=${gitlabUserName}\n" +                   /* hudsonbuild                                                                     */
                         "\u2600 gitlabUserEmail=REMOVED"                                /* ${gitlabUserEmail}amber.yancey@etcc.com                                                           */

                    // The below file will be looked for by a GitLab pre-receive hook.  If it is present, the pre-receive will fail (unless the user is jenkins_worker).
                    BRANCH_LOCKING_FILE="${BRANCH_LOCKING_DIR}/${gitlabSourceNamespace}.${gitlabSourceRepoName}.${gitlabSourceBranch}.locked"
                    writeFile file: "${BRANCH_LOCKING_FILE}", text: 'The presence of this file indicates that this branch is locked.'

                    gitlabUrlStripped = sh(
                                           returnStdout: true,
                                           script: "echo ${gitlabSourceRepoHttpUrl} | sed -e \"s|/${gitlabSourceNamespace}.*\$||g\"" // Escape dollar-sign and quotations in sed command.
                                          ).trim()
                    if (DEBUG) { echo "[gitlabUrlStripped]: ${gitlabUrlStripped}" }

                    jenkinsUrlStripped = sh(
                                           returnStdout: true,
                                           script: "echo ${BUILD_URL} | awk -F'/' '{print \$3}'"
                                          ).trim()
                    if (DEBUG) { echo "[jenkinsUrlStripped] ${jenkinsUrlStripped}" }

                    if (SEND_EMAILS) {
                        withCredentials([string(credentialsId: 'st__gitlab_token_for_jenkins', variable: 'PT')]) {
                            // For projects containing a dot in the namespace or repository name, we must replace the dot with %2E in the below curl command.
                            gitlabSourceNamespace_w_repl = sh(
                                                              returnStdout: true,
                                                              script: "echo ${gitlabSourceNamespace} | sed -e 's|\\.|%2E|g'"
                                                             ).trim()
                            if (DEBUG) { echo "[gitlabSourceNamespace_w_repl]: ${gitlabSourceNamespace_w_repl}" }

                            gitlabSourceRepoName_w_repl = sh(
                                                             returnStdout: true,
                                                             script: "echo ${gitlabSourceRepoName} | sed -e 's|\\.|%2E|g'"
                                                            ).trim()
                            if (DEBUG) { echo "[gitlabSourceRepoName_w_repl]: ${gitlabSourceRepoName_w_repl}" }

                            USER_EMAIL = sh(
                                            returnStdout: true,
                                            script: "curl -s --header \'PRIVATE-TOKEN: $PT\' ${gitlabUrlStripped}/api/v4/projects/${gitlabSourceNamespace_w_repl}%2F${gitlabSourceRepoName_w_repl}/repository/commits/${gitlabAfter} | jq \".committer_email\" | sed -e 's|\"\$||g' -e 's|^\"||g'"
                                            ).trim()

                            if (DEBUG) { echo "[USER_EMAIL]: ${USER_EMAIL}" }

                            BUILD_EMAIL_LIST = sh(
                                                  returnStdout: true,
                                                  script: "curl -s --header \'PRIVATE-TOKEN: $PT\' ${gitlabUrlStripped}/api/v4/projects/${gitlabSourceNamespace_w_repl}%2F${gitlabSourceRepoName_w_repl}/services/pipelines-email | jq \".properties.recipients\" | sed -e 's|\"\$||g' -e 's|^\"||g'"
                                                 ).trim()
                            if (DEBUG) { echo "[BUILD_EMAIL_LIST]: ${BUILD_EMAIL_LIST}" }
                        } // end withCredentials

                        try { notifyBuild('STARTED', USER_EMAIL + "," + BUILD_EMAIL_LIST) }
                        catch (exc) { echo "Caught error during email-sending stage." }
                    }

                    isReleaseBranch = sh(
                                         returnStatus: true,
                                         script: "echo ${gitlabSourceBranch} | grep -E ^.*-RELEASE\$" // 0: found, 1: not found
                                        ) == 0
                    if (DEBUG) { echo "[isReleaseBranch]: ${isReleaseBranch}" }

                    withCredentials([[$class: 'UsernamePasswordMultiBinding', credentialsId: 'nexus_deployment', passwordVariable: 'nexus_pw', usernameVariable: 'nexus_un']]) {
                        sh "echo ${nexus_pw} | docker login --username=${nexus_un} --password-stdin ${NEXUS_PULL_REPO}"
                    }

                    def mavenImage = docker.image("${NEXUS_PULL_REPO}/etcc_maven:3.3.9-jdk8u362") // https://registry.hub.docker.com/_/maven/
                    mavenImage.pull()
                    mavenImage.inside("--group-add ${env.DOCKER_GROUP_ID} --volume ${env.DOCKER_SOCKET_LOCATION}:${env.DOCKER_SOCKET_LOCATION} --env TZ=${env.TZ} --hostname maven-build-on-${env.JENKINS_CONTAINER_PARENT_HOSTNAME} --env MAVEN_CONFIG=/tmp/.m2 --entrypoint=''") {
                        // #########################################################################################################################
                        if (CLEAN_WORKSPACE) {
                            echo "+---------------------+\n" +
                                 "| Cleaning workspace. |\n" +
                                 "+---------------------+"
                            sh "rm -rf * .git* .m2*"
                        }

                        // Check out the source code.
                        git url: '${gitlabSourceRepoHttpUrl}', credentialsId: 'gitlab_login', branch: '${gitlabSourceBranch}'

                        // Get the shortened git hash, to be used as part of the "new" Maven version number, and also part of the Jenkins build description.
                        shortCommit = sh(
                                         returnStdout: true,
                                         script: "git rev-parse --short=8 ${env.gitlabAfter}" // Using 8 digits, to match GitLab's short hash length.
                                        ).trim()
                        if (DEBUG) { echo "[shortCommit]: ${shortCommit}" }

                        withCredentials([[$class: 'UsernamePasswordMultiBinding', credentialsId: 'nexus_deployment', passwordVariable: 'nexus_pw', usernameVariable: 'nexus_un']]) {
                            writeFile file: "$SETTINGS_XML_FILE",
                                      text: "<settings>\n" +
                                            "   <localRepository>${pwd()}/.m2repo</localRepository>\n" + // So we don't need to download all dependencies on every build.
                                            "   <!--\n" +
                                            "   <proxies>\n" +
                                            "       <proxy>\n" +
                                            "           <id>etcc_proxy</id>\n" +
                                            "           <active>true</active>\n" +
                                            "           <protocol>http</protocol>\n" +
                                            "           <host>wbproxy-lb1.etcchostedservices.local</host>\n" +
                                            "           <port>3128</port>\n" +
                                            "           <nonProxyHosts>10.200.10.68|nexus01.etcc.com</nonProxyHosts>\n" +
                                            "       </proxy>\n" +
                                            "   </proxies>\n" +
                                            "   -->\n" +
                                            "   <servers>\n" +
                                            "       <server>\n" +
                                            "           <id>releaseDeploymentRepo</id>\n" +
                                            "           <username>$nexus_un</username>\n" +
                                            "           <password>$nexus_pw</password>\n" +
                                            "       </server>\n" +
                                            "       <server>\n" +
                                            "           <id>snapshotDeploymentRepo</id>\n" +
                                            "           <username>$nexus_un</username>\n" +
                                            "           <password>$nexus_pw</password>\n" +
                                            "       </server>\n" +
                                            "       <server>\n" +
                                            "           <id>nexusDockerRegistry</id>\n" +
                                            "           <username>$nexus_un</username>\n" +
                                            "           <password>$nexus_pw</password>\n" +
                                            "           <configuration>\n" +
                                            "               <email>jenkins_worker@etcc.com</email>\n" +
                                            "           </configuration>\n" +
                                            "       </server>\n" +
                                            "   </servers>\n" +
                                            "   <profiles>\n" +
                                            "       <profile>\n" +
                                            "             <id>GlobalDefaultProfile</id>\n" +
                                            "             <properties>\n" +
                                            "                  <weblogic1034AutoDeployDir>/var/jenkins_home/workspace</weblogic1034AutoDeployDir>\n" +
                                            "              </properties>\n" +
                                            "       </profile>\n" +
                                            "   </profiles>\n" +
                                            "   <activeProfiles>\n" +
                                            "           <activeProfile>GlobalDefaultProfile</activeProfile>\n" +
                                            "   </activeProfiles>\n" +
                                            "</settings>\n"
                        } // end withCredentials

                        sh """
                            set +x
                            chmod 600 $SETTINGS_XML_FILE
                            git status -bs
                            echo "\u2600 ===== [CHANGELOG] =========================================================================="
                            git diff --name-status ${gitlabBefore} ${gitlabAfter}
                            echo "\u2600 ============================================================================================"
                        """

                        // Retrieve the previous version number from the pom.xml file.
                        mavenReturnCode = sh(
                                             returnStatus: true,
                                             script: "mvn -s $SETTINGS_XML_FILE --batch-mode org.apache.maven.plugins:maven-help-plugin:2.1.1:evaluate -Dexpression=project.version > mvn-debug.temp 2>&1" // Had to change backslash to double-backslash.
                                            )

                        if (mavenReturnCode != 0) {
                            echo "[mavenReturnCode]: ${mavenReturnCode}"
                            sh "cat mvn-debug.temp"
                        }

                        pomVersion = sh(
                                        returnStdout: true,
                                        script: "cat mvn-debug.temp | grep -Ev '(^\\[|Download\\w+:)'" // Had to change backslash to double-backslash.
                                       ).trim()

                        if (DEBUG) { echo "[pomVersion]: ${pomVersion}" }

                        if (isReleaseBranch) { // We don't want to use the git commit-hash with RELEASE branches.
                            nexusFlag="nexusRelease"
                            newPomVersion=pomVersion
                        }
                        else {
                            nexusFlag="nexusSnapshot"

                            // Strip the previous version (from the pom.xml file) of its git hash.
                            pomVersionStripped = sh(
                                                    returnStdout: true,
                                                    script: "echo $pomVersion | grep -E -o '^[0-9]+\\.[0-9]+\\.[0-9]+'" // Escape dollar-sign and backslash in sed command. // sed -e 's|-[a-f0-9]\\{8\\}\$||g'"
                                                   ).trim()
                            if (DEBUG) { echo "[pomVersionStripped]: ${pomVersionStripped}" }

                            newPomVersion="${pomVersionStripped}-${shortCommit}"

                            // sh "mvn -s $SETTINGS_XML_FILE --batch-mode versions:set -DgenerateBackupPoms=false -DnewVersion=${pomVersionStripped}-${shortCommit}" -- This didn't work for zero-PlateProcessing/zero-PlateLabeler build.
                            sh "mvn -s $SETTINGS_XML_FILE --batch-mode org.codehaus.mojo:versions-maven-plugin:2.3:set -DgenerateBackupPoms=false -DnewVersion=${newPomVersion}"
                        }

                        // The --batch-mode flag should reduce the amount of download output, to keep the console logs more legible.
                        mavenBuildReturnCode = sh(
                                                  returnStatus: true,
                                                  script: "mvn -s $SETTINGS_XML_FILE --batch-mode clean ${MAVEN_GOAL} -P${nexusFlag} ${DOCKER_FLAG} "  // -DpushImage deploy install -PexecJar -DskipTests -DdockerBuild -PnexusSnapshot -PnexusRelease -e -X
                                                 )
                        // #########################################################################################################################
                    } // end mavenImage.inside()

                    if (mavenBuildReturnCode == 0) {
                        if ("$MAVEN_GOAL" == "deploy") {
                            withCredentials([[$class: 'UsernamePasswordMultiBinding', credentialsId: 'gitlab_login', passwordVariable: 'gl_pw', usernameVariable: 'gl_un']]) {
                                gitCrdHlpString = sh(
                                                     returnStdout: true,
                                                     script: "set +x; echo ${gitlabUrlStripped} | sed -e \"s|://|://${gl_un}:${gl_pw}@|g\"" // Escape quotations in sed command.
                                                    ).trim()
                            } // end withCredentials

                            if (!isReleaseBranch) { // Push auto-incremented pom.xmls to repository.
                                sh """
                                    set +x
                                    git config --global user.email 'jenkins_worker@etcc.com'
                                    git config --global user.name 'jenkins_worker'
                                    git add '*pom.xml'
                                    git commit --message='[ci-skip] Commit performed by Jenkins build.'
                                    echo ${gitCrdHlpString} > $CR_HLP_FILE 2>&1
                                    chmod 600 $CR_HLP_FILE
                                    git config credential.helper \"store --file=$CR_HLP_FILE\"
                                    git push origin ${gitlabSourceBranch}
                                    git config --local --unset credential.helper # Otherwise, the file keeps resurrecting itself.
                                """
                            } // end if (!isReleaseBranch)
                            else {
                                if (DEPLOY_WITH_KUBERNETES) {
                                    withCredentials([file(credentialsId: 'admin_conf', variable: 'kubeconfig_file')]) {
                                        if (fileExists("target/docker/kubernetes_deployment.yaml")) {
                                            sh 'kubectl --kubeconfig ${kubeconfig_file} apply -f target/docker/kubernetes_deployment.yaml'
                                        }
                                        else if (fileExists("zero/target/docker/kubernetes_deployment.yaml")) {
                                            sh 'kubectl --kubeconfig ${kubeconfig_file} apply -f zero/target/docker/kubernetes_deployment.yaml'
                                        }
                                        else {
                                            echo "No kubernetes_deployment.yaml file detected."
                                        }
                                    }
                                } // end if (DEPLOY_WITH_KUBERNETES)
                                if (TAG_RELEASES) {
                                    sh """
                                        set +x
                                        git config --global user.email 'jenkins_worker@etcc.com'
                                        git config --global user.name 'jenkins_worker'
                                        git tag -a tag-${gitlabSourceBranch} -m "Tagging performed automatically as part of Jenkins release build."
                                        echo ${gitCrdHlpString} > $CR_HLP_FILE 2>&1
                                        chmod 600 $CR_HLP_FILE
                                        git config credential.helper \"store --file=$CR_HLP_FILE\"
                                        git push origin tag-${gitlabSourceBranch}
                                        git config --local --unset credential.helper # Otherwise, the file keeps resurrecting itself.
                                    """
                                } // end if (TAG_RELEASES)
                            } // end else
                        } // end if ("$MAVEN_GOAL" == "deploy")
                    }
                    else {
                        error "Maven build failed with exit code $mavenBuildReturnCode." // This throws hudson.AbortException
                    }
                } // end stage

                currentBuild.result = "SUCCESS"

            } // end try
            catch (hudson.AbortException ae) {
                if (DEBUG) { echo "Caught: ${ae}" }

                if (ae.getMessage().contains('exit code 143')) { // This gets triggered by a user aborting the build during maven execution.
                    currentBuild.result = "ABORTED"
                }
                else /*if (ae.getMessage().contains('Maven build failed') || ae.getMessage().contains('exit code 1') || ae.getMessage().contains('exit code 2'))*/ {
                    currentBuild.result = "FAILURE"
                }
            }
            catch (org.jenkinsci.plugins.workflow.steps.FlowInterruptedException fie) { // This gets triggered by a user aborting the build.
                if (DEBUG) { echo "Caught: ${fie}" }
                currentBuild.result = "ABORTED"
            }
            catch (exc) {
                if (DEBUG) { echo "Caught: ${exc}" }
                currentBuild.result = "FAILURE"
            } // end catch
            finally {
                if ( "$DOCKER_FLAG" != "" )
                {
                    sh """
                        NUM_IMAGES_TO_DELETE=\$(docker images --filter=reference='*/*:${newPomVersion}' -q | wc -l)
                        IMAGES_TO_DELETE=\$(docker images --filter=reference='*/*:${newPomVersion}' -q)
                        if [ "\$NUM_IMAGES_TO_DELETE" = "1" ]; then docker rmi \$IMAGES_TO_DELETE; fi
                    """
                }

                if (fileExists("$SETTINGS_XML_FILE"))   { sh "set +x; rm $SETTINGS_XML_FILE"   }
                if (fileExists("$CR_HLP_FILE"))         { sh "set +x; rm $CR_HLP_FILE"         }
                if (fileExists("$BRANCH_LOCKING_FILE")) { sh "set +x; rm $BRANCH_LOCKING_FILE" }

                try { if (SEND_EMAILS) { notifyBuild(currentBuild.result, USER_EMAIL + "," + BUILD_EMAIL_LIST) } }
                catch (rm_exc) { echo "Caught error during email-sending stage." }

                try { currentBuild.description = "[Branch: ${gitlabSourceBranch} #${shortCommit}]<br>" + currentBuild.description }
                catch (rm_exc) { /*echo "Caught: ${rm_exc}"*/ }
            } // end finally
        } // end gitlabCommitStatus

        def result
        switch (currentBuild.result) {
            case "SUCCESS":
                result = 'success'
                break
            case "FAILURE":
                result = 'failed'
                break
            case "ABORTED":
                result = 'canceled'
                break
            default:
                result = 'default'
                break
        }

        echo "RESULT=$result"
        updateGitlabCommitStatus name: 'build', state: "$result"

    } // end if
    else {
        echo "A new branch (${gitlabSourceBranch}) has been detected, but no build needed here."
        currentBuild.description = "[Branch: ${gitlabSourceBranch} (new)]<br>" + currentBuild.description
    }
} // end node

def notifyBuild(String buildStatus = 'STARTED', String buildEmailList = '') {
    // Build status of null means successful.
    buildStatus = buildStatus ?: 'SUCCESS'

    def details = ""

    if (buildStatus == 'STARTED') {
        details = """
            <font face="verdana" color="green" size="2">
                <p>
                    This Jenkins build was triggered by a push to GitLab.<br>
                    Build console output is available @ ${env.BUILD_URL}console
                </p>
            </font>
        """
    } else {
        details = """
            <font face="verdana" color="green" size="2">
                <p>
                    The Jenkins build completion status is : BUILD ${buildStatus}<br>
                    Build console output is available @ ${env.BUILD_URL}console
                </p>
            </font>
        """
    }

    String recipient = "${buildEmailList}"
    mail subject: "[${buildStatus}] : Jenkins Build : ${env.JOB_NAME} (${env.BUILD_NUMBER})",
            body: "${details}",
        mimeType: 'text/html',
              to: recipient,
         replyTo: recipient,
            from: "jenkins@etcc.com"
}
