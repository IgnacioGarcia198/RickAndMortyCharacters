# Container image that runs your code
FROM alpine:3.10

RUN apt-get install -yqq curl

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
