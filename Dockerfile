FROM debian:bullseye-slim

# dependencies
RUN apt-get update && apt-get upgrade -yqq
# RUN apt-add-repository 'deb http://security.debian.org/debian-security stretch/updates main'
RUN apt-get install -yqq apt-utils git clang libclang-dev \
    build-essential curl wget software-properties-common \
    unzip bash-completion android-sdk* locales

# Install Java 17
RUN apt-get update -yqq && \
    apt-get install -yqq apt-transport-https ca-certificates wget dirmngr gnupg2 software-properties-common && \
    wget https://github.com/adoptium/temurin17-binaries/releases/download/jdk-17.0.13%2B11/OpenJDK17U-jdk_x64_linux_hotspot_17.0.13_11.tar.gz && \
    tar -xvf OpenJDK17U-jdk_x64_linux_hotspot_17.0.13_11.tar.gz && ls -la && \
    cp -r jdk-17.0.13+11 /usr/lib/jvm/
RUN update-alternatives --install /usr/bin/javac javac /usr/lib/jvm/jdk-17.0.13+11/bin/javac 2222
RUN update-alternatives --install /usr/bin/java java /usr/lib/jvm/jdk-17.0.13+11/bin/java 2222
RUN update-alternatives --install /usr/bin/jar jar /usr/lib/jvm/jdk-17.0.13+11/bin/jar 2222
RUN update-alternatives --list java --verbose
RUN update-alternatives --set java /usr/lib/jvm/jdk-17.0.13+11/bin/java
#RUN update-alternatives --config java # can be used to see the priority of each alternative just in case.

# setup locales
RUN DEBIAN_FRONTEND="noninteractive" apt-get update -yqq && \
    apt-get install -yqq locales-all && \
    rm -rf /var/lib/apt/lists/*
ENV LANG en_US.UTF-8
ENV LANGUAGE en_US.UTF-8
ENV LC_ALL en_US.UTF-8
RUN locale-gen en_US.UTF-8 && \
    dpkg-reconfigure --frontend=noninteractive locales

# paths and aliases
ENV ANDROID_HOME=/usr/lib/android-sdk
ENV PATH=$ANDROID_HOME/cmdline-tools/latest/bin:$PATH

# download android sdk
RUN mkdir -p $ANDROID_HOME/cmdline-tools/latest
RUN curl -sL --connect-timeout 30 --retry 5 --retry-delay 5 \
    https://dl.google.com/android/repository/commandlinetools-linux-8092744_latest.zip -o android-sdk.zip
RUN unzip android-sdk.zip -d .
RUN mv cmdline-tools/* $ANDROID_HOME/cmdline-tools/latest
RUN rm android-sdk.zip
RUN rm -r cmdline-tools

# sdkmanager init
COPY docker/packages.txt .
RUN yes | sdkmanager --licenses | grep "All SDK package licenses accepted"
RUN sdkmanager --package_file=packages.txt

