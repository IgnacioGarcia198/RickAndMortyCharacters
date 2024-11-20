# Container image that runs your code
FROM alpine:3.10

RUN apk update && apk add --no-cache curl wget
RUN wget https://github.com/adoptium/temurin17-binaries/releases/download/jdk-17.0.13%2B11/OpenJDK17U-jdk_x64_linux_hotspot_17.0.13_11.tar.gz && \
                                                                                                                                                  tar -xvf OpenJDK17U-jdk_x64_linux_hotspot_17.0.13_11.tar.gz && ls -la && \
                                                                                                                                                  cp -r jdk-11.0.20.1+1 /usr/lib/jvm/

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

# Copies your code file from your action repository to the filesystem path `/` of the container
COPY . /RickAndMorty

# Code file to execute when the docker container starts up (`entrypoint.sh`)
WORKDIR /RickAndMorty

RUN ./gradlew build
