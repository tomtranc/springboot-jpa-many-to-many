#!/bin/bash -e
## This script gets executed after a VM creation to properly setup the VM

# Update package list
sudo apt-get update -y

# Install prerequisites for Azure CLI
sudo apt-get install -y ca-certificates curl apt-transport-https lsb-release gnupg

# Add Microsoft signing key and repo
curl -sL https://packages.microsoft.com/keys/microsoft.asc | gpg --dearmor | sudo tee /etc/apt/trusted.gpg.d/microsoft.gpg > /dev/null
echo "deb [arch=amd64] https://packages.microsoft.com/repos/azure-cli/ $(lsb_release -cs) main" | sudo tee /etc/apt/sources.list.d/azure-cli.list

# Install Azure CLI
sudo apt-get update -y
sudo apt-get install -y azure-cli

# Install Docker
sudo apt-get install -y docker.io

# Enable Docker service
sudo systemctl enable docker
sudo systemctl start docker

# Add current user to docker group (optional)
sudo usermod -aG docker $USER