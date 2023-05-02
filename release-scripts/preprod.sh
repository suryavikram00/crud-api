#!/bin/bash

# Enable the below two line for Linux Machine and disable the windows JQ
# AWSCLI='/usr/local/bin/aws --profile netmeds '
# JQ=/usr/bin/jq

# Enable the below line for windows and disable the linux commands above
# Download windows jq from the link https://github.com/stedolan/jq/releases/download/jq-1.6/jq-win64.exe and place in d:\
 
alias jq=/d/jq-win64.exe

# Check whether argument is passed
if [ $# -eq 0 ]
  then
    echo "Build ID should be passed for production deployment!!!"
    exit
fi

BUILD_ID=$1
CLUSTER_NAME=PP-NETMEDS
SERVICE_NAME=(PP-NETMEDS-MAGNUM)
TASKDEFINITION_NAME=PREPROD_NETMEDS_MAGNUM

# Get the template of existing Task Definition by supplying "TASKDEFINITION_NAME" parameter
CURRENT_DATA=$(aws ecs describe-task-definition --task-definition $TASKDEFINITION_NAME)
# echo $CURRENT_DATA
CPU=$(echo $CURRENT_DATA |jq --raw-output .taskDefinition.cpu)
MEMORY=$(echo $CURRENT_DATA |jq --raw-output .taskDefinition.memory)
EXECUTIONROLEARN=$(echo $CURRENT_DATA |jq --raw-output .taskDefinition.executionRoleArn)
TASKROLEARN=$(echo $CURRENT_DATA |jq --raw-output .taskDefinition.taskRoleArn)
VOLUME=$(echo $CURRENT_DATA |jq .taskDefinition.volumes)

CURRENT_TASKDEFINITION=$(echo $CURRENT_DATA |jq .taskDefinition.containerDefinitions)
# echo $CURRENT_TASKDEFINITION

# get the container image URL from existing
EXISTING_IMG_URL=$(echo $CURRENT_TASKDEFINITION|jq --raw-output .[0].image)
# echo $EXISTING_IMG_URL

# remove the version part from the URL
IMG=$(echo $EXISTING_IMG_URL|cut -d ':' -f1)
# echo $IMG

NEW_IMG_URL=$(echo $IMG:$BUILD_ID)
# echo $NEW_IMG_URL

# # below command updates the container definition with new image version
NEW_TASKDEFINITION=${CURRENT_TASKDEFINITION//$EXISTING_IMG_URL/$NEW_IMG_URL}
TASK_VERSION=$(aws ecs register-task-definition --family $TASKDEFINITION_NAME --container-definitions "$NEW_TASKDEFINITION" --execution-role-arn $EXECUTIONROLEARN --task-role-arn $TASKROLEARN --network-mode awsvpc --requires-compatibilities "FARGATE" --cpu $CPU --memory $MEMORY --volumes "$VOLUME"| jq --raw-output '.taskDefinition.revision')
# echo $TASK_VERSION

# below command registers a new task definition and get the task version from its output
# echo $TASK_VERSION

# below command updates the service with new task version
# update all the services relevant to this
for EACH_SERVICE in ${SERVICE_NAME[*]} 
do
    echo Updating service: $EACH_SERVICE
    aws ecs update-service --cluster $CLUSTER_NAME --service $EACH_SERVICE --task-definition $TASKDEFINITION_NAME:$TASK_VERSION | jq --raw-output '.service.serviceName'
    echo ''
done

# this command will poll and exits when the deployment is successfull, otherwise will exit with error
aws ecs wait services-stable --cluster $CLUSTER_NAME --service ${SERVICE_NAME[*]}

echo '-------------------------'
echo 'Deployment completed.....'
echo '-------------------------'